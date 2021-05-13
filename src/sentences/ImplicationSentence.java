package sentences;

import model.Constants;

public class ImplicationSentence extends Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public ImplicationSentence(Sentence alpha, Sentence beta) {
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
		return !alpha.getValue() || beta.getValue();
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
		return new OrSentence(new NotSentence(alpha), beta).reduce(times - 1);
	}

	@Override
	public Sentence copy() {
		Sentence alphaCopy = alpha.copy();
		Sentence betaCopy = beta.copy();
		return new ImplicationSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		return false;
	}

	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", alpha.toString(), Constants.IF, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", alpha.toString(), Constants.IF, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s )%s %s", alpha.toString(), Constants.IF, beta.toString());
		} else {
			return String.format("(%s) %s (%s)", alpha.toString(), Constants.IF, beta.toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ImplicationSentence) {
			return alpha.equals(((ImplicationSentence) other).getAlpha())
					&& beta.equals(((ImplicationSentence) other).getBeta());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 107;
	    int result = 1;
	    result = prime * result + alpha.hashCode();
	    result = prime * result + beta.hashCode();
		return result;
	}
}
