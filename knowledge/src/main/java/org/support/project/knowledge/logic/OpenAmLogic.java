package org.support.project.knowledge.logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.support.project.common.config.INT_FLAG;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.RandomUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.web.config.WebConfig;
import org.support.project.web.dao.UsersDao;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.exception.AuthenticateException;
import org.support.project.web.logic.UserLogic;

import com.utilhub.openam.Am13RestAPI;
import com.utilhub.openam.AmUser;




public class OpenAmLogic  {
	/** ログ */
	private static Log LOG = LogFactory.getLog(OpenAmLogic.class);

	public static OpenAmLogic get() {
		return Container.getComp(OpenAmLogic.class);
	}

	/**
	 * Launch the browser and go to the login page
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @return the result.
	 */
    public  String login(String baseURL, String redirectURL) {
		return Am13RestAPI.login(baseURL, redirectURL);
	}

    public  String logout(String baseURL) {
		return Am13RestAPI.logout(baseURL,"");
    }
    /*
	 * OpenAMを使った認証
	 *
	 */
	public UsersEntity auth(String openamBaseUrl, String tokenId) throws AuthenticateException {
		try {
			UsersEntity usersEntity = null;
			AmUser amuser = Am13RestAPI.validate(openamBaseUrl, tokenId);
			if (amuser != null) {
 				Am13RestAPI.detail(openamBaseUrl,tokenId,amuser);
				UsersDao usersDao = UsersDao.get();
				usersEntity = usersDao.selectOnLowerUserKey(amuser.getUid());
				if (usersEntity == null) {
					usersEntity = addUser(amuser.getUid(), amuser);
				} else {
					updateUser(amuser.getUid(), amuser, usersDao, usersEntity);
				}

			}
			return usersEntity;
		} catch (Exception e) {
			throw new AuthenticateException(e);
		}
	}

	/**
	 * OpenAMから取得した情報でユーザ情報更新
	 * 同一IDの
	 * @param uid
	 * @param amUserInfo
	 * @param usersDao
	 * @param usersEntity
	 */
	private UsersEntity updateUser(String uid,  AmUser amUserInfo, UsersDao usersDao,UsersEntity usersEntity) {
		// 既にユーザ情報は登録されているので、Ldapの情報でデータ更新があればKnowledgeのユーザ情報を更新する
		boolean change = false;
		if (StringUtils.isNotEmpty(amUserInfo.getGivenName())) {
			if (!amUserInfo.getGivenName().equals(usersEntity.getUserName())) {
				usersEntity.setUserName(amUserInfo.getGivenName());
				change = true;
			}
		}
		if (StringUtils.isNotEmpty(amUserInfo.getMail())) {
			if (StringUtils.isEmailAddress(amUserInfo.getMail()) && !amUserInfo.getMail().equals(usersEntity.getMailAddress())) {
				usersEntity.setMailAddress(amUserInfo.getMail());
				change = true;
			}
		}
		if (usersEntity.getAuthLdap() == null || usersEntity.getAuthLdap().intValue() != INT_FLAG.ON.getValue()) {
			// 既にKnowledgeに登録されているユーザとLdapのユーザのIDが同じ場合は、
			// 既存のユーザ情報を更新する？？？
			// TODO 更新で良いか、検討する必要あり（いったん更新する）
			usersEntity.setAuthLdap(INT_FLAG.ON.getValue());
			change = true;
		}
		if (change) {
			usersDao.save(usersEntity);
			LOG.debug("Change User info on Ldap login. [user]" + uid);
		}
		return usersEntity;
	}

	/**
	 * Ldapから取得した情報でユーザ登録
	 * @param uid
	 * @param amUserInfo
	 */
	private UsersEntity addUser(String uid,  AmUser amUserInfo) {
		UsersEntity usersEntity;
		// OpenAM認証でユーザ登録されていないユーザがログイン
		usersEntity = new UsersEntity();
		usersEntity.setUserKey(amUserInfo.getUid());
		if (StringUtils.isNotEmpty(amUserInfo.getGivenName())) {
			usersEntity.setUserName(amUserInfo.getGivenName());
		} else {
			usersEntity.setUserName(amUserInfo.getUid());
		}
		if (StringUtils.isNotEmpty(amUserInfo.getMail())) {
			if (StringUtils.isEmailAddress(amUserInfo.getMail())) {
				usersEntity.setMailAddress(amUserInfo.getMail());
			}
		}
		usersEntity.setAuthLdap(INT_FLAG.ON.getValue());
		usersEntity.setAdmin(amUserInfo.isAdmin());
//		usersEntity.setPassword(password);
		usersEntity.setPassword(RandomUtil.randamGen(24)); //Ldapログインしたユーザのパスワードは推測不可能な形にしておく

		List<String> roles = new ArrayList<String>();
		roles.add(WebConfig.ROLE_USER);
		if (amUserInfo.isAdmin()) {
			roles.add(WebConfig.ROLE_ADMIN);
		}
		UserLogic.get().insert(usersEntity, roles.toArray(new String[0]));
		LOG.info("Add User on first Ldap login. [user]" + uid);
		return usersEntity;
	}


}
