package com.jspiders.mvcproject1.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.jspiders.mvcproject1.pojo.AdminPOJO;
import com.jspiders.mvcproject1.pojo.CustomerPOJO;

@Repository
public class AdminRepository {
	

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

	public AdminPOJO login(String userID, String password) {
		openConnection();
		transaction.begin();
		jpql = "from AdminPOJO "
				+ "where userID = '" + userID + "' "
				+ "and password = '" + password + "'";
		query = manager.createQuery(jpql);
		List<AdminPOJO> list = query.getResultList();
		for(AdminPOJO admin : list) {
			transaction.commit();
			closeConnection();
			return admin;
		}
		transaction.commit();
		closeConnection();
		return null;
	}
	
	
	
}
