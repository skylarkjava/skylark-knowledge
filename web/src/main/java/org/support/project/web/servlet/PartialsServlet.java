package org.support.project.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.support.project.common.log.Log;
import org.support.project.common.log.LogFactory;
import org.support.project.common.util.StringUtils;
import org.support.project.web.common.HttpUtil;

public class PartialsServlet extends HttpServlet {
	/** ログ */
	private static Log LOG = LogFactory.getLog(PartialsServlet.class);

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String path = req.getPathInfo();
		String extention = StringUtils.getExtension(path);
		if (extention == null) {
			path = path.concat(".jsp");
		} else if (extention.equals(".html")) {
			path.replaceAll(".html", ".jsp");
		}
		if (LOG.isDebugEnabled()) {
			LOG.debug("path : " + path);
		}
		HttpUtil.forward(res, req, path);
	}
	
	
}
