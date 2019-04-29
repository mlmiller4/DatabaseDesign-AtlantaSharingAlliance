package edu.gatech.cs6400.team81.model;

import org.apache.commons.lang3.StringUtils;

public enum ItemStorageType {
	//TO DO: Need to come back and fix this error when adding a dry good
	DRYGOOD("DRY GOOD", "Dry Good"),
	REFRIGERATED("REFRIGERATED", "Refrigerated"), 
	FROZEN("FROZEN", "Frozen");
	
	private String name;
	private String value;
	
	private ItemStorageType(String name, String value){
		this.name = name;
		this.value = value;
	}

	public static ItemStorageType byName(String name){
		for (ItemStorageType itemStorageType : values()) {
			if(StringUtils.equals(name.toUpperCase(), itemStorageType.name)){
				return itemStorageType;
			}
		}
		
		return null;
	}
	
	public String getValue() {
		return value;
	}

	public String getName() {
		return name;
	}
}
