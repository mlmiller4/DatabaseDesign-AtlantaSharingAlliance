package edu.gatech.cs6400.team81.model;

public class FoodBank extends BasePOJO implements ASACSService {

	private int serviceId;
	private Site site;
	private ServiceCategory serviceCategory;
	
	@Override
	public int getServiceId() {
		return serviceId;
	}

	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}

	@Override
	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}

	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	@Override
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}
