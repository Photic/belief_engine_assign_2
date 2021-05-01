package model;

public class Literal {
	
	private String name;
	private boolean value;
	
	public Literal(String name, boolean value) {
		this.name = name;
		this.value = value;
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
		}
		else {
			return "¬" + name;
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o instanceof Literal) {
			Literal other = (Literal) o;
			return name.equals(other.getName()) && (value == other.getValue());			
		} else return false;
	}
	@Override
	public int hashCode() {
		return name.hashCode() + (value ? 2 : 1);
	}
	
}
