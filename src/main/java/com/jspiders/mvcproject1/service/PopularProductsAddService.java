package com.jspiders.mvcproject1.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jspiders.mvcproject1.controller.PopularProductsAdd;
import com.jspiders.mvcproject1.pojo.CartAdminPOJO;
import com.jspiders.mvcproject1.pojo.CartPOJO;

import com.jspiders.mvcproject1.pojo.CustomerPOJO;
import com.jspiders.mvcproject1.pojo.OrdersPOJO;
import com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO;
import com.jspiders.mvcproject1.pojo.WishlistPOJO;
import com.jspiders.mvcproject1.repository.PopularProductsAddRepository;

@Service
public class PopularProductsAddService {
	@Autowired
	private PopularProductsAddRepository repository;

	public List<PopularProductsAddPOJO> viewPopularProducts() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProducts();
		return products;
	}
	
	public int cartTotal(int customerId) {
		int totalQuantity = repository.cartTotal(customerId);
		return totalQuantity;
	}
	
	public List<Object[]> viewCartItems(int customerId) {
		List<Object[]> cartItems = repository.viewCartItems(customerId);
		return cartItems;
	}
	
	public CartPOJO SearchCartItems2(int customerId, int id,int size, String color) {
		CartPOJO cart = repository.SearchCartItems2(customerId,id,size,color);
		return cart;
	}

	public PopularProductsAddPOJO addPopularProduct(String type, String name, String info, String brand, int sSize,
			int mSize, int lSize, int xSize, String orColour, String bColour, String yColour, double price,
			int quantity,String gender,String sale,String newest, String image,String image2,String image3) {
		PopularProductsAddPOJO product = repository.addPopularProduct(type, name, info, brand, sSize, mSize, lSize,
				xSize, orColour, bColour, yColour, price, quantity,gender,sale,newest, image,image2,image3);
		return product;
	}



	

	public PopularProductsAddPOJO searchProduct(int id) {
		PopularProductsAddPOJO product = repository.searchProduct(id);
		return product;
	}

	public CustomerPOJO searchCustomer(int id) {
		CustomerPOJO customer = repository.searchCustomer(id);
		return customer;
	}

	public CartPOJO addItems(PopularProductsAddPOJO product, CustomerPOJO customer, int quantity,int size, String color, String image) {
		CartPOJO cart = repository.addItems(product,customer,quantity,size,color,image);
		return cart;
	}

	

	public CartPOJO SearchCartItems(int customerId,int productsId,int size,String color) {
		CartPOJO cart = repository.SearchCartItems(customerId,productsId,size,color);
		return cart;
	}


	public CartPOJO updateItem(int quantity, int id) {
		
		CartPOJO item = repository.updateItem(quantity,id);
		return item;
	}

	public  List<Long> proQuantity() {
		 List<Long> pQuantity = repository.proQuantity();
		return pQuantity;
	}

	public CartPOJO prodMax(int customerId, int id) {
		CartPOJO prodMax = repository.prodMax(customerId,id);
		return prodMax;
	}

	public CartPOJO removeItems(int id) {
		CartPOJO removeItems = repository.removeItems(id);
		return removeItems;
	}

	public void getCartItemId(int id) {
		repository.getCartItemId(id);
		
	}



	public WishlistPOJO addWishlist(CustomerPOJO customer,PopularProductsAddPOJO product,int size,String color,String image) {
		WishlistPOJO wishlist = repository.addWishlist(customer,product,size,color,image);
		return wishlist;
	}

	public List<WishlistPOJO> searchWishlist(int customerId) {
		List<WishlistPOJO> wishlistProducts = repository.searchWishlist(customerId);
		return wishlistProducts;
	}



	public WishlistPOJO searchWishlistItem(int id, int customerId,int size) {
		WishlistPOJO searchWishlist = repository.searchWishlistItem(id,customerId,size);
		return searchWishlist;
	}

	public void removeWishlistItem(int id) {
		repository.removeWishlistItem(id);
		
	}

	public WishlistPOJO updateWishlistItem(int id, int size) {
		WishlistPOJO updateWishlist = repository.updateWishlistItem(id,size);
		return updateWishlist;
	}

	public List<CartPOJO> viewCartItemsAll(int customerId) {
		List<CartPOJO> cartItems = repository.viewCartItemsAll(customerId);
		return cartItems;

	}

	public List<PopularProductsAddPOJO> viewPopularProductsMen() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsMen();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice2() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice2();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice1to2() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice1to2();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice3() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice3();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice1to3() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice1to3();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice2to3() {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice2to3();
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String btn) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsSize(btn);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String activebtn, String btn) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsSize(activebtn,btn);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(String string, String string2, String string3) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsSize(string,string2,string3);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsSize(ArrayList btnList,ArrayList otherActivesList,String sortActive) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsSize(btnList,otherActivesList,sortActive);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsPrice(ArrayList btnList, ArrayList otherActivesList,String sortActive) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsPrice(btnList,otherActivesList,sortActive);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsColor(ArrayList btnList, ArrayList otherActivesList,String sortActive) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsColor(btnList,otherActivesList,sortActive);
		return products;
	}

	public List<PopularProductsAddPOJO> viewPopularProductsbyGender(ArrayList btnList,ArrayList otherActivesList,String sortActive) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsbyGender(btnList,otherActivesList,sortActive);
		return products;
	}

	public List<PopularProductsAddPOJO> saleFillter(ArrayList saleValues, ArrayList otherValues,String sortActive) {
		List<PopularProductsAddPOJO> products = repository.saleFillter(saleValues,otherValues,sortActive);
		return products;
	}

	public List<CartPOJO> AllcartItem() {
		List<CartPOJO> AllcartItems = repository.AllcartItem();
		return AllcartItems;
	}





	public List<PopularProductsAddPOJO> viewPopularProductsByFillter(ArrayList otherValues,String sortBy) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsByFillter(otherValues,sortBy);
		return products;
	}

	

	public List<PopularProductsAddPOJO> viewPopularProductsSort(String sortBy) {
		List<PopularProductsAddPOJO> products = repository.viewPopularProductsSort(sortBy);
		return products;
	}

	public List<WishlistPOJO> searchWishlistByProd(int productId, int customerId) {
		List<WishlistPOJO> wishlistProd = repository.searchWishlistByProd(productId, customerId); 
		return wishlistProd;
	}


	public WishlistPOJO searchWishlistItemColor(int id, int customerId, String color,int size) {
		WishlistPOJO searchWishlistColor = repository.searchWishlistItemColor(id,customerId,color,size);
		return searchWishlistColor;
	}

	public WishlistPOJO searchWishlistSelectSize(int productId, int customerId, String color) {
		WishlistPOJO product = repository.searchWishlistSelectSize(productId,customerId,color);
		return product;
	}

	public WishlistPOJO wishNotAdded(int id, boolean b,int size) {
		WishlistPOJO wishNotAdded = repository.wishNotAdded(id,false,size);
		return wishNotAdded;
	}

	public CartPOJO updateCartItemsBySizeQuantity(int[] ids, int[] size, int[] bagQuantity) {
		CartPOJO updatedCartItems = repository.updateCartItemsBySizeQuantity(ids,size,bagQuantity);
		return null;
	}

	public CartPOJO getProductByCartID(Integer id) {
		CartPOJO getProductById = repository.getProductByCartID(id);
		return getProductById;
	}

	public CartPOJO updateCartItemsBySizeQuantity(int ids,int size , int bagQuantity) {
		CartPOJO updatedCartItems =	repository.updateCartItemsBySizeQuantity(ids,size,bagQuantity);
		return updatedCartItems;
	}

	public CartPOJO searchCartItemPresent(int ids, int size, int bagQuantity ) {
		CartPOJO searchCartItemPresent = repository.searchCartItemPresent(ids, size, bagQuantity);
		return searchCartItemPresent;
	}

	public List<CartPOJO> SearchCartItemsDupli(int customerId, int id, int size, String color) {
		List<CartPOJO> searchCartItemPresent = repository.SearchCartItemsDupli(customerId,id , size, color);
		return searchCartItemPresent;
	}

	public List<PopularProductsAddPOJO> getHomePageCards() {
		List<PopularProductsAddPOJO> products = repository.getHomePageCards();
		return products;
	}

	public Long cartTotalQuantity(int customerId) {
		Long totalQuantity = repository.cartTotalQuantity(customerId);
		return totalQuantity;
	}

	public WishlistPOJO searchWishlistItemNotInCart(int productId, int customerId, int size, String color) {
		WishlistPOJO searchWishlist = repository.searchWishlistItemNotInCart(productId,customerId,size,color);
		return searchWishlist;
	}

	public OrdersPOJO createOrder(String firstName, String lastName, String email, long phone, String address,
			long postalcode, String locality, String state, String country, long amount,String date,String time, String orderId, List<CartAdminPOJO> cartItemsAdmin,
			CustomerPOJO login) {
		OrdersPOJO userOrder = repository.createOrder(firstName,lastName,email,phone,address,postalcode,locality,state,country,amount,date,time,orderId,cartItemsAdmin,login);
		return userOrder;
	}

	public List<OrdersPOJO> gett() {
		List<OrdersPOJO> orderPojo = repository.gett();
		return orderPojo;
	}



	public CartAdminPOJO storeCartTOAdmin(CartPOJO cartItems) {
		CartAdminPOJO cartItemsOrder = repository.storeCartTOAdmin(cartItems);
		return null;
	}

	public List<CartAdminPOJO> getCartItemsAdmin(List<CartPOJO> cartItems) {
		List<CartAdminPOJO> cartItemsAdmin = repository.getCartItemsAdmin(cartItems);
		return cartItemsAdmin;
	}

	public CartAdminPOJO searchStoreCartPresent(CartPOJO cartItems) {
		CartAdminPOJO cartItemsAdmin = repository.searchStoreCartPresent(cartItems);
		return cartItemsAdmin;
	}

	public OrdersPOJO getOrder(String order_id) {
		OrdersPOJO order = repository.getOrder(order_id);
		return order;
	}

	

	public void updatePaymentdetails(String payment_id, int id) {
		repository.updatePaymentdetails(payment_id,id);
		
	}

	public List<OrdersPOJO> getPaidOrder(int id) {
		List<OrdersPOJO> order = repository.getPaidOrder(id);
		return order;
	}

	public CartPOJO searchCartItemById(int cartId) {
		CartPOJO cartItem = repository.searchCartItemById(cartId);
		return cartItem;
	}

	public List<OrdersPOJO> allOrders() {
		List<OrdersPOJO> allOrders = repository.allOrders();
		return allOrders;
	}

	public OrdersPOJO order(int orderId) {
		OrdersPOJO order = repository.order(orderId);
		return order;
	}

	public Long totalAmount() {
		Long totalAmount = repository.totalAmount();
		return totalAmount;
	}

	public double totalBudget() {
		double totalBudget = repository.totalBudget();
		return totalBudget;
	}

	



	


	

}
