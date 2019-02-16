package org.support.project.knowledge.util;

import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;

import org.support.project.common.config.Resources;
import org.support.project.common.exception.ParseException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpUtil;

import org.support.project.web.util.JspUtil;

public class JspUtilEx  extends JspUtil{

	/** ログ */
	private static Log LOG = LogFactory.getLog(JspUtilEx.class);

	private HashMap<String,HashMap<String,String>> cache = new HashMap<String,HashMap<String,String>>();


	private String pickupNlsText(String source,String lang) {
		if (StringUtils.isEmpty(source)) {
			return StringUtils.EMPTY;
		}
		HashMap<String,String> values = cache.get(source);
		if (values == null) {
			values = new HashMap<String,String>();
			if (source.indexOf(";")==-1){
				values.put("default", source);
			} else {
				String[] parts = source.split(";");
				boolean isFirst = true;
				for (String part : parts) {
					String[] pair = part.split(":");
					values.put(pair[0],pair[1]);
					if (isFirst) {
						isFirst = false;
						values.put("default",pair[1]);
					}
				}
			}
			cache.put(source,values);
		}
		String value = values.get(lang);
		if (value == null) {
			value = values.get("default");
		}
		return value;
	}

	public JspUtilEx(HttpServletRequest request, PageContext pageContext) {
		super(request,pageContext);
	}


	/**
	 * メッセージバンドルから文字を取得
	 * パラメータは多言語対応サポート
	 * @param key
	 * @param params 置換用のパラメータ
	 * @return
	 */
	public String labelNlsText(String key, String... params) {
		Locale locale = HttpUtil.getLocale(request);
		String lang = locale.getLanguage();
		for (int i =0;i<params.length;i++) {
			params[i] = this.pickupNlsText(params[i], lang);
		}
		return Resources.getInstance(locale).getResource(key, params);
	}

	public String outNlsText(String paramName) throws ParseException  {
		String source = this.out(paramName);
		Locale locale = HttpUtil.getLocale(request);
		String lang = locale.getLanguage();
		return this.pickupNlsText(source,lang);

	}
}
