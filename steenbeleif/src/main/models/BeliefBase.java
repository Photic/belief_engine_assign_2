package main.models;

import main.Constants;

import java.util.List;

public class BeliefBase {

    private List<Clause> clauses;

    public BeliefBase(List<Clause> clauses) {
        this.clauses = clauses;
    }

    public String beliefBaseToString() {

        String s = "";

        for (int i = 0; i < clauses.size(); i++) {

            s += clauses.get(i).clausesToString();

            if (i < clauses.size() - 1) {
                s += " " + Constants.AND + " ";
            }
        }

        return s;
    }

    public List<Clause> getClauses() {
        return clauses;
    }

    public void addClause(Clause clause) {
        this.clauses.add(clause);
    }

    public void removeClause(Clause clause) {
        this.clauses.remove(clause);
    }
}
