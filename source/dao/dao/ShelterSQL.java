package edu.gatech.cs6400.team81.dao;

public interface ShelterSQL {
	public static final String GET_BY_SITEID = "SELECT * FROM Shelter sh JOIN ClientService cs ON sh.serviceId = cs.serviceId JOIN Site s ON sh.siteId = s.Id WHERE sh.siteId = ?";

	public static final String GET_ALL = "SELECT * FROM Shelter sh JOIN ClientService cs ON sh.serviceId = cs.serviceId JOIN Site s ON sh.siteId = s.Id";

	public static final String DELETE_BY_SERVICEID = "DELETE FROM Shelter WHERE serviceId = ?";
	
	public static final String CREATE_SHELTER = "INSERT INTO Shelter (SiteId, ServiceId, FamilyRoomCount, MaleBunkCount, FemaleBunkCount, MixedBunkCount, TotalBunkCount) VALUES (?, ?, ?, ?, ?, ?, ?)";

	public static final String UPDATE_BY_SERVICEID = "UPDATE Shelter SET FamilyRoomCount = ?, MaleBunkCount = ?, FemaleBunkCount = ?, MixedBunkCount = ?, TotalBunkCount = ? WHERE ServiceId = ?";
	
	public static final String GET_BUNK_COUNTS = "SELECT s.ShortName, sh.FamilyRoomCount, sh.MaleBunkCount, sh.FemaleBunkCount, sh.MixedBunkCount, sh.TotalBunkCount FROM Shelter sh JOIN Site s ON sh.siteId = s.Id WHERE sh.TotalBunkCount > 0 OR sh.FamilyRoomCount > 0 ";

	public static final String GET_BUNK_COUNTS_BY_SITE = "SELECT MaleBunkCount, FemaleBunkCount, MixedBunkCount, TotalBunkCount FROM Shelter WHERE siteId = ?";
	
	public static final String INCREMENT_COUNT_BY_SITE = "UPDATE Shelter SET %s = %s + 1 WHERE SiteId = ?";

	public static final String DECREMENT_COUNT_BY_SITE = "UPDATE Shelter SET %s = %s - 1 WHERE SiteId = ? and %s > 0";

	public static final String GET_FAMILYROOM_COUNT_BY_SITE = "SELECT FamilyRoomCount FROM Shelter WHERE siteId = ?";

	public static final String UPDATE_FAMILYROOM_COUNT_BY_SITE = "UPDATE Shelter SET FamilyRoomCount = ? WHERE SiteId = ?";

	public static final String UPDATE_COL_BY_SITE = "UPDATE Shelter SET %s = ? WHERE SiteId = ?";
	
	public static final String UPDATE_TOTAL_BUNK_BY_SITE = "UPDATE Shelter SET TotalBunkCount = ? WHERE SiteId = ?";
}