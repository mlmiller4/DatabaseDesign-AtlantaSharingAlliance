package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.WaitListManager;
import edu.gatech.cs6400.team81.model.Client;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.WaitList;

public class WaitListServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 749055499854932098L;
	
	@Autowired
	private WaitListManager waitListManager;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Site site = getLoggedOnSite(request);
		String action = request.getParameter("action");
		if("REORDER".equalsIgnoreCase(action)){
			reorder(request, response);
		} else if("REMOVE".equalsIgnoreCase(action)){
			remove(request, response);
		} else if("ADD".equalsIgnoreCase(action)){
			if("true".equalsIgnoreCase(request.getParameter("init"))){
				try {
					List<Client> clients = waitListManager.getClientsAvailableForSite(site.getId());
					request.setAttribute("availableClients", clients);
				} catch (ApplicationException e) {
					e.printStackTrace();
					saveErrors(request, new String[]{e.toString()});
				}
				forward("/jsp/waitListAdd.jsp", request, response);
			} else {
				add(request, response, site.getId());
			}
		} else {
			try {
				List<WaitList> waitList = waitListManager.getWaitListForSite(site.getId());
				request.setAttribute("site", site);
				request.setAttribute("waitList", waitList);
				
				forward("/jsp/waitList.jsp", request, response);
			} catch (ApplicationException e) {
				forward("/jsp/error.jsp", request, response, new String[]{e.getMessage()});
			}
		}
	}
	
	private void add(HttpServletRequest request, HttpServletResponse response, int siteId) throws IOException{
		try {
			int clientId = Integer.parseInt( request.getParameter("clientIdentifier") );
			if( waitListManager.addToWaitList(siteId, clientId) ){
				saveMessages(request, new String[]{"Added client to waitlist."});
			} else {
				saveErrors(request, new String[]{"Failed to add client to waitlist."});
			}
		} catch (NumberFormatException e) {
			saveErrors(request, new String[]{e.getMessage()});
			e.printStackTrace();
		} catch (ApplicationException e) {
			saveErrors(request, new String[]{e.getMessage()});
			e.printStackTrace();
		}
		
		response.sendRedirect("/WaitListServlet");		
	}

	private void remove(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			int clientId = Integer.parseInt(request.getParameter("clientId"));
			Site site = getLoggedOnSite(request);
			
			waitListManager.removeFromWaitList(site.getId(), clientId);
			
			response.getWriter().write("SUCCESS");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (ApplicationException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	private void reorder(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Site site = getLoggedOnSite(request);
		
		try {
//			int from = Integer.parseInt(request.getParameter("from"));
			int to = Integer.parseInt(request.getParameter("to"));
			int clientId = Integer.parseInt(request.getParameter("entry"));
			
			waitListManager.updatePosition(site.getId(), clientId, to);

			response.getWriter().write("SUCCESS");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (ApplicationException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
