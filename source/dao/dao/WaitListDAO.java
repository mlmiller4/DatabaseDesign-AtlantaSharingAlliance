package edu.gatech.cs6400.team81.dao;

import java.sql.SQLException;
import java.util.List;

import edu.gatech.cs6400.team81.model.WaitList;

public interface WaitListDAO {

	public static final String TABLE = "WaitList";
	public static final String POSITION = "Position";
	
	List<WaitList> getBySiteId(int siteId) throws SQLException;

	boolean removeFromList(int siteId, int clientId) throws SQLException;

	int decrementPosition(int siteId, int beginPos, int endPos) throws SQLException;

	int incrementPosition(int siteId, int beginPos, int endPos) throws SQLException;

	boolean addToList(int siteId, int clientId, Integer position) throws SQLException;

	WaitList getBySiteIdClientId(int siteId, int clientId) throws SQLException;

	int decrementPositionToEnd(int siteId, int beginPos) throws SQLException;
}
