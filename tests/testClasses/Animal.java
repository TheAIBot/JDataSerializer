package testClasses;

public abstract class Animal {
	public final String name;
	
	public Animal(String name) {
		this.name = name;
	}
	
	public abstract boolean equals(Animal b);
}
