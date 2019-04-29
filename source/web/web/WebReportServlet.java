package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.MealsManager;

/**
 * Servlet implementation class SiteServlet
 */
public class WebReportServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private MealsManager mealsManager;
	
    /**
     * @see BaseServlet#BaseServlet()
     */
    public WebReportServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reportType = request.getParameter("reportType");
		if("BUNKS".equals(reportType)){
			forward("/jsp/reports/availableBunksReport.jsp", request, response);
		} else if ("OUTS".equals(reportType)){
			forward("/jsp/reports/outstandingRequestsReport.jsp", request, response);
		} else if("MEALS".equals(reportType)){
			try {
				List<String> neededItems = new ArrayList<String>();
				Integer mealsCount = mealsManager.getMealsAvailable(neededItems);
				request.setAttribute("totalMealsCount", mealsCount);
				request.setAttribute("neededItems", neededItems);
			} catch (ApplicationException e) {
				e.printStackTrace();
				saveErrors(request, new String[]{e.toString()});
			}
			
			forward("/jsp/reports/availableMealsReport.jsp", request, response);
		} else {
			forward("/jsp/reports/webReports.jsp", request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
