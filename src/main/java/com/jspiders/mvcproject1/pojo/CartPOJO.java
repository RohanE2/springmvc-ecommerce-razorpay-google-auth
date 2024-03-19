package com.jspiders.mvcproject1.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class CartPOJO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@ManyToOne
	private PopularProductsAddPOJO products;
	@ManyToOne
	private CustomerPOJO customer;
	private int quantity;
	private int size;
	private String color;
	private String image;
	
}
