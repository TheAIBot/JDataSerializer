package serializer;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import serializer.exceptions.NoValidConstructorException;
import testClasses.*;

public class TestArrays {
	@Test
	public void serializePrimitiveArrays() throws Exception {		
		final byte[]    a1 = new byte[] {3, -7, 23, -56};
		final short[]   a2 = new short[] {12342, 432, -3642, -223};
		final int[]     a3 = new int[] {239842, 29837431, -2093428123,17128};
		final long[]    a4 = new long[] {21123163352341l, -189232526213l, 9812671433818723l, 17912318578192386l, 278353244813021l};
		final float[]   a5 = new float[] {2.43123f, 2346.2342f, -4373422.4223f};
		final double[]  a6 = new double[] {325.124, 436375.3457, 579864.3463423, 5782323.5453242};
		final boolean[] a7 = new boolean[] { true, true, false , true, false, false ,false, true};
		final char[]    a8 = new char[] {'s', 't', '9', 'q'};
		final PrimitiveArrays expected = new PrimitiveArrays(a1, a2, a3, a4, a5, a6, a7, a8);
		final PrimitiveArrays actual = TestTools.deSerialize(PrimitiveArrays.class, expected);
		expected.assertSame(actual);
	}
	
	@Test
	public void serializeAnimalArray() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		final Animal[] expected = new Animal[] { new Dog("steve", DogBarks.CUTE), new Cat("bart", 109), new Dog("carl jhonson", DogBarks.LOUD)};
		final Animal[] actual = TestTools.deSerialize(Animal[].class, expected);
		Arrays.equals(expected, actual);
	}
	
	@Test
	public void serializeEnumArray() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		final DogBarks[] expected = TestTools.createRandomEnumArray(DogBarks.class, 107);
		final DogBarks[] actual = TestTools.deSerialize(DogBarks[].class, expected);
		Arrays.equals(expected, actual);
	}
	
	@Test
	public void serializeArray2DSimple() throws Exception {
		Object[][] expected = new Object[1][];
		expected[0] = new Object[1];
		expected[0][0] = new Byte[] {3, -7, 23, -56};
		
		final Object[][] actual = TestTools.deSerialize(Object[][].class, expected);
		
		Arrays.equals((Byte[])expected[0][0], (Byte[])actual[0][0]);
	}
	
	@Test
	public void serializeArray2DComplicated() throws Exception {
		Object[][] expected = new Object[3][];
		expected[0] = new Object[1];
		expected[0][0] = new Byte[] {3, -7, 23, -56};
		expected[1] = new Object[2];
		expected[1][0] = new Cat("steve", 92);
		expected[1][1] = new int[] {1, 2, 9, 4, 5, 6, 7};
		expected[2] = new Object[3];
		expected[2][1] = new Dog("dogberg", DogBarks.LOUD);
		
		final Object[][] actual = TestTools.deSerialize(Object[][].class, expected);
		
		Arrays.equals((Byte[])expected[0][0], (Byte[])actual[0][0]);
		assertTrue(((Cat)expected[1][0]).equals((Cat)actual[1][0]));
		Arrays.equals((int[])expected[1][1], (int[])actual[1][1]);
		assertEquals(expected[2][0], actual[2][0]);
		assertTrue(((Dog)expected[2][1]).equals((Dog)actual[2][1]));
		assertEquals(expected[2][2], actual[2][2]);
	}
	
	@Test
	public void serializeArray4D() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		Object[][][][] expected = new Object[2][][][];
		expected[0] = new Object[1][][];
		expected[1] = new Object[3][][];
		
		expected[0][0] = new Object[2][];
		expected[1][0] = new Object[1][];
		expected[1][1] = new Object[0][];
		expected[1][2] = new Object[3][];
		
		expected[0][0][0] = new Object[1];
		expected[0][0][1] = new Object[1];
		expected[1][0][0] = new Object[2];
		expected[1][2][0] = new Object[2];
		expected[1][2][1] = new Object[3];
		expected[1][2][2] = new Object[3];
		
		expected[0][0][0][0] = new int[] {233842, 29837431, -2093428123,17128};
		expected[0][0][1][0] = null;
		expected[1][0][0][0] = Cat.createRandomCat();
		expected[1][0][0][1] = Cat.createRandomCat();
		expected[1][2][0][0] = Dog.createRandomDog();
		expected[1][2][0][1] = DogBarks.LOUD;
		expected[1][2][1][0] = new Dog[] {Dog.createRandomDog(), Dog.createRandomDog(), Dog.createRandomDog(), Dog.createRandomDog(), Dog.createRandomDog()};
		expected[1][2][1][1] = DogBarks.SILENT;
		expected[1][2][1][2] = new Animal[] {Dog.createRandomDog(), Cat.createRandomCat(), Dog.createRandomDog(), Dog.createRandomDog(), Cat.createRandomCat()};
		expected[1][2][2][0] = new int[] {1, 2, 3, 4, 5, 6, 7, 8};
		expected[1][2][2][1] = 22.4f;
		expected[1][2][2][2] = 2131.327;
		
		Object[][][][] actual = TestTools.deSerialize(Object[][][][].class, expected);
		
		Arrays.equals((int[])expected[0][0][0][0], (int[])actual[0][0][0][0]);
		assertEquals(expected[0][0][1][0], actual[0][0][1][0]);
		assertEquals(expected[1][0][0][0], actual[1][0][0][0]);
		assertEquals(expected[1][0][0][1], actual[1][0][0][1]);
		assertEquals(expected[1][2][0][0], actual[1][2][0][0]);
		assertEquals(expected[1][2][0][1], actual[1][2][0][1]);
		Arrays.equals((Dog[])expected[1][2][1][0], (Dog[])actual[1][2][1][0]);
		assertEquals(expected[1][2][1][1], actual[1][2][1][1]);
		Arrays.equals((Animal[])expected[1][2][1][2], (Animal[])actual[1][2][1][2]);
		Arrays.equals((int[])expected[1][2][2][0], (int[])actual[1][2][2][0]);
		assertEquals(expected[1][2][2][1], actual[1][2][2][1]);
		assertEquals(expected[1][2][2][2], actual[1][2][2][2]);
	}
	
	@Test
	public void serializeArrayWithRefs1() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {		
		Object[] expected = new Object[9];
		Cat cat1 = new Cat("assertion", -1);
		Cat cat2 = new Cat("assertion", -1);
		Dog dog1 = new Dog("Asserter", DogBarks.ANNOYING);
		Dog dog2 = new Dog("Asserter", DogBarks.ANNOYING);
		
		expected[0] = cat1;
		expected[1] = dog1;
		expected[2] = cat2;
		expected[3] = cat1;
		expected[4] = cat1;
		expected[5] = dog1;
		expected[6] = dog2;
		expected[7] = dog2;
		expected[8] = cat1;
		
		Object[] actual = TestTools.deSerialize(Object[].class, expected);
		
		Arrays.equals(expected, actual);
		assertSame(actual[0], actual[3]);
		assertSame(actual[0], actual[4]);
		assertSame(actual[0], actual[8]);
		assertSame(actual[1], actual[5]);
		assertSame(actual[6], actual[7]);
		assertNotSame(actual[0], actual[2]);
		assertNotSame(actual[1], actual[6]);
		assertNotSame(actual[0], actual[1]);
	}
	
	@Test
	public void serializeArrayWithRefs2() throws Exception {
		Object[] expected = new Object[3];
		expected[0] = expected;
		expected[1] = Dog.createRandomDog();
		expected[2] = Cat.createRandomCat();
		
		Object[] actual = TestTools.deSerialize(Object[].class, expected);
		
		Arrays.equals(expected, actual);
	}
}