package sentences;
import model.Constants;

public class AndSentence implements Sentence {
	private Sentence alpha;
	private Sentence beta;
	
	public AndSentence(Sentence alpha, Sentence beta) {
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
		return alpha.getValue() && beta.getValue();
	}
	@Override
	public Sentence reduce() {
		return new AndSentence(alpha.reduce(), beta.reduce());
	}
	public String toString() {
		boolean alphaIsAtomic = alpha instanceof AtomicSentence;
		boolean betaIsAtomic = beta instanceof AtomicSentence;
		
		if (alphaIsAtomic && betaIsAtomic) {
			return String.format("%s%s%s",alpha.toString(), Constants.AND, beta.toString());
		} else if (alphaIsAtomic) {
			return String.format("%s%s(%s)",alpha.toString(), Constants.AND, beta.toString());
		} else if (betaIsAtomic) {
			return String.format("(%s)%s%s",alpha.toString(), Constants.AND, beta.toString());
		} else {
			return String.format("(%s)%s(%s)",alpha.toString(), Constants.AND, beta.toString());
		}
	}

}
