package control;

import java.util.*;
import java.util.regex.Pattern;
import model.BeliefBase;
import model.Constants;
import sentences.*;


public class UserInputControl {

    private List<String> constants = new ArrayList<String>();

    public UserInputControl() {
        constants.add(Constants.AND);
        constants.add(Constants.IF);
        constants.add(Constants.IFF);
        constants.add(Constants.OR);
    }

    public void splitIntoBeliefBaseSentences() {
        String input = "( alpha ,  alpha | !beta , ( !alpha & (beta | (!!gamma)) , alpha <=> (beta | gamma), !beta )";
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

    private void addResolvedSentencesToBeliefBase(List<String> resolvedSentence, BeliefBase beliefBase) {
        for (String string : resolvedSentence) {
            if (Pattern.matches("^.*[\\(\\)].*$", string)) {
                complexBeliefBaseAddition(resolvedSentence, beliefBase);
                return;
            }
        }

        simpleBeliefBaseAddition(resolvedSentence, beliefBase);
    }

    private void complexBeliefBaseAddition(List<String> sentence, BeliefBase beliefBase) {
        boolean foundParentheses = false;
        List<List<Sentence>> output = new ArrayList<>();
        List<Sentence> builder = new ArrayList<Sentence>();
        List<Sentence> builder2 = new ArrayList<Sentence>();
        int j = 0;

        for (int i = 0; i < sentence.size(); i++) {
            if (Pattern.matches("^.*[\\(].*$", sentence.get(i))) {
                i = findParenthesesEnding(sentence, i, j, builder2, output);
                builder2 = new ArrayList<Sentence>();
                foundParentheses = true;
            } else {
                for (j = i; j < sentence.size(); j++) {

                    if (foundParentheses && constants.contains(sentence.get(j))) {
                        builder.add(findSentence(sentence.get(j)));
                        output.add(builder);
                        builder = new ArrayList<Sentence>();
                        continue;
                    }

                    boolean constantFound = false;
                    if (constants.contains(sentence.get(j))) {
                        constantFound = true;
                        builder.add(findSentence(sentence.get(j)));
                        break;
                    }

                    if (!constantFound) {
                        builder.add(literalResolution(sentence.get(j).replaceAll("[()]", "")));
                        continue;
                    }
                }

                output.add(builder);
                builder = new ArrayList<Sentence>();

                i = j;
            }
        }

        finallyAddComplexToBeliefBase(output, beliefBase);
    }
    
    private void finallyAddComplexToBeliefBase(List<List<Sentence>> output, BeliefBase beliefBase) {

        System.out.println();

        // TODO mangler stadig at finde ud af hvordan jeg samler det i funktioner. Tager en pause.

        BinarySentence top = null;
        BinarySentence middle = null;
        BinarySentence bottom = null;

        Sentence alpha = null;
        Sentence beta = null;

        for (int i = output.size() - 1; i >= 0; i--) {
            for (int j = output.get(i).size() - 1; j >= 0; j--) {
                //System.out.println(output.get(i).get(j).getClass());
                if (middle != null && output.get(i).get(j) instanceof BinarySentence) {
                    System.out.println("Top set to " + output.get(i).get(j).getClass());
                    top = (BinarySentence) output.get(i).get(j);
                    alpha = null;
                    beta = new AtomicSentence();
                } else if (bottom == null && output.get(i).get(j) instanceof BinarySentence) {
                    System.out.println("Bottom set to " + output.get(i).get(j).getClass());
                    bottom = (BinarySentence) output.get(i).get(j);
                } else if (beta == null && middle == null && !(output.get(i).get(j) instanceof BinarySentence)) {
                    System.out.println("Beta set to " + output.get(i).get(j));
                    beta = output.get(i).get(j);
                } else if (alpha == null && !(output.get(i).get(j) instanceof BinarySentence)) {
                    System.out.println("Alpha set to " + output.get(i).get(j));
                    alpha = output.get(i).get(j);
                }

                if (bottom != null && beta != null && alpha != null && top == null) {
                    System.out.println("Alpha to middle " + alpha);
                    System.out.println("Beta to middle " + beta);
                    System.out.println("middle class " + bottom.getClass());
                    middle = getSentence(alpha, beta, bottom);
                    bottom = null;
                    alpha = null;
                    beta = null;
                    System.out.println("Middle is now " + middle);
                    continue;
                } else if (alpha != null && middle != null) {
                    System.out.println("Alpha set on top");
                    System.out.println("Coming with to Top alpha " + alpha + " and a middle " + middle);
                    top = getSentence(alpha, middle, top);
                    middle = null;
                    alpha = null;
                    continue;
                }
            }
        }
        
        beliefBase.expand(top);
    }
    
    private BinarySentence getSentence(Sentence alpha, Sentence beta, BinarySentence type) {
        if (type.getClass() == OrSentence.class) {
            return new OrSentence(alpha.copy(), beta.copy());
        } else if (type.getClass() == AndSentence.class) {
            return new AndSentence(alpha.copy(), beta.copy());
        } else if (type.getClass() == ImplicationSentence.class) {
            return new ImplicationSentence(alpha.copy(), beta.copy());
        }

        return new BiimplicationSentence(alpha.copy(), beta.copy());
    }

    private BinarySentence getSentence(Sentence alpha, BinarySentence beta, BinarySentence type) {
        if (type.getClass() == OrSentence.class) {
            return new OrSentence(alpha.copy(), beta.copy().copy());
        } else if (type.getClass() == AndSentence.class) {
            return new AndSentence(alpha.copy(), beta);
        } else if (type.getClass() == ImplicationSentence.class) {
            return new ImplicationSentence(alpha.copy(), beta.copy());
        }

        System.out.println("Bi impli set");
        return new BiimplicationSentence(alpha.copy(), beta.copy());
    }
    
    private int findParenthesesEnding(List<String> sentence, int i, int j, List<Sentence> builder, List<List<Sentence>> output) {
        int startParentheses = 0;
        int endParentheses = 0;

        for (j = i; j < sentence.size(); j++) {
            if (i == j) {
                startParentheses++;
                builder.add(literalResolution(sentence.get(j).replaceAll("[()]", "")));
                continue;
            }

            if (constants.contains(sentence.get(j))) {
                builder.add(findSentence(sentence.get(j)));
                continue;
            }

            for (int k = 0; k < sentence.get(j).length(); k++) {
                if (sentence.get(j).charAt(k) == '(') {
                    startParentheses++;
                }

                if (sentence.get(j).charAt(k) == ')') {
                    endParentheses++;
                }
            }

            if (startParentheses - 1 == endParentheses) {
                output.add(builder);
                builder = new ArrayList<Sentence>();
                return findParenthesesEnding(sentence, j, j, builder, output);
            }

            if (startParentheses == endParentheses) {
                builder.add(literalResolution(sentence.get(j).replaceAll("[()]", "")));
                break;
            }

            builder.add(literalResolution(sentence.get(j).replaceAll("[()]", "")));
        }

        output.add(builder);
        builder = new ArrayList<Sentence>();

        return j;
    }
    
    private Sentence findSentence(String con) {
        if (Constants.OR.equals(con)) {
            return new OrSentence();
        } else if (Constants.AND.equals(con)) {
            return new AndSentence();
        } else if (Constants.IFF.equals(con)) {
            return new BiimplicationSentence();
        }

        return new ImplicationSentence();
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

        beliefBase.expand(pairingSentenceResolved(pairingSentence, pairs));
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
                beliefBase.expand(literalResolution(string));
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
