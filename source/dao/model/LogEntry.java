package edu.gatech.cs6400.team81.model;

import java.time.LocalDateTime;

public class LogEntry extends BasePOJO{

	private int clientId;
	private int siteId;
	private LocalDateTime dateTime;
	private String notes;
	private String descOfService;

	public LogEntry() {
		this(0, 0, null, null);
	}
	
	public LogEntry(int clientId, int siteId) {
		this(clientId, siteId, null);
	}
	
	public LogEntry(int clientId, int siteId, String descOfService) {
		this(clientId, siteId, descOfService, null);

	}	
	
	public LogEntry(int clientId, int siteId, String descOfService, String notes) {
		this.clientId = clientId;
		this.siteId = siteId;
		this.descOfService = descOfService;
		this.notes = notes;
		dateTime = LocalDateTime.now();
	}	
	
	public int getClientId() {
		return clientId;
	}

	public void setClientId(int clientId) {
		this.clientId = clientId;
	}

	public int getSiteId() {
		return siteId;
	}

	public void setSiteId(int siteId) {
		this.siteId = siteId;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getDescOfService() {
		return descOfService;
	}

	public void setDescOfService(String descOfService) {
		this.descOfService = descOfService;
	}

}
