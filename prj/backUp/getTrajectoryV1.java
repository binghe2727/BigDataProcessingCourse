package test;
/*
 * from the all.txt to write the trajector in the matrix
 */
import java.io.*;
import java.util.*;
public class getTrajectoryV1 {
	//set the node 
	public class trajNode{
		double lat;
		double lon;
		long time;
		trajNode next;
	}
	//set the start point
	public class VTraj{
		trajNode firstEdge;
	}
	//set the graph
	VTraj[] trajGraph;
	
	//constructiong work
	getTrajectoryV1(Scanner scanner){
		//initialize
		trajGraph=new VTraj[10345];
		for(int i=0;i<10345;i++){
			trajGraph[i]=new VTraj();
		}
		boolean setEnding=true;
		try{
		while(setEnding){
			//get input
			//step 1 get userID
			String test=scanner.next();
			//System.out.println(test);
			String[] a=test.split(",");
			String userID=a[0];
			//step 2 get lat and lon
			String test1=scanner.next();
			//System.out.println(test1);
			String[] a1=test1.split(",");
			String lon=a1[1];//2008-02-04
//			System.out.println(a[1].charAt(9));
//			System.exit(0);
//			System.out.println(userID);
//			System.out.println(a[0]);
//			System.out.println(a[1]);
//			System.out.println(a1[0]);
//			System.out.println(a1[1]);
//			System.out.println(a1.length);
			String lat=a1[2];//21:14:24
			//String time=a[1]+" "+a1[0];---from strng to long
			//processing the time for later computation
			
			long time=(Integer.parseInt(String.valueOf(a[1].charAt(9)))-2)*86400
			+Integer.parseInt(a1[0].substring(6))
			+Integer.parseInt(a1[0].substring(3, 5))*60
			+Integer.parseInt(a1[0].substring(0, 2))*60*60;//day seconds.
			
			
			
//			System.out.println(a1[0].substring(0, 2));
//			System.out.println(a1[0].substring(3, 5));
//			System.out.println(a1[0].substring(6));
//			System.exit(0);
			
//			a1[0].substring(0, 2)
//			a1[0].substring(3, 5)
//			a1[0].substring(6)
			
			
			//add to graph
			trajNode node=new trajNode();
			node.lat=Double.parseDouble(lat);
			node.lon=Double.parseDouble(lon);
			//process time to get the statnd
			
			node.time=time;
			//set to get the ending;not robost when the dataSet changes
			//setting some problems use the exception to end the funciton 
			//---------later change for comment
//			if(userID=="10340"){
//				if(time=="2008-02-08 17:37:43"){
//					setEnding=false;
//				}
//			}
			if(trajGraph[Integer.parseInt(userID)].firstEdge==null){//first time just use the null to link is wrong!!
				trajGraph[Integer.parseInt(userID)].firstEdge=node;
			}
			else
			linkTrajNode(trajGraph[Integer.parseInt(userID)].firstEdge,node);
		}
	} catch(NoSuchElementException e){
		setEnding=false;
	}
	}
	
	//printGraph
	public void printGraph(){
		int ii=0;
		for(int i=0;i<10345;i++){
			if(trajGraph[i].firstEdge!=null){
				System.out.println("StartPrint");
				trajNode node=trajGraph[i].firstEdge;
				System.out.print(i+":");
				while(node!=null){
					printTrajNode(node);
					node=node.next;
					ii++;
				}
				System.out.print("\n");
			}
			}
		System.out.println(ii);
		}
	

	public void printTrajNode(trajNode node){
		System.out.print(node.time+","+node.lon+","+node.lat+";  ");
	}
	
	//link function
	public void linkTrajNode(trajNode node1, trajNode node2){
		if(node1==null)	{
			node1=node2;
		}
		else{
			while(node1.next!=null){
				node1=node1.next;
			}
			node1.next=node2;
		}
	}
	
	public static void main(String args[]){
		try{
			String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\tTaxi\\all\\all.txt";
			Scanner scanner=new Scanner(new File(pathName));
//			while(true){
//				System.out.println(scanner.next());
//			}
			
//			String a=scanner.next();
//			String[] a1=a.split(",");			
//			String b=scanner.next();
//			String[] b1=b.split(",");
//			System.out.println(b1[2]);
			System.out.println("Finish1");
			getTrajectoryV1 test=new getTrajectoryV1(scanner);
			System.out.println("Finish2");
			test.printGraph();
			//write in the matrix
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
}
