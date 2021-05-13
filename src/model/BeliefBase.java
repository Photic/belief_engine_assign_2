package model;

import java.util.ArrayList;
import java.util.List;
import sentences.*;

public class BeliefBase {

	private List<Sentence> sentences;
	
	public BeliefBase() {
		sentences = new ArrayList<Sentence>();
	}
	
	public boolean add(Sentence newSentence) {
		if (!sentences.contains(newSentence)) {
			return sentences.add(newSentence);
		}
		return false;
	}

	public boolean add(String name) {
		Sentence converted = new AtomicSentence(name);
		if (!sentences.contains(converted)) {
			return sentences.add(converted);
		}
		return false;
	}

	public boolean remove(Sentence newSentence) {
		return sentences.remove(newSentence);
	}
	public boolean remove(int index) {
		return remove(sentences.get(index));
	}
	public List<Sentence> getSentences() {
		return sentences;
	}
	public void convertToCNF(int index) throws Exception {
		sentences.set(index, sentences.get(index).convertToCNF());
	}
	public void convertAllToCNF() throws Exception {
		for (int i = 0; i < sentences.size(); i++) {
			convertToCNF(i);
		}
	}

	public String toString() {
		String result = "(";
		for (Sentence sentence : sentences) {
			result += String.format(" %s , ", sentence.toString());
		}
		result = result.substring(0, result.length() - 2) + ")";
		return result;
	}
	
	public boolean equals(Object o) {
		if (o instanceof BeliefBase) {
			return this.hashCode() == o.hashCode();
		} else
			return false;
	}
	
	public int hashCode() {
		final int prime = 31;
	    int result = 1;
	    for (Sentence sentence : sentences) {
	    	result = prime * result + sentence.hashCode();	    	
	    }
		return result;
	}
	
}
