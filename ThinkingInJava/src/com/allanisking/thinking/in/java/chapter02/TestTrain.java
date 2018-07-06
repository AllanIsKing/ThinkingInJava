package com.allanisking.thinking.in.java.chapter02;

/**
 * @author CHENRONGXUAN
 * @version Create Time : 2018年7月5日 下午8:42:54
 */
public class TestTrain {
	public static void main(String[] args) {
		testTrain01();
		testTrain02();
		testTrain02();
	}

	public static void testTrain01() {
		Train01 train2_01 = new Train01();
		System.out.println("i:" + train2_01.i);// 初始化0
		System.out.println("char:" + train2_01.ch);// 初始化空格
	}

	public static void testTrain02() {
		System.out.println(Train02.hello("world"));
	}
}
