package test;
/*
 * realize construct the two graphs 1 way  2.nodes
 */
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
		long toNodeNum;
		toNode next;
	}
	//define the starting node 
	public class fromNode{
		long fromNodeNum;
		toNode start;
		fromNode next;
		//double lat;
		//double lon;
	}
	//define element in hashtable
	public class element{
		fromNode firstEdge;
	}
	//hashtable to stor the node
	public element[] hTable;
	// the size of the hashTable
	public long tableSize;
	//get number from scanner two types
	
	/*
	 * set the node hashTable
	 */
	//node in the graph
	public class nodePoint{
		public long nodeID;
		public double lat;
		public double lon;
		nodePoint next;
	}
	//firedge node
	public class toNodePoint{
		nodePoint firstEdge;
	}
	//hashTable
	public toNodePoint[] hTable1;
	//testMarking
	public int iterations=0;
	public long getFromID(String string){
		try{
		String a=string;
		System.out.println(a.substring(4));
		String b=a.substring(4,a.length()-1);//
		System.out.println(b);
		//return Integer.parseInt("241619181");//id="1234"
		return Long.parseLong(b);//id="1234"
		//return Integer.valueOf(b).intValue();
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
		//return 0;
	}
	//get number from ref="123"/>
	public long getFromRef(String string){
		try{
			String a=string;
		System.out.println(string);
		System.out.println(a.length());
		String b=a.substring(5, a.length()-3);
		//System.out.println(string.substring(5, string.length()-3));
		//return Integer.parseInt(string.substring(5, string.length()-3));
		System.out.println(b);
		//System.out.println(b.length());
		//b="1234";
		return Long.parseLong(b);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return 0;
		}
	}
	//hashCode
	public long hashCode(long key){
		return key % tableSize;
	}
	//link the from node int the hash element
	public void linkFromNode(fromNode node1,fromNode node2){
		fromNode p=node1;
		while(p.next!=null){
			p=p.next;
		}
		p.next=node2;
	}
	//link the toNode
	public void linkToNode(toNode node1,toNode node2){
		toNode p=node1;
		//int i=0;
		while(p.next!=null){
			p=p.next;
			//i++;
		}
		p.next=node2;
	}
	
	public void linkNodePoint(nodePoint node1,nodePoint node2){
		nodePoint p=node1;
		while(p.next!=null){
			p=p.next;
		}
		p.next=node2;
	}
	
	//construct function
	test(Scanner scanner,Scanner scanner1) {
		//String pathNameTest="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\all.txt";
		//String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\testDataV2.osm";
		
		//scanner=new Scanner(new File(pathNameTest));
		//debugging Test
		String pathNameTest="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\all.txt";
		try{
		Scanner scannerTest=new Scanner(new File(pathNameTest));
		String s=scannerTest.next();
		}catch (Exception e){
			e.printStackTrace();
		}
		//construct the way graph
		boolean break0=true,break1=true;
		tableSize=997;
		hTable=new element[(int)tableSize];
		for(int j=0;j<tableSize;j++){
			//element node=new element();
			hTable[j]=new element();
			hTable[j].firstEdge=null;
		}
		//System.out.println("befor the 1 loop");
		while(break0){
			//System.out.println("in the 1 loop");
			String head=scanner.next();
			//System.out.println(head);
			//System.out.println(head);
			if(head.equals("<way")){
				//System.out.println("after head=<way");
				String new1=scanner.next();
				//System.out.println("0.01");
				//System.out.println(new1);
				long originalIndex=getFromID(new1);//original index number
				//System.out.println("0");
				long index=hashCode(originalIndex);//array index
				//System.out.println("1");
				fromNode node=new fromNode();
				//linke the node
				//System.out.println("before link in  1 loop");
				//System.out.println(index);
				if(hTable[(int)index].firstEdge==null){
					//System.out.println("5");
					hTable[(int)index].firstEdge=node;					
				}
				else{
				//	System.out.println("4");
					linkFromNode(hTable[(int)index].firstEdge,node);
				}
				//System.out.println("3");
				node.fromNodeNum=originalIndex;
				//System.out.println("before the 2 loop");
				break1=true;
				while(break1){
					//System.out.println("in the 2 loop");
					String test0=scanner.next();
				//	System.out.println("get: "+test0);
					if(test0.equals("<nd")){
						toNode nodej=new toNode();
						nodej.toNodeNum=getFromRef(scanner.next());
						//link the node
					//	System.out.println("before link in  2 loop");
						if(node.start==null){
						//	System.out.println("when the from node is empty");
							node.start=nodej;
						}
						else{
						//	System.out.println("when the from node is not empty");
							linkToNode(node.start,nodej);
						}
					}
					//printToNode(node);
					if(test0.equals("</way>")){
					//	System.out.println("in the 1 break");
						break1=false;
					//	System.out.println("head is"+head);
					}
				}
			}
			if(head.equals("</osm>")){
			//	System.out.println("in the 2 break");
				break0=false;
			}
		}		
		scanner.close();
		//------------construct the node graph
		hTable1=new toNodePoint[(int)tableSize];
		for(int j=0;j<tableSize;j++){
			//element node=new element();
			hTable1[j]=new toNodePoint();
			hTable1[j].firstEdge=null;
		}
		
		String head1=scanner1.next();
		while(!head1.equals("</osm>")){
			if(head1.equals("<node")){
				String new1=scanner1.next();
				long originalIndex=getFromID(new1);//original index number
				long index=hashCode(originalIndex);//array index
				//judge whether have
				if(hTable1[(int)index].firstEdge==null){
					nodePoint node=new nodePoint();
					hTable1[(int)index].firstEdge=node;
					//do seven time				
					for(int kk=0;kk<6;kk++){
						scanner1.next();
					}
					node.lat=getLat(scanner1.next());
					node.lon=getLon(scanner1.next());
					node.nodeID=originalIndex;
				}
				else{//first link existed
					nodePoint node=new nodePoint();
					//System.out.println("in the linking processing"+index);
					linkNodePoint(hTable1[(int)index].firstEdge,node);

					//do seven time				
					for(int kk=0;kk<6;kk++){
						scanner1.next();
					}
					node.lat=getLat(scanner1.next());
					node.lon=getLon(scanner1.next());
					node.nodeID=originalIndex;
//					printNodePoint(hTable1[(int)index].firstEdge);
//					printNodePoint(hTable1[(int)index].firstEdge.next);
//					if(hTable1[(int)index].firstEdge.next.next!=null){
//						printNodePoint(hTable1[(int)index].firstEdge.next.next);
//					}
				}
			}
			head1=scanner1.next();
			//System.out.println("head1 is"+head1);
		}		
		scanner1.close();
		
//		printNodePoint(hTable1[(int)1].firstEdge);
//		printNodePoint(hTable1[(int)1].firstEdge.next);
//		if(hTable1[(int)1].firstEdge.next.next!=null){
//			printNodePoint(hTable1[(int)1].firstEdge.next.next);
//		}
//		System.out.println("in loop");
//		for(int i=1;i<2;i++){
//			if(hTable1[i].firstEdge!=null){
//				nodePoint p=new nodePoint();
//				p=hTable1[i].firstEdge;
//				while(p!=null){
//					//printNodePoint(p);
//					System.out.print(p.nodeID+","+p.lat+","+p.lon+"\n");
//					p=p.next;
//					//System.out.print(p.next.nodeID+","+p.next.lat+","+p.next.lon+"\n");
//				}
//			}
//		}		
	}
	
//	public void testConstructNodeHash(Scanner scanner1){
//		/////vertex processing add info		
//		String head1=scanner1.next();
//		while(!head1.equals("</osm>")){
//		if(head1.equals("<node")){
//			String new1=scanner1.next();
//			long originalIndex=getFromID(new1);//original index number
//			long index=hashCode(originalIndex);//array index
//			//judge whether have
//			if(hTable[(int)index].firstEdge==null){					
//				//System.out.println("5");
//				fromNode node=new fromNode();
//				node.fromNodeNum=originalIndex;
//				hTable[(int)index].firstEdge=node; // later add x and y value
//				//do seven time				
//				for(int kk=0;kk<6;kk++){
//					scanner1.next();
//				}
//				//do another 2 times
//				node.lat=getLat(scanner1.next());
//				node.lon=getLon(scanner1.next());				
//			}
//			else {// no fromNode in the index
//				if(isValid(hTable[(int)index].firstEdge,originalIndex)!=null){//index existed
//					
//					//System.out.println("in the needed loop---------------------------------------------------------");
//					//System.exit(0);
//					fromNode node=isValid(hTable[(int)index].firstEdge,originalIndex);////attention to see whether pass!!!
//					//do seven time				
//					for(int kk=0;kk<6;kk++){
//						scanner1.next();
//					}
//					//do another 2 times
//					
//					node.lat=getLat(scanner1.next());
//					node.lon=getLon(scanner1.next());
//					
//					//(isValid(hTable[(int)index].firstEdge,originalIndex)).lat=getLat(scanner1.next());
//					//(isValid(hTable[(int)index].firstEdge,originalIndex)).lon=getLat(scanner1.next());
//					
//				}
//				else{
//					//the first one existed 
//					
//					fromNode node=new fromNode();
//					node.fromNodeNum=originalIndex;
//					//debugging 998
////					if(originalIndex==998)
////						System.out.println("998 here 2");//result 998 here
//					
//					linkFromNode(hTable[(int)index].firstEdge,node);
//					//debugging
//					
////					fromNode nodeij=hTable[(int)index].firstEdge;
////					printToNode(nodeij);
////					//node=node.next;
////					nodeij=nodeij.next;
////					
////					printToNode(nodeij);
//					
//					//do seven time				
//					for(int kk=0;kk<6;kk++){
//						scanner1.next();
//					}
//					//do another 2 times
//					node.lat=getLat(scanner1.next());
//					node.lon=getLon(scanner1.next());
//				}
//			}			
//		}
//		head1=scanner1.next();
//		}
//	}
	
	public fromNode isValid(fromNode node1,long index){
		fromNode p=node1;
		if(p.fromNodeNum==index){
			return p;
		}
		while(p.next!=null){
			p=p.next;	
			if(p.fromNodeNum==index){
				return p;
			}
		}
		return null;
	}
	
	public double getLat(String string){
		try{
			String a=string;
			String b=a.substring(5, a.length()-1);
			return Double.parseDouble(b);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public double getLon(String string){
		try{
			String a=string;
			String b=a.substring(5, a.length()-3);
			return Double.parseDouble(b);
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}
	}
	
	public void printHash(){
		int ii=0;
		
		for(int i=0;i<tableSize;i++){
			if(hTable[i].firstEdge!=null){
				fromNode node=hTable[i].firstEdge;
				while(node!=null){
					//test 998
					//if(node.fromNodeNum==998){
					//	System.out.println("find 998");
					//}
					printToNode(node);
					node=node.next;
					ii++;
				}
			}
		}
		System.out.println("Total vallid fromNodes is"+"\n");
		System.out.println(ii);
		
	}
	
	//print edge graph
	public void printToNode(fromNode node){
		System.out.print(node.fromNodeNum+":");
		//int iterations=0;
		if(node.start!=null){ 
			toNode node1=node.start;
			while(node1!=null){
				System.out.print(node1.toNodeNum+",");
				node1=node1.next;
				iterations++;
			}
			//System.out.println("iteration="+iterations+"\n");
			System.out.println("\n");
		}
		else{
			System.out.println("\n");
		}		
	}
	//print node graph hash
	public void printHash1(){
		int ii=0;
		long maxNumber=0;
		for(int i=0;i<tableSize;i++){
			if(hTable1[i].firstEdge!=null){
				nodePoint p=hTable1[i].firstEdge;
				while(p!=null){//previously use if , it is wrong
					printNodePoint(p);
					if(p.nodeID>maxNumber){
						maxNumber=p.nodeID;
					}
					//System.out.print(p.nodeID+","+p.lat+","+p.lon+"\n");
					p=p.next;
					ii++;
				}
			}
		}
		System.out.println("Total vallid nodes is"+"\n");
		System.out.println(ii);
		System.out.println(maxNumber);
	}
	
	public void printNodePoint(nodePoint node){
		System.out.print(node.nodeID+","+node.lat+","+node.lon+"\n");
	}
	
	public static void main(String args[]){
		try{
			
//			//debugging test 
//			String pathNameTest="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\all.txt";
//			try{
//			Scanner scannerTest=new Scanner(new File(pathNameTest));
//			String s=scannerTest.next();
//			System.out.println(scannerTest.hasNext());
//			}catch (Exception e){
//				e.printStackTrace();
//				System.out.println("Stop finish");
//				System.exit(0);
//			}
				
		String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\all.txt";
		//String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\testDataV2.osm";
		Scanner scanner=new Scanner(new File(pathName));
		Scanner scanner1=new Scanner(new File(pathName));
		

		
		
		//System.out.println(scanner);
		//int[][][] test;
		//test=new int[2][3][];
		//test[1][2]=new int[5];
		System.out.println("Finish1");
		//System.out.println(2);
		test a=new test(scanner,scanner1);
		System.out.println("Finish2");
		//a.printHash();
		a.printHash1();
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
			//System.out.print("some error");;
			a.printStackTrace();
		}
	}
	
	
	
	
}
