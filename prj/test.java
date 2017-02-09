package prj;

import java.io.*;
import java.util.*;

public class test {

//	private class Regex{
//		MatchCollection a;
//	}
//	public void test(){
//		Regex a=new Regex();
//	}
	//define node for the storing id of related nodes.
	public class toNode{
		int toNodeNum;
		toNode next;
	}
	//define the starting node 
	public class fromNode{
		int fromNodeNum;
		toNode start;
		fromNode next;
	}
	//define element in hashtable
	public class element{
		fromNode firstEdge;
	}
	//hashtable to stor the node
	public element[] hTable;
	// the size of the hashTable
	public int tableSize;
	//get number from scanner two types
	public int getFromID(String string){
		try{
		String a=string;
		System.out.println(a.substring(4));
		String b=a.substring(4,a.length()-1);
		System.out.println(b);
		//return Integer.parseInt("241619181");//id="1234"
		return Integer.parseInt(b);//id="1234"
		//return Integer.valueOf(b).intValue();
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
		//return 0;
	}
	//get number from ref="123"/>
	public int getFromRef(String string){
		System.out.println(string);
		return Integer.parseInt(string.substring(4, string.length()-3));
	}
	//hashCode
	public int hashCode(int key){
		return key%tableSize;
	}
	//link the from node int the hash element
	public void linkFromNode(fromNode node1,fromNode node2){
		fromNode p=node1;
		if(p.next!=null){
			p=p.next;
		}
		p.next=node2;
	}
	//link the toNode
	public void linkToNode(toNode node1,toNode node2){
		toNode p=node1;
		if(p.next!=null){
			p=p.next;
		}
		p.next=node2;
	}
	
	//construct function
	test(Scanner scanner){
		tableSize=997;
		hTable=new element[tableSize];
		for(int j=0;j<tableSize;j++){
			//element node=new element();
			hTable[j]=new element();
			hTable[j].firstEdge=null;
		}
		System.out.println("befor the 1 loop");
		while(true){
			System.out.println("in the 1 loop");
			String head=scanner.next();	
			//System.out.println(head);
			if(head.equals("<way")){
				System.out.println("after head=<way");
				String new1=scanner.next();
				System.out.println("0.01");
				System.out.println(new1);
				int originalIndex=getFromID(new1);//original index number
				System.out.println("0");
				int index=hashCode(originalIndex);//array index
				System.out.println("1");
				fromNode node=new fromNode();
				//linke the node
				System.out.println("before link in  1 loop");
				System.out.println(index);
				if(hTable[index].firstEdge==null){
					System.out.println("5");
					hTable[index].firstEdge=node;					
				}
				else{
					System.out.println("4");
					linkFromNode(hTable[index].firstEdge,node);
				}
				System.out.println("3");
				node.fromNodeNum=originalIndex;
				System.out.println("before the 2 loop");
				while(true){
					System.out.println("in the 2 loop");
					String test0=scanner.next();
					if(test0.equals("<nd")){
						toNode nodej=new toNode();
						nodej.toNodeNum=getFromRef(scanner.next());
						//link the node
						System.out.println("before link in  2 loop");
						if(node.start==null){
							node.start=nodej;
						}
						else{
							linkToNode(node.start,nodej);
						}
					}
					if(test0.equals("</way>")){
						System.out.println("in the 1 break");
						break;
					}
				}
			}
			if(head=="</osm>"){
				System.out.println("in the 2 break");
				break;
			}
		}
	}
	
	public void printHash(){
		for(int i=0;i<tableSize;i++){
			if(hTable[i].firstEdge!=null){
				fromNode node=hTable[i].firstEdge;
				if(node!=null){
					printToNode(node);
					node=node.next;
				}
			}
		}
		
	}
	
	public void printToNode(fromNode node){
		System.out.print(node.fromNodeNum+":");
		
		if(node.start!=null){
			toNode node1=node.start;
			while(node1!=null){
				System.out.print(node1.toNodeNum+",");
				node1=node1.next;
			}
			System.out.println("\n");
		}
	} 
	
	public static void main(String args[]){
		try{
		String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\testData.osm";
		Scanner scanner=new Scanner(new File(pathName));
		int[][][] test;
		test=new int[2][3][];
		test[1][2]=new int[5];
		System.out.println("Finish1");
		//System.out.println(2);
		test a=new test(scanner);
		System.out.println("Finish2");
		a.printHash();
		System.out.println("Finish3");
		//scanner.useDelimiter("<node[\\s|\\S|.]*?>[\\s|\\S|.]*?</node>");//regx to parse the string
		//scanner.useDelimiter("<node></node>");
//		while(true){
//		System.out.println(scanner.next());
////		if(scanner.next()=="<way"){
////			
////		}		
//		}
		//System.out.println("Out of Processing");
		}catch (Exception a){
			System.exit(0);
		}
	}
	
}
