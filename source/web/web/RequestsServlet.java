package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.ItemManager;
import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.dao.Grant_DenyFoodBankRequestDAO;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.RequestedItem;
import edu.gatech.cs6400.team81.model.RequestedItemStatus;
import edu.gatech.cs6400.team81.model.Site;

public class RequestsServlet extends BaseServlet implements Servlet{
	

	private static final long serialVersionUID = 7568036377525758379L;
	
	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private Grant_DenyFoodBankRequestDAO grant_denyDAO;
	
	@Autowired
	private	ItemManager itemManager;
	
	   /**
     * @see BaseServlet#BaseServlet()
     */
    public RequestsServlet() {
        super();
    }
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Site site = getLoggedOnSite(request);
		//ResultSet requestedItems;
    
		if("true".equalsIgnoreCase(request.getParameter("init"))){		
		
			try{		
				
				List<RequestedItem> requestedItems = grant_denyDAO.viewRequests();				
				
				RequestedItem myRequestedItem;
				int itemId;
				int siteId;
				Item tmpItem;
				
				for (int i=0; i<requestedItems.size(); i++){
				
					myRequestedItem = requestedItems.get(i);					
					itemId = myRequestedItem.getItemId();					
						
					tmpItem = itemDAO.getRequestedItemInfo(itemId);
						
					myRequestedItem.setSiteId(tmpItem.getSiteId());
					myRequestedItem.setName(tmpItem.getName());
					myRequestedItem.setNumAvailable(tmpItem.getNumberUnits());	
					
					// User only wants to see requested items located at their foodbank, and user does not want to see their own requests
					// Remove requestedItems where requestee site is user's own site, and items that are not located at user's site.
					if (myRequestedItem.getRequesteeSiteId() == site.getId() || myRequestedItem.getSiteId() != site.getId()){
						requestedItems.remove(i);
					}
				}
				
//				for (int i=0; i<requestedItems.size(); i++){
//					
//					myRequestedItem = requestedItems.get(i);					
//					
//					if (myRequestedItem.getRequesteeSiteId() == site.getId()){
//						requestedItems.remove(i);
//					}
//				}
				
				request.setAttribute("items", requestedItems);
	
				forward("/jsp/viewFoodBankRequests.jsp", request, response);
				
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			} 
//			catch (ApplicationException e) {
//				e.printStackTrace();
//				saveErrors(request, new String[] { e.toString() });
//			}
//			
			
			
			
		} else if("Deny".equalsIgnoreCase(request.getParameter("action"))){
			
			int itemId_int = Integer.parseInt(request.getParameter("itemId"));
			
			try {
				grant_denyDAO.denyRequest(itemId_int);
				
				response.sendRedirect("/RequestsServlet?init=true");
				
			} catch (SQLException e) {				
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			}
			
		} else if("grant".equalsIgnoreCase(request.getParameter("action"))){
			
			int itemId_int = Integer.parseInt(request.getParameter("itemId"));
			String strNumUnits = request.getParameter("numberOfUnits");
			System.out.println("strNumUnits = " + strNumUnits);
			
			int numUnits = Integer.parseInt(strNumUnits);			
			System.out.println("numUnits = " + numUnits);
			
			try{
				grant_denyDAO.grantRequest(numUnits, itemId_int);
				//grant_denyDAO.reduceItemCount(numUnits, itemId_int);
				
				
				
				
			} catch (SQLException e) {				
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			} 
			
		}

    
    
    }

}
