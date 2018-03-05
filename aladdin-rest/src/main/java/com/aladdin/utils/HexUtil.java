package com.aladdin.utils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class HexUtil {
	public final static char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

	public static byte[] toBytes(String hex) {
		int length = hex.length();
		byte[] bHex = new byte[length / 2];
		String temp = null;
		int t = 0;
		for (int i = 0; i < length; i++) {
			temp = "" + hex.charAt(i) + hex.charAt(++i);
			bHex[t++] = (byte) Integer.parseInt(temp, 16);
		}
		return bHex;
	}

	public static String toHex(byte[] in) {
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(in));
		String str = "";
		try {
			for (int j = 0; j < in.length; j++) {
				String tmp = Integer.toHexString(data.readUnsignedByte());
				if (tmp.length() == 1) {
					tmp = "0" + tmp;
				}
				str = str + tmp;
			}
		} catch (Exception ex) {
		}
		return str;
	}

	public static String toHex(byte in) {
		byte[] bytes = new byte[1];
		bytes[0] = in;
		DataInputStream data = new DataInputStream(new ByteArrayInputStream(
				bytes));
		String str = "";
		try {
			for (int j = 0; j < bytes.length; j++) {
				String tmp = Integer.toHexString(data.readUnsignedByte());
				if (tmp.length() == 1) {
					tmp = "0" + tmp;
				}
				str = str + tmp;
			}
		} catch (Exception ex) {
		}
		return str;
	}
}
