package com.jspiders.mvcproject1.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.jspiders.mvcproject1.controller.PopularProductsAdd;
import com.jspiders.mvcproject1.pojo.CartAdminPOJO;
import com.jspiders.mvcproject1.pojo.CartPOJO;

import com.jspiders.mvcproject1.pojo.CustomerPOJO;
import com.jspiders.mvcproject1.pojo.OrdersPOJO;
import com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO;
import com.jspiders.mvcproject1.pojo.WishlistPOJO;


@Repository
public class PopularProductsAddRepository {

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
		if (factory != null) {
			factory.close();
		}
		if (manager != null) {
			manager.close();
		}
		if (transaction.isActive()) {
			transaction.rollback();
		}
	}

	public List<PopularProductsAddPOJO> viewPopularProducts() {
		openConnection();
		transaction.begin();
		jpql = " from PopularProductsAddPOJO ";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}
	public int cartTotal(int customerId) {
		openConnection();
		transaction.begin();
		jpql = "select sum(quantity) from CartPOJO where customer.id = " + customerId;
		query = manager.createQuery(jpql);
		Long totalQuantity = (Long) query.getSingleResult();
		System.out.println("fi sdjkl jj "+totalQuantity);
		if (totalQuantity != null) {
			transaction.commit();
			closeConnection();
			return totalQuantity.intValue();
		}
		transaction.commit();
		closeConnection();
		return 0;

	}


	public List<Object[]> viewCartItems(int customerId) {
		openConnection();
		transaction.begin();


		jpql = "select c.products.id,sum(c.quantity) as Prodquantity "
	            + " from CartPOJO c"
				+ " where c.customer.id  = "+ customerId 
	            + " group by c.products.id ";
		
		query = manager.createQuery(jpql);
		List<Object[]> cartItems = query.getResultList();
		
		for(Object[] cart : cartItems) {
			System.out.println("bahdeat " + cart[0] +" "+ cart[1]);
		}
		
		transaction.commit();
		closeConnection();
		return cartItems;
	}
	
	

	

	
	public PopularProductsAddPOJO addPopularProduct(String type, String name, String info, String brand, int sSize,
			int mSize, int lSize, int xSize, String orColour, String bColour, String yColour, double price,
			int quantity,String gender,String sale,String newest, String image,String image2,String image3) {
		openConnection();
		transaction.begin();
		PopularProductsAddPOJO product = new PopularProductsAddPOJO();
		product.setType(type);
		product.setName(name);
		product.setInfo(info);
		product.setBrand(brand);
		product.setSize7(sSize);
		product.setSize8(mSize);
		product.setSize9(lSize);
		product.setSize10(xSize);
		product.setOrColour(orColour);
		product.setBColour(bColour);
		product.setYColour(yColour);
		product.setPrice(price);
		product.setQuantity(quantity);
		product.setGender(gender);
		product.setSale(sale);
		product.setNewest(newest);
		product.setImage(image);
		product.setImage2(image2);
		product.setImage3(image3);
		manager.persist(product);
		transaction.commit();
		closeConnection();
		return product;

	}


	public PopularProductsAddPOJO searchProduct(int id) {
		openConnection();
		transaction.begin();
		PopularProductsAddPOJO product = manager.find(PopularProductsAddPOJO.class, id);
		transaction.commit();
		closeConnection();
		return product;
	}

	public CustomerPOJO searchCustomer(int id) {
		
			openConnection();
			transaction.begin();
			CustomerPOJO customer = manager.find(CustomerPOJO.class, id);
			transaction.commit();
			closeConnection();
			return customer;
		
	}

	public CartPOJO addItems(PopularProductsAddPOJO product, CustomerPOJO customer, int quantity,int size, String color, String image) {
		openConnection();
		transaction.begin();
		CartPOJO cart = new CartPOJO();
		cart.setProducts(product);
		cart.setCustomer(customer);
		cart.setQuantity(quantity);
		cart.setSize(size);
		cart.setColor(color);
		cart.setImage(image);
		manager.persist(cart);
		transaction.commit();
		closeConnection();
		return cart;
	}

	public CartPOJO SearchCartItems2(int customerId, int id,int size, String color) {
		openConnection();
		transaction.begin();
		jpql = "select c from CartPOJO c where c.customer.id = " + customerId + "AND c.products.id = " + id + "AND c.size = " + size + "AND c.color = '" + color+"'";
		query = manager.createQuery(jpql);
		List<CartPOJO> list = query.getResultList();
		for(CartPOJO cart :list ) {
			transaction.commit();
			closeConnection();
			return cart;
		}
		transaction.commit();
		closeConnection();
		return null;
	}
	
	public CartPOJO SearchCartItems(int customerId, int productsId ,int size,String color) {
		try {
			openConnection();
			transaction.begin();
			jpql = "select c from CartPOJO c where c.customer.id = " + customerId + " AND c.products.id = " + productsId + " AND c.size = " + size + " AND c.color = '" + color + "'";
			query = manager.createQuery(jpql);
			CartPOJO cart = (CartPOJO) query.getSingleResult();
			if (cart != null) {
				transaction.commit();
				closeConnection();
				return cart;
			}
			transaction.commit();
			closeConnection();
			return cart;
		} catch (IllegalStateException | RollbackException e) {
			openConnection();
			transaction.begin();
			String jpql = "SELECT c FROM CartPOJO c WHERE c.customer.id = :customerId AND c.products.id = :productsId";
			TypedQuery<CartPOJO> query = manager.createQuery(jpql, CartPOJO.class);
			query.setParameter("customerId", customerId);
			query.setParameter("productsId", productsId);

			CartPOJO cart = query.getSingleResult();
			transaction.commit();
			closeConnection();
			return cart;
		}

	}

	public CartPOJO updateItem(int quantity,int id) {
		System.out.println("nikal "+quantity);
			openConnection();
			transaction.begin();
			CartPOJO item = manager.find(CartPOJO.class, id);
			System.out.println("ab bata na " + item );
			item.setQuantity(quantity);
			manager.persist(item);
			transaction.commit();
			closeConnection();
			return item;
	}


	public  List<Long> proQuantity() {
	    openConnection();
	    transaction.begin();
	    jpql = "SELECT SUM(c.quantity) FROM CartPOJO c GROUP BY c.products.id";
	    query = manager.createQuery(jpql);
	    
	    List<Long> pQuantity = query.getResultList();
	    
	    if ( pQuantity !=null) {
	        transaction.commit();
	        closeConnection();
	        return pQuantity ;
	    }
	    
	    transaction.commit();
	    closeConnection();
	    return null;
	}


	
	public CartPOJO prodMax(int customerId, int id) {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from CartPOJO c"
	            + " where  c.products.id = " + id +" and c.prodquantity = (select max(prodquantity)"
	            + "                from CartPOJO "
	            + "                where products.id = " + id
	            + "					and customer.id =  " + customerId + ")";
		query = manager.createQuery(jpql);
		List<CartPOJO> prod = query.getResultList();
		System.out.println("ja  s s" + prod);
		for(CartPOJO prodMax : prod) {
			transaction.commit();
			closeConnection();
			return prodMax;
		}
		transaction.commit();
		closeConnection();
		return null;
		

		
	}
	

	
	
	public CartPOJO removeItems(int id) {
		openConnection();
		transaction.begin();
		CartPOJO removeItems = manager.find(CartPOJO.class, id);
		manager.remove(removeItems);
		transaction.commit();
		closeConnection();
		return removeItems;
	}

	public void getCartItemId(int id) {
		openConnection();
		transaction.begin();
		jpql = "select id from cartpojo where products.id = " + id;
		query = manager.createQuery(jpql);
		
	}

	

	public WishlistPOJO addWishlist(CustomerPOJO customer,PopularProductsAddPOJO product,int size,String color, String image) {
		openConnection();
		transaction.begin();
		WishlistPOJO wishlist = new WishlistPOJO();
		wishlist.setCustomer(customer);
		wishlist.setProducts(product);
		wishlist.setSize(size);
		wishlist.setColor(color);
		wishlist.setImage(image);
		manager.persist(wishlist);
		transaction.commit();
		closeConnection();
		return wishlist;
	}

	public List<WishlistPOJO> searchWishlist(int customerId) {
		openConnection();
		transaction.begin();
		jpql = "from WishlistPOJO where customer.id = " + customerId;
		query = manager.createQuery(jpql);
		List<WishlistPOJO> wishlistProducts = query.getResultList();
		transaction.commit();
		closeConnection();
		return wishlistProducts;
	}

	public WishlistPOJO searchWishlistItem(int id, int customerId,int size) {
		openConnection();
		transaction.begin();
		jpql = "select w from WishlistPOJO w "+ 
			   " where w.customer.id = " + customerId +
			   " and w.products.id = " + id + 
			   " and w.size = " + size ;
			   
		query = manager.createQuery(jpql);
		List<WishlistPOJO> searchWishlist =  query.getResultList();
		System.out.println("rohan ita iu1"+searchWishlist);
		for(WishlistPOJO searchWishlistItem : searchWishlist) {
			transaction.commit();
			closeConnection();
			System.out.println("rohan ita iu2"+searchWishlist);
			return searchWishlistItem;
		}
		System.out.println("rohan ita iu3"+searchWishlist);
		transaction.commit();
		closeConnection();
		return null;
	}

	public void removeWishlistItem(int id) {
		openConnection();
		transaction.begin();
		WishlistPOJO remove = manager.find(WishlistPOJO.class, id);
		manager.remove(remove);
		transaction.commit();
		closeConnection();
		
	}

	public WishlistPOJO updateWishlistItem(int id, int size) {
		openConnection();
		transaction.begin();
		WishlistPOJO updateWishlist = manager.find(WishlistPOJO.class, id);
		updateWishlist.setSize(size);
		updateWishlist.setAddedToCart(true);
		manager.persist(updateWishlist);
		transaction.commit();
		closeConnection();
		return updateWishlist;
	}

	public List<CartPOJO> viewCartItemsAll(int customerId) {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from CartPOJO c"
	            + " where c.customer.id = " + customerId;	
		query = manager.createQuery(jpql);
		List<CartPOJO> cartItems = query.getResultList();
		transaction.commit();
		closeConnection();
		return cartItems;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsMen() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " order by id desc";
//		
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
		
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where price between 10000 and 20000"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice2() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where price between 20000 and 40000"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice1to2() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where price between 10000 and 40000"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice3() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where price >= 40000"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice1to3() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where (price between 10000 and 20000) or (price >= 40000)"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice2to3() {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where (price between 20000 and 40000) or (price >= 40000)"
				 + " order by price";
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String btn) {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where size"+btn+ " = "  + btn;
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String activebtn, String btn) {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where size"+activebtn+ " = "  + activebtn + " and size"+btn+ " = " + btn;
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String string, String string2, String string3) {
		openConnection();
		transaction.begin();
		jpql = "select c "
	            + " from PopularProductsAddPOJO c"
	            + " where size"+string+ " = "  + string + " and size"+string2+ " = " + string2 + " and size"+string3+ " = " + string3 ;
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(ArrayList btnList,ArrayList otherActivesList,String sortActive) {
		
		openConnection();
		transaction.begin();
		String condition ="";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		for(Object list:btnList) {
			if(!condition.isEmpty()) {
				condition += " and ";
			}
		 condition += "size"+list+ " = " + list;
		}
		if(!condition.isEmpty()) {
			condition = "("+condition+")";
		}
		ArrayList allConditions = new ArrayList();
		for(Object list:otherActivesList) {
			if(list.equals("male") || list.equals("female")) {
				if(!condition2.isEmpty()) {
					condition2 += " or ";
				}
				condition2 += "gender = '" + list +"'";
				  
			}
		    if (list.equals("1")) {
		    	 if (!condition3.isEmpty()) {
				        condition3 += " or ";
				    }
		        condition3 += "price between 10000 and 20000";
		       
		    } else if (list.equals("2")) {
		    	 if (!condition3.isEmpty()) {
				        condition3 += " or ";
				    }
		    	condition3 +="price between 20000 and 40000";
		    
		    } else if(list.equals("3")){
		    	 if (!condition3.isEmpty()) {
				        condition3 += " or ";
				    }
		    	condition3 += "price >= 40000";
		    	
		    }
		    if(list.equals("sale")) {
				 if (!condition4.isEmpty()) {
				        condition4 += " or ";
				    }
				condition4 += "sale = '" + list + "'";
			}
		    if(list.equals("orange") || list.equals("blue") || list.equals("yellow") || list.equals("green") || list.equals("red") || list.equals("purple")) {
				if(!condition5.isEmpty()) {
					condition5 += " or ";
				}
					condition5 += "orColour"+ " = '" +list+"' or bColour = '"+list+"' or yColour = '"+list+"'";
				}
		}
		if(!condition2.isEmpty()) {
			allConditions.add(condition2);
		}
		if(!condition3.isEmpty()) {
			 allConditions.add(condition3);
		}
		if(!condition4.isEmpty()) {
			 allConditions.add(condition4);
		}
		if(!condition5.isEmpty()) {
			 allConditions.add(condition5);
		}
		String otherCondition = "";
		for(Object query : allConditions) {
			if(!otherCondition.isEmpty()) {
				otherCondition += " and ";
			}
			otherCondition += "("+query+")";
			System.out.println("sdf" + otherCondition );
		}
		if(!otherCondition.isEmpty()) {
			condition += " and " + otherCondition ;
		}
		
		if(sortActive.equals("Newest")) {
			if(!condition.isEmpty()) {
				condition += " and " ;
			}
			condition  += "( newest = newest )";
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortActive.equals("Featured")) {
			
		}else if(sortActive.equals("Price: High-Low")) {
			condition  += " order by price desc ";
			otherCondition  += " order by price desc ";
		}else if(sortActive.equals("Price: Low-High")) {
			condition  += " order by price ";
			otherCondition  += " order by price ";
		}
		
		if(btnList.isEmpty()) {
			if(!otherActivesList.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
			}else {
				if(!condition.isEmpty()) {
					if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  condition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ condition ;
					}
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			}
		}else{
			if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
				jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+  condition;
			}else {
				jpql = "select c "
	            + " from PopularProductsAddPOJO c"
				+ " where "+ condition ;
			}
		}
		System.out.println("rohan " + condition);
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice(ArrayList btnList,ArrayList otherActivesList,String sortActive) {
		openConnection();
		transaction.begin();
		String condition ="";
		String condition2 ="";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		for(Object list:btnList) {
			if(list.equals("1")) {
				if(!condition.isEmpty()) {
					condition += " or ";
				}
				condition += "price between 10000 and 20000";
			}else if(list.equals("2")) {
				if(!condition.isEmpty()) {
					condition += " or ";
				}
				condition  += "price between 20000 and 40000";
			}else {
				if(!condition.isEmpty()) {
					condition += " or ";
				}
				condition += "price >= 40000";
			}
		}
		
		if(!condition.isEmpty()) {
			condition = "("+condition+")";
		}
		
		
		
		ArrayList allConditions = new ArrayList();
		for(Object list:otherActivesList) {
			if(list.equals("male") || list.equals("female")) {
				if(!condition2.isEmpty()) {
					condition2 += " or ";
				}
				condition2 += "gender = '" + list +"'";
			}
			if(list.equals("sale")) {
				 if (!condition3.isEmpty()) {
				        condition3 += " or ";
				    }
				condition3 += "sale = '" + list + "'";
			}
			if(list.equals("7") || list.equals("8") || list.equals("9") || list.equals("10")) {
				 if (!condition4.isEmpty()) {
				        condition4 += " and ";
				    }
				 condition4 += "size"+list+ " = " + list;
			}
			if(list.equals("orange") || list.equals("blue") || list.equals("yellow") || list.equals("green") || list.equals("red") || list.equals("purple")) {
				if(!condition5.isEmpty()) {
					condition5 += " or ";
				}
					condition5 += "orColour"+ " = '" +list+"' or bColour = '"+list+"' or yColour = '"+list+"'";
				}
		}
		if(!condition2.isEmpty()) {
			allConditions.add(condition2);
		}
		if(!condition3.isEmpty()) {
			 allConditions.add(condition3);
		}
		if(!condition4.isEmpty()) {
			 allConditions.add(condition4);
		}
		if(!condition5.isEmpty()) {
			 allConditions.add(condition5);
		}
		String otherCondition = "";
		for(Object query : allConditions) {
			if(!otherCondition.isEmpty()) {
				otherCondition += " and ";
			}
			otherCondition += "("+query+")";
		}
		if(!otherCondition.isEmpty()) {
			condition += " and " + otherCondition ;
			System.out.println("got it1 " + condition);
		}
		
		
		
		if(sortActive.equals("Newest")) {
			if(!condition.isEmpty()) {
				condition += " and " ;
			}
			condition  += "( newest = newest )";
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortActive.equals("Featured")) {
			
		}else if(sortActive.equals("Price: High-Low")) {
			condition  += " order by price desc ";
			otherCondition  += " order by price desc ";
		}else if(sortActive.equals("Price: Low-High")) {
			condition  += " order by price ";
			otherCondition  += " order by price ";
		}
			
			
			System.out.println("got it " + condition);
			System.out.println("got it 2 " + otherCondition);

		if(btnList.isEmpty()) {
			if(!otherActivesList.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
			}else {
				if(!condition.isEmpty()) {
					if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  condition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ condition ;
					}
				}else {
			jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			}
		}else{
			if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
				jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+  condition;
			}else {
				jpql = "select c "
	            + " from PopularProductsAddPOJO c"
				+ " where "+ condition ;
			}
		}
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsColor(ArrayList btnList, ArrayList otherActivesList,String sortActive) {
		openConnection();
		transaction.begin();
		String condition ="";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		for(Object list:btnList) {
			if(!condition.isEmpty()) {
				condition += " or ";
			}
		 condition += "orColour"+ " = '" +list+"' or bColour = '"+list+"' or yColour = '"+list+"'";
		}
		if(!condition.isEmpty()) {
			condition = "("+condition+")";
		}
		ArrayList allConditions = new ArrayList();
		for(Object list:otherActivesList) {
			if(list.equals("male") || list.equals("female")) {
				if(!condition2.isEmpty()) {
					condition2 += " or ";
				}
				condition2 += "gender = '" + list +"'";	  
			}
			if(list.equals("1")) {
				if(!condition3.isEmpty()) {
					condition3 += " or ";
				}
				condition3 += "price  between 10000 and 20000" ;	
			}else if(list.equals("2")) {
				if(!condition3.isEmpty()) {
					condition3 += " or ";
				}
				condition3 += "price  between 20000 and 40000";
			}else if(list.equals("3")) {
				if(!condition3.isEmpty()) {
					condition3 += " or ";
				}
				condition3 +="price  >= 40000";
			}
			if(list.equals("sale")) {
				 if (!condition4.isEmpty()) {
				        condition4 += " or ";
				    }
				condition4 += "sale = '" + list + "'";
			}
			if(list.equals("7") || list.equals("8") || list.equals("9") || list.equals("10")) {
				 if (!condition5.isEmpty()) {
				        condition5 += " and ";
				    }
				 condition5 += "size"+list+ " = " + list;
			}
		
		}
		if(!condition2.isEmpty()) {
			allConditions.add(condition2);
		}
		if(!condition3.isEmpty()) {
			 allConditions.add(condition3);
		}
		if(!condition4.isEmpty()) {
			 allConditions.add(condition4);
		}
		if(!condition5.isEmpty()) {
			 allConditions.add(condition5);
		}
		String otherCondition = "";
		for(Object query : allConditions) {
			if(!otherCondition.isEmpty()) {
				otherCondition += " and ";
			}
			otherCondition += "("+query+")";
			System.out.println("sdf" + otherCondition );
		}
		if(!otherCondition.isEmpty()) {
			condition += " and " + otherCondition ;
		}
		System.out.println(condition);

		if(sortActive.equals("Newest")) {
			if(!condition.isEmpty()) {
				condition += " and " ;
			}
			condition  += "( newest = newest )";
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortActive.equals("Featured")) {
			
		}else if(sortActive.equals("Price: High-Low")) {
			condition  += " order by price desc ";
			otherCondition  += " order by price desc ";
		}else if(sortActive.equals("Price: Low-High")) {
			condition  += " order by price ";
			otherCondition  += " order by price ";
		}
		System.out.println("got it " + condition);
		System.out.println("got it 2 " + otherCondition);
		if(btnList.isEmpty()) {
			if(!otherActivesList.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
			}else {
				if(!condition.isEmpty()) {
					if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  condition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ condition ;
					}
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			}
		}else{
			if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
				jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+  condition;
			}else {
				jpql = "select c "
	            + " from PopularProductsAddPOJO c"
				+ " where "+ condition ;
			}
		}

		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
	
		return products;	
	}

	public List<PopularProductsAddPOJO> viewPopularProductsbyGender(ArrayList btnList,ArrayList otherActivesList, String sortActive) {
		openConnection();
		transaction.begin();
		String condition = "";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		for(Object list : btnList) {
			if(!condition.isEmpty()) {
				condition +=" or ";
			}
			condition += "gender = '"+list+"'";
		}
		if(!condition.isEmpty()) {
			condition = "("+condition+")";
		}
		ArrayList allConditions = new ArrayList();
		for(Object list:otherActivesList) {
		if(list.equals("1")) {
			if(!condition2.isEmpty()) {
				condition2 += " or ";
			}
			condition2 += "price  between 10000 and 20000" ;	
		}else if(list.equals("2")) {
			if(!condition2.isEmpty()) {
				condition2 += " or ";
			}
			condition2 += "price  between 20000 and 40000";
		}else if(list.equals("3")) {
			if(!condition2.isEmpty()) {
				condition2 += " or ";
			}
			condition2 +="price  >= 40000";
		}
		if(list.equals("sale")) {
			 if (!condition3.isEmpty()) {
			        condition3 += " or ";
			    }
			condition3 += "sale = '" + list + "'";
		}
		if(list.equals("7") || list.equals("8") || list.equals("9") || list.equals("10")) {
			 if (!condition4.isEmpty()) {
			        condition4 += " and ";
			    }
			 condition4 += "size"+list+ " = " + list;
		}
		if(list.equals("orange") || list.equals("blue") || list.equals("yellow") || list.equals("green") || list.equals("red") || list.equals("purple")) {
		if(!condition5.isEmpty()) {
			condition5 += " or ";
		}
			condition5 += "orColour"+ " = '" +list+"' or bColour = '"+list+"' or yColour = '"+list+"'";
		}
		}
		if(!condition2.isEmpty()) {
			allConditions.add(condition2);
		}
		if(!condition3.isEmpty()) {
			 allConditions.add(condition3);
		}
		if(!condition4.isEmpty()) {
			 allConditions.add(condition4);
		}
		if(!condition5.isEmpty()) {
			 allConditions.add(condition5);
		}
		String otherCondition = "";
		for(Object query : allConditions) {
			if(!otherCondition.isEmpty()) {
				otherCondition += " and ";
			}
			otherCondition += "("+query+")";
			System.out.println("sdf" + otherCondition );
		}
		if(!otherCondition.isEmpty()) {
			condition += " and " + otherCondition ;
		}
		
		System.out.println("sote "+condition);

		if(sortActive.equals("Newest")) {
			if(!condition.isEmpty()) {
				condition += " and " ;
			}
			condition  += "( newest = newest )";
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortActive.equals("Featured")) {
			
		}else if(sortActive.equals("Price: High-Low")) {
			condition  += " order by price desc ";
			otherCondition  += " order by price desc ";
		}else if(sortActive.equals("Price: Low-High")) {
			condition  += " order by price ";
			otherCondition  += " order by price ";
		}
		
		if(btnList.isEmpty()) {
			if(!otherActivesList.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
			}else {
				if(!condition.isEmpty()) {
					if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  condition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ condition ;
					}
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			}
		}else{
			if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
				jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+  condition;
			}else {
				jpql = "select c "
	            + " from PopularProductsAddPOJO c"
				+ " where "+ condition ;
			}
		}
//	
		
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> saleFillter(ArrayList saleValues, ArrayList otherValues,String sortActive) {
		openConnection();
		transaction.begin();
		String condition = "";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		for(Object saleBtnsValues : saleValues)  {
			if(!condition.isEmpty()) {
				condition += " or ";
			}
			condition += "sale = '"+ saleBtnsValues + "'";
		}
		if(!condition.isEmpty()) {
			condition = "("+condition+")";
		}
		ArrayList allConditions = new ArrayList();
		for(Object otherBtns:otherValues) {
			if(otherBtns.equals("male") || otherBtns.equals("female")) {
				if(!condition2.isEmpty()) {
					condition2 += " or ";
				}
				condition2 += "gender = '" + otherBtns +"'";
				  
			}
			
			    if (otherBtns.equals("1")) {
			    	 if (!condition3.isEmpty()) {
					        condition3 += " or ";
					    }
			        condition3 += "price between 10000 and 20000";
			       
			    } else if (otherBtns.equals("2")) {
			    	 if (!condition3.isEmpty()) {
					        condition3 += " or ";
					    }
			    	condition3 +="price between 20000 and 40000";
			    
			    } else if(otherBtns.equals("3")){
			    	 if (!condition3.isEmpty()) {
					        condition3 += " or ";
					    }
			    	condition3 += "price >= 40000";
			    	
			    }
			    if(otherBtns.equals("7") || otherBtns.equals("8") || otherBtns.equals("9") || otherBtns.equals("10")) {
					 if (!condition4.isEmpty()) {
					        condition4 += " and ";
					    }
					 condition4 += "size"+otherBtns+ " = " + otherBtns;
				}
			    if(otherBtns.equals("orange") || otherBtns.equals("blue") || otherBtns.equals("yellow") || otherBtns.equals("green") || otherBtns.equals("red") || otherBtns.equals("purple")) {
					if(!condition5.isEmpty()) {
						condition5 += " or ";
					}
						condition5 += "orColour"+ " = '" +otherBtns+"' or bColour = '"+otherBtns+"' or yColour = '"+otherBtns+"'";
					}
			}
			if(!condition2.isEmpty()) {
				allConditions.add(condition2);
			}
			if(!condition3.isEmpty()) {
				 allConditions.add(condition3);
			}
			if(!condition4.isEmpty()) {
				 allConditions.add(condition4);
			}
			if(!condition5.isEmpty()) {
				 allConditions.add(condition5);
			}
			String otherCondition = "";
			for(Object query : allConditions) {
				if(!otherCondition.isEmpty()) {
					otherCondition += " and ";
				}
				otherCondition += "("+query+")";
				System.out.println("sdf" + otherCondition );
			}
		
			System.out.println("array" + allConditions );
		System.out.println("Conditions " + condition + " " + condition2 + " " + condition3);
		System.out.println("sdf" + otherCondition );
		if(!otherCondition.isEmpty()) {
			condition += " and " + otherCondition ;
			}
		if(sortActive.equals("Newest")) {
			if(!condition.isEmpty()) {
				condition += " and " ;
			}
			condition  += "( newest = newest )";
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortActive.equals("Featured")) {
			
		}else if(sortActive.equals("Price: High-Low")) {
			condition  += " order by price desc ";
			otherCondition  += " order by price desc ";
		}else if(sortActive.equals("Price: Low-High")) {
			condition  += " order by price ";
			otherCondition  += " order by price ";
		}
		
		System.out.println("sdf" + condition );
		if(saleValues.isEmpty()) {
			if(!otherValues.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
			}else {
				if(!condition.isEmpty()) {
					if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  condition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ condition ;
					}
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			}
		}else{
			if(condition.equals(" order by price desc ") || condition.equals(" order by price ")) {
				jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+  condition;
			}else {
				jpql = "select c "
	            + " from PopularProductsAddPOJO c"
				+ " where "+ condition ;
			}
		}
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<CartPOJO> AllcartItem() {
		openConnection();
		transaction.begin();
		jpql = "from CartPOJO";
		query = manager.createQuery(jpql);
		List<CartPOJO> AllcartItems = query.getResultList();
		transaction.commit();
		closeConnection();
		return AllcartItems;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsByFillter(ArrayList otherValues) {
		openConnection();
		transaction.begin();
		String condition1 = "";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		
		
		ArrayList allConditions = new ArrayList();
		for(Object otherBtns:otherValues) {
			if(otherBtns.equals("male") || otherBtns.equals("female")) {
				if(!condition1.isEmpty()) {
					condition1 += " or ";
				}
				condition1 += "gender = '" + otherBtns +"'";
				  
			}
			
			    if (otherBtns.equals("1")) {
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			        condition2 += "price between 10000 and 20000";
			       
			    } else if (otherBtns.equals("2")) {
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			    	condition2 +="price between 20000 and 40000";
			    
			    } else if(otherBtns.equals("3")){
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			    	condition2 += "price >= 40000";
			    	
			    }
			    if(otherBtns.equals("sale")) {
					 if (!condition3.isEmpty()) {
					        condition3 += " or ";
					    }
					condition3 += "sale = '" + otherBtns + "'";
				}
			    if(otherBtns.equals("7") || otherBtns.equals("8") || otherBtns.equals("9") || otherBtns.equals("10")) {
					 if (!condition4.isEmpty()) {
					        condition4 += " and ";
					    }
					 condition4 += "size"+otherBtns+ " = " + otherBtns;
				}
			    if(otherBtns.equals("orange") || otherBtns.equals("blue") || otherBtns.equals("yellow") || otherBtns.equals("green") || otherBtns.equals("red") || otherBtns.equals("purple")) {
					if(!condition5.isEmpty()) {
						condition5 += " or ";
					}
						condition5 += "orColour"+ " = '" +otherBtns+"' or bColour = '"+otherBtns+"' or yColour = '"+otherBtns+"'";
					}
			}
			if(!condition1.isEmpty()) {
				allConditions.add(condition1);
			}
			if(!condition2.isEmpty()) {
				 allConditions.add(condition2);
			}
			if(!condition3.isEmpty()) {
				 allConditions.add(condition3);
			}
			if(!condition4.isEmpty()) {
				 allConditions.add(condition4);
			}
			if(!condition5.isEmpty()) {
				 allConditions.add(condition5);
			}
			String otherCondition = "";
			for(Object query : allConditions) {
				if(!otherCondition.isEmpty()) {
					otherCondition += " and ";
				}
				otherCondition += "("+query+")";
				System.out.println("sdf" + otherCondition );
			}
		
			System.out.println("array" + allConditions );
		
		System.out.println("sdf" + otherCondition );
	
		
			if(!otherValues.isEmpty()) {
				jpql = "Select c "
						+ "from PopularProductsAddPOJO c "
						+ " Where "+ otherCondition;
				System.out.println("where " + otherCondition);
			}else {
			jpql =  "Select c "
					+ "from PopularProductsAddPOJO c ";
			}
		
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
		
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSort(String sortBy) {
		openConnection();
		transaction.begin();
		
	
		if(sortBy.equals("Newest")) {
			jpql =  "Select c "
					+ "from PopularProductsAddPOJO c "
					+ "where newest = newest";	
		
			
		}else if(sortBy.equals("Featured")) {
			jpql =  "Select c "
					+ "from PopularProductsAddPOJO c ";	
			
		}else if(sortBy.equals("Price: High-Low")) {
			jpql =  "Select c "
					+ "from PopularProductsAddPOJO c "
					+ "order by price desc";	
			
			
		}else if(sortBy.equals("Price: Low-High")) {
			jpql =  "Select c "
					+ "from PopularProductsAddPOJO c "
					+ "order by price ";	
			
			
		}
		
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO>   products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsByFillter(ArrayList otherValues, String sortBy) {
		openConnection();
		transaction.begin();
		String condition1 = "";
		String condition2 = "";
		String condition3 = "";
		String condition4 = "";
		String condition5 = "";
		System.out.println("Cresdd "+otherValues );
		
		ArrayList allConditions = new ArrayList();
		for(Object otherBtns:otherValues) {
			if(otherBtns.equals("male") || otherBtns.equals("female")) {
				if(!condition1.isEmpty()) {
					condition1 += " or ";
				}
				condition1 += "gender = '" + otherBtns +"'";
				  
			}
			
			    if (otherBtns.equals("1")) {
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			        condition2 += "price between 10000 and 20000";
			       
			    } else if (otherBtns.equals("2")) {
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			    	condition2 +="price between 20000 and 40000";
			    
			    } else if(otherBtns.equals("3")){
			    	 if (!condition2.isEmpty()) {
					        condition2 += " or ";
					    }
			    	condition2 += "price >= 40000";
			    	
			    }
			    if(otherBtns.equals("sale")) {
					 if (!condition3.isEmpty()) {
					        condition3 += " or ";
					    }
					condition3 += "sale = '" + otherBtns + "'";
				}
			    if(otherBtns.equals("7") || otherBtns.equals("8") || otherBtns.equals("9") || otherBtns.equals("10")) {
					 if (!condition4.isEmpty()) {
					        condition4 += " and ";
					    }
					 condition4 += "size"+otherBtns+ " = " + otherBtns;
				}
			    if(otherBtns.equals("orange") || otherBtns.equals("blue") || otherBtns.equals("yellow") || otherBtns.equals("green") || otherBtns.equals("red") || otherBtns.equals("purple")) {
					if(!condition5.isEmpty()) {
						condition5 += " or ";
					}
						condition5 += "orColour"+ " = '" +otherBtns+"' or bColour = '"+otherBtns+"' or yColour = '"+otherBtns+"'";
					}
			}
			if(!condition1.isEmpty()) {
				allConditions.add(condition1);
			}
			if(!condition2.isEmpty()) {
				 allConditions.add(condition2);
			}
			if(!condition3.isEmpty()) {
				 allConditions.add(condition3);
			}
			if(!condition4.isEmpty()) {
				 allConditions.add(condition4);
			}
			if(!condition5.isEmpty()) {
				 allConditions.add(condition5);
			}
			String otherCondition = "";
			for(Object query : allConditions) {
				if(!otherCondition.isEmpty()) {
					otherCondition += " and ";
				}
				otherCondition += "("+query+")";
				System.out.println("sdf" + otherCondition );
			}
		
			System.out.println("array" + allConditions );
		
		System.out.println("sdf" + otherCondition );
		if(sortBy.equals("Newest")) {
			
			if(!otherCondition.isEmpty()) {
				otherCondition += " and " ;
			}
			otherCondition  += "( newest = newest )";
		}else if(sortBy.equals("Featured")) {
			
		}else if(sortBy.equals("Price: High-Low")) {
			
			otherCondition  += " order by price desc ";
		}else if(sortBy.equals("Price: Low-High")) {
			
			otherCondition  += " order by price ";
		}
		
			if(!otherValues.isEmpty()) {
				if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
					jpql = "select c "
				            + " from PopularProductsAddPOJO c"
							+  otherCondition;
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c"
					+ " where "+ otherCondition ;
				}
				System.out.println("where " + otherCondition);
			}else {
				if(!otherCondition.isEmpty()) {
					if(otherCondition.equals(" order by price desc ") || otherCondition.equals(" order by price ")) {
						jpql = "select c "
					            + " from PopularProductsAddPOJO c"
								+  otherCondition;
					}else {
						jpql = "select c "
			            + " from PopularProductsAddPOJO c"
						+ " where "+ otherCondition ;
					}
				}else {
					jpql = "select c "
		            + " from PopularProductsAddPOJO c";	
				}
			
			}
		System.out.println("ORhan sdjfhn MOBILE " + jpql);
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList();
		transaction.commit();
		closeConnection();
		return products;
	}

	public List<WishlistPOJO> searchWishlistByProd(int productId, int customerId) {
		openConnection();
		transaction.begin();
		jpql = "select w from WishlistPOJO w "+ 
			   " where w.customer.id = " + customerId +
			   " and w.products.id = " + productId ;
		query = manager.createQuery(jpql);
		List<WishlistPOJO> wishlistProd =  query.getResultList();
		transaction.commit();
		closeConnection();
		return wishlistProd;
	
	}

	public WishlistPOJO searchWishlistItemColor(int id, int customerId, String color,int size) {
		openConnection();
		transaction.begin();
		jpql = "select w from WishlistPOJO w "+ 
			   " where w.products.id = " + id +
			   " and w.customer.id = " + customerId +
			   " and w.color = '" + color + "'" + 
			   " and w.size = " + size ;
		System.out.println("rohab " + jpql);
		query = manager.createQuery(jpql);
		List<WishlistPOJO> searchWishlist =  query.getResultList();
		System.out.println("rohab " + searchWishlist);
		for(WishlistPOJO searchWishlistColor : searchWishlist) {
			transaction.commit();
			closeConnection();
			return searchWishlistColor;
		}
		transaction.commit();
		closeConnection();
		return null;
	}

	public WishlistPOJO searchWishlistSelectSize(int productId, int customerId, String color) {
		openConnection();
		transaction.begin();
		jpql = "select w from WishlistPOJO w "+ 
				   " where w.products.id = " + productId +
				   " and w.customer.id = " + customerId +
				   " and w.color = '" + color + "'" + 
				   " and w.size =  0";
		query = manager.createQuery(jpql);
		List<WishlistPOJO> wishlistProd =  query.getResultList();
		for(WishlistPOJO product : wishlistProd) {
			transaction.commit();
			closeConnection();
			return product;
		}
		transaction.commit();
		closeConnection();
		return null;
	}

	public WishlistPOJO wishNotAdded(int id, boolean b,int size) {
		openConnection();
		transaction.begin();
		WishlistPOJO wishNotAdded = manager.find(WishlistPOJO.class, id);
		wishNotAdded.setSize(size);
		wishNotAdded.setAddedToCart(b);
		manager.persist(wishNotAdded);
		transaction.commit();
		closeConnection();
		return wishNotAdded;
	}

	public CartPOJO updateCartItemsBySizeQuantity(int[] ids, int[] size, int[] bagQuantity) {
		openConnection();
		transaction.begin();
		for(int i = 0;i<ids.length;i++) {
		CartPOJO updatedCartItems = manager.find(CartPOJO.class, ids[i]);
		updatedCartItems.setSize(size[i]);
		updatedCartItems.setQuantity(bagQuantity[i]);
		manager.persist(updatedCartItems);
		}
		transaction.commit();
		closeConnection();
		return null;
	}

	public CartPOJO getProductByCartID(Integer id) {
		openConnection();
		transaction.begin();
		CartPOJO getProductById = manager.find(CartPOJO.class, id);
		transaction.commit();
		closeConnection();
		return getProductById;
	}

	public CartPOJO updateCartItemsBySizeQuantity(int ids, int size, int bagQuantity) {
		openConnection();
		transaction.begin();
		CartPOJO updatedCartItems = manager.find(CartPOJO.class, ids);
		updatedCartItems.setSize(size);
		updatedCartItems.setQuantity(bagQuantity);
		manager.persist(updatedCartItems);
		transaction.commit();
		closeConnection();
		return updatedCartItems;
	}

	public CartPOJO searchCartItemPresent(int ids, int size, int bagQuantity) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<CartPOJO> SearchCartItemsDupli(int customerId, int id, int size, String color) {
		openConnection();
		transaction.begin();
		jpql = "select c from CartPOJO c "+ 
				   " where c.products.id = " + id +
				   " and c.customer.id = " + customerId +
				   " and c.color = '" + color + "'" + 
				   " and c.size = " + size;
		query = manager.createQuery(jpql);
		List<CartPOJO> searchCartItemPresent =  query.getResultList();
			transaction.commit();
			closeConnection();
			return searchCartItemPresent;
		
		
	}

	public List<PopularProductsAddPOJO> getHomePageCards() {
		openConnection();
		transaction.begin();
		jpql =  "Select c "
				+ "from PopularProductsAddPOJO c "
				+ "where newest = newest";	
		query = manager.createQuery(jpql);
		List<PopularProductsAddPOJO> products = query.getResultList(); 
			transaction.commit();
			closeConnection();
			return products;
		
	}

	public Long cartTotalQuantity(int customerId) {
		openConnection();
		transaction.begin();
		jpql = "select sum(quantity) from CartPOJO where customer.id = " + customerId;
		query = manager.createQuery(jpql);
		List total = query.getResultList();
		System.out.println(total);
		for(Object totalQuantity : total ) {
			transaction.commit();
			closeConnection();
			return  (Long) totalQuantity;
		}
		transaction.commit();
		closeConnection();
		return (long) 0;

	}

	public WishlistPOJO searchWishlistItemNotInCart(int productId, int customerId, int size, String color) {
		openConnection();
		transaction.begin();
		jpql = "select w from WishlistPOJO w "+ 
			   " where w.customer.id = " + customerId +
			   " and w.products.id = " + productId + 
			   " and w.size = " + size +
			   " and w.color =  '" + color +"'"+
			   " and w.addedToCart = 0 ";
			   
		query = manager.createQuery(jpql);
		List<WishlistPOJO> searchWishlist =  query.getResultList();
		for(WishlistPOJO searchWishlistItem : searchWishlist) {
			transaction.commit();
			closeConnection();
			return searchWishlistItem;
		}
		transaction.commit();
		closeConnection();
		return null;
	}

	public OrdersPOJO createOrder(String firstName, String lastName, String email, long phone, String address,
			long postalcode, String locality, String state, String country, long amount,String date, String time, String orderId,  List<CartAdminPOJO> cartItemsAdmin,
			CustomerPOJO login) {
		openConnection();
		transaction.begin();
		OrdersPOJO orders = new OrdersPOJO();
		orders.setFirstName(firstName);
		orders.setLastName(lastName);
		orders.setEmail(email);
		orders.setPhone(phone);
		orders.setAddress(address);
		orders.setPostalCode(postalcode);
		orders.setLocality(locality);
		orders.setState(state);
		orders.setCountry(country);
		orders.setAmount(amount);
		orders.setDate(date);
		orders.setTime(time);
		orders.setOrderId(orderId);
		orders.setCart(cartItemsAdmin);
		orders.setCustomer(login);
		manager.persist(orders);
		transaction.commit();
		closeConnection();
		return orders;
	}

	public List<OrdersPOJO> gett() {
		openConnection();
		transaction.begin();
		jpql = " from OrdersPOJO";
		query = manager.createQuery(jpql);
		List<OrdersPOJO> OrdersPOJO = query.getResultList();
		transaction.commit();
		closeConnection();
		return OrdersPOJO;
	}



	public CartAdminPOJO storeCartTOAdmin(CartPOJO cartItems) {
		openConnection();
		transaction.begin();

			CartAdminPOJO CartAdmin = new CartAdminPOJO();
			CartAdmin.setProducts(cartItems.getProducts());
			CartAdmin.setCustomer(cartItems.getCustomer());
			CartAdmin.setQuantity(cartItems.getQuantity());
			CartAdmin.setSize(cartItems.getSize());
			CartAdmin.setColor(cartItems.getColor());
			CartAdmin.setImage(cartItems.getImage());
			CartAdmin.setCartId(cartItems.getId());
			manager.persist(CartAdmin);

		transaction.commit();
		closeConnection();
		return null;
	}

	public List<CartAdminPOJO> getCartItemsAdmin(List<CartPOJO> cartItems) {
		openConnection();
		transaction.begin();
	    List<CartAdminPOJO> cartItemsAdmin = new ArrayList();
		for(CartPOJO items : cartItems ) {
			jpql =  "Select c "
					+ "from CartAdminPOJO c "
					+ "where cartId = " + items.getId();
			query = manager.createQuery(jpql);
			List<CartAdminPOJO> cart = query.getResultList(); 
			cartItemsAdmin.addAll(cart);
		}
		System.out.println("rohan " + cartItemsAdmin);
			transaction.commit();
			closeConnection();
			return cartItemsAdmin;
	}

	public CartAdminPOJO searchStoreCartPresent(CartPOJO cartItems) {
		openConnection();
		transaction.begin();

			jpql =  "Select c "
					+ "from CartAdminPOJO c "
					+ "where cartId = " + cartItems.getId();
			query = manager.createQuery(jpql);
			List<CartAdminPOJO> cart = query.getResultList();
			for(CartAdminPOJO cartItemsAdmin : cart) {
				transaction.commit();
				closeConnection();
				return cartItemsAdmin;
			}
			transaction.commit();
			closeConnection();
			return null;
		
	}

	public OrdersPOJO getOrder(String order_id) {
		openConnection();
		transaction.begin();
		jpql = "Select c from OrdersPOJO c where orderId = '" + order_id + "'";
			query = manager.createQuery(jpql);
			System.out.println(jpql);
			List<OrdersPOJO> userOrder = query.getResultList();
			for(OrdersPOJO order : userOrder) {
				transaction.commit();
				closeConnection();
				return order;
			}
			transaction.commit();
			closeConnection();
			return null;
	}

	

	public void updatePaymentdetails(String payment_id, int id) {
		openConnection();
		transaction.begin();
		OrdersPOJO order = manager.find(OrdersPOJO.class, id);
		order.setPaymentId(payment_id);
		manager.persist(order);
		transaction.commit();
		closeConnection();
		
	}

	public List<OrdersPOJO> getPaidOrder(int id) {
		openConnection();
		transaction.begin();
		jpql = "Select c from OrdersPOJO c where c.customer.id = " + id + " and paymentId is not null"  ;
			query = manager.createQuery(jpql);
			System.out.println(jpql);
			List<OrdersPOJO> userOrder = query.getResultList();
		if(!userOrder.isEmpty()) {
				transaction.commit();
				closeConnection();
				return userOrder;
			}	
			transaction.commit();
			closeConnection();
			return null;
	}

	public CartPOJO searchCartItemById(int cartId) {
		openConnection();
		transaction.begin();
		CartPOJO cartItem = manager.find(CartPOJO.class, cartId);
		transaction.commit();
		closeConnection();
		return cartItem;
	}

	public List<OrdersPOJO> allOrders() {
		openConnection();
		transaction.begin();
		jpql = "from OrdersPOJO";
		query = manager.createQuery(jpql);
		List<OrdersPOJO> allOrders = query.getResultList();
		transaction.commit();
		closeConnection();
		return allOrders;
	}

	public OrdersPOJO order(int orderId) {
		openConnection();
		transaction.begin();
		OrdersPOJO order = manager.find(OrdersPOJO.class, orderId);
		transaction.commit();
		closeConnection();
		return order;
	}

	public Long totalAmount() {
		openConnection();
		transaction.begin();
		jpql = "select sum(amount) from OrdersPOJO where paymentId is not null";
		query = manager.createQuery(jpql);
		List totalAmount = query.getResultList();
		for(Object amount : totalAmount) {
			transaction.commit();
			closeConnection();
			return (Long) amount;
			
		}
		transaction.commit();
		closeConnection();
		return (long) 0;
	}

	public double totalBudget() {
		openConnection();
		transaction.begin();
		jpql = "select sum(price * quantity) from PopularProductsAddPOJO ";
		query = manager.createQuery(jpql);
		List totalBudget = query.getResultList();
		for(Object budget : totalBudget) {
			transaction.commit();
			closeConnection();
			return  (double) budget;
			
		}
		transaction.commit();
		closeConnection();
		return  0;
	}



	


	

	


}
