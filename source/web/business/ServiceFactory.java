package edu.gatech.cs6400.team81.business;

import javax.servlet.http.HttpServletRequest;

import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.FoodPantry;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Shelter;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SoupKitchen;
import edu.gatech.cs6400.team81.web.RequestUtils;

public class ServiceFactory {

	public static final ASACSService createService(Site site, ServiceCategory category, HttpServletRequest request) {
		ASACSService service = null;
		switch (category) {
		case FOODBANK:
			service = new FoodBank();
			((FoodBank)service).setServiceCategory(ServiceCategory.FOODBANK);
			((FoodBank)service).setSite(site);
			break;
		case FOODPANTRY:
			service = new FoodPantry();
			((FoodPantry)service).setServiceCategory(ServiceCategory.FOODPANTRY);
			addClientService(site, service, request);
			
			break;
		case SHELTER:
			service = new Shelter();
			((Shelter)service).setServiceCategory(ServiceCategory.SHELTER);
			((Shelter)service).setFamilyRoomCount(RequestUtils.getIntValue(request, "familyRoomCount"));
			((Shelter)service).setFemaleBunkCount(RequestUtils.getIntValue(request, "femaleBunkCount"));
			((Shelter)service).setMaleBunkCount(RequestUtils.getIntValue(request, "maleBunkCount"));
			((Shelter)service).setMixedBunkCount(RequestUtils.getIntValue(request, "mixedBunkCount"));

			addClientService(site, service, request);

			break;
		case SOUPKITCHEN:
			service = new SoupKitchen();
			((SoupKitchen)service).setServiceCategory(ServiceCategory.SOUPKITCHEN);
			((SoupKitchen)service).setAvailableSeats(RequestUtils.getIntValue(request, "availableSeats"));
			addClientService(site, service, request);

			break;

		default:
			break;
		}
		
		return service;
	}

	private static void addClientService(Site site, ASACSService service, HttpServletRequest request) {
		((ClientService)service).setConditionUse(request.getParameter("conditionsofuse"));
		((ClientService)service).setDescription(request.getParameter("description"));
		((ClientService)service).setHoursOperation(request.getParameter("hoursofoperation"));
		((ClientService)service).setSite(site);
	}
}
