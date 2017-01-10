package com.tecule.kestrel.task;

import java.util.Date;
import java.util.Random;

import javax.persistence.OptimisticLockException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.tecule.kestrel.model.Customer;
import com.tecule.kestrel.repository.CustomerRepository;

@Service
public class FooThread implements Runnable {
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public void run() {
		boolean fire = true;

		while (true == fire) {
			Customer customer = customerRepository.find(4);

			Random random = new Random();
			int sleepSeconds = random.nextInt(3);
			if (0 >= sleepSeconds) {
				sleepSeconds = 1;
			}

			try {
				Thread.sleep(sleepSeconds * 1000);

				/*
				 * merge with retry.
				 */
				try {
					customer.setBirthday(new Date());
					customerRepository.merge(customer);
					System.out.println("^_^ thread #" + Thread.currentThread().getId() + ": save in one shot");
				} catch (OptimisticLockException e) {
					retrySaveCustomer(customer);
				} catch (HibernateOptimisticLockingFailureException e) {
					retrySaveCustomer(customer);
				}
			} catch (InterruptedException e) {
				fire = false;
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void retrySaveCustomer(Customer customer) throws InterruptedException {
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
			}
		}

		if (sleepIndex == sleepCount) {
			System.out.println(":-( thread #" + Thread.currentThread().getId() + ": no more retry tickets");
		}
	}
}
