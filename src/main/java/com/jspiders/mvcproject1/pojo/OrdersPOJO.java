package com.jspiders.mvcproject1.pojo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Entity
@Data
public class OrdersPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private long phone;
	private String address;
	private long postalCode;
	private String locality;
	private String state;
	private String country;
	private long amount;
	private String date;
	private String time;
	private String orderId;
	private String paymentId;
	@ManyToMany(fetch = FetchType.EAGER)
	private List<CartAdminPOJO> cart;
	@ManyToOne
	private CustomerPOJO customer;
	
}
