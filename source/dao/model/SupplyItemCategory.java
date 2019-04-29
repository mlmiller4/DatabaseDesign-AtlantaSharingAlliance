package edu.gatech.cs6400.team81.model;

import org.apache.commons.lang3.StringUtils;

public enum SupplyItemCategory {
	 PERSONAL_HYGIENE("PERSONAL HYGIENE", "Personal Hygiene"),
	 CLOTHING("CLOTHING", "Clothing"),
	 SHELTER("SHELTER", "Shelter"),
	 OTHER("OTHER", "Other");
	
	private String name; 
	private String value; 

	SupplyItemCategory(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public static SupplyItemCategory byName(String name){
		for (SupplyItemCategory supplyItemCategory : values()) {
			if(StringUtils.equals(name.toUpperCase(), supplyItemCategory.name)){
				return supplyItemCategory;
			}
		}
		
		return null;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}
}
