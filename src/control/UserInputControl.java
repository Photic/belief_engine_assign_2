package control;

public class UserInputControl {

    public void splitIntoBeliefBaseSentences() {
        String input = "( alpha ,  alpha | !beta ,  !alpha & (beta | (!!gamma)) ,  alpha <=> (beta | gamma) )";

        System.out.println(input);
    }

}


// ( alpha ,  alpha | !beta ,  !alpha & (beta | (!!gamma)) ,  alpha <=> (beta | gamma) )

/*
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
*/
