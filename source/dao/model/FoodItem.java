package edu.gatech.cs6400.team81.model;

  public class FoodItem extends Item{
 	private FoodItemCategory foodCategory;

	public FoodItem() {
	}

	public FoodItemCategory getFoodCategory() {
		return this.foodCategory;
	}

	public void setFoodCategory(FoodItemCategory foodCategory) {
		this.foodCategory = foodCategory;
	}
}