package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.gatech.cs6400.team81.model.FoodItem;

public interface FoodItemDAO {
	public static final String TABLE = "FoodItem";
	public static final String ITEMID = "ItemId";
	public static final String FOODCATEGORY = "FoodCategory";
	
	List<Map<String, Object>> getAllMealCounts() throws SQLException;

	boolean add(FoodItem item) throws SQLException;

	List<FoodItem> getAllForSite(int siteId) throws SQLException;

	List<Map<String, Object>> getMealCountsBySite() throws SQLException;

	boolean delete(int itemId) throws SQLException;
}
