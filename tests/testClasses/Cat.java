package testClasses;

public class Cat extends Animal {
	public int age;
	
	public Cat(String name, int age) {
		super(name);
		this.age = age;
	}
	
	@Override
	public boolean equals(Animal b) {
		if (b instanceof Cat) {
			final Cat cat = (Cat) b;
			return this.name.equals(cat.name) && this.age == cat.age;
		}
		return false;
	}

}
