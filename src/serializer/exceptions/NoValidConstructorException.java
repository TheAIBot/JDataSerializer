package serializer.exceptions;

public class NoValidConstructorException extends Exception {
	
	public NoValidConstructorException(Class<?> type) {
		super("Failed to find a constructor that could create a class of type " + type.getName() + 
				" with default arguments. The recommended way to fix is to create a default constructor for " + type.getName());
	}
	
	
}
