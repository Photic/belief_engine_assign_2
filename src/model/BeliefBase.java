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
				}
			}
			for (Sentence sentence : sentencesToRemove) {
				int index = bbOnCNF.indexOf(sentence);
				bbOnCNF.remove(index);
				sentences.remove(index);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void revise(Sentence newSentence) {
		if (!sentences.contains(new NotSentence(newSentence))) {
			contract(new NotSentence(newSentence));
		}
		if (!sentences.contains(newSentence)) {
			expand(newSentence);
		}
	}

	public boolean PL_Resolution(Sentence alpha){
		List<Sentence> ResolSentences = new ArrayList<Sentence>();

		// for(Sentence s: sentences){
		// 	System.out.println(s.toString());
		// }
		ResolSentences.addAll(sentences);

		System.out.println("typpi!");
		for(Sentence s : ResolSentences){
			System.out.println(s.toString());
		}

		List<Sentence> toDelete = new ArrayList<Sentence>();

		// Delete tautologies that are reduntant
	
		for(Sentence s : ResolSentences){
			for(Sentence predicate : s.getPredicates()){
				if(predicate.isNotValid(ResolSentences)){
					toDelete.add(predicate);
				}
			}
			
		}

		ResolSentences.removeAll(toDelete);
		Sentence notalpha= new NotSentence(alpha);
		// System.out.println("typpi!2");
		// System.out.println(notalpha.toString());

		ResolSentences.add(notalpha); // negate alpha
	
		
		for(Sentence s : ResolSentences){
			System.out.println(s.toString());
		}
			

		try{
		for(int i = 0; i< ResolSentences.size(); i++){
			ResolSentences.get(i).convertToCNF();
			//convertAllToCNF(ResolSentences);
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		// System.out.println("typpi!2");

		for(Sentence s : ResolSentences){
			System.out.println(s.toString());
		}
			


		List<Sentence> newSentences = new ArrayList<Sentence>();

		do{
			List<Sentence> sentencesAsList = new ArrayList<Sentence>(ResolSentences);
			for(int i = 0; i < sentencesAsList.size()-1;i++){
				Sentence si= sentencesAsList.get(i);
				for(int j = i+1; j< sentencesAsList.size();j++){
					Sentence sj = sentencesAsList.get(j);

					List<Sentence> resolvents = resolve(si,sj);

					for(Sentence s : resolvents){
						if(s.getPredicates().isEmpty()){
							// Contradiction found!
							return true;
						}
					}
					newSentences.addAll(resolvents);
				}
			}

			if(ResolSentences.containsAll(newSentences));{
				// No contradiction found!
				return false;
			}
		}while(true);

	}

	public List<Sentence> resolve (Sentence s1, Sentence s2){
		List<Sentence> toResolve = new ArrayList<Sentence>();

		resolvePfromN(s1,s2, toResolve);
		resolvePfromN(s2,s1,toResolve);

		return toResolve;
	}


	public void resolvePfromN(Sentence s1, Sentence s2, List<Sentence> toResolve){
	

		List<Sentence> PfromS1 = new ArrayList<Sentence>(); //List of positive predicates fromm s1
		List<Sentence> NfromS2= new ArrayList<Sentence>(); // List of negative predicates from s2
		
		for(Sentence s : s1.getPredicates()){
			if(s.getValue()){
				PfromS1.add(s);
			}

		}

		for(Sentence s : s2.getPredicates()){
			if(!s.getValue()){
				NfromS2.add(s);
			}
		}
		
		List<Sentence> comp = new ArrayList<Sentence>(); // List of complementary predicates from PfromS1 and NfromS2
		for(Sentence pos : PfromS1){
			for(Sentence neg : NfromS2){
				for(Sentence p : pos.getPredicates()){
					for(Sentence p2 : neg.getPredicates()){
						if(((AtomicSentence) p).getName()==((AtomicSentence) p2).getName()){
							comp.add(p);
						}
					}
				}
			}
		}

		for(Sentence c_s : comp){
			List<Sentence> resolventPredicates = new ArrayList<Sentence>();
			boolean c_sIsAtomic = false;
			boolean c_sIsNotOfAtomic = false;
			Sentence c_sNotChild = null;
			if (c_s instanceof AtomicSentence) {
				c_sIsAtomic = true;
			} else {
				c_sIsNotOfAtomic = true;
				c_sNotChild = ((NotSentence) c_s).getSentence();
			}
			for(Sentence pred1 : s1.getPredicates()){
				if (pred1 instanceof AtomicSentence && c_sIsAtomic) {
					AtomicSentence pred1Atomic = (AtomicSentence) pred1;
					if(!pred1Atomic.getValue() || !pred1Atomic.getName().equals(((AtomicSentence)c_s).getName())){
						resolventPredicates.add(pred1);
					}									
				}
				if (pred1 instanceof NotSentence && c_sIsAtomic) {
					AtomicSentence pred1Atomic = (AtomicSentence) ((NotSentence) pred1).getSentence();
					if(!pred1.getValue() || !pred1Atomic.getName().equals(((AtomicSentence)c_s).getName())){
						resolventPredicates.add(pred1);
					}									
				}
				if (pred1 instanceof AtomicSentence && c_sIsNotOfAtomic) {
					AtomicSentence pred1Atomic = (AtomicSentence) pred1;
					if(!pred1Atomic.getValue() || !pred1Atomic.getName().equals(((AtomicSentence)c_sNotChild).getName())){
						resolventPredicates.add(pred1);
					}									
				}
				if (pred1 instanceof NotSentence && c_sIsNotOfAtomic) {
					AtomicSentence pred1Atomic = (AtomicSentence) ((NotSentence) pred1).getSentence();
					if(!pred1.getValue() || !pred1Atomic.getName().equals(((AtomicSentence)c_sNotChild).getName())){
						resolventPredicates.add(pred1);
					}									
				}
			}

			for(Sentence pred2 : s2.getPredicates()){
				if (pred2 instanceof AtomicSentence && c_sIsAtomic) {
					AtomicSentence pred2Atomic = (AtomicSentence) pred2;
					if(!pred2Atomic.getValue() || !pred2Atomic.getName().equals(((AtomicSentence)c_s).getName())){
						resolventPredicates.add(pred2);
					}									
				}
				if (pred2 instanceof NotSentence && c_sIsAtomic) {
					AtomicSentence pred2Atomic = (AtomicSentence) ((NotSentence) pred2).getSentence();
					if(!pred2.getValue() || !pred2Atomic.getName().equals(((AtomicSentence)c_s).getName())){
						resolventPredicates.add(pred2);
					}									
				}
				if (pred2 instanceof AtomicSentence && c_sIsNotOfAtomic) {
					AtomicSentence pred2Atomic = (AtomicSentence) pred2;
					if(!pred2Atomic.getValue() || !pred2Atomic.getName().equals(((AtomicSentence)c_sNotChild).getName())){
						resolventPredicates.add(pred2);
					}									
				}
				if (pred2 instanceof NotSentence && c_sIsNotOfAtomic) {
					AtomicSentence pred2Atomic = (AtomicSentence) ((NotSentence) pred2).getSentence();
					if(!pred2.getValue() || !pred2Atomic.getName().equals(((AtomicSentence)c_sNotChild).getName())){
						resolventPredicates.add(pred2);
					}									
				}
			}
			toResolve.addAll(resolventPredicates);
		}
		
	}


	public String toString() {
		String result = "(";
		if (sentences.isEmpty()) {
			return result + " )";
		}
		for (Sentence sentence : sentences) {
			result += String.format(" %s , ", sentence.toString());
		}
		result = result.substring(0, result.length() - 2) + ")";
		return result;
	}
	public void clear() {
		sentences.clear();
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
