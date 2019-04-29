package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.MakeFoodBankRequestDAO;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.User;


public class MakeFoodBankRequestServlet extends BaseServlet implements Servlet{

	private static final long serialVersionUID = -2031513274563509310L;
	
	@Autowired
	private MakeFoodBankRequestDAO makeFoodBankRequestDAO;
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public MakeFoodBankRequestServlet() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String itemId = "";
    	String numUnits = "";
    	
		User user = getLoggedOnUser(request);
		Site site = getLoggedOnSite(request);
		request.setAttribute("site", site);
		
		if("true".equalsIgnoreCase(request.getParameter("init"))){
			
			if (request.getParameter("itemId") != null && !request.getParameter("itemId").isEmpty()){
				itemId = request.getParameter("itemId");
			} 
					
			if (request.getParameter("numUnits") != null && !request.getParameter("numUnits").isEmpty()){
				numUnits = request.getParameter("numUnits");
			} 
			
			HttpSession session = request.getSession(false);
			session.setAttribute("itemId", itemId);
			session.setAttribute("numUnits", numUnits);
			
			response.sendRedirect("/jsp/foodBankRequest.jsp");			
		
		} else if("Submit Request".equalsIgnoreCase(request.getParameter("action"))){			
		
	    	int itemId_int = Integer.parseInt(request.getParameter("itemId"));
	    	int numRequested = Integer.parseInt(request.getParameter("numberUnits"));
	    	Timestamp timeStamp = new Timestamp(Calendar.getInstance().getTimeInMillis());
			
			try {
				makeFoodBankRequestDAO.makeFoodBankRequest(itemId_int, site.getId(), timeStamp, user.getEmail(), numRequested);
				
				response.sendRedirect("/jsp/viewFoodBankItems.jsp");			
				
			} catch (SQLException e) {				
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/foodBankRequest.jsp");	
			}
		}    	
    }
    
    /**
     * AJAX calls go here
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if("Submit Request".equalsIgnoreCase(request.getParameter("action"))){			
    		Site site = getLoggedOnSite(request);
    		User user = getLoggedOnUser(request);

	    	int itemId = Integer.parseInt(request.getParameter("itemId"));
	    	int numRequested = Integer.parseInt(request.getParameter("numberUnits"));			
			try {
				makeFoodBankRequestDAO.makeFoodBankRequest(itemId, site.getId(), user.getEmail(), numRequested);
				
				response.getWriter().write(("Success"));			
				
			} catch (SQLException e) {				
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
		}
    }
}
