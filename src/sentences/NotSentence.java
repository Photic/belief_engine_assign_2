package sentences;
import java.util.ArrayList;
import java.util.List;

import model.Constants;

public class NotSentence extends Sentence {
	private Sentence sentence;
	
	public NotSentence(Sentence sentence) {
		this.sentence = sentence.copy();
	}
	
	@Override
	public boolean getValue() {
		return !sentence.getValue();
	}

	@Override
	public Sentence reduce() {
		return reduce(Integer.MAX_VALUE);
	}
	protected List<Sentence> getPredicates(ArrayList<Sentence> predicates) {
		if (sentence instanceof AtomicSentence) {
			predicates.add(this);
		} else {
			predicates.addAll(sentence.getPredicates(predicates));			
		}
		return predicates;
	}
	public boolean contains(Sentence sentence) {
		if (this.sentence.equals(sentence)) {
			return true;
		}
		return this.sentence.contains(sentence);
	}
	@Override
	public Sentence reduce(int times) {
		if (times <= 0) {
			return this;
		}
		if (sentence instanceof AndSentence) { // De Morgan: AND
			return new OrSentence((new NotSentence(((AndSentence) sentence).getAlpha())),
					(new NotSentence(((AndSentence) sentence).getBeta()))).reduce(times - 1);
		} else if (sentence instanceof OrSentence) { // De Morgan: OR
			return new AndSentence((new NotSentence(((OrSentence) sentence).getAlpha())),
					(new NotSentence(((OrSentence) sentence).getBeta()))).reduce(times - 1);

		} else if (sentence instanceof NotSentence) { // Double-negation elimination
			Sentence sentenceOfSentence = ((NotSentence) sentence).getSentence();
			return sentenceOfSentence.reduce(times - 1);
		} else {
			return new NotSentence(sentence.reduce(times - 1));
		}
	}
	
	@Override
	public Sentence copy() {
		Sentence copySentence = sentence.copy();
		return new NotSentence(copySentence);
	}
	public boolean causesFalsum(List<Sentence> predicates) {
		if (sentence instanceof AtomicSentence) {
			if (predicates.contains(sentence)) {
				return true;
			}
		}
		return sentence.causesFalsum(predicates);
	}

	@Override
	public boolean isInCNF() {
		if (sentence instanceof AtomicSentence) {
			return true;
		}
		return false;
	}

	public Sentence getSentence() {
		return sentence;
	}

	public String toString() {
		return String.format("%s(%s)",Constants.NOT, sentence.toString());
	}

	public boolean equals(Object other) {
		if (other instanceof NotSentence) {
			return sentence.equals(((NotSentence) other).getSentence());
		}
		return false;
	}
	
	public int hashCode() {
		final int prime = 43;
	    int result = 1;
	    result = prime * result + sentence.hashCode();
		return result;
	}

}
