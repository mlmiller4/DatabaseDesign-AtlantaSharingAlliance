package edu.gatech.cs6400.team81.dao;

public interface RequestedItemSQL {
		public static final String UPDATE_STATUS_FOR_ITEM_SITE = "UPDATE RequestedItem SET Status = ? WHERE ItemId = ?";

    	public static final String GET_ALL_FOR_SITE = "SELECT * FROM RequestedItem fi JOIN Item i ON fi.ItemId = i.ItemId JOIN Site s ON fi.RequesteeSiteId = s.Id LEFT JOIN FoodItem ON FoodItem.ItemId = i.ItemId LEFT JOIN SupplyItem ON SupplyItem.ItemId = i.ItemId WHERE fi.RequesteeSiteId = ?";

    	public static final String IS_SHORTFALL = "SELECT * FROM RequestedItem fi JOIN Item i ON fi.ItemId = i.ItemId WHERE fi.ItemId = ? AND (i.NumberUnits - fi.NumRequested + fi.NumFilled) < 0";

}
