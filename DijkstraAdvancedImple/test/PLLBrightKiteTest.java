package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import algorithm.PLL;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import util.Util;

public class PLLBrightKiteTest {

	public static void main(String[] args) throws FileNotFoundException {
		Random r = new Random();
		Util.init();
		Graph graph = initBrightKite();
		Util.printTimeCost("init graph finished.");
		Util.printTimeCost("[PLL] ordering begin...");
		PLL pll = new PLL(graph);
		Util.printTimeCost("[PLL] ordering end.");
		Util.printTimeCost("[PLL] labeling begin...");
		pll.labeling();
		// to test labels correctness
		// pll.printLabels();
		Util.printTimeCost("[PLL] labeling end.");
		Util.printTimeCostWithTotalCost("index construction finished.");

		ArrayList<Long> costList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Vertex startVertex = graph.getVertex(r.nextInt(58228));
			Vertex targetVertex = graph.getVertex(r.nextInt(58228));
			if (targetVertex == null || startVertex == null) {
				// to tolerate index missing error
				i--;
				continue;
			}
			System.out.println("绗?" + (i + 1) + "杞煡璇?from " + startVertex.index + " to " + targetVertex.index);// cs
			double spd = pll.search(startVertex, targetVertex);
			if (spd > 0) {
				System.out.println(" spd = " + spd);// cs
				// dijkstra.printPathTo(targetVertex);
			} else {
				System.out.println("no path");
			}
			costList.add(Util.printTimeCost("鏃堕棿娑堣€楋細"));
		}
		System.out.println("鏌ヨ缁撴潫");// cs
		for (int i = 0; i < costList.size(); i++) {
			System.out.println(costList.get(i));
		}
	}

	public static Graph initBrightKite() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./src/loc-brightkite_edges.txt"));
		// Scanner sc = new Scanner(new File("./src/pll_testing"));
		// to tolerate data index error
		Graph graph = new Graph(58228);
		while (sc.hasNextInt()) {
			int fromNodeIndex = sc.nextInt();
			int toNodeIndex = sc.nextInt();
			Vertex fromNode = graph.nodesArr[fromNodeIndex] == null ? new Vertex(fromNodeIndex)
					: graph.nodesArr[fromNodeIndex];
			Vertex toNode = graph.nodesArr[toNodeIndex] == null ? new Vertex(toNodeIndex) : graph.nodesArr[toNodeIndex];
			fromNode.outEdgeList.add(new Edge(fromNode, toNode));
			graph.nodesArr[fromNodeIndex] = fromNode;
			graph.nodesArr[toNodeIndex] = toNode;
		}
		sc.close();

		return graph;
	}
}
