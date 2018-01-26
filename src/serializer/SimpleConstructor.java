package serializer;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.rmi.UnexpectedException;
import java.util.Arrays;

import serializer.exceptions.NoValidConstructorException;

class SimpleConstructor {
	private final Object[] arguments;
	private final Constructor<?> workingConstructor;
	
	private static byte    BYTE_DEFAULT_VALUE;
	private static short   SHORT_DEFAULT_VALUE;
	private static int     INT_DEFAULT_VALUE;
	private static long    LONG_DEFAULT_VALUE;
	private static float   FLOAT_DEFAULT_VALUE;
	private static double  DOUBLE_DEFAULT_VALUE;
	private static boolean BOOLEAN_DEFAULT_VALUE;
	private static char    CHAR_DEFAULT_VALUE;
	
	public SimpleConstructor(Class<?> clazz) throws NoValidConstructorException {
		//maybe flawed logic but get all constructors and sort by arument count.
		//I assume that class instantiation success increases with less constructor arguments.
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Arrays.sort(constructors, (a, b) -> a.getGenericParameterTypes().length - b.getGenericParameterTypes().length);
		
		Constructor<?> canCreateInstance = null;
		Object[] constructorArguments = null;
		for (Constructor<?> constructor : constructors) {
			try {
				constructorArguments = getConstructorArguments(constructor);
				constructor.newInstance(constructorArguments);
				//if he code gets to this line, then it means that the constructor
				//was successfully able to create an instance of the class
				canCreateInstance = constructor;
				break;
			} catch (Exception e) {	}
		}
		
		//if there is no way to create the class then the deserializer can't function
		if (canCreateInstance == null) {
			throw new NoValidConstructorException(clazz);
		}
		
		this.arguments = constructorArguments;
		this.workingConstructor = canCreateInstance;
	}
	
	private Object[] getConstructorArguments(Constructor<?> constructor) {
		final Parameter[] constructorArgumentTypes = constructor.getParameters();
		final Object[] constructorArguments = new Object[constructorArgumentTypes.length];
		for (int i = 0; i < constructorArguments.length; i++) {
			final Parameter argumentType = constructorArgumentTypes[i];
			constructorArguments[i] = getDefaultValue(argumentType.getType());
		}
		
		return constructorArguments;
	}
	
	private Object getDefaultValue(Class<?> type) {
		if (type == Byte.class ||
			type == byte.class) {
				return BYTE_DEFAULT_VALUE;
		}
		else if (type == Short.class ||
				 type == short.class) {
			return SHORT_DEFAULT_VALUE;
		}
		else if (type == Integer.class ||
				 type == int.class) {
			return INT_DEFAULT_VALUE;
		}
		else if (type == Long.class ||
				 type == long.class) {
			return LONG_DEFAULT_VALUE;
		}
		else if (type == Float.class ||
				 type == float.class) {
			return FLOAT_DEFAULT_VALUE;
		}
		else if (type == Double.class ||
				 type == double.class) {
			return DOUBLE_DEFAULT_VALUE;
		}
		else if (type == Boolean.class ||
				 type == boolean.class) {
			return BOOLEAN_DEFAULT_VALUE;
		}
		else if (type == Character.class ||
				 type == char.class) {
			return CHAR_DEFAULT_VALUE;
		}
		else {
			return null;
		}
	}

	public Object newInstance() throws UnexpectedException {
		try {
			return workingConstructor.newInstance(arguments);
		} catch (Exception e) {
			throw new UnexpectedException("Somehow failed to create instance even though it was already confirmed to work.");
		}
	}
}
