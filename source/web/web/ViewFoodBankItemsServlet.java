package edu.gatech.cs6400.team81.web;

import java.io.IOException;
//import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
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
 * Item Search / Report Version 1.0.4 
 * The user can search for an item from the
 * inventory of all Food Banks, or a single specific food bank. By using wild
 * cards, the user can view all inventory, or they can view inventory based upon
 * filters of expiration date, storage type, Food/Supply, individual category
 * within Foods or Supplies, or even a keyword search based upon the item name /
 * description. When they see an item they need, they can request a set number
 * of those items. (Each request will be for a specific number of a single item,
 * although the user at the food bank completing each request may batch them
 * together into a single box or pallet for delivery, the ASACS does not know
 * about boxes, pallets, etc…) A user should NOT be able to request an item from
 * the Food Bank associated with their site, even through they can see items
 * from their site. (In this case, the user should simply reduce the number
 * available when they take the item from the Food Bank and put it into use in
 * another service, see below.) 
 * 
 * A special case exists with the item search
 * report. If the report shows an item that is “owned” by the Food Bank
 * associated with the site that the current user is associated with, the user
 * will be able to modify the numbers of that the item (for example, if some has
 * been thrown out due to reaching it’s expiration date or gotten damaged or
 * lost.) If at any time the count of an item reaches zero, its record should be
 * removed from inventory completely.
 * 
 * @author aaron
 *
 */
public class ViewFoodBankItemsServlet extends BaseServlet implements Servlet {

	private static final long serialVersionUID = 3156014459741792496L;
	
	private static final int SITE_ID 		= 1;
	private static final int EXPIRE_DT 	= 2;
	private static final int STORAGE_TYP	= 3;
	private static final int CATEGORY		= 4;
	private static final int SUBCATEGORY	= 5;
	private static final int NAME 		= 6;

	private static final Map<Integer, String> COLUMNAMES = new HashMap<Integer, String>();
	static{
		COLUMNAMES.put(1, "SiteId");
		COLUMNAMES.put(2, "ExpireDate");
		COLUMNAMES.put(3, "StorageType");
		COLUMNAMES.put(4, "Category");
		COLUMNAMES.put(6, "Name");
	}
	
	@Autowired
	private ItemManager itemManager;

	@Autowired
	private ItemDAO itemDAO;
	
	@Autowired
	private SiteDAO siteDAO; 
	
	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ViewFoodBankItemsServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			if("true".equalsIgnoreCase(request.getParameter("init"))){
				initView(request, response);
				forward("/jsp/viewFoodBankItems.jsp", request, response);
			}else if ("viewMySite".equalsIgnoreCase(request.getParameter("action"))) {
				Site site = getLoggedOnSite(request);
				request.setAttribute("items", itemManager.getAllItems(site.getId()));
				forward("/jsp/viewFoodBankItems.jsp", request, response);
			}else if ("search".equalsIgnoreCase(request.getParameter("action"))) {
				search(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			saveErrors(request, new String[] { e.toString() });
			forward("/jsp/viewFoodBankItems.jsp", request, response);
		} catch (ApplicationException e) {
			e.printStackTrace();
			saveErrors(request, new String[] { e.toString() });
			forward("/jsp/viewFoodBankItems.jsp", request, response);
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void search(HttpServletRequest request, HttpServletResponse response) throws IOException{
		List<String> sqlCriteria = new ArrayList<String>();
		int criteriaIndex = 1;

		while ( request.getParameter("sel-criteria-" + criteriaIndex ) != null ) {
				int columnKey = Integer.parseInt(request.getParameter("sel-criteria-" + criteriaIndex ));
				if(columnKey >= SITE_ID && columnKey <= NAME){
						try {
						String columnName = COLUMNAMES.get(columnKey);
						String value = request.getParameter("sel-value-" + criteriaIndex);
						String matchType = request.getParameter("sel-selector-" + criteriaIndex);
						String connector = request.getParameter("sel-connect-" + criteriaIndex);	
						StringBuilder clause = new StringBuilder();
						switch(columnKey){
						case SITE_ID:
							clause.append("Item.").append(columnName).append(" = '").append(Integer.parseInt(value)).append("' ");
							break;
						case CATEGORY:
							clause.append("Item.").append(columnName).append(" = '").append(ItemCategory.valueOf(value).getValue()).append("' ");
							break;
						case STORAGE_TYP:
							clause.append("Item.").append(columnName).append(" = '").append(ItemStorageType.byName(value).getValue()).append("' ");
							break;
						case NAME:
							clause.append("Item.").append(columnName);
							MATCHTYPE type = MATCHTYPE.valueOf(matchType);
							if(type == MATCHTYPE.MATCH){
								clause.append(" = '").append(value).append("' ");
							} else {
								clause.append(" LIKE '%").append(value).append("%' ");
							}
							break;
						case SUBCATEGORY:
							FoodItemCategory foodCategory = FoodItemCategory.byName(value);
							if(foodCategory != null){
								clause.append("FoodItem.").append("FoodCategory = '").append(foodCategory.getValue()).append("' ");
							} else {
								SupplyItemCategory supplyItemCategory = SupplyItemCategory.byName(value);
								clause.append("SupplyItem.").append("SupplyCategory = '").append(supplyItemCategory.getValue()).append("' ");
							}
							break;
						case EXPIRE_DT:
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								TIMECOMPARE timeCompare = TIMECOMPARE.valueOf(matchType);
								sdf.parse(value);
								clause.append("Item.").append(columnName).append((timeCompare == TIMECOMPARE.BEFORE ? " < '" : " > '")).append(value).append("' ");
							break;
						}
						if(connector != null){
							clause.append(" ").append(CONNECTOR.valueOf(connector).toString()).append(" ");
						}
						
						sqlCriteria.add(clause.toString());
					} catch (ParseException e) {
						e.printStackTrace();
					}
					criteriaIndex++;
				}
		}
		
		StringBuilder whereClause = new StringBuilder(" WHERE "); 
		for (String string : sqlCriteria) {
			whereClause.append(string);
		}
				
		try {
			List<Item> items = itemDAO.search(whereClause.toString());
			ObjectMapper mapper = new ObjectMapper();
			
			response.setContentType("application/json");
			response.getWriter().write(mapper.writeValueAsString(items));
		} catch (JsonGenerationException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (JsonMappingException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
		}
	}

	private void initView(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Site> sites = siteDAO.getAll();
		request.setAttribute("sites", sites);
		request.setAttribute("storageTypes", ItemStorageType.values());
		request.setAttribute("categories", ItemCategory.values());
		request.setAttribute("foodcategories", FoodItemCategory.values());
		request.setAttribute("supplycategories", SupplyItemCategory.values());
		request.setAttribute("searchEnabled", new Boolean(true));
	}
	
	private enum CONNECTOR{
		AND, OR;
	}
	
	private enum MATCHTYPE{
		MATCH, LIKE;	
	}
	
	private enum TIMECOMPARE{
		BEFORE, AFTER;
	}
}
