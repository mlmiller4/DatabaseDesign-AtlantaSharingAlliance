package edu.gatech.cs6400.team81.business;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.gatech.cs6400.team81.dao.ClientDAO;
import edu.gatech.cs6400.team81.dao.LogEntryDAO;
import edu.gatech.cs6400.team81.dao.WaitListDAO;
import edu.gatech.cs6400.team81.model.Client;
import edu.gatech.cs6400.team81.model.DescOfService;
import edu.gatech.cs6400.team81.model.LogEntry;
import edu.gatech.cs6400.team81.model.WaitList;

@Service
public class WaitListManager {
	
	@Autowired
	private WaitListDAO waitListDAO;

	@Autowired
	private ClientDAO clientDAO;
	
	@Autowired
	private LogEntryDAO logEntryDAO;
	
	public List<WaitList> getWaitListForSite(int siteId) throws ApplicationException{
		try {
			return waitListDAO.getBySiteId(siteId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	public boolean addToWaitList(int siteId, int clientId) throws ApplicationException{
		boolean success = false;
		try {
			success = waitListDAO.addToList(siteId, clientId, null);
			if(success){
				logEntryDAO.add(new LogEntry(clientId, siteId, DescOfService.ADDED_TO_WAITLIST.toString()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		
		return success;
	}
	
	public List<Client> getClientsAvailableForSite(int siteId) throws ApplicationException{
		List<Client> clients = null;
		try {
			clients = clientDAO.getUnassignedForSite(siteId);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
		
		return clients;
	}
	
	public void updatePosition(int siteId, int clientId, int newPosition) throws ApplicationException{
		try {
			WaitList waitList = waitListDAO.getBySiteIdClientId(siteId, clientId);
			int currPosition = waitList.getPosition();

			if(waitListDAO.removeFromList(siteId, clientId)){
				if(currPosition > newPosition){
					waitListDAO.incrementPosition(siteId, newPosition, currPosition);
					waitListDAO.addToList(siteId, clientId, newPosition);
				} else {
					waitListDAO.decrementPosition(siteId, currPosition, newPosition);
					waitListDAO.addToList(siteId, clientId, newPosition);
				}
				
				logEntryDAO.add(new LogEntry(clientId, siteId, DescOfService.CHANGED_WAITLIST_POS.toString(), "From " + currPosition + " to " + newPosition));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
	
	public void removeFromWaitList(int siteId, int clientId) throws ApplicationException{
		try{
			WaitList entry = waitListDAO.getBySiteIdClientId(siteId, clientId);
			if(entry != null){
				waitListDAO.removeFromList(siteId, clientId);
				waitListDAO.decrementPositionToEnd(siteId, entry.getPosition());
				logEntryDAO.add(new LogEntry(clientId, siteId, DescOfService.REMOVED_FROM_WAITLIST.toString(), "position: " + entry.getPosition()));				
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ApplicationException(e);
		}
	}
}
