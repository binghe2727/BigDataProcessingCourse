package prj;
/*
 * read the trajectory data to do the mapMatching
 */
import java.io.*;
import java.util.*;




public class adListTrajectory {
	private class endPoint{
		float time;
		float pos1;
		float pos2;
		endPoint next;
	}
	
    // 邻接表中表的顶点
    private class startPoint {
        int userID;          // 顶点信息
        //float pos1;
        //float pos2;
        endPoint firstEdge;    // 指向第一条依附该顶点的弧
    }
    //array
    private startPoint[] mVexs;  // 顶点数组
	
	adListTrajectory(Scanner scanner){
		int userNumber=scanner.nextInt();//number of userID
		int trajectNumber=scanner.nextInt();//number of all trajectory point
		mVexs=new startPoint[userNumber];
		
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new startPoint();
            mVexs[i].userID = i;
            mVexs[i].firstEdge = null;
        }
        
        for (int i = 0; i < trajectNumber; i++) {
            // 读取边的起始顶点和结束顶点
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();//id
            //int p2 = scanner.nextInt();//ending point
            // 初始化node1            
            //node1.ivex = p2;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
              {
            	//initializing the head
            	endPoint node1 = new endPoint();
            	mVexs[p1].firstEdge = node1;
            	

            	//read next vertex            	
            	//node1.ivex=scanner.nextInt();
            	node1.time=scanner.nextFloat();
            	node1.pos1=scanner.nextFloat();
            	node1.pos2=scanner.nextFloat();
            	//node1.dist=distance4Point(mVexs[p1].pos1,mVexs[p1].pos2,node1.pos1,node1.pos2);            	
            	}
            else{
            	endPoint node1 = new endPoint();
            	node1.time=scanner.nextFloat();
            	node1.pos1=scanner.nextFloat();
            	node1.pos2=scanner.nextFloat();
            	//node1.dist=distance4Point(mVexs[p1].pos1,mVexs[p1].pos2,node1.pos1,node1.pos2);
                linkLast(mVexs[p1].firstEdge, node1);                
                }
        }		
	}
	
    /*
     * 将node节点链接到list的最后
     */
		private void linkLast(endPoint list, endPoint node) {
			endPoint p = list;
			while(p.next!=null)
				p = p.next;
				p.next = node;
			}
		
	    public static void main(String[] args){
	    	try{
	    		System.out.println("start read file and record time \n");
	    		//String pathname = "C:\\Users\\bing\\Desktop\\assign01\\testing01.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径01
	    		String pathname = "C:\\Users\\bing\\Desktop\\assign01\\roadNet-CA.txt";
	    		Scanner scanner=new Scanner(new File(pathname));
	    		adListTrajectory a=new adListTrajectory(scanner);
	    		scanner.close();
	    	}
	    		 catch (Exception e) {
	    			e.printStackTrace();
	    		}	    
	    }
}
