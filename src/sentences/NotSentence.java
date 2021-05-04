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
		if (sentence instanceof AndSentence) { // De Morgan: AND
			return new OrSentence(
					(new NotSentence(((AndSentence) sentence).getAlpha())),
					(new NotSentence(((AndSentence) sentence).getBeta()))
					).reduce();
		} else if (sentence instanceof OrSentence) { // De Morgan: OR
			return new AndSentence(
					(new NotSentence(((OrSentence) sentence).getAlpha())),
					(new NotSentence(((OrSentence) sentence).getBeta()))
					).reduce();
			
		} else if (sentence instanceof NotSentence) { // Double-negation elimination
			return ((NotSentence) sentence).getSentence().reduce();
		} else if (sentence instanceof AtomicSentence) {
			return ((AtomicSentence) sentence).switchValue();
		} else {
			return sentence.reduce();			
		}
	}
	@Override
	public Sentence reduceOnce() {
		if (sentence instanceof AndSentence) { // De Morgan: AND
			return new OrSentence(
					(new NotSentence(((AndSentence) sentence).getAlpha())),
					(new NotSentence(((AndSentence) sentence).getBeta()))
					);
		} else if (sentence instanceof OrSentence) { // De Morgan: OR
			return new AndSentence(
					(new NotSentence(((OrSentence) sentence).getAlpha())),
					(new NotSentence(((OrSentence) sentence).getBeta()))
					).reduce();
			
		} else if (sentence instanceof NotSentence) { // Double-negation elimination
			return ((NotSentence) sentence).getSentence();
		} else if (sentence instanceof AtomicSentence) {
			return ((AtomicSentence) sentence).switchValue();
		} else {
			return sentence;			
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
