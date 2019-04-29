package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.Item;

public interface ItemDAO {
	public static final String TABLE = "Item";
	
	public static final String ITEMID = "ItemId";
	public static final String SITEID = "SiteId";
	public static final String CATEGORY = "Category";
	public static final String EXPIREDATE = "ExpireDate";
	public static final String NUMBERUNITS = "NumberUnits";
	public static final String NAME = "Name";
	public static final String STORAGETYPE = "StorageType";
	
	Item add(Item item) throws SQLException;
	List<Item> search(String whereClause) throws SQLException;
	boolean delete(int itemId) throws SQLException;
	Item getByItemId(int siteId, int itemId) throws SQLException;
	Item getRequestedItemInfo(int itemId) throws SQLException;
	boolean updateQuantity(int itemId, int newQuantity) throws SQLException;	
}
