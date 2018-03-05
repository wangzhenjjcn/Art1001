package com.aladdin.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class UrlEncoder {
	public static String encodePercent(String s, String enc) {
		try {
			String encs = URLEncoder.encode(s, enc);
			encs = encs.replaceAll("*", "%2A");
			encs = encs.replaceAll("+", "%20");
			encs = encs.replaceAll("%7E", "~");
			return encs;
		} catch (UnsupportedEncodingException ex) {
			return s;
		}
	}

	public static String decodePercent(String s, String enc) {
		try {
			String decs = s;/*
							 * .replaceAll("%2A", "*"); url =
							 * url.replaceAll("%20", "+"); url =
							 * url.replaceAll("~", "%7E");
							 */
			decs = URLDecoder.decode(decs, enc);
			return decs;
		} catch (UnsupportedEncodingException ex) {
			return s;
		}
	}

	public static String encode(String url, String encoding) {
		try {
			return URLEncoder.encode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String decode(String url, String encoding) {
		try {
			return URLDecoder.decode(url, encoding);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static Map<String, String> decodeForm(String form, String enc) {
		Map<String, String> params = new HashMap<String, String>();
		if (!StringUtils.isEmpty(form)) {
			for (String nvp : form.split("\\&")) {
				int equals = nvp.indexOf('=');
				String name;
				String value;
				if (equals < 0) {
					name = decodePercent(nvp, enc);
					value = null;
				} else {
					name = decodePercent(nvp.substring(0, equals), enc);
					value = decodePercent(nvp.substring(equals + 1), enc);
				}
				params.put(name, value);
			}
		}
		return params;
	}

	public static String encodeForm(Map<String, String> params, String enc) {
		StringBuffer sb = new StringBuffer();
		if (params != null) {
			boolean first = true;
			for (Entry<String, String> parameter : params.entrySet()) {
				if (first) {
					first = false;
				} else {
					sb.append('&');
				}
				sb.append(TypeConvert.string2byte(encodePercent(parameter
						.getKey(), enc), enc));
				sb.append('=');
				sb.append(TypeConvert.string2byte(encodePercent(parameter
						.getValue(), enc), enc));
			}
		}
		return sb.toString();
	}
}
