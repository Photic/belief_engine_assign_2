package main.examples;

import main.models.BeliefBase;
import main.control.BeliefControl;
import main.models.Clause;
import main.models.Literal;

import java.util.ArrayList;
import java.util.List;

public class ResolutionExample {

    public static void main(String[] arg) {

        //
        // This example contains a resolution.
        //

        BeliefControl BB = new BeliefControl();

        //Setup the Belief Base.
        Literal l1 = new Literal("P", false);
        Literal l2 = new Literal("B", true);
        List<Literal> cl1 = new ArrayList<>();
        cl1.add(l1);
        cl1.add(l2);
        Clause c1 = new Clause(cl1);

        Literal l21 = new Literal("B", false);
        Literal l22 = new Literal("Q", true);
        Literal l23 = new Literal("P", true);
        List<Literal> cl2 = new ArrayList<>();
        cl2.add(l21);
        cl2.add(l22);
        cl2.add(l23);
        Clause c2 = new Clause(cl2);

        Literal l31 = new Literal("Q", false);
        Literal l32 = new Literal("B", true);
        List<Literal> cl3 = new ArrayList<>();
        cl3.add(l31);
        cl3.add(l32);
        Clause c3 = new Clause(cl3);

        Literal l41 = new Literal("B", false);
        List<Literal> cl4 = new ArrayList<>();
        cl4.add(l41);
        Clause c4 = new Clause(cl4);

        List<Clause> allClauses = new ArrayList<>();
        allClauses.add(c1);
        allClauses.add(c2);
        allClauses.add(c3);
        allClauses.add(c4);

        BeliefBase beliefBase = new BeliefBase(allClauses);

        //Printing the Belief Base.
        System.out.println("Belief Base : " + beliefBase.beliefBaseToString());

        Literal l = new Literal("Q", false);
        List<Literal> alpha = new ArrayList<>();
        alpha.add(l);
        Clause alphaClause = new Clause(alpha);

        System.out.println("Alpha " + alphaClause);

        //Running the resolution
        boolean result = BB.PLResolution(beliefBase, alphaClause);

        System.out.println("\n" + "Running the PLResolution");
        System.out.println("." + "\n" +"." + "\n" + ".");
        System.out.println(result);
    }
}
