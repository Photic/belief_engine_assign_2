package sentences;

import model.Literal;

public class AtomicSentence extends Sentence {
	private Literal literal;
	private boolean value;
	private boolean definedByLiteral;
	
	public AtomicSentence(Literal literal) {
		this.literal = literal.copy();
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
	public Sentence reduce(int times) {
		return this;
	}

	@Override
	public Sentence copy() {
		return new AtomicSentence(literal.copy());
	}

	@Override
	public boolean isInCNF() {
		return true;
	}
	public AtomicSentence switchValue() {
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
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AtomicSentence) {
			return literal.equals(((AtomicSentence) other).getLiteral());
		}
		return false;
	}
}
