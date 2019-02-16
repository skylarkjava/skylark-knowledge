package org.support.project.common.util;

public class HtmlUtils {

	/** HTMLエンコードが必要な文字 **/
	static char[] htmlEncChar = { '&', '"', '<', '>' };
	/** HTMLエンコードした文字列 **/
	static String[] htmlEncStr = { "&amp;", "&quot;", "&lt;", "&gt;" };

	/**
	 * HTMLエンコード処理。 &,",<,>の置換
	 **/
	public static String encode(String strIn) {
		if (strIn == null) {
			return (null);
		}

		// HTMLエンコード処理
		StringBuffer strOut = new StringBuffer(strIn);
		// エンコードが必要な文字を順番に処理
		for (int i = 0; i < htmlEncChar.length; i++) {
			// エンコードが必要な文字の検索
			int idx = strOut.toString().indexOf(htmlEncChar[i]);

			while (idx != -1) {
				// エンコードが必要な文字の置換
				strOut.setCharAt(idx, htmlEncStr[i].charAt(0));
				strOut.insert(idx + 1, htmlEncStr[i].substring(1));

				// 次のエンコードが必要な文字の検索
				idx = idx + htmlEncStr[i].length();
				idx = strOut.toString().indexOf(htmlEncChar[i], idx);
			}
		}
		return (strOut.toString());
	}
	
	
	/**
	 * 引数で与えられた文字列にHTMLエスケープを行った結果文字列を返す
	 * @param str
	 * @return
	 */
	public static String escapeHTML(String str){
		if (str == null) {
			return null;
		}

		// 文字列の結合を繰り返すため、StringBuffer（可変の文字列を扱う）を使用
		StringBuffer escapeStr = new StringBuffer();
 
		for(int i=0; i < str.length(); i++){
			char c = str.charAt(i);
 
			if(c == '<'){
				escapeStr.append("&lt;");
			}
			else if(c == '>'){
				escapeStr.append("&gt;");
			}
			else if(c == '&'){
				escapeStr.append("&amp;");
			}
			else if(c == '"'){
				escapeStr.append("&quot;");
			}
			else if(c == '\''){
				escapeStr.append("&#39;");
			}
			else{
				escapeStr.append(c);
			}
		}
		return escapeStr.toString();
	}
	

}
