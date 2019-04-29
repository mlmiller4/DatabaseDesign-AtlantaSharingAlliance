package edu.gatech.cs6400.team81.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import edu.gatech.cs6400.team81.model.UserRole;
import edu.gatech.cs6400.team81.model.User;

public class AuthorizationManager {
	private static final Map<UserRole, String[]> AUTHORIZATIONS = new HashMap<UserRole, String[]>();
	static{
		AUTHORIZATIONS.put(UserRole.ANONYMOUS, new String[]{ "/resources/.*", "/webreports/.*", "/asacs-api/webreports/.*", "/jsp/logon.jsp", "/LogonServlet" });
		AUTHORIZATIONS.put(UserRole.EMPLOYEE, new String[]{ "/.*" });
		AUTHORIZATIONS.put(UserRole.VOLUNTEER, new String[]{ "/.*" });
	}
	
	public static boolean authorized(User user, HttpServletRequest request) {
		boolean authorized = false;
		
		String requestedPath = request.getRequestURI();
		UserRole role = UserRole.ANONYMOUS;
		if(user != null){
			role = user.getRole();
		}
		
		String[] authorizedPaths = AUTHORIZATIONS.get(role);
		for (String authorizedPath : authorizedPaths) {
			authorized = authorized || requestedPath.matches(authorizedPath);
		}
		
		return authorized;
	}
}