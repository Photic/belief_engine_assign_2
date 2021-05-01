package src.model;

import java.util.HashSet;
import java.util.Set;

public class BeliefBase {

	private Set<Clause> clauses;
	
	public BeliefBase() {
		clauses = new HashSet<Clause>();
	}
	public boolean add(Clause newClause) {
		return clauses.add(newClause);
	}
	public boolean remove(Clause clause) {
		return clauses.remove(clause);
	}
	public Set<Clause> getClauses() {
		return clauses;
	}
	public String toString() {
		String result = "";
		for (Clause clause : clauses) {
	        result += String.format("%s %s ", clause.toString(), Constants.AND);
	    }
		result = result.substring(0,result.length()-3);
		return result;
	}
	public boolean equals(Object o) {
		if (o instanceof BeliefBase) {
			return this.hashCode() == o.hashCode();			
		} else return false;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    for (Clause clause : clauses) {
	    	result = prime * result + clause.hashCode();	    	
	    }
		return result;
	}
	
}
