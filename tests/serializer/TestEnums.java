package serializer;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import serializer.exceptions.NoValidConstructorException;
import testClasses.DogBarks;

public class TestEnums {
	@Test
	public void serializeEnum() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		DogBarks expected = DogBarks.CUTE;
		DogBarks actual = TestTools.deSerialize(DogBarks.class, expected);
		assertEquals(expected, actual);
	}
}
