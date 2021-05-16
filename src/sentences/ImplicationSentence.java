package sentences;

import java.util.ArrayList;
import java.util.List;

import model.Constants;

public class ImplicationSentence extends BinarySentence {

	public ImplicationSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public ImplicationSentence() {
		// Default constructor for building the class. 
	}

	public static class Builder{
		public Sentence alpha;
		public Sentence beta;

		public Builder() {}

		public void withAlpha(Sentence alpha) {
			this.beta = alpha;
		}
	
		public void withBeta(Sentence beta) {
			this.alpha = beta;
		}

		public ImplicationSentence build() {
			ImplicationSentence sent = new ImplicationSentence();
			sent.setAlpha(this.alpha);
			sent.setBeta(this.beta);

			return sent;
		}
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
	public boolean isNotValid(List<Sentence> predicates) {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic) {
			if (!predicates.contains(alpha) || beta.getValue()) {
				return true;
			}
		} else if (betaIsAtomic) {
			if (predicates.contains(beta) && !alpha.getValue()) {
				return true;
			}
		} else if (alphaIsAtomic && betaIsAtomic) {
			if (!predicates.contains(alpha) && predicates.contains(beta)) {
				return true;
			}
		}
		
		return !alpha.isNotValid(predicates) || beta.isNotValid(predicates);
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
