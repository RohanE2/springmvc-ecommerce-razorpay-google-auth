package com.jspiders.mvcproject1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jspiders.mvcproject1.pojo.AdminPOJO;
import com.jspiders.mvcproject1.pojo.CustomerPOJO;
import com.jspiders.mvcproject1.repository.AdminRepository;

@Service
public class AdminService {

	@Autowired
	private AdminRepository repository;

	public  AdminPOJO login(String userID, String password) {
		AdminPOJO admin = repository.login(userID,password);
		return admin;
	}
}
