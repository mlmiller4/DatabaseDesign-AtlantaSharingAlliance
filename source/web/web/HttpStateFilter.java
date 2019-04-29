package edu.gatech.cs6400.team81.web;

import static edu.gatech.cs6400.team81.web.BaseServlet.ERRORS;
import static edu.gatech.cs6400.team81.web.BaseServlet.MESSAGES;
import static edu.gatech.cs6400.team81.web.BaseServlet.SESSION_ERRORS;
import static edu.gatech.cs6400.team81.web.BaseServlet.SESSION_MESSAGES;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * 
 */
public class HttpStateFilter implements Filter {
	private static final String[] ALLOW_CACHE = new String[]{ "/resources/.*"};

    /**
     * Default constructor. 
     */
    public HttpStateFilter() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession(true);
		String[] savedErrors = (String[]) session.getAttribute(SESSION_ERRORS);
		String[] savedMessages = (String[]) session.getAttribute(SESSION_MESSAGES);
		
		if(savedErrors != null){
			request.setAttribute(ERRORS, savedErrors);
			session.removeAttribute(SESSION_ERRORS);
		}
		
		if(savedMessages != null){
			request.setAttribute(MESSAGES, savedMessages);
			session.removeAttribute(SESSION_MESSAGES);
		}
		
		noCache(request, response);
		
		chain.doFilter(request, response);
	}
	
	private void noCache(ServletRequest request, ServletResponse response){
		String requestedPath = ((HttpServletRequest)request).getRequestURI();
		boolean allowCache = false;
		for (String allowedPath : ALLOW_CACHE) {
			allowCache = allowCache || requestedPath.matches(allowedPath);
		}
		if(!allowCache){
			((HttpServletResponse)response).setHeader("Expires", "-1");
			((HttpServletResponse)response).setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
			((HttpServletResponse)response).setHeader("Expires", "-1");
			((HttpServletResponse)response).setHeader("Pragma", "no-cache");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
