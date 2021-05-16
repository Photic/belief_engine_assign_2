package sentences;
import java.util.ArrayList;
import java.util.List;

import model.Constants;

public class AndSentence extends BinarySentence {
	
	public AndSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public AndSentence() {
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

		public AndSentence build() {
			AndSentence sent = new AndSentence();
			sent.setAlpha(this.alpha);
			sent.setBeta(this.beta);

			return sent;
		}
	}

	@Override
	public boolean getValue() {
		return this.getAlpha().getValue() && this.getBeta().getValue();
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
		return new AndSentence(this.getAlpha().reduce(times - 1), this.getBeta().reduce(times - 1));
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
	public boolean isNotValid(List<Sentence> predicates) {
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
		
		return !alpha.isNotValid(predicates) || !beta.isNotValid(predicates);
	}
	@Override
	public Sentence copy() {
		Sentence alphaCopy = this.getAlpha().copy();
		Sentence betaCopy = this.getBeta().copy();
		return new AndSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		boolean alphaIsIfThen = this.getAlpha() instanceof ImplicationSentence;
		boolean betaIsIfThen = this.getBeta() instanceof ImplicationSentence;
		boolean alphaIsIFF = this.getAlpha() instanceof BiimplicationSentence;
		boolean betaIsIFF = this.getBeta() instanceof BiimplicationSentence;
		
		if (alphaIsIfThen || betaIsIfThen || alphaIsIFF || betaIsIFF) {
			return false;
		} else if (alpha instanceof AndSentence && beta instanceof AndSentence) {
			return false;
		}
		return this.getAlpha().isInCNF() && this.getBeta().isInCNF();

	}
	
	@Override
	public String toString() {
		boolean alphaIsAtomic = this.getAlpha() instanceof AtomicSentence;
		boolean betaIsAtomic = this.getBeta() instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (alphaIsAtomic && this.getBeta() instanceof AndSentence) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (betaIsAtomic && this.getAlpha() instanceof AndSentence) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (!(this.getAlpha() instanceof AndSentence) && this.getBeta() instanceof AndSentence) {
			return String.format("(%s) %s %s", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (!(this.getBeta() instanceof AndSentence) && this.getAlpha() instanceof AndSentence) {
			return String.format("%s %s (%s)", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		} else {
			return String.format("(%s) %s (%s)", this.getAlpha().toString(), Constants.AND, this.getBeta().toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AndSentence) {
			return this.getAlpha().equals(((AndSentence) other).getAlpha()) && this.getBeta().equals(((AndSentence) other).getBeta());
		}
		return false;
	}

	public int hashCode() {
		final int prime = 73;
	    int result = 1;
	    result = prime * result + this.getAlpha().hashCode();
	    result = prime * result + this.getBeta().hashCode();
		return result;
	}
}
