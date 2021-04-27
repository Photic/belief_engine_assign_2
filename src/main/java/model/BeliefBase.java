package model;

import java.util.HashSet;
import java.util.Set;

public class BeliefBase {

	private Set<Clause> clauses;
	
	public BeliefBase() {
		clauses = new HashSet<Clause>();
	}
}
