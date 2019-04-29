package edu.gatech.cs6400.team81.dao;

public interface MakeFoodBankRequestSQL {
	
	public static final String MAKE_FOOD_BANK_REQUEST = "INSERT INTO RequestedItem (ItemID, RequesteeSiteID, ReqDateTime, UserId, Status, NumRequested, NumFilled) "
			+ "VALUES (?, ?, ?, ?, 'Pending', ?, 0);";

}
