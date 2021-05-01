package src.model;

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
			result += String.format("%s %s ",literal.toString(),Constants.OR);
		}
		result = result.substring(0,result.length()-3) + ")";
		
		
		return result;
	}
	
}
