package com.jspiders.mvcproject1.controller;

import java.awt.print.Printable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.TransactionException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jspiders.mvcproject1.pojo.AdminPOJO;
import com.jspiders.mvcproject1.pojo.CartAdminPOJO;
import com.jspiders.mvcproject1.pojo.CartPOJO;
import com.jspiders.mvcproject1.pojo.CustomerPOJO;
import com.jspiders.mvcproject1.pojo.OrdersPOJO;
import com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO;
import com.jspiders.mvcproject1.pojo.WishlistPOJO;
import com.jspiders.mvcproject1.service.AdminService;
import com.jspiders.mvcproject1.service.CustomerService;
import com.jspiders.mvcproject1.service.PopularProductsAddService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


@Controller
public class PopularProductsAdd {

	@Autowired
	private PopularProductsAddService service;

	@Autowired
	private CustomerService customerService;
	
	@Autowired 
	private AdminService adminService;

	@GetMapping("/")
	public String HomePage(@SessionAttribute(name = "login" ,required = false) CustomerPOJO login ,ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
			map.addAttribute("size", 0);
		}
		List<PopularProductsAddPOJO> products = service.getHomePageCards();
		map.addAttribute("productList",products);
		map.addAttribute("size", 0);	
		map.addAttribute("pageId", 0);
		return "/NavBar";
	}

	

@GetMapping("/callback")
 public String googleCallback(String code,ModelMap map,HttpServletRequest request) {
		
	String accessToken = exchangeCodeForAccessToken(code);
	if (accessToken != null) {
	    JSONObject jsonResponse = fetchUserInfo(accessToken);
	    if (jsonResponse != null) {
	    	String name = jsonResponse.getString("name");
	        String email = jsonResponse.getString("email");
	        String givenName = jsonResponse.getString("given_name");
	        String familyName = jsonResponse.getString("family_name");
	        String pictureUrl = jsonResponse.getString("picture");
	  //      String locale = jsonResponse.getString("locale");
	        String googleuserId = jsonResponse.getString("sub");
	        System.out.println("User Information: " + jsonResponse);
	    	
			CustomerPOJO verifyEmail = customerService.SearchEmail(email);
			if(verifyEmail != null) {
				if(verifyEmail.getGoogleuserId() == null) {
					customerService.addGoogleuserId(verifyEmail.getId(),googleuserId);
				}
			}
			if(verifyEmail == null) {
				CustomerPOJO admin = customerService.addAdmin(givenName,familyName,email,null,googleuserId);
			}
				CustomerPOJO admin = customerService.loginbyGoogleuserId(email,googleuserId);
				if(admin != null) {
					// Success response
					HttpSession session = request.getSession();
					session.setAttribute("login", admin);
					map.addAttribute("userActive", "active");
					int totalQuantity = service.cartTotal(admin.getId());
					map.addAttribute("totalQuantity", totalQuantity);
					List<Object[]> cartItems = service.viewCartItems(admin.getId());
					map.addAttribute("cartItems", cartItems);
					List<CartPOJO> AllcartItems = service.AllcartItem();
					ArrayList cartItemProd = cartItems(AllcartItems);
					map.addAttribute("cartItem", cartItemProd);
					map.addAttribute("size", 0);
					List<PopularProductsAddPOJO> products = service.getHomePageCards();
					map.addAttribute("productList",products);	
					map.addAttribute("pageId", 0);
				
					map.addAttribute("loginMsg", "Log In Successfully..!! ");
				}
				return "/NavBar";
	     //  return "redirect:./";
	    } else {
	        // Handle the case where fetching user information failed
	        return "redirect:/error";
	    }
	} else {
	    // Handle the case where token exchange failed
	    return "redirect:/error";
	} 
			
		}
	
		 
		 private String exchangeCodeForAccessToken(String code) {
			    String tokenUrl = "https://accounts.google.com/o/oauth2/token";
			    RestTemplate restTemplate = new RestTemplate();
	
			    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
			    requestBody.add("code", code);
			    requestBody.add("client_id", "217084231230-aigo48lncb4amfnogq1sn5heuca154ol.apps.googleusercontent.com");
			    requestBody.add("client_secret", "");
			    requestBody.add("redirect_uri", "http://localhost:8080/mvcproject1/callback");
			    requestBody.add("grant_type", "authorization_code");
	
			    ResponseEntity<String> response = restTemplate.postForEntity(tokenUrl, requestBody, String.class);
	
			    if (response.getStatusCode() == HttpStatus.OK) {
			        // Parse the response to extract the access token
			        JSONObject jsonResponse = new JSONObject(response.getBody());
			        return jsonResponse.getString("access_token");
			    } else {
			        // Handle the case where token exchange failed
			        return null;
			    }
			}
	
			private JSONObject fetchUserInfo(String accessToken) {
			    HttpHeaders headers = new HttpHeaders();
			    headers.set("Authorization", "Bearer " + accessToken);
	
			    HttpEntity<String> entity = new HttpEntity<>(headers);
			    RestTemplate restTemplate = new RestTemplate();
			    ResponseEntity<String> response = restTemplate.exchange("https://www.googleapis.com/oauth2/v3/userinfo", HttpMethod.GET, entity, String.class);
	
	
			    if (response.getStatusCode() == HttpStatus.OK) {
			        // Parse the response to extract user information
			        JSONObject jsonResponse = new JSONObject(response.getBody());
			        String name = jsonResponse.getString("name");
			        String email = jsonResponse.getString("email");
			        String givenName = jsonResponse.getString("given_name");
			        String familyName = jsonResponse.getString("family_name");
			        String pictureUrl = jsonResponse.getString("picture");
			//        String locale = jsonResponse.getString("locale");
			        String sub = jsonResponse.getString("sub"); // User's Google ID
			        // Customize this part based on the user information you want to fetch
			        System.out.println("Name: " + name);
			        System.out.println("Email: " + email);
			        System.out.println("Given Name: " + givenName);
			        System.out.println("Family Name: " + familyName);
			        System.out.println("Picture URL: " + pictureUrl);
			 //       System.out.println("Locale: " + locale);
			        System.out.println("Sub: " + sub);
			        return jsonResponse;
			    } else {
			        // Handle the case where fetching user information failed
			        return null;
			    }
			}

	


	public ArrayList cartItems(List<CartPOJO> AllcartItems) {
		ArrayList cartItem = new ArrayList();
		TreeSet cartProdId = new TreeSet();
		int selectedCartItemsId = 0;
		for(CartPOJO items : AllcartItems) {
			cartProdId.add(items.getProducts().getId());
		}
		for(Object items : cartProdId) {
			String selectedCartItems= "";
			for(CartPOJO item : AllcartItems) {
				if(item.getProducts().getId() == (int)items) {
					if(!selectedCartItems.isEmpty()) {
						selectedCartItems +=  "$" + item.getSize() + "&" + item.getColor() + "&" + item.getQuantity();
					}else {
					selectedCartItems = item.getProducts().getId() + "%" + item.getSize() + "&" + item.getColor() + "&" + item.getQuantity();
					}
					
				}
			}
			cartItem.add(selectedCartItems);
		}
		return cartItem;
	}
	
//	View Popular products controller
	@GetMapping("/view")
	public String viewPopularProducts(@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, HttpSession session,ModelMap map) {
		// int count = 0;
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		
		map.addAttribute("size", 0);
		map.addAttribute("pageId", 1);

		String path = session.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resource"
				+ File.separator + "assests" + File.separator ;
		System.out.println(path);
		return "PopularProductsView";


	}


	public List<PopularProductsAddPOJO> fillterAdding( String otherActive,String sortActive, ModelMap map){
		System.out.println("Kahtma " + otherActive);
		String[] otherActiveValues = otherActive.split(",");
		ArrayList otherValues = new ArrayList();
		for(String otherBtns:otherActiveValues ) {
			if(!otherBtns.equals("null")) {
				otherValues.add(otherBtns);
				
			}	
			System.out.println("gettting split " + otherBtns);
		}
		System.out.println("getttihng " + otherValues);
		String genderActive = "null";
		String priceActive = "null";
		String saleActive = "null";
		String sizeActive = "null";
		String colorActive = "null";
		for (Object btns : otherValues) {
			if(btns.equals("male") || btns.equals("female")) {
				if(genderActive.equals("null")) {
					genderActive = "";
				}
				if(!genderActive.isEmpty()) {
					genderActive += ",";
				}
				map.addAttribute("genderfillter" + btns,"genderfillter" + btns);
				genderActive +=  btns;
				map.addAttribute("genderActive" ,  genderActive);
			}
			if(btns.equals("1") || btns.equals("2") || btns.equals("3") ) {
				if(priceActive.equals("null")) {
					priceActive = "";
				}
				if(!priceActive.isEmpty()) {
					priceActive += ",";
				}
				map.addAttribute("pricefillter" + btns,"pricefillter" + btns);
				priceActive +=  btns;
				map.addAttribute("active" ,  priceActive);
			}
			if(btns.equals("sale")) {
				if(saleActive.equals("null")) {
					saleActive = "";
				}
				if(!saleActive.isEmpty()) {
					saleActive += ",";
				}
				map.addAttribute("sale" , btns);
				saleActive +=  btns;
				map.addAttribute("saleActive" ,  saleActive);
			}
			if(btns.equals("7") || btns.equals("8") || btns.equals("9") || btns.equals("10") ) {
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				map.addAttribute("getStyle" + btns, "getStyle" + btns);
				sizeActive += btns;
				map.addAttribute("sizeActive", sizeActive);
			}
		
			if(btns.equals("orange") || btns.equals("blue")|| btns.equals("yellow")||btns.equals("green")||btns.equals("red")||btns.equals("purple")) {
				
				if(colorActive.equals("null")) {
					colorActive = "";
				}
				if(!colorActive.isEmpty()) {
					colorActive += ",";
				}
				map.addAttribute( (String) btns, btns);
				colorActive += btns;	
				map.addAttribute("activeColor", colorActive);
			}
		}
		List<PopularProductsAddPOJO> products = service.viewPopularProductsByFillter(otherValues,sortActive);
		ArrayList otherActiveArray = new ArrayList();
		if(!genderActive.equals("null")) {
			otherActiveArray.add(genderActive);
		}
		if(!priceActive.equals("null")) {
			otherActiveArray.add(priceActive);
		}
		if(!saleActive.equals("null")) {
			otherActiveArray.add(saleActive);
		}
		if(!sizeActive.equals("null")) {
			otherActiveArray.add(sizeActive);
		}
		if(!colorActive.equals("null")) {
			otherActiveArray.add(colorActive);
			products = createDuplicatePro(products,otherValues);
		}
		String actives = "";
		for(Object others : otherActiveArray) {
			if(!actives.isEmpty()) {
				actives += ",";
			}
			actives += others;
		}
		if(actives.isEmpty()) {
			map.addAttribute("otherActive","null");
		}else {
				map.addAttribute("otherActive",  actives);
		}
		String sortClass ="";
		if(sortActive.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortActive.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortActive,sortActive);
		}
		if(!sortActive.equals("null")) {
			map.addAttribute("sortActive",sortActive);
			}
		
		return products;
	} 

	
	// addCart controller
			@GetMapping("/addCart")
			public String addCart(@RequestParam int id, @RequestParam int size,@RequestParam String color,@RequestParam int prodQuantity,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int pageId,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
				
				
				if(login != null) {
					int	customerId = login.getId();
				//	 customerId = 12;
						CustomerPOJO customer = service.searchCustomer(customerId);
						
						if(size != 0) {
							CartPOJO cart = service.SearchCartItems2(customerId, id, size,color);
							int quantity = 1;
							if (cart != null) {
								quantity = cart.getQuantity();
								quantity = prodQuantity;
								service.updateItem(quantity, cart.getId());
								map.addAttribute("cart", cart);
							
							}else {
								PopularProductsAddPOJO product = service.searchProduct(id);
								quantity = prodQuantity;
								String image ="";
								if(product.getOrColour().equals(color)) {
									image = product.getImage();
								}else if(product.getBColour().equals(color)) {
									image = product.getImage2();
								}else if(product.getYColour().equals(color)) {
									image = product.getImage3();
								}
								service.addItems(product, customer, quantity, size,color,image);
								cart = service.SearchCartItems2(customerId, id, size,color);
								map.addAttribute("cart", cart);
								
							}
							
						}else {
							
							map.addAttribute("getsize", "getsize");
							map.addAttribute("selectSizeMsg", "Please select a size.");
							map.addAttribute("selectedProductId", id);
						}

						map.addAttribute("userActive", "active");
						List<Object[]> cartItems = service.viewCartItems(login.getId());
						map.addAttribute("cartItems", cartItems);
						List<CartPOJO> AllcartItems = service.AllcartItem();
						ArrayList cartItemProd = cartItems(AllcartItems);
						map.addAttribute("cartItem", cartItemProd);
						int totalQuantity = service.cartTotal(login.getId());
						map.addAttribute("totalQuantity", totalQuantity);
					
						
				}else {
					map.addAttribute("loginPopup", "loginPopup");
					map.addAttribute("totalQuantity", 0);
				}
				List<PopularProductsAddPOJO> products = fillterAdding(otherActive,sortActive, map);

				
				map.addAttribute("size", 0);

				map.addAttribute("productList", products);
				map.addAttribute("pageId", pageId);
				if(pageId == 0) {
					
					 products = service.getHomePageCards();
					map.addAttribute("productList",products);
					return "NavBar";
				}
				return "PopularProductsView";
			
	
			}
	
	
	
	
	@GetMapping("sort")
	public String sort(@RequestParam String sortBy,@RequestParam String otherActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		List<PopularProductsAddPOJO> products = null;
		
		int customerId = 12;
		CustomerPOJO customer = service.searchCustomer(customerId);
		
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}

		if(!otherActive.equals("null") ) {
	
			 products = fillterAdding(otherActive,sortBy, map);
		}else {
		  products = service.viewPopularProductsSort(sortBy);
		}
		String sortClass ="";
		if(sortBy.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortBy.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortBy,sortBy);
		}
		
		map.addAttribute("sortActive",sortBy);
		map.addAttribute("productList", products);
		
		
		map.addAttribute("size", 0);
		System.out.println("oye  "+sortBy);
		map.addAttribute("pageId", 1);
		return "PopularProductsView";
	}


    @ExceptionHandler(PersistenceException.class)
    public String handlePersistenceException(PersistenceException ex) {
    	ex.printStackTrace();
		return "error";
	}
    @ExceptionHandler(IllegalStateException.class)
    public String illegalStateException(IllegalStateException ex) {
    	ex.printStackTrace();
		return "error";
	}
    @ExceptionHandler(TransactionException .class)
    public String handleTransactionException(TransactionException  ex) {
    	ex.printStackTrace();
		return "error";
	}
    @ExceptionHandler(TransactionException .class)
    public String handleNullPointerException(TransactionException  ex) {
    	ex.printStackTrace();
		return "error";
	}
    @ExceptionHandler(TransactionException .class)
    public String handleIllegalStateException(TransactionException  ex) {
    	ex.printStackTrace();
		return "error";
	}


// customerAdd page Controller
	@GetMapping("/customer")
	public String showCustomer() {
		return "AddCustomer";
	}

// CustomerAdd Controller	
	@PostMapping("/customerAdd")
	public String customerAdd(@RequestParam String name, @RequestParam long contact, @RequestParam String city,
			ModelMap map) {

		map.addAttribute("msg", "Added Successfully");
		return "AddCustomer";
	}



	// fillter controller
	@GetMapping("menFillter")
	public String menFillter(@RequestParam String gender,@RequestParam String active,@RequestParam String otherActive,@RequestParam String sortActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		String[] btnlist = active.split(",");
		String[] otherActives = otherActive.split(",");
		
		ArrayList otherActivesList = new ArrayList();
		for (String list : otherActives) {
			if(!list.equals("null")) {
				otherActivesList.add(list);
			}
		}
		
		ArrayList btnList = new ArrayList();
		for (String list : btnlist) {
			if(!list.equals("null")) {
			btnList.add(list);
			}
		}
		
		if (btnList.contains(gender)) {
			btnList.remove(gender);
			otherActivesList.remove(gender);
		} else {
			btnList.add(gender);
		}
		
		List<PopularProductsAddPOJO> products = service.viewPopularProductsbyGender(btnList,otherActivesList,sortActive);
		
		String genderActive = "null";
		for (Object btns : btnList) {
		map.addAttribute("genderfillter" + btns, "genderfillter" + btns);
			if(genderActive.equals("null")) {
				genderActive = "";
			}
			if(!genderActive.isEmpty()) {
				genderActive += ",";
			}
				genderActive += btns;
		}
		map.addAttribute("genderActive", genderActive);
		
		
		String priceActive = "null";
		String saleActive = "null";
		String sizeActive = "null";
		String colorActive = "null";
		for (Object btns : otherActivesList) {
			if(btns.equals("1") || btns.equals("2") || btns.equals("3") ) {
				if(priceActive.equals("null")) {
					priceActive = "";
				}
				if(!priceActive.isEmpty()) {
					priceActive += ",";
				}
				map.addAttribute("pricefillter" + btns,"pricefillter" + btns);
				priceActive +=  btns;
				map.addAttribute("active" ,  priceActive);
			}
			if(btns.equals("sale")) {
				if(saleActive.equals("null")) {
					saleActive = "";
				}
				if(!saleActive.isEmpty()) {
					saleActive += ",";
				}
				map.addAttribute("sale" , btns);
				saleActive +=  btns;
				map.addAttribute("saleActive" ,  saleActive);
			}
			if(btns.equals("7") || btns.equals("8") || btns.equals("9") || btns.equals("10") ) {
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				map.addAttribute("getStyle" + btns, "getStyle" + btns);
				sizeActive += btns;
				map.addAttribute("sizeActive", sizeActive);
			}
		
			if(btns.equals("orange") || btns.equals("blue")|| btns.equals("yellow")||btns.equals("green")||btns.equals("red")||btns.equals("purple")) {
				
				if(colorActive.equals("null")) {
					colorActive = "";
				}
				if(!colorActive.isEmpty()) {
					colorActive += ",";
				}
				map.addAttribute( (String) btns, btns);
				colorActive += btns;	
				map.addAttribute("activeColor", colorActive);
			}
		}
		ArrayList otherActiveArray = new ArrayList();
		if(!priceActive.equals("null")) {
			otherActiveArray.add(priceActive);
		}
		if(!saleActive.equals("null")) {
			otherActiveArray.add(saleActive);
		}
		if(!sizeActive.equals("null")) {
			otherActiveArray.add(sizeActive);
		}
		if(!colorActive.equals("null")) {
			otherActiveArray.add(colorActive);
			products = createDuplicatePro(products,otherActivesList);
		}
		String actives = "";
		for(Object others : otherActiveArray) {
			if(!actives.isEmpty()) {
				actives += ",";
			}
			actives += others;
		}
		if(actives.isEmpty()) {
			map.addAttribute("otherActive",genderActive);
		}else {
			if(genderActive.equals("null")) {
				map.addAttribute("otherActive",  actives);
			}else {
			map.addAttribute("otherActive", genderActive +","+ actives);
			}
		}
		String sortClass ="";
		if(sortActive.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortActive.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortActive,sortActive);
		}
		if(!sortActive.equals("null")) {
			map.addAttribute("sortActive",sortActive);
			}
		int customerId = 12;
		CustomerPOJO customer = service.searchCustomer(customerId);
		
		map.addAttribute("size", 0);
		map.addAttribute("productList", products);
		map.addAttribute("pageId", 1);
		return "PopularProductsView";
	}



	@GetMapping("priceFillter")
	public String priceFillter(@RequestParam String checkedPrice, @RequestParam String active,@RequestParam String otherActive,@RequestParam String sortActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		
		String[] btnlist = active.split(",");
		String[] otherActives = otherActive.split(",");
		
		ArrayList otherActivesList = new ArrayList();
		for (String list : otherActives) {
			if(!list.equals("null")) {
				otherActivesList.add(list);
			}
		}
		
		ArrayList btnList = new ArrayList();
		for (String list : btnlist) {
			if(!list.equals("null")) {
			btnList.add(list);
			}
		}
		
		if (btnList.contains(checkedPrice)) {
			btnList.remove(checkedPrice);
			otherActivesList.remove(checkedPrice);
		} else {
			btnList.add(checkedPrice);
		}
		
		List<PopularProductsAddPOJO> products = service.viewPopularProductsPrice(btnList,otherActivesList,sortActive);
		String priceActive = "null";
		for (Object btns : btnList) {
			map.addAttribute("pricefillter" + btns, "pricefillter" + btns);

			if(priceActive.equals("null")) {
				priceActive = "";
			}
			if(!priceActive.isEmpty()) {
				priceActive += ",";
			}
			priceActive += btns;
		}
		map.addAttribute("active", priceActive);

		String genderActive1 = "null";
		String saleActive = "null";
		String sizeActive = "null";
		String colorActive = "null";
		for (Object btns : otherActivesList) {
			if(btns.equals("male") || btns.equals("female")) {
				if(genderActive1.equals("null")) {
					genderActive1 = "";
				}
				if(!genderActive1.isEmpty()) {
					genderActive1 += ",";
				}
				map.addAttribute("genderfillter" + btns, "genderfillter" + btns);
				genderActive1 += btns;
				map.addAttribute("genderActive" ,  genderActive1);
			}
			if(btns.equals("sale")) {
				if(saleActive.equals("null")) {
					saleActive = "";
				}
				if(!saleActive.isEmpty()) {
					saleActive += ",";
				}
				map.addAttribute("sale" , btns);
				saleActive +=  btns;
				map.addAttribute("saleActive" ,  saleActive);
			}
			if(btns.equals("7") || btns.equals("8") || btns.equals("9") || btns.equals("10") ) {
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				map.addAttribute("getStyle" + btns, "getStyle" + btns);
				sizeActive += btns;
				map.addAttribute("sizeActive", sizeActive);
			}

			if(btns.equals("orange") || btns.equals("blue")|| btns.equals("yellow")||btns.equals("green")||btns.equals("red")||btns.equals("purple")) {
				
				if(colorActive.equals("null")) {
					colorActive = "";
				}
				if(!colorActive.isEmpty()) {
					colorActive += ",";
				}
				map.addAttribute( (String) btns, btns);
				colorActive += btns;	
				map.addAttribute("activeColor", colorActive);
			}
		}
		ArrayList otherActiveArray = new ArrayList();
		if(!genderActive1.equals("null")) {
			otherActiveArray.add(genderActive1);
		}
		if(!saleActive.equals("null")) {
			otherActiveArray.add(saleActive);
		}
		if(!sizeActive.equals("null")) {
			otherActiveArray.add(sizeActive);
		}
		if(!colorActive.equals("null")) {
			otherActiveArray.add(colorActive);
			products = createDuplicatePro(products,otherActivesList);
		}
		String actives = "";
		for(Object others : otherActiveArray) {
			if(!actives.isEmpty()) {
				actives += ",";
			}
			actives += others;
		}
		if(actives.isEmpty()) {
			map.addAttribute("otherActive",priceActive);
		}else {
			if(priceActive.equals("null")) {
				map.addAttribute("otherActive",  actives);
			}else {
			map.addAttribute("otherActive", priceActive +","+ actives);
			}
		}
		
		String sortClass ="";
		if(sortActive.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortActive.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortActive,sortActive);
		}
		if(!sortActive.equals("null")) {
			map.addAttribute("sortActive",sortActive);
			}
		
		int customerId = 12;
		CustomerPOJO customer = service.searchCustomer(customerId);

		
		map.addAttribute("size", 0);
		map.addAttribute("productList", products);
		map.addAttribute("pageId", 1);
		return "PopularProductsView";

	}
	
//	Sale Fillter Controller
	@GetMapping("sale")
	
	public String sale(@RequestParam String btn,@RequestParam String saleActive,@RequestParam String otherActive,@RequestParam String sortActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		String[] saleActiveValues = saleActive.split(",");
		String[] otherActiveValues = otherActive.split(",");
		
		ArrayList otherValues = new ArrayList();
		for(String otherBtns:otherActiveValues ) {
			if(!otherBtns.equals("null")) {
				otherValues.add(otherBtns);
			}	
		}
		
		ArrayList saleValues = new ArrayList();
		for(String saleBtns:saleActiveValues ) {
			if(!saleBtns.equals("null")) {
			saleValues.add(saleBtns);
			}	
		}
		
		if(saleValues.contains(btn)) {
			saleValues.remove(btn);
			otherValues.remove(btn);
		}else {
			saleValues.add(btn);
		}
		List<PopularProductsAddPOJO>products = service.saleFillter(saleValues,otherValues,sortActive);

		String activeSale = "null";
		for(Object saleBtnsValues : saleValues) {
			map.addAttribute("sale" ,  saleBtnsValues);
			if(activeSale.equals("null")) {
				activeSale = "";
			}
			if(!activeSale.isEmpty()) {
				activeSale += ",";
			}
			activeSale += saleBtnsValues;
		}
		map.addAttribute("saleActive", activeSale);
		
		String genderActive = "null";
		String priceActive = "null";
		String sizeActive = "null";
		String colorActive = "null";
		for(Object otherBtns : otherValues) {
			if(otherBtns.equals("male") || otherBtns.equals("female")) {
				if(genderActive.equals("null")) {
					genderActive = "";
				}
				if(!genderActive.isEmpty()) {
					genderActive += ",";
				}
				map.addAttribute("genderfillter" + otherBtns,"genderfillter" + otherBtns);
				genderActive +=  otherBtns;
				map.addAttribute("genderActive" ,  genderActive);
			}
			if(otherBtns.equals("1") || otherBtns.equals("2") ||otherBtns.equals("3")  ) {
				if(priceActive.equals("null")) {
					priceActive = "";
				}
				if(!priceActive.isEmpty()) {
					priceActive += ",";
				}
				map.addAttribute("pricefillter" + otherBtns,"pricefillter" + otherBtns);
				priceActive +=  otherBtns;
				map.addAttribute("active" ,  priceActive);
			}
			if(otherBtns.equals("7") || otherBtns.equals("8") || otherBtns.equals("9") || otherBtns.equals("10") ) {
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				map.addAttribute("getStyle" + otherBtns, "getStyle" + otherBtns);
				sizeActive += otherBtns;
				map.addAttribute("sizeActive", sizeActive);
			}
				if(otherBtns.equals("orange") || otherBtns.equals("blue") || otherBtns.equals("yellow") || otherBtns.equals("green") || otherBtns.equals("red") || otherBtns.equals("purple")) {
				
				if(colorActive.equals("null")) {
					colorActive = "";
				}
				if(!colorActive.isEmpty()) {
					colorActive += ",";
				}
				map.addAttribute( (String) otherBtns, otherBtns);
				colorActive += otherBtns;	
				map.addAttribute("activeColor", colorActive);
			}
			
			
		}
		
		ArrayList otherActiveArray = new ArrayList();
		if(!genderActive.equals("null")) {
			otherActiveArray.add(genderActive);
		}
		if(!priceActive.equals("null")) {
			otherActiveArray.add(priceActive);
		}
		if(!sizeActive.equals("null")) {
			otherActiveArray.add(sizeActive);
		}
		if(!colorActive.equals("null")) {
			otherActiveArray.add(colorActive);
			products = createDuplicatePro(products,otherValues);
		}
		String otherActives = "";
		for(Object actives : otherActiveArray) {
			if(!otherActives.isEmpty()) {
				otherActives += ",";
			}
			otherActives += actives;
		}
		if(otherActives.isEmpty()) {
			map.addAttribute("otherActive",activeSale);
		}else {
			if(activeSale.equals("null")) {
				map.addAttribute("otherActive",  otherActives);
			}else {
			map.addAttribute("otherActive", activeSale +","+ otherActives);
			}
		}
		
		String sortClass ="";
		if(sortActive.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortActive.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortActive,sortActive);
		}
		if(!sortActive.equals("null")) {
			map.addAttribute("sortActive",sortActive);
			}
		

		map.addAttribute("size", 0);
		map.addAttribute("productList", products);
		map.addAttribute("pageId", 1);
		return "PopularProductsView";
		
	}
	
	@GetMapping("sizeFillter")
	public String sizeFillter(@RequestParam String btn, @RequestParam String active,@RequestParam String otherActive,@RequestParam String sortActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		
		String[] btnlist = active.split(",");
		String[] otherActives = otherActive.split(",");
		ArrayList otherActivesList = new ArrayList();
		for (String list : otherActives) {
			if(!list.equals("null")) {
				otherActivesList.add(list);
			}
		}
		ArrayList btnList = new ArrayList();
			for (String list : btnlist) {
				if(!list.equals("null")) {
				btnList.add(list);
				}
			}
		if (btnList.contains(btn)) {
			btnList.remove(btn);
			otherActivesList.remove(btn);
		} else {
			btnList.add(btn);
		}
		List<PopularProductsAddPOJO> products = service.viewPopularProductsSize(btnList,otherActivesList,sortActive);
		String sizeActive = "null";  
		for (Object btns : btnList) {
			map.addAttribute("getStyle" + btns, "getStyle" + btns);
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				sizeActive += btns;
			}
			map.addAttribute("sizeActive", sizeActive);
			String genderActive = "null";
			String priceActive = "null";
			String saleActive = "null";
			String colorActive = "null";
			for (Object btns : otherActivesList) {
				if(btns.equals("male") || btns.equals("female")) {
					if(genderActive.equals("null")) {
						genderActive = "";
					}
					if(!genderActive.isEmpty()) {
						genderActive += ",";
					}
					map.addAttribute("genderfillter" + btns,"genderfillter" + btns);
					genderActive +=  btns;
					map.addAttribute("genderActive" ,  genderActive);
				}
				if(btns.equals("1") || btns.equals("2") || btns.equals("3") ) {
					if(priceActive.equals("null")) {
						priceActive = "";
					}
					if(!priceActive.isEmpty()) {
						priceActive += ",";
					}
					map.addAttribute("pricefillter" + btns,"pricefillter" + btns);
					priceActive +=  btns;
					map.addAttribute("active" ,  priceActive);
				}
				if(btns.equals("sale")) {
					if(saleActive.equals("null")) {
						saleActive = "";
					}
					if(!saleActive.isEmpty()) {
						saleActive += ",";
					}
					map.addAttribute("sale" , btns);
					saleActive +=  btns;
					map.addAttribute("saleActive" ,  saleActive);
				}
				if(btns.equals("orange") || btns.equals("blue")|| btns.equals("yellow")||btns.equals("green")||btns.equals("red")||btns.equals("purple")) {
					
					if(colorActive.equals("null")) {
						colorActive = "";
					}
					if(!colorActive.isEmpty()) {
						colorActive += ",";
					}
					map.addAttribute( (String) btns, btns);
					colorActive += btns;	
					map.addAttribute("activeColor", colorActive);
				}
			}
			ArrayList otherActiveArray = new ArrayList();
			if(!genderActive.equals("null")) {
				otherActiveArray.add(genderActive);
			}
			if(!priceActive.equals("null")) {
				otherActiveArray.add(priceActive);
			}
			if(!saleActive.equals("null")) {
				otherActiveArray.add(saleActive);
			}
			if(!colorActive.equals("null")) {
				otherActiveArray.add(colorActive);
				products = createDuplicatePro(products,otherActivesList);
			}
			String otherActivesbtn = "";
			for(Object actives : otherActiveArray) {
				if(!otherActivesbtn.isEmpty()) {
					otherActivesbtn += ",";
				}
				otherActivesbtn += actives;
			}
			if(otherActivesbtn.isEmpty()) {
				map.addAttribute("otherActive",sizeActive);
			}else {
				if(sizeActive.equals("null")) {
					map.addAttribute("otherActive",  otherActivesbtn);
				}else {
				map.addAttribute("otherActive", sizeActive +","+ otherActivesbtn);
				}
			}
			
			String sortClass ="";
			if(sortActive.equals("Price: High-Low")) {
				sortClass = "HighLow";
			}else if(sortActive.equals("Price: Low-High")) {
				sortClass = "LowHigh";
			}
			if(!sortClass.isEmpty()) {
				map.addAttribute(sortClass,sortClass);
			}else {
				map.addAttribute(sortActive,sortActive);
			}
			if(!sortActive.equals("null")) {
				map.addAttribute("sortActive",sortActive);
				}

			map.addAttribute("size", 0);
			map.addAttribute("productList", products);
			map.addAttribute("pageId", 1);
			return "PopularProductsView";
	}

	public List<PopularProductsAddPOJO> createDuplicatePro(List<PopularProductsAddPOJO> products, ArrayList btnList) {
		List<PopularProductsAddPOJO> duplicates = new ArrayList<>();
		for(PopularProductsAddPOJO list1 : products ) {
			int count = 0;
			for(Object list:btnList) {
				if((list1.getOrColour() != null && list1.getOrColour().contains((String)list)) || (list1.getBColour() != null && list1.getBColour().contains((String)list)) || (list1.getYColour() != null && list1.getYColour().contains((String)list))) {
					count++;
					if(count >= 2) {
						PopularProductsAddPOJO	duplicate = new PopularProductsAddPOJO();
						duplicate.setId(list1.getId());
						duplicate.setType(list1.getType());
						duplicate.setName(list1.getName());
						duplicate.setInfo(list1.getInfo());
						duplicate.setBrand(list1.getBrand());
						duplicate.setSize7(list1.getSize7());
						duplicate.setSize8(list1.getSize8());
						duplicate.setSize9(list1.getSize9());
						duplicate.setSize10(list1.getSize10());
						duplicate.setOrColour(list1.getOrColour());
						duplicate.setBColour(list1.getBColour());
						duplicate.setYColour(list1.getYColour());
						duplicate.setPrice(list1.getPrice());
						duplicate.setQuantity(list1.getQuantity());
						duplicate.setImage(list1.getImage());
						duplicate.setImage2(list1.getImage2());
						duplicate.setImage3(list1.getImage3());
						duplicates.add(duplicate);
				}

			if(!list1.getOrColour().contains((String)list)) {
			if(list1.getBColour().contains((String)list)) {
				list1.setBColour(list1.getOrColour());
				list1.setOrColour((String)list);
				String img =(String)list1.getImage();
				list1.setImage(list1.getImage2());
				list1.setImage2(img);
			}else if(list1.getYColour().contains((String)list)) {
				list1.setYColour(list1.getOrColour());
				list1.setOrColour((String)list);
				String img =(String)list1.getImage();
				list1.setImage(list1.getImage3());
				list1.setImage3(img);
			}
			}
			
		}
			}
			

	}
		
		if(!duplicates.isEmpty()) {
			List<PopularProductsAddPOJO> dupliProds = new ArrayList<>();

		System.out.println("mutttt "+duplicates);
		for(PopularProductsAddPOJO list:products) {	
			dupliProds.add(list);
			for(PopularProductsAddPOJO duplicate:duplicates) {
				if(duplicate.getId() == list.getId()) {
					dupliProds.add(duplicate);
				}
			}
		}
		products.removeAll(products);
		products.addAll(dupliProds);
		
			
			List<PopularProductsAddPOJO> matchingProducts = new ArrayList<>();
			for(int i = btnList.size()-1;i>=0;i--) {
			for(PopularProductsAddPOJO product : products) {
			
				if(product.getOrColour().equals(btnList.get(i))) {
					matchingProducts.add(product);
				}
			}
				 
			}
			products.removeAll(products);
			products.addAll(matchingProducts);
			}
		return products;
	}
	
	
	@GetMapping("colorFillter")
	public String colorFillter(@RequestParam String color, @RequestParam String activeColor,@RequestParam String otherActive,String sortActive,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<Object[]> cartItems = service.viewCartItems(login.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
		}
		
		String[] btnlist = activeColor.split(",");
		String[] otherActives = otherActive.split(",");
		ArrayList otherActivesList = new ArrayList();
		for (String list : otherActives) {
			if(!list.equals("null")) {
				otherActivesList.add(list);
			}
		}
		ArrayList btnList = new ArrayList();
			for (String list : btnlist) {
				if(!list.equals("null")) {
				btnList.add(list);
				}
			}
		if (btnList.contains(color)) {
			btnList.remove(color);
			otherActivesList.remove(color);
		} else {
			btnList.add(color);
		}
		
		List<PopularProductsAddPOJO> products = service.viewPopularProductsColor(btnList,otherActivesList,sortActive);
		if(!btnList.isEmpty()) { 
		List<PopularProductsAddPOJO> duplicates = new ArrayList<>();
				for(PopularProductsAddPOJO list1 : products ) {
					int count = 0;
					for(Object list:btnList) {
						if((list1.getOrColour() != null && list1.getOrColour().contains((String)list)) || (list1.getBColour() != null && list1.getBColour().contains((String)list)) || (list1.getYColour() != null && list1.getYColour().contains((String)list))) {
							count++;
							if(count >= 2) {
								PopularProductsAddPOJO	duplicate = new PopularProductsAddPOJO();
								duplicate.setId(list1.getId());
								duplicate.setType(list1.getType());
								duplicate.setName(list1.getName());
								duplicate.setInfo(list1.getInfo());
								duplicate.setBrand(list1.getBrand());
								duplicate.setSize7(list1.getSize7());
								duplicate.setSize8(list1.getSize8());
								duplicate.setSize9(list1.getSize9());
								duplicate.setSize10(list1.getSize10());
								duplicate.setOrColour(list1.getOrColour());
								duplicate.setBColour(list1.getBColour());
								duplicate.setYColour(list1.getYColour());
								duplicate.setPrice(list1.getPrice());
								duplicate.setQuantity(list1.getQuantity());
								duplicate.setImage(list1.getImage());
								duplicate.setImage2(list1.getImage2());
								duplicate.setImage3(list1.getImage3());
								duplicates.add(duplicate);
						}

					if(!list1.getOrColour().contains((String)list)) {
					if(list1.getBColour().contains((String)list)) {
						list1.setBColour(list1.getOrColour());
						list1.setOrColour((String)list);
						String img =(String)list1.getImage();
						list1.setImage(list1.getImage2());
						list1.setImage2(img);
					}else if(list1.getYColour().contains((String)list)) {
						list1.setYColour(list1.getOrColour());
						list1.setOrColour((String)list);
						String img =(String)list1.getImage();
						list1.setImage(list1.getImage3());
						list1.setImage3(img);
					}
					}
					
				}
					}
					

			}
				
				
				if(!duplicates.isEmpty()) {
					List<PopularProductsAddPOJO> dupliProds = new ArrayList<>();
		
				System.out.println("mutttt "+duplicates);
				for(PopularProductsAddPOJO list:products) {	
					dupliProds.add(list);
					for(PopularProductsAddPOJO duplicate:duplicates) {
						if(duplicate.getId() == list.getId()) {
							dupliProds.add(duplicate);
						}
					}
				}
				products.removeAll(products);
				products.addAll(dupliProds);
				
				List<PopularProductsAddPOJO> matchingProducts = new ArrayList<>();
				for(int i = btnList.size()-1;i>=0;i--) {
				for(PopularProductsAddPOJO product : products) {
					if(product.getOrColour().equals(btnList.get(i))) {
						matchingProducts.add(product);
					}
				}
					 
				}
				
				products.removeAll(products);
				products.addAll(matchingProducts);
				}
		}
		String colorActive = "null";
		for (Object btns : btnList) {
			map.addAttribute( (String) btns, btns);
			if(colorActive.equals("null")) {
				colorActive = "";
			}
			if(!colorActive.isEmpty()) {
				colorActive += ",";
			}
			colorActive += btns;			
		}
		map.addAttribute("activeColor", colorActive);
		String genderActive = "null";
		String priceActive = "null";
		String saleActive = "null";
		String sizeActive = "null";
		for (Object btns : otherActivesList) {
			if(btns.equals("male") || btns.equals("female")) {
				if(genderActive.equals("null")) {
					genderActive = "";
				}
				if(!genderActive.isEmpty()) {
					genderActive += ",";
				}
				map.addAttribute("genderfillter" + btns,"genderfillter" + btns);
				genderActive +=  btns;
				map.addAttribute("genderActive" ,  genderActive);
			}
			if(btns.equals("1") || btns.equals("2") || btns.equals("3") ) {
				if(priceActive.equals("null")) {
					priceActive = "";
				}
				if(!priceActive.isEmpty()) {
					priceActive += ",";
				}
				map.addAttribute("pricefillter" + btns,"pricefillter" + btns);
				priceActive +=  btns;
				map.addAttribute("active" ,  priceActive);
			}
			if(btns.equals("sale")) {
				if(saleActive.equals("null")) {
					saleActive = "";
				}
				if(!saleActive.isEmpty()) {
					saleActive += ",";
				}
				map.addAttribute("sale" , btns);
				saleActive +=  btns;
				map.addAttribute("saleActive" ,  saleActive);
			}
			if(btns.equals("7") || btns.equals("8") || btns.equals("9") || btns.equals("10") ) {
				if(sizeActive.equals("null")) {
					sizeActive = "";
				}
				if(!sizeActive.isEmpty()) {
					sizeActive += ",";
				}
				map.addAttribute("getStyle" + btns, "getStyle" + btns);
				sizeActive += btns;
				map.addAttribute("sizeActive", sizeActive);
			}
			
		}
		ArrayList otherActiveArray = new ArrayList();
		if(!genderActive.equals("null")) {
			otherActiveArray.add(genderActive);
		}
		if(!priceActive.equals("null")) {
			otherActiveArray.add(priceActive);
		}
		if(!saleActive.equals("null")) {
			otherActiveArray.add(saleActive);
		}
		if(!sizeActive.equals("null")) {
			otherActiveArray.add(sizeActive);
		}
		String otherActivesbtn = "";
		for(Object actives : otherActiveArray) {
			if(!otherActivesbtn.isEmpty()) {
				otherActivesbtn += ",";
			}
			otherActivesbtn += actives;
		}
		if(otherActivesbtn.isEmpty()) {
			map.addAttribute("otherActive",colorActive);
		}else {
			if(colorActive.equals("null")) {
				map.addAttribute("otherActive",  otherActivesbtn);
			}else {
			map.addAttribute("otherActive", colorActive +","+ otherActivesbtn);
			}
		}
		String sortClass ="";
		if(sortActive.equals("Price: High-Low")) {
			sortClass = "HighLow";
		}else if(sortActive.equals("Price: Low-High")) {
			sortClass = "LowHigh";
		}
		if(!sortClass.isEmpty()) {
			map.addAttribute(sortClass,sortClass);
		}else {
			map.addAttribute(sortActive,sortActive);
		}
		if(!sortActive.equals("null")) {
			map.addAttribute("sortActive",sortActive);
			}
		

		map.addAttribute("size", 0);
		map.addAttribute("productList", products);
		map.addAttribute("pageId", 1);
		return "PopularProductsView";
	}


//	selectedCart controller
	@GetMapping("selectedCart")
	public String selectedCart(@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int pageId,@RequestParam int id, @RequestParam int size,@RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {

		
			
				if(login != null) {
					int totalQuantity = service.cartTotal(login.getId());
					map.addAttribute("totalQuantity", totalQuantity);
					List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
					map.addAttribute("cartItems", cartItems);
					List<PopularProductsAddPOJO> products = service.viewPopularProducts();
					map.addAttribute("productList", products);
					map.addAttribute("userActive", "active");
					return "SelectedCart";
				}else {
					map.addAttribute("loginPopup", "loginPopup");
					map.addAttribute("size", 0);
					map.addAttribute("totalQuantity", 0);
					map.addAttribute("pageId", pageId);
					if(pageId == 0) {
						List<PopularProductsAddPOJO> products = service.getHomePageCards();
						map.addAttribute("productList",products);
						return "NavBar";
					}else if(pageId == 1) {
						List<PopularProductsAddPOJO> products = fillterAdding(otherActive,sortActive, map);
						map.addAttribute("productList",products);
						return "PopularProductsView";
					}else {
						PopularProductsAddPOJO product = service.searchProduct(id);
						if(product.getOrColour().equals(color)) {
							map.addAttribute("imgIndex", "0");
						}else if(product.getBColour().equals(color)) {
							map.addAttribute("imgIndex", "1");
						}else if(product.getYColour().equals(color)) {
							map.addAttribute("imgIndex", "2");
						}
						map.addAttribute("color", color);
						
						map.addAttribute("product", product);
						List<PopularProductsAddPOJO> products = service.viewPopularProducts();
						map.addAttribute("productList", products);
						map.addAttribute("id",id);
						return "Product";
					}
				}
				
				

				
		}

// product controller
	@GetMapping("product")
	public String product(@RequestParam int id, @RequestParam int size,@RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
			map.addAttribute("userActive", "active");
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<WishlistPOJO> wishlistProd = service.searchWishlistByProd(id, login.getId()); 
			String wishProd ="";
			for(WishlistPOJO item:wishlistProd) {
				if(!wishProd.isEmpty()) {
					wishProd += "&";
				}
			wishProd +=item.getColor() + "$" + item.getSize();
			}
			map.addAttribute("wishProd", wishProd);
			WishlistPOJO searchWishlist = service.searchWishlistItem(id, login.getId(), size);
			if (searchWishlist != null) {
				map.addAttribute("fillIcon", "solid");
			} else {
				map.addAttribute("fillIcon", "regular");
			}
		}
		
		if (size == 7) {
			map.addAttribute("Ssize", "Ssize");
		} else if (size == 8) {
			map.addAttribute("Msize", "Msize");
		} else if (size == 9) {
			map.addAttribute("Lsize", "Lsize");
		} else if (size == 10) {
			map.addAttribute("Xsize", "Xsize");
		}
		if (size != 0) {
			map.addAttribute("size", size);
		} else {
			map.addAttribute("size", 0);
		}

	
		PopularProductsAddPOJO product = service.searchProduct(id);
		if(product.getOrColour().equals(color)) {
			map.addAttribute("imgIndex", "0");
		}else if(product.getBColour().equals(color)) {
			map.addAttribute("imgIndex", "1");
		}else if(product.getYColour().equals(color)) {
			map.addAttribute("imgIndex", "2");
		}
		map.addAttribute("color", color);
		
		map.addAttribute("product", product);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
	
	
		map.addAttribute("pageId", 2);
		map.addAttribute("id",id);
		return "Product";
	}

// wishList controller
	@GetMapping("wishlist")
	public String wishlist(@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int pageId,@RequestParam int id, @RequestParam int size,@RequestParam String color, @SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {


		if(login != null) {
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			List<WishlistPOJO> wishlistProducts = service.searchWishlist(login.getId());
			map.addAttribute("wishlistProducts", wishlistProducts);
			List<PopularProductsAddPOJO> products = service.viewPopularProducts();
			map.addAttribute("productList", products);
			map.addAttribute("userActive", "active");
			return "Wishlist";
		}else {
			map.addAttribute("loginPopup", "loginPopup");
			map.addAttribute("size", 0);
			map.addAttribute("totalQuantity", 0);
			map.addAttribute("pageId", pageId);
			if(pageId == 0) {
				List<PopularProductsAddPOJO> products = service.getHomePageCards();
				map.addAttribute("productList",products);
				return "NavBar";
			}else if(pageId == 1) {
				List<PopularProductsAddPOJO> products = fillterAdding(otherActive,sortActive, map);
				map.addAttribute("productList",products);
				return "PopularProductsView";
			}else {
				PopularProductsAddPOJO product = service.searchProduct(id);
				if(product.getOrColour().equals(color)) {
					map.addAttribute("imgIndex", "0");
				}else if(product.getBColour().equals(color)) {
					map.addAttribute("imgIndex", "1");
				}else if(product.getYColour().equals(color)) {
					map.addAttribute("imgIndex", "2");
				}
				map.addAttribute("color", color);
				
				map.addAttribute("product", product);
				List<PopularProductsAddPOJO> products = service.viewPopularProducts();
				map.addAttribute("productList", products);
				map.addAttribute("id",id);
				return "Product";
			}
		}
		
		
	}

	// addtobagProduct Controller

	@GetMapping("addtobagProduct")
	public String addtobagProduct(@RequestParam int size, @RequestParam int productId,@RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		
		PopularProductsAddPOJO product = service.searchProduct(productId);
		map.addAttribute("product", product);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
	//	int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", 0);
		map.addAttribute("color",color);
		map.addAttribute("size", 0);
		map.addAttribute("pageId", 2);
		map.addAttribute("id",productId);
		if(product.getOrColour().equals(color)) {
			map.addAttribute("imgIndex", "0");
		}else if(product.getBColour().equals(color)) {
			map.addAttribute("imgIndex", "1");
		}else if(product.getYColour().equals(color)) {
			map.addAttribute("imgIndex", "2");
		}
		if(login != null) {
		
			CustomerPOJO customer = service.searchCustomer(login.getId());
			map.addAttribute("userActive", "active");
			WishlistPOJO searchWishlist = service.searchWishlistItem(productId, login.getId(), size);
			if (searchWishlist != null) {
				map.addAttribute("fillIcon", "solid");
			} else {
				map.addAttribute("fillIcon", "regular");
			}
			List<WishlistPOJO> wishlistProd = service.searchWishlistByProd(productId, login.getId()); 
			String wishProd ="";
			for(WishlistPOJO item:wishlistProd) {
				if(!wishProd.isEmpty()) {
					wishProd += "&";
				}
			wishProd +=item.getColor() + "$" + item.getSize();
			}
			map.addAttribute("wishProd", wishProd);
			
			if (size != 0) {
				CartPOJO cart = service.SearchCartItems2(login.getId(), productId, size,color);
				int quantity = 0;
				String image ="";
				if(product.getOrColour().equals(color)) {
					image = product.getImage();
				}else if(product.getBColour().equals(color)) {
					image = product.getImage2();
				}else if(product.getYColour().equals(color)) {
					image = product.getImage3();
				}
				if (cart != null) {
					quantity = cart.getQuantity();
					quantity++;
					service.updateItem(quantity, cart.getId());
				} else {
					quantity = 1;
					service.addItems(product,customer,quantity,size,color,image);
				}
				cart = service.SearchCartItems2(login.getId(), productId, size,color);
				map.addAttribute("cart", cart);
				int totalQuantity = service.cartTotal(login.getId());
				map.addAttribute("totalQuantity", totalQuantity);
				return "Product" ;
			}
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			map.addAttribute("getSize", "getSize");
			map.addAttribute("selectSize", "selectSize");
			map.addAttribute("selectSizemsg", "Please select a size.");
			return "Product";
		}else {
			map.addAttribute("loginPopup", "loginPopup");
			return "Product";
		}
		

	}

//	Favourite controller
	@GetMapping("favourite")
	public String favourite(@RequestParam int id, @RequestParam int size,@RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
			
		PopularProductsAddPOJO product = service.searchProduct(id);
		map.addAttribute("product", product);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
//		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", 0);
		map.addAttribute("color",color);
		map.addAttribute("size", 0);
		map.addAttribute("pageId", 2);
		map.addAttribute("id",id);
		if(product.getOrColour().equals(color)) {
			map.addAttribute("imgIndex", "0");
		}else if(product.getBColour().equals(color)) {
			map.addAttribute("imgIndex", "1");
		}else if(product.getYColour().equals(color)) {
			map.addAttribute("imgIndex", "2");
		}
		if(login != null) {
		
			CustomerPOJO customer = service.searchCustomer(login.getId());
			map.addAttribute("userActive", "active");
			WishlistPOJO searchWishlistColor = service.searchWishlistItemColor(id, login.getId(), color,size);
			String image ="";
			if(product.getOrColour().equals(color)) {
				image = product.getImage();
			}else if(product.getBColour().equals(color)) {
				image = product.getImage2();
			}else if(product.getYColour().equals(color)) {
				image = product.getImage3();
			}
			if (searchWishlistColor != null) {
				service.removeWishlistItem(searchWishlistColor.getId());
				map.addAttribute("fillIcon", "regular");
			} else {
				WishlistPOJO list = service.addWishlist(customer, product, size,color,image);
				map.addAttribute("fillIcon", "solid");
			}
			List<WishlistPOJO> wishlistProd = service.searchWishlistByProd(id, login.getId()); 
			String wishProd ="";
			for(WishlistPOJO item:wishlistProd) {
				if(!wishProd.isEmpty()) {
					wishProd += "&";
				}
			wishProd +=item.getColor() + "$" + item.getSize();
			}
			map.addAttribute("wishProd", wishProd);
			searchWishlistColor =  service.searchWishlistItemColor(id, login.getId(), color,size);
			map.addAttribute("wishlist", searchWishlistColor);
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity );
			return "Product";
		}
		map.addAttribute("loginPopup", "loginPopup");
		
		return "Product";
//
	}

	// selectSize controller
	@GetMapping("selectSize")
	public String selectSize(@RequestParam int productId,@RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		WishlistPOJO  product  = service.searchWishlistSelectSize(productId, login.getId(), color);
		map.addAttribute("product", product);
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<WishlistPOJO> wishlistProducts = service.searchWishlist(login.getId());
		map.addAttribute("wishlistProducts", wishlistProducts);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		map.addAttribute("userActive", "active");
		return "Wishlist";
	}

	// wishlist add to bag controller
	@GetMapping("addtobag")
	public String addToBag(@RequestParam int size, @RequestParam int productId, @RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
	
	
			int customerId = login.getId();
			PopularProductsAddPOJO product1 = service.searchProduct(productId);
			CartPOJO cart = service.SearchCartItems2(customerId, productId, size,color);
			int quantity = 0;
			String image ="";
			if(product1.getOrColour().equals(color)) {
				image = product1.getImage();
			}else if(product1.getBColour().equals(color)) {
				image = product1.getImage2();
			}else if(product1.getYColour().equals(color)) {
				image = product1.getImage3();
			}
			
			if (cart != null) {
				quantity = cart.getQuantity();
				quantity++;
			
				service.updateItem(quantity, cart.getId());
			} else {
				quantity = 1;
				
				service.addItems(product1,login,quantity,size,color,image);
			}

			
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);	
	
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		WishlistPOJO  product  = service.searchWishlistSelectSize(productId, customerId, color);
		WishlistPOJO updateWishlist = service.updateWishlistItem(product.getId(), size);
		cart = service.SearchCartItems2(customerId, productId, size,color);
		map.addAttribute("product1", cart);
		List<WishlistPOJO> wishlistProducts = service.searchWishlist(login.getId());
		map.addAttribute("wishlistProducts", wishlistProducts);
		map.addAttribute("userActive", "active");

		return "Wishlist";

	}
	@GetMapping("addtobag1")
	public String addToBag1(@RequestParam int size, @RequestParam int productId, @RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		
		int customerId = login.getId();
		PopularProductsAddPOJO product1 = service.searchProduct(productId);
		CartPOJO cart = service.SearchCartItems2(customerId, productId, size,color);
		int quantity = 0;
		String image ="";
		if(product1.getOrColour().equals(color)) {
			image = product1.getImage();
		}else if(product1.getBColour().equals(color)) {
			image = product1.getImage2();
		}else if(product1.getYColour().equals(color)) {
			image = product1.getImage3();
		}
		
		if (cart != null) {
			quantity = cart.getQuantity();
			quantity++;
		
			service.updateItem(quantity, cart.getId());
		} else {
			quantity = 1;
			
			service.addItems(product1,login,quantity,size,color,image);
		}
		
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		WishlistPOJO  product  = service.searchWishlistItemNotInCart(productId, customerId, size, color);
//		WishlistPOJO  product  = service.searchWishlistSelectSize(productId, customerId, color);
		WishlistPOJO updateWishlist = service.updateWishlistItem(product.getId(), size);
		cart = service.SearchCartItems2(customerId, productId, size,color);
		map.addAttribute("product1", cart);
		List<WishlistPOJO> wishlistProducts = service.searchWishlist(login.getId());
		map.addAttribute("wishlistProducts", wishlistProducts);
		map.addAttribute("userActive", "active");
		return "Wishlist";

	}
	// insert into wishlist from cart
	@GetMapping("cartWishlist")
	public String cartWishlist(@RequestParam int productId, @RequestParam int size, @RequestParam int previousSize,@RequestParam String color,
			@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {
		int customerId = login.getId();
		CustomerPOJO customer = service.searchCustomer(customerId);
		PopularProductsAddPOJO product = service.searchProduct(productId);
		WishlistPOJO searchWishlist = service.searchWishlistItemColor(productId, customerId, color,size);
		String image ="";
		if(product.getOrColour().equals(color)) {
			image = product.getImage();
		}else if(product.getBColour().equals(color)) {
			image = product.getImage2();
		}else if(product.getYColour().equals(color)) {
			image = product.getImage3();
		}
		if (searchWishlist == null) {
			WishlistPOJO list = service.addWishlist(customer, product, size,color,image);
		}else {
			WishlistPOJO wishNotAdded = service.wishNotAdded(searchWishlist.getId(),false,size);
			WishlistPOJO searchWishlistPrev = service.searchWishlistItemColor(productId, customerId, color,previousSize);
			 service.wishNotAdded(searchWishlistPrev.getId(),false,previousSize);
		}
		CartPOJO cart = service.SearchCartItems(customerId, productId, previousSize,color);
		CartPOJO removeItems = service.removeItems(cart.getId());
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
		map.addAttribute("cartItems", cartItems);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		return "SelectedCart";
	}

	@GetMapping("cartRemove")
	public String cartRemove(@RequestParam int productId, @RequestParam int size, @RequestParam String color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, ModelMap map) {
		int customerId = login.getId();
		CustomerPOJO customer = service.searchCustomer(customerId);
		PopularProductsAddPOJO product = service.searchProduct(productId);
		CartPOJO cart = service.SearchCartItems(customerId, productId, size,color);
		CartPOJO removeItems = service.removeItems(cart.getId());

		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
		map.addAttribute("cartItems", cartItems);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		WishlistPOJO searchWishlist = service.searchWishlistItemColor(productId, customerId, color,size);
		
//		WishlistPOJO searchWishlistPrev = service.searchWishlistItemColor(productId, customerId, color,previousSize);
//		 service.wishNotAdded(searchWishlistPrev.getId(),false,previousSize);
		if(searchWishlist != null) {
			WishlistPOJO wishNotAdded = service.wishNotAdded(searchWishlist.getId(),false,size);
		}
		return "SelectedCart";
	}

	@GetMapping("removeWishlist")
	public String removeWishlist(@RequestParam String ids,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login ,ModelMap map) {
		String [] getId = ids.split("&");
		if(!ids.isEmpty()) {
			for(String id : getId) {
			 service.removeWishlistItem(Integer.valueOf(id));
			}
		}
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<WishlistPOJO> wishlistProducts = service.searchWishlist(login.getId());
		map.addAttribute("wishlistProducts", wishlistProducts);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		map.addAttribute("userActive", "active");
		return "Wishlist";
	}

	
	@GetMapping("updateCartItems")
	public String updateCartItems(@RequestParam int[] ids,@RequestParam int[] size,@RequestParam int[] bagQuantity,@RequestParam int[] productId,@RequestParam String[] color,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {
		int customerId = login.getId();
		CustomerPOJO customer = service.searchCustomer(customerId);
		
		for(int i = 0;i<ids.length;i++) {
			CartPOJO getProductById = service.getProductByCartID(ids[i]);

			if(getProductById != null) {
			List<CartPOJO> searchCartItemPresent = service.SearchCartItemsDupli(customerId,getProductById.getProducts().getId() , size[i], getProductById.getColor());
			System.out.println("REEEEEEEEEEEE "+searchCartItemPresent);
			if(searchCartItemPresent.isEmpty()) {
				CartPOJO updatedCartItems =	service.updateCartItemsBySizeQuantity(ids[i],size[i],bagQuantity[i]);
			}

				for(CartPOJO item:searchCartItemPresent) {
					if(ids[i] != item.getId()) {
						bagQuantity[i] += item.getQuantity();
					//	WishlistPOJO wishNotAdded = service.wishNotAdded(searchWishlist.getId(),false,size);
						WishlistPOJO searchWishlistPrev = service.searchWishlistItemColor(getProductById.getProducts().getId(), customerId, getProductById.getColor(),getProductById.getSize());
						System.out.println("rohan + " + searchWishlistPrev);
						System.out.println("rohan id " + item.getId());
						if(searchWishlistPrev != null) {
							 service.wishNotAdded(searchWishlistPrev.getId(),false,getProductById.getSize());
						}
					
						service.removeItems(item.getId());
					}
				}
				CartPOJO updatedCartItems =	service.updateCartItemsBySizeQuantity(ids[i],size[i],bagQuantity[i]);


		 }
		}
		System.out.println("Reeeeeeeeeeee");
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
		map.addAttribute("cartItems", cartItems);
		List<PopularProductsAddPOJO> products = service.viewPopularProducts();
		map.addAttribute("productList", products);
		map.addAttribute("userActive", "active");
		return "SelectedCart";
		
	}
	
	
	
	// Login Controller
	@GetMapping("loginPage")
	public String loginPage(@RequestParam int pageId,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int id, @RequestParam int size,@RequestParam String color,ModelMap map,Model model) {
		
		
		map.addAttribute("pageId",pageId);
		map.addAttribute("otherActive",otherActive);
		map.addAttribute("sortActive",sortActive);
		map.addAttribute("id",id);
		System.out.println("Cuuif cig  "+size);
		map.addAttribute("size",size);
		map.addAttribute("color",color);
		return "Login";
	}
	// Login Controller
	@GetMapping("login")
	public String login(@RequestParam String email,@RequestParam String password,@RequestParam int pageId,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int id, @RequestParam int size,@RequestParam String color,@RequestParam int loginPageReturn, HttpServletRequest request,ModelMap map) {
		
		CustomerPOJO admin = customerService.login(email,password);
		if(admin != null) {
			// Success response
			HttpSession session = request.getSession();
			session.setAttribute("login", admin);
			
			map.addAttribute("userActive", "active");
			map.addAttribute("loginMsg", "Log In Successfully..!! ");
			
			List<Object[]> cartItems = service.viewCartItems(admin.getId());
			map.addAttribute("cartItems", cartItems);
			List<CartPOJO> AllcartItems = service.AllcartItem();
			ArrayList cartItemProd = cartItems(AllcartItems);
			map.addAttribute("cartItem", cartItemProd);
			int totalQuantity = service.cartTotal(admin.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			
		}else {
			// Failure response
			map.addAttribute("msg","Invalid username or password");
			if(loginPageReturn == 0) {
				map.addAttribute("pageId",pageId);
				map.addAttribute("otherActive",otherActive);
				map.addAttribute("sortActive",sortActive);
				map.addAttribute("id",id);
				map.addAttribute("size",size);
				map.addAttribute("color",color);
				map.addAttribute("totalQuantity", 0);
				return "Login";
			}else {
			map.addAttribute("loginPopup", "loginPopup");
			map.addAttribute("totalQuantity", 0);
			}
		}
		
	
		map.addAttribute("size", 0);
//		Long totalQuantity = service.cartTotalQuantity(admin.getId());
//		
		
		
		map.addAttribute("pageId", pageId);
		
		
		if(pageId == 0) {
			List<PopularProductsAddPOJO> products = service.getHomePageCards();
			map.addAttribute("productList",products);
			
			return "NavBar";
		}else if(pageId == 2) {

			if (size == 7) {
				map.addAttribute("Ssize", "Ssize");
			} else if (size == 8) {
				map.addAttribute("Msize", "Msize");
			} else if (size == 9) {
				map.addAttribute("Lsize", "Lsize");
			} else if (size == 10) {
				map.addAttribute("Xsize", "Xsize");
			}
			if (size != 0) {
				map.addAttribute("size", size);
			} else {
				map.addAttribute("size", 0);
			}
			PopularProductsAddPOJO product = service.searchProduct(id);
			if(product.getOrColour().equals(color)) {
				map.addAttribute("imgIndex", "0");
			}else if(product.getBColour().equals(color)) {
				map.addAttribute("imgIndex", "1");
			}else if(product.getYColour().equals(color)) {
				map.addAttribute("imgIndex", "2");
			}
			map.addAttribute("color", color);
			
			map.addAttribute("product", product);
			List<PopularProductsAddPOJO>	 products = service.viewPopularProducts();
			map.addAttribute("productList", products);
			int customerId = 12;
			 CustomerPOJO customer = service.searchCustomer(customerId);
			List<WishlistPOJO> wishlistProd = service.searchWishlistByProd(id, customerId); 
			String wishProd ="";
			for(WishlistPOJO item:wishlistProd) {
				if(!wishProd.isEmpty()) {
					wishProd += "&";
				}
			wishProd +=item.getColor() + "$" + item.getSize();
			}
			map.addAttribute("wishProd", wishProd);
			WishlistPOJO searchWishlist = service.searchWishlistItem(id, customerId, size);
			if (searchWishlist != null) {
				map.addAttribute("fillIcon", "solid");
			} else {
				map.addAttribute("fillIcon", "regular");
			}
			map.addAttribute("pageId", 2);
			map.addAttribute("id",id);
			return "Product";
		}
		List<PopularProductsAddPOJO> products = fillterAdding(otherActive,sortActive, map);
		map.addAttribute("productList", products);
		return "PopularProductsView";
	}
	
	
	@GetMapping("/signupPage")
	public String signupPage(@RequestParam int pageId,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int id, @RequestParam int size,@RequestParam String color,ModelMap map) {
		map.addAttribute("pageId",pageId);
		map.addAttribute("otherActive",otherActive);
		map.addAttribute("sortActive",sortActive);
		map.addAttribute("id",id);
		map.addAttribute("size",size);
		map.addAttribute("color",color);
		return "Signup";
	}
	
	// Create Admin Data Controller
	@GetMapping("/signup")
	public String signup(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam String password,@RequestParam int pageId,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int id, @RequestParam int size,@RequestParam String color,ModelMap map) {
		System.out.println("paratha sfs " + size);
		CustomerPOJO verifyEmail = customerService.SearchEmail(email);
		map.addAttribute("pageId", pageId);
		map.addAttribute("otherActive",otherActive);
		map.addAttribute("sortActive",sortActive);
		map.addAttribute("id",id);
		map.addAttribute("size",size);
		map.addAttribute("color",color);
		if(verifyEmail == null) {
			CustomerPOJO admin = customerService.addAdmin(firstName,lastName,email,password,null);
			if (admin != null) {
				// Success response
				map.addAttribute("msg", "Account created successfully..!!");
			
				return "Login";
			}
		}
		// Failure response
				map.addAttribute("msg", "Account not created Email is Already Registered..!!");
				return "Signup";
		
	}
	
	// Logout Controller
		@GetMapping("/logoutPage")
		public String logout(@RequestParam int pageId,@RequestParam String otherActive,@RequestParam String sortActive,@RequestParam int id, @RequestParam int size,@RequestParam String color,ModelMap map,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login, HttpSession session) {

			map.addAttribute("size", 0);

			map.addAttribute("totalQuantity", 0);
			map.addAttribute("pageId", pageId);
			map.addAttribute("loginMsg", "Logged out Successfully..!! ");
			
			session.invalidate();
			if(pageId == 0) {
				List<PopularProductsAddPOJO> products = service.getHomePageCards();
				map.addAttribute("productList",products);	
				return "NavBar";
			}else if(pageId == 2) {

				if (size == 7) {
					map.addAttribute("Ssize", "Ssize");
				} else if (size == 8) {
					map.addAttribute("Msize", "Msize");
				} else if (size == 9) {
					map.addAttribute("Lsize", "Lsize");
				} else if (size == 10) {
					map.addAttribute("Xsize", "Xsize");
				}
				if (size != 0) {
					map.addAttribute("size", size);
				} else {
					map.addAttribute("size", 0);
				}
		//		int totalQuantity = service.cartTotal();
		//		map.addAttribute("totalQuantity", totalQuantity);
				PopularProductsAddPOJO product = service.searchProduct(id);
				if(product.getOrColour().equals(color)) {
					map.addAttribute("imgIndex", "0");
				}else if(product.getBColour().equals(color)) {
					map.addAttribute("imgIndex", "1");
				}else if(product.getYColour().equals(color)) {
					map.addAttribute("imgIndex", "2");
				}
				map.addAttribute("color", color);
				
				map.addAttribute("product", product);
				List<PopularProductsAddPOJO>	 products = service.viewPopularProducts();
				map.addAttribute("productList", products);
		
				map.addAttribute("pageId", 2);
				map.addAttribute("id",id);
				return "Product";
			}
			List<PopularProductsAddPOJO> products = fillterAdding(otherActive,sortActive, map);
			map.addAttribute("productList", products);
			return "PopularProductsView";
		}
		// checkout Controller
				@GetMapping("/checkout")
				public String checkout(ModelMap map,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login) {
					map.addAttribute("totalQuantity", 0);
					if(login != null) {
						List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
						map.addAttribute("cartItems", cartItems);
						int totalQuantity = service.cartTotal(login.getId());
						map.addAttribute("totalQuantity", totalQuantity);
						map.addAttribute("userActive", "active");
						long subtotal = 1250;
						for (CartPOJO items : cartItems) {	
							subtotal += items.getProducts().getPrice() * items.getQuantity();
						}
						map.addAttribute("subtotal", subtotal);
						return "Checkout";
					}
					List<PopularProductsAddPOJO> products = service.getHomePageCards();
					map.addAttribute("productList",products);
					map.addAttribute("size", 0);	
					map.addAttribute("pageId", 0);
					return "/NavBar";
					
				}
				
				
	// 	create order
	@GetMapping("/createOrder")	
	public String createOrder(@RequestParam String firstName,@RequestParam String lastName,@RequestParam String email,@RequestParam long phone,@RequestParam String address,@RequestParam long postalcode,@RequestParam String locality,@RequestParam String state,@RequestParam String country,@RequestParam long amount,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) throws RazorpayException {
		map.addAttribute("totalQuantity", 0);
		if(login != null) {
		
		
		RazorpayClient razorpay = new RazorpayClient("rzp_test_hIzNEXTrDELfJo", "EEyqPyDxtJbDGBtxN0tOir8q");
		JSONObject orderRequest = new JSONObject();
		orderRequest.put("amount",amount*100);
		orderRequest.put("currency","INR");
		orderRequest.put("receipt", "receipt#1");
		
//		JSONObject notes = new JSONObject();
//		notes.put("notes_key_1","Tea, Earl Grey, Hot");
//		orderRequest.put("notes",notes);

		Order order = razorpay.orders.create(orderRequest);
		String orderId = order.get("id");
		
		List<CartPOJO> cartItems = service.viewCartItemsAll(login.getId());
		for(CartPOJO cp : cartItems) {
			CartAdminPOJO searchCartItemsAdmin = service.searchStoreCartPresent(cp);
			if(searchCartItemsAdmin == null) {
				CartAdminPOJO cartItemsOrder = service.storeCartTOAdmin(cp);
			}
		}

		List<CartAdminPOJO> cartItemsAdmin = service.getCartItemsAdmin(cartItems);
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

		Date createdAt = (Date) order.get("created_at"); 

		String date = dateFormat.format(createdAt);
		String time = timeFormat.format(createdAt);

		OrdersPOJO userOrder = service.createOrder(firstName,lastName,email,phone,address,postalcode,locality,state,country,amount,date,time,orderId,cartItemsAdmin,login);
		map.addAttribute("cartItems", cartItems);
		int totalQuantity = service.cartTotal(login.getId());
		map.addAttribute("totalQuantity", totalQuantity);
		map.addAttribute("userActive", "active");
		map.addAttribute("subtotal", amount);
		map.addAttribute("orderId", orderId);
		
		return "Checkout";
		}
		List<PopularProductsAddPOJO> products = service.getHomePageCards();
		map.addAttribute("productList",products);
		map.addAttribute("size", 0);	
		map.addAttribute("pageId", 0);
		return "/NavBar";
		
	}
	
	// Razor pay payment callback
	// 	create order
	@GetMapping("/paymentCallback")	
	public String paymentCallback(@RequestParam String payment_id ,@RequestParam String order_id,ModelMap map,@SessionAttribute(name = "login" ,required = false) CustomerPOJO login) {

		if(login != null) {
			OrdersPOJO order = service.getOrder(order_id);
			service.updatePaymentdetails(payment_id,order.getId()); // from ordersPOJO
			 order = service.getOrder(order_id);
			for(CartAdminPOJO cart:order.getCart()) {
				CartPOJO cartItem = service.searchCartItemById(cart.getCartId());
				if(cartItem != null) {
				service.removeItems(cart.getCartId()); // from cartPOJO
				}
			}
			List<OrdersPOJO> orders = service.getPaidOrder(login.getId());
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			map.addAttribute("userActive", "active");
			map.addAttribute("order", order);
			map.addAttribute("orders", orders);
			return "Orders";
		}
		
		List<PopularProductsAddPOJO> products = service.getHomePageCards();
		map.addAttribute("productList",products);
		map.addAttribute("size", 0);	
		map.addAttribute("pageId", 0);
		map.addAttribute("totalQuantity", 0);
		return "/NavBar"; 
	}
	
	//order Controller
	@GetMapping("/order")	
	public String order(@SessionAttribute(name = "login" ,required = false) CustomerPOJO login,ModelMap map) {
		if(login != null) {
			List<OrdersPOJO> order = service.getPaidOrder(login.getId());
			int totalQuantity = service.cartTotal(login.getId());
			map.addAttribute("totalQuantity", totalQuantity);
			map.addAttribute("userActive", "active");
			map.addAttribute("orders", order);
			return "Orders";
		}
	
		List<PopularProductsAddPOJO> products = service.getHomePageCards();
		map.addAttribute("productList",products);
		map.addAttribute("size", 0);	
		map.addAttribute("pageId", 0);
		map.addAttribute("totalQuantity", 0);
		map.addAttribute("loginPopup", "loginPopup");
		return "/NavBar"; 
	}
	

	
// AdminLogin controller	
	@PostMapping("/AdminLogin")
	public String AdminLogin(@RequestParam String userID,@RequestParam String password,HttpServletRequest request,ModelMap map) {
		AdminPOJO admin = adminService.login(userID,password);
		if(admin != null) {
			// Success response
			HttpSession sessionA = request.getSession();
			sessionA.setAttribute("adminlogin", admin);
			List<OrdersPOJO> allOrders = service.allOrders();
			Long totalAmount = service.totalAmount();
			double totalBudget = service.totalBudget();
			map.addAttribute("totalBudget",totalBudget);
			map.addAttribute("totalAmount",totalAmount);
			map.addAttribute("allOrders",allOrders);
			return "/AdminDashborad";
		
		}
		map.addAttribute("msg","No user Found..!!!");
		return "/AdminLogin";
	}
	

		@GetMapping("/adminLogout")
		public String adminLogout(ModelMap map, HttpSession adminSession) {
			map.addAttribute("msg", "Logged out Successfully..!! ");
			adminSession.invalidate();
			return "/AdminLogin";
		}
	
	
	@GetMapping("/dashborad")
	public String AdminDashborad(@SessionAttribute(name = "adminlogin" ,required = false) AdminPOJO adminlogin,ModelMap map) {
		if(adminlogin != null) {
			List<OrdersPOJO> allOrders = service.allOrders();
			Long totalAmount = service.totalAmount();
			double totalBudget = service.totalBudget();
			map.addAttribute("totalBudget",totalBudget);
			map.addAttribute("totalAmount",totalAmount);
			map.addAttribute("allOrders",allOrders);
			return "/AdminDashborad";
		}
		return "/AdminLogin";
		 
	}
	
	// Order Details Controller
	@GetMapping("/details")
	public String details(int orderId,@SessionAttribute(name = "adminlogin" ,required = false) AdminPOJO adminlogin,ModelMap map) {
		if(adminlogin != null) {
			OrdersPOJO order = service.order(orderId);
			map.addAttribute("orderDetail",order);
			Long totalAmount = service.totalAmount();
			map.addAttribute("totalAmount",totalAmount);
			List<OrdersPOJO> allOrders = service.allOrders();
			map.addAttribute("allOrders",allOrders);
			double totalBudget = service.totalBudget();
			map.addAttribute("totalBudget",totalBudget);
			return "/AdminDashborad";
		}
		return "/AdminLogin";
	}
	
	
	//	Add products controller
	@PostMapping("/addPopularProducts")
	public String addPopularProduct(
	        @RequestParam String productType, @RequestParam String name, @RequestParam String description,
	        @RequestParam String brand, @RequestParam int sSize, @RequestParam int mSize, @RequestParam int lSize,
	        @RequestParam int xSize, @RequestParam String orColor, @RequestParam String bColor,
	        @RequestParam String yColor, @RequestParam double price, @RequestParam int quantity,
	        @RequestParam String gender, @RequestParam String sale, @RequestParam String newest,
	        @RequestParam("images") CommonsMultipartFile file, @RequestParam("image2") CommonsMultipartFile file2,@RequestParam("image3") CommonsMultipartFile file3,
	        HttpSession session,@SessionAttribute(name = "adminlogin" ,required = false) AdminPOJO adminlogin, ModelMap map) {
		if(adminlogin != null) {
		
	    handleFileUpload(file, session, map);
	    handleFileUpload(file2, session, map);
	    handleFileUpload(file3, session, map);
	    
	    PopularProductsAddPOJO product = service.addPopularProduct(productType, name, description, brand, sSize, mSize, lSize, xSize,
	            orColor, bColor, yColor, price, quantity, gender, sale, newest, file.getOriginalFilename(), file2.getOriginalFilename(), file3.getOriginalFilename());
	    
			List<OrdersPOJO> allOrders = service.allOrders();
			Long totalAmount = service.totalAmount();
			double totalBudget = service.totalBudget();
			map.addAttribute("totalBudget",totalBudget);
			map.addAttribute("totalAmount",totalAmount);
			map.addAttribute("allOrders",allOrders);
			return "/AdminDashborad";
		}
		return "/AdminLogin";
	}

	private void handleFileUpload(CommonsMultipartFile file, HttpSession session, ModelMap map) {
	    byte[] data = file.getBytes();
	    String path = session.getServletContext().getRealPath("/") + "WEB-INF" + File.separator + "resource"
	            + File.separator + "assests" + File.separator + file.getOriginalFilename();
	    System.out.println(path);

	    try {
	        FileOutputStream fos = new FileOutputStream(path);
	        fos.write(data);
	        fos.close();
	        map.addAttribute("msg", "File uploaded");
	        map.addAttribute("filename", file.getOriginalFilename());
	    } catch (IOException e) {
	        e.printStackTrace();
	        map.addAttribute("msg", "Uploading error");
	    }
	}

	
}
