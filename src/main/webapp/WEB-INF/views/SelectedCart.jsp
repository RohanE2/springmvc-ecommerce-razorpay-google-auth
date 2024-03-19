<%@page import="com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO"%>
<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
int totalQuantity = (int) request.getAttribute("totalQuantity");
List<CartPOJO> cartItems = (List<CartPOJO>) request.getAttribute("cartItems");
List<PopularProductsAddPOJO> products = (List<PopularProductsAddPOJO>) request.getAttribute("productList");
String msg = (String) request.getAttribute("msg");
String userActive = (String) request.getAttribute("userActive");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nike-cart</title>
<link rel="stylesheet"
	href="<c:url value='/resource/css/SelectedCart.css'/>">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">

</head>
<style>
	
</style>
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
					<li>
						<a href="./order" class="hover-link">Orders</a>
					</li>
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
	<!-- Bag -->

	
	<section>
		<div class="container bag-checkout flex">
		<div class = "bag flex">
	
	
		<div class="bag-heading ">
			<h2>Bag</h2>
			<%if(cartItems.isEmpty()){ %>
					<p>There are no items in your bag.</p>
		<% } %>
			</div>
<%long subtotal = 0; %>
	<form action="./updateCartItems">
	<%if(!cartItems.isEmpty()){ %>
	<div class="update-btn flex">
	<input readonly class="update-btn-input" type="text" value="Update" > 
	</div>
		<% } %>
				<%if(cartItems != null){ %>
				<%
				for (CartPOJO items : cartItems) {	
				%>
				<div class="bag-content flex">
					<a href="./product?id=<%=items.getProducts().getId()%>&size=0&color=<%=items.getColor()%>">
					<div class="bag-img ">
						<img src="./resource/assests/<%=items.getImage()%>"
							alt="green tick">
					</div>
					</a>
					<div class= "bag-text-price-wrapper">
					<div class="bag-text">
						<p><%=items.getProducts().getName()%></p>
						<p>Men's Football Shoes</p>
						<div class="bag-size flex">
						
						<div class="size-wrapper flex">
							<p>Size</p>
								<select class= "size-wrapper-select">
								<%if(items.getSize() == 7){%>
								<%if(items.getProducts().getSize7() != 0){ %>
									<option selected value = "<%=items.getProducts().getSize7() %>"><%=items.getProducts().getSize7() %></option>
									<%} %>
									<%if(items.getProducts().getSize8() != 0){ %>
									<option value = "<%=items.getProducts().getSize8() %>"><%=items.getProducts().getSize8() %></option>
										<%} %>
											<%if(items.getProducts().getSize9() != 0){ %>
									<option value = "<%=items.getProducts().getSize9() %>"><%=items.getProducts().getSize9() %></option>
									<%} %>
										<%if(items.getProducts().getSize10() != 0){ %>
									<option value = "<%=items.getProducts().getSize10() %>"><%=items.getProducts().getSize10() %></option>
									<%} %>
									
								<%}else if(items.getSize() == 8){ %>
									<%if(items.getProducts().getSize7() != 0){ %>
									<option  value = "<%=items.getProducts().getSize7() %>"><%=items.getProducts().getSize7() %></option>
									<%} %>
									<%if(items.getProducts().getSize8() != 0){ %>
									<option selected value = "<%=items.getProducts().getSize8() %>"><%=items.getProducts().getSize8() %></option>
										<%} %>
											<%if(items.getProducts().getSize9() != 0){ %>
									<option value = "<%=items.getProducts().getSize9() %>"><%=items.getProducts().getSize9() %></option>
									<%} %>
										<%if(items.getProducts().getSize10() != 0){ %>
									<option value = "<%=items.getProducts().getSize10() %>"><%=items.getProducts().getSize10() %></option>
									<%} %>
									<%}else if(items.getSize() == 9){ %>
									<%if(items.getProducts().getSize7() != 0){ %>
									<option  value = "<%=items.getProducts().getSize7() %>"><%=items.getProducts().getSize7() %></option>
									<%} %>
									<%if(items.getProducts().getSize8() != 0){ %>
									<option  value = "<%=items.getProducts().getSize8() %>"><%=items.getProducts().getSize8() %></option>
										<%} %>
											<%if(items.getProducts().getSize9() != 0){ %>
									<option selected value = "<%=items.getProducts().getSize9() %>"><%=items.getProducts().getSize9() %></option>
									<%} %>
										<%if(items.getProducts().getSize10() != 0){ %>
									<option value = "<%=items.getProducts().getSize10() %>"><%=items.getProducts().getSize10() %></option>
									<%} %>
									<%}else if(items.getSize() == 10){ %>
									<%if(items.getProducts().getSize7() != 0){ %>
									<option  value = "<%=items.getProducts().getSize7() %>"><%=items.getProducts().getSize7() %></option>
									<%} %>
									<%if(items.getProducts().getSize8() != 0){ %>
									<option  value = "<%=items.getProducts().getSize8() %>"><%=items.getProducts().getSize8() %></option>
										<%} %>
											<%if(items.getProducts().getSize9() != 0){ %>
									<option  value = "<%=items.getProducts().getSize9() %>"><%=items.getProducts().getSize9() %></option>
									<%} %>
										<%if(items.getProducts().getSize10() != 0){ %>
									<option selected value = "<%=items.getProducts().getSize10() %>"><%=items.getProducts().getSize10() %></option>
									<%} %>
									<%} %>
								</select>
						 	</div>
						<div class="quantity-wrapper flex">
						<p>Quantity </p>  
						<div class="minus">-</div>
						<input readonly class="num" type = "number" value = "<%=items.getQuantity()%>" name ="bagQuantity">
						<div class="plus">+</div>
						</div>
						</div>
						<div class="bag-icon flex">
					
							<input hidden class="size-wishlist" type = "text" name = "size" value = "<%=items.getSize()%>"> 
							<input hidden class="quantity-wishlist" type = "text" name = "quantity" value = "<%=items.getQuantity()%>">
							<input hidden type = "number" name = "ids" value = "<%=items.getId()%>">
							<input hidden type = "number" name = "productId" value = "<%=items.getProducts().getId()%>">
							<input hidden type = "text" name = "color" value = "<%=items.getColor()%>"> 
								 <a href = "./cartWishlist?productId=<%=items.getProducts().getId()%>&size=<%=items.getSize()%>&color=<%=items.getColor()%>&quantity=<%=items.getQuantity()%>&previousSize=<%=items.getSize()%>" class="anchor">
							 <div class="like-submit flex">
							 <i class="fa-regular fa-heart"></i>
							 	
							 </div>
							 </a>
							<a href = "./cartRemove?productId=<%=items.getProducts().getId()%>&size=<%=items.getSize()%>&color=<%=items.getColor() %>" class="anchor">
							 <i class="bi bi-trash"></i>
							 </a>
						</div>
					</div>
					<div class="bag-price flex">
						<p>MRP:$ </p>
						<input hidden = "true" class= "price"  type="text" value = "<%=items.getProducts().getPrice()%>" name = "price">
						<input hidden = "true" class="total-input" type="text" readonly value = "<%=items.getProducts().getPrice() * items.getQuantity()%>">
							<% subtotal += items.getProducts().getPrice() * items.getQuantity();%>
						<p class="total-input-p"><%=items.getProducts().getPrice() * items.getQuantity()%></p>	
					</div>
					</div>
				</div>
				
				<%
				}
				%>
				<%
				}
				%>
				</form>

 	
	</div>
	
	<%if(!cartItems.isEmpty()){ %>
	<div class="checkout">
		<h2>Summary</h2>
		<div class="subtotal flex">
		<div class="subtotal-icon flex">
		<p>Subtotal</p>
		<i class="fa-solid fa-circle-question subtotal-info"></i>
		<div class="subtotal-text"></div>
		</div>
		<div class="subtotal-symbol flex">
		<p>$</p>
		<input hidden = "true" class= "subtotal-input" type = "text" value ="<%=subtotal %>">
		<p class= "subtotal-input-p"><%=subtotal%>.00</p>
		</div>
		</div>
		<div class="delivery-handling flex">
		<p>Estimated Delivery & Handling</p>
		<p>$ 1250.00</p>
		</div>
		<div class="line"></div>
		<div class="total flex">
		<p>Total</p>
		<div class= "total-symbol flex">
		<p>$</p>
		<input hidden = "true" readonly class= "alltotal" type = "text" value ="<%=subtotal + 1250 %>">
		<p class= "alltotal-p"><%=subtotal + 1250%>.00</p>
		</div>
		</div>
		<div class="line"></div>
		<form action="./checkout">
		 <div class="checkout-btn flex">
				 <input class="checkout-btn-input" type="submit" name="checkout" value="Checkout">
			</div>
			 </form>
	</div>
	<%} %>
	</div>

	</section>
		<section class="more-products-section ">
			<div class=" more-products-main container">
			<div class="more-products-heading flex">
				<p>You Might Also Like</p>
				<div class="controls flex">
					<div class="arrow">
						<i id = "left"class="fa-solid fa-angle-left"></i>
					</div>
					<div class=" arrow">
						<i id = "right"class="fa-solid fa-angle-right"></i>
					</div>
				</div>
			</div>
	<div class="more-products flex">
		<%for(PopularProductsAddPOJO item : products){ %>
			<a href="./product?id=<%=item.getId()%>&size=0&color=<%=item.getOrColour()%>">
		<div class="more-products-content ">
			<div class="more-products-img-card">
				<div class="more-products-img">
				<img src="./resource/assests/<%=item.getImage()%>" alt="cleats">
				</div>
			</div>
			
			<div class="more-products-text">
			<div class="more-products-text-name">
			<p><%=item.getName() %></p>
			<p>Firm-Ground Football Boot</p>
			</div>
			<div class="more-products-text-price">
			<p>MRP : $ <%=item.getPrice() %></p>
			</div>
			</div>
			</div>
			</a>
		<%} %>	
	</div>
	</div>
</section>
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
		<script type="text/javascript" src="./resource/js/selectedCart.js" defer></script>
		<script type="text/javascript">
	
	</script>
</body>
</html>