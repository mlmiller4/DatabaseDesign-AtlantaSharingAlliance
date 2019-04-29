package edu.gatech.cs6400.team81.dao;

public interface Grant_DenyFoodBankRequestSQL {	

	
	public static final String IS_ITEM_REQUESTED = "SELECT * FROM RequestedItem WHERE ItemId = ?";			
	
	public static final String VIEW_REQUESTS = "SELECT ItemId, RequesteeSiteId, ReqDateTime, UserId, Status, NumRequested, NumFilled FROM RequestedItem WHERE Status='Pending'";
	
	public static final String VIEW_REQUESTED_ITEMIDS = "SELECT ItemId FROM RequestedItem";
	
	public static final String GRANT_REQUEST = "UPDATE RequestedItem "
			+ "SET NumFilled = ? AND SET Status = 'Closed' "
			+ "WHERE ItemId = ?";	
	
	public static final String REDUCE_ITEMS = "UPDATE Item "
			+ "SET NumberUnits = NumberUnits - ? "
			+ "WHERE ItemId = ?";
	
	public static final String DENY_REQUEST = "UPDATE RequestedItem " 
			+ "SET NumFilled = 0, Status = 'Closed' "
			+ "WHERE ItemId = ?";
}
