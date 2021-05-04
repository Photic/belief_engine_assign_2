package sentences;

import model.Constants;

public class OrSentence implements Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public OrSentence(Sentence alpha, Sentence beta) {
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
		return alpha.getValue() || beta.getValue();
	}
	@Override
	public Sentence reduce() {
		if (beta instanceof AndSentence) {
			return new AndSentence(
					new OrSentence(alpha, ((AndSentence) beta).getAlpha()).reduce(),
					new OrSentence(alpha, ((AndSentence) beta).getBeta()).reduce()
					);
		} else if (alpha instanceof AndSentence) {
			return new AndSentence(
					new OrSentence(beta, ((AndSentence) alpha).getAlpha()).reduce(),
					new OrSentence(beta, ((AndSentence) alpha).getBeta()).reduce()
					);
		} else {
			return new OrSentence(alpha.reduce(),beta.reduce());
		}
	}
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s%s%s",alpha.toString(), Constants.OR, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s%s(%s)",alpha.toString(), Constants.OR, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s)%s%s",alpha.toString(), Constants.OR, beta.toString());
		} else {
			return String.format("(%s)%s(%s)",alpha.toString(), Constants.OR, beta.toString());
		}
	}
}
