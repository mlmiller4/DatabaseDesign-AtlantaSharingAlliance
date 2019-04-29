package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.Client;

public interface ClientDAO {
	public static final String TABLE = "Client";
	
	public static final String IDENTIFIER = "Identifier";
	public static final String DESCRIPTION = "Description";
	public static final String NAME = "Name";
	public static final String PHONE = "Phone";
	List<Client> getUnassignedForSite(int siteId) throws SQLException;
}
