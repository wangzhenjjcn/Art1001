package com.aladdin.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;
import java.util.regex.Pattern;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class StringUtils extends org.springframework.util.StringUtils{
	
	private static final String NUMERIC_PATTERN="(\\d+\\.)?\\d+";
	
	private static final String NUMBER_PATTERN="\\d+";
	
	public static boolean isNotEmpty(String str){
		return !(isEmpty(str));
	}
	
	/**
	 * 获取32位的UUID 编码
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		String newuuid = String.valueOf(uuid).replace("-", "");
		return newuuid;
	}
	
	/**
	 * base64加密
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str){
		if(isEmpty(str)){
			return null;
		}
		String res="";
		try {
			byte[] bs=str.getBytes("utf-8");
			BASE64Encoder base64Encoder=new BASE64Encoder();
			res=base64Encoder.encode(bs);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
		
	}
	
	/**
	 * base64解密
	 * @param str
	 * @return
	 */
	public String base64Decode(String str){
		BASE64Decoder base64Decoder=new BASE64Decoder();
		byte[] bs=null;
		String res="";
		try {
			bs = base64Decoder.decodeBuffer(str);
			res=new String(bs, "utf-8");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return res;
	}
	
	public static boolean isNumeric(String str) {
		return Pattern.compile(NUMERIC_PATTERN).matcher(str).matches();
	}
	
	public static boolean isNumber(String str) {
		return Pattern.compile(NUMBER_PATTERN).matcher(str).matches();
	}
	
	
}
