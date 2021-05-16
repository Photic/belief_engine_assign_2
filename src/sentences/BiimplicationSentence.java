package sentences;

import java.util.ArrayList;
import java.util.List;

import model.Constants;

public class BiimplicationSentence extends BinarySentence {

	public BiimplicationSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public BiimplicationSentence() {
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

		public BiimplicationSentence build() {
			BiimplicationSentence sent = new BiimplicationSentence();
			sent.setAlpha(this.alpha);
			sent.setBeta(this.beta);

			return sent;
		}
	}

	@Override
	public boolean getValue() {
		boolean alphaValue = this.getAlpha().getValue();
		boolean betaValue = this.getBeta().getValue();
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
		return new AndSentence(new ImplicationSentence(this.getAlpha().copy(), this.getBeta().copy()),
				new ImplicationSentence(this.getBeta().copy(), this.getAlpha().copy())).reduce(times - 1);
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
		Sentence alphaCopy = this.getAlpha().copy();
		Sentence betaCopy = this.getBeta().copy();
		return new BiimplicationSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		return false;
	}
	public boolean isNotValid(List<Sentence> predicates) {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic) {
			if ((!predicates.contains(alpha) || beta.getValue()) && (predicates.contains(alpha) || !beta.getValue())) {
				return true;
			}
		} else if (betaIsAtomic) {
			if ((!predicates.contains(beta) || alpha.getValue()) && (predicates.contains(beta) || !alpha.getValue())) {
				return true;
			}
		} else if (alphaIsAtomic && betaIsAtomic) {
			if ((!predicates.contains(alpha) || predicates.contains(beta)) && (predicates.contains(alpha) || !predicates.contains(beta))) {
				return true;
			}
		}
		
		return (!predicates.contains(alpha) || predicates.contains(beta)) && (!predicates.contains(beta) || predicates.contains(alpha));
	}
	@Override
	public String toString() {
		boolean alphaIsAtomic = this.getAlpha() instanceof AtomicSentence;
		boolean betaIsAtomic = this.getBeta() instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.IFF, this.getBeta().toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", this.getAlpha().toString(), Constants.IFF, this.getBeta().toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s", this.getAlpha().toString(), Constants.IFF, this.getBeta().toString());
		} else {
			return String.format("(%s) %s (%s)", this.getAlpha().toString(), Constants.IFF, this.getBeta().toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof BiimplicationSentence) {
			return this.getAlpha().equals(((BiimplicationSentence) other).getAlpha())
					&& this.getBeta().equals(((BiimplicationSentence) other).getBeta());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 131;
	    int result = 1;
	    result = prime * result + this.getAlpha().hashCode();
	    result = prime * result + this.getBeta().hashCode();
		return result;
	}

}
