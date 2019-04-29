package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;

@Repository
public class ClientServiceDAOImpl extends BaseDAO<ClientService> implements ClientServiceDAO, ClientServiceSQL{

	@Override
	protected ClientService mapResult(ResultSet rs) throws SQLException {
		ClientService clientService = new ClientService();
		clientService.setConditionUse(rs.getString(CONDITIONUSE));
		clientService.setDescription(rs.getString(DESCRIPTION));
		clientService.setHoursOperation(rs.getString(HOURSOPERATION));
		clientService.setServiceCategory(ServiceCategory.valueOf(rs.getString(SERVICECATEGORY).toUpperCase()));
		clientService.setServiceId(rs.getInt(SERVICEID));
		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));
		
		clientService.setSite(site);
		
		return clientService;
	}
	
	@Override
	public List<ClientService> getBySiteId(int siteId) throws SQLException {
		return getMultiple(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int serviceId) throws SQLException {
		int rows = execute(DELETE_BY_SERVICEID, new Object[]{serviceId});
		return rows == 1;
	}
	
	@Override
	public ClientService getByServiceId(int serviceId) throws SQLException {
		return getUnique(GET_BY_SERVCIEID, new Object[]{serviceId});
	}
	
	@Override
	public ClientService getBySiteIdCategory(int site, ServiceCategory category) throws SQLException {
		return getUnique(GET_BY_SITEID_CATEGORY, new Object[]{site, category.getValue()});
	}
	
	@Override
	public ClientService add(ClientService newService) throws SQLException {
		Integer serviceId =  executeForGeneratedKey(ADD, new Object[]{newService.getSite().getId(), newService.getServiceCategory().getValue(), newService.getDescription(), newService.getHoursOperation(), newService.getConditionUse()});
		if(serviceId != null){
			newService.setServiceId(serviceId);
			return newService;
		}
		
		return null;
	}
	
	@Override
	public boolean update(ClientService updates) throws SQLException {
		int rows = execute(UPDATE_BY_SERVICEID, new Object[]{updates.getDescription(), updates.getHoursOperation(), updates.getConditionUse(), updates.getServiceId()});
		return rows == 1;
	}
}
