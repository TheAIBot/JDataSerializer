package serializer;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

class SerializationInfo {
	private final HashMap<String, Integer> classnames  = new HashMap<String, Integer>();
	private final ArrayList<String> classnamesList = new ArrayList<String>();
	
	private final HashMap<Object, Integer> seenObjects = new HashMap<Object, Integer>();
	private final ArrayList<Object> seenObjectsList = new ArrayList<Object>();
	
	public int getClassNameIndex(Class<?> clazz) {
		final String alias = DeSerializerData.getSerializerAlias(clazz);
		
		//if already stored then return the index of where
		//it's stored
		if (classnames.containsKey(alias)) {
			return classnames.get(alias);
		}
		
		//otherwise store it and return the index
		classnamesList.add(alias);
		final int index = classnamesList.size() - 1;
		classnames.put(alias, index);
		return index; 
	}
	
	public boolean hasSeenObjectBefore(Object obj) {
		return seenObjects.containsKey(obj);
	}
	
	public void addObject(Object obj) {
		seenObjectsList.add(obj);
		seenObjects.put(obj, seenObjectsList.size() - 1);
	}
	
	public int getObjectIndex(Object obj) {
		return seenObjects.get(obj);
	}
	
	public void serialize(DataOutput out) throws IOException {
		out.writeInt(classnamesList.size());
		for (int i = 0; i < classnamesList.size(); i++) {
			out.writeUTF(classnamesList.get(i));
		}
	}
}
