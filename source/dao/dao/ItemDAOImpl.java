package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.FoodItem;
import edu.gatech.cs6400.team81.model.FoodItemCategory;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SupplyItem;
import edu.gatech.cs6400.team81.model.SupplyItemCategory;

@Repository
public class ItemDAOImpl extends BaseDAO<Item> implements ItemDAO, ItemSQL{

	@Override
	protected Item mapResult(ResultSet rs) throws SQLException {
		Item item = null;
		ItemCategory category = ItemCategory.valueOf(rs.getString(ItemDAO.CATEGORY).toUpperCase());
		if(category == ItemCategory.FOOD){
			item = new FoodItem();
			((FoodItem)item).setFoodCategory(FoodItemCategory.byName(rs.getString(FoodItemDAO.FOODCATEGORY).toUpperCase()));
		} else {
			item = new SupplyItem();
			((SupplyItem)item).setSupplyCategory(SupplyItemCategory.byName(rs.getString(SupplyItemDAO.SUPPLYCATEGORY).toUpperCase()));			
		}
		item.setItemId(Integer.parseInt(rs.getString(ITEMID)));
		item.setCategory(category);
		item.setExpireDate(rs.getDate(ItemDAO.EXPIREDATE));
		item.setName(rs.getString(ItemDAO.NAME));
		item.setNumberUnits(rs.getInt(ItemDAO.NUMBERUNITS));
		item.setStorageType(ItemStorageType.byName(rs.getString(ItemDAO.STORAGETYPE).toUpperCase()));

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));	
		
		item.setSite(site);

		
		return item;
	}
	
	@Override 
	public Item add(Item item) throws SQLException{
		//SITEID, CATEGORY, EXPIREDATE, NUMBERUNITS, NAME, STORAGETYPE
		
		Object[] params = new Object[6];
		int index = 0;
		params[index] = item.getSite().getId();
		params[++index] = item.getCategory().getValue();
		params[++index] = item.getExpireDate();
		params[++index] = item.getNumberUnits();
		params[++index] = item.getName();
		params[++index] = item.getStorageType().getValue();
		
		Integer itemId = executeForGeneratedKey(CREATE_ITEM, params);
		if(itemId != null){
			item.setItemId(itemId);
			return item;
		}
		
		return null;
	}
	
	@Override
	public List<Item> search(String whereClause) throws SQLException{
		return getMultiple(SEARCH + whereClause, new Object[]{});
	}
	
	@Override
	public boolean delete(int itemId) throws SQLException {
		int rows = execute(DELETE_BY_ITEMID, new Object[]{itemId});
		return rows == 1;
	}
	
	@Override
	public Item getByItemId(int siteId, int itemId) throws SQLException {
		return getUnique(GET_BY_SITEID_ITEMID, new Object[] {siteId, itemId});
	}
	
	@Override
	public Item getRequestedItemInfo(int itemId) throws SQLException {
		return getMatchingRequestedItems(GET_REQUESTED_ITEM_INFO, new Object[]{itemId});
	}
	
	@Override
	public boolean updateQuantity(int itemId, int newQuantity) throws SQLException {
		int rows = execute(UPDATE_QTY_BY_ITEMID, new Object[]{newQuantity, itemId});
		return rows > 0;
	}
}