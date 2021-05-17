package com.coding.LojoFundrasing.Util;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coding.LojoFundrasing.Models.Donation;
import com.coding.LojoFundrasing.Models.Donor;
import com.coding.LojoFundrasing.Models.Emails;
import com.coding.LojoFundrasing.Services.DonationService;
import com.coding.LojoFundrasing.Services.DonorService;
import com.coding.LojoFundrasing.Services.EmailService;

@Component
public class ExcelUtil {
	@Autowired
	private DonorService dservice;
	@Autowired
	private EmailService eservice;
	@Autowired
	private DonationService donservice;
	
	public void getSheetDetails(String excelPath)
			throws EncryptedDocumentException, InvalidFormatException, IOException {
		
		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));

		// Retrieving the number of sheets in the Workbook
		System.out.println("Workbook has " + workbook.getNumberOfSheets() + " Sheets : ");

		System.out.println("Retrieving Sheets using for-each loop");
		
		for (Sheet sheet : workbook) {
			System.out.println("=> " + sheet.getSheetName());
		}
	}
	
	public void readExcelSheet(String excelPath)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		List<String> list = new ArrayList<String>();

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));
		System.out.println("workbook created");

		int x = workbook.getNumberOfSheets();
		
		System.out.println("number of sheets " + x);

		int noOfColumns = 0;
		List<Cell> headers = new ArrayList<Cell>();
		Cell header = null;
		Cell value = null;
		List<Cell> values = new ArrayList<Cell>();
		
		// Getting the Sheet at index zero
		for (int i = 0; i < x; i++) {

			Sheet sheet1 = workbook.getSheetAt(i);
			
			System.out.println("sheet1 " + sheet1);
			Iterator<Row> rowIterator = sheet1.iterator();

			noOfColumns = sheet1.getRow(i).getLastCellNum();
			
			System.out.println("number of columns " + noOfColumns);
			

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();
			int NameColumn = 0;
			int EmailColumn = 0;
			int LastNameColumn = 0;
			int AmountColumn = 0;
			int TimeColumn = 0;
			int RefcodeColumn = 0;
			int DateColumn = 0;
			String emailValue = null;
			String nameValue = null;
			String LNValue = null;
			Double amount = null;
			String refcode = null;
			Date date = null;
			Donor donor = null;
			Date dateValue = new Date();
			Date timeValue = null;
			Donation donation  = null;
			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {

	                   
	                	Cell cell = cellIterator.next();
	                	System.out.println("CELL: " + cell.getAddress());
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							System.out.println("Header: " + header);
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							
							String headerValue = dataFormatter.formatCellValue(header);
							if (headerValue.contentEquals("FirstName")) {
								NameColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("firstname")) {
								NameColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("LastName")) {
								LastNameColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("lastname")) {
								LastNameColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("email")) {
								EmailColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Email")) {
								EmailColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Amount")) {
								AmountColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Date")) {
								DateColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Refcode")) {
								RefcodeColumn = header.getColumnIndex();
							}
							System.out.println("Headers: " + headers);
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									/*System.out.println("Value: " + value);
									values.add(value);
									System.out.println("Values: " + values);
									System.out.println("-----header check-----");*/
									if (cell.getColumnIndex() == EmailColumn) {
										emailValue = dataFormatter.formatCellValue(cell);
										System.out.println(emailValue);
									}
									else if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										System.out.println(nameValue);
										/*System.out.print("map: " + userMap);
								        System.out.print("usermap section");*/
								        /*Set<String> keys = userMap.keySet();
								        System.out.print(keys);
								        for(String key : keys) {
								            System.out.println(key);
								            System.out.println(userMap.get(key));    
								        }*/
									}
									else if (cell.getColumnIndex() == LastNameColumn) {
										LNValue = dataFormatter.formatCellValue(cell);
										System.out.println(LNValue);
									}
									else if (cell.getColumnIndex() == AmountColumn) {
										String amount1 = dataFormatter.formatCellValue(cell);
										amount = Double.parseDouble(amount1); 
										System.out.println(amount);
									}
									else if (cell.getColumnIndex() == DateColumn) {
										String dateValue1 = dataFormatter.formatCellValue(cell);
										date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										System.out.println(dateValue1);
										System.out.println("Simple date: " + date);
									}
									else if (cell.getColumnIndex() == RefcodeColumn) {
										refcode = dataFormatter.formatCellValue(cell);
										System.out.println("Refcode: " + refcode);
										System.out.println("EMAIL AFTER: " + emailValue);
										System.out.println("REFCODE AFTER: " + refcode);
										System.out.println("Name AFTER: " + nameValue);
										System.out.println("LN AFTER: " + LNValue);
										System.out.println("AMOUNT AFTER: " + amount);
										System.out.println("DATE AFTER: " + date);
							    	    if (dservice.findDonorbyEmail(emailValue) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	    		Emails email = donation.getEmailDonation();
					    	    		eservice.getEmailData(email);
					    	    		dservice.getDonorData(donor);
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorbyEmail(emailValue);
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	    		Emails email = donation.getEmailDonation();
					    	    		eservice.getEmailData(email);
					    	    		dservice.getDonorData(donor);
					    	        	refcode = null;
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }
									}
							//}
							/*else {
							System.out.println("-----check-----");
									System.out.println("EMAIL AFTER: " + emailValue);
									System.out.println("REFCODE AFTER: " + refcode);
									System.out.println("Name AFTER: " + nameValue);
									System.out.println("LN AFTER: " + LNValue);
									System.out.println("AMOUNT AFTER: " + amount);
									System.out.println("DATE AFTER: " + date);
									donor = dservice.findDonorbyEmail(emailValue);
									System.out.println("DONOR: " + dservice.findDonorbyEmail(emailValue));*/
					    	       /*if (dservice.findDonorbyEmail(emailValue) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorbyEmail(emailValue);
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }*/
								//}
							}
		    	        }

	            }
		}
	}
	public void readExcelSheetEmails(String excelPath)
			throws EncryptedDocumentException, InvalidFormatException, IOException, ParseException {

		List<String> list = new ArrayList<String>();

		// Creating a Workbook from an Excel file (.xls or .xlsx)
		Workbook workbook = WorkbookFactory.create(new File(excelPath));
		System.out.println("workbook created");

		int x = workbook.getNumberOfSheets();
		
		System.out.println("number of sheets " + x);

		int noOfColumns = 0;
		List<Cell> headers = new ArrayList<Cell>();
		Cell header = null;
		Cell value = null;
		List<Cell> values = new ArrayList<Cell>();
		
		// Getting the Sheet at index zero
		for (int i = 0; i < x; i++) {

			Sheet sheet1 = workbook.getSheetAt(i);
			
			System.out.println("sheet1 " + sheet1);
			Iterator<Row> rowIterator = sheet1.iterator();

			noOfColumns = sheet1.getRow(i).getLastCellNum();
			
			System.out.println("number of columns " + noOfColumns);
			

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();
			int NameColumn = 0;
			int RefcodeColumn = 0;
			int DateColumn = 0;
			String nameValue = null;
			String refcode = null;
			Emails email = null;
			Date date = null;
			Date dateValue = new Date();
			System.out.println("The sheet number is " + i + 1);
			// 2. Or you can use a for-each loop to iterate over the rows and columns
			System.out.println("\n\nIterating over Rows and Columns using for-each loop\n");
	        while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();
	             Iterator<Cell> cellIterator = row.cellIterator();
	                while(cellIterator.hasNext()) {

	                   
	                	Cell cell = cellIterator.next();
	                	System.out.println("CELL: " + cell.getAddress());
						if (row.getRowNum() == 0) {
							//header = cell.getAddress();
							header = cell;
							System.out.println("Header: " + header);
							headers.add(header);
							//System.out.println("Header column: " + header.getColumn());
							
							String headerValue = dataFormatter.formatCellValue(header);
							if (headerValue.contentEquals("Name")) {
								NameColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Date")) {
								DateColumn = header.getColumnIndex();
							}
							if (headerValue.contentEquals("Refcode")) {
								RefcodeColumn = header.getColumnIndex();
							}
							System.out.println("Headers: " + headers);
						}
						else if (row.getRowNum() > 0){
							//if (refcode == null) {
								//if (cell.getColumnIndex() == headers.get(j).getColumnIndex()) {
									value = cell;
									/*System.out.println("Value: " + value);
									values.add(value);
									System.out.println("Values: " + values);
									System.out.println("-----header check-----");*/
									if (cell.getColumnIndex() == NameColumn) {
										System.out.println("Values: " + values);
										//userMap.put(headerValue, valValue);
										System.out.println("NameColumn TWO: " + NameColumn);
										nameValue = dataFormatter.formatCellValue(cell);
										System.out.println(nameValue);
										/*System.out.print("map: " + userMap);
								        System.out.print("usermap section");*/
								        /*Set<String> keys = userMap.keySet();
								        System.out.print(keys);
								        for(String key : keys) {
								            System.out.println(key);
								            System.out.println(userMap.get(key));    
								        }*/
									}
									else if (cell.getColumnIndex() == DateColumn) {
										String dateValue1 = dataFormatter.formatCellValue(cell);
										date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateValue1);
										System.out.println(dateValue1);
										System.out.println("Simple date: " + date);
									}
									else if (cell.getColumnIndex() == RefcodeColumn) {
										refcode = dataFormatter.formatCellValue(cell);
										System.out.println("REFCODE AFTER: " + refcode);
										System.out.println("Name AFTER: " + nameValue);
										System.out.println("DATE AFTER: " + date);
							    	    if (eservice.findEmailbyRefcode(refcode) == null) {
					    	        	email = new Emails();
					    	        	//System.out.println("ID: " + id);
					    	        	email.setEmailName(nameValue);
					    	        	email.setEmaildate(date);
					    	        	email.setEmailRefcode(refcode);
					    	        	eservice.createEmail(email);
					    	    		eservice.getEmailData(email);
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + email.getId() + " Email: " + email.getEmailRefcode());
					    	        }
					    	        else {
					    	        	email = eservice.findEmailbyRefcode(refcode);
					    	        	email.setEmailName(nameValue);
					    	        	email.setEmaildate(date);
					    	        	email.setEmailRefcode(refcode);
					    	        	eservice.createEmail(email);
					    	    		eservice.getEmailData(email);
					    	        	refcode = null;
					    				System.out.println("NEW Id: " + email.getId() + " Email: " + email.getEmailRefcode());
					                }
								}
							//}
							/*else {
							System.out.println("-----check-----");
									System.out.println("EMAIL AFTER: " + emailValue);
									System.out.println("REFCODE AFTER: " + refcode);
									System.out.println("Name AFTER: " + nameValue);
									System.out.println("LN AFTER: " + LNValue);
									System.out.println("AMOUNT AFTER: " + amount);
									System.out.println("DATE AFTER: " + date);
									donor = dservice.findDonorbyEmail(emailValue);
									System.out.println("DONOR: " + dservice.findDonorbyEmail(emailValue));*/
					    	       /*if (dservice.findDonorbyEmail(emailValue) == null) {
					    	        	donor = new Donor();
					    	        	//System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.createDonor(donor);
					    	        	Long id = donor.getId();
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    				System.out.println("NEW Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					    	        }
					    	        else {
					    	        	donor = dservice.findDonorbyEmail(emailValue);
					    	        	Long id = donor.getId();
					    	        	System.out.println("ID: " + id);
					    	        	donor.setDonorFirstName(nameValue);
					    	        	donor.setDonorLastName(LNValue);
					    	        	donor.setDonorEmail(emailValue);
					    	        	dservice.updateDonor(donor);
					    	        	donation = new Donation();
					    	        	donation.setAmount(amount);
					    	        	donation.setDondate(date);
					    	        	donation.setDonor(dservice.findbyId(id));
					    	        	donation.setEmailDonation(eservice.findEmailbyRefcode(refcode));
					    	        	donservice.createDonation(donation);
					    	        	System.out.println("UPDATED Id: " + donor.getId() + " Person: " + donor.getDonorFirstName() + " Email: " + donor.getDonorEmail());
					                }*/
								//}
							}
		    	        }

	            }
		}
	}
}
