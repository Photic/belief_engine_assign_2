package sentences;

public abstract class Sentence implements ISentence {
	private Sentence alpha;
	private Sentence beta;
	
	public void setAlpha(Sentence alpha) {
		this.alpha = alpha;
	}

	public void setBeta(Sentence beta) {
		this.beta = beta;
	}

	public Sentence getAlpha() {
		return alpha;
	}

	public Sentence getBeta() {
		return beta;
	}
	
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
