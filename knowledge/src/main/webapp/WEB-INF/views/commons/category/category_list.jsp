<%@page import="org.support.project.common.util.StringUtils"%>
<%@page import="java.util.List"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>
	<!-- category -->
	<c:if test="${!empty categories}">
	<div class="form-group row">
		<c:if test="${param.PARAM_SHOW_CATEGORY_LABEL != null}">
			<label for="categoryId"><%= jspUtil.label("knowledge.label.category") %></label><br/>
		</c:if>
		<c:if test="${param.PARAM_SHOW_CATEGORY_ALL != null}">
			<label class="col-xs-6 col-sm-4  col-md-1 radio-inline <%=jspUtil.is("all", "categoryId", "checked") %>" for="categoryId_all">
				<input type="radio" value="all" name="categoryId"
				    <c:choose>
				      <c:when test="${param.PARAM_REFERSH_ONCHANGE != null}">
				    	onclick="changeCategory(this.value,true)"
				      </c:when>
				      <c:otherwise>
				    	onclick="changeCategory(this.value)"
				      </c:otherwise>
				    </c:choose>
					<c:if test="${param.PARAM_NOT_EDITABLE != null}">disabled</c:if>
					id="categoryId_all" <%= jspUtil.checked("all", "categoryId", false) %>/>
					<i class="fa fa-folder-o"></i>&nbsp;
				<%= jspUtil.label("knowledge.label.category.all") %>
			</label>
		</c:if>
		<c:forEach var="category" items="${categories}" varStatus="loop" >
			<label class="col-xs-6 col-sm-4 ${!loop.last ? 'col-md-2' : 'col-md-1'} radio-inline <%=jspUtil.is(jspUtil.out("category.categoryId"), "categoryId", "checked") %>">
				<input type="radio" value="<%= jspUtil.out("category.categoryId") %>" name="categoryId"
				    <c:choose>
				      <c:when test="${param.PARAM_REFERSH_ONCHANGE != null}">
				    	onclick="changeCategory(this.value,true)"
				      </c:when>
				      <c:otherwise>
				    	onclick="changeCategory(this.value)"
				      </c:otherwise>
				    </c:choose>
				    <c:if test="${param.PARAM_NOT_EDITABLE != null}">disabled</c:if>
					id="categoryId_<%= jspUtil.out("category.categoryId") %>" <%= jspUtil.checked(jspUtil.out("category.categoryId"), "categoryId", false) %>/>
				<% if (!StringUtils.isEmpty(jspUtil.out("category.categoryIcon"))) { %>
					<i class="fa <%= jspUtil.out("category.categoryIcon") %>"></i>&nbsp;
				<% } %>
				<%= jspUtil.outNlsText("category.categoryName") %>
			</label>
		</c:forEach>
	</div>
	</c:if>
