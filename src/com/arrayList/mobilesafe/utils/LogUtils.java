package com.arrayList.mobilesafe.utils;

import android.util.Log;

/**
 * 封装的打印日志的Log类
 * @author arrayList
 *
 */
public class LogUtils {
	
	/**
	 * 日志级别是VERBOSE时代表的常量
	 */
	private static final int VERBOSE = 1;
	
	/**
	 * 日志级别是DEBUG时代表的常量
	 */
	private static final int DEBUG = 2;
	
	/**
	 * 日志级别是INFO时代表的常量
	 */
	private static final int INFO = 3;
	
	/**
	 * 日志级别是WARN时代表的常量
	 */
	private static final int WARN = 4;
	
	/**
	 * 日志级别是ERROR时代表的常量
	 */
	private static final int ERROR = 5;
	
	/**
	 * 当LEVEL常量是NOTHING时，表示日志不输出
	 */
	private static final int NOTHING = 6;
	
	/**
	 * 日志级别
	 */
	private static final int LEVEL = VERBOSE;
	
	/**
	 * 当LEVEL值等于或者小于VERBOSE值时，打印verbose级别的日志
	 * @param tag 日志的标识
	 * @param msg 日志的内容
	 */
	public static void v(String tag, String msg){
		if (LEVEL <= VERBOSE) {
			Log.v(tag, msg);
		}
	}
	
	/**
	 * 当LEVEL值等于或者小于DEBUG值时，打印debug级别的日志
	 * @param tag 日志的标识
	 * @param msg 日志的内容
	 */
	public static void d(String tag, String msg){
		if (LEVEL <= DEBUG) {
			Log.d(tag, msg);
		}
	}
	
	/**
	 * 当LEVEL值等于或者小于INFO值时，打印info级别的日志
	 * @param tag 日志的标识
	 * @param msg 日志的内容
	 */
	public static void i(String tag, String msg){
		if (LEVEL <= INFO) {
			Log.i(tag, msg);
		}
	}
	
	/**
	 * 当LEVEL值等于或者小于WARN值时，打印warn级别的日志
	 * @param tag 日志的标识
	 * @param msg 日志的内容
	 */
	public static void w(String tag, String msg){
		if (LEVEL <= WARN) {
			Log.w(tag, msg);
		}
	}
	
	/**
	 * 当LEVEL值等于或者小于ERROR值时，打印error级别的日志
	 * @param tag 日志的标识
	 * @param msg 日志的内容
	 */
	public static void e(String tag, String msg){
		if (LEVEL <= ERROR) {
			Log.e(tag, msg);
		}
	}

}
