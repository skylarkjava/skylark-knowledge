<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.config.SystemConfig"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
JspUtilEx jspUtil = new JspUtilEx(request, pageContext);
SystemConfigLogic syscfg = SystemConfigLogic.get();
String systemText = syscfg.getSystemText();
String entryText = syscfg.getEntryText();
%>

<meta charset="UTF-8">

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Style-Type" content="text/css" />
<meta http-equiv="Content-Script-Type" content="text/javascript" />
<meta http-equiv="imagetoolbar" content="no" />

<meta http-equiv="expires" content="0" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache">

<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1, maximum-scale=1">

<meta content="<%= jspUtil.labelNlsText("knowledge.header.meta.title",systemText,entryText) %>" name="title">
<meta content="<%= jspUtil.labelNlsText("knowledge.header.meta.description",systemText,entryText) %>" name="description">
<meta content='Knowledge,Markdown' name='keywords'>
<meta property="og:type" content="article"/>
<meta property="og:title" content="<%= jspUtil.labelNlsText("knowledge.header.meta.title",systemText,entryText) %>"/>
<meta property="og:description" content="<%= jspUtil.labelNlsText("knowledge.header.meta.description",systemText,entryText) %>" />
<%-- <meta property="og:image" content="" /> --%>
<meta property="og:site_name" content="Knowledge"/>

<link rel="icon" href="<%= request.getContextPath() %>/favicon.ico" type="image/vnd.microsoft.icon" />

<% if (StringUtils.isNotEmpty(jspUtil.out("thema"))) { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.out("thema") %>/bootstrap.min.css" />
<% } else { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootswatch/<%= jspUtil.cookie(SystemConfig.COOKIE_KEY_THEMA, "flatly") %>/bootstrap.min.css" />
<% } %>

<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/font-awesome/css/font-awesome.min.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/jquery-oembed-all/jquery.oembed.css" />
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/flag-icon-css/css/flag-icon.min.css" />

<% if (StringUtils.isNotEmpty(jspUtil.out("highlight"))) { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/highlightjs/styles/<%= jspUtil.out("highlight") %>.css" />
<% } else { %>
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/highlightjs/styles/<%= jspUtil.cookie(SystemConfig.COOKIE_KEY_HIGHLIGHT, "darkula") %>.css" />
<% } %>

<!--[if lt IE 9]>
    <script src="<%= request.getContextPath() %>/bower/html5shiv/dist/html5shiv.min.js"></script>
    <script src="<%= request.getContextPath() %>/bower/respond/dest/respond.min.js"></script>
<![endif]-->

<!-- Knowledge - <%= jspUtil.label("label.version") %> -->
