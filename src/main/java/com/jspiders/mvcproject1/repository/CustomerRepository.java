package com.jspiders.mvcproject1.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;


import com.jspiders.mvcproject1.pojo.CustomerPOJO;

@Repository
public class CustomerRepository {

	private static EntityManagerFactory factory;
	private static EntityManager manager;
	private static EntityTransaction transaction;
	private static Query query;
	private String jpql;
	
	private static void openConnection() {
		factory = Persistence.createEntityManagerFactory("prod");
		manager = factory.createEntityManager();
		transaction = manager.getTransaction();
	}
	
	private static void closeConnection() {
		if(factory != null) {
			factory.close();
		}
		if(manager != null) {
			manager.close();
		}
		if(transaction.isActive()) {
			transaction.rollback();
		}
	}
	
	public CustomerPOJO login(String email, String password) {
		openConnection();
		transaction.begin();
		jpql = "from CustomerPOJO "
				+ "where email = '" + email + "' "
				+ "and password = '" + password + "'";
		query = manager.createQuery(jpql);
		List<CustomerPOJO> list = query.getResultList();
		for(CustomerPOJO admin : list) {
			transaction.commit();
			closeConnection();
			return admin;
		}
		transaction.commit();
		closeConnection();
		return null;
	}

	public CustomerPOJO addAdmin(String firstName, String lastName, String email, String password, String gogoleUserId) {
		openConnection();
		transaction.begin();
		CustomerPOJO admin = new CustomerPOJO();
		admin.setFirstName(firstName);
		admin.setLastName(lastName);
		admin.setEmail(email);
		admin.setPassword(password);
		admin.setGoogleuserId(gogoleUserId);
		manager.persist(admin);
		transaction.commit();
		closeConnection();
		return admin;
	}

	public CustomerPOJO SearchEmail(String email) {
		openConnection();
		transaction.begin();
		jpql = "from CustomerPOJO "
				+ "where email = '" + email + "'" ;
		query = manager.createQuery(jpql);
		List<CustomerPOJO> list = query.getResultList();
		for(CustomerPOJO verifyEmail : list) {
			transaction.commit();
			closeConnection();
			return verifyEmail;
		}
		transaction.commit();
		closeConnection();
		return null;
		
	}

	public void addGoogleuserId(int id, String gogoleUserId) {
		openConnection();
		transaction.begin();
		CustomerPOJO customer = manager.find(CustomerPOJO.class, id);
		customer.setGoogleuserId(gogoleUserId);
		transaction.commit();
		closeConnection();
	}

	public CustomerPOJO loginbyGoogleuserId(String email, String googleuserId) {
		openConnection();
		transaction.begin();
		jpql = "from CustomerPOJO "
				+ "where email = '" + email + "' "
				+ "and googleuserId = '" + googleuserId + "'";
		query = manager.createQuery(jpql);
		List<CustomerPOJO> list = query.getResultList();
		for(CustomerPOJO admin : list) {
			transaction.commit();
			closeConnection();
			return admin;
		}
		transaction.commit();
		closeConnection();
		return null;
	}

}
