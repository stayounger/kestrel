package com.tecule.kestrel.task;

import java.util.Date;
import java.util.Random;

import org.springframework.context.ApplicationContext;

import com.tecule.kestrel.env.ApplicationContextProvider;
import com.tecule.kestrel.model.Customer;
import com.tecule.kestrel.repository.CustomerRepository;
import com.tecule.kestrel.service.CustomerService;

public class DataThread implements Runnable {
	@Override
	public void run() {
		boolean fire = true;
		ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
		if (null == applicationContext) {
			System.out.println("### thread #" + Thread.currentThread().getId() + ": applicationContext is null");
			return;
		}
		CustomerRepository customerRepository = (CustomerRepository) applicationContext.getBean("customerRepository");
		if (null == customerRepository) {
			System.out.println("### thread #" + Thread.currentThread().getId() + ": customerRepository is null");
			return;
		}
		CustomerService customerService = (CustomerService) applicationContext.getBean("customerService");
		if (null == customerService) {
			System.out.println("### thread #" + Thread.currentThread().getId() + ": customerService is null");
			return;
		}

		while (true == fire) {
			Customer customer = customerRepository.find(4);

			Random random = new Random();
			int sleepSeconds = random.nextInt(3);
			if (0 >= sleepSeconds) {
				sleepSeconds = 1;
			}

			try {
				Thread.sleep(sleepSeconds * 1000);

				customer.setBirthday(new Date());
				customerService.lastCommitWinsMerge(customer);
			} catch (InterruptedException e) {
				fire = false;
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}