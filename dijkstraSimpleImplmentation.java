package dijkstra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class adjListDGraph {
	private static int INF = Integer.MAX_VALUE;
	private class ENode {
        int ivex;       // 该边所指向的顶点的位置
        int weight=1;   //later adding by bing He
        ENode nextEdge; // 指向下一条弧的指针
    }
    
    // 邻接表中表的顶点
    private class VNode {
        int data;          // 顶点信息
        ENode firstEdge;    // 指向第一条依附该顶点的弧
    }
    
    private VNode[] mVexs;  // 顶点数组
    
    public adjListDGraph(String lineAll) {
    	//assume lineAll with all ints
    	Scanner scanner=new Scanner(lineAll);
    	int vlen = scanner.nextInt();
    	int elen = scanner.nextInt();
        if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return ;
        }
        
        // 初始化"顶点"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = i;
            mVexs[i].firstEdge = null;
        }
        
        // 初始化"边"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();
            int p2 = scanner.nextInt();
            // 初始化node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
        }
        scanner.close();
    }
        
    public adjListDGraph(Scanner scanner) {
    	//assume lineAll with all ints
    	//Scanner scanner=new Scanner(lineAll);
    	int vlen = (scanner.nextInt())+7000;// in some data there are more vertex ID than the total
    	int elen = scanner.nextInt();
        //if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
         //   System.out.printf("input error: invalid parameters!\n");
         //   return ;
        //}
        
        // 初始化"顶点"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = i;
            mVexs[i].firstEdge = null;
        }
        
        // 初始化"边"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // 读取边的起始顶点和结束顶点
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();
            int p2 = scanner.nextInt();
            // 初始化node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // 将node1链接到"p1所在链表的末尾"
            if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
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
     * 打印矩阵队列图
     */
    public void print() {
        System.out.printf("List Graph:\n");
        for (int i = 0; i < mVexs.length; i++) {
            System.out.printf("%d(%c): ", i, mVexs[i].data);
            ENode node = mVexs[i].firstEdge;
            while (node != null) {
                System.out.printf("%d(%c) ", node.ivex, mVexs[node.ivex].data);
                node = node.nextEdge;
            }
            System.out.printf("\n");
        }
    }  
        
	
    /*
     * 获取边<start, end>的权值；若start和end不是连通的，则返回无穷大。
     */
    private int getWeight(int start, int end) {

        if (start==end)
            return 0;

        ENode node = mVexs[start].firstEdge;
        while (node!=null) {
            if (end==node.ivex)
                return node.weight;
            node = node.nextEdge;
        }

        return INF;
    }
    
    private int get(int start){
    	ENode node=mVexs[start].firstEdge;
    	int i=0;
    	while(node!=null){
    		i++;
    		node=node.nextEdge;
    	}
    	return i;
    }
	
	public void dijkstra(int vs, int vt) {
        // flag[i]=true表示"顶点vs"到"顶点i"的最短路径已成功获取。
        boolean[] flag = new boolean[mVexs.length];
        int[] dist=new int[mVexs.length];
        // 初始化
        for (int i = 0; i < mVexs.length; i++) {
            flag[i] = false;            // 顶点i的最短路径还没获取到。
            //prev[i] = 0;                // 顶点i的前驱顶点为0。
            dist[i] = getWeight(vs, i); // 顶点i的最短路径为"顶点vs"到"顶点i"的权。
        }
        //System.out.println("finishi initializing\n");
        // 对"顶点vs"自身进行初始化
        flag[vs] = true;
        dist[vs] = 0;

        // 遍历mVexs.length-1次；每次找出一个顶点的最短路径。
        int k = 0;
        
        
        for (int i = 1; i < mVexs.length; i++) {
        	
            // 寻找当前最小的路径；
            // 即，在未获取最短路径的顶点中，找到离vs最近的顶点(k)。
            int min = INF;
            for (int j = 0; j < mVexs.length; j++) {
                if (flag[j]==false && dist[j]<min) {
                    min = dist[j];
                    k = j;
                    //break;//modify because the graph.
                }
                //System.out.println(j+"Being finish finding local mimum\n");
            }
           //
            
            //not all searching
            if(k==vt){
            	//System.out.println(min);
            	break;
            }
            ///
            
            // 标记"顶点k"为已经获取到最短路径
            flag[k] = true;


            
            ENode node=mVexs[k].firstEdge;
            while(node!=null){
            	if((flag[node.ivex]==false)&&((node.weight+min)<dist[node.ivex])){
            		dist[node.ivex]=(node.weight+min);
            	}
            	node=node.nextEdge;
            }     
            
           // 
        }
	}
    

    
    public static void main(String[] args) {

    	
		try { // 防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw			
			System.out.println("start read file and record time \n");
			long a = System.currentTimeMillis();  
			//String pathname = "C:\\Users\\bing\\Desktop\\assign01\\testing01.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径01
			String pathname = "C:\\Users\\bing\\Desktop\\assign01\\Brightkite_edges.txt";
			Scanner scanner=new Scanner(new File(pathname));
			long b = System.currentTimeMillis();
			System.out.println((b-a)+" running time of reading file\n");			
			///////////////////
			System.out.println("Start construct the graph\n");
            adjListDGraph DGraph=new adjListDGraph(scanner);
            a = System.currentTimeMillis();
            System.out.println((a-b)+" running time of construt the grah\n");
            //////
            System.out.println("start dijkstra and query \n");
            Scanner scanner1=new Scanner(new File(pathname));
        	int vlen = scanner1.nextInt();
        	scanner1.close();
        	//DGraph.dijkstra(0,380);
        	//System.out.println(101+" round finish\n");
            for(int j=0;j<100;j++){
            	int s=(int)(0+Math.random()*(vlen-1+1));
            	int t=(int)(0+Math.random()*(vlen-1+1));
            	System.out.println(s+" "+t+"\n");
            	DGraph.dijkstra(s,t);
            	System.out.println(j+" round finish\n");
            }
            b = System.currentTimeMillis();
            System.out.println((b - a)+" running time of query of dijkstra\n");
		} catch (Exception e) {
			e.printStackTrace();
		}		
    	   	
    }
   
}
