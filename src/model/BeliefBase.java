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
		convertToCNF(sentences, index);
	}
	private void convertToCNF(List<Sentence> beliefBase,int index) throws Exception {
		beliefBase.set(index, beliefBase.get(index).convertToCNF());
	}
	public void convertAllToCNF() throws Exception {
		convertAllToCNF(sentences);
	}
	private void convertAllToCNF(List<Sentence> beliefBase) throws Exception {
		for (int i = 0; i < beliefBase.size(); i++) {
			convertToCNF(i);
		}
	}
	public void contract(Sentence newSentence) {
		sentences.remove(newSentence);
		try {
			Sentence newSentenceCNF = newSentence.convertToCNF();
			List<Sentence> bbOnCNF = new ArrayList<Sentence>();
			bbOnCNF.addAll(sentences);
			convertAllToCNF(bbOnCNF);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void contract(String name) {
		Sentence converted = new AtomicSentence(name);
		contract(converted);
	}
	public void revise(Sentence newSentence) {
		if (!sentences.contains(newSentence)) {
			// Contract by not(newSentence)
			sentences.add(newSentence);
			
		}
	}
	public void revise(String name) {
		Sentence converted = new AtomicSentence(name);
		revise(converted);
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
