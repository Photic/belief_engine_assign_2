package main;

import java.util.ArrayList;

public class TesterClass {

	public static void main(String[] args) {
		ArrayList<Literal> literalList = new ArrayList<Literal>();
		literalList.add(new Literal("a",true));
		literalList.add(new Literal("b",false));
		Clause clause = new Clause(literalList);
		
		System.out.println(clause.toString());

	}

}
