package com.aladdin.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;

public class TypeConvert {
	public static String byte2hex(byte[] src, int srcPos, int destPos,
			int length) {
		byte[] tmp = new byte[length];
		System.arraycopy(src, srcPos, tmp, destPos, length);
		return HexUtil.toHex(tmp);
	}

	public static byte[] hex2byte(String hex) {
		return HexUtil.toBytes(hex);
	}

	public static String byte2string(byte[] buf, int offset) {
		int pos = offset;
		for (; offset < buf.length; offset++)
			if (buf[offset] == 0)
				break;

		if (offset > pos)
			offset--;
		else if (offset == pos)
			return "";
		int len = (offset - pos) + 1;
		byte bb[] = new byte[len];
		System.arraycopy(buf, pos, bb, 0, len);
		String str = new String(bb);
		return str;
	}

	public static String byte2string(byte[] src, int srcPos, int destPos,
			int length) {
		byte[] tmp = new byte[length];
		System.arraycopy(src, srcPos, tmp, destPos, length);
		return (new String(tmp).trim());

	}

	public static String byte2string(byte[] buf, String enc) {
		try {
			if (buf != null) {
				return new String(buf, enc);
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException ex) {
			return new String(buf);
		}
	}

	public static byte[] string2byte(String s, String enc) {
		try {
			if (!StringUtils.isEmpty(s)) {
				return s.getBytes(enc);
			} else {
				return null;
			}
		} catch (UnsupportedEncodingException ex) {
			return s.getBytes();
		}
	}

	public static Object byte2object(byte[] bytes) {
		ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(bais);
			Object obj = in.readObject();
			return obj;
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException ex) {
			}
		}
		return null;
	}

	public static String object2base64(Object obj) {
		byte[] bytes = TypeConvert.object2byte(obj);
		if (bytes != null) {
			byte[] base64bytes = Base64Util.encode(bytes);
			return new String(base64bytes);
		}
		return null;
	}

	public static Object base642object(String base64) {
		if (StringUtils.isEmpty(base64))
			return null;
		byte[] bytes = Base64Util.decode(base64.getBytes());
		return TypeConvert.byte2object(bytes);
	}

	public static byte[] object2byte(Object obj) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(baos);
			out.writeObject(obj);
		} catch (IOException ex) {
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException ex) {
			}
		}
		return baos.toByteArray();
	}

	public static int byte2int(byte b[]) {
		return b[3] & 0xff | (b[2] & 0xff) << 8 | (b[1] & 0xff) << 16
				| (b[0] & 0xff) << 24;
	}

	public static int byte2int(byte b[], int offset) {
		return b[offset + 3] & 0xff | (b[offset + 2] & 0xff) << 8
				| (b[offset + 1] & 0xff) << 16 | (b[offset] & 0xff) << 24;
	}

	public static long byte2long(byte b[], int offset) {
		return b[offset + 7] & 255L | (b[offset + 6] & 255L) << 8
				| (b[offset + 5] & 255L) << 16 | (b[offset + 4] & 255L) << 24
				| (b[offset + 3] & 255L) << 32 | (b[offset + 2] & 255L) << 40
				| (b[offset + 1] & 255L) << 48 | (long) b[offset] << 56;
	}

	public static long byte2long(byte b[]) {
		return b[7] & 255L | (b[6] & 255L) << 8 | (b[5] & 255L) << 16
				| (b[4] & 255L) << 24 | (b[3] & 255L) << 32
				| (b[2] & 255L) << 40 | (b[1] & 255L) << 48 | (long) b[0] << 56;
	}

	public static short byte2short(byte b[], int offset) {
		return (short) (b[offset + 1] & 0xff | (b[offset] & 0xff) << 8);
	}

	public static short byte2short(byte b[]) {
		return (short) (b[1] & 0xff | (b[0] & 0xff) << 8);
	}

	public static short byte2tinyint(byte b[], int offset) {
		return (short) (b[offset] & 0xff);
	}

	public static void int2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 24);
		buf[offset + 1] = (byte) (n >> 16);
		buf[offset + 2] = (byte) (n >> 8);
		buf[offset + 3] = (byte) n;
	}

	public static byte[] int2byte(int n) {
		byte b[] = new byte[4];
		b[0] = (byte) (n >> 24);
		b[1] = (byte) (n >> 16);
		b[2] = (byte) (n >> 8);
		b[3] = (byte) n;
		return b;
	}

	public static void tinyint2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) n;
	}

	public static byte tinyint2byte(int n) {
		return (byte) n;
	}

	public static void long2byte(long n, byte buf[], int offset) {
		buf[offset] = (byte) (int) (n >> 56);
		buf[offset + 1] = (byte) (int) (n >> 48);
		buf[offset + 2] = (byte) (int) (n >> 40);
		buf[offset + 3] = (byte) (int) (n >> 32);
		buf[offset + 4] = (byte) (int) (n >> 24);
		buf[offset + 5] = (byte) (int) (n >> 16);
		buf[offset + 6] = (byte) (int) (n >> 8);
		buf[offset + 7] = (byte) (int) n;
	}

	public static byte[] long2byte(long n) {
		byte b[] = new byte[8];
		b[0] = (byte) (int) (n >> 56);
		b[1] = (byte) (int) (n >> 48);
		b[2] = (byte) (int) (n >> 40);
		b[3] = (byte) (int) (n >> 32);
		b[4] = (byte) (int) (n >> 24);
		b[5] = (byte) (int) (n >> 16);
		b[6] = (byte) (int) (n >> 8);
		b[7] = (byte) (int) n;
		return b;
	}

	public static void short2byte(int n, byte buf[], int offset) {
		buf[offset] = (byte) (n >> 8);
		buf[offset + 1] = (byte) n;
	}

	public static byte[] short2byte(int n) {
		byte b[] = new byte[2];
		b[0] = (byte) (n >> 8);
		b[1] = (byte) n;
		return b;
	}

	public static byte[] trimRightBytes(byte[] bytes) {
		int length = 0;
		for (int i = 0; i < bytes.length; i++) {
			if (bytes[i] == 0) {
				break;
			} else {
				length = length + 1;
			}
		}
		byte[] newBytes = new byte[length];
		System.arraycopy(bytes, 0, newBytes, 0, length);
		return newBytes;
	}

	public static void fillBytesWithZero(byte[] destArray, int destOffset,
			int length) {
		for (int i = 0; i < length; i++) {
			destArray[destOffset + i] = 0;
		}
	}
}
