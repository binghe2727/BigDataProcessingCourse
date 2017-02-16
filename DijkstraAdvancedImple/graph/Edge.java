package graph;

public class Edge implements Comparable<Edge> {

	public Vertex fromVertex;
	public Vertex toVertex;
	// default weight = 1;
	public int weight = 1;

	public Edge(Vertex fromVertex, Vertex toVertex, int weight) {
		super();
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.weight = weight;
	}

	public Edge(Vertex fromVertex, Vertex toVertex) {
		super();
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
	}

	public Vertex getCounterpart(Vertex Vertex) {
		if (Vertex == null) {
			return null;
		}
		if (Vertex.equals(fromVertex)) {
			return toVertex;
		} else {
			return fromVertex;
		}
	}

	@Override
	public String toString() {
		return "Edge [from " + fromVertex.index + " to " + toVertex.index + ", weight=" + weight + "]";
	}

	@Override
	public int compareTo(Edge e) {
		if (this.weight > e.weight) {
			return 1;
		} else if (this.weight == e.weight) {
			return 0;
		} else {
			return -1;
		}
	}

}
