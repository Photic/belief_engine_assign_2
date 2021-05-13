package sentences;

import model.Constants;

public class OrSentence extends Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public OrSentence(Sentence alpha, Sentence beta) {
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
		return alpha.getValue() || beta.getValue();
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
		if (beta instanceof AndSentence) {
			return new AndSentence(new OrSentence(alpha.copy(), ((AndSentence) beta.copy()).getAlpha()),
					new OrSentence(alpha.copy(), ((AndSentence) beta.copy()).getBeta())).reduce(times - 1);
		} else if (alpha instanceof AndSentence) {
			return new AndSentence(new OrSentence(((AndSentence) alpha.copy()).getAlpha(), beta.copy()),
					new OrSentence(((AndSentence) alpha.copy()).getBeta(), beta.copy())).reduce(times - 1);
		} else {
			return new OrSentence(alpha.reduce(times - 1), beta.reduce(times - 1));
		}
	}
	
	@Override
	public Sentence copy() {
		Sentence alphaCopy = alpha.copy();
		Sentence betaCopy = beta.copy();
		return new OrSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		boolean alphaIsOr = alpha instanceof OrSentence;
		boolean betaIsOr = beta instanceof OrSentence;
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		boolean alphaIsNot = alpha instanceof NotSentence;
		boolean betaIsNot = beta instanceof NotSentence;
		
		if (alphaIsAtomic && betaIsOr) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsOr && betaIsAtomic) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsAtomic && betaIsAtomic) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsNot && betaIsOr) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsOr && betaIsNot) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsAtomic && betaIsNot) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsNot && betaIsAtomic) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsNot && betaIsNot) {
			return alpha.isInCNF() && beta.isInCNF();
		}
		return false;
	}
	
	@Override
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", alpha.toString(), Constants.OR, beta.toString());
		} else if (alphaIsAtomic && (beta instanceof OrSentence)) {
			return String.format("%s %s %s", alpha.toString(), Constants.OR, beta.toString());
		} else if (betaIsAtomic && (alpha instanceof OrSentence)) {
			return String.format("%s %s %s", alpha.toString(), Constants.OR, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", alpha.toString(), Constants.OR, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s", alpha.toString(), Constants.OR, beta.toString());
		} else {
			return String.format("(%s) %s (%s)", alpha.toString(), Constants.OR, beta.toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OrSentence) {
			return alpha.equals(((OrSentence) other).getAlpha()) && beta.equals(((OrSentence) other).getBeta());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 61;
	    int result = 1;
	    result = prime * result + alpha.hashCode();
	    result = prime * result + beta.hashCode();
		return result;
	}
}
