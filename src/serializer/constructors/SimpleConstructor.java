package serializer.constructors;

import java.rmi.UnexpectedException;

import serializer.exceptions.NoValidConstructorException;

public abstract class SimpleConstructor {
	protected final Class<?> clazz;
	
	public SimpleConstructor(Class<?> clazz) {
		this.clazz = clazz;
	}
	
	public Object newInstance() throws UnexpectedException {
		try {
			return newClassInstance();
		} catch (Exception e) {
			throw new UnexpectedException("Somehow failed to create instance even though it was already confirmed to work.");
		}
	}
	protected abstract Object newClassInstance() throws Exception;
	
	public static SimpleConstructor getConstructor(Class<?> clazz) throws NoValidConstructorException {
		try {
			return new SafeConstructor(clazz);
		} catch (Exception e) {}
		try {
			return new UnsafeConstructor(clazz);
		} catch (Exception e) {}
		try {
			return new ObjectStreamConstructor(clazz);
		} catch (Exception e) {}
		
		throw new NoValidConstructorException(clazz);
	}
}
