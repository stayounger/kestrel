package com.tecule.kestrel.service;

import com.tecule.kestrel.model.Customer;

public interface CustomerService {
	public Customer create(String firstName, String lastName);
}
