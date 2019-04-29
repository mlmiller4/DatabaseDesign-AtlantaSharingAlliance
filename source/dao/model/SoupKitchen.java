package edu.gatech.cs6400.team81.model;

/**
 * The persistent class for the SoupKitchen database table.
 * 
 */
  public class SoupKitchen extends ClientService implements ASACSService {
 	private int availableSeats;

 	public SoupKitchen() {
	}

	public int getAvailableSeats() {
		return this.availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}
}