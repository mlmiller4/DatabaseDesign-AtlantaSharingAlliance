package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.SiteDAO;
import edu.gatech.cs6400.team81.dao.UserDAO;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.User;
import edu.gatech.cs6400.team81.util.HashUtils;

/**
 * Servlet implementation class LogonServlet
 */
public class LogonServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private SiteDAO siteDAO;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LogonServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	 
		boolean logonSuccess = false;
		String username = request.getParameter("uname");
		String password = request.getParameter("pwd");
		if (isValid(username, password)) {
			try {
				User user = userDAO.getByUsername(username);
				if (user != null && isMatch(user.getAccount().getPassword(), password)) {
					request.getSession(true).setAttribute("authenticatedUser", user);
					Site site = siteDAO.getBySiteId(user.getSiteId());
					request.getSession(true).setAttribute("authenticatedUsersSite", site);
					logonSuccess = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if (logonSuccess) {
			response.sendRedirect("/HomeServlet");
		} else {
			forward("/jsp/logon.jsp", request, response, new String[] { "Unable to logon user." });
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if("logoff".equalsIgnoreCase(request.getParameter("action"))){
			request.getSession(false).invalidate();
			saveMessages(request, new String[]{"Loggoff Success"});
			forward("/jsp/logon.jsp", request, response);
		}
	}
	
	private boolean isMatch(String savedPassword, String password) {
		return StringUtils.equalsIgnoreCase(savedPassword, HashUtils.md5(password));
	}

	private boolean isValid(String username, String password) {
		//TODO add real validation length, valid chars, etc.
		return true;
	}

}
