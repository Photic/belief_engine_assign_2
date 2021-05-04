package sentences;

public interface Sentence {
	public boolean getValue();
	public Sentence reduce();
	public String toString();
}