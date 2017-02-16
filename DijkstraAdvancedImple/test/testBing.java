package test;
import java.io.*;
public class testBing {
	int a=999;
	@Override
	public String toString(){
		return a+":";
	}
	public static void main(String args[]){		
		int[] a=new int[3];
		for(int b:a){
			b=1;
		}
		System.out.println(a[1]);
	}
}
