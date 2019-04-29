package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
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

import edu.gatech.cs6400.team81.dao.ShelterDAO;
import edu.gatech.cs6400.team81.model.Site;

/**
 * Servlet implementation class ShelterServlet
 */
public class ShelterServlet extends BaseServlet implements Servlet {

	private static final long serialVersionUID = -8594595426313623330L;
	
	private static final int MALE_BUNKS 	= 0;
	private static final int FEMALE_BUNKS 	= 1;
	private static final int MIXED_BUNKS 	= 2;
	private static final int TOTAL_BUNKS 	= 3;
	
	@Autowired
	private ShelterDAO shelterDAO;

    /**
     * @see BaseServlet#BaseServlet()
     */
    public ShelterServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Site site = getLoggedOnSite(request);
		
		if ("true".equalsIgnoreCase(request.getParameter("init"))) {
			try {
				int[] bunkcounts = shelterDAO.getBunkCountsBySite(site.getId());
				int roomcount = shelterDAO.getFamilyRoomCountBySite(site.getId());
				request.setAttribute("bunkcounts", bunkcounts);
				request.setAttribute("familyroomcount", roomcount);
			} catch (SQLException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.getMessage()});
			}
			
			forward("/jsp/shelter.jsp", request, response);
		} else {
			try {
				if("update".equalsIgnoreCase(request.getParameter("action"))){
					String countType = request.getParameter("countType");
					String direction = request.getParameter("direction");
					
					if("DEC".equalsIgnoreCase(direction)){
						shelterDAO.decrementCount(site.getId(), countType);
					} else {
						shelterDAO.incrementCount(site.getId(), countType);
					}
				}
				
				response.setContentType("application/json");
				response.getWriter().write(getCountsAsJson(site.getId()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.toString());
			}				
		}
	}
	
	private String getCountsAsJson(int siteId) throws SQLException, JsonGenerationException, JsonMappingException, IOException {
		int[] bunkcounts = shelterDAO.getBunkCountsBySite(siteId);
		int roomcount = shelterDAO.getFamilyRoomCountBySite(siteId);

		Map<String, Integer> allCounts = new HashMap<String, Integer>();
		allCounts.put("MaleBunks", bunkcounts[MALE_BUNKS]);
		allCounts.put("FemaleBunks", bunkcounts[FEMALE_BUNKS]);
		allCounts.put("MixedBunks", bunkcounts[MIXED_BUNKS]);
		allCounts.put("TotalBunks", bunkcounts[TOTAL_BUNKS]);
		allCounts.put("FamilyRooms", roomcount);
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.writeValueAsString(allCounts);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
