package sentences;
import model.Constants;

public class NotSentence implements Sentence {
	private Sentence sentence;
	
	public NotSentence(Sentence sentence) {
		this.sentence = sentence;
	}
	
	@Override
	public boolean getValue() {
		return !sentence.getValue();
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
		if (sentence instanceof AndSentence) { // De Morgan: AND
			return new OrSentence(
					(new NotSentence(((AndSentence) sentence).getAlpha())),
					(new NotSentence(((AndSentence) sentence).getBeta()))
					).reduce(times - 1);
		} else if (sentence instanceof OrSentence) { // De Morgan: OR
			return new AndSentence(
					(new NotSentence(((OrSentence) sentence).getAlpha())),
					(new NotSentence(((OrSentence) sentence).getBeta()))
					).reduce(times - 1);
			
		} else if (sentence instanceof NotSentence) { // Double-negation elimination
			return ((NotSentence) sentence).getSentence().reduce(times - 1);
		} else if (sentence instanceof AtomicSentence) {
			return ((AtomicSentence) sentence).switchValue();
		} else {
			return sentence.reduce(times - 1);			
		}
	}
	public Sentence getSentence() {
		return sentence;
	}
	public String toString() {
		if (sentence instanceof AtomicSentence) {
			return String.format("%s%s", Constants.NOT, sentence.toString());
		} else {
			return String.format("%s(%s)", Constants.NOT, sentence.toString());
		}
	}

}
