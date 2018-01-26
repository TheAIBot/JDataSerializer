package serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import serializer.exceptions.NoValidConstructorException;

public class TestTools {
	private final static Random random = new Random();
	
	public static <T> T deSerialize(Class<T> type, T toSerialize) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		byte[] data = null;
		
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			try (DataOutputStream out = new DataOutputStream(stream)) {
				
				DataSerializer.serialize(toSerialize, out);
				data = stream.toByteArray();
			}
		}
		
		try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
			try (DataInputStream in = new DataInputStream(stream)) {				
				return DataDeserializer.deserialize(type, in);
			}
		}
	}
}
