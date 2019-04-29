package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.ItemCategory;
import edu.gatech.cs6400.team81.model.ItemStorageType;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SupplyItem;
import edu.gatech.cs6400.team81.model.SupplyItemCategory;

@Repository
public class SupplyItemDAOImpl extends BaseDAO<SupplyItem> implements SupplyItemDAO, SupplyItemSQL{

	@Override
	protected SupplyItem mapResult(ResultSet rs) throws SQLException {
		SupplyItem supplyItem = new SupplyItem();
		supplyItem.setItemId(Integer.parseInt(rs.getString(ITEMID)));
		supplyItem.setSupplyCategory(SupplyItemCategory.byName(rs.getString(SUPPLYCATEGORY).toUpperCase()));
		
		supplyItem.setCategory(ItemCategory.valueOf(rs.getString(ItemDAO.CATEGORY).toUpperCase()));
		supplyItem.setExpireDate(rs.getDate(ItemDAO.EXPIREDATE));
		supplyItem.setName(rs.getString(ItemDAO.NAME));
		supplyItem.setNumberUnits(rs.getInt(ItemDAO.NUMBERUNITS));
		supplyItem.setStorageType(ItemStorageType.byName(rs.getString(ItemDAO.STORAGETYPE).toUpperCase()));

		Site site = new Site();
		site.setCity(rs.getString(SiteDAO.CITY));
		site.setId(rs.getInt(SiteDAO.ID));
		site.setPhoneNumber(rs.getString(SiteDAO.PHONENUMBER));
		site.setShortName(rs.getString(SiteDAO.SHORTNAME));
		site.setState(rs.getString(SiteDAO.STATE));
		site.setStreetAddress(rs.getString(SiteDAO.STREETADDRESS));
		site.setZip(rs.getString(SiteDAO.ZIP));	
		
		supplyItem.setSite(site);

		
		return supplyItem;
	}

	@Override
	public boolean add(SupplyItem item) throws SQLException {
		int rows = execute(CREATE_SUPPLYITEM, new Object[]{item.getItemId(), item.getSupplyCategory().getValue()});
		return rows == 1;
	}
	
	@Override
	public List<SupplyItem> getAllForSite(int siteId) throws SQLException {
		return getMultiple(GET_ALL_FOR_SITE, new Object[]{siteId});
	}
	
	@Override
	public boolean delete(int itemId) throws SQLException {
		int rows = execute(DELETE_BY_ITEMID, new Object[]{itemId});
		return rows == 1;
	}
}
