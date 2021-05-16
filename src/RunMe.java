import java.util.Scanner;

import model.BeliefBase;
import sentences.*;

public class RunMe {
    public static void main(String args[]) throws Exception {
    	//SentenceToCNFTest();
    	//beliefBaseSentenceTest();
    	contractionPostulateTests();
    	revisionPostulateTests();
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
    	distriTest = distriTest.convertToCNF();
    	System.out.println(String.format("Sentence after CNF conversion: %s", distriTest.toString()));
    	System.out.println(String.format("Sentence isInCNF()-evaluation: %s", distriTest.isInCNF()));
    	System.out.println(String.format("distriTest predicate list: %s", distriTest.getPredicates()));
    }
    
    public static void revisionPostulateTests() {
    	revisionSuccess();
    	revisionInclusion();
    	revisionVacuity();
    	revisionExtensionality();
    	
    }
    public static void contractionPostulateTests() {
    	contractionSuccess();
    	contractionInclusion();
    	contractionVacuity();
    	contractionExtensionality();
    }

	private static void contractionSuccess() {
		System.out.println("Testing the Success postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		bBase.contract(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the belief base after contraction with q: %s", bBase));
		System.out.println("The implication was no longer valid, and thus is it no longer in the resulting belief set");
		System.out.println();
		
	}

	private static void contractionInclusion() {
		System.out.println("Testing the Inclusion postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		bBase.contract(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the belief base after contraction with r: %s", bBase));
		System.out.println("We can see that the belief set after contraction with r is a subset of the original belief set");
		System.out.println();
		
	}

	private static void contractionVacuity() {
		System.out.println("Testing the Vacuity postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		bBase.contract(new AtomicSentence("s"));
		System.out.println(String.format("Elements in the belief base after contraction with s: %s", bBase));
		System.out.println("Since s wasn't in the belief base, the contraction does not change the belief set");
		System.out.println();
		
	}

	private static void contractionExtensionality() {
		System.out.println("Testing the Extensionality postulate for contraction");
		BeliefBase bBase1 = new BeliefBase();
		bBase1.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		bBase1.expand(new AtomicSentence("p"));
		bBase1.expand(new AtomicSentence("q"));
		bBase1.expand(new AtomicSentence("r"));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
    	bBase2.expand(new AtomicSentence("p"));
		bBase2.expand(new AtomicSentence("q"));
		bBase2.expand(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("Elements in the second belief base: %s", bBase2));
		bBase1.contract(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
		bBase2.contract(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the first belief base after revision with (!p | q): %s", bBase1));
		System.out.println(String.format("Elements in the second belief base after revision with (p => q): %s", bBase2));
		System.out.println("We can see that the contractions behave in the same way, and the extensionality postulate is fulfilled.");
		System.out.println();
		
	}

	private static void revisionSuccess() {
		System.out.println("Testing the Success postulate for revision");
		BeliefBase bBase = new BeliefBase();
		System.out.println(String.format("Elements in the belief base: %s", bBase));
    	bBase.revise(new AtomicSentence("p"));
		System.out.println(String.format("Elements in the belief base after revision with p: %s", bBase));
		System.out.println(String.format("Does the belief base contain p?: %s", bBase.contains(new AtomicSentence("p"))));
		System.out.println();
		
	}

	private static void revisionInclusion() {
		System.out.println("Testing the Inclusion postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand(new AtomicSentence("p"));
    	bBase1.expand(new AtomicSentence("q"));
    	bBase1.expand(new AtomicSentence("r"));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand(new AtomicSentence("p"));
    	bBase2.expand(new AtomicSentence("q"));
    	bBase2.expand(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("Elements in the second belief base: %s", bBase2));
		bBase1.revise(new AtomicSentence("s"));
		System.out.println(String.format("Elements in the first belief base after revision with s: %s", bBase1));
		bBase2.expand(new AtomicSentence("s"));
		System.out.println(String.format("Elements in the second belief base after expanding with s: %s", bBase2));
		System.out.println("We can see they contain the same elements, therefore the first belief base is a subset of the second.");
		System.out.println();
		
	}

	private static void revisionVacuity() {
		System.out.println("Testing the Vacuity postulate for revision");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("Elements in the belief base: %s", bBase));
		bBase.revise(new AtomicSentence("s"));
		System.out.println(String.format("Elements in the belief base after revision with s: %s", bBase));
		System.out.println("Since !s wasn't in the belief base, the revision behaves as expand");
		System.out.println();
	}

	private static void revisionExtensionality() {
		System.out.println("Testing the Extensionality postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand(new AtomicSentence("p"));
    	bBase1.expand(new AtomicSentence("q"));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand(new AtomicSentence("p"));
    	bBase2.expand(new AtomicSentence("q"));
		System.out.println(String.format("Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("Elements in the second belief base: %s", bBase2));
		bBase1.revise(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the first belief base after revision with (!p | q): %s", bBase1));
		bBase2.revise(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("Elements in the second belief base after revision with (p => q): %s", bBase2));
		System.out.println("We can see that the revisions behave in the same way, and the extensionality postulate is fulfilled.");
		System.out.println();
		
	}
}

