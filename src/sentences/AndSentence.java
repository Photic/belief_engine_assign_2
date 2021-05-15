package sentences;
import java.util.ArrayList;
import java.util.List;

import model.Constants;

public class AndSentence extends Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public AndSentence(Sentence alpha, Sentence beta) {
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
	public boolean contains(Sentence sentence) {
		if (alpha.equals(sentence) || beta.equals(sentence)) {
			return true;
		}
		return alpha.contains(sentence) || beta.contains(sentence);
	}
	protected List<Sentence> getPredicates(ArrayList<Sentence> predicates) {
		predicates.addAll(alpha.getPredicates(predicates));
		predicates.addAll(beta.getPredicates(predicates));
		return predicates;
	}
	public boolean causesFalsum(List<Sentence> predicates) {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic) {
			if (predicates.contains(alpha) || !beta.getValue()) {
				return true;
			}
		} else if (betaIsAtomic) {
			if (predicates.contains(beta) || !alpha.getValue()) {
				return true;
			}
		} else if (alphaIsAtomic && betaIsAtomic) {
			if (predicates.contains(alpha) || predicates.contains(beta)) {
				return true;
			}
		}
		
		return !alpha.causesFalsum(predicates) || !beta.causesFalsum(predicates);
	}
	@Override
	public Sentence copy() {
		Sentence alphaCopy = alpha.copy();
		Sentence betaCopy = beta.copy();
		return new AndSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		boolean alphaIsIfThen = alpha instanceof ImplicationSentence;
		boolean betaIsIfThen = beta instanceof ImplicationSentence;
		boolean alphaIsIFF = alpha instanceof BiimplicationSentence;
		boolean betaIsIFF = beta instanceof BiimplicationSentence;
		
		if (alphaIsIfThen || betaIsIfThen || alphaIsIFF || betaIsIFF) {
			return false;
		} else if (alpha instanceof AndSentence && beta instanceof AndSentence) {
			return false;
		}
		return alpha.isInCNF() && beta.isInCNF();

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
