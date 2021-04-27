package main;

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
	public String getName() {
		return name;
	}
	public String toString() {
		if (value) {
			return name;
		}
		else {
			return "�" + name;
		}
	}
	
}
