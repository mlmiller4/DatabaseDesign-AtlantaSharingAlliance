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

import edu.gatech.cs6400.team81.business.ServiceManager;
import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.Site;

/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends BaseServlet implements Servlet {	

	private static final long serialVersionUID = 253341960992061712L;

	@Autowired
	private ServiceManager serviceManager;
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public HomeServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Site site = getLoggedOnSite(request);
			
			List<ASACSService> siteServices = serviceManager.getServicesForSite(site.getId());
			
			request.setAttribute("availableServices", siteServices);
			request.setAttribute("site", site);

			forward("/jsp/home.jsp", request, response);
		} catch (SQLException e) {
			e.printStackTrace();
			forward("/jsp/home.jsp", request, response, new String[]{e.getMessage()});
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
