package edu.gatech.cs6400.team81.dao;

public interface ItemSQL {
	public static final String CREATE_ITEM = "INSERT INTO Item ( SITEID, CATEGORY, EXPIREDATE, NUMBERUNITS, NAME, STORAGETYPE) VALUES (?, ?, ?, ?, ?, ?)";

	public static final String GET_ITEM = "SELECT * FROM Item i JOIN Site s ON i.siteId = s.Id WHERE i.SiteId = ? AND i.ItemID = ?;";
	
	public static final String SEARCH = "SELECT * FROM Item JOIN Site ON Item.siteId = Site.Id LEFT JOIN FoodItem  ON FoodItem.ItemId = Item.ItemId LEFT JOIN SupplyItem ON SupplyItem.ItemId = Item.ItemId "; 

	public static final String DELETE_BY_ITEMID = "DELETE FROM Item WHERE ItemId = ?";
	
	public static final String GET_BY_SITEID_ITEMID = SEARCH + " WHERE Site.Id = ? AND Item.ItemId = ?";
	
	public static final String GET_REQUESTED_ITEM_INFO = "SELECT SiteId, NumberUnits, Name FROM Item WHERE ItemId=?";
	
	public static final String UPDATE_QTY_BY_ITEMID = "UPDATE Item SET NumberUnits = ? WHERE ItemId = ?";

}