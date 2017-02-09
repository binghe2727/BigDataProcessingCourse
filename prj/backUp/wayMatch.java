package test;
/*
 * realize the function of Mapmatching first step to let the road in the array[256][256]
 */
import java.io.*;
import java.util.*;
import java.io.*;
import java.util.*;

import test.getTrajectoryV1.trajNode;
import test.test.*;
import test.test.fromNode;
import test.test.nodePoint;
import java.math.*;
/*
 * firstly match the way to the 256*256 diagram
 */
public class wayMatch {
	//too complex to construt multiple recussion struct
//	//point Info
//	public class pointInfo {
//		double lat;
//		double lon;
//	}
//	//public class roadid
//	public class roadID{
//		long wayid;
//		int wayseg;
//	}
	// road part info
	public class roadPart{
		//roadID roadid;
		long wayid;
		int wayseg;
		//pointInfo start;
		double startLat;
		double startLon;
		double endLat;
		double endLon;
		//later add for traverse the trajectory line
		long StartID;
		long EndID;
		roadPart nextOne;
		//pointInfo end;
	}
	//user part info
	public class userPart{
		//roadID roadidMatch;
		long roadidMatchwayid;
		int roadidMatchwayseg;
		long userid;
		//pointInfo userLocation;
		double userLaat;
		double userLon;
	}
	//element road in array 
	public class element{
		int roadNumber;
		int userNumber;
		userPart userEdge;
		roadPart roadEdge;
	}
	//array
	public element[][] array;
	//arraay size
	int scale;
	double scaleX;
	double scaleY;
	//location scale in the array;
	double left,right,up,down;
	//---------------------//
	//construct graph
	public class nodeInGraph{
		double timeCost;//time dijkstra
		long countingTime;//popular dijkstra
		double distance;//weight
		long nodeIDinfo;//like the vertex number index read from hTable1
		nodeInGraph next;
	}
	//to point information
	public class toNodeInGraph{
		nodeInGraph firstEdge;
		long index;// starting point information
		double lat;
		double lon;
	}
	//final graph
	toNodeInGraph[] searchGraph0;//1600000000 store 
	toNodeInGraph[] searchGraph1;//1600000000 store 
	toNodeInGraph[] searchGraph2;//1600000000 store
	
	public long deductSize;
	
	// list of the x,y coordingts.---back string
	
	//---in traverse the trajectory set the global parameters
	double startX;
	double startY;
	double endX;
	double endY;
	long StartIDGlobal;
	long EndIDGlobal;
	//Prev one
	double prevstartX;
	double prevstartY;
	double preveendX;
	double prevendY;
	long preStartIDGlobal;
	long preEndIDGlobal;
	//road time cost use
	long roadStartTime;
	//constructFunction
	wayMatch(Scanner scanner0,Scanner scanner1,Scanner scanner2){//add scanner2 for the trajectory info
		// construct the test class
		test testDataBase=new test(scanner0,scanner1);
		//construct now parameter
		scale=256;
		left=116.2614;
		right=116.4894;
		down=39.8286;
		up=39.9913;
		//x,y coordinate setting
		scaleX=(right-left)/scale;
		scaleY=(up-down)/scale;
		array=new element[scale][scale];
		for(int i=0;i<scale;i++){
			for(int j=0;j<scale;j++){
				array[i][j]=new element();
			    array[i][j].roadNumber=0;
				array[i][j].userNumber=0;
			    array[i][j].roadEdge=null;
			    array[i][j].userEdge=null;
			}
		}
		//initial the searchGraph  max range number 4511514989
		//Integer.MAX_VALUE=4511514989;
		deductSize=1600000000;
		searchGraph0= new toNodeInGraph[400000000];
		searchGraph1= new toNodeInGraph[400000000];
		searchGraph2= new toNodeInGraph[400000000];
		searchGraph0= new toNodeInGraph[400000000];
		searchGraph1= new toNodeInGraph[400000000];
		searchGraph2= new toNodeInGraph[400000000];
		searchGraph0= new toNodeInGraph[400000000];
		searchGraph1= new toNodeInGraph[400000000];
		searchGraph2= new toNodeInGraph[400000000];
		searchGraph0= new toNodeInGraph[400000000];
		searchGraph1= new toNodeInGraph[400000000];
		searchGraph2= new toNodeInGraph[400000000];
		searchGraph0= new toNodeInGraph[400000000];
		searchGraph1= new toNodeInGraph[400000000];
		searchGraph2= new toNodeInGraph[400000000];
		for(int i=0;i<1600000000;i++){
			searchGraph0[i]=new toNodeInGraph();
			searchGraph1[i]=new toNodeInGraph();
			searchGraph2[i]=new toNodeInGraph();
		}
		//start traverse all the graph in the test clss
		//use a.hTable(way info) and a.hTable1(point info)
		  
		//first travse all the point infor to get for the vertex grah--the point graph
		for(int i=0;i<testDataBase.tableSize;i++){
			if(testDataBase.hTable1[i].firstEdge!=null){
				nodePoint p=testDataBase.hTable1[i].firstEdge;
				while(p!=null){//previously use if , it is wrong
					//printNodePoint(p);
					if(p.nodeID<deductSize){
						searchGraph0[(int)p.nodeID].index=p.nodeID;
						searchGraph0[(int)p.nodeID].lat=p.lat;
						searchGraph0[(int)p.nodeID].lon=p.lon;
					}
					else
						if(p.nodeID<2*deductSize){
							searchGraph1[(int)p.nodeID].index=p.nodeID-deductSize;
							searchGraph1[(int)p.nodeID].lat=p.lat;//should only index deduct ,not the lat and lon deduct
							searchGraph1[(int)p.nodeID].lon=p.lon;
						}
						else{
							searchGraph2[(int)p.nodeID].index=p.nodeID-deductSize*2;
							searchGraph2[(int)p.nodeID].lat=p.lat;
							searchGraph2[(int)p.nodeID].lon=p.lon;
						}
					//System.out.print(p.nodeID+","+p.lat+","+p.lon+"\n");
					p=p.next;
				}
			}
		}
		//secondly, traverse the wayGraph
		///two layer traversing---two loops maybe!!!----attention
		for(int i=0;i<testDataBase.tableSize;i++){
			if(testDataBase.hTable[i].firstEdge!=null){
				fromNode node=testDataBase.hTable[i].firstEdge;//from just way number no great meaning
				while(node!=null){
					//node.//function to traver a fromNode to its toNode
					//traverse from node
					traverseFromNodeToStartNode(node);					
					node=node.next;
				}
			}
		}
		//thirdly, traverse the trajectory graph to update the information
		getTrajectoryV1 traGrap=new getTrajectoryV1(scanner2);
		for(int i=0;i<10345;i++){
			if(traGrap.trajGraph[i].firstEdge!=null){
				trajNode node=traGrap.trajGraph[i].firstEdge;
				//do the first search to get the road x,y,x,y
				findTheNearRoad(node.lon,node.lat,
						(int) Math.floor((node.lon-left)/scaleX),(int) Math.floor((node.lat-down)/scaleY));
				roadStartTime=node.time;
				//update
				node=node.next;
				while(node!=null){					
					//Prev one					
					//in fact can change to the StartID, EndID comparing
					 prevstartX=startX;
					 prevstartY=startY;
					 preveendX=endX;
					 prevendY=endY;
					 preStartIDGlobal=StartIDGlobal;
					 preEndIDGlobal=EndIDGlobal;					
					//traverseTrajNode(node);
					//consider the parameter limits so set in the global enviroment
					//step1---find the array location-----------------
					int x1Coordin;
					int y1Coordin;		
					x1Coordin=(int) Math.floor((node.lon-left)/scaleX);
					y1Coordin=(int) Math.floor((node.lat-down)/scaleY);
					//step2---find the nearest distance road with x,y,x,y
					findTheNearRoad(node.lon,node.lat,x1Coordin,y1Coordin);//be careful of the golbal parameters.
					//out put x,y,x,y currrent match
					
					//step 3----when change road ,update the time and counting time.
					//step3---judge whether it is the prevous one
					if((prevstartX!=startX)||(prevstartY!=startY)||(preveendX!=endX)||(prevendY!=endY)){
						//coungting time change from start id to  endid						
						//when advise the original directed graph use the function of matchinSearchGraphStartingPoint
						nodeInGraph nodeLoopFinding=matchinSearchGraphStartingPoint(preStartIDGlobal).firstEdge;
						while(nodeLoopFinding.nodeIDinfo!=preEndIDGlobal){
							nodeLoopFinding=nodeLoopFinding.next;
							//set if for debugging checking
							if(nodeLoopFinding==null){
								System.out.println("Not match");
								System.exit(0);
							}
						}
						nodeLoopFinding.countingTime++;
						//timeCost updating and updating
						nodeLoopFinding.timeCost=node.time-roadStartTime;
						roadStartTime=node.time;						
					}
					//--------------------
					node=node.next;
				}
			}
		}
	}
	
	//find the near roads;lon lat means the trajectory, x and y means the box
	public void findTheNearRoad(double lon, double lat,int x,int y){
		//lon=x;
		//lat=y

		roadPart node=array[x][y].roadEdge;
		startX=node.startLon;
		startY=node.startLat;
		endX=node.endLon;
		endY=node.endLat;
		StartIDGlobal=node.StartID;
		EndIDGlobal=node.EndID;
		double distanceMin=pointToLine(startX,startY,endX,endY,lon,lat);//when get a x,y and start x, start y, endx,endy compute distance		
		while(node.nextOne!=null){
			if(distanceMin>pointToLine(node.nextOne.startLon,node.nextOne.endLat,node.nextOne.endLon,node.nextOne.endLat,lon,lat)){
				startX=node.nextOne.startLon;
				startY=node.nextOne.startLat;
				endX=node.nextOne.endLon;
				endY=node.nextOne.endLat;
				StartIDGlobal=node.nextOne.StartID;
				EndIDGlobal=node.nextOne.EndID;
				distanceMin=pointToLine(node.nextOne.startLon,node.nextOne.endLat,node.nextOne.endLon,node.nextOne.endLat,lon,lat);
			}
			node=node.nextOne;
		}		
	}
	//compute distance between line and road
	
	// 点到直线的最短距离的判断 点（x0,y0） 到由两点组成的线段（x1,y1） ,( x2,y2 )

    public double pointToLine(double x1, double y1, double x2, double y2, double x0, double y0) {

        double space = 0;

        double a, b, c;

        a = lineSpace(x1, y1, x2, y2);// 线段的长度

        b = lineSpace(x1, y1, x0, y0);// (x1,y1)到点的距离

        c = lineSpace(x2, y2, x0, y0);// (x2,y2)到点的距离

        if (c+b == a) {//点在线段上

           space = 0;

           return space;

        }

        if (a <= 0.000001) {//不是线段，是一个点

           space = b;

           return space;

        }

        if (c * c >= a * a + b * b) { //组成直角三角形或钝角三角形，(x1,y1)为直角或钝角

           space = b;

           return space;

        }

        if (b * b >= a * a + c * c) {//组成直角三角形或钝角三角形，(x2,y2)为直角或钝角

           space = c;

           return space;

        }
	//组成锐角三角形，则求三角形的高
        double p = (a + b + c) / 2;// 半周长

        double s = Math.sqrt(p * (p - a) * (p - b) * (p - c));// 海伦公式求面积

        space = 2 * s / a;// 返回点到线的距离（利用三角形面积公式求高）

        return space;

    }


    // 计算两点之间的距离

    private double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;

        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2)

               * (y1 - y2));

        return lineLength;
    }
	
	//traverse trajnode in the graph
	public void traverseTrajNode(trajNode node){
		
	}
	
	//travser a from node
	public void traverseFromNodeToStartNode(fromNode node){
		toNode node1=node.start;
		while(node1.next!=null){
			//find a two-point road
			//step 1 fill the directed graph
			//construct node
			nodeInGraph node2=new nodeInGraph();
			node2.nodeIDinfo=node1.next.toNodeNum;//ending point in the road
			node2.distance=distanceTwoPoints(					
					matchinSearchGraphStartingPoint(node1.toNodeNum).lon,
					matchinSearchGraphStartingPoint(node1.toNodeNum).lat,
					matchinSearchGraphStartingPoint(node1.next.toNodeNum).lon,
					matchinSearchGraphStartingPoint(node1.next.toNodeNum).lat);//compute the distance from 
																			//from nodeID to arrayID
			//link
			if(matchinSearchGraphStartingPoint(node1.toNodeNum).firstEdge==null){
				matchinSearchGraphStartingPoint(node1.toNodeNum).firstEdge=node2;
			}
			else{
				linkNodeInGraph(matchinSearchGraphStartingPoint(node1.toNodeNum).firstEdge,node2);//attention!!!
																									//existed judge
			}
			

			//step 2 wayMatch to 256*256
			//basic on the distance to return some thinigs--basedOn this location and next Location
			String passBoxGet=wayMatchIn256(
					matchinSearchGraphStartingPoint(node1.toNodeNum).lon,
					matchinSearchGraphStartingPoint(node1.toNodeNum).lat,
					matchinSearchGraphStartingPoint(node1.next.toNodeNum).lon,
					matchinSearchGraphStartingPoint(node1.next.toNodeNum).lat);
			//string 0,0-1,0-1,1 like with road from
			//maybe need debug about the even length
//			for(int ii=0;ii<(passBoxGet.length());ii=ii+2){
//				int j=Integer.parseInt(passBoxGet.charAt(ii));
//				Scanner scanner2=new Scanner(passBoxGet);
//				array[][]
//			}
			//---------use scanner---
			Scanner boxGet=new Scanner(passBoxGet);
			int totBoxAll=boxGet.nextInt();
			for(int loop=0;loop<totBoxAll;loop++){
				int indexX=boxGet.nextInt();
				int indexY=boxGet.nextInt();
				array[indexX][indexY].roadNumber++;
				
				//first judge whether ther is roadEdge
				if(array[indexX][indexY].roadEdge==null){//node roadEge first
					roadPart nodeRoad=new roadPart();
					nodeRoad.startLat=matchinSearchGraphStartingPoint(node1.toNodeNum).lat;
					nodeRoad.startLon=matchinSearchGraphStartingPoint(node1.toNodeNum).lon;
					nodeRoad.endLat=matchinSearchGraphStartingPoint(node1.next.toNodeNum).lat;
					nodeRoad.endLon=matchinSearchGraphStartingPoint(node1.next.toNodeNum).lon;
					nodeRoad.wayid=node.fromNodeNum;// if necessary to check it
					nodeRoad.StartID=node1.toNodeNum;
					nodeRoad.EndID=node1.next.toNodeNum;
					//nodeRoad.wayseg//not set by loopping if necessary add it
					array[indexX][indexY].roadEdge=nodeRoad;
				}// if ther is already node in the link
				else{
					roadPart nodeRoad=new roadPart();
					nodeRoad.startLat=matchinSearchGraphStartingPoint(node1.toNodeNum).lat;
					nodeRoad.startLon=matchinSearchGraphStartingPoint(node1.toNodeNum).lon;
					nodeRoad.endLat=matchinSearchGraphStartingPoint(node1.next.toNodeNum).lat;
					nodeRoad.endLon=matchinSearchGraphStartingPoint(node1.next.toNodeNum).lon;
					nodeRoad.wayid=node.fromNodeNum;// if necessary to check it
					nodeRoad.StartID=node1.toNodeNum;
					nodeRoad.EndID=node1.next.toNodeNum;
					//nodeRoad.wayseg//not set by loopping if necessary add it
					//link;
					linkRoadPart(array[indexX][indexY].roadEdge,nodeRoad);
				}				
			}			
			//step3 updating
			node1=node1.next;			
		}
	}
	
	public void linkRoadPart(roadPart node1,roadPart node2){
		roadPart p=node1;
		while(p.nextOne!=null){
			p=p.nextOne;
		}
		p.nextOne=node2;
	}
	
	//return the route line box from x1,y1 to x2,y2
	public String wayMatchIn256(double x1,double y1,double x2,double y2){//judge the boundry
		String string=new String();
		int x1Coordin;
		int y1Coordin;
		int x2Coordin;
		int y2Coordin;
		x1Coordin=(int) Math.floor((x1-left)/scaleX);
		y1Coordin=(int) Math.floor((y1-down)/scaleY);
		x2Coordin=(int) Math.floor((x2-left)/scaleX);
		y2Coordin=(int) Math.floor((y2-down)/scaleY);		
		//in the same box
		if((x1Coordin==x2Coordin)&&(y1Coordin==y2Coordin)){
			string="1"+" "+String.valueOf(x1Coordin)+" "+String.valueOf(y1Coordin);
		}
		//in the adjecent box
		else
			if(((x1Coordin==x2Coordin)&&(1==Math.abs(y2Coordin-y1Coordin)))
				||((y1Coordin==y2Coordin)&&(1==Math.abs(x2Coordin-x1Coordin)))){
					string="2"+" "+string+String.valueOf(x1Coordin)+" "+String.valueOf(y1Coordin)+" "+
					String.valueOf(x2Coordin)+" "+String.valueOf(y2Coordin);
			}
			else{
				double k=(y2-y1)/(x2-x1);//get y=kx+b:k
				if(x2==x1){
					System.out.println("x1=x2");
					System.exit(0);
				}
				//get the for different k>,k<0 this condition
				int xMin=(x1Coordin<x2Coordin)?x1Coordin:x2Coordin;
				int xMax=(x1Coordin>x2Coordin)?x1Coordin:x2Coordin;
				int yMin=(y1Coordin<y2Coordin)?y1Coordin:y2Coordin;
				int yMax=(y1Coordin>y2Coordin)?y1Coordin:y2Coordin;				
				int totBox=0;
				for(int i=xMin;i<=xMax;i++){
					for(int j=yMin;j<=yMax;j++){
						if(isPass(i,j,k,x1,y1)){//k,x1,y1,pass the i,j box
							totBox++;
							string=string+String.valueOf(i)+" "+String.valueOf(j)+" ";
						}
					}
				}
				string=String.valueOf(totBox)+" "+string;//get the scanner number for iterations
			}
		return string;
	}	
	public boolean isPass(int i,int j,double k,double x0,double y0){
		int x1=i;
		int y1=j;
		int x2=i+1;
		int y2=j;
		int x3=i;
		int y3=j+1;
		int x4=i+1;
		int y4=j+1;		
		if((k*(x1-x0)+y0-y1)*(k*(x2-x0)+y0-y2)*(k*(x3-x0)+y0-y3)*(k*(x4-x0)+y0-y4)>0){
			return false;
		}
		else
			return true;		
	}
	
	public String returnPassBox(){
		return null;
	}
	
	public void linkNodeInGraph(nodeInGraph node1,nodeInGraph node2){
		nodeInGraph p=node1;

		
		while(p.next!=null){
			p=p.next;
		}
		p.next=node2;
		
		}
	
	public double distanceTwoPoints(double x1,double y1,double x2,double y2){//attention set the boundary
		return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
	}
	
	//get the  starting index node in the two node road
	public 	toNodeInGraph matchinSearchGraphStartingPoint(long index){
		if(index<deductSize){
			return searchGraph0[(int)index];
		}
		else
			if(index<2*deductSize){
				return searchGraph1[(int) (index-deductSize)];
			}
			else{
				return searchGraph2[(int) (index-deductSize*2)];
			}
	}	
	//main run function
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
		System.out.println("StartMapMatch");
		//trajectory data
		String pathName="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\tTaxi\\all\\all.txt";
		//osm data
		String pathName1="C:\\eFile\\UMRelated\\UM2016-2017Semester1Course\\BigDataAnalysis\\Project\\dataset\\all.txt";
		//in scanner
		Scanner scanner0=new Scanner(new File(pathName));
		Scanner scanner1=new Scanner(new File(pathName1));
		Scanner scanner2=new Scanner(new File(pathName1));		
		wayMatch mapMatching=new wayMatch(scanner1,scanner2,scanner0);
		System.out.println("AfterMapMatching");
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
