package com.tecule.kestrel.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tecule.kestrel.model.Customer;

/**
 * the @Repository annotation means it's a Spring bean class, a bean will created by Spring application context, the
 * bean name is also specified.
 * 
 * @author xiangqian
 *
 */
@Repository("customerRepository")
public class CustomerRepositoryImpl implements CustomerRepository {
	/*
	 * ?
	 */
	@PersistenceContext
	private EntityManager entityManager;

	// use spring transactional annotation
	@Override
	@Transactional
	public long persist(Customer customer) {
		entityManager.persist(customer);
		return customer.getId();
	}

	@Override
	public Customer find(long id) {
		return entityManager.find(Customer.class, id);
	}

	@Override
	@Transactional
	public Customer merge(Customer customer) {
		return entityManager.merge(customer);		
	}
}
