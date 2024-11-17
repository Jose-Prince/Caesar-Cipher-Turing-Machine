package com.mycompany.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Encriptar {
    private int key;
    private String input;
    private List<String> inputTape;
    private List<String> alphabetTape;
    private List<String> kTape;
    private Map<String, List<String>> delta;

    public Encriptar(Machine machine, String input) {
        System.err.println("\nGuardando información para encriptar :)");
        String[] inputInfo = input.split("#");
        this.key = Integer.parseInt(inputInfo[0].trim());
        this.input = inputInfo[1];

        tapesInitialization(machine.getBlanc(), machine.getsigma());
    }

    private void tapesInitialization(String blanc, List<String> alphabet) {
        inputTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < input.length(); i++) { // alfabeto - ' '
            inputTape.add(String.valueOf(input.charAt(i)));
        }
        inputTape.add(blanc);
        inputTape.add(blanc);

        alphabetTape = new ArrayList<>();
        for (int i = 0; i < (alphabet.size()); i++) {
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
        System.err.println("\nEncriptando mensaje...");
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

    private String[] transition(String state, String charInput, String charAlphabet, String charK) {
        String[] changes = new String[7];
        // alpha = charInput
        // beta = charAlphabet
        switch (state) {
            case "q0":
                if (!charInput.equals(charAlphabet)) { // ["q0", "alpha", "beta", "-"], ["q0", "alpha", "beta", "-",
                                                       // "S", "R",
                    // "S"]
                    changes[0] = "q0";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "0";
                } else if (charInput.equals(charAlphabet)) { // ["q0", "alpha", "alpha", "-"], ["q2", "alpha", "alpha",
                                                             // "-", "S", "R", "R"]
                    changes[0] = "q2";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "1";
                } else if (charAlphabet.equals("-")) { // ["q0", "alpha", "-", "-"], ["q1", "alpha", "-", "-", "S", "L",
                                                       // "S"]
                    changes[0] = "q1";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                }
                break;
            case "q1":
                if (!charInput.equals(charAlphabet)) { // ["q1", "alpha", "beta", "-"], ["q1", "alpha", "beta", "-",
                                                       // "S", "L",
                    // "S"]
                    changes[0] = "q1";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                } else if (charInput.equals(charAlphabet)) { // ["q1", "alpha", "alpha", "-"], ["q2", "alpha", "alpha",
                                                             // "-",
                    // "S", "R", "R"]
                    changes[0] = "q2";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "1";
                }
                break;
            case "q2":
                if (!charInput.equals(charAlphabet)) {
                    if (charK.equals(" ")) { // ["q2", "alpha", "beta", " "], ["q2", "alpha", "beta", " ", "S", "R",
                        // "R"]
                        changes[0] = "q2";
                        changes[1] = charInput;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "0";
                        changes[5] = "1";
                        changes[6] = "1";
                    } else if (charK.equals("-")) { // ["q2", "alpha", "beta", "-"], ["q4", "beta", "beta", "-", "R",
                        // "S", "L"]
                        changes[0] = "q4";
                        changes[1] = charAlphabet;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "1";
                        changes[5] = "0";
                        changes[6] = "-1";
                    }
                } else if (charAlphabet.equals("-")) { // ["q2", "alpha", "-", " "], ["q3", "alpha", "-", " ", "S", "L",
                                                       // "S"]
                    changes[0] = "q3";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = " ";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                }
                break;
            case "q3":
                if (!charInput.equals(charAlphabet)) { // ["q3", "alpha", "beta", " "], ["q3", "alpha", "beta", " ",
                                                       // "S", "L",
                    // "S"]
                    changes[0] = "q3";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = " ";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                } else if (charInput.equals(charAlphabet)) { // ["q3", "alpha", "-", " "], ["q2", "alpha", "-", " ",
                                                             // "S",
                    // "R", "S"]
                    changes[0] = "q2";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = " ";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "0";
                }
                break;
            case "q4":
                if (!charInput.equals(charAlphabet)) {
                    if (charK.equals(" ")) { // ["q4", "alpha", "beta", " "], ["q4", "alpha", "beta", " ", "S", "S",
                        // "L"]
                        changes[0] = "q4";
                        changes[1] = charInput;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "0";
                        changes[5] = "0";
                        changes[6] = "-1";
                    } else if (charK.equals("-")) { // ["q4", "alpha", "beta", "-"], ["q0", "alpha", "beta", "-", "S",
                                                    // "S",
                        // "R"]
                        changes[0] = "q0";
                        changes[1] = charAlphabet;
                        changes[2] = charAlphabet;
                        changes[3] = "-";
                        changes[4] = "0";
                        changes[5] = "0";
                        changes[6] = "1";
                    }
                }
                break;
            default:
                break;
        }
        return changes;
    }

    private List<String> delta_transitions(String state, String charInput, String charAlphabet, String charK) {
        List<String> changes;

        // Use transitions
        String keyValue = state + ", ";
        if (charInput.equals(charAlphabet)) {
            keyValue += "alpha, alpha, ";
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
