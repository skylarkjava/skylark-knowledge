<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.common.util.NumberUtils"%>
<%@page import="org.support.project.knowledge.logic.KnowledgeLogic"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>


		<!-- Sub List -->
		<div class="col-sm-12 col-md-4">
		
		<h5>- <i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group") %> - </h5>
		<c:choose>
		<c:when test="${groups != null}">
		<div class="list-group">
			<c:forEach var="group_item" items="${groups}">
			<a class="list-group-item"
				href="<%= request.getContextPath() %>/open.entry/list?group=<%= jspUtil.out("group_item.groupId") %>" >
				<span class="badge"><%= jspUtil.out("group_item.groupKnowledgeCount") %></span>
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.out("group_item.groupName") %>
			</a>
			</c:forEach>
		</div>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups">
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</c:when>
		<c:otherwise>
		<%= jspUtil.label("knowledge.list.info.group") %>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/protect.group/mygroups">
				<i class="fa fa-group"></i>&nbsp;<%= jspUtil.label("knowledge.navbar.config.group.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</c:otherwise>
		</c:choose>
		<br/>
		
		<h5>- <i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("knowledge.list.popular.tags") %> - </h5>
		<div class="list-group">
		<c:forEach var="tag_item" items="${tags}">
			<a class="list-group-item"
				href="<%= request.getContextPath() %>/open.entry/list?tag=<%= jspUtil.out("tag_item.tagId") %>" >
				<span class="badge"><%= jspUtil.out("tag_item.knowledgeCount") %></span>
				<i class="fa fa-tag"></i>&nbsp;<%= jspUtil.out("tag_item.tagName") %>
			</a>
		</c:forEach>
		</div>
		<div style="width: 100%;text-align: right;">
			<a href="<%= request.getContextPath() %>/open.tag/list">
				<i class="fa fa-tags"></i>&nbsp;<%= jspUtil.label("knowledge.list.tags.list") %>
			</a>&nbsp;&nbsp;&nbsp;
		</div>
		</div>

