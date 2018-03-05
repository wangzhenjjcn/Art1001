package com.aladdin.component;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.Assert;

public class ControllerHelper {
	
	public static Map<String, Object> extractParams(HttpServletRequest request, Object... requiredParams) {
		Map<String, Object> map = new HashMap<>();
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> m : params.entrySet()) {

			String key = m.getKey();
			String[] values = m.getValue();

			if (values != null && values.length == 1) {
				map.put(key, values[0]);
			}
			if (values != null && values.length > 1) {
				map.put(key, values);
			}
		}
		return map;
	}

	public static void assertHas(Map<String, Object> params, String... requiredParams) throws ParamsException {

		Assert.notNull(params);
		Assert.notNull(requiredParams);

		for (String key : requiredParams) {

			int num = key.indexOf(":");
			String paramName = "";

			if (num > 0) {
				paramName = key.split(":")[0];
			} else {
				paramName = key;
			}

			if (!params.containsKey(paramName)) {
				if (num > 0) {
					params.put(key.substring(0, num), key.substring(num + 1));// 设置默认值
				} else {
					throw new ParamsException("参数" + key + "为必填项！");
				}
			}
		}
	}

	public static class ParamsException extends Exception {
		
		private static final long serialVersionUID = 1L;

		public ParamsException(String message) {
			super(message);
		}
	}
}
