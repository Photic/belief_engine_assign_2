import java.util.Scanner;

import model.Literal;
import sentences.*;

public class RunMe {
    public static void main(String args[]) {
    	SentenceToCNFTest();
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
    	Literal alpha = new Literal("alpha", true);
    	Literal beta = new Literal("beta", true);
    	Literal gamma = new Literal("gamma", true);
    	
    	Sentence sentence = new BiimplicationSentence(
    			new AtomicSentence(alpha),
    			new OrSentence(
    					new AtomicSentence(beta),
    					new AtomicSentence(gamma)
    					)
    			);
    	System.out.println(String.format("Sentence before CNF conversion: %s", sentence.toString()));
    	sentence = sentence.reduce();
    	System.out.println(String.format("Sentence after CNF conversion: %s", sentence.toString()));
    }
}

