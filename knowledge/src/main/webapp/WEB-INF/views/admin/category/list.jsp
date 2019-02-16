<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.navbar.config.admin.category") %></h4>

<nav>
	<ul class="pager">
		<li >
			<a href="<%= request.getContextPath() %>/admin.category/view_add">
				<i class="fa fa-plus-circle"></i>&nbsp;<%= jspUtil.label("label.add") %>
			</a>
		</li>
	</ul>
</nav>


<div class="list-group">
<c:if test="${empty categories}">
<%= jspUtil.label("knowledge.category.list.label.empty") %>
</c:if>

<c:forEach var="category" items="${categories}" varStatus="status">
	<a href="<%= request.getContextPath() %>/admin.category/view_edit/<%= jspUtil.out("category.categoryId") %>" class="list-group-item">
		<h4 class="list-group-item-heading">
			<i class="fa <%= jspUtil.out("category.categoryIcon") %>"></i>&nbsp;<%= jspUtil.out("category.categoryName") %>
		</h4>
		<p class="list-group-item-text">
		<%= jspUtil.out("category.description") %>
		</p>

	</a>
</c:forEach>
</div>




</c:param>

</c:import>

