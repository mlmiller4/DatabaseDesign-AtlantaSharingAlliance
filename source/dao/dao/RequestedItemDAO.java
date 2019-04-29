package edu.gatech.cs6400.team81.dao;

import edu.gatech.cs6400.team81.model.RequestedItem;

import java.sql.SQLException;
import java.util.List;

public interface RequestedItemDAO {
        public static final String TABLE = "RequestedItem";
	public static final String ITEMID = "ItemId";
	public static final String REQUESTEESITEID = "RequesteeSiteId";
	public static final String REQDATETIME = "ReqDateTime";
	public static final String USERID = "UserId";
	public static final String STATUS = "Status";
	public static final String NUMREQUESTED = "NumRequested";
	public static final String NUMFILLED = "NumFilled";

	boolean closeRequestsForItem(int itemId) throws SQLException;

	List<RequestedItem> getAllForSite(int siteId) throws SQLException;

	boolean isShortFall(int itemId) throws SQLException;

}
