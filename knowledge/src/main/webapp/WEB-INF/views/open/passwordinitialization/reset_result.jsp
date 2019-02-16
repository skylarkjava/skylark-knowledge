<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page import="org.support.project.knowledge.logic.SystemConfigLogic"%>
<%@page import="org.support.project.common.util.StringUtils"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>


<c:import url="/WEB-INF/views/commons/layout/layoutMain.jsp">

<c:param name="PARAM_HEAD">
</c:param>

<c:param name="PARAM_CONTENT">
<h4 class="title"><%= jspUtil.label("knowledge.auth.title.forgot.password") %></h4>

<%= jspUtil.label("knowledge.auth.msg.changed") %>


<br/><br/>
<br/><br/>


<a href="<%= request.getContextPath() %>/index"><%= jspUtil.label("knowledge.auth.label.back.top") %></a>



</c:param>

</c:import>

