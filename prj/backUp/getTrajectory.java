package test;
/*
 * collect all the txts
 */
import java.io.*;
import java.util.*;
public class getTrajectory {
	//
	public static void main(String args[])throws Exception {
		BufferedWriter bw = new BufferedWriter(new FileWriter(new File(
				"C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\tTaxi\\all\\all.txt")));
		String line=new String();
		for(int i=0;i<10345;i++){
		try{
			String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\tTaxi\\all\\"
					+String.valueOf(i)+".txt";
			BufferedReader br = new BufferedReader(new FileReader(new File(pathName)));
			while ((line = br.readLine()) != null) {
				bw.write(line);
				bw.newLine();
			}
			br.close();
			System.out.println("Finish Copy"+i);
		}catch (FileNotFoundException e){			
		} 
		}
		bw.close();
		System.out.println("Copy Finish");
	}
}
