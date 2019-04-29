package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.RequestedItem;

public interface Grant_DenyFoodBankRequestDAO {
	
	//protected abstract RESULT mapResult(ResultSet rs) throws SQLException;
	
	public static final String TABLENAME = "RequestedItem";
	public static final String NUMFILLED = "NumFilled";
	public static final String STATUS = "Status";
	public static final String NUMBERUNITS = "NumberUnits";
	
	boolean isItemRequested(int itemId) throws SQLException;	
	List<RequestedItem> viewRequests() throws SQLException;
	List<Integer> viewItemIDs() throws SQLException;
	void grantRequest(int numFilled, int itemId) throws SQLException;
	void denyRequest(int itemId) throws SQLException;
	void reduceItemCount(int numToReduce, int itemId) throws SQLException;

}
