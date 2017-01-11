package com.tecule.kestrel.task;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecule.kestrel.model.Customer;
import com.tecule.kestrel.repository.CustomerRepository;
import com.tecule.kestrel.service.CustomerService;

@Service
public class FooThread implements Runnable {
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CustomerService customerService;

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
