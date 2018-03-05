package com.aladdin.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class LogUtil {
	public static Log log(Class<?> clazz) {
		return LogFactory.getLog(clazz);
	}
}
