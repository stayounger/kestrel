package com.tecule.kestrel.service;

import java.util.Date;

import com.tecule.kestrel.model.Customer;

public interface CustomerService {
	public long addCustomer(String firstName, String lastName, Date birthday);

	/**
	 * call a @Transactionl method from within the same class does not work, since merge() in repository class is
	 * annotated with @Transactionl, the lastCommitWinsMerge method is defined in service layer. better solution?
	 * aspectj?
	 * 
	 * @param customer
	 * @throws InterruptedException
	 */
	public void lastCommitWinsMerge(Customer customer) throws InterruptedException;
}
