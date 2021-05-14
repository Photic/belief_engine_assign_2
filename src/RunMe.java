import java.util.Scanner;

import model.BeliefBase;
import sentences.*;

public class RunMe {
    public static void main(String args[]) throws Exception {
    	//SentenceToCNFTest();
    	beliefBaseSentenceTest();
    	/*
        Scanner scan = new Scanner(System.in);
        String line = "";

        while (!line.equals("q")) {
            line = input(scan);

        }

        System.out.println("Agent have Exited");*/
    }

    public static String input(Scanner scan) {
        System.out.print("Give input to agent: ");
        return scan.nextLine();
    }
    public static void SentenceToCNFTest() throws Exception {
    	String b11 = "b11";
    	String p12 = "p12";
    	String p21 = "p21";
    	
    	Sentence sentence = new BiimplicationSentence(
    			new AtomicSentence(b11),
    			new OrSentence(
    					new AtomicSentence(p12),
    					new AtomicSentence(p21)
    					)
    			);
    	System.out.println(String.format("Sentence before CNF conversion: %s", sentence.toString()));
    	sentence = sentence.convertToCNF();
    	System.out.println(String.format("Sentence after CNF conversion: %s", sentence.toString()));
    	System.out.println(String.format("Sentence isInCNF()-evaluation: %s", sentence.isInCNF()));
    	
    	String alpha = "alpha";
    	String beta = "beta";
    	String gamma = "gamma";
    	
    	Sentence distriTest = new BiimplicationSentence(
    			new AtomicSentence(alpha),
    			new OrSentence(
    					new AtomicSentence(beta),
    					new AtomicSentence(gamma)
    					)
    			);
    	System.out.println(String.format("Sentence before CNF conversion: %s", distriTest.toString()));
    	distriTest = sentence.convertToCNF();
    	System.out.println(String.format("Sentence after CNF conversion: %s", distriTest.toString()));
    	System.out.println(String.format("Sentence isInCNF()-evaluation: %s", distriTest.isInCNF()));
    }
    public static void beliefBaseSentenceTest() throws Exception {
    	BeliefBase bBase = new BeliefBase();
    	String alpha = "alpha";
    	String beta = "beta";
    	String gamma = "gamma";

    	bBase.add(alpha);
    	bBase.add(new OrSentence(new AtomicSentence(alpha), new NotSentence(new AtomicSentence(beta))));
    	bBase.add(new AndSentence(new NotSentence(new AtomicSentence(alpha)), new OrSentence(new AtomicSentence(beta), new NotSentence(new NotSentence(new AtomicSentence(gamma))))));
    	bBase.add(new BiimplicationSentence(
    				new AtomicSentence(alpha),
    				new OrSentence(
    					new AtomicSentence(beta),
    					new AtomicSentence(gamma)
    					)
		));
				 
    	System.out.println(bBase.toString());
    	String alphaCopy = "alpha";
    	System.out.println(String.format("Result of adding duplicate String: %s", bBase.add(alphaCopy)));
    	System.out.println(bBase.toString());
    	bBase.convertAllToCNF();
    	System.out.println("Result of converting to CNF: ");
    	System.out.println(bBase.toString());
    	
    	
    }
}

