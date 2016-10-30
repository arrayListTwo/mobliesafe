package com.arrayList.mobilesafe.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 读取流的工具
 * @author lei
 *
 */
public class StreamUtils {
	
	/**
	 * 将输入流读取成String返回
	 * @param inputStream 输入流
	 * @return 字符串
	 * @throws IOException IO异常 
	 */
	public static String readFromStream(InputStream inputStream) throws IOException{
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = inputStream.read(buffer)) != -1) {
			arrayOutputStream.write(buffer, 0, len);
		}
		String result = arrayOutputStream.toString();
		inputStream.close();
		arrayOutputStream.close();
		return result;
	}

}
