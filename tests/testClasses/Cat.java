package testClasses;

import serializer.TestTools;

public class Cat extends Animal {
	public int age;
	
	public Cat(String name, int age) {
		super(name);
		this.age = age;
	}
	
	public static Cat createRandomCat() {
		return new Cat(TestTools.randomString(10), TestTools.randomInt());
	}
	
	@Override
	public boolean equals(Object b) {
		if (b instanceof Cat) {
			final Cat cat = (Cat) b;
			return this.name.equals(cat.name) && this.age == cat.age;
		}
		return false;
	}

}
