package serializer;

import java.io.DataInput;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

import serializer.exceptions.NoValidConstructorException;

public class DataDeserializer {
	
	@SuppressWarnings("unchecked")
	public static <T> T deserialize(Class<T> clazz, DataInput in) throws ClassNotFoundException, IOException, NoValidConstructorException, IllegalArgumentException, IllegalAccessException {
		final boolean isPrimitive = in.readBoolean();
		if (isPrimitive) {
			return (T)deserializePrimitive(clazz, in);
		}
		
		final DeserializationInfo dInfo = new DeserializationInfo(in);
		return (T)deserializeNullable(clazz, in, dInfo);
	}
	
	private static Object deserializePrimitive(Class<?> clazz, DataInput in) throws IOException {
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
	
	private static Object deserializeNullable(Class<?> clazz, DataInput in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final boolean isNull = in.readBoolean();
		if (isNull) {
			return null;
		}
		
		final boolean isReference = in.readBoolean();
		if (isReference) {
			final int refIndex = in.readShort();
			return dInfo.getObject(refIndex);
		}
		
		final VariableType varType = VariableType.getType(clazz);
		if (varType == VariableType.ENUM) {
			return deserializeEnum(clazz, in, dInfo);
		}
		else if (varType == VariableType.ARRAY) {
			return deserializeArray(clazz, in, dInfo);
		}
		else if (varType == VariableType.OBJECT) {
			return deserializeObject(in, dInfo);
		}
		else {
			throw new UnexpectedException("Expected a nullable type but got instead: " + varType.toString());
		}
	}
	
	private static Object deserializeEnum(Class<?> clazz, DataInput in, DeserializationInfo dInfo) throws IOException {		
		final int index = in.readShort();
		final Object res = clazz.getEnumConstants()[index];
		dInfo.addObject(res);
		return res;
	}
	
	private static Object deserializeArray(Class<?> clazz, DataInput in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {		
		final VariableType varType = VariableType.getType(clazz.getComponentType());
		switch (varType) {
			case PRIMITIVE:
				return deserializePrimitiveArray(clazz, in, dInfo);
			case ARRAY:
			case ENUM:
			case OBJECT:
				return deserializeNullableArray(in, dInfo, varType);
			default:
				throw new UnexpectedException("The VariableType did not match any of the possible types. Type: " + varType.name());
		}
	}
	
	private static Object deserializePrimitiveArray(Class<?> clazz, DataInput in, DeserializationInfo dInfo) throws IOException {
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
	
	private static Object deserializeNullableArray(DataInput in, DeserializationInfo dInfo, VariableType varType) throws IllegalArgumentException, IllegalAccessException, ClassNotFoundException, IOException, NoValidConstructorException {
		final Class<?> clazz = dInfo.getClass(in.readShort());
		final int length = in.readInt();
		final Object[] data = (Object[]) Array.newInstance(clazz, length);
		dInfo.addObject(data);
		for (int i = 0; i < data.length; i++) {
			data[i] = deserializeNullable(clazz.getComponentType(), in, dInfo);
		}
		return data;
	}
	
	private static Object deserializeObject(DataInput in, DeserializationInfo dInfo) throws IOException, IllegalArgumentException, IllegalAccessException, ClassNotFoundException, NoValidConstructorException {
		final Class<?> clazz = dInfo.getClass(in.readShort());
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
