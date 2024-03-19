package com.jspiders.mvcproject1.pojo;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;


@Data
@Entity
public class AdminPOJO {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String UserID;
	private String password;
}
