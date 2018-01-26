package serializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

import serializer.exceptions.NoValidConstructorException;

public class DataDeserializer {
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> clazz, DataInputStream in) throws ClassNotFoundException, IOException, NoValidConstructorException, IllegalArgumentException, IllegalAccessException {
		final boolean isPrimitive = in.readBoolean();
		if (isPrimitive) {
			return (T)deserializePrimitive(clazz, in);
		}
		
		final DeserializationInfo dInfo = new DeserializationInfo(in);
		return (T)deserializeNullable(clazz, in, dInfo);
	}
	
	private static Object deserializePrimitive(Class<?> clazz, DataInputStream in) throws IOException {
		if (clazz == byte.class) {
			return in.readByte();
		}
		else if (clazz == short.class) {
			return in.readShort();
		}
		else if (clazz == int.class) {
			return in.readInt();
		}
		else if (clazz == long.class) {
			return in.readLong();
		}
		else if (clazz == float.class) {
			return in.readFloat();
		}
		else if (clazz == double.class) {
			return in.readDouble();
		}
		else if (clazz == boolean.class) {
			return in.readBoolean();
		}
		else if (clazz == char.class) {
			return in.readChar();
		}
		else {
			throw new UnexpectedException("Tried to deserialize as a primitive but class was not a primitive. Class: " + clazz.getName());
		}
	}
	
	private static Object deserializeNullable(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final VariableType varType = VariableType.getType(clazz);
		if (varType == VariableType.ENUM) {
			return deserializeEnum(clazz, in);
		}
		else if (varType == VariableType.ARRAY) {
			return deserializeArray(clazz, in, dInfo);
		}
		else if (varType == VariableType.OBJECT) {
			return deserializeObject(clazz, in, dInfo);
		}
		else {
			throw new UnexpectedException("Expected a nullable type but got instead: " + varType.toString());
		}
	}
	
	private static Object deserializeEnum(Class<?> clazz, DataInputStream in) throws IOException {
		final boolean isNull = in.readBoolean();
		if (isNull) {
			return null;
		}
		
		final String enumConstantName = in.readUTF();
		return Enum.valueOf((Class<? extends Enum>) clazz, enumConstantName);
	}
	
	private static Object deserializeArray(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final boolean isNull = in.readBoolean();
		if (isNull) {
			return null;
		}
		
		final boolean isReference = in.readBoolean();
		if (isReference) {
			final int refIndex = in.readShort();
			return dInfo.getObject(refIndex);
		}
		
		final VariableType varType = VariableType.getType(clazz.getComponentType());
		if (varType == VariableType.PRIMITIVE) {
			return deserializePrimitiveArray(clazz, in, dInfo);
		}
		else if (varType == VariableType.ARRAY) {
			return deserializeArrayArray(clazz, in, dInfo);
		}
		else if (varType == VariableType.ENUM) {
			return deserializeEnumArray(clazz, in, dInfo);
		}
		else if (varType == VariableType.OBJECT) {
			return deserializeObjectArray(clazz, in, dInfo);
		}
		else {
			throw new UnexpectedException("The VariableType did not match any of the possible types. Type: " + varType.name());
		}
	}
	
	private static Object deserializePrimitiveArray(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException {
		if (clazz == byte[].class) {
			final int length = in.readInt();
			final byte[] data = new byte[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readByte();
			}
			return data;
		}
		else if (clazz == short[].class) {
			final int length = in.readInt();
			final short[] data = new short[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readShort();
			}
			return data;
		}
		else if (clazz == int[].class) {
			final int length = in.readInt();
			final int[] data = new int[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readInt();
			}
			return data;
		}
		else if (clazz == long[].class) {
			final int length = in.readInt();
			final long[] data = new long[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readLong();
			}
			return data;
		}
		else if (clazz == float[].class) {
			final int length = in.readInt();
			final float[] data = new float[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readFloat();
			}
			return data;
		}
		else if (clazz == double[].class) {
			final int length = in.readInt();
			final double[] data = new double[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readDouble();
			}
			return data;
		}
		else if (clazz == boolean[].class) {
			final int length = in.readInt();
			final boolean[] data = new boolean[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readBoolean();
			}
			return data;
		}
		else if (clazz == char[].class) {
			final int length = in.readInt();
			final char[] data = new char[length];
			dInfo.addObject(data);
			for (int i = 0; i < data.length; i++) {
				data[i] = in.readChar();
			}
			return data;
		}
		else {
			throw new UnexpectedException("Expected the type to be a primitive array bit it was instead type: " + clazz.getName());
		}
	}
	
	private static Object deserializeArrayArray(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final int length = in.readInt();
		final Object[] data = (Object[]) Array.newInstance(clazz, length);
		dInfo.addObject(data);
		for (int i = 0; i < data.length; i++) {
			final boolean isNull = in.readBoolean();
			if (isNull) {
				continue;
			}
			
			final int classIndex = in.readShort();
			final Class<?> type = dInfo.getClass(classIndex);
			data[i] = deserializeArray(type, in, dInfo);
		}
		return data;
	}
	
	private static Object deserializeEnumArray(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException {		
		final int length = in.readInt();
		final Object[] data = (Object[]) Array.newInstance(clazz, length);
		dInfo.addObject(data);
		for (int i = 0; i < data.length; i++) {
			final boolean isNull = in.readBoolean();
			if (isNull) {
				continue;
			}
			
			final int classIndex = in.readShort();
			final Class<?> type = dInfo.getClass(classIndex);
			data[i] = deserializeEnum(type, in);
		}
		return data;
	}
	
	private static Object deserializeObjectArray(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final int length = in.readInt();
		final Object[] data = (Object[]) Array.newInstance(clazz, length);
		dInfo.addObject(data);
		for (int i = 0; i < data.length; i++) {
			final boolean isNull = in.readBoolean();
			if (isNull) {
				continue;
			}
			
			final int classIndex = in.readShort();
			final Class<?> type = dInfo.getClass(classIndex);
			data[i] = deserializeObject(type, in, dInfo);
		}
		return data;
	}
	
	private static Object deserializeObject(Class<?> clazz, DataInputStream in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final boolean isNull = in.readBoolean();
		if (isNull) {
			return null;
		}
		
		final boolean isReference = in.readBoolean();
		if (isReference) {
			final int refIndex = in.readShort();
			return dInfo.getObject(refIndex);
		}
		
		final Object obj = dInfo.newInstance(clazz);
		dInfo.addObject(obj);
		//deserialize all the fields in order
		final ArrayList<Field> fields = SerializationCache.getClassFields(clazz);
		for (Field field : fields) {
			final boolean isAccessible = field.isAccessible();
			field.setAccessible(true);
			
			VariableType varType = VariableType.getType(field.getType());
			if (varType == VariableType.PRIMITIVE) {
				field.set(obj, deserializePrimitive(field.getType(), in));
			}
			else {
				field.set(obj, deserializeNullable(field.getType(), in, dInfo));
			}
			
			field.setAccessible(isAccessible);
		}
		
		return obj;
	}
}
