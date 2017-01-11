package com.tecule.kestrel.service;

import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
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

	@Override
	public void lastCommitWinsMerge(Customer customer) throws InterruptedException {
		try {
			Customer nc = customerRepository.merge(customer);
			System.out.println(nc.getVersion());
		} catch (OptimisticLockException e) {
			retryMerge(customer);
		} catch (HibernateOptimisticLockingFailureException e) {
			retryMerge(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retryMerge(Customer customer) throws InterruptedException {
		int sleepMilliseconds = 100, sleepIndex = 1, sleepCount = 30;

		for (; sleepIndex < sleepCount; sleepIndex++) {
			try {
				Customer updatedCustomer = customerRepository.find(customer.getId());
				customer.setVersion(updatedCustomer.getVersion());
				customerRepository.merge(customer);

				System.out.println("^_^ thread #" + Thread.currentThread().getId() + ": retry successfully in "
						+ sleepIndex + " time(s)");
				break;
			} catch (OptimisticLockException e) {
				try {
					Thread.sleep(sleepMilliseconds);
				} catch (InterruptedException e1) {
					throw e1;
				}
			} catch (HibernateOptimisticLockingFailureException e) {
				try {
					Thread.sleep(sleepMilliseconds);
				} catch (InterruptedException e1) {
					throw e1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (sleepIndex == sleepCount) {
			System.out.println(":-( thread #" + Thread.currentThread().getId() + ": no more retry tickets");
		}
	}
}
