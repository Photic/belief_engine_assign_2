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
		if (sentence instanceof NotSentence) { // Double-negation elimination
			return ((NotSentence) sentence).getSentence().reduce();
		} else if (sentence instanceof AndSentence) { // De Morgan
			return new OrSentence(
					(new NotSentence(((AndSentence) sentence).getAlpha().reduce())).reduce(),
					(new NotSentence(((AndSentence) sentence).getBeta().reduce())).reduce()
					);
		} else if (sentence instanceof OrSentence) { // De Morgan
			return new AndSentence(
					(new NotSentence(((OrSentence) sentence).getAlpha())).reduce(),
					(new NotSentence(((OrSentence) sentence).getBeta())).reduce()
					);
			
		} else if (sentence instanceof AtomicSentence) {
			return ((AtomicSentence) sentence).switchValue();
		} else {
			return sentence.reduce();			
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
