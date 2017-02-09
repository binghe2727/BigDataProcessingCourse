package em;

import java.io.*;
import java.util.*;


public class em {

	public int itemNumber;
	public int workNumber;
	public int scale;
	public int[][] scoreMatrix;//from txt//2000*5
	public int[] outputLabelItemByVot;//by voting get result
	public int[] outputLabelItemByVotPrev;//means the last one
	public int[][][] scoreCountByUserIntFile;//5*5*500，worker score counting for perent
	public float[][][] scoreCountByUserPerceFile;//5*5*500 worker score count by percent
	public float[][] outputLabelItemByPercent01;//2000*5 item score by counting percent 0
	public float[][] outputLabelItemByPercent02;//2000*5 item score by percent	
	
	 em(){
		itemNumber=2000;
		workNumber=500;
		scale=5;
		//scale=5;
		scoreMatrix=new int[itemNumber][workNumber];//
		outputLabelItemByVot=new int[itemNumber];
		outputLabelItemByVotPrev=new int[itemNumber];
		
		scoreCountByUserIntFile=new int[scale][scale][workNumber];
		scoreCountByUserPerceFile=new float[scale][scale][workNumber];
		outputLabelItemByPercent01=new float[itemNumber][scale];
		outputLabelItemByPercent02=new float[itemNumber][scale];
	}
	 
		public void readTxtToMatrixV2(String pathName){
			try{
				Scanner scanner=new Scanner(new File(pathName));
				for(int j=0;j<workNumber;j++){
				for(int i=0;i<itemNumber;i++){
					
						scoreMatrix[i][j]=(scanner.nextInt())+2;//
						//System.out.println("---debugging last item");
						//for analysis debugging
						//if(i==(itemNumber)){
							//scoreMatrix[i][j]=-2+2;
						//}
					}
				}
				scanner.close();
			}catch(Exception e1){
				System.exit(0);
			}		
		}
		
		public void VoteForResultV2(){
			

			
			for(int i=0;i<itemNumber;i++){
				
				int count0=0;
				int count1=0;
				int count2=0;
				int count3=0;
				int count4=0;
				
				for(int j=0;j<workNumber;j++){
					switch(scoreMatrix[i][j]){
					case 0:count0++;
							break;
					case 1:count1++;
							break;
					case 2:count2++;
							break;
					case 3:count3++;
							break;
					case 4:count4++;
						    break;
					}
				}
				outputLabelItemByVot[i]=maxIDInt5(count0,count1,count2,count3,count4);			
			}
		}
		
		public void workerScoreV2(){
			//clear 
			//scoreCountByUserIntFile=new int[scale][scale][workNumber];
			//scoreCountByUserPerceFile=new float[scale][scale][workNumber];
			
			for(int i=0;i<scale;i++){
				for(int j=0;j<scale;j++){
					for(int k=0;k<workNumber;k++){
						scoreCountByUserIntFile[i][j][k]=0;
						scoreCountByUserPerceFile[i][j][k]=0;
					}
				}
			}
			
			
			//updating
			for(int i=0;i<workNumber;i++){
				//this.printMatrixInt1(this.outputLabelItemByVot);
				for(int j=0;j<itemNumber;j++){
					//System.out.println((outputLabelItemByVot[j]));
					//System.out.println(j);
					//System.out.println((outputLabelItemByVot[j]));
					//System.out.println(j);
					(scoreCountByUserIntFile[(outputLabelItemByVot[j])][(scoreMatrix[j][i])][i])++;//counting on all items
				}
				//write percent in matrix
				for(int ii=0;ii<scale;ii++){// raw truth searching
					int sum=0;
					for(int jj=0;jj<scale;jj++){//compute each raw
						sum=sum+scoreCountByUserIntFile[ii][jj][i];
					}
					if(sum!=0){
						for(int k=0;k<scale;k++){//compute percent
							scoreCountByUserPerceFile[ii][k][i]=(float) scoreCountByUserIntFile[ii][k][i]/sum;
						}
					}
					else{
						for(int k=0;k<scale;k++){//compute percent
							scoreCountByUserPerceFile[ii][k][i]=(float) 0.0;
						}
					}
				}
			}
			//System.out.println("--DebuggingscoreCountByUserPerceFile");
			//this.printMatrixFloat3(scoreCountByUserPerceFile);
			//System.out.println("-----------end");
		}
		
		public void estimateLabelV2(){
			//System.out.println("----previously showing percent02");
			//this.printMatrixFloat2(this.outputLabelItemByPercent02);
			//outputLabelItemByPercent01=new float[itemNumber][scale];
			//outputLabelItemByPercent01=new float[itemNumber][scale];
			
			for(int i=0;i<itemNumber;i++){
				for(int j=0;j<scale;j++){
					outputLabelItemByPercent01[i][j]=0;
					outputLabelItemByPercent02[i][j]=0;
				}
			}
			
			
			for(int i=0;i<itemNumber;i++){
				for(int j=0;j<workNumber;j++){
					for(int k=0;k<scale;k++){ 
						outputLabelItemByPercent01[i][k]=outputLabelItemByPercent01[i][k]+scoreCountByUserPerceFile[k][(scoreMatrix[i][j])][j];
						//outputLabelItemByPercent01[i][k]=outputLabelItemByPercent01[i][k]+scoreCountByUserPerceFile[(scoreMatrix[i][j])][k][j];
					}
				}
				//System.out.println("----Debuging in estimate labe  for print percent01----");
				//this.printMatrixFloat2(outputLabelItemByPercent01);
				//System.out.println("---------------ending-----");
				//compute the percent in matrix with estimated label
				float sum=0;
				for(int jj=0;jj<scale;jj++){
					sum=sum+outputLabelItemByPercent01[i][jj];
				}
				//System.out.println("----print sum");
				//System.out.println(sum);
				for(int ii=0;ii<scale;ii++){
					outputLabelItemByPercent02[i][ii]=(float)outputLabelItemByPercent01[i][ii]/sum;
				}
			}
			//System.out.println("----Debuging in estimate labe  for print percent02----");
			//this.printMatrixFloat2(this.outputLabelItemByPercent02);
			//System.out.println("---------ending--");
		}
		
//		public int[] writePerentToVoteLabelV2(){
//			int[] a=new int[itemNumber];
//			//this.printMatrixFloat2(this.outputLabelItemByPercent02);
//			for(int i=0;i<itemNumber;i++){
//				a[i]=maxIDFloat5(outputLabelItemByPercent02[i][0],outputLabelItemByPercent02[i][1],outputLabelItemByPercent02[i][2],outputLabelItemByPercent02[i][3],outputLabelItemByPercent02[i][4]);
//			}		
//			this.printMatrixInt1(a);
//			return a;
//		}
		
		public void writePerentToVoteLabelV2(){
			//int[] a=new int[itemNumber];
			//this.printMatrixFloat2(this.outputLabelItemByPercent02);
			for(int i=0;i<itemNumber;i++){
				outputLabelItemByVot[i]=maxIDFloat5(outputLabelItemByPercent02[i][0],outputLabelItemByPercent02[i][1],outputLabelItemByPercent02[i][2],outputLabelItemByPercent02[i][3],outputLabelItemByPercent02[i][4]);
			}		
			//this.printMatrixInt1(a);
			//return a;
		}
		
		public int maxIDInt5(int a,int b, int c, int d,int e){
			int max=a;
			int id=0;
			if(b>max)
				{max=b;id=1;}
			if(c>max)
				{max=c;id=2;}
			if(d>max)
				{max=d;id=3;}
			if(e>max)
				{max=e;id=4;}
			return id;
		}
		
		public int maxIDFloat5(float a,float b, float c, float d,float e){
			float max=a;
			int id=0;
			if(b>max)
				{max=b;id=1;}
			if(c>max)
				{max=c;id=2;}
			if(d>max)
				{max=d;id=3;}
			if(e>max)
				{max=e;id=4;}
			return id;
		}
		
		public int maxID1V2C2(float a,float b){
			float max=a;
			int id=0;
			if(b>max)
				{max=b;id=1;}

			return id;
		}
		
		public void printMatrixInt1(int[] matrix){
			int a=matrix.length;
			for(int i=0;i<a;i++){
				System.out.print(matrix[i]+",");
			}
			System.out.println("\n");
			//return null;
		}
		
		public void printMatrixInt2(int[][] matrix){
			int a=matrix.length;//row number
			int b=matrix[0].length;//coloum number
			for(int i=0;i<a;i++){
				for(int j=0;j<b;j++){
					System.out.print(matrix[i][j]+",");				
				}
				System.out.print("\n");
			}
		}
		
		public void printMatrixInt3(int[][][] matrix){
			int a=matrix.length;
			int b=matrix[0].length;
			int c=matrix[0][0].length;
			//for testing 
			//System.out.println(a);
			//System.out.println(b);
			//System.out.println(c);
			for(int i=0;i<c;i++){//worker
				for(int j=0;j<a;j++){//
					for(int k=0;k<b;k++){
						System.out.print(matrix[j][k][i]+",");
					}
					System.out.print("\n");
				}
				System.out.println("----------------"+"\n");
			}
		}
		
		
		public void printMatrixFloat1(float[] matrix){
			int a=matrix.length;
			for(int i=0;i<a;i++){
				System.out.print(matrix[i]+",");
			}
			System.out.println("\n");
			//return null;
		}
		
		public void printMatrixFloat2(float[][] matrix){
			int a=matrix.length;//row number
			int b=matrix[0].length;//coloum number
			for(int i=0;i<a;i++){
				for(int j=0;j<b;j++){
					System.out.print(matrix[i][j]+",");				
				}
				System.out.print("\n");
			}
		}
		
		public void printMatrixFloat3(float[][][] matrix){
			int a=matrix.length;
			int b=matrix[0].length;
			int c=matrix[0][0].length;
			//for testing 
			//System.out.println(a);
			//System.out.println(b);
			//System.out.println(c);
			for(int i=0;i<c;i++){//worker
				for(int j=0;j<a;j++){//
					for(int k=0;k<b;k++){
						System.out.print(matrix[j][k][i]+",");
					}
					System.out.print("\n");
				}
				System.out.println("----------------"+"\n");
			}
		}
		
		public boolean isConverage(){
			for(int i=0;i<itemNumber;i++){
				if(outputLabelItemByVot[i]!=outputLabelItemByVotPrev[i]){
					return false;
				}
			}
			return true;
		}
		
		public void csvWrite(){
			
		}
		
		public  void testing(String a) { 
	        try { 
	          File csv = new File("C:\\Users\\bing\\Desktop\\writers.csv"); // CSV数据文件
	    
	          BufferedWriter bw = new BufferedWriter(new FileWriter(csv,true)); // 附加
	          // 添加新的数据行
	          bw.write(a); 
	          bw.newLine(); 
	          bw.close(); 
	    
	        } catch (FileNotFoundException e) { 
	          // File对象的创建过程中的异常捕获
	          e.printStackTrace(); 
	        } catch (IOException e) { 
	          // BufferedWriter在关闭对象捕捉异常
	          e.printStackTrace(); 
	        } 
	      } 
		
		public static void main(String[] args){
			
			em a=new em();

			System.out.println("start read file \n");
			String pathname = "C:\\Users\\bing\\Desktop\\hw2\\testBook.txt";	
			String pathname1 = "C:\\Users\\bing\\Desktop\\hw2\\score.txt";	
			String pathname2 = "C:\\Users\\bing\\Desktop\\hw2\\testIn.txt";	
			a.readTxtToMatrixV2(pathname1);		
			
			//a.printMatrixInt2(a.scoreMatrix);
			//System.exit(0);
			
			a.VoteForResultV2();//get the fist time result
			// estimate perfect for generation
			
			
			// test for the coverage,when the last two outputs gets the same.
			System.out.println("start processing \n");
			int iterations=0;
			//a.printMatrix(a.outputLabelItemByVot);
			//a.printMatrix(a.outputLabelItemByVotPrev);
			
			while(!a.isConverage()){
				//a.printMatrix(a.outputLabelItemByVot);
				//a.printMatrix(a.outputLabelItemByVotPrev);
				
				//a.outputLabelItemByVotPrev=a.outputLabelItemByVot;	
				
				
					for(int i=0;i<a.itemNumber;i++){
						a.outputLabelItemByVotPrev[i]=a.outputLabelItemByVot[i];						
					}
				
			
				
				//System.out.println("Compute the work score \n");
				a.workerScoreV2();
				//System.out.println("----In computing:work score");
				//a.printMatrixFloat3(a.scoreCountByUserPerceFile);
				//a.printMatrixInt3(a.scoreCountByUserIntFile);
				
				//a.printMatrix(a.scoreCountByUserIntFile);
				//System.out.println("compute the estimated label \n");
				a.estimateLabelV2();
				//System.out.println("----In computing:estimate label");
				//a.printMatrixFloat2(a.outputLabelItemByPercent02);
				
				//a.outputLabelItemByVot=a.writePerentToVoteLabelV2();
				a.writePerentToVoteLabelV2();
				
				iterations++;				
//				System.out.println("when computing"+"\n");
//				System.out.println(iterations+"\n");
//				System.out.println("-----------------");
//				a.printMatrixInt1(a.outputLabelItemByVot);
//				System.out.println("-----------------");
//				a.printMatrixInt1(a.outputLabelItemByVotPrev);
//				//int[][][] testing=new int[1][2][3];
//				System.out.println("-----------------");				
			}
			

			

			
			System.out.println("After computing"+"\n");
			System.out.println(iterations+"\n");
			System.out.println("-----------------");
			a.printMatrixInt1(a.outputLabelItemByVot);
			System.out.println("-----------------");
			a.printMatrixInt1(a.outputLabelItemByVotPrev);
			//int[][][] testing=new int[1][2][3];
			System.out.println("-----------------");
			a.printMatrixFloat3(a.scoreCountByUserPerceFile);
			System.out.println("----Int Testing-----");
			a.printMatrixInt3(a.scoreCountByUserIntFile);//scoreCountByUserIntFile
			a.printMatrixFloat2(a.outputLabelItemByPercent01);
			System.out.println("----Int Testing-----");
			a.printMatrixFloat2(a.outputLabelItemByPercent02);	
			System.out.println(iterations+"\n");
			System.out.println("-----------------");
			//a.printMatrixInt1(a.outputLabelItemByVot);
			System.out.println("-----------------");
			//a.printMatrixInt1(a.outputLabelItemByVotPrev);
//			int[] reverse=new int[2000];
//			//a.testing();
//			//for reverse number 2 to the output
//			for(int iii=0;iii<2000;iii++){
//				reverse[iii]=a.outputLabelItemByVot[iii]-2;//
//			}
//			
		//-------writing to csv file
			String output=new String();
			output=output+"Converage iterations:"+","+Integer.toString(iterations)+"\n";
			output=output+" True label of items"+"\n"+"item_id"+","+"True_label"+"\n";
			for(int iiii=0;iiii<a.itemNumber;iiii++){
				output=output+Integer.toString(iiii)+","+Integer.toString(a.outputLabelItemByVot[iiii]-2)+"\n";
			}
			output=output+"Worker accuracy:"+"\n";
			for(int iiii=0;iiii<a.workNumber;iiii++){
				output=output+"item_"+Integer.toString(iiii)+","+"-2"+","+"-1"+","+"0"+","+"1"+","+"2"+"\n";
				for(int j=0;j<5;j++){
					output=output+Integer.toString(j-2)+",";
					for(int k=0;k<5;k++){
						output=output+Float.toString(a.scoreCountByUserPerceFile[j][k][iiii])+",";
					}
					output=output+"\n";
				}
				output=output+"\n";
			}
			a.testing(output);
			//a.printMatrixInt1(reverse);		
			}
}

