package control;

import java.util.*;
import java.util.regex.Pattern;

import model.BeliefBase;
import model.Constants;
import sentences.AndSentence;
import sentences.AtomicSentence;
import sentences.BiimplicationSentence;
import sentences.ImplicationSentence;
import sentences.NotSentence;
import sentences.OrSentence;
import sentences.Sentence;

public class UserInputControl {

    private List<String> constants = new ArrayList<String>();
    private boolean remove;

    public UserInputControl() {
        constants.add(Constants.AND);
        constants.add(Constants.IF);
        constants.add(Constants.IFF);
        constants.add(Constants.OR);
    }

    public void splitIntoBeliefBaseSentences() {
        String input = "( alpha ,  alpha | !beta ,  !alpha & (beta | (!!gamma)) , alpha <=> ((beta | gamma)), !beta )";
        System.out.println(input);
        input = removeLeadingAndTrailingEncapsulations(input);
        String[] split = input.split(",");

        BeliefBase beliefBase = new BeliefBase();

        for (int i = 0; i < split.length; i++) {
            split[i] = split[i].replaceAll("\\s+", "");
        }

        List<String> splitArray = new ArrayList<>();
        Collections.addAll(splitArray, split);

        findSingularAtomicSentences(splitArray, beliefBase);

        System.out.println(beliefBase);
        System.out.println("");
        System.out.println("Whats Left Over");
        System.out.println("");

        // for (String string : splitArray) {
        // System.out.println(string);
        // }

        findMoreComplexSentences(splitArray, beliefBase);

        System.out.println(beliefBase);
    }

    public void findMoreComplexSentences(List<String> split, BeliefBase beliefBase) {
        List<List<String>> resolvedSentences = new ArrayList<>();

        for (String string : split) {
            List<String> input = resolveComplexSentence(string);
            List<String> output = new ArrayList<>();

            output = handleInputOutputList(input);
            resolvedSentences.add(output);
        }

        System.out.println(resolvedSentences);

        for (List<String> list : resolvedSentences) {
            addResolvedSentencesToBeliefBase(list, beliefBase);
        }
    }

    // ( alpha , alpha | !beta , !alpha & (beta | (!!gamma)) , alpha <=> (beta |
    // gamma) )

    /*
     * bBase.add(alpha);
     * 
     * bBase.add(new OrSentence(new AtomicSentence(alpha), new
     * AtomicSentence(notBeta)));
     * 
     * bBase.add(new AndSentence(new AtomicSentence(notAlpha), new OrSentence(new
     * AtomicSentence(beta), new NotSentence(new AtomicSentence(notGamma)))));
     * 
     * bBase.add(new BiimplicationSentence( new AtomicSentence(alpha), new
     * OrSentence( new AtomicSentence(beta), new AtomicSentence(gamma) )));
     */

    private void addResolvedSentencesToBeliefBase(List<String> resolvedSentence, BeliefBase beliefBase) {
        List<Class> functions = new ArrayList<>();

        for (String string : resolvedSentence) {
            if (Pattern.matches("^.*[\\(\\)].*$", string)) {
                // TODO, more complex sentence
                return;
            }
        }

        simpleBeliefBaseAddition(resolvedSentence, beliefBase);
    }

    private void complexBeliefBaseAddition(List<String> sentence, BeliefBase beliefBase) {

    }

    private void simpleBeliefBaseAddition(List<String> sentence, BeliefBase beliefBase) {
        boolean isAConstant = false;
        String pairingSentence = "";
        Pair<Sentence, Sentence> pairs = new Pair<>();

        for (String string : sentence) {
            for (String con : constants) {
                if (string.contains(con)) {
                    pairingSentence = con;
                    isAConstant = true;
                    break;
                }
            }
            if (!isAConstant) {
                if (pairs.first == null) {
                    pairs.first = literalResolution(string);
                } else {
                    pairs.last = literalResolution(string);
                }
            }
            isAConstant = false;
        }

        beliefBase.add(pairingSentenceResolved(pairingSentence, pairs));
    }

    private Sentence literalResolution(String literal) {
        int nots = 0;

        for (int i = 0; i < literal.length(); i++) {
            if (literal.charAt(i) == Constants.NOT.charAt(0)) {
                nots++;
            }
        }

        if (nots == 0) {
            return new AtomicSentence(literal);
        }

        if (nots == 1) {
            return new NotSentence(new AtomicSentence(literal.replaceAll("\\!", "")));
        }

        if (nots == 2) {
            return new NotSentence(new NotSentence(new AtomicSentence(literal.replaceAll("\\!", ""))));
        }

        return null;
    }

    private Sentence pairingSentenceResolved(String con, Pair<Sentence, Sentence> sentences) {
        if (Constants.OR.equals(con)) {
            return new OrSentence(sentences.first, sentences.last);
        } else if (Constants.AND.equals(con)) {
            return new AndSentence(sentences.first, sentences.last);
        } else if (Constants.IFF.equals(con)) {
            return new BiimplicationSentence(sentences.first, sentences.last);
        } else if (Constants.IF.equals(con)) {
            return new ImplicationSentence(sentences.first, sentences.last);
        }

        return null;
    }

    private List<String> handleInputOutputList(List<String> input) {
        boolean constAdded = false;
        List<String> output = new ArrayList<>();

        for (int i = 0; i < input.size(); i++) {
            for (String cos : constants) {
                List<String> input2;
                if (input.get(i).length() > cos.length() + 1 && input.get(i).contains(cos)) {
                    input2 = resolveComplexSentence(input.get(i));

                    for (int j = 0; j < input2.size(); j++) {
                        output.add(input2.get(j));
                    }

                    constAdded = true;
                    break;
                }
                constAdded = false;
            }
            if (!constAdded) {
                output.add(input.get(i));
                constAdded = false;
            }
        }

        for (int i = 0; i < output.size(); i++) {
            for (String cos : constants) {
                if (output.get(i).length() > cos.length() + 1 && output.get(i).contains(cos)) {
                    return handleInputOutputList(output);
                }
            }
        }

        return output;
    }

    private List<String> resolveComplexSentence(String string) {
        boolean breakThis = false;
        List<String> output = new ArrayList<>();

        for (int i = 0; i < string.length(); i++) {
            for (String con : constants) {
                for (int j = 0; j < con.length(); j++) {
                    if (string.charAt(i) == con.charAt(j)) {
                        String first = string.substring(0, i);
                        String second = string.substring(i + con.length(), string.length());

                        output.add(first);
                        output.add(con);
                        output.add(second);
                        breakThis = true;
                        break;
                    }
                }
                if (breakThis) {
                    break;
                }
            }
            if (breakThis) {
                break;
            }
        }
        return output;
    }

    public void findSingularAtomicSentences(List<String> split, BeliefBase beliefBase) {
        boolean add = true;

        List<String> removeObj = new ArrayList<>();

        for (String string : split) {
            for (String con : constants) {
                if (string.contains(con)) {
                    add = false;
                }
            }
            if (add) {
                beliefBase.add(literalResolution(string));
                removeObj.add(string.replaceAll("\\s+", ""));
            }
            add = true;
        }

        for (String string : removeObj) {
            split.remove(string);
        }
    }

    public String removeLeadingAndTrailingEncapsulations(String input) {
        if (input.charAt(0) == '(' && input.charAt(input.length() - 1) == ')') {
            return input.substring(1, input.length() - 1);
        }
        return input;
    }

}
