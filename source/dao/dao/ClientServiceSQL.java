package edu.gatech.cs6400.team81.dao;

public interface ClientServiceSQL {
	public static final String GET_BY_SITEID = "SELECT * FROM Site s JOIN ClientService cs ON s.Id = cs.SiteId WHERE s.Id = ?";

	public static final String GET_BY_SERVCIEID = "SELECT * FROM Site s JOIN ClientService cs ON s.Id = cs.SiteId WHERE cs.ServiceId = ?";

	public static final String GET_BY_SITEID_CATEGORY = "SELECT * FROM Site s JOIN ClientService cs ON s.Id = cs.SiteId WHERE cs.siteId = ? AND cs.serviceCategory = ?";

	public static final String DELETE_BY_SERVICEID = "DELETE FROM ClientService WHERE serviceId = ?";
	
	public static final String ADD = "INSERT INTO ClientService (SiteId, ServiceCategory, Description, HoursOperation, ConditionUse) VALUES (?,?,?,?,?)";
	
	public static final String UPDATE_BY_SERVICEID = "UPDATE ClientService SET Description = ?, HoursOperation=?,  ConditionUse=? WHERE ServiceId = ?";
}
