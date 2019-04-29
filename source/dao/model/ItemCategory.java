package edu.gatech.cs6400.team81.model;

public enum ItemCategory {
	FOOD("Food"), SUPPLY("Supply");
	
	private String value;
	
	private ItemCategory(String value){
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}