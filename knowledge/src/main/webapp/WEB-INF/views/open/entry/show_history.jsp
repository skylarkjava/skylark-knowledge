<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.css" />
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/knowledge-list.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/bootstrap-tagsinput/dist/bootstrap-tagsinput.min.js"></script>
<script type="text/javascript" src="<%= request.getContextPath() %>/bower/echojs/dist/echo.min.js"></script>
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/knowledge-list.js") %>"></script>
</c:param>

<c:param name="PARAM_CONTENT">

	<!-- category -->
	<c:import url="/WEB-INF/views/commons/category/category_list.jsp">
		<c:param name="PARAM_SHOW_CATEGORY_ALL" value="true"/>
		<c:param name="PARAM_REFERSH_ONCHANGE" value="true"/>
	</c:import>

	<!-- Title -->
	<div class="row">
		<ul class="nav nav-tabs">
			<li role="presentation"><a href="<%= request.getContextPath() %>/open.entry/list"><%= jspUtil.label("knowledge.list.kind.list") %></a></li>
<%--		<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.popular") %></a></li> --%>
<%--			<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li> --%>
			<li role="presentation" class="active"><a href="<%= request.getContextPath() %>/open.entry/show_history"><%= jspUtil.label("knowledge.list.kind.history") %></a></li>
		</ul>
	</div>

	<!-- リスト -->
	<div class="row" id="knowledgeList">
		<% request.setAttribute("list_data", jspUtil.getValue("histories", List.class)); %>
		<c:import url="/WEB-INF/views/open/entry/common_list.jsp" />
		<c:import url="/WEB-INF/views/open/entry/common_sub_list.jsp" />
	</div>

</c:param>

</c:import>


