package com.aladdin.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.channels.FileChannel;

import org.apache.tomcat.jni.OS;

public class FileUtil {

	/**
	 * Determines whether a given file is underneath a given root.
	 * 
	 * @param root
	 *            the root directory
	 * @param file
	 *            the file to test
	 * @return true if the file is underneath the root, false otherwise or if
	 *         this can not be determined because of security constraints in
	 *         place
	 */
	public static boolean underneathRoot(File root, File file) {
		try {
			// first of all, find the root directory for this type of file
			root = root.getCanonicalFile();
			file = file.getCanonicalFile();
			while (file != null) {
				if (file.equals(root)) {
					return true;
				} else {
					file = file.getParentFile();
				}
			}
		} catch (IOException ioe) {
			return false;
		}

		return false;
	}

	/**
	 * Deletes a file, including all files and sub-directories if the specified
	 * file is a directory.
	 * 
	 * @param directory
	 *            a File instance representing the directory to delete
	 */
	public static void delete(File file) {
		if (file.isDirectory()) {
			File files[] = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				delete(files[i]);
			}
		}
		file.delete();
	}

	/**
	 * Copies one file to another.
	 * 
	 * @param source
	 *            the source
	 * @param destination
	 *            the destination
	 * @throws IOException
	 */
	public static void copy(File source, File destination) throws IOException {
		if (source.isDirectory()) {
			if (!destination.exists()) {
				try {
					destination.setWritable(true);
					destination.mkdirs();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			File files[] = source.listFiles();
			for (int i = 0; i < files.length; i++) {
				copy(files[i], new File(destination, files[i].getName()));
			}
		} else {
			FileInputStream fileInputStream=new FileInputStream(source);
			FileOutputStream fileOutputStream=new FileOutputStream(destination);
			FileChannel srcChannel = fileInputStream.getChannel();
			FileChannel dstChannel =fileOutputStream.getChannel();
			
//			FileChannel srcChannel = new FileInputStream(source).getChannel();
//			FileChannel dstChannel = new FileOutputStream(destination)
//					.getChannel();
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
			dstChannel.force(true);
			srcChannel.close();
			dstChannel.close();
			
			fileInputStream.close();
			fileOutputStream.close();
			
			
		}
	}
	
	public static void inputstreamtofile(InputStream io, File destination){
		
		try {
			OutputStream outputStream = new FileOutputStream(destination);
			int bytesRead=0;
			byte[] buffer=new byte[512];
			while ((bytesRead=io.read(buffer, 0, 512))!=-1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			outputStream.close();
			io.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	public static void move(File source, File destination) throws IOException {
		copy(source, destination);
		delete(source);
	}

	/**
	 * Gets the content type for the specified filename.
	 * 
	 * @param name
	 *            the name of a file
	 * @return a MIME type, or application/octet-stream if one can't be found
	 */
//	public static String getContentType(String name) {
//		FileNameMap fileNameMap = URLConnection.getFileNameMap();
//		String contentType = fileNameMap.getContentTypeFor(name);
//
//		if (contentType == null) {
//			int index = name.lastIndexOf(".");
//			if (index > -1) {
//				contentType = localFileNameMap.getProperty(name
//						.substring(index));
//			}
//		}
//
//		return contentType;
//	}

	public static String getFileExtension(File file) {
		String fname = file.getName();
		if (fname.lastIndexOf(".") != -1 && fname.lastIndexOf(".") != 0) {
			return fname.substring(fname.lastIndexOf(".") + 1);
		} else {
			return "";
		}
	}

	public static String readAsText(File file) throws FileNotFoundException,
			IOException {
		return readAsText(new FileInputStream(file));
	}

	public static String readAsText(String file) throws FileNotFoundException,
			IOException {
		return readAsText(new FileInputStream(file));
	}

	public static String readAsText(InputStream is) throws IOException {
		StringBuffer sb = new StringBuffer();
		Reader fr = new InputStreamReader(is);
		char[] chars = new char[512];
		int len = 0;
		while ((len = fr.read(chars)) > 0) {
			for (int i = 0; i < len; i++) {
				sb.append(chars[i]);
			}
		}
		return sb.toString();
	}

	public static void writeto(InputStream is, String filepath)
			throws IOException {
		OutputStream bos = new FileOutputStream(filepath);
		StreamUtil.copyStream(is, bos);
		bos.flush();
		bos.close();
		is.close();
	}

	public static void writeto(String text, String filepath, boolean append)
			throws IOException {
		int i = filepath.lastIndexOf(File.separator);
		String path = filepath.substring(0, i);
		File target = new File(path);
		if (!target.exists()) {
			target.mkdirs();
		}
		FileWriter fw = new FileWriter(filepath, append);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(text);
		bw.flush();
		bw.close();
	}

	public static void writeto(String text, String filepath) throws IOException {
		writeto(text, filepath, false);
	}

	public static void appendto(String text, String filepath)
			throws IOException {
		writeto(text, filepath, true);
	}
}
