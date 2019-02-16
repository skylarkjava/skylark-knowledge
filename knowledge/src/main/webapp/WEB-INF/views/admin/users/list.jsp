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
<h4 class="title"><%= jspUtil.label("knowledge.user.list.title") %></h4>

<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("previous") %>"><span aria-hidden="true">&larr;</span>
			<%= jspUtil.label("label.previous") %></a>
		</li>
		<li >
			<a href="<%= request.getContextPath() %>/admin.users/view_add?offset=<%= jspUtil.out("offset") %>"><i class="fa fa-plus-circle"></i>&nbsp;
			<%= jspUtil.label("label.add") %></a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("next") %>"><%= jspUtil.label("label.next") %>
			<span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


<div class="list-group">
<c:if test="${empty users}">
<%= jspUtil.label("knowledge.user.list.label.empty") %>
</c:if>

<c:forEach var="user" items="${users}" varStatus="status">
	<a href="<%= request.getContextPath() %>/admin.users/view_edit/<%= jspUtil.out("user.userId") %>?offset=<%= jspUtil.out("offset") %>" class="list-group-item">
		<h4 class="list-group-item-heading">
			[<%= jspUtil.out("user.userId") %>] <%= jspUtil.out("user.userName") %> (<%= jspUtil.out("user.userKey") %>)
		</h4>
		<p class="list-group-item-text">
		<%= jspUtil.label("label.regist.datetime") %> / <%= jspUtil.label("label.update.datetime") %>
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.insertDatetime")%> / 
			<i class="fa fa-calendar"></i>&nbsp;<%= jspUtil.date("user.updateDatetime")%>
			<% if (jspUtil.is(1, "user.authLdap")) { %>
			[LDAP USER]
			<% } else { %>
			[DB USER]
			<% } %>
		</p>
		
	</a>
</c:forEach>
</div>


<nav>
	<ul class="pager">
		<li class="previous">
			<a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("previous") %>"><span aria-hidden="true">&larr;</span>
			<%= jspUtil.label("label.previous") %></a>
		</li>
		<li >
			<a href="<%= request.getContextPath() %>/admin.users/view_add?offset=<%= jspUtil.out("offset") %>"><i class="fa fa-plus-circle"></i>&nbsp;
			<%= jspUtil.label("label.add") %></a>
		</li>
		<li class="next">
			<a href="<%= request.getContextPath() %>/admin.users/list/<%= jspUtil.out("next") %>"><%= jspUtil.label("label.next") %>
			<span aria-hidden="true">&rarr;</span></a>
		</li>
	</ul>
</nav>


</c:param>

</c:import>

