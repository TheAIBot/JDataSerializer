package serializer;

import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import testClasses.Animal;
import testClasses.Cat;
import testClasses.ClassUsingInterfaces;
import testClasses.Dog;
import testClasses.Mammal;

public class TestInterfaces {
	@Test
	public void serializeList() throws Exception {
		final List<Animal> expected = new ArrayList<Animal>();
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
	
	@Test
	public void serializeClassWithInterfaceFields() throws Exception {
		final ClassUsingInterfaces expected = new ClassUsingInterfaces();
		final ClassUsingInterfaces actual = TestTools.deSerialize(ClassUsingInterfaces.class, expected);
		assertEquals(expected, actual);
	}
	
	@Test
	public void serializeAnonymousInterfaceClass() throws Exception {
		final Mammal expected = new Mammal() {
			String a = "cats are totally not mammals!";
			@Override
			public String getA() {
				return a;
			}
			@Override
			public void setA(String newA) {
				this.a = newA;
			}
			@Override
			public String getB() {
				return "something test worthy";
			}
		};
		expected.setA("cats are also mammals");
		
		//just make sure that it can be serialized.
		//Not sure if it's actually usefully to serialize
		final Mammal actual = TestTools.deSerialize(Mammal.class, expected);
		
		assertEquals(expected.getA(), actual.getA());
		assertEquals(expected.getB(), actual.getB());
	}
}
