package sentences;

import java.util.ArrayList;
import java.util.List;

public class AtomicSentence extends Sentence {
	private String name;

	public AtomicSentence() {}
	
	public AtomicSentence(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	@Override
	public boolean getValue() {
		return true;
	}

	@Override
	public Sentence reduce() {
		return this;
	}

	@Override
	public Sentence reduce(int times) {
		return this;
	}
	public boolean contains(Sentence sentence) {
		return false;
	}
	protected List<Sentence> getPredicates(ArrayList<Sentence> predicates) {
		predicates.add(this);
		return predicates;
	}
	@Override
	public Sentence copy() {
		return new AtomicSentence(name);
	}

	@Override
	public boolean isInCNF() {
		return true;
	}

	public String toString() {
		return name;
	}
	public boolean isNotValid(List<Sentence> predicates) {
		if (predicates.contains(this)) {
			return true;
		}
		return false;
	}
	@Override
	public boolean equals(Object other) {
		if (other instanceof AtomicSentence) {
			return name.equals(((AtomicSentence) other).getName());
		}
		return false;
	}
	@Override
	public int hashCode() {
		final int prime = 17;
	    int result = 1;
	    result = prime * result + name.hashCode();
		return result;
	}
}
