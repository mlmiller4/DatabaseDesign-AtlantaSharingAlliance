package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.Site;

public interface SiteDAO {
	public static final String ID = "ID";
	public static final String SHORTNAME = "SHORTNAME";
	public static final String STREETADDRESS = "STREETADDRESS";
	public static final String CITY = "CITY";
	public static final String STATE = "STATE";
	public static final String ZIP = "ZIP";
	public static final String PHONENUMBER = "PHONENUMBER";
	
	Site getBySiteId(int siteId) throws SQLException;

	List<Site> getAll() throws SQLException;
}
