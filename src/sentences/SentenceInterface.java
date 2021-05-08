package sentences;

public interface SentenceInterface {
	public boolean getValue();
	public boolean isInCNF();
	public Sentence convertToCNF();
	public Sentence reduce();
	public Sentence reduce(int times);
	public String toString();
	public Sentence copy();
}