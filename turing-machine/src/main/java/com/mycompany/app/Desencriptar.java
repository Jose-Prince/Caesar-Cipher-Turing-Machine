package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Desencriptar {
    private int key;
    private String input;
    private List<String> inputTape;
    private List<String> alphabetTape;
    private List<String> kTape;
    private Map<String, List<String>> delta;

    public Desencriptar(Machine machine, String input) {
        System.err.println("\nGuardando información para desencriptar");
        String[] inputInfo = input.split("#");
        this.key = Integer.parseInt(inputInfo[0].trim());
        this.input = inputInfo[1].toLowerCase().trim();
        
        tapesInitialization(machine.getBlanc(), machine.getsigma());
    }

    private void tapesInitialization(String blanc, List<String> alphabet) {
        inputTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < input.length(); i++) {
            inputTape.add(String.valueOf(input.charAt(i)));
        }
        inputTape.add(blanc);
        inputTape.add(blanc);

        alphabetTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < alphabet.size(); i++) {
            alphabetTape.add(alphabet.get(i));
        }
        alphabetTape.add(blanc);
        alphabetTape.add(blanc);

        kTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < (key - 1); i++) {
            kTape.add(" ");
        }
        kTape.add(blanc);
        kTape.add(blanc);
    }

    public String derivation(String initialState, String acceptanceState, Map<String, List<String>> delta) {
        System.err.println("Desencriptando mensaje...");
        this.delta = delta;
        String state = initialState;
        int index = 2;
        int alph_index = 2;
        int k_index = 1;

        String charTape = inputTape.get(index);
        String alphTape = alphabetTape.get(alph_index);
        String kcharTape = kTape.get(k_index);

        int maxIterations = 1000;
        int iterations = 0;

        while (!(state.equals(acceptanceState) && charTape.equals("-")) && iterations < maxIterations) {
            System.err.println("\nCurrent State: " + state);
            System.err.println("Input Char: " + charTape);
            System.err.println("Alphabet Char: " + alphTape);
            System.err.println("K Char: " + kcharTape);
            
            List<String> changes = delta_transitions(state, charTape, alphTape, kcharTape);

            if (changes != null) {
                System.err.println("Transition found:");
                System.err.println("New state: " + changes.get(0));
                System.err.println("New input char: " + changes.get(1));
                System.err.println("New alphabet char: " + changes.get(2));
                System.err.println("New k char: " + changes.get(3));
                System.err.println("Movements - Input: " + changes.get(4) + 
                                  ", Alphabet: " + changes.get(5) + 
                                  ", K: " + changes.get(6));
            }
            
            if (changes == null) {
                System.err.println("No valid transition found for current configuration");
                System.err.println("Current tapes before looking for transition:");
                System.err.println("Input tape: " + inputTape);
                System.err.println("Alphabet tape: " + alphabetTape);
                System.err.println("K tape: " + kTape);
                break;
            }
            
            // Apply changes
            state = changes.get(0);
            inputTape.set(index, changes.get(1));
            alphabetTape.set(alph_index, changes.get(2));
            kTape.set(k_index, changes.get(3));

            // Update positions
            index += Integer.parseInt(changes.get(4));
            alph_index += Integer.parseInt(changes.get(5));
            k_index += Integer.parseInt(changes.get(6));

            // Ensure we don't go out of bounds
            index = Math.max(0, Math.min(index, inputTape.size() - 1));
            alph_index = Math.max(0, Math.min(alph_index, alphabetTape.size() - 1));
            k_index = Math.max(0, Math.min(k_index, kTape.size() - 1));

            // Get next characters
            charTape = inputTape.get(index);
            alphTape = alphabetTape.get(alph_index);
            kcharTape = kTape.get(k_index);
            
            iterations++;
        }

        if (iterations >= maxIterations) {
            System.err.println("Warning: Maximum iterations reached");
        }

        String decryptedMessage = String.join("", inputTape);
        return decryptedMessage.replace("-", "");
    }

    private List<String> delta_transitions(String state, String charInput, String charAlphabet, String charK) {
        // Build transition key
        StringBuilder keyValue = new StringBuilder(state).append(", ");
        
        boolean isInputLetter = !charInput.equals("-") && !charInput.equals(" ");
        boolean isAlphabetLetter = !charAlphabet.equals("-") && !charAlphabet.equals(" ");
        
        // Handle specific state cases
        if (state.equals("q1")) {
            if (charAlphabet.equals("-")) {
                keyValue.append("alpha, -, ");
            } else if (charInput.equals(charAlphabet)) {
                keyValue.append("alpha, alpha, ");
            } else {
                keyValue.append("alpha, beta, ");
            }
        }
        // State q0 cases
        else if (state.equals("q0")) {
            if (charInput.equals("-")) {
                keyValue.append("-, beta, ");
            } else if (charInput.equals(charAlphabet)) {
                keyValue.append("alpha, alpha, ");
            } else if (charAlphabet.equals("-")) {
                keyValue.append("alpha, -, ");
            } else {
                keyValue.append("alpha, beta, ");
            }
        }
        // State q2 cases
        else if (state.equals("q2")) {
            if (charInput.equals("-") && charAlphabet.equals("-")) {
                keyValue.append("-, -, ");
            } else if (charAlphabet.equals("-")) {
                keyValue.append("alpha, -, ");
            } else if (charInput.equals(charAlphabet)) {
                keyValue.append("alpha, alpha, ");
            } else {
                keyValue.append("alpha, beta, ");
            }
        }
        // Other states
        else {
            if (charInput.equals("-")) {
                keyValue.append("-, beta, ");
            } else if (charInput.equals(charAlphabet)) {
                keyValue.append("alpha, alpha, ");
            } else if (charAlphabet.equals("-")) {
                keyValue.append("alpha, -, ");
            } else {
                keyValue.append("alpha, beta, ");
            }
        }
        
        // Add the K tape character
        keyValue.append(charK == null || charK.isEmpty() ? " " : charK);
    
        String transitionKey = keyValue.toString();
        System.err.println("Looking for transition with key: " + transitionKey);
        
        List<String> transitionValue = delta.get(transitionKey);
        
        // Try alternative key if needed
        if (transitionValue == null && (charK == null || charK.isEmpty() || charK.equals(" "))) {
            String altKey = keyValue.substring(0, keyValue.length() - 1) + "-";
            transitionValue = delta.get(altKey);
        }
        
        if (transitionValue == null) {
            System.err.println("No transition found for key: " + transitionKey);
            System.err.println("Available transitions: " + delta.keySet());
            return null;
        }
    
        List<String> changes = new ArrayList<>(transitionValue);
    
        // Handle symbol substitutions based on state
        if (state.equals("q2") && changes.get(1).equals("beta") && isInputLetter) {
            // Perform Caesar cipher shift
            char shiftedChar = shiftLetter(charInput.charAt(0), -key);
            changes.set(1, String.valueOf(shiftedChar));
            changes.set(2, String.valueOf(shiftedChar));
        } else if (!changes.get(1).equals("-")) {
            if (changes.get(1).equals(changes.get(2))) {
                changes.set(1, charInput);
                changes.set(2, charInput);
            } else if (changes.get(1).equals("beta")) {
                changes.set(1, charAlphabet);
                changes.set(2, charAlphabet);
            } else {
                changes.set(1, charInput);
                if (changes.get(2).equals("beta")) {
                    changes.set(2, charAlphabet);
                } else {
                    changes.set(2, charInput);
                }
            }
        }
    
        // Convert movement directions
        for (int i = 4; i < changes.size(); i++) {
            changes.set(i, changes.get(i).equals("S") ? "0" : 
                        changes.get(i).equals("R") ? "1" : "-1");
        }
    
        return changes;
    }
    
    private char shiftLetter(char c, int shift) {
        if (!Character.isLetter(c)) return c;
        
        int base = Character.isUpperCase(c) ? 'A' : 'a';
        int shifted = c - base;
        shifted = Math.floorMod(shifted + shift, 26);  // Ensure positive modulo
        return (char)(base + shifted);
    }
    

}