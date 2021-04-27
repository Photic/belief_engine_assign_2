package src.model;

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
	
}
