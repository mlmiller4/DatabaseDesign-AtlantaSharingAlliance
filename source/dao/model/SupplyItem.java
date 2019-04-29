package edu.gatech.cs6400.team81.model;

  public class SupplyItem extends Item {
 	private SupplyItemCategory supplyCategory;

	public SupplyItem() {
	}

	public SupplyItemCategory getSupplyCategory() {
		return this.supplyCategory;
	}

	public void setSupplyCategory(SupplyItemCategory supplyCategory) {
		this.supplyCategory = supplyCategory;
	}
}