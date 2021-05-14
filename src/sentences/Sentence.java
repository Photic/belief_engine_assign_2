package sentences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public abstract class Sentence implements ISentence {
	
	@Override
	public Sentence convertToCNF() throws Exception {
		int allowedCycles = 256;
		Sentence cnfSentence = this;
		
		do {
			cnfSentence = cnfSentence.reduce();
			if (allowedCycles <= 1) {
				throw new Exception("Could not convert to CNF-form. Please check that all sentences are properly defined.");
			}
			allowedCycles--;
		} while (!cnfSentence.isInCNF());
		return cnfSentence;
	}
	public List<Sentence> getPredicates() {
		List<Sentence> distinctPredicates = new ArrayList<Sentence> (
				new HashSet<>(getPredicates(new ArrayList<Sentence>()))
				);
		return distinctPredicates;
	}
	protected abstract List<Sentence> getPredicates(ArrayList<Sentence> predicates);
}
