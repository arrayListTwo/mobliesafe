package com.arrayList.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	
	/**
	 * MD5加密
	 * @param password 明文密码
	 * @return 经过MD5加密的密码
	 */
	public static String encode(String password){
		StringBuffer buffer = new StringBuffer();
		//获得MD5算法对象
		MessageDigest instance;
		try {
			instance = MessageDigest.getInstance("MD5");
			//对字符串进行MD5加密，返回字节数组
			byte[] digest = instance.digest(password.getBytes());
			for (byte b : digest) {
				int i = b & 0xff;  //获取字节的低八位有效值
				String hexString = Integer.toHexString(i);
				if (hexString.length() < 2) {
					hexString = "0" + hexString; //如果是一位，则前面补字符0
				}
				buffer.append(hexString);
			}
			return buffer.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return null;
	}

}
