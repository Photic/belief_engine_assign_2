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
    	System.out.println(String.format("distriTest predicate list: %s", distriTest.getPredicates()));
    }
    public static void beliefBaseSentenceTest() throws Exception {
    	BeliefBase bBase = new BeliefBase();
    	/*
    	bBase.add("alpha");
    	bBase.add(new OrSentence(new AtomicSentence("beta"), new NotSentence(new AtomicSentence("gamma"))));
    	bBase.add(new AndSentence(new NotSentence(new AtomicSentence("alpha")), new OrSentence(new AtomicSentence("beta"), new NotSentence(new NotSentence(new AtomicSentence("omega"))))));
    	bBase.add(new BiimplicationSentence(
    				new AtomicSentence("alpha"),
    				new OrSentence(
    					new AtomicSentence("beta"),
    					new AtomicSentence("gamma")
    					)
		));
    	*/
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
    	System.out.println(bBase.toString());
    	System.out.println("Revising bBase with (!p | q)");
    	bBase.revise(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
    	System.out.println(bBase.toString());
    	bBase.clear();
    	System.out.println("Clearing bBase");
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
    	System.out.println(bBase.toString());
    	System.out.println("Revising bBase with (p => q)");
    	bBase.revise(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
    	System.out.println(bBase.toString());
    	
    	bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
    	System.out.println(bBase.toString());
    	bBase.revise(new NotSentence(new OrSentence(new AtomicSentence("q"),new AtomicSentence("r"))));
    	System.out.println(bBase.toString());
    	/*
    	System.out.println(bBase.toString());
    	String alphaCopy = "alpha";
    	System.out.println(String.format("Result of adding duplicate String: %s", bBase.add(alphaCopy)));
    	System.out.println(bBase.toString());
    	bBase.convertAllToCNF();
    	System.out.println("Result of converting to CNF: ");
    	*/
    	
    }
    public static void revisionPostulateTests() {
    	revisionSuccess();
    	revisionInclusion();
    	revisionVacuity();
    	revisionExtensionality();
    	
    }

	private static void revisionSuccess() {
		System.out.println("Testing the Success postulate for revision");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand("p");
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		System.out.println(String.format("Does the belief base contain p?: %s", bBase.contains(new AtomicSentence("p"))));
		System.out.println();
		
	}

	private static void revisionInclusion() {
		System.out.println("Testing the Inclusion postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand("p");
    	bBase1.expand("q");
    	bBase1.expand("r");
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand("p");
    	bBase2.expand("q");
    	bBase2.expand("r");
		System.out.println(String.format("Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("Elements in the second belief base: %s", bBase2));
		bBase1.revise("s");
		System.out.println(String.format("Elements in the first belief base after revision with s: %s", bBase1));
		bBase2.expand("s");
		System.out.println(String.format("Elements in the second belief base after expanding with s: %s", bBase2));
		System.out.println("We can see they contain the same elements, therefore the first belief base is a subset of the second.");
		System.out.println();
		
	}

	private static void revisionVacuity() {
		System.out.println("Testing the Vacuity postulate for revision");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand("p");
    	bBase.expand("q");
    	bBase.expand("r");
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		bBase.revise("s");
		System.out.println(String.format("Elements in the belief base after revision with s: %s", bBase));
		System.out.println("Since !s wasn't in the belief base, the revision behaves as expand");
		System.out.println();
	}

	private static void revisionExtensionality() {
		System.out.println("Testing the Extensionality postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand("p");
    	bBase1.expand("q");
    	bBase1.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand("p");
    	bBase2.expand("q");
    	bBase2.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("Elements in the second belief base: %s", bBase2));
		bBase1.revise(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the first belief base after revision with (!p | q): %s", bBase1));
		bBase2.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the second belief base after revision with (p => q): %s", bBase2));
		System.out.println("We can see that the revisions behave in the same way, and the extensionality postulate is fulfilled.");
		System.out.println();
		
	}
}

