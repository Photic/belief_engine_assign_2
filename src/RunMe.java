import java.util.Scanner;

import control.UserInputControl;
import model.BeliefBase;
import sentences.*;

public class RunMe {
	public static void main(String args[]) throws Exception {
        Scanner scan = new Scanner(System.in);
		String line = "";
		UserInputControl user = new UserInputControl();
		BeliefBase beliefBase = new BeliefBase();

		boolean userInput = false;
		boolean preProgrammedExamples = false;
		boolean manipulateBB = false;

		while (!line.toLowerCase().equals("q")) {
			if (!userInput && !preProgrammedExamples && !manipulateBB) {
				System.out.println();
				System.out.println("\t | Menu\t\t| Type \t\t\t\t\t| Options");
				System.out.println("\t | \t\t: Input Belief Base\t\t\t: 1 ");
				System.out.println("\t | \t\t: Examples\t\t\t\t: 2");
				System.out.println("\t | \t\t: Quit Agent\t\t\t\t: q");

				line = input(scan, "Choose Menu Option: ");
				if (line.toLowerCase().equals("q")) {
					continue;
				}

				if (line.toLowerCase().equals("1")) {
					userInput = true;
					preProgrammedExamples = false;
					continue;
				} else if (line.toLowerCase().equals("2")) {
					preProgrammedExamples = true;
					userInput = false;
					continue;
				}

				System.out.println("\t | " + line + " Is not an option");
				continue;
			}

			if (manipulateBB) {
				System.out.println();
				System.out.println("\t | Menu\t\t| Type \t\t\t\t\t| Options");
				System.out.println("\t | Expand\t: Belief Base with Sentence\t\t: 1");
				System.out.println("\t | Contract\t: Belief Base with Sentence\t\t: 2");
				System.out.println("\t | Revise\t: Belief Base with Sentence\t\t: 3");
				System.out.println("\t | Contain\t: Does Belief Base contain Sentence\t: 4");
				System.out.println("\t | CNF\t\t: Shown Belief Base as CNF\t\t: 5");
				System.out.println("\t | Back\t\t\t\t\t\t\t: b");
				System.out.println("\t | Quit Agent\t\t\t\t\t\t: q");

				line = input(scan, "Choose Menu Option: ");
				if (line.toLowerCase().equals("q")) {
					continue;
				}

				switch (line.toLowerCase()) {
					case "1":
						line = input(scan, "Sentence = ");
						BeliefBase bb = user.splitIntoBeliefBaseSentences(line);
						for (Sentence sent : bb.getSentences()) {
							beliefBase.expand(sent);
						}
						System.out.println("\t | The Belief Base is now = " + beliefBase);
						break;
					case "2":
						line = input(scan, "Sentence = ");
						BeliefBase bb2 = user.splitIntoBeliefBaseSentences(line);
						for (Sentence sent : bb2.getSentences()) {
							beliefBase.contract(sent);
						}
						System.out.println("\t | The Belief Base is now = " + beliefBase);
						break;
					case "3":
						line = input(scan, "Sentence = ");
						BeliefBase bb4 = user.splitIntoBeliefBaseSentences(line);
						for (Sentence sent : bb4.getSentences()) {
							beliefBase.revise(sent);
						}
						System.out.println("\t | The Belief Base is now = " + beliefBase);
						break;
					case "4":
						line = input(scan, "Sentence = ");
						BeliefBase bb3 = user.splitIntoBeliefBaseSentences(line);
						boolean containsSentence = false;
						for (Sentence sent : bb3.getSentences()) {
							containsSentence = beliefBase.contains(sent);
						}
						if (containsSentence) {
							System.out.println("\t | The belief base contains the sentence = " + line);
							System.out.println("\t | The Belief Base is = " + beliefBase);
						} else {
							System.out.println("\t | The belief base does not contain the sentence = " + line);
							System.out.println("\t | The Belief Base is = " + beliefBase);
						}
						break;
					case "5":
						BeliefBase toCnf = new BeliefBase();
						for (Sentence sent : beliefBase.getSentences()) {
							toCnf.expand(sent);
						}
						toCnf.convertAllToCNF();
						System.out.println("\t | The Belief Base as CNF = " + toCnf);
						break;
					case "b":
						preProgrammedExamples = false;
						manipulateBB = false;
						userInput = false;
						System.out.println();
						break;
					default:
						System.out.println("\t | " + line + " Is not an option");
						break;
				}

				continue;
			}

			if (userInput) {
				line = input(scan, "Belief Base = ");
				if (line.toLowerCase().equals("q")) {
					continue;
				}

				beliefBase = user.splitIntoBeliefBaseSentences(line);
				System.out.println("\t | The Belief Base is now = " + beliefBase);
				manipulateBB = true;
				userInput = false;
				continue;
			}

			if (preProgrammedExamples) {
				System.out.println();
				System.out.println("\t | Menu\t\t| Type \t\t\t\t\t| Options");
				System.out.println("\t | Test\t\t: Revision Postulate Tests\t\t: 1");
				System.out.println("\t | Test\t\t: Contraction Postulate Tests\t\t: 2");
				System.out.println("\t | Test\t\t: Sentence To CNF Test\t\t\t: 3");
				System.out.println("\t | Back\t\t\t\t\t\t\t: b");
				System.out.println("\t | Quit Agent\t\t\t\t\t\t: q");

				line = input(scan, "Choose Menu Option: ");
				if (line.toLowerCase().equals("q")) {
					continue;
				}

				switch (line.toLowerCase()) {
					case "1":
						System.out.println();
						revisionPostulateTests();
						break;
					case "2":
						System.out.println();
						contractionPostulateTests();
						break;
					case "3":
						System.out.println();
						SentenceToCNFTest();
						break;
					case "b":
						preProgrammedExamples = false;
						manipulateBB = false;
						userInput = false;
						System.out.println();
						break;
					default:
						System.out.println("\t | " + line + " Is not an option");
						break;
				}
				continue;
			}

		}
		System.out.println();
		System.out.println("Agent have Exited");
		System.out.println();
    }

	public static String input(Scanner scan, String text) {
		System.out.println();
		System.out.print("\t | " + text);
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
				
    	System.out.println(String.format("\t | Sentence before CNF conversion: %s", distriTest.toString()));
    	distriTest = distriTest.convertToCNF();
    	System.out.println(String.format("\t | Sentence after CNF conversion: %s", distriTest.toString()));
    	System.out.println(String.format("\t | Sentence isInCNF()-evaluation: %s", distriTest.isInCNF()));
    	System.out.println(String.format("\t | distriTest predicate list: %s", distriTest.getPredicates()));
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
		System.out.println("\t | Testing the Success postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("\t | Elements in the belief base: %s", bBase));
		bBase.contract(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("\t | Elements in the belief base after contraction with q: %s", bBase));
		System.out.println("\t | The implication was no longer valid, and thus is it no longer in the resulting belief set");
		System.out.println();
	}

	private static void contractionInclusion() {
		System.out.println("\t | Testing the Inclusion postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("\t | Elements in the belief base: %s", bBase));
		bBase.contract(new AtomicSentence("r"));
		System.out.println(String.format("\t | Elements in the belief base after contraction with r: %s", bBase));
		System.out.println("\t | We can see that the belief set after contraction with r is a subset of the original belief set");
		System.out.println();
	}

	private static void contractionVacuity() {
		System.out.println("\t | Testing the Vacuity postulate for contraction");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("\t | Elements in the belief base: %s", bBase));
		bBase.contract(new AtomicSentence("s"));
		System.out.println(String.format("\t | Elements in the belief base after contraction with s: %s", bBase));
		System.out.println("\t | Since s wasn't in the belief base, the contraction does not change the belief set");
		System.out.println();
	}

	private static void contractionExtensionality() {
		System.out.println("\t | Testing the Extensionality postulate for contraction");
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
		System.out.println(String.format("\t | Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("\t | Elements in the second belief base: %s", bBase2));
		bBase1.contract(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
		bBase2.contract(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("\t | Elements in the first belief base after revision with (!p | q): %s", bBase1));
		System.out.println(String.format("\t | Elements in the second belief base after revision with (p => q): %s", bBase2));
		System.out.println("\t | We can see that the contractions behave in the same way, and the extensionality postulate is fulfilled.");
		System.out.println();
	}

	private static void revisionSuccess() {
		System.out.println("\t | Testing the Success postulate for revision");
		BeliefBase bBase = new BeliefBase();
		System.out.println(String.format("\t | Elements in the belief base: %s", bBase));
    	bBase.revise(new AtomicSentence("p"));
		System.out.println(String.format("\t | Elements in the belief base after revision with p: %s", bBase));
		System.out.println(String.format("\t | Does the belief base contain p?: %s", bBase.contains(new AtomicSentence("p"))));
		System.out.println();
	}

	private static void revisionInclusion() {
		System.out.println("\t | Testing the Inclusion postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand(new AtomicSentence("p"));
    	bBase1.expand(new AtomicSentence("q"));
    	bBase1.expand(new AtomicSentence("r"));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand(new AtomicSentence("p"));
    	bBase2.expand(new AtomicSentence("q"));
    	bBase2.expand(new AtomicSentence("r"));
		System.out.println(String.format("\t | Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("\t | Elements in the second belief base: %s", bBase2));
		bBase1.revise(new AtomicSentence("s"));
		System.out.println(String.format("\t | Elements in the first belief base after revision with s: %s", bBase1));
		bBase2.expand(new AtomicSentence("s"));
		System.out.println(String.format("\t | Elements in the second belief base after expanding with s: %s", bBase2));
		System.out.println("\t | We can see they contain the same elements, therefore the first belief base is a subset of the second.");
		System.out.println();
	}

	private static void revisionVacuity() {
		System.out.println("\t | Testing the Vacuity postulate for revision");
		BeliefBase bBase = new BeliefBase();
    	bBase.expand(new AtomicSentence("p"));
    	bBase.expand(new AtomicSentence("q"));
    	bBase.expand(new AtomicSentence("r"));
		System.out.println(String.format("\t | Elements in the belief base: %s", bBase));
		bBase.revise(new AtomicSentence("s"));
		System.out.println(String.format("\t | Elements in the belief base after revision with s: %s", bBase));
		System.out.println("\t | Since !s wasn't in the belief base, the revision behaves as expand");
		System.out.println();
	}

	private static void revisionExtensionality() {
		System.out.println("\t | Testing the Extensionality postulate for revision");
		BeliefBase bBase1 = new BeliefBase();
    	bBase1.expand(new AtomicSentence("p"));
    	bBase1.expand(new AtomicSentence("q"));
    	BeliefBase bBase2 = new BeliefBase();
    	bBase2.expand(new AtomicSentence("p"));
    	bBase2.expand(new AtomicSentence("q"));
		System.out.println(String.format("\t | Elements in the first belief base: %s", bBase1));
		System.out.println(String.format("\t | Elements in the second belief base: %s", bBase2));
		bBase1.revise(new OrSentence(new NotSentence(new AtomicSentence("p")), new AtomicSentence("q")));
		System.out.println(String.format("\t | Elements in the first belief base after revision with (!p | q): %s", bBase1));
		bBase2.revise(new ImplicationSentence(new AtomicSentence("p"), new AtomicSentence("q")));
		System.out.println(String.format("\t | Elements in the second belief base after revision with (p => q): %s", bBase2));
		System.out.println("\t | We can see that the revisions behave in the same way, and the extensionality postulate is fulfilled.");
		System.out.println();
	}
}

