package org.support.project.web.control;

import java.util.List;
import java.util.Locale;

import org.support.project.common.util.StringUtils;
import org.support.project.web.bean.LabelValue;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.AppConfig;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

public class LangControl extends Control {

	private void setLocale(LoginedUser loginedUser, Locale locale) {
		if (loginedUser == null) {
			return;
		}
		loginedUser.setLocale(locale);
	}

	@Get
	public Boundary select() throws InvalidParamException {
		Locale locale = Locale.ENGLISH;
		String LocaleID = getPathString();
		if (StringUtils.isEmpty(LocaleID)) {
			locale = Locale.ENGLISH;
		} else if (LocaleID.equals("en")) {
			locale = Locale.ENGLISH;
		} else if (LocaleID.equals("ja")) {
			locale = Locale.JAPANESE;
		} else if (LocaleID.equals("zh")) {
			locale = Locale.CHINESE;
		} else {
			List<LabelValue> languages = AppConfig.get().getLanguages();
			boolean exist = false;
			for (LabelValue language : languages) {
				if (LocaleID.equalsIgnoreCase(language.getValue())) {
					exist = true;
					break;
				}
			}
			if (exist) {
				if (LocaleID.indexOf("_") == -1) {
					locale = new Locale(LocaleID);
				} else {
					String[] sp = LocaleID.split("_");
					if (sp.length == 2) {
						locale = new Locale(sp[0], sp[1]);
					} else if (sp.length >= 3) {
						locale = new Locale(sp[0], sp[1], sp[2]);
					}
				}
			}
		}
		HttpUtil.setLocale(super.getRequest(), locale);

		this.setLocale(getLoginedUser(), HttpUtil.getLocale(getRequest()));
		return redirect(getRequest().getContextPath() + "/index/lang" + "/" + LocaleID);
	}


}
