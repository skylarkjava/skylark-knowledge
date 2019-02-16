<%@page import="org.support.project.knowledge.vo.Roles"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>

<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
<link rel="stylesheet" href="<%= jspUtil.mustReloadFile("/css/category.css") %>" />
</c:param>

<c:param name="PARAM_SCRIPTS">
<script type="text/javascript" src="<%= jspUtil.mustReloadFile("/js/category.js") %>"></script>
<script>
var LABEL_DELETE = '<%= jspUtil.label("knowledge.category.label.item.delete") %>';
var LABEL_TEXT_ITEM = '<i class="fa fa-pencil"></i>&nbsp;<%= jspUtil.label("knowledge.category.label.item.text") %>';
var LABEL_RADIO_ITEM = '<i class="fa fa-dot-circle-o"></i>&nbsp;<%= jspUtil.label("knowledge.category.label.item.radio") %>';
var LABEL_CHECKBOX_ITEM = '<i class="fa fa-check-square-o"></i>&nbsp;<%= jspUtil.label("knowledge.category.label.item.checkbox") %>';
var LABEL_ITEM_TITLE = '<%= jspUtil.label("knowledge.category.label.item.title") %>';
var LABEL_ITEM_DESCRIPTION = '<%= jspUtil.label("knowledge.category.label.item.description") %>';
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.category.add.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.category/create" method="post" role="form" id="categoryForm">
	<div class="form-group">
		<label for="categoryName"><%= jspUtil.label("knowledge.category.label.name") %></label>
		<input type="text" class="form-control" name="categoryName" id="categoryName" placeholder="Name" value="<%= jspUtil.out("categoryName") %>" />
	</div>
	<div class="form-group">
		<label for="categoryIcon"><%= jspUtil.label("knowledge.category.label.icon") %><%= jspUtil.label("knowledge.category.label.icon.msg") %></label>
		<input type="text" class="form-control" name="categoryIcon" id="categoryIcon" placeholder="Icon" value="<%= jspUtil.out("categoryIcon") %>" />
	</div>
	<div class="form-group">
		<label for="description"><%= jspUtil.label("knowledge.category.label.description") %></label>
		<textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
	</div>


	<input type="hidden" name="categoryId" value="-1" id="categoryId"/>

	<button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.registration") %></button>
	<a href="<%= request.getContextPath() %>/admin.category/list/<%= jspUtil.out("offset") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>

</form>

</c:param>

</c:import>

