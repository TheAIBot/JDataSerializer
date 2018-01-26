package serializer;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SerializationCache {
	private static HashMap<String, ArrayList<Field>> classSerializerInfo = new HashMap<>();
	
	static ArrayList<Field> getClassFields(Class<?> clazz) {
		//if it's the first time this type has been serialized
		//then create the information required to serialize it.
		//The information is then stored so it doesn't have to be created twice
		if (!classSerializerInfo.containsKey(clazz.getName())) {
			final ArrayList<Field> fields = createSerializationInfo(clazz);
			classSerializerInfo.put(clazz.getName(), fields);
		}
		return classSerializerInfo.get(clazz.getName());
	}
	
	private static ArrayList<Field> createSerializationInfo(Class<?> type) {
		final ArrayList<Field> fields = getAllFieldsFromType(type);
		//the fields need to be sorted because they have to
		//be in the same order when de/serializing
		sortFields(fields);
		
		return fields;
	}
	
	private static ArrayList<Field> getAllFieldsFromType(Class<?> type) {
		final ArrayList<Field> fields = new ArrayList<Field>();
		//get fields from class. This doesn't get fields from super classes
		addFieldsToList(fields, type.getDeclaredFields(), type);
		
		//go through each super class and get fields from them as well
		Class<?> current = type.getSuperclass();
		while (current != null) {
			addFieldsToList(fields, current.getDeclaredFields(), type);
			current = current.getSuperclass();
		}
		
		return fields;
	}
	
	private static void addFieldsToList(ArrayList<Field> fieldsList, Field[] newFields, Class<?> type) {
		for (Field field : newFields) {
			//don't add transient fields as transient means the field
			//shouldn't be serialized.
			//The this field should also not be serialized
			//as that's the pointer to the class itself
			if (!Modifier.isTransient(field.getModifiers()) &&
				!(Modifier.isFinal(field.getModifiers()) && Modifier.isStatic(field.getModifiers()))) {
				fieldsList.add(field);	
			}
		}
	}
	
	private static void sortFields(ArrayList<Field> fields) {
		Collections.sort(fields, (a,b) -> a.getName().compareTo(b.getName()));
	}

}
