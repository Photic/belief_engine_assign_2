package model;

public class Literal {
	
	private String name;
	private boolean value;
	
	public Literal(String name, boolean value) {
		this.name = name;
		this.value = value;
	}
	
	public Literal(boolean value) {
		boolean evaluated = value;
		this.name = "" + evaluated;
		this.value = evaluated;
	}
	
	public boolean getValue() {
		return value;
	}

	public void setValue(boolean newValue) {
		this.value = newValue;
	}

	public String getName() {
		return name;
	}

	public String toString() {
		if (value) {
			return name;
		} else {
			return Constants.NOT + name;
		}
	}
	
	public Literal copy() {
		return new Literal(name, value);
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Literal) {
			Literal other = (Literal) o;
			return name.equals(other.getName()) && (value == other.getValue());
		} else
			return false;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() + (value ? 2 : 1);
	}
	
}
