package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.SupplyItem;

public interface SupplyItemDAO {
	public static final String TABLE = "FoodItem";
	public static final String ITEMID = "ItemId";
	public static final String SUPPLYCATEGORY = "SupplyCategory";
	
	boolean add(SupplyItem insertedItem) throws SQLException;

	List<SupplyItem> getAllForSite(int siteId) throws SQLException;

	boolean delete(int itemId) throws SQLException;
}
