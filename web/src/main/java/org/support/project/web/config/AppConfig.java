package org.support.project.web.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.support.project.common.config.ConfigLoader;
import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.LabelValue;

public class AppConfig extends org.support.project.common.config.AppConfig {
	public static AppConfig get() {
		if (appConfig == null) {
			appConfig = ConfigLoader.load(AppConfig.APP_CONFIG, AppConfig.class);
		}
		return appConfig;
	}
	private static AppConfig appConfig = null;

	private static final int DEFAULT_HASH_ITERATIONS = 100;

	private String tmpPath;
	private int uploadMaxMBSize;
	private boolean convTmpPath = false;

	/** HASH_ITERATIONS */
	private Integer hashIterations = DEFAULT_HASH_ITERATIONS;
	/** HASH_SIZE_BITS */
//	private Integer hashSizeBits;

	private static String webRealPath = "";

	private List<LabelValue> languages = new ArrayList<>();

	/**
	 * @return the tmpPath
	 */
	public String getTmpPath() {
		if (StringUtils.isEmpty(tmpPath)) {
			return "";
		}
		if (!convTmpPath) {
			String path = tmpPath;
			tmpPath = convPath(path);
			convTmpPath = true;
		}
		return tmpPath;
	}

	/**
	 * @param tmpPath the tmpPath to set
	 */
	public void setTmpPath(String tmpPath) {
		this.tmpPath = tmpPath;
	}

	/**
	 * @return the uploadMaxMBSize
	 */
	public int getUploadMaxMBSize() {
		return uploadMaxMBSize;
	}

	/**
	 * @param uploadMaxMBSize the uploadMaxMBSize to set
	 */
	public void setUploadMaxMBSize(int uploadMaxMBSize) {
		this.uploadMaxMBSize = uploadMaxMBSize;
	}


	/**
	 * @return the hashIterations
	 */
	public Integer getHashIterations() {
		return hashIterations;
	}

	/**
	 * @param hashIterations the hashIterations to set
	 */
	public void setHashIterations(Integer hashIterations) {
		this.hashIterations = hashIterations;
	}

	/**
	 * @return the webRealPath
	 */
	public String getWebRealPath() {
		return webRealPath;
	}

	/**
	 * @param webRealPath the webRealPath to set
	 */
	public void setWebRealPath(String webRealPath) {
		AppConfig.webRealPath = webRealPath;
	}

	/**
	 * @return the languages
	 */
	public List<LabelValue> getLanguages() {
		return languages;
	}

	/**
	 * @param languages the languages to set
	 */
	public void setLanguages(List<LabelValue> languages) {
		this.languages = languages;
	}

	public boolean isOnlyOneLanguage() {
		List<LabelValue> languages = this.getLanguages();
		return languages.size() == 1;
	}

    public Locale adjustLocal(String localeID) {
    	Locale locale;
		List<LabelValue> languages = this.getLanguages();
		boolean exist = false;
		String firstLangId = null;
		for (LabelValue language : languages) {
			if (firstLangId == null) {
				firstLangId = language.getValue();
			}
			if (localeID.equalsIgnoreCase(language.getValue())) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			localeID = firstLangId;
		}
		if (localeID.indexOf("_") == -1) {
			locale = new Locale(localeID);
		} else {
			String[] sp = localeID.split("_");
			if (sp.length == 2) {
				locale = new Locale(sp[0], sp[1]);
			} else if (sp.length >= 3) {
				locale = new Locale(sp[0], sp[1], sp[2]);
			} else {
				locale = new Locale(sp[0]);
			}
		}

		return locale;

    }
}
