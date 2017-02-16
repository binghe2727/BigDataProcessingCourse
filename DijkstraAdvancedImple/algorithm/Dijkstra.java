package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import graph.Edge;
import graph.Graph;
import graph.PQVertex;
import graph.Vertex;

public class Dijkstra {

	public final static int DEFAULT_DIST = 0;
	public Graph graph;
	public Vertex startVertex;
	public Vertex targetVertex;
	public int graphVertexSize;
	public Edge[] edgeTo;
	public boolean[] marked;
	PriorityQueue<PQVertex> pq;
	public HashMap<Integer, PQVertex> pqvMap;

	public Dijkstra(Vertex startVertex, Vertex targetVertex, Graph graph) {
		this.graph = graph;
		this.startVertex = startVertex;
		this.targetVertex = targetVertex;
		graphVertexSize = graph.getVerticesSize();
		edgeTo = new Edge[graphVertexSize];
		marked = new boolean[graphVertexSize];
		pq = new PriorityQueue<PQVertex>();
		pqvMap = new HashMap<>();
	}

	public double search() {

		// init start Vertex
		PQVertex startPQv = new PQVertex(startVertex, 0);
		pqvMap.put(startVertex.index, startPQv);
		pq.add(startPQv);

		while (pq.size() > 0) {
			if (relax(graph, pq.poll())) {
				break;
			}
		}
		if (pqvMap.get(targetVertex.index) == null) {
			return 0.0;
		} else {
			return pqvMap.get(targetVertex.index).distFromStart;
		}
	}

	public boolean relax(Graph graph, PQVertex curPQv) {

		marked[curPQv.index] = true;
		Vertex curVertex = graph.getVertex(curPQv.index);
		ArrayList<Edge> edges = curVertex.outEdgeList;
		for (Edge edge : edges) {
			Vertex counterpart = edge.getCounterpart(curVertex);
			if (marked[counterpart.index])
				continue;

			PQVertex pqvCp = pqvMap.get(counterpart.index);
			if (pqvCp == null) {
				pqvCp = new PQVertex(counterpart);
				pqvMap.put(counterpart.index, pqvCp);
			}

			double newDistance = curPQv.distFromStart + edge.weight;
			double oldDistance = pqvCp.distFromStart;

			if (newDistance < oldDistance || oldDistance == DEFAULT_DIST) {
				pqvCp.distFromStart = newDistance;
				edgeTo[counterpart.index] = edge;
			}

			if (counterpart.index == targetVertex.index) {
				return true;
			} else {
				if (pq.contains(pqvCp)) {
					pq.remove(pqvCp);
				}
				pq.add(pqvCp);
			}
		}
		return false;
	}

	public void printPathTo(Vertex target) {
		StringBuilder sb = new StringBuilder();
		Vertex curV = target;
		while (true) {
			sb.append(curV.index);
			if (curV.index == startVertex.index) {
				break;
			} else {
				sb.append("<-");
			}
			curV = edgeTo[curV.index].getCounterpart(curV);
		}
		System.out.println(sb.toString());
	}

}
