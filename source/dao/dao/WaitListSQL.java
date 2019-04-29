package edu.gatech.cs6400.team81.dao;

public interface WaitListSQL {
	public static final String GET_BY_SITEID = "SELECT * FROM WaitList wl JOIN Client c ON wl.ClientId = c.Identifier JOIN Site s ON wl.SiteId = s.Id WHERE wl.SiteId = ? ORDER BY wl.Position ASC";

	public static final String GET_BY_SITEID_CLIENTID = "SELECT * FROM WaitList wl JOIN Client c ON wl.ClientId = c.Identifier JOIN Site s ON wl.SiteId = s.Id WHERE wl.SiteId = ? AND wl.ClientId = ?";

	public static final String CREATE = "INSERT INTO WaitList (ClientId, SiteId, Position) VALUES (?, ?, ?)";

	public static final String LAST_POSITION_BY_SITE = "SELECT MAX(Position) FROM WaitList WHERE SiteId = ?";

	public static final String INCREMENT_BETWEEN = "UPDATE WaitList SET Position = (Position + 1) WHERE SiteId = ? AND Position >= ? AND Position < ?";

	public static final String DECREMENT_BETWEEN = "UPDATE WaitList SET Position = (Position - 1) WHERE SiteId = ? AND Position >= ? AND Position <= ?";

	public static final String DECREMENT_FROM = "UPDATE WaitList SET Position = Position - 1 WHERE SiteId = ? AND Position >= ?";

	public static final String REMOVE_FROM_LIST = "DELETE FROM WaitList WHERE SiteId = ? AND ClientId = ?";
}