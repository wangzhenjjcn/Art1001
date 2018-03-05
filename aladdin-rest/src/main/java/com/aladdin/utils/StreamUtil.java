package com.aladdin.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;

public class StreamUtil {
	public static String getStreamContents(InputStream is) {
		try {
			char[] buffer = new char[65536];
			StringBuilder out = new StringBuilder();
			Reader in = new InputStreamReader(is, "UTF-8");
			int read;
			do {
				read = in.read(buffer, 0, buffer.length);
				if (read <= 0)
					continue;
				out.append(buffer, 0, read);
			} while (read >= 0);
			in.close();
			return out.toString();
		} catch (IOException ioe) {
			throw new IllegalStateException(
					"Error while reading response body", ioe);
		}
	}

	public static void copyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			int len = 0;
			while ((len = is.read(bytes, 0, buffer_size)) != -1) {
				os.write(bytes, 0, len);
			}
		} catch (Exception ex) {
		}
	}
}
