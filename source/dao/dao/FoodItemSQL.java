package edu.gatech.cs6400.team81.dao;

public interface FoodItemSQL {
	public static final String GET_MEAL_COUNTS_BY_SITE = "SELECT s.ShortName, fi.FoodCategory, SUM(i.NumberUnits) AS TotalCount "
			+ "FROM FoodItem fi "
			+ "JOIN Item i ON fi.ItemId = i.ItemId "
			+ "JOIN Site s ON i.SiteId = s.Id "
			+ "WHERE fi.FoodCategory IN ('Vegetables','Nuts/Grains/Beans','Meat/Seafood','Dairy/Eggs') "
			+ "AND i.ExpireDate >= ? "
			+ "GROUP BY s.ShortName, fi.FoodCategory";
	
	public static final String GET_MEAL_COUNTS = "SELECT 'All Sites', fi.FoodCategory, SUM(i.NumberUnits) AS TotalCount "
			+ "FROM FoodItem fi "
			+ "JOIN Item i ON fi.ItemId = i.ItemId "
			+ "JOIN Site s ON i.SiteId = s.Id "
			+ "WHERE fi.FoodCategory IN ('Vegetables','Nuts/Grains/Beans','Meat/Seafood','Dairy/Eggs') "
			+ "AND i.ExpireDate >= ? "
			+ "GROUP BY fi.FoodCategory";
	
	public static final String CREATE_FOODITEM = "INSERT INTO FoodItem (ITEMID, FOODCATEGORY) VALUES (?, ?)";
	
	public static final String GET_ALL_FOR_SITE = "SELECT * FROM FoodItem fi JOIN Item i ON fi.ItemId = i.ItemId JOIN Site s ON i.SiteId = s.Id WHERE i.SiteId = ?";
	
	public static final String DELETE_BY_ITEMID = "DELETE FROM FoodItem WHERE ItemId = ?";
}
