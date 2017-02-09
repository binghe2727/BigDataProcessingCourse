package prj;

import java.io.*;
import java.util.*;

import prj.mapMatchElement.Vnode;
import prj.mapMatchElement.node;



public class mapMatch {
	
	
	public class node{
		int roadID;
		node next;
	}
	
	public class Vnode{
		int id;
		node firstedge;
	}
	
	public Vnode[] mVex;
	
	
	public adListTrajectory a1;
	public Dgraph a2;
	//route structure
	
	mapMatch(Scanner scanner1,Scanner scanner2){
		
		//base on a1, a2 to compute 
		a1=new adListTrajectory(scanner1);
		a2=new Dgraph(scanner2);
		
		
	}
	
	public static void main(String[] args){
		
	}
}
