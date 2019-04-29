package edu.gatech.cs6400.team81.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.stereotype.Repository;

import edu.gatech.cs6400.team81.model.LogEntry;

@Repository
public class LogEntryDAOImpl extends BaseDAO<LogEntry> implements LogEntryDAO, LogEntrySQL{

	@Override
	protected LogEntry mapResult(ResultSet rs) throws SQLException {
		LogEntry logEntry = new LogEntry();
		logEntry.setClientId(rs.getInt(CLIENTID));
		logEntry.setDateTime(rs.getTimestamp(DATETIME).toLocalDateTime());
		logEntry.setDescOfService(rs.getString(DESCOFSERVICE));
		logEntry.setNotes(rs.getString(NOTES));
		logEntry.setSiteId(rs.getInt(SITEID));

		return logEntry;
	}
	
	@Override
	public boolean add(LogEntry logEntry) throws SQLException{
		int rows = execute(CREATE_LOGENTRY, new Object[]{logEntry.getClientId(), Timestamp.valueOf(logEntry.getDateTime()), logEntry.getDescOfService(), logEntry.getNotes(), logEntry.getSiteId()});
		return rows == 1;
	}
}
