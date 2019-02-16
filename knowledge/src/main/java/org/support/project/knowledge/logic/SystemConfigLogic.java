package org.support.project.knowledge.logic;

import java.util.HashMap;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.knowledge.config.AppConfig;
import org.support.project.knowledge.config.SystemConfig;
import org.support.project.web.dao.LdapConfigsDao;
import org.support.project.web.dao.SystemConfigsDao;
import org.support.project.web.entity.LdapConfigsEntity;
import org.support.project.web.entity.SystemConfigsEntity;

@DI(instance=Instance.Singleton)
public class SystemConfigLogic {
	/** ログ */
	private static Log LOG = LogFactory.getLog(SystemConfigLogic.class);

	private boolean close = false;

	// added by lwf start
	private String systemText;

	private String entryText;

	private String sloganText;

	// added by lwf end

	public static SystemConfigLogic get() {
		return Container.getComp(SystemConfigLogic.class);
	}

	/**
	 * ユーザ自身の手でユーザ追加のリクエストを出せるかチェック
	 * @return
	 */
	public boolean isUserAddAble() {
		SystemConfigsDao systemConfigsDao = SystemConfigsDao.get();
		SystemConfigsEntity userAddType = systemConfigsDao.selectOnKey(SystemConfig.USER_ADD_TYPE, AppConfig.get().getSystemName());
		if (userAddType == null) {
			return false;
		}
		if (userAddType.getConfigValue().equals(SystemConfig.USER_ADD_TYPE_VALUE_ADMIN)) {
			return false;
		}

		LdapConfigsDao ldapConfigsDao = LdapConfigsDao.get();
		LdapConfigsEntity ldapConfigsEntity = ldapConfigsDao.selectOnKey(AppConfig.get().getSystemName());
		if (ldapConfigsEntity != null && ldapConfigsEntity.isLdapLoginOnly()) {
			return false; // Ldapでのみ認証するので、新規ユーザの追加はできない
		}
		return true;
	}

	/**
	 * @return the close
	 */
	public boolean isClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(boolean close) {
		this.close = close;
	}

	// added by lwf start
	public void setSystemText(String systemText) {
		this.systemText = systemText;
	}

	public void setEntryText(String entryText) {
		this.entryText = entryText;
	}

	public void setSloganText(String sloganText) {
		this.sloganText = sloganText;
	}

	public String getSystemText() {
		if (StringUtils.isEmpty(systemText)){
			return SystemConfig.SYSTEM_NAME_EXTERNAL_INITVAL;
		} else {
			return systemText;
		}
	}

	public String getEntryText() {
		if (StringUtils.isEmpty(entryText)){
			return SystemConfig.SYSTEM_ENTRY_TEXT_INITVAL;
		} else {
			return entryText;
		}
	}

	public String getSloganText() {
		if (StringUtils.isEmpty(sloganText)){
			return SystemConfig.SYSTEM_SLOGAN_TEXT_INITVAL;
		} else {
			return sloganText;
		}
	}	// added by lwf end


}
