package com.homeloanfinance.main.app.service;

import java.util.List;
import java.util.Optional;

import com.homeloanfinance.main.app.model.CustomerDetails;

public interface CustomerService {

	public void addForm(CustomerDetails cust);

	public List<CustomerDetails> displayAllData();

	public CustomerDetails DocumentUpdate(CustomerDetails cu, Integer customerId);

	public Optional<CustomerDetails> findById(Integer customerId);

	public void deleteData(Integer customerId);

}
