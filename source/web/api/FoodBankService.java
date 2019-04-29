package edu.gatech.cs6400.team81.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import edu.gatech.cs6400.team81.model.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.ItemManager;
import edu.gatech.cs6400.team81.web.BaseServlet;

@Component
@Path("foodbank")
public class FoodBankService {
	@Autowired
	private ItemManager itemManager;
	
	@GET
	@Path("itemsReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllItems( @Context HttpServletRequest request ){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			Site site = (Site)request.getSession(false).getAttribute(BaseServlet.AUTHENTICATED_USERS_SITE);
			List<Item> reportData = itemManager.getAllItems(site.getId());
			String[][] reportAjax = new String[reportData.size()][];
			
			int i = 0;
			for (Item rowData : reportData) {
				reportAjax[i] = createItemRow(rowData);
				i++;
			}
			Map<String, String[][]> reportShell = new HashMap<String, String[][]>();
			reportShell.put("data", reportAjax);
			json = mapper.writeValueAsString(reportShell);
		} catch (IOException | ApplicationException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}
		return Response.ok(json).build();
	}

	private static final int ITEM_ID_INDEX 		= 0;
	private static final int NAME_INDEX 		= 1;
	private static final int CATEGORY_INDEX 	= 2;
	private static final int SUBCATEGORY__INDEX = 3;	
	private static final int STORE_TYPE_INDEX 	= 4;	
	private static final int EXPIRE_DT_INDEX 	= 5;
	private static final int NUM_UNITS_INDEX 	= 6;
		
	private String[] createItemRow(Item rowData) {
		String[] row = new String[7];
		if(rowData != null){
			row[CATEGORY_INDEX] = rowData.getCategory().getValue();
			row[EXPIRE_DT_INDEX] = rowData.getExpireDate().toString();
			row[ITEM_ID_INDEX] = Integer.toString(rowData.getItemId());
			row[NAME_INDEX] = rowData.getName();
			row[NUM_UNITS_INDEX] = Integer.toString(rowData.getNumberUnits());
			row[STORE_TYPE_INDEX] = rowData.getStorageType().getValue();
			System.out.println(row[ITEM_ID_INDEX]);
			if(rowData.getCategory() == ItemCategory.FOOD){
				row[SUBCATEGORY__INDEX] = ((FoodItem)rowData).getFoodCategory().getValue();
			} else {
				row[SUBCATEGORY__INDEX] = ((SupplyItem)rowData).getSupplyCategory().getValue();
			}
			
		}
		return row;
	}

	@GET
	@Path("outstandingRequestReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSiteRequestedItems( @Context HttpServletRequest request ) throws SQLException{
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			Site site = (Site)request.getSession(false).getAttribute(BaseServlet.AUTHENTICATED_USERS_SITE);
			List<RequestedItem> reportData = itemManager.getAllRequestedItems(site.getId());

			String[][] reportAjax = new String[reportData.size()][];

			int i = 0;
			for (RequestedItem rowData : reportData) {
				//System.out.println(rowData.toString());
				reportAjax[i] = createRequestedItemRow(rowData);
				i++;
			}
			Map<String, String[][]> reportShell = new HashMap<String, String[][]>();
			reportShell.put("data", reportAjax);
			json = mapper.writeValueAsString(reportShell);
		} catch (IOException | ApplicationException e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity(e.toString()).build();
		}
		return Response.ok(json).build();
	}

	private String[] createRequestedItemRow(RequestedItem rowData)throws SQLException, ApplicationException{
		String[] row = new String[11];
		if(rowData != null){
			row[0] = Integer.toString(rowData.getItemId());
			row[1] = rowData.getName();
			row[2] = String.valueOf(rowData.getReqDateTime());
			row[3] = rowData.getUserId();
			row[4] = rowData.getStatus().getValue();
			row[5] = Integer.toString(rowData.getNumRequested());
			row[6] = Integer.toString(rowData.getNumFilled());
			row[7] = rowData.getStorageType().toString();
			row[8] = rowData.getCategory().toString();
			row[9] = rowData.getSubCategory().toString();
			row[10] = itemManager.isShortFall(rowData.getItemId());
 		}
		return row;
	}
}