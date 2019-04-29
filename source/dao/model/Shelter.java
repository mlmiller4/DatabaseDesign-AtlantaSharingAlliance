package edu.gatech.cs6400.team81.model;

public class Shelter extends ClientService implements ASACSService{
 	private int familyRoomCount;

 	private int femaleBunkCount;

 	private int maleBunkCount;

 	private int mixedBunkCount;

 	private int totalBunkCount;

	public Shelter() {
	}

	public int getFamilyRoomCount() {
		return this.familyRoomCount;
	}

	public void setFamilyRoomCount(int familyRoomCount) {
		this.familyRoomCount = familyRoomCount;
	}

	public int getFemaleBunkCount() {
		return this.femaleBunkCount;
	}

	public void setFemaleBunkCount(int femaleBunkCount) {
		this.femaleBunkCount = femaleBunkCount;
	}

	public int getMaleBunkCount() {
		return this.maleBunkCount;
	}

	public void setMaleBunkCount(int maleBunkCount) {
		this.maleBunkCount = maleBunkCount;
	}

	public int getMixedBunkCount() {
		return this.mixedBunkCount;
	}

	public void setMixedBunkCount(int mixedBunkCount) {
		this.mixedBunkCount = mixedBunkCount;
	}

	public int getTotalBunkCount() {
		return this.totalBunkCount;
	}

	public void setTotalBunkCount(int totalBunkCount) {
		this.totalBunkCount = totalBunkCount;
	}
}