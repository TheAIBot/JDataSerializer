package serializer;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import testClasses.Animal;
import testClasses.Cat;
import testClasses.Dog;

public class TestInterfaces {
	@Test
	public void serializeList() throws Exception {
		List<Animal> expected = new ArrayList<Animal>();
		expected.add(Dog.createRandomDog());
		expected.add(Dog.createRandomDog());
		expected.add(Dog.createRandomDog());
		expected.add(Cat.createRandomCat());
		expected.add(Dog.createRandomDog());
		expected.add(Cat.createRandomCat());
		
		final List<Animal> actual = TestTools.deSerialize(List.class, expected);
		
		assertEquals(expected.size(), actual.size());
		assertEquals(expected.get(0), actual.get(0));
		assertEquals(expected.get(1), actual.get(1));
		assertEquals(expected.get(2), actual.get(2));
		assertEquals(expected.get(3), actual.get(3));
		assertEquals(expected.get(4), actual.get(4));
		assertEquals(expected.get(5), actual.get(5));
		
	}
}
