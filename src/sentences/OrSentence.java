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
		return reduce(Integer.MAX_VALUE);
	}
	@Override
	public Sentence reduce(int times) {
		if (times <= 0) {
			return this;
		}
		if (beta instanceof AndSentence) {
			return new AndSentence(
					new OrSentence(alpha, ((AndSentence) beta).getAlpha()),
					new OrSentence(alpha, ((AndSentence) beta).getBeta())
					).reduce(times - 1);
		} else if (alpha instanceof AndSentence) {
			return new AndSentence(
					new OrSentence(((AndSentence) alpha).getAlpha(), beta),
					new OrSentence(((AndSentence) alpha).getBeta(), beta)
					).reduce(times - 1);
		} else {
			return new OrSentence(alpha.reduce(times - 1),beta.reduce(times - 1));
		}
	}
	
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s %s %s",alpha.toString(), Constants.OR, beta.toString());
		} else if (alphaIsAtomic && (beta instanceof OrSentence)) {
			return String.format("%s %s %s",alpha.toString(), Constants.OR, beta.toString());
		} else if (betaIsAtomic && (alpha instanceof OrSentence)) {
			return String.format("%s %s %s",alpha.toString(), Constants.OR, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s %s (%s)",alpha.toString(), Constants.OR, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s) %s %s",alpha.toString(), Constants.OR, beta.toString());
		} else {
			return String.format("(%s) %s (%s)",alpha.toString(), Constants.OR, beta.toString());
		}
	}
}
