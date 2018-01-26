package serializer;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;

import serializer.exceptions.NoValidConstructorException;

class DeserializationInfo {
	private final HashMap<Class<?>, SimpleConstructor> classConstructors = new HashMap<>();
	private final ArrayList<Object> deserializedObjects = new ArrayList<Object>();
	private final Class<?>[] classes;
	
	public DeserializationInfo(DataInputStream in) throws IOException, ClassNotFoundException, NoValidConstructorException {
		final int length = in.readInt();
		
		this.classes = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			final String classname = in.readUTF();
			final Class<?> aliasClass = DeSerializerData.getDeserializerAlias(classname);
			classes[i] = aliasClass;
			classConstructors.put(aliasClass, DeSerializerData.getClassConstructor(aliasClass));
		}
	}
	
	public void addObject(Object obj) {
		deserializedObjects.add(obj);
	}
	
	public Object getObject(int index) {
		return deserializedObjects.get(index);
	}
	
	public Class<?> getClass(int index) {
		return classes[index];
	}
	
	public Object newInstance(Class<?> clazz) throws UnexpectedException, ClassNotFoundException, NoValidConstructorException {
		if (!classConstructors.containsKey(clazz)) {
			classConstructors.put(clazz, DeSerializerData.getClassConstructor(clazz));
		}
		return classConstructors.get(clazz).newInstance();
	}
}
