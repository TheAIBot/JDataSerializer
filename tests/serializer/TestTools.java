package serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;

import serializer.DataDeserializer;
import serializer.DataSerializer;
import serializer.exceptions.NoValidConstructorException;
import tools.DebugDataInputStream;
import tools.DebugDataOutputStream;

public class TestTools {
	private final static Random random = new Random();
	
	public static <T> T deSerialize(Class<T> type, T toSerialize) throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, IOException, NoValidConstructorException {
		byte[] data = null;
		
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			try (DebugDataOutputStream out = new DebugDataOutputStream(stream)) {
				
				DataSerializer.serializeTest(toSerialize, out);
				out.print();
				data = stream.toByteArray();
			}
		}
		
		try (ByteArrayInputStream stream = new ByteArrayInputStream(data)) {
			try (DebugDataInputStream in = new DebugDataInputStream(stream)) {				
				T result = DataDeserializer.deserialize(type, in);
				in.print();
				return result;
			}
		}
	}
}
