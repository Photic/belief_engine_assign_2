package sentences;

public interface ISentence {
	public boolean getValue();
	public boolean isInCNF();
	public Sentence convertToCNF() throws Exception;
	public Sentence reduce();
	public Sentence reduce(int times);
	public String toString();
	public Sentence copy();
	public boolean equals(Object other);
	public int hashCode();
}