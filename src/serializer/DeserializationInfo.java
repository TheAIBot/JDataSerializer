package serializer;

import java.io.DataInput;
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
	private final String[] classnames;
	private final Class<?>[] classes;
	
	public DeserializationInfo(DataInput in) throws IOException {
		final int length = in.readInt();
		
		this.classnames = new String[length];
		this.classes = new Class<?>[length];
		for (int i = 0; i < length; i++) {
			classnames[i] = in.readUTF();
		}
	}
	
	public void addObject(Object obj) {
		deserializedObjects.add(obj);
	}
	
	public Object getObject(int index) {
		return deserializedObjects.get(index);
	}
	
	public Class<?> getClass(int index) throws ClassNotFoundException {
		if (classes[index] == null) {
			classes[index] = Class.forName(classnames[index]);
		}
		return classes[index];
	}
	
	public Object newInstance(Class<?> clazz) throws UnexpectedException, ClassNotFoundException, NoValidConstructorException {
		if (!classConstructors.containsKey(clazz)) {
			classConstructors.put(clazz, DeSerializerData.getClassConstructor(clazz));
		}
		return classConstructors.get(clazz).newInstance();
	}
}
