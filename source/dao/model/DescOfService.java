package edu.gatech.cs6400.team81.model;

public enum DescOfService {
	ADDED_TO_WAITLIST("Added to WaitList"), 
	CHANGED_WAITLIST_POS("Changed WaitList Position"),
	REMOVED_FROM_WAITLIST("Removed from WaitList");

	private String desc;
	
	private DescOfService(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
