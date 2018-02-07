package serializer;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

public class TestObjects {
	@Test
	public void serializeArrayList() {
		final ArrayList<Integer> obj = new ArrayList<>();
		for (int i = 0; i < 15; i++) {
			obj.add(TestTools.randomInt());
			obj.add(null);
		}
		testObject(obj);
	}
	
	@Test
	public void serializeInteger() {
		testObject(new Integer(-2387));
	}
	
	@Test
	public void serializePoint() {
		testObject(new Point(32, 238940));
	}
	
	@Test
	public void serializeException() {
		testObject(new IOException("some exception error"));
	}
	
	@SuppressWarnings("unchecked")
	private <T> void testObject(T expected) {
		try {
			final T actual = TestTools.deSerialize((Class<T>)expected.getClass(), expected);
			assertEquals(expected, actual);
		} catch (Exception e) {
			assertTrue(false); // forgot the correct function name
		}
	}
}
