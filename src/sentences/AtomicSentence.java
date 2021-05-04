package sentences;

import model.Literal;

public class AtomicSentence implements Sentence {
	private Literal literal;
	private boolean value;
	private boolean definedByLiteral;
	
	public AtomicSentence(Literal literal) {
		this.literal = literal;
		definedByLiteral = true;
	}
	public AtomicSentence(boolean value) {
		this.value = value;
	}
	public Literal getLiteral() {
		return definedByLiteral ? literal : null;
	}
	@Override
	public boolean getValue() {
		return definedByLiteral ? literal.getValue() : value;
	}
	@Override
	public Sentence reduce() {
		return this;
	}
	@Override
	public Sentence reduceOnce() {
		return this;
	}
	public Sentence switchValue() {
		if (definedByLiteral) {
			literal.setValue(!literal.getValue());
		} else {
			value = !value;
		}
		return this;
	}
	public String toString() {
		if (definedByLiteral) {
			return literal.toString();
		} else {
			return "" + value;
		}
	}
}
