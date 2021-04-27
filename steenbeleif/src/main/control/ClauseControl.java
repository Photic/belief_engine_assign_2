package main.control;

import main.models.Clause;
import main.models.Literal;

import java.util.ArrayList;
import java.util.List;

public class ClauseControl {

    public void deleteTautologies(List<Clause> clauses) {

        List<Clause> toDelete = new ArrayList<Clause>();

        for (Clause c : clauses) {
            if (c.isTautologi()) {
                toDelete.add(c);
            }
        }

        clauses.removeAll(toDelete);
    }

    public List<String> getAllNegativeSymbols(Clause clause) {

        List<String> negativeSymbols = new ArrayList<>();

        for (Literal l : clause.getLiterals()) {
            if (l.isLiteralNegative()) {
                negativeSymbols.add(l.getSymbol());
            }
        }

        return negativeSymbols;
    }

    public List<String> getAllPositiveSymbols(Clause clause) {

        List<String> positiveSymbols = new ArrayList<>();

        for (Literal l : clause.getLiterals()) {
            if (l.isLiteralPositiv()) {
                positiveSymbols.add(l.getSymbol());
            }
        }

        return positiveSymbols;
    }

    public Clause negateClause(Clause clause) {

        List<Literal> literals = new ArrayList<Literal>();

        for (int i = 0; i < clause.getLiterals().size(); i++) {
            literals.add(new Literal(clause.getLiterals().get(i).getSymbol(), !clause.getLiterals().get(i).isPositiv()));
        }

        Clause c = new Clause(literals);

        return c;
    }

    public void simplifyListClauses(List<Clause> clauses) {

        for (int i = 0; i < clauses.size(); i++) {
            for (int j = i+1; j < clauses.size(); j++) {
                if (clauses.get(i).compare(clauses.get(j))) {
                    clauses.remove(clauses.get(j));
                    j--;
                }
            }
        }
    }
}
