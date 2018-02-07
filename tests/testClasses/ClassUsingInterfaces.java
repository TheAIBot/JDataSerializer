package testClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import serializer.TestTools;

public class ClassUsingInterfaces {
	final public List<Animal> animals = new ArrayList<Animal>();
	final public List<Mammal> mammals = new ArrayList<Mammal>();
	final public Map<String, Integer> something = new HashMap<>();
	final public Map<Integer, DogBarks> somethingElse = new TreeMap<>();
	
	public ClassUsingInterfaces() {
		for (int i = 0; i < 5; i++) {
			animals.add(Dog.createRandomDog());
			animals.add(Cat.createRandomCat());
			mammals.add(Dog.createRandomDog());
		}
		for (int i = 0; i < 5; i++) {
			something.put(TestTools.randomString(3), TestTools.randomInt());
		}
		for (int i = 0; i < 7; i++) {
			somethingElse.put(TestTools.randomInt(), TestTools.randomEnum(DogBarks.class));
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ClassUsingInterfaces) {
			final ClassUsingInterfaces cui = (ClassUsingInterfaces)obj;
			
			if (this.animals.size() != cui.animals.size() ||
				this.mammals.size() != cui.mammals.size() ||
				this.something.size() != cui.something.size() ||
				this.somethingElse.size() != cui.somethingElse.size()) {
				return false;
			}
			
			if (this.animals.equals(cui.animals) &&
				this.mammals.equals(cui.mammals) &&
				this.something.equals(cui.something) &&
				this.somethingElse.equals(cui.somethingElse)) {
				return true;
			}
		}
		return false;
	}
}
