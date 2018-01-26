package testClasses;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

public class PrimitiveArrays {
	private final byte[]    a1;
	private final short[]   a2;
	private final int[]     a3;
	private final long[]    a4;
	private final float[]   a5;
	private final double[]  a6;
	private final boolean[] a7;
	private final char[]    a8;
	
	public PrimitiveArrays(byte[] a1, short[] a2, int[] a3, long[] a4, float[] a5, double[] a6, boolean[] a7, char[] a8) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
		this.a6 = a6;
		this.a7 = a7;
		this.a8 = a8;
	}
	
	public void assertSame(PrimitiveArrays b) {
		assertTrue(Arrays.equals(a1, b.a1));
		assertTrue(Arrays.equals(a2, b.a2));
		assertTrue(Arrays.equals(a3, b.a3));
		assertTrue(Arrays.equals(a4, b.a4));
		assertTrue(Arrays.equals(a5, b.a5));
		assertTrue(Arrays.equals(a6, b.a6));
		assertTrue(Arrays.equals(a7, b.a7));
		assertTrue(Arrays.equals(a8, b.a8));
	}
}
