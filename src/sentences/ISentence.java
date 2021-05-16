package sentences;

import java.util.List;

public interface ISentence {
	public boolean getValue();
	public boolean isInCNF();
	public Sentence convertToCNF() throws Exception;
	public Sentence reduce();
	public Sentence reduce(int times);
	public boolean contains(Sentence sentence);
	public List<Sentence> getPredicates();
	public String toString();
	public Sentence copy();
	public boolean equals(Object other);
	public int hashCode();
	public boolean isNotValid(List<Sentence> predicates);
}