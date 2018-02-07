package testClasses;

public interface Mammal {
	public default String getA() {
		return "some default a string";
	}
	public void setA(String newA);
	
	public default String getB() {
		return "some default b string";
	}
}
