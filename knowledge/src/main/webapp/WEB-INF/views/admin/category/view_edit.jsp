<%@page import="org.support.project.knowledge.logic.CategoryLogic"%>
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
var LABEL_UPDATE = '<%= jspUtil.label("label.update") %>';
</script>
<script>
function deleteUser() {
	bootbox.confirm("本当に削除しますか?", function(result) {
		if (result) {
			$('#categoryForm').attr('action', '<%= request.getContextPath()%>/admin.category/delete');
			$('#categoryForm').submit();
		}
	});
};
</script>
</c:param>



<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.category.edit.title") %></h4>

<form action="<%= request.getContextPath()%>/admin.category/update" method="post" role="form" id="categoryForm">
	<c:if test="${!editable}">
	<div class="alert alert-info alert-dismissible" role="alert">
		<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		<strong>Information</strong><br/>
		- <%= jspUtil.label("knowledge.category.label.not.editable") %>
	</div>

	</c:if>

	<div class="form-group">
		<label for="categoryName"><%= jspUtil.label("knowledge.category.label.name") %></label>
		<input type="text" class="form-control" name="categoryName" id="categoryName" placeholder="Name" value="<%= jspUtil.out("categoryName") %>" />
	</div>
	<div class="form-group">
		<label for="categoryIcon"><%= jspUtil.label("knowledge.category.label.icon") %></label>
		<input type="text" class="form-control" name="categoryIcon" id="categoryIcon" placeholder="Icon" value="<%= jspUtil.out("categoryIcon") %>" />
	</div>
	<div class="form-group">
		<label for="description"><%= jspUtil.label("knowledge.category.label.description") %></label>
		<textarea class="form-control" name="description" id="description" placeholder="Description" ><%= jspUtil.out("description") %></textarea>
	</div>



	<input type="hidden" name="categoryId" value="<%= jspUtil.out("categoryId") %>" id="categoryId"/>

	<button type="submit" class="btn btn-primary" id="savebutton"><i class="fa fa-save"></i>&nbsp;<%= jspUtil.label("label.update") %></button>
	<c:if test="${editable}">
	<button type="button" class="btn btn-danger" onclick="deleteUser();"><i class="fa fa-remove"></i>&nbsp;<%= jspUtil.label("label.delete") %></button>
	</c:if>
	<a href="<%= request.getContextPath() %>/admin.category/list/<%= jspUtil.out("offset") %>"
		class="btn btn-success" role="button"><i class="fa fa-list-ul"></i>&nbsp;<%= jspUtil.label("label.backlist") %></a>

</form>

</c:param>

</c:import>

