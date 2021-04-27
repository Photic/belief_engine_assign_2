package main.examples;

import main.models.BeliefBase;
import main.control.BeliefControl;
import main.models.Clause;
import main.models.Literal;

import java.util.ArrayList;
import java.util.List;

public class RevisionExample {

    public static void main(String[] arg) {

        //
        // This example contains B * 𝛗 := (B ÷ ¬𝛗) + 𝛗 where 𝛗 = ¬C
        //

        BeliefControl BB = new BeliefControl();

        //Generate the clause to be revised.
        Literal literalToBeRevised = new Literal("C", false);
        List<Literal> literals = new ArrayList<>();
        literals.add(literalToBeRevised);
        Clause clauseToRevised = new Clause(literals);

        //Printing the clause to contract.
        System.out.println("Clause to be revised: " + clauseToRevised.clausesToString());

        //Generating the Belief Base.
        Literal b1 = new Literal("Q", false);
        Literal b2 = new Literal("C", true);
        Literal b3 = new Literal("P", false);
        Literal b4 = new Literal("R", true);
        List<Literal> BBLiterals = new ArrayList<>();
        BBLiterals.add(b1);
        BBLiterals.add(b2);
        BBLiterals.add(b3);
        BBLiterals.add(b4);
        Clause clause = new Clause(BBLiterals);

        //Generating the Belief Base.
        List<Clause> beliefClauses = new ArrayList<>();
        beliefClauses.add(clause);

        BeliefBase beliefBase = new BeliefBase(beliefClauses);

        //Printing the belief base.
        System.out.println("Belief Base before revision: " + beliefBase.beliefBaseToString());
        System.out.println("." + "\n" + "." + "\n" + "." + "\n");

        //Running the contraction
        BB.revision(beliefBase, clauseToRevised);

        //Printing the belief base
        System.out.println("Belief Base after revision: " + beliefBase.beliefBaseToString());
    }


}
