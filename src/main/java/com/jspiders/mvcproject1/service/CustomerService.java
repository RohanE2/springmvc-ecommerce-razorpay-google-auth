package com.jspiders.mvcproject1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.jspiders.mvcproject1.pojo.CustomerPOJO;
import com.jspiders.mvcproject1.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository repository;

	public CustomerPOJO login(String email, String password) {
		CustomerPOJO admin = repository.login(email,password);
		return admin;
	}

	public CustomerPOJO addAdmin(String firstName, String lastName, String email, String password, String gogoleUserId) {
		CustomerPOJO admin = repository.addAdmin(firstName,lastName,email,password,gogoleUserId);
		return admin;
	}

	public CustomerPOJO SearchEmail(String email) {
		CustomerPOJO verifyEmail = repository.SearchEmail(email);
		return verifyEmail;
	}

	public void addGoogleuserId(int id, String gogoleUserId) {
		repository.addGoogleuserId(id,gogoleUserId);
	}

	public CustomerPOJO loginbyGoogleuserId(String email, String googleuserId) {
		CustomerPOJO admin = repository.loginbyGoogleuserId(email,googleuserId);
		return admin;
	}
}
