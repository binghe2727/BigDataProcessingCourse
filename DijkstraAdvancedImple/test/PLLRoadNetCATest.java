package test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import algorithm.PLL;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import util.Util;

public class PLLRoadNetCATest {

	public static void main(String[] args) throws IOException {
		Random r = new Random();
		File file = new File("pllRoadNet.log");
		FileWriter fw = new FileWriter(file);
		Util.init();
		Graph graph = initBrightKite();
		String l = Util.printTimeStrCost("init graph finished.");
		fw.write(l);
		System.out.println(l);
		l = Util.printTimeStrCost("[PLL] ordering begin...");
		fw.write(l);
		System.out.println(l);
		PLL pll = new PLL(graph);
		l = Util.printTimeStrCost("[PLL] ordering end.");
		fw.write(l);
		System.out.println(l);
		l = Util.printTimeStrCost("[PLL] labeling begin...");
		fw.write(l);
		System.out.println(l);
		pll.labeling();
		// to test labels correctness
		// pll.printLabels();
		l = Util.printTimeStrCost("[PLL] labeling end.");
		fw.write(l);
		l = Util.printTimeStrCost("index construction finished.");
		fw.write(l);

		ArrayList<Long> costList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Vertex startVertex = graph.getVertex(r.nextInt(58228));
			Vertex targetVertex = graph.getVertex(r.nextInt(58228));
			if (targetVertex == null || startVertex == null) {
				// to tolerate index missing error
				i--;
				continue;
			}
			System.out.println("第 " + (i + 1) + "轮查询 from " + startVertex.index + " to " + targetVertex.index);// cs
			fw.write("第 " + (i + 1) + "轮查询 from " + startVertex.index + " to " + targetVertex.index);// cs
			double spd = pll.search(startVertex, targetVertex);
			if (spd > 0) {
				System.out.println(" spd = " + spd);// cs
				// dijkstra.printPathTo(targetVertex);
			} else {
				System.out.println("no path");
			}
			costList.add(Util.printTimeCost("时间消耗："));
		}
		System.out.println("查询结束");// cs
		fw.write("查询结束");// cs
		double totalCost = 0;
		for (int i = 0; i < costList.size(); i++) {
			totalCost += costList.get(i);
			System.out.println(costList.get(i));
			fw.write(costList.get(i) + "");
		}
		System.out.println("avg query time : " + (totalCost / costList.size()));
		fw.write("avg query time : " + (totalCost / costList.size()));
		fw.close();
	}

	public static Graph initBrightKite() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./src/roadNet-CA.txt"));
		// Scanner sc = new Scanner(new File("./src/pll_testing"));
		// drop first 4 lines
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		// to tolerate data index error
		Graph graph = new Graph(1965206 * 2);
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
