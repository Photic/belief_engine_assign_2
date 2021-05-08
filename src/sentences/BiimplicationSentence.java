package sentences;

import model.Constants;

public class BiimplicationSentence implements Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public BiimplicationSentence(Sentence alpha, Sentence beta) {
		this.alpha = alpha;
		this.beta = beta;
	}
	public Sentence getAlpha() {
		return alpha;
	}
	public Sentence getBeta() {
		return beta;
	}
	@Override
	public boolean getValue() {
		boolean alphaValue = alpha.getValue();
		boolean betaValue = beta.getValue();
		return (!alphaValue || betaValue) && (!betaValue || alphaValue);
	}
	@Override
	public Sentence reduce() {
		return reduce(Integer.MAX_VALUE);
	}
	@Override
	public Sentence reduce(int times) {
		if (times <= 0) {
			return this;
		}
		return new AndSentence(
				new ImplicationSentence(alpha, beta),
				new ImplicationSentence(beta, alpha)
				).reduce(times - 1);
	}
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s",alpha.toString(), Constants.IFF, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)",alpha.toString(), Constants.IFF, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s",alpha.toString(), Constants.IFF, beta.toString());
		} else {
			return String.format("(%s) %s (%s)",alpha.toString(), Constants.IFF, beta.toString());
		}
	}

}
