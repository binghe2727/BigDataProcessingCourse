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
    private VNode[] mVexs;  // ��������
	
	//the node in graph of adjacent list
	private class ENode {
        int ivex;       // �ñ���ָ��Ķ����λ��
        float pos1;   //later adding by bing He
        float pos2;
        float dist;
        float countingTime;
        float timeCost;
        ENode nextEdge; // ָ����һ������ָ��
        //later changing for mapMatching
        int roadID;
        }
	
    // �ڽӱ��б�Ķ���
    private class VNode {
        int key;          // ������Ϣ
        float pos1;
        float pos2;
        //later changing:for record of road total number 
        int totRoadNum;
        ENode firstEdge;    // ָ���һ�������ö���Ļ�
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
        
        // ��ʼ��"����"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].key = i;
            mVexs[i].firstEdge = null;
        }
        
        // ��ʼ��"��"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // ��ȡ�ߵ���ʼ����ͽ�������
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();//starting point
            //int p2 = scanner.nextInt();//ending point
            // ��ʼ��node1
            
            //node1.ivex = p2;
            // ��node1���ӵ�"p1���������ĩβ"
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
     * ��node�ڵ����ӵ�list�����
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
    		//String pathname = "C:\\Users\\bing\\Desktop\\assign01\\testing01.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��01
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
