package model;

import java.util.ArrayList;
import java.util.List;

public class Clause {

	private List<Literal> literals;
	
	public Clause() {
		literals = new ArrayList<Literal>();
	}
	
	public Clause negateClause() {
		
		for (Literal literal : literals) {
			literal.setValue(!literal.getValue());
		}
		return this;
	}
	
}
