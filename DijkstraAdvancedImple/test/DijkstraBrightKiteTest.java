package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import algorithm.Dijkstra;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import util.Util;

public class DijkstraBrightKiteTest {
	public static void main(String[] args) throws FileNotFoundException {
		Random r = new Random();
		Util.init();
		Graph graph = initBrightKite();
		Util.printTimeCost("init graph finished.");
		System.out.println("查询开始");// cs
		ArrayList<Long> costList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Vertex startVertex = graph.getVertex(r.nextInt(58227));
			Vertex targetVertex = graph.getVertex(r.nextInt(58227));
			if (targetVertex == null || startVertex == null) {
				// to tolerate index missing error
				i--;
				continue;
			}

			System.out.println("第 " + (i + 1) + "轮查询  from " + startVertex.index + " to " + targetVertex.index);// cs
			Dijkstra dijkstra = new Dijkstra(startVertex, targetVertex, graph);
			double spd = dijkstra.search();
			if (spd > 0) {
				System.out.println(" spd = " + spd);// cs
				// dijkstra.printPathTo(targetVertex);
			} else {
				System.out.println("no path");
			}
			costList.add(Util.printTimeCost("时间消耗："));
		}
		System.out.println("查询结束");// cs
		for (int i = 0; i < costList.size(); i++) {
			System.out.println(costList.get(i));
		}
	}

	public static Graph initBrightKite() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./src/loc-brightkite_edges.txt"));
		// Scanner sc = new Scanner(new File("./src/input"));
		// to tolerate data index error
		Graph clfniaRN = new Graph(58228);
		while (sc.hasNextInt()) {
			int fromNodeIndex = sc.nextInt();
			int toNodeIndex = sc.nextInt();
			Vertex fromNode = clfniaRN.nodesArr[fromNodeIndex] == null ? new Vertex(fromNodeIndex)
					: clfniaRN.nodesArr[fromNodeIndex];
			Vertex toNode = clfniaRN.nodesArr[toNodeIndex] == null ? new Vertex(toNodeIndex)
					: clfniaRN.nodesArr[toNodeIndex];
			fromNode.outEdgeList.add(new Edge(fromNode, toNode));
			clfniaRN.nodesArr[fromNodeIndex] = fromNode;
			clfniaRN.nodesArr[toNodeIndex] = toNode;
		}
		sc.close();

		return clfniaRN;
	}
}
