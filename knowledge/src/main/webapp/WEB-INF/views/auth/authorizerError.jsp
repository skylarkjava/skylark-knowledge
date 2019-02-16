<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" isErrorPage="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">
	
	<c:param name="PARAM_CONTENT">
		<%= jspUtil.label("message.authorize.error") %>
	</c:param>

</c:import>

