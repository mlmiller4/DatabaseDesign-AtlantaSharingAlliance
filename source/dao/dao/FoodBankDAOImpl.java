package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Site;

@Repository
public class FoodBankDAOImpl extends BaseDAO<FoodBank> implements FoodBankDAO, FoodBankSQL {

	@Override
	protected FoodBank mapResult(ResultSet rs) throws SQLException {
		FoodBank foodBank = new FoodBank();
		foodBank.setServiceCategory(ServiceCategory.FOODBANK);
		foodBank.setServiceId(rs.getInt(SERVICEID));
		
		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));
		
		foodBank.setSite(site);
		
		return foodBank;
	}
	
	@Override
	public FoodBank getBySiteId(int siteId) throws SQLException {
		return getUnique(GET_BY_SITEID, new Object[]{siteId});
	}

	@Override
	public boolean delete(int siteId) throws SQLException {
		int rows = execute(DELETE_BY_SITEID, new Object[]{siteId});
		return rows == 1;
	}
	
	@Override
	public boolean add(FoodBank newService) throws SQLException {
		int rows = execute(CREATE_FOODBANK, new Object[]{newService.getSite().getId()});
		return rows == 1;
	}
}
