import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Encriptar {
    private int key;
    private String input;
    private List<String> inputTape;
    private List<String> alphabetTape;
    private List<String> k_alphabetTape;

    public Encriptar(Machine machine, String input) {
        this.key = input.charAt(0) - '0';
        this.input = input.substring(1);

        tapesInitialization(machine.getBlanc(), machine.getΣ());
    }

    private void tapesInitialization(String blanc, List<String> alphabet) {
        inputTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < input.length(); i++) {
            inputTape.add(String.valueOf(input.charAt(i)));
        }
        inputTape.add(blanc);
        inputTape.add(blanc);

        alphabetTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        k_alphabetTape = new ArrayList<>(Arrays.asList(blanc, blanc));
        for (int i = 0; i < alphabet.size(); i++) {
            alphabetTape.add(alphabet.get(i));
        }

        for (int i = 0; i < (key - 1); i++) {
            k_alphabetTape.add(" ");
        }

    }

    public String derivation(String initialState, String acceptanceState) {
        String state = initialState;
        int index = 2;
        int alph_index = 2;
        int k_index = 2;
        String charTape = inputTape.get(index);
        String alphTape = alphabetTape.get(alph_index);
        String kTape = alphabetTape.get(k_index);

        while (state != initialState) {
            String[] changes = transition(state, charTape, alphTape, kTape);
            state = changes[0];
            charTape = changes[1];
            alphTape = changes[2];
            kTape = changes[3];

            index += Integer.parseInt(changes[4]);
            alph_index += Integer.parseInt(changes[5]);
            k_index += Integer.parseInt(changes[6]);
        }

        return "ahjbfdj";
    }

    private String[] transition(String state, String charInput, String charAlphabet, String charKAlphabet) {
        String[] changes = new String[7];
        // alpha = charInput
        // beta = charAlphabet
        switch (state) {
            case "q0":
                if (charInput != charAlphabet) { // ["q0", "alpha", "beta", "-"], ["q0", "alpha", "beta", "-", "S", "R",
                                                 // "S"]
                    changes[0] = "q0";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "0";
                } else if (charInput == charAlphabet) { // ["q0", "alpha", "alpha", "-"], ["q2", "alpha", "alpha", "-",
                                                        // "S", "R", "R"]
                    changes[0] = "q2";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "1";
                    changes[6] = "1";
                } else if (charAlphabet == "-") { // ["q0", "alpha", "-", "-"], ["q1", "alpha", "-", "-", "S", "L", "S"]
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
                if (charInput != charAlphabet) { // ["q1", "alpha", "beta", "-"], ["q1", "alpha", "beta", "-", "S", "L",
                                                 // "S"]
                    changes[0] = "q1";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = "-";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                } else if (charInput == charAlphabet) { // ["q1", "alpha", "alpha", "-"], ["q2", "alpha", "alpha", "-",
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
                if (charInput != charAlphabet) {
                    if (charKAlphabet == " ") { // ["q2", "alpha", "beta", " "], ["q2", "alpha", "beta", " ", "S", "R",
                                                // "R"]
                        changes[0] = "q2";
                        changes[1] = charInput;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "0";
                        changes[5] = "1";
                        changes[6] = "1";
                    } else if (charKAlphabet == "-") { // ["q2", "alpha", "beta", "-"], ["q4", "beta", "beta", "-", "R",
                                                       // "S", "L"]
                        changes[0] = "q4";
                        changes[1] = charAlphabet;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "1";
                        changes[5] = "0";
                        changes[6] = "-1";
                    }
                } else if (charAlphabet == "-") { // ["q2", "alpha", "-", " "], ["q3", "alpha", "-", " ", "S", "L", "S"]
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
                if (charInput != charAlphabet) { // ["q3", "alpha", "beta", " "], ["q3", "alpha", "beta", " ", "S", "L",
                                                 // "S"]
                    changes[0] = "q3";
                    changes[1] = charInput;
                    changes[2] = charAlphabet;
                    changes[3] = " ";
                    changes[4] = "0";
                    changes[5] = "-1";
                    changes[6] = "0";
                } else if (charInput == charAlphabet) { // ["q3", "alpha", "-", " "], ["q2", "alpha", "-", " ", "S",
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
                if (charInput != charAlphabet) {
                    if (charKAlphabet == " ") { // ["q4", "alpha", "beta", " "], ["q4", "alpha", "beta", " ", "S", "S",
                                                // "L"]
                        changes[0] = "q4";
                        changes[1] = charInput;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "0";
                        changes[5] = "0";
                        changes[6] = "-1";
                    } else if (charKAlphabet == "-") { // ["q4", "alpha", "beta", " "], ["q4", "alpha", "beta", " ",
                                                       // "S", "S", "L"]
                        changes[0] = "q4";
                        changes[1] = charAlphabet;
                        changes[2] = charAlphabet;
                        changes[3] = " ";
                        changes[4] = "0";
                        changes[5] = "0";
                        changes[6] = "-1";
                    }
                }
                break;
            default:
                break;
        }
        return changes;
    }

}
