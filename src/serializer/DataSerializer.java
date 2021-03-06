package serializer;

import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.rmi.UnexpectedException;
import java.util.ArrayList;

import tools.DebugDataOutputStream;

public class DataSerializer {	
	
	public static <T> void serialize(T obj, DataOutput out) throws IOException, IllegalArgumentException, IllegalAccessException {
		final VariableType varType = VariableType.getType(obj.getClass());
		if (varType == VariableType.PRIMITIVE) {
			out.writeBoolean(true);
			serializePrimitive(obj, obj.getClass(), out);
			return;
		}
		out.writeBoolean(false);
		
		SerializationInfo sInfo = new SerializationInfo();
		
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			try (DataOutputStream objectDataOut = new DataOutputStream(stream)) {
				serializeNullable(obj, objectDataOut, sInfo);
				
				sInfo.serialize(out);
				out.write(stream.toByteArray());
			}
		}
	}
	
	static <T> void serializeTest(T obj, DebugDataOutputStream out) throws IOException, IllegalArgumentException, IllegalAccessException {
		final VariableType varType = VariableType.getType(obj.getClass());
		if (varType == VariableType.PRIMITIVE) {
			out.writeBoolean(true);
			serializePrimitive(obj, obj.getClass(), out);
			return;
		}
		out.writeBoolean(false);
		
		SerializationInfo sInfo = new SerializationInfo();
		
		try (ByteArrayOutputStream stream = new ByteArrayOutputStream()) {
			try (DebugDataOutputStream objectDataOut = new DebugDataOutputStream(stream)) {
				serializeNullable(obj, objectDataOut, sInfo);
				
				sInfo.serialize(out);
				out.write(stream.toByteArray());
				out.addDataAsString(objectDataOut.dataAsString());
			}
		}
	}
	
	private static void serializePrimitive(Object obj, Class<?> clazz, DataOutput out) throws IOException {
		//if the object is a primitive type then it can
		//be written directly to the stream
		if (clazz == byte.class) {
			out.writeByte((int)(byte) obj);
		}
		else if (clazz == short.class) {
			out.writeShort((int)(short) obj);
		}
		else if (clazz == int.class) {
			out.writeInt((int) obj);
		}
		else if (clazz == long.class) {
			out.writeLong((long) obj);
		}
		else if (clazz == float.class) {
			out.writeFloat((float) obj);
		}
		else if (clazz == double.class) {
			out.writeDouble((double) obj);
		}
		else if (clazz == boolean.class) {
			out.writeBoolean((boolean) obj);
		}
		else if (clazz == char.class) {
			out.writeChar((int)(char) obj);
		}
		else {
			throw new UnexpectedException("Tried to serialize as a primitive but class was not a primitive. Class: " + obj.getClass().getName());
		}
	}
	
	private static void  serializeNullable(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException, IllegalArgumentException, IllegalAccessException {
		if (obj == null) {
			out.writeByte(DataType.Null.ordinal());
			return;
		}
		
		if (sInfo.hasSeenObjectBefore(obj)) {
			out.writeByte(DataType.Ref.ordinal());
			out.writeShort(sInfo.getObjectIndex(obj));
			return;
		}
		out.writeByte(DataType.Unique.ordinal());
		sInfo.addObject(obj);
		
		out.writeShort(sInfo.getClassNameIndex(obj.getClass()));
		final VariableType varType = VariableType.getType(obj.getClass());
		if (varType == VariableType.ENUM) {
			serializeEnum(obj, out, sInfo);
		}
		else if (varType == VariableType.ARRAY) {
			serializeArray(obj, out, sInfo);
		}
		else if (varType == VariableType.OBJECT) {
			serializeObject(obj, out, sInfo);
		}
		else {
			throw new UnexpectedException("Expected a nullable type but got instead: " + varType.toString());
		}
	}
	
	private static void serializeEnum(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException {
		out.writeShort(((Enum)obj).ordinal());
	}
	
	private static void serializeArray(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException, IllegalArgumentException, IllegalAccessException {		
		final VariableType varType = VariableType.getType(obj.getClass().getComponentType());
		switch (varType) {
			case PRIMITIVE:
				serializePrimitiveArray(obj, out, sInfo);
				break;
			case ARRAY:
			case ENUM:
			case OBJECT:
				serializeNullableArray(obj, out, sInfo);
				break;
			default:
				throw new UnexpectedException("The VariableType did not match any of the possible types. Type: " + varType.name());
		}
	}
		
	private static void serializePrimitiveArray(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException {
		if (obj instanceof byte[]) {
			final byte[] array = (byte[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeByte(array[i]);
			}
		}
		else if (obj instanceof short[]) {
			final short[] array = (short[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeShort(array[i]);
			}
		}
		else if (obj instanceof int[]) {
			final int[] array = (int[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeInt(array[i]);
			}
		}
		else if (obj instanceof long[]) {
			final long[] array = (long[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeLong(array[i]);
			}
		}
		else if (obj instanceof float[]) {
			final float[] array = (float[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeFloat(array[i]);
			}
		}
		else if (obj instanceof double[]) {
			final double[] array = (double[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeDouble(array[i]);
			}
		}
		else if (obj instanceof boolean[]) {
			final boolean[] array = (boolean[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeBoolean(array[i]);
			}
		}
		else if (obj instanceof char[]) {
			final char[] array = (char[])obj;
			out.writeInt(array.length);
			for (int i = 0; i < array.length; i++) {
				out.writeChar(array[i]);
			}
		}
		else {
			throw new UnexpectedException("Expected the object to be a primitive array bit it was instead type: " + obj.getClass().getName());
		}
	}
	
	private static void serializeNullableArray(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException, IllegalArgumentException, IllegalAccessException {
		final Object[] array = (Object[])obj;
		out.writeInt(array.length);
		out.writeByte(getArrayDimensions(obj));
		for (int i = 0; i < array.length; i++) {
			final Object arrayObj = array[i];
			serializeNullable(arrayObj, out, sInfo);
		}
	}
	
	private static int getArrayDimensions(Object array) {
		int dimensions = 0;
		Class<?> arrayClass = array.getClass();
		while (arrayClass.isArray()) {
			dimensions++;
			arrayClass = arrayClass.getComponentType();
		}
		return dimensions;
	}
	
	private static void serializeObject(Object obj, DataOutput out, SerializationInfo sInfo) throws IOException, IllegalArgumentException, IllegalAccessException {
		//serialize all the fields in order
		final ArrayList<Field> fields = SerializationCache.getClassFields(obj.getClass());
		for (Field field : fields) {
			final boolean isAccessible = field.isAccessible();
			field.setAccessible(true);
			
			final Object fieldObject = field.get(obj);
			final VariableType varType = VariableType.getType(field.getType());
			if (varType == VariableType.PRIMITIVE) {
				serializePrimitive(fieldObject, field.getType(), out);
			}
			else {
				serializeNullable(fieldObject, out, sInfo);
			}
			
			field.setAccessible(isAccessible);
		}
	}
}