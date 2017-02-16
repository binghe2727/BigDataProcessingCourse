package graph;

public class Graph {
	public Vertex[] nodesArr;

	public int getVerticesSize() {
		return nodesArr.length;
	}

	public Graph(int vNumbs) {
		nodesArr = new Vertex[vNumbs];
	}

	public Vertex getVertex(int i) {
		return nodesArr[i];
	}

	@Override
	public String toString() {
		for (Vertex v : nodesArr) {
			if (v != null)
				System.out.println(v);
		}
		return "";
	}
}
