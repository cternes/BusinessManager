package org.businessmanager.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This servlet will redirect an incoming HttpRequest to the real login page.
 * The servlet examines the incoming request an decides if it is an ajax request or a normal one.
 * <p/>
 * If it is an ajax request an ajax redirect response is generated, so that JSF can examine the response and do the redirect to the 
 * real login page. 
 * <p/>
 * If a normal request comes in, a normal redirect is created.
 * 
 * @author Christian Ternes
 *
 */
public class RedirectLoginPageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String LOGIN_PAGE_URL = "/login.jsf";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doService(request, response);
	}

	protected void doService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String facesRequest = request.getHeader("Faces-Request");
		
		if (facesRequest != null && facesRequest.equals("partial/ajax")) {
			String url = MessageFormat.format("{0}://{1}:{2,number,####0}{3}"
					+ LOGIN_PAGE_URL, request.getScheme(),
					request.getServerName(), request.getServerPort(),
					request.getContextPath());
			
			PrintWriter pw = response.getWriter();
			pw.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			pw.println("<partial-response><redirect url=\"" + url
					+ "\"></redirect></partial-response>");
			pw.flush();
		} 
		else {
			response.sendRedirect(request.getContextPath() + LOGIN_PAGE_URL);
		}
	}

}
