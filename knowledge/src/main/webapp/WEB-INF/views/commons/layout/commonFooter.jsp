<%@page import="org.support.project.knowledge.util.JspUtilEx"%>
<%@page pageEncoding="UTF-8" isELIgnored="false" session="false" errorPage="/WEB-INF/views/commons/errors/jsp_error.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="org.support.project.knowledge.config.AppConfig"%>

<% JspUtilEx jspUtil = new JspUtilEx(request, pageContext); %>


	<div id="footer">
		<ul class="footer-menu list-inline">
			<li class="first">
				<a class="" href="<%= request.getContextPath() %>/index" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.about") %></a>
			</li>
			<%--
			<li>
				<a class="" href="<%= request.getContextPath() %>/open.entry/list" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.list") %></a>
			</li>
			<li>
				<a class="" href="<%= request.getContextPath() %>/open.license" style="cursor: pointer;"> <%= jspUtil.label("knowledge.footer.license") %></a>
			</li>
			--%>
			<% if (!(AppConfig.get().isOnlyOneLanguage())) { %>
			<li>
				<a class="" href="<%= request.getContextPath() %>/open.language" style="cursor: pointer;">
				<% if (jspUtil.locale().getLanguage().equals("ja")) { %>
					<i class="flag-icon flag-icon-jp"></i>
				<% } else { %>
					<i class="flag-icon flag-icon-us"></i>
				<% } %>
				&nbsp;<%= jspUtil.locale().getDisplayName(jspUtil.locale()) %>
				</a>
			</li>
			<% } %>
		</ul>
		<!-- /nav -->
		<div class="clearfix"></div>
		<div class="copy">
			<span>Copyright &#169; 2015 - 2016 <a href="http://www.hudaokeji.com">hudaokeji.com</a></span>
		</div>
		<!-- /copy -->
	</div>
	<!-- /footer -->
