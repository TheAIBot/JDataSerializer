package serializer;

public class CompareRef {
	public final Object value;
	
	public CompareRef(Object value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		return value == obj;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode();
	}
}

