//assgin.java
package assgin02;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.io.*;
public class assgin02 {
	
    public static void main(String[] args) {
//    	readText read=new readText();
//    	read.readText1();
    	 //readText1();
    	
		try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw
			/* ����TXT�ļ� */
			
			System.out.println("Start reading files\n");
			String pathname = "C:\\Users\\bing\\Desktop\\assign01\\roadNet-CA.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��

			Scanner scanner=new Scanner(new File(pathname));
			
			System.out.println("Start saving  files\n");
			int vlen = scanner.nextInt();
			int elen = scanner.nextInt();
			String outputLine="";
			
			//write for ddsg;
			
			outputLine=outputLine+"d"+"\n";
			outputLine=outputLine+Integer.toString(vlen)+" "+Integer.toString(elen)+"\n";
			System.out.println("0 ");
			for(int i=0;i<elen;i++){
				outputLine=outputLine+Integer.toString(scanner.nextInt())+" "+Integer.toString(scanner.nextInt())+" "+"1"+" "+"1"+"\n";
			}
			System.out.println("1");
			
			FileWriter writer=new FileWriter("C:\\Users\\bing\\Desktop\\assign02\\roadNet-CA.ddsg");
			writer.write(outputLine);
			//writer.write("");
			System.out.println("Finish\n");
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
    	   	
    }
}
