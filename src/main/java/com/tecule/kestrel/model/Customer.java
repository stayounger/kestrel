package com.tecule.kestrel.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.springframework.format.annotation.DateTimeFormat;

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

	private Date birthday;
	
	private int version;

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

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(style = "M-")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * The default constructor must be exists for the sake of JPA.
	 * 
	 */
	public Customer() {

	}
}
