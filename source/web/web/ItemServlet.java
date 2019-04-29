package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.ItemFactory;
import edu.gatech.cs6400.team81.business.ItemManager;
import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.dao.SiteDAO;
import edu.gatech.cs6400.team81.model.FoodItemCategory;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SupplyItemCategory;

/**
 * Servlet implementation class HomeServlet
 */
public class ItemServlet extends BaseServlet implements Servlet {	
	private static final long serialVersionUID = 4961433641181096163L;

	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private ItemManager itemManager;
	
	@Autowired
	private SiteDAO siteDAO;
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Site site = getLoggedOnSite(request);
		request.setAttribute("site", site);
		if("true".equalsIgnoreCase(request.getParameter("init"))){
			try {
				List<Site> sites = siteDAO.getAll();
				request.setAttribute("categories", ItemCategory.values());
				request.setAttribute("storageTypes", ItemStorageType.values());
				request.setAttribute("foodItemTypes", FoodItemCategory.values());
				request.setAttribute("supplyItemTypes", SupplyItemCategory.values());
				request.setAttribute("sites", sites);
				
				forward("/jsp/item.jsp", request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				forward("/jsp/item.jsp", request, response, new String[]{e.getMessage()});
			}
		} else if("Add Donation".equalsIgnoreCase(request.getParameter("action"))){
			try {
				Site assignedSite = siteDAO.getBySiteId(Integer.parseInt(request.getParameter("donateTo")));
				ItemCategory itemCategory = ItemCategory.valueOf(request.getParameter("category").toUpperCase());
				Item item = ItemFactory.createItem(assignedSite, itemCategory, request);
				itemManager.saveItem(item);
				
				saveMessages(request, new String[]{"Saved Donation: " + item.getName() + ", Quantity: " + item.getNumberUnits()});
				response.sendRedirect("/jsp/item.jsp");
			} catch (NumberFormatException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/item.jsp");					
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/item.jsp");					
			} catch (ApplicationException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
				response.sendRedirect("/jsp/item.jsp");					
			}
		} else if("removeItem".equalsIgnoreCase(request.getParameter("action"))){
			try {
				int itemId = Integer.parseInt( request.getParameter("itemId") );
				Item item = itemDAO.getByItemId(site.getId(), itemId);
				if(item != null){
					itemManager.removeItem(item);
					response.getWriter().write("Success");
				} else {
					response.getWriter().write("Item not found:" + itemId + " at " + site.getShortName());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());			
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (ApplicationException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
		} else if("updateQty".equalsIgnoreCase(request.getParameter("action"))){
			try {
				int newQty = Integer.parseInt( request.getParameter("newQty") );
				int itemId = Integer.parseInt( request.getParameter("itemId") );
				Item item = itemDAO.getByItemId(site.getId(), itemId);
				
				if(item != null){
					itemManager.updateQuantity(item, newQty);
					response.getWriter().write("Success");
				} else {
					response.getWriter().write("Item not found:" + itemId + " at " + site.getShortName());
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());			
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (ApplicationException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
