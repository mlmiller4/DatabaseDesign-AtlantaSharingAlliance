package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;

import edu.gatech.cs6400.team81.model.SoupKitchen;

public interface SoupKitchenDAO {
	public static final String TABLENAME = "SoupKitchen";
	public static final String SITEID = "SITEID";
	public static final String SERVICEID = "SERVICEID";
	public static final String availableSeats = "AVAILABLESEATS";


	int[] getAvailableSeats(int siteID) throws SQLException;
	void setAvailableSeats(int siteID, int seats) throws SQLException;


	SoupKitchen getBySiteId(int siteId) throws SQLException;


	boolean delete(int serviceId) throws SQLException;


	boolean add(SoupKitchen newService) throws SQLException;


	boolean update(SoupKitchen updates) throws SQLException;
}
