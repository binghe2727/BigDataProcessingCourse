package graph;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex> {
	public int index;
	public ArrayList<Edge> outEdgeList = new ArrayList<>();

	public Vertex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return index + ": " + outEdgeList;
	}

	@Override
	public int compareTo(Vertex o) {
		if (o.index > this.index) {
			return -1;
		} else if (o.index == this.index) {
			return 0;
		} else {
			return 1;
		}
	}
}
