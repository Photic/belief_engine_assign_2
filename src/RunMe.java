import java.util.Scanner;

import model.BeliefBase;
import model.Literal;
import sentences.*;

public class RunMe {
    public static void main(String args[]) {
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
	
	public static void SentenceToCNFTest() {
		Literal b11 = new Literal("b11", true);
		Literal p12 = new Literal("p12", true);
		Literal p21 = new Literal("p21", true);

		Sentence sentence = new BiimplicationSentence(new AtomicSentence(b11),
				new OrSentence(new AtomicSentence(p12), new AtomicSentence(p21)));
		System.out.println(String.format("Sentence before CNF conversion: %s", sentence.toString()));
		sentence = sentence.convertToCNF();
		System.out.println(String.format("Sentence after CNF conversion: %s", sentence.toString()));
		System.out.println(String.format("Sentence isInCNF()-evaluation: %s", sentence.isInCNF()));

		Literal alpha = new Literal("alpha", false);
		Literal beta = new Literal("beta", true);
		Literal gamma = new Literal("gamma", true);

		Sentence distriTest = new BiimplicationSentence(new AtomicSentence(alpha),
				new OrSentence(new AtomicSentence(beta), new AtomicSentence(gamma)));
		System.out.println(String.format("Sentence before CNF conversion: %s", distriTest.toString()));
		distriTest = sentence.convertToCNF();
		System.out.println(String.format("Sentence after CNF conversion: %s", distriTest.toString()));
		System.out.println(String.format("Sentence isInCNF()-evaluation: %s", distriTest.isInCNF()));
	}
	
    public static void beliefBaseSentenceTest() {
    	BeliefBase bBase = new BeliefBase();
    	Literal alpha = new Literal("alpha", true);
    	Literal beta = new Literal("beta", true);
    	Literal gamma = new Literal("gamma", true);
    	Literal notAlpha = new Literal("alpha", false);
    	Literal notBeta = new Literal("beta", false);
		Literal notGamma = new Literal("gamma", false);
		
    	bBase.add(alpha);
    	bBase.add(new OrSentence(new AtomicSentence(alpha), new AtomicSentence(notBeta)));
    	bBase.add(new AndSentence(new AtomicSentence(notAlpha), new OrSentence(new AtomicSentence(beta), new NotSentence(new AtomicSentence(notGamma)))));
    	bBase.add(new BiimplicationSentence(
    				new AtomicSentence(alpha),
    				new OrSentence(
    					new AtomicSentence(beta),
    					new AtomicSentence(gamma)
    					)
		));
				 
    	System.out.println(bBase.toString());
    	Literal alphaCopy = alpha.copy();
    	System.out.println(String.format("Result of adding duplicate literal: %s", bBase.add(alphaCopy)));
    	System.out.println(bBase.toString());
    	bBase.convertAllToCNF();
    	System.out.println("Result of converting to CNF: ");
    	System.out.println(bBase.toString());
    	
    	
    }
}

