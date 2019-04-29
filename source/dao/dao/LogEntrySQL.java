package edu.gatech.cs6400.team81.dao;

public interface LogEntrySQL {
	public static final String CREATE_LOGENTRY = "INSERT INTO LogEntry (ClientId, DateTime, DescOfService, Notes, SiteId) VALUES (?,?,?,?,?)";
}
