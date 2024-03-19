package com.jspiders.mvcproject1.pojo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;



import lombok.Data;

@Data
@Entity
public class PopularProductsAddPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String type;
	private String name;
	private String info;
	private String brand;
	private String gender;
	private String image;
	private String image2;
	private String image3;
	private int Size7;
	private int Size8;
	private int Size9;
	private int Size10;
	private String orColour;
	private String bColour;
	private String yColour;
	private double price;
	private String sale;
	private String newest;
	private int quantity; 
	
}
