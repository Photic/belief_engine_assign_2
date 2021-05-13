package model;

import java.util.ArrayList;
import java.util.List;

public class Clause {

	private List<Literal> literals;

	public Clause() {
		literals = new ArrayList<Literal>();
	}

	public Clause(ArrayList<Literal> literals) {
		this.literals = literals;
	}

	public Clause negateClause() {

		for (Literal literal : literals) {
			literal.setValue(!literal.getValue());
		}
		return this;
	}

	public String toString() {
		String result = "(";

		for (Literal literal : literals) {
			result += String.format("%s %s ", literal.toString(), Constants.OR);
		}
		result = result.substring(0, result.length() - 3) + ")";

		return result;
	}

	public List<Literal> getLiterals() {
		return literals;
	}

	public boolean add(Literal newLiteral) {
		return literals.add(newLiteral);
	}

	public boolean remove(Literal newLiteral) {
		return literals.remove(newLiteral);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Clause) {
			return this.hashCode() == o.hashCode();
		} else
			return false;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		for (Literal literal : literals) {
			result = prime * result + literal.hashCode();
		}
		return result;
	}
}
