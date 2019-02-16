package org.support.project.knowledge.listener;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.util.PasswordUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.knowledge.logic.OpenAmLogic;
import org.support.project.web.bean.LdapInfo;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.entity.UsersEntity;
import org.support.project.web.logic.LdapLogic;
import org.support.project.web.wrapper.HttpServletRequestWrapper;

import com.utilhub.openam.Am13RestAPI;

import net.arnx.jsonic.JSON;

public class OpenAmAuthenticationFilter extends CloseAbleAuthenticationFilter {

	private static String MANUAL_LOGIN_FLAG_KEY = "MANUAL_LOGIN_FLAG_KEY";

	// OpenAMサーバ情報
	private String cookieNameForToken = "";
	private String openamBaseUrl = "";
	private boolean openamAutoLogin = false;

	/**
	 * マニュアルログインフラグをクッキーから取得
	 * @param req
	 * @param res
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private boolean getManualLoginFlag(HttpServletRequest req, HttpServletResponse res) throws NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//return "1".equals(HttpUtil.getCookie(req,MANUAL_LOGIN_FLAG_KEY));
		return "1".equals(req.getSession().getAttribute(MANUAL_LOGIN_FLAG_KEY));
	}

	/**
	 * マニュアルログインフラグをクッキーに保持
	 * @param req
	 * @param res
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	private void setManualLoginFlag(HttpServletRequest req, HttpServletResponse res) throws NoSuchAlgorithmException, NoSuchPaddingException,
		InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		//Cookie cookie = new  Cookie(MANUAL_LOGIN_FLAG_KEY, "1");
		//cookie.setPath(req.getContextPath() + "/");
		//cookie.setMaxAge(-1);
		//cookie.setSecure(cookieSecure);
		//res.addCookie(cookie);
		req.getSession().setAttribute(MANUAL_LOGIN_FLAG_KEY, "1");
	}

	/**
	 * マニュアルログインフラグをCookieから削除
	 * @param req
	 * @param res
	 */
	private void removeManualLoginFlag(HttpServletRequest req, HttpServletResponse res) {
		// Cookie削除
		//Cookie cookie = new  Cookie(MANUAL_LOGIN_FLAG_KEY, "0");
		//cookie.setPath(req.getContextPath() + "/");
		//cookie.setMaxAge(0);
		//cookie.setSecure(cookieSecure);
		//res.addCookie(cookie);
		req.getSession().removeAttribute(MANUAL_LOGIN_FLAG_KEY);

	}

	/* (non-Javadoc)
	 * @see org.support.project.web.filter.AuthenticationFilter#init(javax.servlet.FilterConfig)
	 */
	@Override
	public void init(FilterConfig filterconfig) throws ServletException {
		String openamBaseUrl = filterconfig.getInitParameter("openam-base-url");
		if (StringUtils.isNotEmpty(openamBaseUrl)) {
			this.openamBaseUrl = openamBaseUrl;
		} else {
			throw new ServletException("the init parameter openam-base-url is not defined!");
		}
		try {
			this.cookieNameForToken = Am13RestAPI.getCookieNameForToken(openamBaseUrl);
		} catch (Exception e) {
			throw new ServletException("can not connect to the opanam server:" + this.openamBaseUrl);
		}
		String login = filterconfig.getInitParameter("openam-auto-login");
		if (login != null && login.toLowerCase().equals("true")) {
			openamAutoLogin = true;
		}
		super.init(filterconfig);
	}



	/* (non-Javadoc)
	 * @see org.support.project.web.filter.AuthenticationFilter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) servletrequest;
		HttpServletResponse res = (HttpServletResponse) servletresponse;
		try {
			boolean isReturn = this.getManualLoginFlag(req, res);
			if (openamAutoLogin || isReturn) {
				//組込アプリモードでOpenAMサーバへ自動ログインの場合 OR
				//独立アプリモードでOpenAMサーバへログインして戻ってきた場合
				this.removeManualLoginFlag(req, res);
				if (!isLogin(req)) {
					// ログインしていない
					Cookie[] cookies = req.getCookies();
					if (cookies != null) {
						for (Cookie cookie : cookies) {
							if (cookie.getName().equals(this.cookieNameForToken)) {
								String tokenId = cookie.getValue();
								OpenAmLogic openamLogic = OpenAmLogic.get();
								UsersEntity usersEntity = openamLogic.auth(this.openamBaseUrl,tokenId);
								if (usersEntity !=null ) {
									this.logined(usersEntity.getUserKey(), req);
								}
								break;
							}
						}
					}
				}
			}
			if (!openamAutoLogin) {
				//独立アプリモードでOpenAMサーバへマニュアルログインする必要な場合のみ、以下の処理を実施
				StringBuilder pathBuilder = new StringBuilder();
				pathBuilder.append(req.getServletPath());
				if (req.getPathInfo() != null && req.getPathInfo().length() > 0) {
					pathBuilder.append(req.getPathInfo());
				}
				String path = pathBuilder.toString();

				if (path.equals(loginProcess)) {
					String page = req.getParameter("page");
					req.setAttribute("page", page);
					if (!isLogin(req)) {
						String returnUrl = HttpUtil.getContextUrl(req)+page;
						OpenAmLogic openamLogic = OpenAmLogic.get();
						this.setManualLoginFlag(req, res);
						String redirectUrl = openamLogic.login(this.openamBaseUrl, returnUrl);
						HttpUtil.redirect(res, req, redirectUrl);
						return;
					} else {
						this.changePage(req, res);
					}
				}
			}

		} catch (Exception e) {
			throw new ServletException(e);
		}
		super.doFilter(servletrequest, servletresponse, filterchain);
	}

}
