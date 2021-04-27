package src;
import java.util.ArrayList;
import src.model.*;

public class RunMe {
    public static void main(String args[]) {
        ArrayList<Literal> literals = new ArrayList<Literal>();
        literals.add(new Literal("a",true));
        literals.add(new Literal("b",false));
        Clause clause = new Clause(literals);
        System.out.println(clause.toString());
    }
}
