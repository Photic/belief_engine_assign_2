package sentences;

import model.Constants;

public class OrSentence extends Sentence {

	public OrSentence(Sentence alpha, Sentence beta) {
		this.setAlpha(alpha.copy());
		this.setBeta(beta.copy());
	}

	public OrSentence() {}

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

		public OrSentence build() {
			OrSentence sent = new OrSentence();
			sent.setAlpha(this.alpha);
			sent.setBeta(this.beta);

			return sent;
		}
	}

	@Override
	public boolean getValue() {
		return this.getAlpha().getValue() || this.getBeta().getValue();
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
		if (this.getBeta() instanceof AndSentence) {
			return new AndSentence(new OrSentence(this.getAlpha().copy(), ((AndSentence) this.getBeta().copy()).getAlpha()),
					new OrSentence(this.getAlpha().copy(), ((AndSentence) this.getBeta().copy()).getBeta())).reduce(times - 1);
		} else if (this.getAlpha() instanceof AndSentence) {
			return new AndSentence(new OrSentence(((AndSentence) this.getAlpha().copy()).getAlpha(), this.getBeta().copy()),
					new OrSentence(((AndSentence) this.getAlpha().copy()).getBeta(), this.getBeta().copy())).reduce(times - 1);
		} else {
			return new OrSentence(this.getAlpha().reduce(times - 1), this.getBeta().reduce(times - 1));
		}
	}
	
	@Override
	public Sentence copy() {
		Sentence alphaCopy = this.getAlpha().copy();
		Sentence betaCopy = this.getBeta().copy();
		return new OrSentence(alphaCopy, betaCopy);
	}

	@Override
	public boolean isInCNF() {
		boolean alphaIsOr = this.getAlpha() instanceof OrSentence;
		boolean betaIsOr = this.getBeta() instanceof OrSentence;
		boolean alphaIsAtomic = this.getAlpha() instanceof AtomicSentence;
		boolean betaIsAtomic = this.getBeta() instanceof AtomicSentence;
		boolean alphaIsNot = this.getAlpha() instanceof NotSentence;
		boolean betaIsNot = this.getBeta() instanceof NotSentence;
		
		if (alphaIsAtomic && betaIsOr) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsOr && betaIsAtomic) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsAtomic && betaIsAtomic) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsNot && betaIsOr) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsOr && betaIsNot) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsAtomic && betaIsNot) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsNot && betaIsAtomic) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		} else if (alphaIsNot && betaIsNot) {
			return this.getAlpha().isInCNF() && this.getBeta().isInCNF();
		}
		return false;
	}
	
	@Override
	public String toString() {
		boolean alphaIsAtomic = this.getAlpha() instanceof AtomicSentence;
		boolean betaIsAtomic = this.getBeta() instanceof AtomicSentence;

		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		} else if (alphaIsAtomic && (this.getBeta() instanceof OrSentence)) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		} else if (betaIsAtomic && (this.getAlpha() instanceof OrSentence)) {
			return String.format("%s %s %s", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		} else {
			return String.format("(%s) %s (%s)", this.getAlpha().toString(), Constants.OR, this.getBeta().toString());
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OrSentence) {
			return this.getAlpha().equals(((OrSentence) other).getAlpha()) && this.getBeta().equals(((OrSentence) other).getBeta());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 61;
	    int result = 1;
	    result = prime * result + this.getAlpha().hashCode();
	    result = prime * result + this.getBeta().hashCode();
		return result;
	}
}
