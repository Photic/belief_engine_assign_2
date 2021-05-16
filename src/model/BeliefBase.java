package model;

import java.util.ArrayList;
import java.util.List;
import sentences.*;

public class BeliefBase {

	private List<Sentence> sentences;
	
	public BeliefBase() {
		sentences = new ArrayList<Sentence>();
	}
	public BeliefBase(List<Sentence> sentences) {
		this.sentences = sentences;
	}
	
	public boolean expand(Sentence newSentence) {
		if (!sentences.contains(newSentence)) {
			return sentences.add(newSentence);
		}
		return false;
	}

	public boolean expand(String name) {
		Sentence converted = new AtomicSentence(name);
		return expand(converted);
	}
	public List<Sentence> getSentences() {
		return sentences;
	}
	public boolean contains(Sentence sentence) {
		return sentences.contains(sentence);
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
			convertToCNF(beliefBase, i);
		}
	}
	public void contract(Sentence newSentence) {
		sentences.remove(newSentence);
		try {
			Sentence newSentenceCNF = newSentence.convertToCNF();
			List<Sentence> bbOnCNF = new ArrayList<Sentence>();
			bbOnCNF.addAll(sentences);
			//System.out.println(String.format("newSentence to String: %s", newSentence.toString()));
			//System.out.println(String.format("bbOnCNF to String: %s", bbOnCNF.toString()));
			convertAllToCNF(bbOnCNF);
			
			List<Sentence> sentencesToRemove = new ArrayList<Sentence>();
			for (Sentence bbSentence : bbOnCNF) {
				if (bbSentence.equals(newSentenceCNF)) {
					sentencesToRemove.add(bbSentence);
				}
				else {
					List<Sentence> bbSentencePredicates = bbSentence.getPredicates();
					List<Sentence> newSentencePredicates = newSentenceCNF.getPredicates();
					
					if (bbSentencePredicates.containsAll(newSentencePredicates) || newSentencePredicates.containsAll(bbSentencePredicates)) {
						if (bbSentence.isNotValid(newSentencePredicates)) {
							sentencesToRemove.add(bbSentence);						
					    }
					}
					/*
					for (Sentence predicate : newSentencePredicates) {
						if (bbSentencePredicates.contains(predicate)) {
							sentencesToRemove.add(bbSentence);
							break;
						}
					}
					*/
				}
			}
			for (Sentence sentence : sentencesToRemove) {
				int index = bbOnCNF.indexOf(sentence);
				bbOnCNF.remove(index);
				sentences.remove(index);
			}
			System.out.println(String.format("Sentences after contraction: %s", sentences.toString()));
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
			contract(new NotSentence(newSentence));
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
