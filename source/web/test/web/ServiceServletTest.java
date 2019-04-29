package edu.gatech.cs6400.team81.web;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.ServiceCategory;

public class ServiceServletTest {

	@Test
	public void testGetVacantServices() throws Exception {
		ServiceServlet underTest = new ServiceServlet();
		
		ClientService cs1 = new ClientService();
		cs1.setServiceCategory(ServiceCategory.FOODPANTRY);
		
		List<ASACSService> services = new ArrayList<ASACSService>();
		services.add(cs1);
		
		List<ServiceCategory> actual = underTest.getVacantServices(services);
		assertEquals(ServiceCategory.values().length -1 , actual.size());
	}
	
	@Test
	public void testGetVacantServicesEmpty() throws Exception {
		ServiceServlet underTest = new ServiceServlet();
		
		List<ASACSService> services = new ArrayList<ASACSService>();
		
		List<ServiceCategory> actual = underTest.getVacantServices(services);
		assertEquals(ServiceCategory.values().length, actual.size());
	}
	
	@Test
	public void testGetVacantServicesAll() throws Exception {
		ServiceServlet underTest = new ServiceServlet();
		
		List<ASACSService> services = new ArrayList<ASACSService>();
		for (ServiceCategory serviceCategory : ServiceCategory.values()) {
			ClientService cs = new ClientService();
			cs.setServiceCategory(serviceCategory);
			services.add(cs);
		}
		
		List<ServiceCategory> actual = underTest.getVacantServices(services);
		assertEquals(0, actual.size());
	}
	
	@Test
	public void testGetVacantServicesNull() throws Exception {
		ServiceServlet underTest = new ServiceServlet();
		
		List<ServiceCategory> actual = underTest.getVacantServices(null);
		assertEquals(ServiceCategory.values().length, actual.size());
	}

}
