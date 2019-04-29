package edu.gatech.cs6400.team81.model;

public enum RequestedItemStatus {
	PENDING("Pending"), CLOSED("Closed");
	
	private String value;
	
	private RequestedItemStatus(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
