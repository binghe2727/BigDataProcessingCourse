package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class DDSGHandler {
	public static void main(String[] args) throws IOException {
		// formatBrightKiteToDDSG();
		formatCaliforniaRoadNetworkToDDSG();
	}

	public static void formatBrightKiteToDDSG() throws IOException {
		Scanner sc = new Scanner(new File("./src/loc-brightkite_edges.txt"));
		// Scanner sc = new Scanner(new
		// File("./src/short_loc-brightkite_edges.txt"));
		HashSet<Integer> vSet = new HashSet<>();
		ArrayList<int[]> edgeList = new ArrayList<>();
		while (sc.hasNextInt()) {
			int fromNodeIndex = sc.nextInt();
			int toNodeIndex = sc.nextInt();
			vSet.add(fromNodeIndex);
			vSet.add(toNodeIndex);

			int[] pair = { fromNodeIndex, toNodeIndex };
			edgeList.add(pair);
		}
		sc.close();
		generateFile("./ddsg_brightkite.ddsg", vSet.size(), edgeList);
	}

	public static void generateFile(String fileName, int vNums, ArrayList<int[]> edgeList) throws IOException {
		File file = new File(fileName);
		FileWriter fw = new FileWriter(file);
		StringBuilder sb = new StringBuilder();
		sb.append("d");
		sb.append("\n");
		sb.append(vNums);
		sb.append(" ");
		sb.append(edgeList.size());
		sb.append("\n");
		for (int i = 0; i < edgeList.size(); i++) {
			sb.append(edgeList.get(i)[0]);
			sb.append(" ");
			sb.append(edgeList.get(i)[1]);
			sb.append(" ");
			// all weight is 1;
			sb.append(1);
			sb.append(" ");
			sb.append(0);
			sb.append("\n");
		}
		fw.write(sb.toString());
		fw.close();
	}

	public static void formatCaliforniaRoadNetworkToDDSG() throws IOException {
		Scanner sc = new Scanner(new File("./src/roadNet-CA.txt"));
		// Scanner sc = new Scanner(new File("./src/input"));
		// drop first 4 lines
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		sc.nextLine();
		HashSet<Integer> vSet = new HashSet<>();
		ArrayList<int[]> edgeList = new ArrayList<>();
		while (sc.hasNextInt()) {
			int fromNodeIndex = sc.nextInt();
			int toNodeIndex = sc.nextInt();
			// to tolerate data index error
			if (fromNodeIndex > 1965206 || toNodeIndex > 1965206) {
				continue;
			}
			vSet.add(fromNodeIndex);
			vSet.add(toNodeIndex);
			int[] pair = { fromNodeIndex, toNodeIndex };
			edgeList.add(pair);

		}
		sc.close();
		generateFile("./ddsg_roadNet_CA.ddsg", 1965206, edgeList);
	}
}
