<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
List<PopularProductsAddPOJO> products = (List<PopularProductsAddPOJO>) request.getAttribute("productList");
int totalQuantity = (int) request.getAttribute("totalQuantity");
String selectSizeMsg = (String) request.getAttribute("selectSizeMsg");
Integer selectedProductId = (Integer) request.getAttribute("selectedProductId");
String getsize = (String) request.getAttribute("getsize");
String cross = "cross";
List<Object[]> cartItems = (List<Object[]>) request.getAttribute("cartItems");
ArrayList<String> cartItem = (ArrayList) request.getAttribute("cartItem");
int size = (int) request.getAttribute("size");
CartPOJO cart = (CartPOJO) request.getAttribute("cart");
Integer customerId = (Integer) request.getAttribute("customerId");
String loginPopup = (String) request.getAttribute("loginPopup");
String msg1 = (String) request.getAttribute("msg");
String userActive = (String) request.getAttribute("userActive");
String loginMsg = (String) request.getAttribute("loginMsg");
int pageId = (int) request.getAttribute("pageId");
String confrim_order = (String) request.getAttribute("confrim_order");
%>

<!DOCTYPE html>
<html lang="en">
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>NIKE</title>
<link rel="stylesheet" href="<c:url value='/resource/css/style.css'/>">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

<style>


</style>
</head>

<body>

	<!-- nav bar -->
	<div class="body overlay " id="body"></div>
	<nav>
		<div class=" main-nav flex">
			  
			<a href="./" class="company-logo"> 
				<img src="./resource/assests/black-and-white-brand-pattern-nike-logo-png-f10610cd223765f9cb76402f10be85ca.png" alt="Nike.just do it">
			</a>
			<div class="nav-links nav-links-active">
				<ul class="flex">
					<li>
						<a href="./sort?sortBy=Newest&otherActive=null" class="hover-link">New Releases</a>
					</li>
					<li>
						<a href="./menFillter?gender=male&active=null&otherActive=null&sortActive=null" class="hover-link">Men</a>
					</li>
					<li>
						<a href="./menFillter?gender=female&active=null&otherActive=null&sortActive=null" class="hover-link">Woman</a>
					</li>
					<li>
						<a href="./view" class="hover-link">collections</a>
					</li>
					<li>
						<a href="./order" class="hover-link">Orders</a>
					</li>
					<%
					if (userActive != null) {
					%>
					<li>
						<a href="./logoutPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=0&size=0&color=null" class="hover-link">Sign Out</a>
					</li>
					<%
					} else {
					%>
					<li>
						<a href="./loginPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=0&size=0&color=null" class="hover-link">Sign In</a>
					</li>
					<%
					}
					%>
				</ul>
			</div>
			<div class="search flex">
				<a href="#" class="search-icon hover-link"> 
					<i class="fa-solid fa-magnifying-glass"></i>
				</a>
				<div class="input">
					<input type="text" placeholder="Search" id="mysearch">
				</div>
			</div>
			<div class="toggle-add-wrapper">	
			<div class="add flex">
				<a href="./wishlist?otherActive=null&sortActive=null&pageId=<%=pageId%>&id=0&size=0&color=null" class="search-like hover-link">
			   		 <i class="fa-regular fa-heart"></i>
				</a> 
				<a href="./selectedCart?otherActive=null&sortActive=null&pageId=<%=pageId%>&id=0&size=0&color=null" class="search-bag hover-link">
					<div class="cart">
							<i class="fa-solid fa-bag-shopping"></i>
							<div id="cartAmount" class="cartAmount"><%=totalQuantity > 0 ? totalQuantity : ""%></div>
					</div>
				</a>
			</div>
			<a href="#" class="nav-toggle hover-link" id="nav-toggle">
				 <i class="fa-solid fa-bars"></i>
			</a>
			</div>
		</div>
	</nav>

	<!-- Header -->
	<header>
		<div class="container header-section flex ">
			<div class="header-left ">
				<h3>Nike Mercurial</h3>
				<h1>Vapor XI FG</h1>
				<p>
					The Men's Nike Mercurial Vapor xI(FG)Firm-Ground Football <br>
					Boot combines a lightweight contoured nylon plate.
				</p>
				<span><s>&#8377;210</s></span> <span>&#8377;170</span> 
				<a href="./product?id=2&size=0&color=red" class="primary-button get-this-product">Get this product</a>
			</div>
			<div class="header-right flex ">
				<div class="circle-container flex ">
					<div class="circles   "></div>
					<div class="circle1  "></div>
				</div>
				<div class="imgbx">
					<a href="./product?id=2&size=0&color=red"> 
					<img src="./resource/assests/imgbin_football-boots-png.png" alt="hero-img" class="active">
					</a>
				</div>
				<div class="rating">
					<div class="stars flex">
						<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
						<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
						<span> 
						<i class="fa-solid fa-star"></i>
						</span>
					</div>
					<p>4.5 out of 5</p>
				</div>
			</div>
		</div>
	</header>
	<%
	if (selectSizeMsg != null) {
	%>
	<div class="msg">
		<p class="danger1"><%=selectSizeMsg%></p>
	</div>
	<%
	}
	%>
	<%
	if (loginMsg != null) {
	%>
	<div class="msg login-msg">
		<p class="danger1"><%=loginMsg%></p>
	</div>
	<%
	}
	%>
	<!-- cards -->
	<section class="cards-section container ">
		<div class="container flex text">
			<h1>
				Popular <span>Products</span>
			</h1>
			<div class="slider-btns flex">
				<div class="box1">
					<i id="left" class="fa-solid fa-angle-left"></i>
				</div>
				<div class=" box1">
					<i id="right" class="fa-solid fa-angle-right"></i>
				</div>
			</div>
		</div>
		<div class="cards-section-wrapper  flex">
			<div class=" card-container " id="card-container">
				<%
				for (PopularProductsAddPOJO pojo : products) {
				%>
				<div class="card">
					<form action="./product">
						<input hidden="true" type="text" name="id" value="<%=pojo.getId()%>"> 
						<input hidden="true" type="text" name="size" value="<%=size%>">
						<input hidden="true" class="buttons-color" type="text" name="color" value=""> 
						<input class="product-submit-btn" type="submit" value="Submit">
						<div class="imgBox">
							<%
							if (pojo.getImage() != null) {
							%>
							<img class="imgBoxDisplay mainImg" src="./resource/assests/<%=pojo.getImage()%>" alt="cleats">
							<%
							}
							%>
							<%
							if (pojo.getImage2() != null) {
							%>
							<img class="imgBoxDisplay" src="./resource/assests/<%=pojo.getImage2()%>" alt="cleats">
							<%
							}
							%>
							<%
							if (pojo.getImage3() != null) {
							%>
							<img class="imgBoxDisplay" src="./resource/assests/<%=pojo.getImage3()%>" alt="cleats">
							<%
							}
							%>
						</div>
					</form>
					<div class="contentBox">
						<h2><%=pojo.getType()%></h2>
						<%
						if (selectSizeMsg != null && selectedProductId == pojo.getId()) {
						%>
						<p class="danger "><%=selectSizeMsg%></p>
						<%
						}
						%>
						<p class="sizeMsg"></p>
						<input hidden="true" type="number" name="pageId" value="0">
						<input hidden="true" type="number" name="productId" value="<%=pojo.getId()%>">
						<div class="size ">
							<h3>Sizes:</h3>
							<%
							if (selectSizeMsg != null && selectedProductId == pojo.getId()) {
							%>
							<div class="size-border <%=getsize%> flex">
								<%
								} else {
								%>
								<div class="size-border flex">
									<%
									}
									%>
									<%
									if (pojo.getSize7() == 0) {
									%>
									<div class="size-cross">
										<input class="<%=cross%>" type="submit" value="7" name="size">
										<i class="fa-solid fa-x"></i>
									</div>
									<%
									} else {
									%>
									<input  type="submit" value="<%=pojo.getSize7()%>" name="size">
									<%
									}
									%>
									<%
									if (pojo.getSize8() == 0) {
									%>
									<div class="size-cross">
										<input class="<%=cross%>" type="submit" value="8" name="size">
										<i class="fa-solid fa-x"></i>
									</div>
									<%
									} else {
									%>
									<input  type="submit" value="<%=pojo.getSize8()%>" name="size">
									<%
									}
									%>
									<%
									if (pojo.getSize9() == 0) {
									%>
									<div class="size-cross">
										<input class="<%=cross%>" type="submit" value="9" name="size">
										<i class="fa-solid fa-x"></i>
									</div>
									<%
									} else {
									%>
									<input  type="submit" value="<%=pojo.getSize9()%>" name="size">
									<%
									}
									%>
									<%
									if (pojo.getSize10() == 0) {
									%>
									<div class="size-cross">
										<input class="<%=cross%>" type="submit" value="10" name="size">
										<i class="fa-solid fa-x"></i>
									</div>
									<%
									} else {
									%>
									<input  type="submit" value="<%=pojo.getSize10()%>" name="size">
									<%
									}
									%>
								</div>
							</div>
							<div class="colour">
								<h3>Colour:</h3>
								<%
								if (pojo.getOrColour() != null) {
								%>
								<span class="selected <%=pojo.getOrColour()%>"></span>
								<%
								}
								%>
								<%
								if (pojo.getBColour() != null) {
								%>
								<span class="<%=pojo.getBColour()%>"></span>
								<%
								}
								%>
								<%
								if (pojo.getYColour() != null) {
								%>
								<span class="<%=pojo.getYColour()%>"></span>
								<%
								}
								%>
							</div>
							<div class="price">
								<h3>Price:$<%=pojo.getPrice()%></h3>
							</div>
							<div class="buttons">
								<input hidden="true" type="number" name="size" value="<%=size%>">
								<input hidden="true" type="number" name="id" value=<%=pojo.getId()%>>
								<input hidden="true" type="text" name="btnId" value="0">
								 <input type="submit" value="-" class="btn">
								<form action="./addCart">
									<input hidden="true" type="number" name="pageId" value="0">
									<input hidden="true" type="number" name="id" value=<%=pojo.getId()%>>
									<input hidden="true" class="buttons-size" type="number" name="size" value="0">
									 <input hidden="true" class="buttons-color" type="text" name="color" value="">
									<input hidden="true" class="buttons-quantity" type="text" name="prodQuantity" value="1">
									 <input hidden type="text" name="otherActive" value="null"> 
									 <input hidden type="text" name="sortActive" value="null"> 
									 <input type="submit" value="Add To Cart" class="addcart">
								</form>
								<input hidden="true" type="number" name="size" value="<%=size%>">
								<input hidden="true" type="number" name="id" value=<%=pojo.getId()%>>
								 <input hidden="true" type="text" name="btnId" value="1">
								 <input type="submit" name="increment" value="+" class="btn">
							</div>
							<div class="quantityMsg"></div>
							<input hidden="true" class="prodQuantity" type="text" name="" value="<%=pojo.getQuantity()%>">
							<%
							if (cartItem != null) {
							%>
							<%
							for (String item : cartItem) {
								int i = item.indexOf("%");
								String ProdId = item.substring(0, i);
								int id = Integer.parseInt(ProdId);
							%>
							<%
							if (pojo.getId() == id) {
							%>
							<input hidden="true" class="productDetails" type="text" name=""
								value="<%=item%>">
							<%
							}
							%>
							<%
							}
							%>
							<%
							}
							%>
							<%
							if (cartItems != null) {
							%>
							<%
							for (Object[] items : cartItems) {
								if (items[0] == (Integer) pojo.getId()) {
							%>
							<div id="<%=pojo.getId()%>" class="quantity"><%=items[1]%></div>
							<p class="stock">
								InStock Only
								<%=pojo.getQuantity()%>
								Left
							</p>
							<%
							}
							}
							%>
							<%
							}
							%>
						</div>
					</div>
					<%
					}
					%>
					<%
					if (products.isEmpty()) {
					%>
					<h1>There no such products available please deselect</h1>
					<%
					}
					%>
				</div>
	</section>
	<!-- Popup -->
	<%
	if (cart != null) {
	%>
	<div class="popup" id="popup">
		<div class="popup-container ">
			<div class="popup-top flex">
				<div class="popup-top-img flex">
					<img src="./resource/assests/pngwing.com (3).png" alt="green tick" width="20px">
					<p>Added to Bag</p>
				</div>
				<div class="close-icon">
					<i class="bi bi-x-lg"></i>
				</div>
			</div>
			<div class="popup-content flex">
				<img src="./resource/assests/<%=cart.getImage()%>" alt="green tick" width="100px">
				<div class="popup-text">
					<p><%=cart.getProducts().getName()%></p>
					<p>Men's Football Shoes</p>
					<p>Size UK <%=cart.getSize()%></p>
					<p>MRP : $ <%=cart.getProducts().getPrice() %></p>
				</div>
			</div>
			<div class="popup-btn flex">
				<form action="./selectedCart">
					<input hidden type="text" name="otherActive" value="null">
					<input hidden type="text" name="sortActive" value="null">
					<input hidden type="number" name="pageId" value="<%=pageId%>">
					<input hidden type="number" name="id" value="0"> 
					<input hidden type="number" name="size" value="0">
					<input hidden type="text" name="color" value="null">
					<input class="popup-btn-input" type="submit" name="view-bag" value="View Bag (8)">
				</form>
				<form action="./checkout">
					<input class="popup-btn-input" type="submit" name="Checkout" value="Checkout">
				</form>
			</div>
		</div>
	</div>
	<%
	}
	%>
	<!-- About us -->
	<section class="about-us">
		<div class="container about-us-container flex">
			<div class="left-card">
				<div class="imgbox">
					<div class="rotate">
						<img src="./resource/assests/pngwing.com (6) (1) (1) (1) (1).png" alt="">
					</div>
				</div>
				<div class="offer-card">
					<h4>
						Get up to <br> 30% off
					</h4>
					<p>You can get up to 30 percent discount from here</p>
				</div>
			</div>
			<div class="right-card">
				<p>About Us</p>
				<h1>
					We Provide <span>High</span><br>Quality Shoes.
				</h1>
				<p>Lorem ipsum dolor, sit amet consectetur adipisicing elit.
					Delectus in id totam doloribus vel aliquam quibusdam, illum
					praesentium deleniti placeat.</p>
				<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
					Nobis, dolorum.</p>
				<a href="./view" class="primary-button explore-more-button flex">Explore
					More <i class="fa-solid fa-circle-arrow-right"></i>
				</a>
			</div>
		</div>
	</section>
	<!-- Product details  -->
	<section class="product-details">
		<div class="container product-detail flex">
			<div class="left-text">
				<p>Product Details</p>
				<h1>
					Get to Know Our <br>Feature <span>Product</span>
				</h1>
				<p>Lorem, ipsum dolor sit amet consectetur adipisicing elit.
					Magnam sunt itaque ad ab eos nostrum doloremque assumenda repellat
					quam aliquid.</p>
				<div class="content flex">
					<div class="icon">
						<i class="fa-solid fa-award icon-img"></i>
					</div>
					<div class="card-text">
						<h4>Best Quality Shoes</h4>
						<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit.
							Aperiam, nam.</p>
					</div>
				</div>
				<div class="content flex">
					<div class="icon">
						<i class="fa-solid fa-hand-holding-dollar"></i>
					</div>
					<div class="card-text">
						<h4>Best Giving Pricing</h4>
						<p>Lorem ipsum dolor sit, amet consectetur adipisicing elit.
							Aperiam, nam.</p>
					</div>
				</div>
			</div>
			<div class="right-card">
				<div class="img-box">
					<img src="./resource/assests/box1-Hnucpr9Ij-transformed.png" alt="">
				</div>
				<div class="img-box2 ">
					<h3>Nike Mercurial Vapor M15</h3>
					<span>&#8377;210</span><span>&#8377;170</span> <a href="#"
						class="search-bag hover-link"> <i
						class="fa-solid fa-bag-shopping"></i>
					</a>
				</div>
			</div>
		</div>
	</section>
	<!-- Customer Details -->
	<section class="customer-detail">
		<div class="container customer-details">
			<div class="heading">
				<h1>
					What Our <br> <span>Customer Says</span>
				</h1>
				<div class="controls flex">
					<div class="box">
						<i id="left" class="fa-solid fa-arrow-left"></i>
					</div>
					<div class="box">
						<i id="right" class="fa-solid fa-arrow-right"></i>
					</div>
				</div>
			</div>
			<div class="user-details flex">
				<div class="user-box flex">
					<img
						src="./resource/assests/Screenshot_2023-07-28_204239-transformed1.png"
						alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Remington</h2>
				</div>
				<div class="user-box flex">
					<img src="./resource/assests/1000_F_185048418_X1kohHSgyAbPJQxPHurs4uXCTmcRSNAp.jpg" alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Thomson Zarki</h2>
				</div>
				<div class="user-box flex">
					<img src="./resource/assests/img-3.jpg" alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Lariach Freach</h2>
				</div>
				<div class="user-box flex">
					<img src="./resource/assests/img-4.jpg" alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Joenas Brauers</h2>
				</div>
				<div class="user-box flex">
					<img src="./resource/assests/img-5.jpg" alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Kristina Zasiadko</h2>
				</div>
				<div class="user-box flex">
					<img src="./resource/assests/img-2.jpg" alt="">
					<p>Lorem ipsum dolor sit amet consectetur adipisicing elit.
						Voluptatibus minima, dolore dignissimos eligendi id repellat
						suscipit natus impedit ullam maxime ducimus expedita quisquam,
						tempore ipsam quasi saepe, nisi adipisci reiciendis.</p>
					<div class="rating">
						<div class="stars flex">
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i> <i class="fa-solid fa-star"></i>
							<i class="fa-solid fa-star"></i>
						</div>
					</div>
					<h2>Donald Horton</h2>
				</div>
			</div>
		</div>
	</section>
	<!-- Newsletter -->
	<section class="newsletter">
		<div class="container newsletters flex">
			<h1>
				Sign Up for <span>Updates</span> <br> & Newsletter
			</h1>
			<div class="email">
				<input type="email" placeholder="Type Your Email"> <a
					href="#" class="primary-button">Subscirbe Now</a>
			</div>
		</div>
	</section>


	<!-- login popup -->
	<%
	if (loginPopup != null) {
	%>
	<div class="details login-popup">
		<div class="info flex">
			<h2>LOGIN</h2>
			<div class="close-icon">
				<i class="bi bi-x-lg"></i>
			</div>
		</div>
		<div>
		<a href="https://accounts.google.com/o/oauth2/auth?client_id=217084231230-aigo48lncb4amfnogq1sn5heuca154ol.apps.googleusercontent.com&redirect_uri=http://localhost:8080/mvcproject1/callback&scope=openid%20email%20profile&response_type=code">  
			<button class="google-btn">
				<img alt="" src="./resource/assests/icons8-google-48.png"> Log
				in with Google
			</button>
		</a>
		</div>
		<form action="./login">
			<input hidden="true" type="number" name="pageId" value="0">
			<input hidden type="number" name="id" value="0">
			<input hidden class="getSizeValue" type="number" name="size" value="0">
			<input hidden class="colorChoice" type="text" name="color" value="null">
			<input hidden type="text" name="otherActive" value="">
			<input hidden type="text" name="sortActive" value="">
			<input hidden type="number" name="loginPageReturn" value="1">
			<div class="input">
				<div class="input-field">
					<label>Email</label>
					<input id="email" type="email" placeholder="example123@gmail.com" name="email" class="input-area" required="required">
					<p></p>
				</div>
				<div class="input-field">
					<label>Password</label>
					 <input id="password" type="password" placeholder="Your password" name="password" class="input-area" required="required">
					<p></p>
				</div>
			</div>
			<div class="checkbox">
				<input type="checkbox"> <label>Remember me</label>
			</div>
			<input readonly type="text" class="login" value="Login In">
		</form>
		<div class="create">
			<div class="signup">
				<label>Don't have account yet?</label>
				 <a href="./signupPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=0&size=0&color=null">Sign up</a>
			</div>
			<div class="signup">
				<a>Forgot your password?</a>
			</div>
		</div>
		<%
		if (msg1 != null) {
		%>
		<h3 class="msg1"><%=msg1%></h3>
		<%
		}
		%>
	</div>
	<%
	}
	%>
	<!-- Footer -->
	<div class="hr"></div>
	<footer class="footer">
		<div class=" footer-bx container">
			<div class="content flex">
				<div class="box1">
					<img
						src="./resource/assests/black-and-white-brand-pattern-nike-logo-png-f10610cd223765f9cb76402f10be85ca.png"
						alt="">
					<p>Lorem, ipsum dolor sit amet consectetur adipisicing elit.
						Placeat, tempora?</p>
					<div class="icon-container flex">
						<a class="icon-bx"><i class="fa-brands fa-square-facebook"></i></a>
						<a class="icon-bx"><i class="fa-brands fa-instagram"></i></a> <a
							class="icon-bx"><i class="fa-brands fa-twitter"></i></a>
					</div>
				</div>
				<div class="box-wrapper">
				<div class="box">
					<h3>Find Product</h3>
					<a href="#">Brownze Arnold</a> <a href="#">Chronograph Blue</a> <a
						href="#">Smart Phones</a> <a href="#">Automatic Watch</a> <a
						href="#">Hair Straighteners</a>
				</div>
				<div class="box">
					<h3>Get help</h3>
					<a href="#">About Us</a> <a href="#">Contact Us</a> <a href="#">Return
						Policy</a> <a href="#">Privacy Policy</a> <a href="#">Payment
						Policy</a>
				</div>
				<div class="box">
					<h3>About Us</h3>
					<a href="#">News</a> <a href="#">Service</a> <a href="#">Our
						Policy</a> <a href="#">Customer Care</a> <a href="#">Faq's</a>
				</div>
				</div>
			</div>
		</div>
		<div class="hr"></div>
		<p>&copy; 2023 CreativePeoples.All rights reserved.</p>
	</footer>

	
	



	<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
	<script type="text/javascript" src="./resource/js/NavBar.js" defer></script>
	<!-- defer attribute tells the browser to wait until the page has finshied parshing before exeuting the script -->
	<script type="text/javascript">
	

	</script>
</body>
</html>