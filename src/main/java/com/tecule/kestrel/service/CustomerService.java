package com.tecule.kestrel.service;

import java.util.Date;

public interface CustomerService {
	public long addCustomer(String firstName, String lastName, Date birthday);
}
