package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.FoodPantry;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;

@Repository
public class FoodPantryDAOImpl extends BaseDAO<FoodPantry> implements FoodPantryDAO, FoodPantrySQL {

	@Override
	protected FoodPantry mapResult(ResultSet rs) throws SQLException {
		FoodPantry foodPantry = new FoodPantry();

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));
		foodPantry.setSite(site);
		
		foodPantry.setConditionUse(rs.getString(ClientServiceDAO.CONDITIONUSE));
		foodPantry.setDescription(rs.getString(ClientServiceDAO.DESCRIPTION));
		foodPantry.setHoursOperation(rs.getString(ClientServiceDAO.HOURSOPERATION));
		foodPantry.setServiceCategory(ServiceCategory.valueOf(rs.getString(ClientServiceDAO.SERVICECATEGORY).toUpperCase()));
		foodPantry.setServiceId(rs.getInt(SERVICEID));
		
		return foodPantry;
	}

	@Override
	public FoodPantry getBySiteId(int siteId) throws SQLException{
		return getUnique(GET_BY_SITEID, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int serviceId) throws SQLException {
		int rows = execute(DELETE_BY_SERVICEID, new Object[]{serviceId});
		return rows == 1;
	}
	
	@Override
	public boolean add(FoodPantry newService) throws SQLException {
		int rows = execute(CREATE_FOODPANTRY, new Object[]{newService.getServiceId(), newService.getSite().getId()});
		return rows == 1;
	}
}
