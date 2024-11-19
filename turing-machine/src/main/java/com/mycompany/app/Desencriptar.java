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
        for (int i = 0; i < input.length(); i++) {
            kTape.add("-");
        }
        kTape.add(blanc);
        kTape.add(blanc);
    }

    public String derivation(String initialState, String acceptanceState, Map<String, List<String>> delta) {
        System.err.println("Desencriptando mensaje...");
        this.delta = delta;
        String state = initialState;
        int index = 2;

        while (index < inputTape.size() - 2) {
            String currentChar = inputTape.get(index);
            if (Character.isLetter(currentChar.charAt(0))) {
                char decryptedChar = shiftLetter(currentChar.charAt(0), -key);
                inputTape.set(index, String.valueOf(decryptedChar));
            }
            index++;
        }

        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 2; i < inputTape.size() - 2; i++) {
            String c = inputTape.get(i);
            if (!c.equals("-") && !c.equals(" ")) {
                decryptedMessage.append(c);
            }
        }
        
        return decryptedMessage.toString();
    }

    private char shiftLetter(char c, int shift) {
        if (!Character.isLetter(c)) return c;
        
        int base = Character.isLowerCase(c) ? 'a' : 'A';
        int position = c - base;
        int newPosition = (position + shift + 26) % 26;
        return (char) (base + newPosition);
    }
}