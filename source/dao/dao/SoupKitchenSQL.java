package edu.gatech.cs6400.team81.dao;

public interface SoupKitchenSQL {
	public static final String GET_BY_SITEID = "SELECT * FROM SoupKitchen sk JOIN ClientService cs ON sk.serviceId = cs.serviceId JOIN Site s ON sk.siteId = s.Id WHERE sk.siteId = ?";
	
	public static final String GET_AVAILABLE_SEATS = "SELECT s.AvailableSeats FROM SoupKitchen s WHERE s.SiteId = ?";
	public static final String UPDATE_AVAILABLE_SEATS = "UPDATE SoupKitchen AS s SET s.AvailableSeats = ? WHERE s.SiteId = ?";
	public static final String DELETE_BY_SERVICEID = "DELETE FROM SoupKitchen WHERE serviceId = ?";
	
	public static final String CREATE_SOUPKITCHEN = "INSERT INTO SoupKitchen (SiteId, ServiceId, AvailableSeats) VALUES (?,?,?)";
	
	public static final String UPDATE_BY_SERVICEID = "UPDATE SoupKitchen SET AvailableSeats = ? WHERE ServiceId = ?";
}