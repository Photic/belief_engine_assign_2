package sentences;

import model.Constants;

public class ImplicationSentence implements Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public ImplicationSentence(Sentence alpha, Sentence beta) {
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
		return !alpha.getValue() || beta.getValue();
	}
	@Override
	public Sentence reduce() {
		return new OrSentence(
				new NotSentence(alpha).reduce(), beta.reduce()
				);
	}
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s%s%s",alpha.toString(), Constants.IF, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s%s(%s)",alpha.toString(), Constants.IF, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s)%s%s",alpha.toString(), Constants.IF, beta.toString());
		} else {
			return String.format("(%s)%s(%s)",alpha.toString(), Constants.IF, beta.toString());
		}
	}

}
