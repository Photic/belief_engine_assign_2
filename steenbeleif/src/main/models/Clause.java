package main.models;

import main.Constants;

import java.util.List;

public class Clause {

    List<Literal> literals;

    public Clause(List<Literal> literals) {
        this.literals = literals;

        //Simplify literals
        for (int i = 0; i < this.literals.size(); i++) {
            for (int j = i + 1; j < this.literals.size(); j++) {
                if ( this.literals.get(i).isEqual(this.literals.get(j))) {
                    this.literals.remove(this.literals.get(j));
                }
            }
        }
    }

    public String clausesToString() {

        String s = "";

        s += "(";
        for (int i = 0; i < literals.size(); i++) {

            s += literals.get(i).literalToString();

            if (i < literals.size() - 1) {
                s += " " + Constants.OR + " ";
            }
        }

        s += ")";
        return s;
    }

    public Clause negatedClause() {


        for (int i = 0; i < this.literals.size(); i++) {

            this.literals.get(i).setPositiv(!this.literals.get(i).isPositiv());

        }

        return this;
    }

    public boolean isTautologi() {

        for (int i = 0; i < this.literals.size(); i++) {
            for (int j = i + 1; j < this.literals.size(); j++) {
                if (this.literals.get(i).getSymbol().equals(this.literals.get(j).getSymbol()) &&
                        this.literals.get(i).isPositiv() != this.literals.get(j).isPositiv()) {
                    return true;
                }

            }
        }

        return false;
    }

    public void removeComplementaryLiterals() {
        for (int i = 0; i < this.literals.size(); i++) {
            for (int j = i + 1; j < this.literals.size(); j++) {
                if (this.literals.get(i).getSymbol().equals(this.literals.get(j).getSymbol()) &&
                        this.literals.get(i).isPositiv() != this.literals.get(j).isPositiv()) {
                    System.out.println(this.literals.get(i).literalToString());
                    System.out.println(this.literals.get(j).literalToString());
                    this.literals.remove(this.literals.get(i));
                    this.literals.remove(this.literals.get(j-1));
                }
            }
        }

    }

    public Clause simplifyClause() {

        for (int i = 0; i < this.literals.size(); i++) {
            for (int j = i + 1; j < this.literals.size(); j++) {
                if ( this.literals.get(i).isEqual(this.literals.get(j))) {
                    this.literals.remove(this.literals.get(j));
                }
            }
        }

        return this;
    }

    public List<Literal> getLiterals() {
        return literals;
    }

    public boolean compare(Clause clause) {

        if (this.literals.size() != clause.getLiterals().size()) {
            return false;
        }

        String clauseInString = this.clausesToString();

        for (Literal l: clause.getLiterals()) {
            if (!clauseInString.contains(l.literalToString())) {
                return false;
            }
        }

        return true;
    }


    public String toString() {
        return this.clausesToString();
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
