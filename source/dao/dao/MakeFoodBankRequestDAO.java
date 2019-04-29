package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.sql.Timestamp;

public interface MakeFoodBankRequestDAO {
	
	public static final String TABLENAME = "RequestedItem";
	public static final String REQUESTEESITEID = "RequesteeSiteId";
	public static final String NUMREQUESTED = "NumberUnitsRequested";
	public static final String STATUS = "Status";
	
	void makeFoodBankRequest(int itemId, int requesteeSiteId, Timestamp currDate, String userId, int numRequested) throws SQLException;

	void makeFoodBankRequest(int itemId, int requesteeSiteId, String userId, int numRequested) throws SQLException;

}
