package edu.gatech.cs6400.team81.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.ServiceFactory;
import edu.gatech.cs6400.team81.business.ServiceManager;
import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.User;

/**
 * Servlet implementation class SiteServlet
 */
public class ServiceServlet extends BaseServlet implements Servlet {
	private static final long serialVersionUID = 5542912207135855058L;

	private enum Action {
		ADD, REMOVE, EDIT;
	}

	@Autowired
	private ServiceManager serviceManager;

	/**
	 * @see BaseServlet#BaseServlet()
	 */
	public ServiceServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String actionParam = request.getParameter("action");
		try {
			Action action = Action.valueOf(actionParam.toUpperCase());
			switch (action) {
			case ADD:
				addService(request, response);
				break;
			case REMOVE:
				removeService(request, response);
				break;
			case EDIT:
				editService(request, response);
				break;
			default:
				forward("/jsp/error.jsp", request, response,
						new String[] { "Invalid request: Processing action [" + actionParam + "]" });
			}
		} catch (SQLException e) {
			e.printStackTrace();
			forward("/jsp/error.jsp", request, response,
					new String[] { "Invalid request: Processing action [" + actionParam + "] - " + e.getMessage() });
		} catch (ApplicationException e) {
			e.printStackTrace();
			forward("/jsp/error.jsp", request, response,
					new String[] { "Invalid request: Processing action [" + actionParam + "] - " + e.getMessage() });
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void addService(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		User user = getLoggedOnUser(request);
		int siteId = user.getSiteId();
		List<ASACSService> siteServices = serviceManager.getServicesForSite(siteId);
		List<ServiceCategory> vacantServices = getVacantServices(siteServices);

		if ("true".equalsIgnoreCase(request.getParameter("init"))) {
			request.setAttribute("vacantServices", vacantServices);
			forward("/jsp/addService.jsp", request, response);
		} else {
			String selectedService = request.getParameter("selectedService");
			if (selectedService != null && !selectedService.equals("CANCELLED")) {
				ServiceCategory category = ServiceCategory.valueOf(selectedService.trim());
				String[] validationErrors = validateService(category, request);
				if (validationErrors != null && validationErrors.length > 0){
					forward("/jsp/addService.jsp", request, response, validationErrors);
				}
				Site site = getLoggedOnSite(request);
				ASACSService newService = ServiceFactory.createService(site, category, request);
				serviceManager.saveService(newService);
				saveMessages(request, new String[]{"Added new " + category.getLabel() + " Service."});
			}
			response.sendRedirect("/SiteServlet");
		}
	}

	private String[] validateService(ServiceCategory category, HttpServletRequest request) {
		List<String> errors = new ArrayList<String>(); 
		if(category == ServiceCategory.FOODBANK){
			//TODO: validate FOODBANK
		} else {
			//TODO: validate ClientService
			switch (category) {
			case FOODPANTRY:
				//TODO: validate FOODPANTRY	
				break;
			case SHELTER:
				//TODO: validate SHELTER
				break;
			case SOUPKITCHEN:
				//TODO: validate SOUPKITCHEN
				break;
	
			default:
				break;
			}
		}
		
		return errors.toArray(new String[errors.size()]);
	}

	private void removeService(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException, ServletException, IOException {
		try {
			User user = getLoggedOnUser(request);
			int siteId = user.getSiteId();
			int serviceId = RequestUtils.getIntValue(request, "serviceId");
			ServiceCategory category = ServiceCategory.valueOf(request.getParameter("serviceCategory"));

			//TODO confirm from user if Items still assigned to FoodBank
			//TODO confirm from user if WaitList still assigned to Shelter
			boolean success = serviceManager.removeService(siteId, serviceId, category);
			if (success) {
				saveMessages(request, new String[]{"Removed Service: " + category.getLabel() + "."});
			}
		} catch (Exception e) {
			saveErrors(request, new String[]{e.getMessage()});
		}
		
		response.sendRedirect("/SiteServlet");

	}

	private void editService(HttpServletRequest request, HttpServletResponse response)
			throws ApplicationException, ServletException, IOException, SQLException {
		// User user = getLoggedOnUser(request);
		Site site = getLoggedOnSite(request);
		
		ServiceCategory category = ServiceCategory.valueOf(request.getParameter("serviceCategory"));

		ClientService siteService = serviceManager.getBySiteId(site.getId(), category);

		if ("true".equalsIgnoreCase(request.getParameter("init"))) {
			request.setAttribute("service", siteService);
			forward("/jsp/editService.jsp", request, response);
		} else {
			ASACSService updatedService = ServiceFactory.createService(site, category, request);
			ASACSService currentService = serviceManager.getBySiteId(site.getId(), category);
			BeanUtils.copyProperties(updatedService, currentService, new String[]{"siteId", "serviceId", "serviceCategory"});
			serviceManager.updateService(currentService);
			
			saveMessages(request, new String[]{"Updated Service: " + category.getLabel() + "."});
			response.sendRedirect("/SiteServlet");		
		}
	}

	protected List<ServiceCategory> getVacantServices(List<ASACSService> siteServices) {
		List<ServiceCategory> allCategories = new ArrayList<ServiceCategory>();
		allCategories.addAll(Arrays.asList(ServiceCategory.values()));
		if (siteServices != null) {
			List<ServiceCategory> currentServices = new ArrayList<ServiceCategory>();
			for (ASACSService clientService : siteServices) {
				currentServices.add(clientService.getServiceCategory());
			}

			allCategories.removeAll(currentServices);
		}

		return allCategories;
	}
}
