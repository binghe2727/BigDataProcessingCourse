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
        int ivex;       // �ñ���ָ��Ķ����λ��
        int weight=1;   //later adding by bing He
        ENode nextEdge; // ָ����һ������ָ��
    }
    
    // �ڽӱ��б�Ķ���
    private class VNode {
        int data;          // ������Ϣ
        ENode firstEdge;    // ָ���һ�������ö���Ļ�
    }
    
    private VNode[] mVexs;  // ��������
    
    public adjListDGraph(String lineAll) {
    	//assume lineAll with all ints
    	Scanner scanner=new Scanner(lineAll);
    	int vlen = scanner.nextInt();
    	int elen = scanner.nextInt();
        if ( vlen < 1 || elen < 1 || (elen > (vlen*(vlen - 1)))) {
            System.out.printf("input error: invalid parameters!\n");
            return ;
        }
        
        // ��ʼ��"����"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = i;
            mVexs[i].firstEdge = null;
        }
        
        // ��ʼ��"��"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // ��ȡ�ߵ���ʼ����ͽ�������
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();
            int p2 = scanner.nextInt();
            // ��ʼ��node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // ��node1���ӵ�"p1���������ĩβ"
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
        
        // ��ʼ��"����"
        mVexs = new VNode[vlen+1];
        for (int i = 0; i < mVexs.length; i++) {
            //System.out.printf("vertex(%d): ", i);
            mVexs[i] = new VNode();
            mVexs[i].data = i;
            mVexs[i].firstEdge = null;
        }
        
        // ��ʼ��"��"
        //mMatrix = new int[vlen][vlen];
        for (int i = 0; i < elen; i++) {
            // ��ȡ�ߵ���ʼ����ͽ�������
            //System.out.printf("edge(%d):", i);
            int p1 = scanner.nextInt();
            int p2 = scanner.nextInt();
            // ��ʼ��node1
            ENode node1 = new ENode();
            node1.ivex = p2;
            // ��node1���ӵ�"p1���������ĩβ"
            if(mVexs[p1].firstEdge == null)
              mVexs[p1].firstEdge = node1;
            else
                linkLast(mVexs[p1].firstEdge, node1);
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
     * ��ӡ�������ͼ
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
     * ��ȡ��<start, end>��Ȩֵ����start��end������ͨ�ģ��򷵻������
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
        // flag[i]=true��ʾ"����vs"��"����i"�����·���ѳɹ���ȡ��
        boolean[] flag = new boolean[mVexs.length];
        int[] dist=new int[mVexs.length];
        // ��ʼ��
        for (int i = 0; i < mVexs.length; i++) {
            flag[i] = false;            // ����i�����·����û��ȡ����
            //prev[i] = 0;                // ����i��ǰ������Ϊ0��
            dist[i] = getWeight(vs, i); // ����i�����·��Ϊ"����vs"��"����i"��Ȩ��
        }
        //System.out.println("finishi initializing\n");
        // ��"����vs"������г�ʼ��
        flag[vs] = true;
        dist[vs] = 0;

        // ����mVexs.length-1�Σ�ÿ���ҳ�һ����������·����
        int k = 0;
        
        
        for (int i = 1; i < mVexs.length; i++) {
        	
            // Ѱ�ҵ�ǰ��С��·����
            // ������δ��ȡ���·���Ķ����У��ҵ���vs����Ķ���(k)��
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
            
            // ���"����k"Ϊ�Ѿ���ȡ�����·��
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

    	
		try { // ��ֹ�ļ��������ȡʧ�ܣ���catch��׽���󲢴�ӡ��Ҳ����throw			
			System.out.println("start read file and record time \n");
			long a = System.currentTimeMillis();  
			//String pathname = "C:\\Users\\bing\\Desktop\\assign01\\testing01.txt"; // ����·�������·�������ԣ������Ǿ���·����д���ļ�ʱ��ʾ���·��01
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
