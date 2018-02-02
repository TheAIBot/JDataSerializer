package testClasses;

import serializer.TestTools;

public class Dog extends Animal {
	public final DogBarks barkSound;
	
	public Dog(String name, DogBarks dogNoise) {
		super(name);
		this.barkSound = dogNoise;
	}
	
	public static Dog createRandomDog() {
		return new Dog(TestTools.randomString(10), TestTools.randomEnum(DogBarks.class));
	}

	@Override
	public boolean equals(Object b) {
		if (b instanceof Dog) {
			final Dog dog = (Dog)b;
			return this.name.equals(dog.name) && this.barkSound == dog.barkSound;
		}
		return false;
	}
}