package serializer;

import java.io.IOException;

import org.junit.Test;

import serializer.exceptions.NoValidConstructorException;
import testClasses.PrimitiveWrappers;
import testClasses.Primitives;

public class TestPrimitives {
	@Test
	public void testPrimitivesSerialization() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		final Primitives expected = new Primitives(Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, true, 'a');
		final Primitives actual = TestTools.deSerialize(Primitives.class, expected);
		expected.assertSame(actual);
	}
	
	@Test
	public void testPrimitiveWrappersSerialization() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		final PrimitiveWrappers expected = new PrimitiveWrappers(Byte.MAX_VALUE, Short.MAX_VALUE, Integer.MAX_VALUE, Long.MAX_VALUE, Float.MAX_VALUE, Double.MAX_VALUE, true, 'a');
		final PrimitiveWrappers actual = TestTools.deSerialize(PrimitiveWrappers.class, expected);
		expected.assertSame(actual);
	}
}
