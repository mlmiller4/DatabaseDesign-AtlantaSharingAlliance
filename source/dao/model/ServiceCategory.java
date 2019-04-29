package edu.gatech.cs6400.team81.model;

public enum ServiceCategory {
	FOODPANTRY("Food Pantry", "FoodPantry"), SHELTER("Shelter", "Shelter"), SOUPKITCHEN("Soup Kitchen", "SoupKitchen"), FOODBANK("Food Bank", "FoodBank");

	private String label;
	private String value;
	private ServiceCategory(String label, String value) {
		this.label = label;
		this.value = value;
	}
	
	public String getLabel() {
		return label;
	}

	public String getValue() {
		return value;
	}
}