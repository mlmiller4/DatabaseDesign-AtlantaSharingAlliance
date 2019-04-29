package edu.gatech.cs6400.team81.model;

import org.apache.commons.lang3.StringUtils;

public enum FoodItemCategory {
	 VEGETABLES("VEGETABLES", "Vegetables"),
	 NUTS_GRAINS_BEANS("NUTS/GRAINS/BEANS", "Nuts/Grains/Beans"),
	 MEAT_SEAFOOD("MEAT/SEAFOOD", "Meat/Seafood"),
	 DAIRY_EGGS("DAIRY/EGGS", "Dairy/Eggs"),
	 SAUCE_CONDIMENT_SEASONING("SAUCE/CONDIMENT/SEASONING", "Sauce/Condiment/Seasoning"),
	 JUICE_DRINK("JUICE/DRINK", "Juice/Drink");
	
	private String name;
	private String value; 

	FoodItemCategory(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public static FoodItemCategory byName(String name){
		for (FoodItemCategory foodItemCategory : values()) {
			if(StringUtils.equals(name.toUpperCase(), foodItemCategory.name)){
				return foodItemCategory;
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
