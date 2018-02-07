package serializer.constructors;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class UnsafeConstructor extends SimpleConstructor {
	private static Object unsafe = null;
	private static Method allocator = null;
	
	UnsafeConstructor(Class<?> clazz) throws Exception {
		super(clazz);
		if (Modifier.isInterface(clazz.getModifiers()) ||
			Modifier.isAbstract(clazz.getModifiers())) {
			throw new Exception("Unsafe constructor can't create that class type");
		}
		if (allocator == null) {
			//apparently relying on sun is bad?
			//solution is to move the class down here instead of having it
			//in the imports so it can scrash the constructor
			//instead of the whole program
			final Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
			final Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
			
			final boolean isAccesible = unsafeField.isAccessible();
			unsafeField.setAccessible(true);
			
			UnsafeConstructor.unsafe = unsafeField.get(null);
			UnsafeConstructor.allocator = unsafeClass.getMethod("allocateInstance", Class.class);
			
			//unsafeField.setAccessible(isAccesible);
		}
		Object ss = newClassInstance();
		Object tt = ss;
		Object aa = tt;
	}

	@Override
	public Object newClassInstance() throws Exception {
		return allocator.invoke(unsafe, clazz);
	}
}
