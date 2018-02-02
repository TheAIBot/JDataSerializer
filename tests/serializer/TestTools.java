package serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.Random;

import serializer.DataDeserializer;
import serializer.DataSerializer;
import serializer.exceptions.NoValidConstructorException;
import tools.DebugDataInputStream;
import tools.DebugDataOutputStream;

public class TestTools {
	private final static Random random = new Random(27);
	private final static String characters = "0123456789abcdefghijklmnopqrstuwxyzABCDEFGHIJKLMNOPQRSTUWXYZ ";
	
	public static <T> T deSerialize(Class<T> type, T toSerialize) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		byte[] data = null;
		
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			try (DebugDataOutputStream out = new DebugDataOutputStream(stream)) {
				
				DataSerializer.serializeTest(toSerialize, out);
				out.print();
				data = stream.toByteArray();
			}
		}
		System.out.println(data.length);
		try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
			try (DebugDataInputStream in = new DebugDataInputStream(stream)) {				
				T result = DataDeserializer.deserialize(type, in);
				in.print();
				return result;
			}
		}
	}
	
	public static String randomString(int length) {
		String randomString = "";
		for (int i = 0; i < length; i++) {
			randomString += characters.charAt(random.nextInt(characters.length()));
		}
		return randomString;
	}
	
	public static int randomInt() {
		return random.nextInt();
	}
	
	public static <T> T randomEnum(Class<T> clazz) {
		T[] values = clazz.getEnumConstants();
		return (T) values[random.nextInt(values.length)];
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T[] createRandomEnumArray(Class<T> clazz, int length) {
		final T[] array = (T[]) Array.newInstance(clazz, length);
		for (int i = 0; i < array.length; i++) {
			array[i] = randomEnum(clazz);
		}
		return array;
	}
}
