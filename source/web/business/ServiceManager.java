package edu.gatech.cs6400.team81.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gatech.cs6400.team81.dao.ClientServiceDAO;
import edu.gatech.cs6400.team81.dao.FoodBankDAO;
import edu.gatech.cs6400.team81.dao.FoodPantryDAO;
import edu.gatech.cs6400.team81.dao.ShelterDAO;
import edu.gatech.cs6400.team81.dao.SoupKitchenDAO;
import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.FoodPantry;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Shelter;
import edu.gatech.cs6400.team81.model.SoupKitchen;

@Service
public class ServiceManager {

	@Autowired
	private ClientServiceDAO clientServiceDAO;

	@Autowired
	private SoupKitchenDAO soupKitchenDAO; 

	@Autowired
	private ShelterDAO shelterDAO;
	
	@Autowired
	private FoodPantryDAO foodPantryDAO;
	
	@Autowired
	private FoodBankDAO foodBankDAO;
	
	public boolean removeService(int siteId, int serviceId, ServiceCategory category) throws ApplicationException{
		try {
			List<ASACSService> siteServices = getServicesForSite(siteId);
			if(siteServices == null || siteServices.size() <= 1 ){
				throw new ApplicationException("Cannot remove service, a site must have atleast 1 service");
			}
			ASACSService siteService = null;
			if(category == ServiceCategory.FOODBANK){
				siteService = foodBankDAO.getBySiteId(siteId);
			} else {
				siteService = clientServiceDAO.getByServiceId(serviceId);	
			}
			if(	siteService!= null && siteService.getServiceCategory() == category && siteService.getSite().getId() == siteId){
				return removeService(siteService);
			}
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
		
		return false;
	}

	private boolean removeService(ASACSService siteService) throws SQLException {
		boolean success = true;
		//coordinate service removal ref integrity
		switch(siteService.getServiceCategory()){
		case FOODBANK:
			//TODO delete donated items, requested items?
			success &= foodBankDAO.delete(siteService.getSite().getId());
			break;
		case FOODPANTRY:
			success &= foodPantryDAO.delete(siteService.getServiceId());
			success &= clientServiceDAO.delete(siteService.getServiceId());
			break;
		case SHELTER:
			success &= shelterDAO.delete(siteService.getServiceId());
			success &= clientServiceDAO.delete(siteService.getServiceId());
			break;
		case SOUPKITCHEN:
			success &= soupKitchenDAO.delete(siteService.getServiceId());
			success &= clientServiceDAO.delete(siteService.getServiceId());
			break;
		}
		
		return success;
	}

	//TODO refactor to remove ServiceCategory if not needed
	public ClientService getBySiteId(int siteId, ServiceCategory category) throws ApplicationException {
		try {
			ClientService siteService = null;	
			switch(category){
			case FOODPANTRY:
				siteService = foodPantryDAO.getBySiteId(siteId);
				break;
			case SHELTER:
				siteService = shelterDAO.getBySiteId(siteId);
				break;
			case SOUPKITCHEN:
				siteService = soupKitchenDAO.getBySiteId(siteId);
				break;
			case FOODBANK:
			default:
				break;
			}
			return siteService;
		} catch (SQLException e) {
			throw new ApplicationException(e);
		}
	}
	
	public List<ASACSService> getServicesForSite(int siteId) throws SQLException{
		List<ASACSService> services = new ArrayList<>();
		List<ClientService> clientServices = clientServiceDAO.getBySiteId(siteId);
		if(clientServices != null){
			services.addAll(clientServices);
		}
		
		ASACSService foodBank = (ASACSService)foodBankDAO.getBySiteId(siteId);
		if(foodBank != null){
			services.add(foodBank);
		}
		
		return services;
	}

	public void saveService(ASACSService newService) throws SQLException {
		ServiceCategory category = newService.getServiceCategory();
		if(category == ServiceCategory.FOODBANK){
			foodBankDAO.add((FoodBank)newService);
		} else {
			clientServiceDAO.add((ClientService)newService);
			ClientService justAdded = clientServiceDAO.getBySiteIdCategory(newService.getSite().getId(), category);
			((ClientService)newService).setServiceId(justAdded.getServiceId());
			switch (category) {
			case FOODPANTRY:
				foodPantryDAO.add((FoodPantry)newService);
				break;
			case SHELTER:
				shelterDAO.add((Shelter)newService);
				break;
			case SOUPKITCHEN:
				soupKitchenDAO.add((SoupKitchen)newService);
				break;
	
			default:
				break;
			}
		}
	}
	

	public void updateService(ASACSService updates) throws SQLException {
		ServiceCategory category = updates.getServiceCategory();

		if(category != ServiceCategory.FOODBANK){
			clientServiceDAO.update((ClientService)updates);
			switch (category) {
			case SHELTER:
				shelterDAO.update((Shelter)updates);
				break;
			case SOUPKITCHEN:
				soupKitchenDAO.update((SoupKitchen)updates);
				break;
			default:
				break;
			}
		}
	}
}
