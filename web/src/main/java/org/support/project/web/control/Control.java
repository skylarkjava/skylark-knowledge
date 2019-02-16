package org.support.project.web.control;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.arnx.jsonic.JSON;
import net.arnx.jsonic.JSONException;

import org.support.project.common.bean.ValidateError;
import org.support.project.common.config.CommonBaseParameter;
import org.support.project.common.config.Resources;
import org.support.project.common.exception.SystemException;
import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.PropertyUtil;
import org.support.project.common.util.StringUtils;
import org.support.project.di.Container;
import org.support.project.di.DI;
import org.support.project.di.Instance;
import org.support.project.web.bean.LoginedUser;
import org.support.project.web.bean.MessageResult;
import org.support.project.web.boundary.Boundary;
import org.support.project.web.boundary.DownloadBoundary;
import org.support.project.web.boundary.ForwardBoundary;
import org.support.project.web.boundary.JsonBoundary;
import org.support.project.web.boundary.RedirectBoundary;
import org.support.project.web.boundary.SendMessageBoundary;
import org.support.project.web.common.HttpStatus;
import org.support.project.web.common.HttpUtil;
import org.support.project.web.common.InvokeSearch;
import org.support.project.web.common.InvokeTarget;
import org.support.project.web.config.CommonWebParameter;
import org.support.project.web.config.HttpMethod;
import org.support.project.web.config.MessageStatus;
import org.support.project.web.control.service.Get;
import org.support.project.web.exception.InvalidParamException;

@DI(instance=Instance.Prototype)
public abstract class Control {
	/** ログ */
	private static Log log = LogFactory.getLog(Control.class);

	/** HttpServletRequest */
	private HttpServletRequest request;
	/** HttpServletResponse */
	private HttpServletResponse response;
	/** このコントロールの呼び出しに使われたパス */
	private String path;
	/** このコントロールの呼び出しに使われたパスの後に続く情報部分 */
	private String pathInfo;
	
	/** Controlを呼び出す際に使った情報 */
	private InvokeTarget invokeTarget = null;
	/** フォワードするディレクトリ名 */
	private String forwardDir = null;
	
	/** リソース */
	protected Resources resources = Resources.getInstance(CommonBaseParameter.APP_RESOURCE);
	
	public static final String BASE_PATH = "/WEB-INF/views";
	
	private boolean sendEscapeHtml = true;
	
	/**
	 * 初期アクセスメソッド
	 * @return
	 */
	@Get
	public Boundary index() {
		log.trace("index");
		return forward("index.jsp");
	}
	/**
	 * 言語選択
	 * @return
	 */
	@Get
	public Boundary lang() {
		log.trace("lang");
		return forward("index.jsp");
	}
	
	/**
	 * コントローラーから、別のコントローラーを呼び出す（処理委譲）際に、
	 * 別の委譲するコントローラーを取得する
	 * 
	 * @param control
	 */
	protected Boundary devolution(HttpMethod method, String path) {
		return devolution(method, path, getPathInfo());
	}
	/**
	 * コントローラーから、別のコントローラーを呼び出す（処理委譲）際に、
	 * 別の委譲するコントローラーを取得する
	 * 
	 * @param control
	 */
	protected Boundary devolution(HttpMethod method, String path, String pathinfo) {
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		InvokeSearch invokeSearch = Container.getComp(InvokeSearch.class);
		InvokeTarget invokeTarget = invokeSearch.getController(method, path);
		if (invokeTarget != null && Control.class.isAssignableFrom(invokeTarget.getTargetClass())) {
			Control control = (Control) invokeTarget.getTarget();
			copy(control);
			control.setPath(path);
			control.setInvokeTarget(invokeTarget);
			control.setPathInfo(pathinfo);
			return (Boundary) invokeTarget.invoke();
		}
		throw new SystemException("can not devolution path :" + path);
	}
	/**
	 * 委譲するControlに自身の持っている情報をコピーする
	 * @param control
	 */
	protected void copy(Control control) {
		control.setPathInfo(pathInfo);
		control.setRequest(request);
		control.setResponse(response);
	}
	
	
	/**
	 * フォワードするディレクトリ取得
	 * @return
	 */
	protected String getForwardDir() {
		if (forwardDir != null) {
			return forwardDir;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append(BASE_PATH);
		builder.append("/");
//		String className = ClassUtils.getShortClassName(this.getClass());
		String className = this.getClass().getSimpleName();
		if (className.indexOf("_$$_") != -1) {
			className = className.substring(0, className.indexOf("_$$_"));
		}
		
		if (invokeTarget != null) {
			String subPackageName = invokeTarget.getSubPackageName();
			if (StringUtils.isNotEmpty(subPackageName)) {
				if (subPackageName.indexOf("\\.") != -1) {
					String[] packages = subPackageName.split(".");
					for (int i = packages.length -1; i <= 0; i--) {
						String string = packages[i];
						builder.append(string).append("/");
					}
				} else {
					builder.append(subPackageName).append("/");
				}
			}
			
			if (className.endsWith(invokeTarget.getClassSuffix())) {
				className = className.substring(0, className.length() - invokeTarget.getClassSuffix().length());
			}
		}
		builder.append(className.toLowerCase()).append("/");
		
		forwardDir = builder.toString();
		return forwardDir;
	}
	/**
	 * 指定のパスにフォワード
	 * @param path
	 * @return
	 */
	protected ForwardBoundary forward(String path) {
		String forwardPath;
		if (path.startsWith("/")) {
			StringBuilder builder = new StringBuilder();
			builder.append(BASE_PATH);
			builder.append(path);
			forwardPath = builder.toString();
		} else {
			forwardPath = getForwardDir().concat(path);
			if (log.isTraceEnabled()) {
				log.trace("forward : " + forwardPath);
			}
		}
		
		ForwardBoundary boundary = new ForwardBoundary(forwardPath);
		boundary.setRequest(request);
		boundary.setResponse(response);
		return boundary;
	}
	
	/**
	 * 指定のパスにリダイレクト
	 * @param path
	 * @return
	 */
	protected RedirectBoundary redirect(String path) {
		RedirectBoundary boundary = new RedirectBoundary(path);
		boundary.setRequest(request);
		boundary.setResponse(response);
		return boundary;
	}
	
	
	/**
	 * JSONでオブジェクト送信
	 * @param json
	 * @return
	 */
	protected JsonBoundary send(Object json) {
		if (json instanceof MessageResult) {
			MessageResult messageResult = (MessageResult) json;
			if (messageResult.getStatus() > MessageStatus.Success.getValue()) {
				if (messageResult.getCode() != null && messageResult.getCode() > HttpStatus.SC_200_OK) {
					return send(messageResult.getCode(), messageResult);
				} else if (messageResult.getCode() == null) {
					return send(HttpStatus.SC_400_BAD_REQUEST, messageResult);
				}
			}
		}
		JsonBoundary boundary = new JsonBoundary(json);
		boundary.setRequest(request);
		boundary.setResponse(response);
		boundary.setSendEscapeHtml(sendEscapeHtml);
		return boundary;
	}
	
	/**
	 * JSONでオブジェクト送信
	 * @param json
	 * @return
	 */
	protected JsonBoundary send(int status, Object json) {
		if (json instanceof MessageResult) {
			MessageResult messageResult = (MessageResult) json;
			messageResult.setCode(status);
		}
		JsonBoundary boundary = new JsonBoundary(json);
		response.setStatus(status);
		boundary.setRequest(request);
		boundary.setResponse(response);
		boundary.setSendEscapeHtml(sendEscapeHtml);
		return boundary;
	}
	
	/**
	 * メッセージ送信
	 * @param httpstatus
	 * @param messageKey
	 * @param params
	 * @return
	 */
	protected JsonBoundary sendMsg(MessageStatus messageStatus, int httpStatus, String result, String messageKey, String... params) {
		Resources resources = Resources.getInstance(HttpUtil.getLocale(getRequest()));
		String msg = resources.getResource(messageKey, params);
		MessageResult messageResult = new MessageResult();
		messageResult.setMessage(msg);
		messageResult.setStatus(messageStatus.getValue());
		messageResult.setCode(httpStatus);
		messageResult.setResult(result);
		return send(messageResult);
	}
	
	/**
	 * エラーを送信
	 * @param errors
	 * @return
	 */
	protected JsonBoundary sendValidateError(List<ValidateError> errors) {
		MessageResult messageResult = new MessageResult();
		messageResult.setMessage("Bad Request");
		messageResult.setResult("Bad Request");
		messageResult.setCode(HttpStatus.SC_400_BAD_REQUEST);
		
		int status = MessageStatus.Warning.getValue();
		for (ValidateError validateError : errors) {
			String msg = validateError.getMsg(HttpUtil.getLocale(getRequest()));
			if (validateError.getLevel() == MessageStatus.Error.getValue()) {
				status = MessageStatus.Error.getValue();
			}
			MessageResult child = new MessageResult();
			child.setMessage(msg);
			messageResult.getChildren().add(child);
		}
		messageResult.setStatus(status);
		return send(messageResult);
	}

	
	/**
	 * エラーを送信
	 * @param status
	 * @param msg
	 * @return
	 */
	protected SendMessageBoundary sendError(int status, String msg) {
		SendMessageBoundary boundary = new SendMessageBoundary();
		boundary.setRequest(request);
		boundary.setResponse(response);
		
		boundary.setStatus(status);
		boundary.setMsg(msg);
		return boundary;
	}

	
	/**
	 * ダウンロード
	 * @param fileName
	 * @param inputStream
	 * @param size
	 * @return
	 */
	protected Boundary download(String fileName, InputStream inputStream, long size) {
		DownloadBoundary boundary = new DownloadBoundary(fileName, inputStream, size);
		boundary.setRequest(request);
		boundary.setResponse(response);
		return boundary;
	}
	/**
	 * ダウンロード
	 * @param fileName
	 * @param inputStream
	 * @param size
	 * @param contentType
	 * @return
	 */
	protected Boundary download(String fileName, InputStream inputStream, long size, String contentType) {
		DownloadBoundary boundary = new DownloadBoundary(fileName, inputStream, size, contentType);
		boundary.setRequest(request);
		boundary.setResponse(response);
		return boundary;
	}
	
	/**
	 * リクエストのAttributeを取得(Stringで)
	 * @param name
	 * @return
	 */
	protected String getParameter(String name) {
		Object val = request.getAttribute(name);
		if (val == null) {
			return "";
		}
		if (val instanceof String) {
			return (String) val;
		}
		return val.toString();
	}
	/**
	 * リクエストのAttributeにセット
	 * @param key
	 * @param value
	 */
	protected void setAttribute(String key, Object value) {
		request.setAttribute(key, value);
	}
	/**
	 * リクエストのAttributeを取得
	 * @param name
	 * @return
	 */
	protected Object getAttribute(String name) {
		return request.getAttribute(name);
	}
	/**
	 * リクエストのAttributeを取得
	 * @param name
	 * @return
	 */
	protected String getAttribute(String name, String defaultValue) {
		Object obj = request.getAttribute(name);
		if (obj == null) {
			return defaultValue;
		}
		return obj.toString();
	}

	/**
	 * リクエストにセット
	 * @param value（プロパティ名でsetAttributeする）
	 */
	protected void setAttributeOnProperty(Object value) {
		List<String> props = PropertyUtil.getPropertyNames(value);
		for (String key : props) {
			request.setAttribute(key, PropertyUtil.getPropertyValue(value, key));
		}
	}
	
	/**
	 * リクエスト内のJSONデータをオブジェクトのパースする
	 * @param type
	 * @return
	 * @throws JSONException
	 * @throws IOException
	 */
	protected <T> T getJsonObject(Class<? extends T> type) throws JSONException, IOException {
		HttpServletRequest request = getRequest();
		T object = JSON.decode(request.getInputStream(), type);
		return object;
	}

	/**
	 * パス情報を配列で取得
	 * @return
	 */
	protected String[] getPathInfos() {
		if (StringUtils.isEmpty(pathInfo)) {
			return null;
		}
		if (pathInfo.indexOf("/") != -1) {
			String[] infos = pathInfo.split("/");
			List<String> list = new ArrayList<>();
			for (String string : infos) {
				if (StringUtils.isNotEmpty(string.trim())) {
					list.add(string.trim());
				}
			}
			return list.toArray(new String[0]);
		} else {
			String[] infos = new String[1];
			infos[0] = pathInfo.trim();
			return infos;
		}
	}
	
	/**
	 * ログインユーザを取得
	 * @return
	 */
	public LoginedUser getLoginedUser() {
		return (LoginedUser) request.getSession().getAttribute(CommonWebParameter.LOGIN_USER_INFO_SESSION_KEY);
	}
	
	/**
	 * ログインユーザのIDを取得
	 * @return
	 */
	public Integer getLoginUserId() {
		LoginedUser loginedUser = getLoginedUser();
		if (loginedUser == null) {
			return Integer.MIN_VALUE;
		}
		return loginedUser.getLoginUser().getUserId();
	}
	
	
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	public InvokeTarget getInvokeTarget() {
		return invokeTarget;
	}

	public void setInvokeTarget(InvokeTarget invokeTarget) {
		this.invokeTarget = invokeTarget;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getPathInfo() {
		return pathInfo;
	}
	public void setPathInfo(String pathInfo) {
		this.pathInfo = pathInfo;
	}

	
	/**
	 * PathInfoに設定された文字列を取得する
	 * @return
	 * @throws InvalidParamException
	 */
	protected String getPathString() throws InvalidParamException {
		String[] pathInfos = getPathInfos();
		if (pathInfos == null || StringUtils.isEmpty(pathInfos[0])) {
			MessageResult result = new MessageResult();
			result.setStatus(MessageStatus.Warning.getValue());
			result.setMessage(resources.getResource("errors.required", "id"));
			result.setCode(HttpStatus.SC_400_BAD_REQUEST);
			throw new InvalidParamException(result);
		}
		return pathInfos[0];
	}
	
	/**
	 * PathInfoに設定された文字列をInteger形式で取得する
	 * @param defaultNum
	 * @return
	 * @throws InvalidParamException
	 */
	protected Integer getPathInteger(Integer defaultNum) throws InvalidParamException {
		String[] pathInfos = getPathInfos();
		if (pathInfos == null || pathInfos.length == 0 || StringUtils.isEmpty(pathInfos[0])) {
			if (defaultNum != null) {
				return defaultNum;
			}
			MessageResult result = new MessageResult();
			result.setStatus(MessageStatus.Warning.getValue());
			result.setMessage(resources.getResource("errors.required", "param"));
			result.setCode(HttpStatus.SC_400_BAD_REQUEST);
			throw new InvalidParamException(result);
		}
		String param = pathInfos[0];
		if (!StringUtils.isInteger(param)) {
			MessageResult result = new MessageResult();
			result.setStatus(MessageStatus.Warning.getValue());
			result.setMessage(resources.getResource("errors.invalid", "param"));
			result.setCode(HttpStatus.SC_400_BAD_REQUEST);
			throw new InvalidParamException(result);
		}
		return Integer.parseInt(param);
	}
	
	/**
	 * PathInfoに設定された文字列をInteger形式で取得する
	 * @return
	 * @throws InvalidParamException
	 */
	protected Integer getPathInteger() throws InvalidParamException {
		return getPathInteger(null);
	}
	
	
	/**
	 * PathInfoに設定された文字列をInteger形式で取得する
	 * @param defaultNum
	 * @return
	 * @throws InvalidParamException
	 */
	protected Long getPathLong(Long defaultNum) throws InvalidParamException {
		String[] pathInfos = getPathInfos();
		if (pathInfos == null || pathInfos.length == 0 || StringUtils.isEmpty(pathInfos[0])) {
			if (defaultNum != null) {
				return defaultNum;
			}
			MessageResult result = new MessageResult();
			result.setStatus(MessageStatus.Warning.getValue());
			result.setMessage(resources.getResource("errors.required", "param"));
			result.setCode(HttpStatus.SC_400_BAD_REQUEST);
			throw new InvalidParamException(result);
		}
		String param = pathInfos[0];
		if (!StringUtils.isLong(param)) {
			MessageResult result = new MessageResult();
			result.setStatus(MessageStatus.Warning.getValue());
			result.setMessage(resources.getResource("errors.invalid", "param"));
			result.setCode(HttpStatus.SC_400_BAD_REQUEST);
			throw new InvalidParamException(result);
		}
		return Long.parseLong(param);
	}
	
	/**
	 * PathInfoに設定された文字列をInteger形式で取得する
	 * @return
	 * @throws InvalidParamException
	 */
	protected Long getPathLong() throws InvalidParamException {
		return getPathLong(null);
	}
	
	/**
	 * パラメータ取得
	 * @param paramName
	 * @return
	 */
	protected String getParam(String paramName) {
		return getParamWithDefault(paramName, "");
	}
	/**
	 * パラメータ取得
	 * パラメータがなければデフォルト値を取得
	 * @param paramName
	 * @param defaultval
	 * @return
	 */
	protected String getParamWithDefault(String paramName, String defaultval) {
		try {
			String param = HttpUtil.getParameter(request, paramName, String.class);
			if (param == null) {
				return defaultval;
			}
			return param;
		} catch (InvalidParamException e) {
			throw new SystemException(e);
		}
	}
	
	
	protected <T> T getParam(String paramName, Class<? extends T> type) {
		try {
			return HttpUtil.getParameter(request, paramName, type);
		} catch (InvalidParamException e) {
			throw new SystemException(e);
		}
	}
	
	protected <T> T getParams(Class<? extends T> type) {
		try {
			return HttpUtil.parseRequest(request, type);
		} catch (InstantiationException | IllegalAccessException | JSONException | IOException | InvalidParamException e) {
			throw new SystemException(e);
		}
	}
	
	protected Map<String, String> getParams() {
		Map<String, String> map = new HashMap<>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String key = (String) enumeration.nextElement();
			String value = request.getParameter(key);
			map.put(key, value);
		}
		return map;
	}

	/**
	 * @return the sendEscapeHtml
	 */
	public boolean isSendEscapeHtml() {
		return sendEscapeHtml;
	}

	/**
	 * @param sendEscapeHtml the sendEscapeHtml to set
	 */
	public void setSendEscapeHtml(boolean sendEscapeHtml) {
		this.sendEscapeHtml = sendEscapeHtml;
	}
	
	public <T> T getParamOnProperty(final Class<? extends T> type) 
			throws InstantiationException, IllegalAccessException, JSONException, IOException, InvalidParamException {
		return HttpUtil.parseRequest(request, type);
	}
	
	/**
	 * 指定のキーのCookie値を取得
	 * @param key
	 * @return
	 */
	protected String getCookie(String key) {
		return HttpUtil.getCookie(getRequest(), key);
	}
	/**
	 * 指定のCookieをセット
	 * @param key
	 * @param value
	 */
	protected void setCookie(String key, String value) {
		HttpUtil.setCookie(getRequest(), getResponse(), key, value);
	}
	/**
	 * Localeを取得
	 * @return
	 */
	protected Locale getLocale() {
		return HttpUtil.getLocale(request);
	}
	
}
