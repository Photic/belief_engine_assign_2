package sentences;

import model.Constants;

public class ImplicationSentence extends Sentence {

	public ImplicationSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public ImplicationSentence() {}

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
		return !this.getAlpha().getValue() || this.getBeta().getValue();
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
		return new OrSentence(new NotSentence(this.getAlpha()), this.getBeta()).reduce(times - 1);
	}

	@Override
	public Sentence copy() {
		Sentence alphaCopy = this.getAlpha().copy();
		Sentence betaCopy = this.getBeta().copy();
		return new ImplicationSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		return false;
	}

	public String toString() {
		boolean alphaIsAtomic = this.getAlpha() instanceof AtomicSentence;
		boolean betaIsAtomic = this.getBeta() instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.IF, this.getBeta().toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", this.getAlpha().toString(), Constants.IF, this.getBeta().toString());
		} else if (betaIsAtomic) {
			return String.format("(%s )%s %s", this.getAlpha().toString(), Constants.IF, this.getBeta().toString());
		} else {
			return String.format("(%s) %s (%s)", this.getAlpha().toString(), Constants.IF, this.getBeta().toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof ImplicationSentence) {
			return this.getAlpha().equals(((ImplicationSentence) other).getAlpha())
					&& this.getBeta().equals(((ImplicationSentence) other).getBeta());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 107;
	    int result = 1;
	    result = prime * result + this.getAlpha().hashCode();
	    result = prime * result + this.getBeta().hashCode();
		return result;
	}
}
