package edu.gatech.cs6400.team81.dao;

public interface SupplyItemSQL {
	public static final String CREATE_SUPPLYITEM = "INSERT INTO SupplyItem (ITEMID, SUPPLYCATEGORY) VALUES (?, ?)";
	
	public static final String GET_ALL_FOR_SITE = "SELECT * FROM SupplyItem si JOIN Item i ON si.ItemId = i.ItemId JOIN Site s ON i.SiteId = s.Id WHERE i.SiteId = ?";
	
	public static final String DELETE_BY_ITEMID = "DELETE FROM SupplyItem WHERE ItemId = ?";
}
