package testClasses;

import static org.junit.Assert.assertEquals;

public class PrimitiveWrappers {	
	private final Byte    a1;
	private final Short   a2;
	private final Integer     a3;
	private final Long    a4;
	private final Float   a5;
	private final Double  a6;
	private final Boolean a7;
	private final Character    a8;
	
	public PrimitiveWrappers(Byte a1, Short a2, Integer a3, Long a4, Float a5, Double a6, Boolean a7, Character a8) {
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.a5 = a5;
		this.a6 = a6;
		this.a7 = a7;
		this.a8 = a8;
	}
	
	public void assertSame(PrimitiveWrappers b) {
		assertEquals(a1, b.a1);
		assertEquals(a2, b.a2);
		assertEquals(a3, b.a3);
		assertEquals(a4, b.a4);
		assertEquals(a5, b.a5, 0);
		assertEquals(a6, b.a6, 0);
		assertEquals(a7, b.a7);
		assertEquals(a8, b.a8);
	}
}
