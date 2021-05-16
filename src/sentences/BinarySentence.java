package sentences;

public abstract class BinarySentence extends Sentence {
	protected Sentence alpha;
	protected Sentence beta;
	
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
