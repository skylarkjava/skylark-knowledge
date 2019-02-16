<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>

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
			<li role="presentation" class="active"><a href="<%= request.getContextPath() %>/open.entry/list"><%= jspUtil.label("knowledge.list.kind.list") %></a></li>
<%--		<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.popular") %></a></li> --%>
<%--			<li role="presentation"><a href="#"><%= jspUtil.label("knowledge.list.kind.stock") %></a></li> --%>
			<li role="presentation"><a href="<%= request.getContextPath() %>/open.entry/show_history"><%= jspUtil.label("knowledge.list.kind.history") %></a></li>
		</ul>
	</div>

	<!-- Filter -->
	<c:if test="${!empty selectedTag || !empty selectedGroup || !empty selectedUser || !empty searchTags || !empty searchGroups || !empty keyword}">
		<div class="row">
			<div class="col-sm-12 selected_tag">

			<c:if test="${!empty selectedTag}">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?tag=<%=jspUtil.out("selectedTag.tagId") %>" >
					<i class="fa fa-tag"></i>&nbsp;<%=jspUtil.out("selectedTag.tagName") %>
			</a>
			</c:if>

			<c:if test="${!empty selectedGroup}">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?group=<%=jspUtil.out("selectedGroup.groupId") %>" >
					<i class="fa fa-group"></i>&nbsp;<%=jspUtil.out("selectedGroup.groupName") %>
			</a>
			</c:if>

			<c:if test="${!empty selectedUser}">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?user=<%= jspUtil.out("selectedUser.userId") %>" >
					<i class="fa fa-user"></i>&nbsp;<%= jspUtil.out("selectedUser.userName") %>
			</a>
			</c:if>

			<c:if test="${!empty searchTags}">
			<c:forEach var="searchTag" items="${searchTags}" varStatus="status">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?tag=<%=jspUtil.out("searchTag.tagId") %>" >
					<i class="fa fa-tag"></i>&nbsp;<%=jspUtil.out("searchTag.tagName") %>
			</a>
			</c:forEach>
			</c:if>

			<c:if test="${!empty searchGroups}">
			<c:forEach var="searchGroup" items="${searchGroups}" varStatus="status">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?group=<%=jspUtil.out("searchGroup.groupId") %>" >
					<i class="fa fa-group"></i>&nbsp;<%=jspUtil.out("searchGroup.groupName") %>
			</a>
			</c:forEach>
			</c:if>

			<c:if test="${!empty keyword}">
			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list?keyword=<%=jspUtil.out("keyword") %>" >
					<i class="fa fa-search"></i>&nbsp;<%=jspUtil.out("keyword") %>
			</a>
			</c:if>

			<a class="text-link"
				href="<%= request.getContextPath() %>/open.entry/list" >
					<i class="fa fa-times-circle"></i>&nbsp;
			</a>
			</div>
		</div>
	</c:if>

	<!-- リスト -->
	<div class="row" id="knowledgeList">
		<% request.setAttribute("list_data", jspUtil.getValue("knowledges", List.class)); %>
		<c:import url="/WEB-INF/views/open/entry/common_list.jsp" />
		<c:import url="/WEB-INF/views/open/entry/common_sub_list.jsp" />
	</div>

	<!-- Pager -->
	<nav>
		<ul class="pager">
			<li class="previous">
				<a href="<%= request.getContextPath() %>/open.entry/list/<%= jspUtil.out("previous") %><%= jspUtil.out("params") %>">
					<span aria-hidden="true">&larr;</span><%= jspUtil.label("label.previous") %>
				</a>
			</li>
			<li class="next">
				<a href="<%= request.getContextPath() %>/open.entry/list/<%= jspUtil.out("next") %><%= jspUtil.out("params") %>">
					<%= jspUtil.label("label.next") %> <span aria-hidden="true">&rarr;</span>
				</a>
			</li>
		</ul>
	</nav>

</c:param>

</c:import>

