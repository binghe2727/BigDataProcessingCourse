package graph;

public class PQVertex implements Comparable<PQVertex> {
	public int index;
	public double distFromStart;

	public PQVertex(Vertex v) {
		this.index = v.index;
	}

	public PQVertex(Vertex v, double distFromStart) {
		this.index = v.index;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return index + ": " + distFromStart;
	}

	@Override
	public int compareTo(PQVertex o) {
		if (o.distFromStart > this.distFromStart) {
			return -1;
		} else if (o.distFromStart == this.distFromStart) {
			return 0;
		} else {
			return 1;
		}
	}
}
