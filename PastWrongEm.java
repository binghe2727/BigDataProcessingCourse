package em;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class em {
	public int itemNumber;
	public int workNumber;
	public int scale;
	public int[][] scoreMatrix;//from txt
	public int[] outputLabelItemByVot;//by voting get result
	public int[] outputLabelItemByVotNext;//means the last one
	public int[][][] scoreCountByUserIntFile;//5*5*500£¬worker score counting for perent
	public float[][][] scoreCountByUserPerceFile;//5*5*500 worker score count by percent
	public float[][] outputLabelItemByPercent01;//2000*5 item score by counting percent 0
	public float[][] outputLabelItemByPercent02;//2000*5 item score by percent	
	 em(){
		itemNumber=5;
		workNumber=5;
		scale=5;
		//scale=5;
		scoreMatrix=new int[itemNumber][workNumber];//
		outputLabelItemByVot=new int[itemNumber];
		outputLabelItemByVotNext=new int[itemNumber];
		
		scoreCountByUserIntFile=new int[scale][scale][workNumber];
		scoreCountByUserPerceFile=new float[scale][scale][workNumber];
		outputLabelItemByPercent01=new float[itemNumber][scale];
		outputLabelItemByPercent02=new float[itemNumber][scale];
	}
	
	
	public void readTxtToMatrix(String pathName){
		try{
			Scanner scanner=new Scanner(new File(pathName));
			for(int i=0;i<itemNumber;i++){
				for(int j=0;j<workNumber;j++){
					scoreMatrix[i][j]=scanner.nextInt();
				}
			}
			scanner.close();
		}catch(Exception e1){
			System.exit(0);
		}
		
	}
	
	public void readTxtToMatrixV2(String pathName){
		try{
			Scanner scanner=new Scanner(new File(pathName));
			for(int i=0;i<itemNumber;i++){
				for(int j=0;j<workNumber;j++){
					scoreMatrix[i][j]=scanner.nextInt()+2;
				}
			}
			scanner.close();
		}catch(Exception e1){
			System.exit(0);
		}		
	}
	
	public void VoteForResult(int[][] matrix){
		for(int i=0;i<itemNumber;i++){
			int countMin2=0;
			int countMin1=0;
			int count0=0;
			int countMax1=0;
			int countMax2=0;
			for(int j=0;j<workNumber;j++){
				switch(matrix[i][j]){
				case -2:countMin2++;
						break;
				case -1:countMin1++;
						break;
				case 0:count0++;
						break;
				case 1:countMax1++;
						break;
				case 2:countMax2++;
					    break;
				}
			}
			outputLabelItemByVot[i]=maxID(countMin2,countMin1,count0,countMax1,countMax2);			
		}
	}
	
	public void VoteForResultV2(int[][] matrix){
		for(int i=0;i<itemNumber;i++){
			int count0=0;
			int count1=0;
			int count2=0;
			int count3=0;
			int count4=0;
			for(int j=0;j<workNumber;j++){
				switch(matrix[i][j]){
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
			outputLabelItemByVot[i]=maxID(count0,count1,count2,count3,count4);			
		}
	}
	
	public void workerScore(){
		for(int i=0;i<workNumber;i++){
			for(int j=0;j<itemNumber;j++){
				scoreCountByUserIntFile[(outputLabelItemByVot[j]+2)][(scoreMatrix[j][i]+2)][i]++;//counting by column
			}
			//write percent in matrix
			for(int ii=0;ii<scale;ii++){// raw truth searching
				int sum=0;
				for(int jj=0;jj<scale;jj++){
					sum=sum+scoreCountByUserIntFile[ii][jj][i];
				}
				for(int k=0;k<scale;k++){
					scoreCountByUserPerceFile[ii][k][i]=(float) scoreCountByUserIntFile[ii][k][i]/sum;
				}
			}
		}
	}
	
	public void workerScoreV2(){
		for(int i=0;i<workNumber;i++){
			for(int j=0;j<itemNumber;j++){
				System.out.println((outputLabelItemByVot[j]));
				System.out.println(j);
				scoreCountByUserIntFile[(outputLabelItemByVot[j])][(scoreMatrix[j][i])][i]++;//counting on all items
			}
			//write percent in matrix
			for(int ii=0;ii<scale;ii++){// raw truth searching
				int sum=0;
				for(int jj=0;jj<scale;jj++){//compute each raw
					sum=sum+scoreCountByUserIntFile[ii][jj][i];
				}
				for(int k=0;k<scale;k++){//compute percent
					scoreCountByUserPerceFile[ii][k][i]=(float) scoreCountByUserIntFile[ii][k][i]/sum;
				}
			}
		}
	}
	
	public void estimateLabel(){
		for(int i=0;i<itemNumber;i++){
			for(int j=0;j<workNumber;j++){
				for(int k=0;k<scale;k++){ 
					outputLabelItemByPercent01[i][k]=outputLabelItemByPercent01[i][k]+scoreCountByUserPerceFile[k][(scoreMatrix[i][j]+2)][j];
				}
			}
			//compute the percent in matrix with estimated label
			float sum=0;
			for(int jj=0;jj<scale;jj++){
				sum=sum+outputLabelItemByPercent01[i][jj];
			}
			for(int ii=0;ii<scale;ii++){
				outputLabelItemByPercent02[i][ii]=outputLabelItemByPercent01[i][ii]/sum;
			}
		}
	}
	
	public void estimateLabelV2(){
		for(int i=0;i<itemNumber;i++){
			for(int j=0;j<workNumber;j++){
				for(int k=0;k<scale;k++){ 
					outputLabelItemByPercent01[i][k]=outputLabelItemByPercent01[i][k]+scoreCountByUserPerceFile[k][(scoreMatrix[i][j])][j];
				}
			}
			//compute the percent in matrix with estimated label
			float sum=0;
			for(int jj=0;jj<scale;jj++){
				sum=sum+outputLabelItemByPercent01[i][jj];
			}
			for(int ii=0;ii<scale;ii++){
				outputLabelItemByPercent02[i][ii]=outputLabelItemByPercent01[i][ii]/sum;
			}
		}
	}
	
	//rewirte the percentage to the outputLabelSingeIntValue
	public int[] writePerentToVoteLabel(){
		int[] a=new int[itemNumber];
		for(int i=0;i<itemNumber;i++){
			a[i]=maxID1(outputLabelItemByPercent02[i][0],outputLabelItemByPercent02[i][1],outputLabelItemByPercent02[i][2],outputLabelItemByPercent02[i][3],outputLabelItemByPercent02[i][4]);
		}		
		return a;
	}
	
	public int[] writePerentToVoteLabelV2(){
		int[] a=new int[itemNumber];
		for(int i=0;i<itemNumber;i++){
			a[i]=maxID1(outputLabelItemByPercent02[i][0],outputLabelItemByPercent02[i][1],outputLabelItemByPercent02[i][2],outputLabelItemByPercent02[i][3],outputLabelItemByPercent02[i][4]);
		}		
		return a;
	}
	
	
	
//	public void writeworkerScore(int workerID, int score, int truelable){
//		//scoreMatrix
//		
//	}
	
	public int maxID(int a,int b, int c, int d,int e){
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
	
	public int maxID1(float a,float b, float c, float d,float e){
		float max=a;
		int id=-2;
		if(b>max)
			{max=b;id=-1;}
		if(c>max)
			{max=c;id=0;}
		if(d>max)
			{max=d;id=1;}
		if(e>max)
			{max=e;id=2;}
		return id;
	}
	
	public int maxID1V2C2(float a,float b){
		float max=a;
		int id=0;
		if(b>max)
			{max=b;id=1;}

		return id;
	}
	
	public void printMatrix(int[] matrix){
		int a=matrix.length;
		for(int i=0;i<a;i++){
			System.out.print(matrix[i]+",");
		}
		System.out.println("\n");
		//return null;
	}
	
	public void printMatrix(int[][] matrix){
		int a=matrix.length;//row number
		int b=matrix[0].length;//coloum number
		for(int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				System.out.print(matrix[i][j]+",");				
			}
			System.out.print("\n");
		}
	}
	
	public void printMatrix(int[][][] matrix){
		int a=matrix.length;
		int b=matrix[0].length;
		int c=matrix[0][0].length;
		//for testing 
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
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
	
	
	public void printMatrix(float[] matrix){
		int a=matrix.length;
		for(int i=0;i<a;i++){
			System.out.print(matrix[i]+",");
		}
		System.out.println("\n");
		//return null;
	}
	
	public void printMatrix(float[][] matrix){
		int a=matrix.length;//row number
		int b=matrix[0].length;//coloum number
		for(int i=0;i<a;i++){
			for(int j=0;j<b;j++){
				System.out.print(matrix[i][j]+",");				
			}
			System.out.print("\n");
		}
	}
	
	public void printMatrix(float[][][] matrix){
		int a=matrix.length;
		int b=matrix[0].length;
		int c=matrix[0][0].length;
		//for testing 
		System.out.println(a);
		System.out.println(b);
		System.out.println(c);
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
	

	
	public static void main(String[] args){
		
		em a=new em();

		System.out.println("start read file \n");
		String pathname = "C:\\Users\\bing\\Desktop\\hw2\\testBookV5.txt";		
		a.readTxtToMatrixV2(pathname);		
		a.VoteForResultV2(a.scoreMatrix);//get the fist time result
		// test for the coverage,when the last two outputs gets the same.
		System.out.println("start processing \n");
		int iterations=0;
		//a.printMatrix(a.outputLabelItemByVot);
		//a.printMatrix(a.outputLabelItemByVotNext);
		
		while(!Arrays.equals(a.outputLabelItemByVot, a.outputLabelItemByVotNext)){
			a.printMatrix(a.outputLabelItemByVot);
			a.printMatrix(a.outputLabelItemByVotNext);
			a.outputLabelItemByVotNext=a.outputLabelItemByVot;
			
			System.out.println("Compute the work score \n");
			a.workerScoreV2();
			//a.printMatrix(a.scoreCountByUserIntFile);
			System.out.println("compute the estimated label \n");
			a.estimateLabelV2();
			a.outputLabelItemByVot=a.writePerentToVoteLabelV2();
			iterations++;
		}
		System.out.println(iterations+"\n");
		a.printMatrix(a.outputLabelItemByVot);
		a.printMatrix(a.outputLabelItemByVotNext);
		//int[][][] testing=new int[1][2][3];
		a.printMatrix(a.scoreCountByUserPerceFile);
		System.out.println("----Int Testing-----");
		a.printMatrix(a.scoreCountByUserIntFile);//scoreCountByUserIntFile
		a.printMatrix(a.outputLabelItemByPercent01);
		System.out.println("----Int Testing-----");
		a.printMatrix(a.outputLabelItemByPercent02);		
	}
}
