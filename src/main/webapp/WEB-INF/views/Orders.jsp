<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.CartAdminPOJO"%>
<%@page import="com.jspiders.mvcproject1.pojo.OrdersPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <%
int totalQuantity = (int) request.getAttribute("totalQuantity");
String userActive = (String) request.getAttribute("userActive");
List<OrdersPOJO> orders = (List<OrdersPOJO>) request.getAttribute("orders");
OrdersPOJO order = (OrdersPOJO) request.getAttribute("order");

%>
<!DOCTYPE html>
<html>
<head>
  
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="ISO-8859-1">
<title>Orders</title>
<link rel="stylesheet" href="<c:url value='/resource/css/Orders.css'/>">
<style type="text/css">

</style>
</head>
<body>
<!-- nav bar -->

	<div class="body overlay" id="body"></div>
	<nav>
		<div class=" main-nav flex">
			 <a href="./" class="company-logo"> <img
				src="./resource/assests/black-and-white-brand-pattern-nike-logo-png-f10610cd223765f9cb76402f10be85ca.png"
				alt="Nike.just do it">
			</a>
			<div class="nav-links nav-links-active">
				<ul class="flex">
					<li><a href="./sort?sortBy=Newest&otherActive=null" class="hover-link">New Releases</a></li>
					<li><a href="./menFillter?gender=male&active=null&otherActive=null&sortActive=null" class="hover-link">Men</a></li>
					<li><a href="./menFillter?gender=female&active=null&otherActive=null&sortActive=null" class="hover-link">Woman</a></li>
					<li><a href="./view" class="hover-link">collections</a></li>
						<%if(userActive != null){ %>
							<li><a href="./logoutPage?pageId=0&otherActive=null&sortActive=null&id=0&size=0&color=null" class="hover-link">Sign Out</a></li>
				<% 	}else{ %>
							<li><a href="./loginPage?pageId=0&otherActive=null&sortActive=null&id=0&size=0&color=null" class="hover-link">Sign In</a></li>
				<%	} %>
				</ul>
			</div>
			<div class="search flex">
				<a href="#" class="search-icon hover-link"> <i
					class="fa-solid fa-magnifying-glass"></i>
				</a>
				<div class="input">
					<input type="text" placeholder="Search" id="mysearch">
				</div>
			</div>
			<div class="toggle-add-wrapper">
			<div class="add flex">
				
					<a href="./wishlist?otherActive=null&sortActive=null&pageId=0&id=0&size=0&color=null" class="search-like hover-link"> <i
						class="fa-regular fa-heart"></i>
				
				</a> <a href="./selectedCart?otherActive=null&sortActive=null&pageId=0&id=0&size=0&color=null" class="search-bag hover-link">
					<div class="cart">
						<i class="fa-solid fa-bag-shopping"></i>
						<div id="cartAmount" class="cartAmount"><%=totalQuantity > 0 ? totalQuantity : ""%></div>
					</div>
				</a>
				<a href="#" class="nav-toggle hover-link" id="nav-toggle"> <i
				class="fa-solid fa-bars"></i>
			</a>
			</div>
			</div>
		</div>
	</nav>
		
	<div class="container main">
		<div class="heading"><h2>Order Details</h2></div>
		<%
		if(orders != null){ 
		%>
		
		<%
		for(OrdersPOJO or : orders){
		%>
		<div class="info flex">
		<div class="bag-content-wrapper">
		<% 	
		for (CartAdminPOJO items : or.getCart()) {	
		%>
		<div class="bag-content flex">
			<div class="bag-img ">
				<img src="./resource/assests/<%=items.getImage()%>" alt="green tick">
			</div>
			<div class="bag-text">
				<p><%=items.getProducts().getName()%></p>
				<p>Men's Football Shoes</p>
				<p>QTY <%=items.getQuantity()%></p>
				<p>Size <%=items.getSize() %></p>
				<p>Price &#8377; <%=items.getProducts().getPrice()%></p>
			</div>
		</div>
	
	<%}%>	
	</div>
		<div class="shipping-address ">
				<div class="flex details">
					<p>Order Id</p>
					<p><%=or.getOrderId()%></p>
				</div>
				<div class="flex details">
					<p>Payment Id</p>
					<p><%=or.getPaymentId() %></p>
				</div>
				<div class="flex details">
					<p>Email</p>
					<p><%=or.getEmail()%></p>
				</div>
				<div class="flex details">	
					<p>Phone</p>
					<p><%=or.getPhone()%></p>
				</div>	
				<div class="flex details">	
					<p>Amount Paid</p>
					<p>&#8377; <%=or.getAmount()%></p>
				</div>
				<div class="hr"></div>
				<div class="address">
				<h3>Shipping Address</h3>
					<p><%=or.getAddress() %></p>
					<p><%=or.getLocality() %></p>
					<p><%=or.getCountry() %></p>
					<p><%=or.getPostalCode() %></p>
				</div>	
				<div class="hr"></div>
				<div class="date-cart-products">
				<p class="date-text"></p>
				<input class="date-input" hidden type = "date" value = "<%=or.getDate()%>">
				</div>
		</div>
	</div>
	<div class="hr"></div>
	<%}%>
	
	<%}else{ %>
	<h1>You have No orders Present.</h1>	
	<%} %>
	</div>
	
	<%if(order != null){ %>
		<div class="payment">
		<div class="payment-icon">
			<img alt="success-icon" src="./resource/assests/original-3e1ed5c75a6ad9b1b5368f026492b6df (1) (1).png">
		</div>	
			<h2>Payment Success!</h2>
			<p>Your Payment has been successfully done.</p>
			<div class="hr"></div>
			<p>Total Payment</p>
			<h1>&#8377;<%=order.getAmount() %></h1>
			<div class="payment-warpper flex">
			<div class="payment-details">
			<p>Payment Id</p>
			<p><%=order.getPaymentId()%></p>
			</div>
			<div class="payment-details">
			<p>Payment Time</p>
			<p><%=order.getDate() %> <%=order.getTime() %></p>
			</div>
			</div>
			<div class="payment-warpper flex">
			<div class="payment-details">
			<p>Order Id</p>
			<p><%=order.getOrderId()%></p>
			</div>
			<div class="payment-details">
			<p>Sender Name</p>
			<p><%=order.getFirstName() %> <%=order.getLastName() %></p>
			</div>
		</div>
		
	</div>

	<%} %>
			<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
		<script type="text/javascript" src="./resource/js/Orders.js" defer></script>
		<script type="text/javascript">
		
		
	

		
		</script>
</body>
</html>