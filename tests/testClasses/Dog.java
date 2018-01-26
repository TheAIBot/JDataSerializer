package testClasses;

public class Dog extends Animal {
	public final DogBarks barkSound;
	
	public Dog(String name, DogBarks dogNoise) {
		super(name);
		this.barkSound = dogNoise;
	}

	@Override
	public boolean equals(Animal b) {
		if (b instanceof Dog) {
			final Dog dog = (Dog)b;
			return this.name.equals(dog.name) && this.barkSound == dog.barkSound;
		}
		return false;
	}
}