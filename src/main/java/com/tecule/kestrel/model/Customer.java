package com.tecule.kestrel.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * the class is annotated with @Entity, indicating that it is a JPA entity. use @Table annotation to map this entity to
 * specified table, otherwise it will be mapped to a table named "Customer".
 * 
 * @author xiangqian
 */
@Entity
@Table(name = "customer")
public class Customer {
	private long id;

	private String firstName;

	private String lastName;

	/*
	 * the id property is annotated with @Id so that JPA will recognize it as the object ID. the id property is also
	 * annotated with @GeneratedValue to indicate that the ID should be generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * The default constructor must be exists for the sake of JPA.
	 * 
	 */
	public Customer() {

	}
}
