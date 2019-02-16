package org.support.project.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;

public class FileUtil {
	/** ログ */
	private static Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * ファイルを削除します ディレクトリの場合、中身をまず削除し、確実に削除ができます。
	 * 
	 * @param f
	 */
	public static void delete(File f) {
		if (f.exists() == false) {
			return;
		}

		if (f.isFile()) {
			if (!f.delete()) {
				f.deleteOnExit();
			}
		}
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; i < files.length; i++) {
				delete(files[i]);
			}
			f.delete();
		}
	}

	/**
	 * ファイル名から拡張子を取得する
	 * 
	 * @param filename
	 *            拡張子付き文字列
	 * @return 拡張子
	 */
	public static String getExtension(File file) {
		return StringUtils.getExtension(file.getPath());
	}

	/**
	 * ファイル名称の取得(パスの最後のファイル名)
	 * 
	 * @param fileName
	 *            ファイル名称(パス含む)
	 * @return ファイル名称
	 */
	public static String getFileName(File file) {
		return StringUtils.getFileName(file.getPath());
	}

	/**
	 * コピー
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
		// int count = 0;
		byte[] buff = new byte[2048];
		int len = 0;
		while ((len = inputStream.read(buff)) != -1) {
			outputStream.write(buff, 0, len);
			// count += len;
			outputStream.flush();
		}
		// log.info("copy : " + count + " bytes");
	}

	/**
	 * ファイルへテキストを書き込み(UTF-8)
	 * 
	 * @param file
	 * @param string
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	public static void write(File file, String string) throws FileNotFoundException, UnsupportedEncodingException, IOException {
		PrintWriter pw = null;
		try {
			// 出力ストリームの生成（文字コード指定）
			FileOutputStream fos = new FileOutputStream(file);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
			pw = new PrintWriter(osw);
			// ファイルへの書き込み
			pw.write(string);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}

	/**
	 * テキストを書き込み
	 * @param out
	 * @param string
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void write(OutputStream out, String string) throws UnsupportedEncodingException, IOException {
		write(out, string, "UTF-8");
	}

	/**
	 * テキストを書き込み
	 * @param out
	 * @param string
	 * @param encode
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static void write(OutputStream out, String string, String encode) throws UnsupportedEncodingException, IOException {
		PrintWriter pw = null;
		try {
			// 出力ストリームの生成（文字コード指定）
			OutputStreamWriter osw = new OutputStreamWriter(out, encode);
			pw = new PrintWriter(osw);
			// ファイルへの書き込み
			pw.write(string);
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
	}
	
	/**
	 * テキストを読み込み
	 * @param in
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String read(InputStream in) throws UnsupportedEncodingException, IOException {
		return read(in, "UTF-8");
	}
	
	/**
	 * テキストを読み込み
	 * @param out
	 * @param string
	 * @param encode
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static String read(InputStream in, String encode) throws UnsupportedEncodingException, IOException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new InputStreamReader(in, encode));
			StringBuilder builder = new StringBuilder();
			String s;
			while((s = reader.readLine())!=null){
				builder.append(s);
				builder.append("\n");
			}
			return builder.toString();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
	}	
	
	
	
	
	
	
	
}
