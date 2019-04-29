package edu.gatech.cs6400.team81.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import edu.gatech.cs6400.team81.dao.ClientServiceDAO;
import edu.gatech.cs6400.team81.dao.FoodBankDAO;
import edu.gatech.cs6400.team81.dao.FoodPantryDAO;
import edu.gatech.cs6400.team81.dao.ShelterDAO;
import edu.gatech.cs6400.team81.dao.SoupKitchenDAO;
import edu.gatech.cs6400.team81.model.ASACSService;
import edu.gatech.cs6400.team81.model.ClientService;
import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.FoodPantry;
import edu.gatech.cs6400.team81.model.ServiceCategory;
import edu.gatech.cs6400.team81.model.Shelter;
import edu.gatech.cs6400.team81.model.Site;
import edu.gatech.cs6400.team81.model.SoupKitchen;

@RunWith(MockitoJUnitRunner.class)
public class ServiceManagerTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	@Mock
	private ClientServiceDAO clientServiceDAO;

	@Mock
	private SoupKitchenDAO soupKitchenDAO;

	@Mock
	private ShelterDAO shelterDAO;

	@Mock
	private FoodPantryDAO foodPantryDAO;

	@Mock
	private FoodBankDAO foodBankDAO;

	@InjectMocks
	private ServiceManager underTest;

	private static final int SITEID = 1;
	private static final int FOODBANK_SERVICEID = 20;
	private static final int FOODPANTRY_SERVICEID = 30;
	private static final int SHELTER_SERVICEID = 40;
	private static final int SOUPKITCHEN_SERVICEID = 50;

	private static final Site SITE = new Site();
	private static final ClientService FOODPANTRY = new FoodPantry();
	private static final ClientService SOUPKITCHEN = new SoupKitchen();
	private static final ClientService SHELTER = new Shelter();
	private static final FoodBank FOODBANK = new FoodBank();
	static {
		SITE.setId(SITEID);
		FOODPANTRY.setServiceCategory(ServiceCategory.FOODPANTRY);
		FOODPANTRY.setServiceId(FOODPANTRY_SERVICEID);
		FOODPANTRY.setSite(SITE);

		SOUPKITCHEN.setServiceCategory(ServiceCategory.SOUPKITCHEN);
		SOUPKITCHEN.setServiceId(SOUPKITCHEN_SERVICEID);
		SOUPKITCHEN.setSite(SITE);

		SHELTER.setServiceCategory(ServiceCategory.SHELTER);
		SHELTER.setServiceId(SHELTER_SERVICEID);
		SHELTER.setSite(SITE);

		FOODBANK.setServiceCategory(ServiceCategory.FOODBANK);
		FOODBANK.setServiceId(FOODBANK_SERVICEID);
		FOODBANK.setSite(SITE);
	}

	@Test
	public void testRemoveService_CantRemoveFinalService() throws SQLException, ApplicationException {
		expectedException.expect(ApplicationException.class);
		expectedException.expectMessage("Cannot remove service, a site must have atleast 1 service");

		List<ClientService> services = new ArrayList<ClientService>();
		services.add(FOODPANTRY);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(foodBankDAO.getBySiteId(SITEID)).thenReturn(null);

		when(clientServiceDAO.delete(FOODPANTRY_SERVICEID)).thenReturn(true);
		when(foodPantryDAO.delete(FOODPANTRY_SERVICEID)).thenReturn(true);

		underTest.removeService(SITEID, FOODPANTRY_SERVICEID, ServiceCategory.FOODPANTRY);
	}

	@Test
	public void testRemoveService_FoodBank() throws SQLException, ApplicationException {
		List<ClientService> services = new ArrayList<ClientService>();
		services.add(FOODPANTRY);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(foodBankDAO.getBySiteId(SITEID)).thenReturn(FOODBANK);

		when(clientServiceDAO.delete(FOODBANK_SERVICEID)).thenReturn(true);
		when(foodBankDAO.delete(SITEID)).thenReturn(true);

		boolean actual = underTest.removeService(SITEID, FOODBANK_SERVICEID, ServiceCategory.FOODBANK);
		assertTrue(actual);

		verify(foodBankDAO, times(1)).delete(SITEID);
		verify(clientServiceDAO, never()).delete(FOODBANK_SERVICEID);
	}

	@Test
	public void testRemoveService_FoodPantry() throws SQLException, ApplicationException {
		List<ClientService> services = new ArrayList<ClientService>();
		services.add(SHELTER);
		services.add(FOODPANTRY);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(clientServiceDAO.getByServiceId(FOODPANTRY_SERVICEID)).thenReturn(FOODPANTRY);

		when(clientServiceDAO.delete(FOODPANTRY_SERVICEID)).thenReturn(true);
		when(foodPantryDAO.delete(FOODPANTRY_SERVICEID)).thenReturn(true);

		boolean actual = underTest.removeService(SITEID, FOODPANTRY_SERVICEID, ServiceCategory.FOODPANTRY);
		assertTrue(actual);

		verify(foodPantryDAO, times(1)).delete(FOODPANTRY_SERVICEID);
		verify(clientServiceDAO, times(1)).delete(FOODPANTRY_SERVICEID);
	}

	@Test
	public void testRemoveService_Shelter() throws SQLException, ApplicationException {
		List<ClientService> services = new ArrayList<ClientService>();
		services.add(SHELTER);
		services.add(FOODPANTRY);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(shelterDAO.getBySiteId(SITEID)).thenReturn(null);

		when(clientServiceDAO.getByServiceId(SHELTER_SERVICEID)).thenReturn(SHELTER);

		when(clientServiceDAO.delete(SHELTER_SERVICEID)).thenReturn(true);
		when(shelterDAO.delete(SHELTER_SERVICEID)).thenReturn(true);

		boolean actual = underTest.removeService(SITEID, SHELTER_SERVICEID, ServiceCategory.SHELTER);
		assertTrue(actual);

		verify(shelterDAO, times(1)).delete(SHELTER_SERVICEID);
		verify(clientServiceDAO, times(1)).delete(SHELTER_SERVICEID);
	}

	@Test
	public void testRemoveService_SoupKitchen() throws SQLException, ApplicationException {
		List<ClientService> services = new ArrayList<ClientService>();
		services.add(SHELTER);
		services.add(SOUPKITCHEN);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(soupKitchenDAO.getBySiteId(SITEID)).thenReturn(null);

		when(clientServiceDAO.getByServiceId(SOUPKITCHEN_SERVICEID)).thenReturn(SOUPKITCHEN);

		when(clientServiceDAO.delete(SOUPKITCHEN_SERVICEID)).thenReturn(true);
		when(soupKitchenDAO.delete(SOUPKITCHEN_SERVICEID)).thenReturn(true);

		boolean actual = underTest.removeService(SITEID, SOUPKITCHEN_SERVICEID, ServiceCategory.SOUPKITCHEN);
		assertTrue(actual);

		verify(soupKitchenDAO, times(1)).delete(SOUPKITCHEN_SERVICEID);
		verify(clientServiceDAO, times(1)).delete(SOUPKITCHEN_SERVICEID);
	}

	@Test
	public void testGetBySiteId_FoodPantry() throws ApplicationException, SQLException {
		underTest.getBySiteId(SITEID, ServiceCategory.FOODPANTRY);
		verify(foodPantryDAO, times(1)).getBySiteId(SITEID);
	}
	@Test
	public void testGetBySiteId_Shelter() throws ApplicationException, SQLException {
		underTest.getBySiteId(SITEID, ServiceCategory.SHELTER);
		verify(shelterDAO, times(1)).getBySiteId(SITEID);
	}
	@Test
	public void testGetBySiteId_SoupKitchen() throws ApplicationException, SQLException {
		underTest.getBySiteId(SITEID, ServiceCategory.SOUPKITCHEN);
		verify(soupKitchenDAO, times(1)).getBySiteId(SITEID);
	}

	@Test
	public void testGetServicesForSite() throws SQLException {
		List<ClientService> services = new ArrayList<ClientService>();
		services.add(FOODPANTRY);
		services.add(SHELTER);
		services.add(SOUPKITCHEN);

		when(clientServiceDAO.getBySiteId(SITEID)).thenReturn(services);
		when(foodBankDAO.getBySiteId(SITEID)).thenReturn(FOODBANK);

		List<ASACSService> actual = underTest.getServicesForSite(SITEID);
		assertEquals(4, actual.size());
		verify(clientServiceDAO, times(1)).getBySiteId(SITEID);
		verify(foodBankDAO, times(1)).getBySiteId(SITEID);
	}

	@Test
	public void testSaveService_FoodBank() throws ApplicationException, SQLException {
		underTest.saveService(FOODBANK);
		verify(foodBankDAO, times(1)).add(FOODBANK);
	}

	@Test
	public void testSaveService_FoodPantry() throws ApplicationException, SQLException {
		FoodPantry newService = mock(FoodPantry.class);
		when(newService.getSite()).thenReturn(SITE);
		when(newService.getServiceCategory()).thenReturn(ServiceCategory.FOODPANTRY);

		when(clientServiceDAO.getBySiteIdCategory(SITEID, ServiceCategory.FOODPANTRY)).thenReturn(FOODPANTRY);
		underTest.saveService(newService);
		verify(clientServiceDAO, times(1)).add(newService);
		verify(foodPantryDAO, times(1)).add((FoodPantry) newService);
		verify(newService, times(1)).setServiceId(FOODPANTRY_SERVICEID);
	}

	@Test
	public void testSaveService_Shelter() throws ApplicationException, SQLException {
		Shelter newService = mock(Shelter.class);
		when(newService.getSite()).thenReturn(SITE);
		when(newService.getServiceCategory()).thenReturn(ServiceCategory.SHELTER);

		when(clientServiceDAO.getBySiteIdCategory(SITEID, ServiceCategory.SHELTER)).thenReturn(SHELTER);
		underTest.saveService(newService);
		verify(clientServiceDAO, times(1)).add(newService);
		verify(shelterDAO, times(1)).add((Shelter) newService);
		verify(newService, times(1)).setServiceId(SHELTER_SERVICEID);
	}

	@Test
	public void testSaveService_SoupKitchen() throws ApplicationException, SQLException {
		SoupKitchen newService = mock(SoupKitchen.class);
		when(newService.getSite()).thenReturn(SITE);
		when(newService.getServiceCategory()).thenReturn(ServiceCategory.SOUPKITCHEN);

		when(clientServiceDAO.getBySiteIdCategory(SITEID, ServiceCategory.SOUPKITCHEN)).thenReturn(SOUPKITCHEN);
		underTest.saveService(newService);
		verify(clientServiceDAO, times(1)).add(newService);
		verify(soupKitchenDAO, times(1)).add((SoupKitchen) newService);
		verify(newService, times(1)).setServiceId(SOUPKITCHEN_SERVICEID);
	}
}
