package sentences;

public interface Sentence {
	public boolean getValue();
	public Sentence reduce();
	public Sentence reduce(int times);
	public String toString();
}