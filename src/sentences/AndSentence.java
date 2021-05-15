package sentences;

import model.Constants;

public class AndSentence extends BinarySentence {
	
	public AndSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public AndSentence() {}

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
