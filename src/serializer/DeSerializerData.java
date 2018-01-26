package serializer;

import java.util.HashMap;

import serializer.exceptions.DuplicateAliasException;
import serializer.exceptions.NoValidConstructorException;

public class DeSerializerData {
	private static final HashMap<Class<?>, String> serializerAliases = new HashMap<>();
	private static final HashMap<String, Class<?>> deserializerAliases = new HashMap<>();
	
	private static final HashMap<Class<?>, SimpleConstructor> classConstructors = new HashMap<>();
	
	public static synchronized void addAlias(Class<?> clazz, String alias) throws DuplicateAliasException {
		//one type can't have two aliases associated with it
		if (serializerAliases.containsKey(clazz)) {
			final String duplicateAlias = serializerAliases.get(clazz);
			throw new DuplicateAliasException(DuplicateAliasException.duplicateType(clazz, duplicateAlias));
		}
		
		//one alias can't have two types associated with it
		if (deserializerAliases.containsKey(alias)) {
			final Class<?> duplicateType = deserializerAliases.get(alias);
			throw new DuplicateAliasException(DuplicateAliasException.duplicateAlias(alias, duplicateType));
		}
		
		serializerAliases.put(clazz, alias);
		deserializerAliases.put(alias, clazz);
	}
	
	static String getSerializerAlias(Class<?> clazz) {
		if (serializerAliases.containsKey(clazz)) {
			return serializerAliases.get(clazz);
		}
		return clazz.getCanonicalName();
	}
	
	static Class<?> getDeserializerAlias(String classname) throws ClassNotFoundException {
		if (deserializerAliases.containsKey(classname)) {
			return deserializerAliases.get(classname);
		}
		return Class.forName(classname);
	}

	public static synchronized SimpleConstructor getClassConstructor(Class<?> clazz) throws ClassNotFoundException, NoValidConstructorException {		
		//constructors are stored so they are easily retrieved after the first call
		//because creating a constructor may be expensive
		if (classConstructors.containsKey(clazz)) {
			return classConstructors.get(clazz);
		}
		
		final SimpleConstructor simpleConstructor = new SimpleConstructor(clazz);
		classConstructors.put(clazz, simpleConstructor);
		return simpleConstructor;

	}
}
