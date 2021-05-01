package src.model;

import java.util.HashSet;
import java.util.Set;

public class BeliefBase {

	private Set<Clause> clauses;
	
	public BeliefBase() {
		clauses = new HashSet<Clause>();
	}
	public boolean addClause(Clause newClause) {
		return clauses.add(newClause);
	}
	public boolean removeClause(Clause clause) {
		return clauses.remove(clause);
	}
	public Set<Clause> getClauses() {
		return clauses;
	}
}
