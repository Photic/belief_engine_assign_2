package src;
import src.model.*;

public class RunMe {
    public static void main(String args[]) {
    	Clause clauseA = new Clause();
    	clauseA.add(new Literal("a",true));
    	clauseA.add(new Literal("b",false));
        
        Clause clauseB = new Clause();
        clauseB.add(new Literal("c",false));
        clauseB.add(new Literal("d",true));
        clauseB.add(new Literal("e",false));
        
        BeliefBase bB = new BeliefBase();
        bB.add(clauseA);
        bB.add(clauseB);
        System.out.println(bB.toString());

        Clause clauseACopy = new Clause();
        clauseACopy.add(new Literal("a",true));
        clauseACopy.add(new Literal("b",true));
        System.out.println("Clause A hashcode: "+clauseA.hashCode());
        System.out.println("Clause B hashcode: "+clauseB.hashCode());
        System.out.println("Clause A copy hashcode: "+clauseACopy.hashCode());
        bB.add(clauseACopy);
        System.out.println(bB.toString());


    }
}
