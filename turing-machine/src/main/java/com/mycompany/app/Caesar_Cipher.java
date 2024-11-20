package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Caesar_Cipher {
    private int key;
    private String input;
    private List<String> inputTape;
    private List<String> alphabetTape;
    private List<String> kTape;
    private Map<String, List<String>> delta;

    public int getKey() {
        return key;
    }

    public String getInput() {
        return input;
    }

    public List<String> getInputTape() {
        return inputTape;
    }

    public List<String> getAlphabetTape() {
        return alphabetTape;
    }

    public List<String> getkTape() {
        return kTape;
    }

    public Map<String, List<String>> getDelta() {
        return delta;
    }

    public Caesar_Cipher(Machine machine, String input) {
        System.err.println("\nGuardando información para encriptar");
        String[] inputInfo = input.split("#");
        this.key = Integer.parseInt(inputInfo[0].trim()) % 26;
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
        for (int i = 0; i < (alphabet.size() - 1); i++) { // alfabeto - ' '
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
        System.err.println("Encriptando mensaje...");
        this.delta = delta;
        String state = initialState;
        int index = 2;
        int alph_index = 2;
        int k_index = 1;

        String charTape = inputTape.get(index);
        String alphTape = alphabetTape.get(alph_index);
        String kcharTape = kTape.get(k_index);

        while (!(state.equals(acceptanceState) && charTape.equals("-"))) {
            List<String> changes = delta_transitions(state, charTape, alphTape, kcharTape);
            state = changes.get(0);
            inputTape.set(index, changes.get(1));
            alphabetTape.set(alph_index, changes.get(2));
            kTape.set(k_index, changes.get(3));

            index += Integer.parseInt(changes.get(4));
            alph_index += Integer.parseInt(changes.get(5));
            k_index += Integer.parseInt(changes.get(6));

            charTape = inputTape.get(index);
            alphTape = alphabetTape.get(alph_index);
            kcharTape = kTape.get(k_index);
        }

        String encriptedMessage = String.join("", inputTape);

        return encriptedMessage.replace("-", "");
    }

    private List<String> delta_transitions(String state, String charInput, String charAlphabet, String charK) {
        List<String> changes;

        // Use transitions
        String keyValue = state + ", ";
        if (charInput.equals(charAlphabet)) {
            keyValue += "alpha, alpha, ";
        } else if (charInput.equals(" ")) {
            keyValue += " , beta, ";
        } else if (charAlphabet.equals("-")) {
            keyValue += "alpha, -, ";
        } else {
            keyValue += "alpha, beta, ";
        }
        keyValue += charK;

        changes = new ArrayList<>(delta.get(keyValue));

        if (changes.get(1).equals(changes.get(2))) {
            switch (changes.get(1)) {
                case "alpha":
                    changes.set(1, charInput);
                    changes.set(2, charInput);
                    break;
                case "beta":
                    changes.set(1, charAlphabet);
                    changes.set(2, charAlphabet);
                    break;
            }
        } else {
            changes.set(1, charInput);
            changes.set(2, charAlphabet);
        }

        // Reemplazar dirección por valor de suma
        for (int i = 4; i < changes.size(); i++) {
            if (changes.get(i).equals("S")) {
                changes.set(i, "0");
            } else if (changes.get(i).equals("R")) {
                changes.set(i, "1");
            } else if (changes.get(i).equals("L")) {
                changes.set(i, "-1");
            }
        }

        return changes;
    }
}
