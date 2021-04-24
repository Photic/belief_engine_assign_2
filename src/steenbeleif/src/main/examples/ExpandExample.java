package main.examples;


import main.models.BeliefBase;
import main.control.BeliefControl;
import main.models.Clause;
import main.models.Literal;

import java.util.ArrayList;
import java.util.List;

public class ExpandExample {

    public static void main(String[] arg) {

        //
        // This example contains K + 𝛗, where 𝛗 = R
        //

        BeliefControl BB = new BeliefControl();

        //Generate the clause to be expand.
        Literal literalToExpand = new Literal("R", true);
        List<Literal> literals = new ArrayList<Literal>();
        literals.add(literalToExpand);
        Clause clauseToExpand = new Clause(literals);

        //Printing the clause to expand.
        System.out.println("Clause to expand: " + clauseToExpand.clausesToString());

        //Generating the Belief Base.
        Literal b1 = new Literal("Q", false);
        Literal b2 = new Literal("C", true);
        Literal b3 = new Literal("P", false);
        List<Literal> BBLiterals = new ArrayList<Literal>();
        BBLiterals.add(b1);
        BBLiterals.add(b2);
        BBLiterals.add(b3);
        Clause clause = new Clause(BBLiterals);

        //Generating the Belief Base.
        List<Clause> beliefClauses = new ArrayList<Clause>();
        beliefClauses.add(clause);

        BeliefBase beliefBase = new BeliefBase(beliefClauses);

        //Printing the belief base.
        System.out.println("Belief Base before expansion: " + beliefBase.beliefBaseToString());
        System.out.println("." + "\n" + "." + "\n" + "." + "\n");

        //Running the expansion
        BB.expand(beliefBase, clauseToExpand);

        //Printing the belief base
        System.out.println("Belief Base after expansion: " + beliefBase.beliefBaseToString());
    }
}
