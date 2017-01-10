package com.tecule.kestrel.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecule.kestrel.model.Customer;
import com.tecule.kestrel.repository.CustomerRepository;

/**
 * the @Service annotation means it's a Spring bean class, a bean will created by Spring application context, the bean
 * name is also specified.
 * 
 * @author xiangqian
 *
 */
@Service("customerService")
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public long addCustomer(String firstName, String lastName, Date birthday) {
		if (null == customerRepository) {
			System.out.println("customerRepository is null");
			return -1;
		}

		Customer customer = new Customer();
		customer.setFirstName(firstName);
		customer.setLastName(lastName);
		customer.setBirthday(birthday);
		return customerRepository.persist(customer);
	}
}
