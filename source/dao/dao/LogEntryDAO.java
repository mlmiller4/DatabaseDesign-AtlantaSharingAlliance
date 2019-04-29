package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.LogEntry;

public interface LogEntryDAO {
	public static final String CLIENTID = "ClientId";
	public static final String DATETIME = "DateTime";
	public static final String DESCOFSERVICE = "DescOfService";
	public static final String NOTES = "Notes";
	public static final String SITEID = "SiteId";

	boolean add(LogEntry logEntry) throws SQLException;

}
