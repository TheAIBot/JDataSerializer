package serializer;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import serializer.exceptions.NoValidConstructorException;
import testClasses.*;

public class TestArrays {
	@Test
	public void serializePrimitiveArrays() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
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
	public void serializeArray2D() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		Object[][] expected = new Object[3][];
		expected[0] = new Object[1];
		expected[0][0] = new Byte[] {3, -7, 23, -56};
		expected[1] = new Object[2];
		expected[1][0] = new Cat("steve", 92);
		expected[1][1] = new int[] {1, 2, 9, 4, 5, 6, 7};
		expected[2] = new Object[3];
		expected[2][1] = new Dog("dogberg", DogBarks.LOUD);
		
		final Object[][] actual = TestTools.deSerialize(Object[][].class, expected);
		
		assertEquals(expected.length, actual.length);
		assertEquals(expected[0].length, actual[0].length);
		assertEquals(expected[1].length, actual[1].length);
		assertEquals(expected[2].length, actual[2].length);
		
		Arrays.equals((Byte[])expected[0][0], (Byte[])actual[0][0]);
		assertTrue(((Cat)expected[1][0]).equals((Cat)actual[1][0]));
		Arrays.equals((int[])expected[1][1], (int[])actual[1][1]);
		assertEquals(expected[2][0], actual[2][0]);
		assertTrue(((Dog)expected[2][1]).equals((Dog)actual[2][1]));
		assertEquals(expected[2][2], actual[2][2]);
	}
	
	@Test
	public void serializeArray4D() {
		
	}
	
	@Test
	public void serializeArray3DWithRef() {
		
	}
}