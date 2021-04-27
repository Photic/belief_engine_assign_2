package main.models;

import main.Constants;

public class Literal {

    private String symbol;
    private boolean positiv;

    public Literal(String symbol, boolean positiv) {
        this.symbol = symbol;
        this.positiv = positiv;
    }


    public boolean isLiteralPositiv() {
        return positiv;
    }

    public boolean isLiteralNegative() {
        return !positiv;
    }

    public boolean isEqual(Literal l) {

        if (this.symbol.equals(l.symbol) && this.positiv == l.positiv) {
            return true;
        }

        return false;
    }

    public String literalToString() {
        String s = "";

        if (this.isLiteralNegative()) {
            s += Constants.NOT + this.symbol;
        }
        else {
            s += this.symbol;
        }
        return s;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isPositiv() {
        return positiv;
    }

    public void setPositiv(boolean positiv) {
        this.positiv = positiv;
    }


    public String toString() {
        return literalToString();
    }
}
