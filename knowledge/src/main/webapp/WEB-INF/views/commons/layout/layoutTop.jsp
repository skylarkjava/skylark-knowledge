<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.AnalyticsConfig"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
 JspUtilEx jspUtil = new JspUtilEx(request, pageContext);
 SystemConfigLogic syscfg = SystemConfigLogic.get();
 String lang = jspUtil.locale().getLanguage();
 String systemText = syscfg.getSystemText();
 String entryText = syscfg.getEntryText();
%>

<!DOCTYPE html>
<html>
<head>
<c:import url="/WEB-INF/views/commons/layout/commonHeader.jsp" />

<%-- 後からヘッダー部に何か追加する場合のパラメータ --%>
<c:if test="${param.PARAM_HEAD != null}">
	${param.PARAM_HEAD}
</c:if>

<title>
<c:if test="${param.PARAM_PAGE_TITLE != null}">
	${param.PARAM_PAGE_TITLE}
</c:if>
<c:if test="${param.PARAM_PAGE_TITLE == null}">
	<%= jspUtil.labelNlsText("knowledge.title",systemText,entryText)%>
</c:if>
</title>

<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/common.css") %>" />

</head>

<body>


${param.PARAM_CONTENT}


<c:import url="/WEB-INF/views/commons/layout/commonFooter.jsp" />

<c:import url="/WEB-INF/views/commons/layout/commonScripts.jsp" />

<c:if test="${param.PARAM_SCRIPTS != null}">
${param.PARAM_SCRIPTS}
</c:if>

<%= AnalyticsConfig.get().getAnalyticsScript() %>

</body>
</html>

