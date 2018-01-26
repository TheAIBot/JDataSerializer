package testClasses;

import java.util.ArrayList;

public class GraphNode<T> {
	public final ArrayList<GraphNode<?>> edges = new ArrayList<GraphNode<?>>();
	private final GraphValue<T> nodeValue;
	
	public GraphNode(T nodeValue) {
		this.nodeValue = new GraphValue<T>();
		this.nodeValue.value = nodeValue;
	}
	
	private class GraphValue<T> {
		private T value;
	}
}
