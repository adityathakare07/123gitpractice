package com.homeloanfinance.main.app.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.homeloanfinance.main.app.model.CustomerDetails;
import com.homeloanfinance.main.app.repository.CustomerRepository;
import com.homeloanfinance.main.app.service.CustomerService;
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository cr;
	@Override
	public void addForm(CustomerDetails cust) {
		cr.save(cust);
		
	}
	@Override
	public List<CustomerDetails> displayAllData() {
		return (List<CustomerDetails>)cr.findAll();
	
	}
	@Override
	public CustomerDetails DocumentUpdate(CustomerDetails cu, Integer customerId) {
		
		return cr.save(cu);
	}
	@Override
	public Optional<CustomerDetails> findById(Integer customerId) {
		Optional<CustomerDetails> findById= cr.findById(customerId);
		return findById;
	}
	@Override
	public void deleteData(Integer customerId) {
		cr.deleteById(customerId);
		
	}
	
}
