package sentences;
import model.Constants;

public class AndSentence extends Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public AndSentence(Sentence alpha, Sentence beta) {
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
		return alpha.getValue() && beta.getValue();
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
		return new AndSentence(alpha.reduce(times - 1), beta.reduce(times - 1));
	}

	@Override
	public Sentence copy() {
		Sentence alphaCopy = alpha.copy();
		Sentence betaCopy = beta.copy();
		return new AndSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		boolean alphaIsAnd = alpha instanceof AndSentence;
		boolean betaIsAnd = beta instanceof AndSentence;
		boolean alphaIsOr = alpha instanceof OrSentence;
		boolean betaIsOr = beta instanceof OrSentence;

		if (alphaIsAnd && betaIsOr) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsOr && betaIsAnd) {
			return alpha.isInCNF() && beta.isInCNF();
		} else if (alphaIsOr && betaIsOr) {
			return alpha.isInCNF() && beta.isInCNF();
		}
		return false;
	}
	
	@Override
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", alpha.toString(), Constants.AND, beta.toString());
		} else if (alphaIsAtomic && beta instanceof AndSentence) {
			return String.format("%s %s %s", alpha.toString(), Constants.AND, beta.toString());
		} else if (betaIsAtomic && alpha instanceof AndSentence) {
			return String.format("%s %s %s", alpha.toString(), Constants.AND, beta.toString());
		} else if (!(alpha instanceof AndSentence) && beta instanceof AndSentence) {
			return String.format("(%s) %s %s", alpha.toString(), Constants.AND, beta.toString());
		} else if (!(beta instanceof AndSentence) && alpha instanceof AndSentence) {
			return String.format("%s %s (%s)", alpha.toString(), Constants.AND, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", alpha.toString(), Constants.AND, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s", alpha.toString(), Constants.AND, beta.toString());
		} else {
			return String.format("(%s) %s (%s)", alpha.toString(), Constants.AND, beta.toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AndSentence) {
			return alpha.equals(((AndSentence) other).getAlpha()) && beta.equals(((AndSentence) other).getBeta());
		}
		return false;
	}

	public int hashCode() {
		final int prime = 73;
	    int result = 1;
	    result = prime * result + alpha.hashCode();
	    result = prime * result + beta.hashCode();
		return result;
	}
}
