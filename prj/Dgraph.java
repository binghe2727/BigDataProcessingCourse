/*
 * prj.java for the class of graph
 */

package prj;

import java.io.*;
import java.util.*;

public class Dgraph {
	
	public int vlen;//vertex number 
	public int elen;//edge number
    //array
    private VNode[] mVexs;  // 顶点数组
	
	//the node in graph of adjacent list
	private class ENode {
        int ivex;       // 该边所指向的顶点的位置
        float pos1;   //later adding by bing He
        float pos2;
        float dist;
        float countingTime;
        float timeCost;
        ENode nextEdge; // 指向下一条弧的指针
        //later changing for mapMatching
        int roadID;
        }
	
    // 邻接表中表的顶点
    private class VNode {
        int key;          // 顶点信息
        float pos1;
        float pos2;
        //later changing:for record of road total number 
        int totRoadNum;
        ENode firstEdge;    // 指向第一条依附该顶点的弧
    }
    

    
    public Dgraph(Scanner scanner) {
    	//assume lineAll with all ints
    	//Scanner scanner=new Scanner(lineAll);
    	
    	 vlen = scanner.nextInt();// in some data there are more vertex ID than the total
    	 elen = scanner.nextInt();
    	
        //if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
        //   System.out.printf("input error: invalid parameters!\n");
        //   return ;
        //}
        
        // 初始化"顶点"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].key = i;
            mVexs[i].firstEdge = null;
        }
        
        // 初始化"边"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();//starting point
            //int p2 = scanner.nextInt();//ending point
            // 初始化node1
            
            //node1.ivex = p2;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
              {
            	//initializing the head
            	ENode node1 = new ENode();
            	mVexs[p1].firstEdge = node1;
            	mVexs[p1].pos1=scanner.nextFloat();
            	mVexs[p1].pos2=scanner.nextFloat();
            	mVexs[p1].totRoadNum++;
            	//read next vertex            	
            	node1.ivex=scanner.nextInt();
            	node1.pos1=scanner.nextFloat();
            	node1.pos2=scanner.nextFloat();
            	node1.dist=distance4Point(mVexs[p1].pos1,mVexs[p1].pos2,node1.pos1,node1.pos2); 
            	node1.roadID=i;
            	}
            else{
            	ENode node1 = new ENode();
            	scanner.nextFloat();
            	scanner.nextFloat();
            	mVexs[p1].totRoadNum++;            	
            	node1.ivex=scanner.nextInt();
            	node1.pos1=scanner.nextFloat();
            	node1.pos2=scanner.nextFloat();
            	node1.dist=distance4Point(mVexs[p1].pos1,mVexs[p1].pos2,node1.pos1,node1.pos2);
            	node1.roadID=i;
                linkLast(mVexs[p1].firstEdge, node1);                
                }
        }
        scanner.close();
    }
    
    /*
     * 将node节点链接到list的最后
     */
    private void linkLast(ENode list, ENode node) {
        ENode p = list;
        while(p.nextEdge!=null)
            p = p.nextEdge;
        p.nextEdge = node;
    }
    
    /*
     * distance computing
     */
    private float distance4Point(float a,float b,float c,float d){
    	float dist;
    	dist=(a-c)*(a-c)+(b-d)*(b-d);
    	return dist;
    }
    
    public static void main(String[] args){
    	try{
    		System.out.println("start read file and record time \n");
    		//String pathname = "C:\\Users\\bing\\Desktop\\assign01\\testing01.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径01
    		String pathname = "C:\\Users\\bing\\Desktop\\assign01\\roadNet-CA.txt";
    		Scanner scanner=new Scanner(new File(pathname));
    		Dgraph a=new Dgraph(scanner);
    		scanner.close();
    	}
    		 catch (Exception e) {
    			e.printStackTrace();
    		}	    
    }
    }
