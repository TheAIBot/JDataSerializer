package serializer.exceptions;

public class DuplicateAliasException extends Exception {
	
	public DuplicateAliasException(String message) {
		super(message);
	}
	
	public static String duplicateType(Class<?> type, String alias) {
		return "The type " + type.getName() + " already has the alias " + alias + 
				".\n One type is not allowed to have two aliases.";
	}
	
	public static String duplicateAlias(String alias, Class<?> type) {
		return "The alias " + alias + " already has the type" + type.getName() + 
			   ".\n One alias is not allowed to have two types.";
	}
}
