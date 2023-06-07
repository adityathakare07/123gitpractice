package com.homeloanfinance.main.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.homeloanfinance.main.app.exception.CustomerNotFound;
import com.homeloanfinance.main.app.model.CustomerDetails;
import com.homeloanfinance.main.app.model.CustomerDocuments;
import com.homeloanfinance.main.app.response.BaseResponse;
import com.homeloanfinance.main.app.service.CustomerService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService cs;
	
	@PostMapping("/save")
	public String postCustomer(@RequestParam("panCard")MultipartFile file1,
			@RequestParam("aadharCard")MultipartFile file2,@RequestParam("photo")MultipartFile file3,
			@RequestParam("salarySlips")MultipartFile file4,@RequestParam("bankStatement") MultipartFile file5,
			@RequestParam("sanctionLetter")MultipartFile file6,
			@RequestPart("Customer")String customer) {
		log.info("begin of Customer SaveData");
		System.out.println(customer);
		ObjectMapper om=new ObjectMapper();
		CustomerDetails cust;
		System.out.println("Its work");
		try {
			cust=om.readValue(customer,CustomerDetails.class);
			
			CustomerDocuments cd=new CustomerDocuments();
			cd.setPanCard(file1.getBytes());
			cd.setAadharCard(file2.getBytes());
			cd.setPhoto(file3.getBytes());
			cd.setSalarySlips(file4.getBytes());     
			cd.setBankStatement(file5.getBytes());
			
			
			cust.getCustomerSanctionLetter().setSanctionLetter(file6.getBytes());
			cust.setCustomerdocuments(cd);
			
		  cs.addForm(cust);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return "Data Successfully Submited";
	}
System.out.println("*****GetMapping*****");
	@GetMapping("/getAllData")
	public ResponseEntity<List<CustomerDetails>> displayAllData() {
		List<CustomerDetails> displayAllData = cs.displayAllData();
		log.debug("{}",displayAllData);
		try {
			int a=10/0;
		} catch (Exception e) {
			log.error("get ArithmaticException");
		}
		return new ResponseEntity<List<CustomerDetails>>(displayAllData, HttpStatus.OK);
	}
	
	@PutMapping(value = "/update/{customerId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<CustomerDetails> DocumentUpdate(@RequestParam("panCard")MultipartFile file1,
			@RequestParam("aadharCard")MultipartFile file2,@RequestParam("photo")MultipartFile file3,
			@RequestParam("salarySlips")MultipartFile file4,@RequestParam("bankStatement") MultipartFile file5,
			@RequestParam("sanctionLetter")MultipartFile file6,
			@RequestPart("Customer")String customer,@PathVariable ("customerId")Integer customerId){
	
		ObjectMapper om = new ObjectMapper();
	CustomerDetails cu = new CustomerDetails();
System.out.println("git practice");

		try {
			cu = om.readValue(customer, CustomerDetails.class);

			System.out.println(cu);

			CustomerDocuments ad = new CustomerDocuments();

			
			ad.setPanCard(file1.getBytes());
			ad.setAadharCard(file2.getBytes());
			ad.setPhoto(file4.getBytes());
			ad.setSalarySlips(file3.getBytes());
			ad.setBankStatement(file5.getBytes());
		
			cu.setCustomerdocuments(ad);
			
			
			cu.getCustomerSanctionLetter().setSanctionLetter(file6.getBytes());
			CustomerDetails documentUpdate = cs.DocumentUpdate(cu, customerId);
			cs.addForm(documentUpdate);
	}
		 catch (IOException ie) {
				// e.printStackTrace();
			}
			return new ResponseEntity<CustomerDetails>(cu, HttpStatus.ACCEPTED);
		}
	   
	@DeleteMapping("/deleteCustomer/{customerId}")	//delete customer by id
	public ResponseEntity<BaseResponse<CustomerDetails>> deleteCustomer(@PathVariable Integer customerId) {

		Optional<CustomerDetails> customerdetail = cs.findById(customerId);
		if (customerdetail.isPresent()) {
			cs.deleteData(customerId);
			BaseResponse br = new BaseResponse<>(200, "Data Deleted Successfully", customerdetail);
			return new ResponseEntity<BaseResponse<CustomerDetails>>(br, HttpStatus.OK);

		} else {

			throw new CustomerNotFound();
		}
	}


}
