package sentences;

public abstract class Sentence implements ISentence {
	
	@Override
	public Sentence convertToCNF() throws Exception {
		int allowedCycles = 25;
		Sentence cnfSentence = this;
		
		do {
			cnfSentence = cnfSentence.reduce();
			if (allowedCycles <= 1) {
				throw new Exception("Could not convert to CNF-form. Please check that all sentences are properly defined.");
			}
			allowedCycles--;
		} while (!cnfSentence.isInCNF());
		return cnfSentence;
	}
}
