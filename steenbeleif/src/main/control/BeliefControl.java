package main.control;

import main.*;
import main.models.BeliefBase;
import main.models.Clause;
import main.models.Literal;

import java.util.ArrayList;
import java.util.List;

public class BeliefControl {

    public void expand(BeliefBase beliefBase, Clause clause) {
        // K + 𝛗
        
        beliefBase.addClause(clause);
    }

    public void contraction(BeliefBase beliefBase, Clause clause) {
        // B ÷ 𝛗

        for (int i = 0; i < beliefBase.getClauses().size(); i++) {
            if (searchClauseInBeliefBase(beliefBase, clause)) {
                for (int j = 0; j < beliefBase.getClauses().get(i).getLiterals().size(); j++) {
                    if (beliefBase.getClauses().get(i).getLiterals().get(j).isLiteralNegative() || isLiteralInClause(beliefBase.getClauses().get(i).getLiterals().get(j), clause)) {
                        beliefBase.getClauses().get(i).getLiterals().remove(beliefBase.getClauses().get(i).getLiterals().get(j));
                        j -= 1;
                    }
                }
            }

            if (beliefBase.getClauses().get(i).getLiterals().size() <= 0) {
                beliefBase.removeClause(beliefBase.getClauses().get(i));
            }
        }
    }

    public void revision(BeliefBase beliefBase, Clause clause) {
        //B * 𝛗 := (B ÷ ¬𝛗) + 𝛗

        ClauseControl cc = new ClauseControl();

        //Convert the clause to the negated clause
        Clause negatedClause = cc.negateClause(clause);

        //B ÷ ¬𝛗 (Contraction)
        contraction(beliefBase, negatedClause);

        // B + 𝛗 (Expand)
        expand(beliefBase, clause);
    }

    public boolean PLResolution(BeliefBase beliefBase, Clause alpha) {
        ClauseControl clauseControl = new ClauseControl();

        List<Clause> clauses = new ArrayList<Clause>();

        //Remove all tautologies from the belief base.
        //We don't want redundant tautologies.
        clauseControl.deleteTautologies(beliefBase.getClauses());

        //Adding the belief base clauses and the negated alpha.
        clauses.addAll(beliefBase.getClauses());
        clauses.add(alpha.negatedClause());

        //Create a empty list of clauses, we will fill doing the do/while-loop.
        List<Clause> newClauses = new ArrayList<Clause>();

        do {
            List<Clause> clausesAsList = new ArrayList<Clause>(clauses);

            for (int i = 0; i < clausesAsList.size() - 1; i++) {
                Clause ci = clausesAsList.get(i);
                for (int j = i+1; j < clausesAsList.size(); j++) {
                    Clause cj = clausesAsList.get(j);

                    //resolve the two input clauses
                    //We get all the new clauses resolved from the to clauses.
                    List<Clause> resolvents = PLResolve(ci, cj);

                    //If we have a empty clause, then return true.
                    for (Clause c : resolvents) {
                        if (c.getLiterals().isEmpty()) {
                            //We found an contradiction
                            return true;
                        }
                    }
                    newClauses.addAll(resolvents);
                }
            }

            clauseControl.simplifyListClauses(newClauses);

            if (clauses.containsAll(newClauses)) {
                //We didn't find a contradiction
                return false;
            }

            clauses.addAll(newClauses);

        } while (true);
    }

    private List<Clause> PLResolve(Clause c1, Clause c2) {

        List<Clause> resolvents = new ArrayList<>();

        //resolve the complementary from both clauses.
        resolvePositiveFromNegative(c1, c2, resolvents);
        resolvePositiveFromNegative(c2, c1, resolvents);

        return resolvents;
    }

    private void resolvePositiveFromNegative(Clause c1, Clause c2, List<Clause> resolvents) {

        ClauseControl clauseControl = new ClauseControl();

        List<String> complementarySymbols;

        //Find the positive and negative symbols, from clause c1 and c2
        List<String> positiveSymbolsFromC1 = clauseControl.getAllPositiveSymbols(c1);
        List<String> negativeSymbolsFromC2 = clauseControl.getAllNegativeSymbols(c2);

        //Removing all symbols that is not in the negativeSymbolsFromC2
        complementarySymbols = Util.getComplementary(positiveSymbolsFromC1, negativeSymbolsFromC2);

        for (String complementSymbol : complementarySymbols) {

            List<Literal> resolventLiterals = new ArrayList<Literal>();

            for (Literal literalInC1 : c1.getLiterals()) {
                if (literalInC1.isLiteralNegative() || !literalInC1.getSymbol().equals(complementSymbol)) {
                    resolventLiterals.add(literalInC1);
                }
            }

            for (Literal literalInC2 : c2.getLiterals()) {
                if (literalInC2.isLiteralPositiv() || !literalInC2.getSymbol().equals(complementSymbol)) {
                    resolventLiterals.add(literalInC2);
                }
            }

            //Create the resolvent, that will return to the PLResolution
            //If it is empty, then we have find a contradiction
            Clause resolvent = new Clause(resolventLiterals);

            resolvents.add(resolvent);
        }
    }

    private boolean searchClauseInBeliefBase(BeliefBase beliefBase, Clause clause) {

        boolean isIn = false;

        List<String> beliefBaseLiteralsInString = new ArrayList<>();
        List<String> clauseLiteralsInString = new ArrayList<>();

        for (int i = 0; i < beliefBase.getClauses().size(); i++) {
            for (int j = 0; j < beliefBase.getClauses().get(i).getLiterals().size(); j++) {
                beliefBaseLiteralsInString.add(beliefBase.getClauses().get(i).getLiterals().get(j).literalToString());
            }
        }

        for (int i = 0; i < clause.getLiterals().size(); i++) {
            clauseLiteralsInString.add(clause.getLiterals().get(i).literalToString());
        }

        if (beliefBaseLiteralsInString.containsAll(clauseLiteralsInString)) {
            isIn = true;
        }

        return isIn;
    }

    private boolean isLiteralInClause(Literal l, Clause clause) {

        boolean isIn = false;

        List<String> clauseLiteralsInString = new ArrayList<>();


        for (int i = 0; i < clause.getLiterals().size(); i++) {
            clauseLiteralsInString.add(clause.getLiterals().get(i).literalToString());
        }

        if (clauseLiteralsInString.contains(l.literalToString())) {
            isIn = true;
        }

        return isIn;
    }




}
