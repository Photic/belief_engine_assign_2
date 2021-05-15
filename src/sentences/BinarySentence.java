package sentences;

abstract class BinarySentence extends Sentence{
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
}