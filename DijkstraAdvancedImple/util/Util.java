package util;

import java.util.Date;

public class Util {

	static long lastTime = 0;
	static long beginTime = 0;

	public static void main(String[] args) throws InterruptedException {
		init();
	}

	public static void init() {
		beginTime = new Date().getTime();
		lastTime = beginTime;
	}

	/**
	 * 打印时间差，单位毫秒
	 */
	public static long printTimeCost(String tag) {
		long now = new Date().getTime();
		long interval = now - lastTime;
		System.out.println(tag + "\t\t" + "TimeCost : \t" + interval);
		lastTime = now;
		return interval;
	}
	
	/**
	 * 打印时间差，单位毫秒
	 */
	public static String printTimeStrCost(String tag) {
		long now = new Date().getTime();
		long interval = now - lastTime;
		
		lastTime = now;
		return tag + "\t\t" + "TimeCost : \t" + interval;
	}

	public static void printTimeCostWithTotalCost(String tag) {
		long now = new Date().getTime();
		long interval = now - lastTime;
		long totalcost = now - beginTime;
		System.out.println(tag + "\t" + "TimeCost : \t" + interval);
		System.out.println("\t" + "TotalCost : \t" + totalcost);
		lastTime = now;
	}
}
