package serializer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import serializer.exceptions.NoValidConstructorException;
import testClasses.PrimitiveWrappers;
import testClasses.Primitives;

public class TestPrimitives {
	@Test
	public void serializePrimitiveClass() throws Exception {
		final Primitives expected = new Primitives(Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, true, 'a');
		final Primitives actual = TestTools.deSerialize(Primitives.class, expected);
		expected.assertSame(actual);
	}
	
	@Test
	public void serializePrimitiveWrappersClass() throws Exception {
		final PrimitiveWrappers expected = new PrimitiveWrappers(Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, true, 'a');
		final PrimitiveWrappers actual = TestTools.deSerialize(PrimitiveWrappers.class, expected);
		expected.assertSame(actual);
	}
	
	@Test
	public void serializeByte() throws Exception {
		serializePrimitive(byte.class, (byte)53);
		final Object obj = (byte)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeShort() throws Exception {
		serializePrimitive(short.class, (short)53);
		final Object obj = (short)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeInt() throws Exception {
		serializePrimitive(int.class, (int)53);
		final Object obj = (int)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeLong() throws Exception {
		serializePrimitive(long.class, (long)53);
		final Object obj = (long)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeFloat() throws Exception {
		serializePrimitive(float.class, (float)53);
		final Object obj = (float)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeDouble() throws Exception {
		serializePrimitive(double.class, (double)53);
		final Object obj = (double)53;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeBoolean() throws Exception {
		serializePrimitive(boolean.class, (boolean)true);
		final Object obj = (boolean)true;
		serializePrimitive(Object.class, obj);
	}

	@Test
	public void serializeCharacter() throws Exception {
		serializePrimitive(char.class, (char)'3');
		final Object obj = (char)'3';
		serializePrimitive(Object.class, obj);
	}
	
	private <T> void serializePrimitive(Class<T> clazz, T expected) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		final T actual = TestTools.deSerialize(clazz, expected);
		assertEquals(expected, actual);
	}
}
