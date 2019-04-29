package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import edu.gatech.cs6400.team81.model.Shelter;

public interface ShelterDAO {
	public static final String TABLENAME = "Shelter";
	public static final String SITEID = "SiteId";
	public static final String SERVICEID = "ServiceId";
	public static final String FAMILYROOMCOUNT = "FamilyRoomCount";
	public static final String MALEBUNKCOUNT = "MaleBunkCount";
	public static final String FEMALEBUNKCOUNT = "FemaleBunkCount";
	public static final String MIXEDBUNKCOUNT = "MixedBunkCount";
	public static final String TOTALBUNKCOUNT = "TotalBunkCount";

	Shelter getBySiteId(int siteId) throws SQLException;

	boolean delete(int serviceId) throws SQLException;

	boolean add(Shelter newService) throws SQLException;

	boolean update(Shelter updates) throws SQLException;

	List<Map<String, Object>> getSiteBunkRooms() throws SQLException;

	int[] getBunkCountsBySite(int siteId) throws SQLException;

	int getFamilyRoomCountBySite(int siteId) throws SQLException;

	boolean updateFamilyRoomCount(int siteId, int value) throws SQLException;

	boolean updateBunkCount(int siteId, String columnName, int value) throws SQLException;

	boolean updateTotalBunkCount(int siteId) throws SQLException;

	List<Shelter> getAll() throws SQLException;

	boolean decrementCount(int siteId, String columnName) throws SQLException;

	boolean incrementCount(int siteId, String columnName) throws SQLException;
}
