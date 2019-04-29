package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.dao.SoupKitchenDAO;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.User;

/**
 * Servlet implementation class SiteServlet
 */
public class SoupKitchenServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = -4760198395037880252L;

	@Autowired
	private SoupKitchenDAO soupKitchenDAO;

    /**
     * @see BaseServlet#BaseServlet()
     */
    public SoupKitchenServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Site site = getLoggedOnSite(request);
        request.setAttribute("site", site);

        if ("true".equalsIgnoreCase(request.getParameter("init"))) {
            try {
                int[] availableSeats = soupKitchenDAO.getAvailableSeats(site.getId());
                if(availableSeats != null){
                    request.setAttribute("availableSeats", availableSeats[0]);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                saveErrors(request, new String[]{e.getMessage()});
            }

            forward("/jsp/soupKitchen.jsp", request, response);
        } else {
            if("update".equalsIgnoreCase(request.getParameter("action"))){
                try {
                    int value = Integer.parseInt(request.getParameter("curValue"));
                    soupKitchenDAO.setAvailableSeats(site.getId(), value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                } catch (SQLException e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
                }
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
