package sentences;

import model.Constants;

public class BiimplicationSentence extends Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public BiimplicationSentence(Sentence alpha, Sentence beta) {
		this.alpha = alpha.copy();
		this.beta = beta.copy();
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
				new ImplicationSentence(alpha.copy(), beta.copy()),
				new ImplicationSentence(beta.copy(), alpha.copy())
				).reduce(times - 1);
	}
	@Override
	public Sentence copy() {
		Sentence alphaCopy = alpha.copy();
		Sentence betaCopy = beta.copy();
		return new BiimplicationSentence(alphaCopy, betaCopy);
	}
	@Override
	public boolean isInCNF() {
		return false;
	}
	@Override
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
	@Override
	public boolean equals(Object other) {
		if (other instanceof BiimplicationSentence) {
			return alpha.equals(((BiimplicationSentence) other).getAlpha()) && beta.equals(((BiimplicationSentence) other).getBeta());
		}
		return false;
	}
	public int hashCode() {
		final int prime = 131;
	    int result = 1;
	    result = prime * result + alpha.hashCode();
	    result = prime * result + beta.hashCode();
		return result;
	}

}
