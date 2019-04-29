package edu.gatech.cs6400.team81.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import edu.gatech.cs6400.team81.business.ApplicationException;
import edu.gatech.cs6400.team81.business.ItemFactory;
import edu.gatech.cs6400.team81.business.ItemManager;
import edu.gatech.cs6400.team81.dao.AccountDAO;
import edu.gatech.cs6400.team81.dao.FoodBankDAO;
import edu.gatech.cs6400.team81.dao.ItemDAO;
import edu.gatech.cs6400.team81.dao.UserDAO;
import edu.gatech.cs6400.team81.model.FoodBank;
import edu.gatech.cs6400.team81.model.Item;
import edu.gatech.cs6400.team81.model.User;
import edu.gatech.cs6400.team81.model.UserBuilder;

@Component
@Path("bulk")
public class BulkUpload {
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Autowired
	private ItemManager itemManager; 
	
	@Autowired
	private FoodBankDAO foodBankDAO;
	
	@POST
	@Path("addUsersCsv")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addNewUsers(	@FormDataParam("file") InputStream fileInputStream,
									@FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
		
		try {
			List<CSVRecord> list = readCsvFile(fileInputStream);

			for (CSVRecord csvRecord : list) {
				User user = new UserBuilder().withCSVRecord(csvRecord).build();
				if(user != null){
					accountDAO.createAccount(user.getAccount());
					userDAO.createUser(user);
				}
			}
			return Response.ok("SUCCESS").build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.ok("ERROR:" + e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok("ERROR:" + e.getMessage()).build();
		}
	}

	@POST
	@Path("addSitesCsv")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addNewSites(	@FormDataParam("file") InputStream fileInputStream,
									@FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
		
		try {
			List<CSVRecord> list = readCsvFile(fileInputStream);

			for (CSVRecord csvRecord : list) {
				System.out.println(csvRecord.toString());
			}
			
			return Response.ok("SUCCESS").build();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return Response.ok("ERROR:" + e.getMessage()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok("ERROR:" + e.getMessage()).build();
		}
	}
	
	@POST
	@Path("addDonationsCsv")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response addNewDonations(	@FormDataParam("file") InputStream fileInputStream,
										@FormDataParam("file") FormDataContentDisposition contentDispositionHeader){
		
		int count = 0;
		int total = 0;
		try {
			List<CSVRecord> list = readCsvFile(fileInputStream);

			FoodBank cache = null;
			total = list.size();
			for (CSVRecord csvRecord : list) {
				try {
					int siteId = Integer.parseInt(csvRecord.get(ItemDAO.SITEID.toUpperCase()));
					if(cache == null || siteId != cache.getSite().getId()){
						cache = foodBankDAO.getBySiteId(siteId);
					}
					if(cache != null){
						Item item = ItemFactory.createItem(cache.getSite(), csvRecord);
						itemManager.saveItem(item);
						count++;
					} else {
						System.err.println("No FoodBank associated with site: " + siteId);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ApplicationException e) {
					e.printStackTrace();
				}
			}
			
			return Response.ok("Imported " + count + " of " + total + " records in " + contentDispositionHeader.getFileName()).build();
		} catch (IOException e) {
			e.printStackTrace();
			return Response.ok("ERROR:" + e.getMessage()).build();
		}
	}
	
	protected List<CSVRecord> readCsvFile(InputStream fileInputStream) throws IOException {
		List<CSVRecord> list = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(fileInputStream, StandardCharsets.UTF_8));

		CSVParser csvParser = new CSVParser(br, CSVFormat.EXCEL.withFirstRecordAsHeader());
		try {
			list = csvParser.getRecords();
		} finally {
			csvParser.close();
		}

		return list;
	}
}
