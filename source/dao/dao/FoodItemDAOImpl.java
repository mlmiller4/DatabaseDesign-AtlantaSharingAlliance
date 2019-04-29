package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.FoodItem;
import edu.gatech.cs6400.team81.model.FoodItemCategory;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;

@Repository
public class FoodItemDAOImpl extends BaseDAO<FoodItem> implements FoodItemDAO, FoodItemSQL{

	@Override
	protected FoodItem mapResult(ResultSet rs) throws SQLException {
		FoodItem foodItem = new FoodItem();
		foodItem.setItemId(Integer.parseInt(rs.getString(ITEMID)));
		foodItem.setFoodCategory(FoodItemCategory.byName(rs.getString(FOODCATEGORY).toUpperCase()));
		
		foodItem.setCategory(ItemCategory.valueOf(rs.getString(ItemDAO.CATEGORY).toUpperCase()));
		foodItem.setExpireDate(rs.getDate(ItemDAO.EXPIREDATE));
		foodItem.setName(rs.getString(ItemDAO.NAME));
		foodItem.setNumberUnits(rs.getInt(ItemDAO.NUMBERUNITS));
		foodItem.setStorageType(ItemStorageType.byName(rs.getString(ItemDAO.STORAGETYPE).toUpperCase()));

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));	
		
		foodItem.setSite(site);

		
		return foodItem;
	}
	
	@Override 
	public List<Map<String, Object>> getAllMealCounts() throws SQLException{
		return getResult(GET_MEAL_COUNTS, new Object[]{ new Date() });
	}
	
	@Override 
	public List<Map<String, Object>> getMealCountsBySite() throws SQLException{
		return getResult(GET_MEAL_COUNTS_BY_SITE, new Object[]{ new Date() });
	}
	
	@Override
	public boolean add(FoodItem item) throws SQLException {
		int rows = execute(CREATE_FOODITEM, new Object[]{item.getItemId(), item.getFoodCategory().getValue()});
		return rows == 1;
	}
	
	@Override
	public List<FoodItem> getAllForSite(int siteId) throws SQLException {
		return getMultiple(GET_ALL_FOR_SITE, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int itemId) throws SQLException {
		int rows = execute(DELETE_BY_ITEMID, new Object[]{itemId});
		return rows == 1;
	}
}
