package com.tecule.kestrel.repository;

import com.tecule.kestrel.model.Customer;

public interface CustomerRepository {
	public long persist(Customer customer);

	public Customer find(long id);

	public Customer merge(Customer customer);
}
