package algorithm;

import java.util.*;

import graph.Edge;
import graph.Graph;
import graph.PQVertex;
import graph.Vertex;
import util.Util;

public class PLL {

	public static final double NO_LABEL_DISTANCE = -1;
	Graph graph;
	ArrayList<Vertex> orderList;
	HashMap<Integer, Integer> orderMap = new HashMap<>();

	// <index, <index,distance> >
	public HashMap<Integer, HashMap<Integer, Double>> labelMap = new HashMap<>();

	public PLL(Graph graph) {
		this.graph = graph;
		orderList = ordering();
	}

	public ArrayList<Vertex> ordering() {

		ArrayList<Vertex> orderList = new ArrayList<>();
		System.out.println(Util.printTimeCost("0"));// cs
		for (int i = 0; i < graph.nodesArr.length; i++) {
			if (graph.nodesArr[i] != null) {
				orderList.add(graph.nodesArr[i]);
			}
		}
		Collections.sort(orderList, new Comparator<Vertex>() {
			@Override
			public int compare(Vertex o1, Vertex o2) {
				if (o1 == null) {
					return 1;
				} else if (o2 == null) {
					return -1;
				}
				if (o1.outEdgeList.size() > o2.outEdgeList.size()) {
					return -1;
				} else if (o1.outEdgeList.size() < o2.outEdgeList.size()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i) != null) {
				orderMap.put(orderList.get(i).index, i);
			}
		}
		return orderList;

	}

	public void labeling() {
		for (int i = 0; i < orderList.size(); i++) {
			// get the highest order vertex.
			Vertex curSource = orderList.get(i);
			if (curSource == null)
				continue;
			PriorityQueue<PQVertex> pq = new PriorityQueue<>();
			HashMap<Integer, PQVertex> pqvMap = new HashMap<>();
			PQVertex pqvU = new PQVertex(curSource);

			pqvMap.put(pqvU.index, pqvU);
			pq.add(new PQVertex(curSource));
			while (!pq.isEmpty()) {
				PQVertex pqv = pq.poll();
				Vertex v = graph.getVertex(pqv.index);
				ArrayList<Edge> edges = v.outEdgeList;
				for (Edge edge : edges) {
					Vertex cpv = edge.getCounterpart(v);
					if (orderMap.get(cpv.index) < orderMap.get(curSource.index)) {
						// skip higher order vertex
						continue;
					}
					PQVertex cpPQv = new PQVertex(cpv);
					// update dist
					cpPQv.distFromStart = pqv.distFromStart + edge.weight;
					double labelDist = getLabelDistance(curSource, cpv);
					if (labelDist < cpPQv.distFromStart && labelDist != NO_LABEL_DISTANCE)
						// real distance larger than labeling distance
						// so PRUNED and STOP EXPAND from this vertex
						continue;

					pqvMap.put(cpPQv.index, cpPQv);
					pq.add(cpPQv);
					// update label
					HashMap<Integer, Double> labels = labelMap.get(cpPQv.index);
					labels.put(curSource.index, cpPQv.distFromStart);
				}
			}

			Util.printTimeCost(
					i + 1 + "/" + orderList.size() + "\t" + (int) ((i + 1.0) / orderList.size() * 100) + "%...");// cs
		}

	}

	public double search(Vertex s, Vertex t) {
		return getLabelDistance(s, t);
	}

	/**
	 * @WARNING very time consuming
	 */
	public void printLabels() {
		for (int i = 0; i < orderList.size(); i++) {
			if (orderList.get(i) == null)
				continue;
			System.out.println(orderList.get(i).index + ":\t" + labelMap.get(orderList.get(i).index));
		}
	}

	private HashMap<Integer, Double> initLabelForSingleVertex(int index) {
		HashMap<Integer, Double> labels = new HashMap<>();
		labels.put(index, 0.0);
		labelMap.put(index, labels);
		return labels;
	}

	private double getLabelDistance(Vertex source, Vertex current) {
		HashMap<Integer, Double> sLabel = labelMap.get(source.index);
		HashMap<Integer, Double> curLabel = labelMap.get(current.index);

		if (sLabel == null) {
			sLabel = initLabelForSingleVertex(source.index);
		}
		if (curLabel == null) {
			curLabel = initLabelForSingleVertex(current.index);
		}
		double distance = NO_LABEL_DISTANCE;
		for (Integer labelInS : sLabel.keySet()) {
			if (curLabel.get(labelInS) == null)
				continue;
			double cDistance = sLabel.get(labelInS) + curLabel.get(labelInS);
			if (distance == NO_LABEL_DISTANCE || cDistance < distance) {
				distance = cDistance;
			}
		}
		return distance;
	}
}
