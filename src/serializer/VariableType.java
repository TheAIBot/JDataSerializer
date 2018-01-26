package serializer;

import java.util.HashSet;

public enum VariableType {
	PRIMITIVE,
	ARRAY,
	ENUM,
	OBJECT;
	
	private static final HashSet<Class<?>> primitiveTypes = new HashSet<Class<?>>();
	static {
		primitiveTypes.add(byte.class);
		primitiveTypes.add(short.class);
		primitiveTypes.add(int.class);
		primitiveTypes.add(long.class);
		primitiveTypes.add(float.class);
		primitiveTypes.add(double.class);
		primitiveTypes.add(boolean.class);
		primitiveTypes.add(char.class);
		/*
		primitiveTypes.add(Byte.class);
		primitiveTypes.add(Short.class);
		primitiveTypes.add(Integer.class);
		primitiveTypes.add(Long.class);
		primitiveTypes.add(Float.class);
		primitiveTypes.add(Double.class);
		primitiveTypes.add(Boolean.class);
		primitiveTypes.add(Character.class);
		*/
	}
	
	public static VariableType getType(Class<?> type) {
		if (primitiveTypes.contains(type)) {
			return VariableType.PRIMITIVE;
		}
		else if (type.isArray()) {
			return VariableType.ARRAY;
		}
		else if (type.isEnum()) {
			return VariableType.ENUM;
		}
		else {
			return VariableType.OBJECT;
		}
	}
}
