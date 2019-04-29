package edu.gatech.cs6400.team81.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.User;

/**
 * Servlet implementation class BaseServlet
 */
public abstract class BaseServlet extends HttpServlet {
	public static final String AUTHENTICATED_USER 			= "authenticatedUser";
	public static final String AUTHENTICATED_USERS_SITE 	= "authenticatedUsersSite";
	public static final String ERRORS 						= "errors";
	public static final String MESSAGES						= "messages";
	public static final String SESSION_ERRORS				= "session.errors";
	public static final String SESSION_MESSAGES				= "session.messages";

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BaseServlet() {
		super();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
	}

	protected User getLoggedOnUser(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (User) session.getAttribute(AUTHENTICATED_USER);
	}

	protected Site getLoggedOnSite(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		return (Site) session.getAttribute(AUTHENTICATED_USERS_SITE);
	}

	protected void forward(String path, HttpServletRequest request, HttpServletResponse response, String[] errors)
			throws ServletException, IOException {
		if (errors != null && errors.length > 0) {
			request.setAttribute(ERRORS, errors);
		}

		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}

	protected void forward(String path, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		forward(path, request, response, null);
	}

	protected void saveErrors(HttpServletRequest request, String[] errors) {
		request.setAttribute("errors", errors);
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_ERRORS, errors);
	}

	protected void saveMessages(HttpServletRequest request, String[] messages) {
		request.setAttribute("messages", messages);
		HttpSession session = request.getSession();
		session.setAttribute(SESSION_MESSAGES, messages);
	}

}
