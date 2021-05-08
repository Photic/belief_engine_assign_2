package sentences;

public abstract class Sentence implements SentenceInterface {
	
	@Override
	public Sentence convertToCNF() {
		int allowedCycles = 25;
		Sentence cnfSentence = this;
		
		while (!cnfSentence.isInCNF()) {
			cnfSentence = cnfSentence.reduce();
			allowedCycles--;
			if (allowedCycles <= 0) {
				return this;
			}
		}
		return cnfSentence;
	}
}
