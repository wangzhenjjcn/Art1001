package com.aladdin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	
	public static final String TYPE1="yyyy-MM-dd HH:mm:ss";
	
	public static String dateToString(Date date){
		SimpleDateFormat df = new SimpleDateFormat(TYPE1);
		String res = df.format(date);
		return res;
	}
	
}
