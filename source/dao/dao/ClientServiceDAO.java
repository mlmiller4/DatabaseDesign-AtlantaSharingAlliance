package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.ServiceCategory;

public interface ClientServiceDAO {
	public static final String SITEID = "SITEID";
	public static final String SERVICEID = "SERVICEID";
	public static final String SERVICECATEGORY = "SERVICECATEGORY";
	public static final String DESCRIPTION = "DESCRIPTION";
	public static final String HOURSOPERATION = "HOURSOPERATION";
	public static final String CONDITIONUSE = "CONDITIONUSE";
	
	List<ClientService> getBySiteId(int siteId) throws SQLException;

	boolean delete(int serviceId) throws SQLException;

	ClientService getByServiceId(int serviceId) throws SQLException;

	ClientService add(ClientService newService) throws SQLException;

	ClientService getBySiteIdCategory(int site, ServiceCategory category) throws SQLException;

	boolean update(ClientService updates) throws SQLException;
}
