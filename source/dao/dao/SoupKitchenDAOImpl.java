package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SoupKitchen;

@Repository
public class SoupKitchenDAOImpl extends BaseDAO<SoupKitchen> implements SoupKitchenDAO, SoupKitchenSQL {

	@Override
	protected SoupKitchen mapResult(ResultSet rs) throws SQLException {
		SoupKitchen soupKitchenTO = new SoupKitchen();

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));
		soupKitchenTO.setSite(site);

		soupKitchenTO.setConditionUse(rs.getString(ClientServiceDAO.CONDITIONUSE));
		soupKitchenTO.setDescription(rs.getString(ClientServiceDAO.DESCRIPTION));
		soupKitchenTO.setHoursOperation(rs.getString(ClientServiceDAO.HOURSOPERATION));
		soupKitchenTO.setServiceCategory(ServiceCategory.valueOf(rs.getString(ClientServiceDAO.SERVICECATEGORY).toUpperCase()));
		soupKitchenTO.setServiceId(rs.getInt(SERVICEID));

		soupKitchenTO.setAvailableSeats(rs.getInt(availableSeats));

		return soupKitchenTO;
	}

	@Override
	public SoupKitchen getBySiteId(int siteId) throws SQLException{
		return getUnique(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int serviceId) throws SQLException {
		int rows = execute(DELETE_BY_SERVICEID, new Object[]{serviceId});
		return rows == 1;
	}
	
	@Override
	public int[] getAvailableSeats(int siteID) throws SQLException {
		return getIntResult(GET_AVAILABLE_SEATS, new Object[]{siteID});
	}

	@Override
	public void setAvailableSeats(int siteID, int seats) throws SQLException {
		/*int rows = */execute(UPDATE_AVAILABLE_SEATS, new Object[]{seats, siteID});
	}

	@Override
	public boolean add(SoupKitchen newService) throws SQLException {
		int rows = execute(CREATE_SOUPKITCHEN, new Object[]{newService.getSite().getId(), newService.getServiceId(), newService.getAvailableSeats()});
		return rows == 1;
	}
	
	@Override
	public boolean update(SoupKitchen updates) throws SQLException {
		int rows = execute(UPDATE_BY_SERVICEID, new Object[]{updates.getAvailableSeats(), updates.getServiceId()});
		return rows == 1;
	}
}
