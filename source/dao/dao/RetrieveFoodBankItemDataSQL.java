package edu.gatech.cs6400.team81.dao;

public interface RetrieveFoodBankItemDataSQL {

	// Gets list of food bank items located at one or more user-specified food banks
	public static final String GET_FOODBANK_ITEMS = "SELECT i.Name, i.NumberUnits, i.ExpirationDate, i.Storage Type"
			+ "FROM Item AS i WHERE i.SiteId IN ?;";
	
	// Gets list of food bank items from all food banks - no siteID value is specified
	public static final String GET_FOODBANK_ITEMS_ALL_SITES = "SELECT i.Name, i.NumberUnits, i.ExpirationDate, i.Storage Type FROM Item AS i;";
	
	
}
