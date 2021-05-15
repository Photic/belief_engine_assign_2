package sentences;

import model.Constants;

public class BiimplicationSentence extends Sentence {

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
