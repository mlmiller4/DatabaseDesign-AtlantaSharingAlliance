package edu.gatech.cs6400.team81.api;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import edu.gatech.cs6400.team81.dao.FoodItemDAO;
import edu.gatech.cs6400.team81.dao.ShelterDAO;
import edu.gatech.cs6400.team81.model.Shelter;

@Component
@Path("webreports")
public class WebReports {
	//[{"TotalBunkCount":90,"MaleBunkCount":30,"FamilyRoomCount":10,"FemaleBunkCount":20,"ShortName":"Aarons Site","MixedBunkCount":40}]
	private static final String[] SHELTER_COLUMNNAMES = new String[]{"ShortName", "FamilyRoomCount", "MaleBunkCount", "FemaleBunkCount", "MixedBunkCount", "TotalBunkCount"};
	@Autowired
	private ShelterDAO shelterDAO;

	@Autowired
	private FoodItemDAO foodItemDAO;
	
	@GET
	@Path("shelterReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBunkRoomReport(){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {
			
			List<Map<String, Object>> reportData = shelterDAO.getSiteBunkRooms();
			String[][] reportAjax = new String[reportData.size()][];
			
			int i = 0;
			for (Map<String, Object> rowData : reportData) {
				reportAjax[i] = createBunksRow(rowData);
				i++;
			}
			Map<String, String[][]> reportShell = new HashMap<String, String[][]>();
			reportShell.put("data", reportAjax);
			json = mapper.writeValueAsString(reportShell);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Response.ok(json).build();
	}

	@GET
	@Path("mealsReport")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMealsReport(){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {			
			Object[][] reportAjax = new Object[0][6];

			List<Map<String, Object>> reportData = foodItemDAO.getMealCountsBySite();
			if(!CollectionUtils.isEmpty(reportData)){
				Map<String, Map<String, Integer>> siteCounts = new HashMap<String, Map<String,Integer>>();
				for (Map<String, Object> rowData : reportData) {
					String siteName = rowData.get("ShortName").toString();
					String foodCategory = rowData.get("FoodCategory").toString();
					Integer categoryCount = Integer.parseInt(rowData.get("TotalCount").toString());
					Map<String, Integer> counts = siteCounts.get(siteName);
					if(counts == null){
						counts = new HashMap<String, Integer>();
						siteCounts.put(siteName, counts);
					}
					counts.put(foodCategory, categoryCount);
				}
				
				reportAjax = new Object[siteCounts.keySet().size()][];
				int i = 0;
				for (String siteName : siteCounts.keySet()) {
					reportAjax[i] = createMealsRow(siteName, siteCounts.get(siteName));
					i++;
				}
			}
			
			Map<String, Object[][]> reportShell = new HashMap<String, Object[][]>();
			reportShell.put("data", reportAjax);
			json = mapper.writeValueAsString(reportShell);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Response.ok(json).build();
	}

	@GET
	@Path("shelters")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getSheltersInfo(){
		ObjectMapper mapper = new ObjectMapper();
		String json = "";
		try {	
			Map<String, Shelter> siteMap = new HashMap<String, Shelter>();

			List<Shelter> shelters = shelterDAO.getAll();
			if(!CollectionUtils.isEmpty(shelters)){

				for (Shelter shelter : shelters) {
					String siteName = shelter.getSite().getShortName();
					siteMap.put(siteName, shelter);
				}
			}
			
			json = mapper.writeValueAsString(siteMap);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return Response.ok(json).build();
	}
	/**
	 * 
	 * 
	 *<tr>
	 * 	<th>Site Name</th>
	 *  <th>Family Rooms</th>
	 *  <th>Male Bunks</th>
	 *  <th>Female Bunks</th>
	 *  <th>Mixed Bunks</th>
	 *  <th>Total Bunks</th>
	 *</tr>
	 * @param rowData
	 * @return
	 */
	private String[] createBunksRow(Map<String, Object> rowData){
		String[] rowAjax = new String[SHELTER_COLUMNNAMES.length];
		for (int i= 0; i < SHELTER_COLUMNNAMES.length; i++) {
			rowAjax[i] = rowData.get(SHELTER_COLUMNNAMES[i]) == null ? "" : rowData.get(SHELTER_COLUMNNAMES[i]).toString();
		}
		
		return rowAjax;
	}

	/**
	 * 
	 *<tr>
	 *	<th>Site Name</th>
	 *  <th>Vegetables</th>
	 *  <th>Nuts/Grains/Beans</th>
	 *  <th>Meat/Seafood</th>
	 *  <th>Dairy/Eggs</th>
	 *  <th>Possible Meals</th>
	 *</tr>
	 *
	 * @param map 
	 * @param siteName 
	 * @return
	 */
	private Object[] createMealsRow(String siteName, Map<String, Integer> categoryCounts){
		Integer vegCount = categoryCounts.get("Vegetables") == null ? 0 : categoryCounts.get("Vegetables");
		Integer nutGrainCount = categoryCounts.get("Nuts/Grains/Beans") == null ? 0 : categoryCounts.get("Nuts/Grains/Beans");
		Integer meatSeafoodCount = categoryCounts.get("Meat/Seafood") == null ? 0 : categoryCounts.get("Meat/Seafood");
		Integer dairyEggCount = categoryCounts.get("Dairy/Eggs") == null ? 0 : categoryCounts.get("Dairy/Eggs");
		Integer possibleMeals = Math.min(vegCount, Math.min(nutGrainCount, meatSeafoodCount + dairyEggCount));
		return new Object[]{siteName, vegCount, nutGrainCount, meatSeafoodCount, dairyEggCount, possibleMeals};
	}
}
