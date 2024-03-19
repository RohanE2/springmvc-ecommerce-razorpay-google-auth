<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@page import="com.jspiders.mvcproject1.pojo.WishlistPOJO"%>
<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
<%
int totalQuantity = (int) request.getAttribute("totalQuantity");
List<WishlistPOJO> wishlistProducts = (List<WishlistPOJO>) request.getAttribute("wishlistProducts");
List<PopularProductsAddPOJO> products = (List<PopularProductsAddPOJO>) request.getAttribute("productList");
WishlistPOJO product = (WishlistPOJO) request.getAttribute("product");
CartPOJO product1 = (CartPOJO) request.getAttribute("product1");
String size = (String) request.getAttribute("size");
String userActive = (String) request.getAttribute("userActive");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nike-wishlist</title>
<link rel="stylesheet" href="<c:url value='/resource/css/Wishlist.css'/>">
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

	<section>
		<div class="wishlist container">
		<div class="wishlist-heading-place">
		<form action="./removeWishlist">
		<div class="wishlist-heading flex">
		<h3>Favourites</h3>
		<div class="wishlist-edit">
		<input readonly class="edit-btn-input" type="text" name="view-bag" value="Edit"> 
		</div>
		</div>
		<input hidden class = "wishlistIds" type="text" name="ids" value="">
		</form>
		</div>
		<div class="wishlist-content">
	
		<% for(WishlistPOJO items : wishlistProducts){ %>
			<div class="wishlist-content-items">
			<a href="./product?id=<%=items.getProducts().getId()%>&size=0&color=<%=items.getColor()%>">
			<div class="img-icon-wrapper">
			<div class="icon-bg ">
			<i class="fa-solid fa-heart"></i>
			</div>
			<div class="wishlist-img-card">
			<img src="./resource/assests/<%=items.getImage()%>" alt="cleats">
			</div>
			<div class="img-overlay"></div>
			</div>
			</a>
			
			<input hidden type="number" name="id" value="<%=items.getId()%>">
			<div class="wishlist-name-price flex">
			<p><%=items.getProducts().getName() %></p>
			<h3>MRP:$ <%=items.getProducts().getPrice() %></h3>
			</div>
			<p>Firm-ground Football Boot</p>
			<%if(items.getProducts().getQuantity()!= 0){ %>
				<%if(items.getSize() != 0){ %>
				<%if(items.isAddedToCart()){ %>
				<div class= "green-tick-img">
				<img src="./resource/assests/pngwing.com (3).png" alt="green tick" width="20px">
				<input class="size-btn-input" type="submit" name="view-bag" value="Added"> 
				</div>
				<%}else{ %>
				<form action="./addtobag1">
				<input hidden type = "number" name = "productId" value = "<%=items.getProducts().getId()%>">
				<input hidden type = "number" name = "size" value = "<%=items.getSize()%>">	
				<input hidden type = "text" name = "color" value = "<%=items.getColor()%>">	
				<input class="size-btn-input" type="submit" name="view-bag" value="Add to Bag"> 
				</form>
				<%} %>
			<%}else { %>
				<form action="./selectSize">
				<input hidden type = "number" name = "productId" value = "<%=items.getProducts().getId()%>">
				<input hidden type = "text" name = "color" value = "<%=items.getColor()%>">
				<input class="size-btn-input" type="submit" name="view-bag" value="Select Size"> 
				</form>
			<%} %>
			
			<%}else{ %>
				<input class="size-btn-input" type="submit" name="view-bag" value="Sold Out"> 
			<%} %>
			
			</div>
			<%} %>
			<%if(wishlistProducts.isEmpty()){%>
				<h3>YOUR Wishlist products will be displayed here</h3>
		<% 	} %>
			
			
			</div>
		</div>
	</section>
	
	<%if(product != null){ %>
	<div class="popup">
		<div class = "popup-content flex">
		<div class= "popup-img-card">
		<img src="./resource/assests/<%=product.getImage()%>" alt="cleats">
		</div>
		
		<div class="popup-text">
			<p>Firm-Ground Football Boot</p>
			<h1><%=product.getProducts().getName() %></h1>
			<p>MRP:$ <%=product.getProducts().getPrice() %></p>
			<div class="popup-text-selectsize"><p>Select Size</p></div>
			<div class="btn flex ">
			<%if(product.getProducts().getSize7() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="7">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="7">
						<%} %>
						<%if(product.getProducts().getSize8() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="8">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="8">
						<%} %>
						<%if(product.getProducts().getSize9() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="9">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="9">
						<%} %>
						<%if(product.getProducts().getSize10() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="10">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="10">
						<%} %>
			</div>
			<form action="./addtobag">
			<input hidden class="selectedsize" type = "number" name = "size" value="">
			<input hidden  type = "text" name = "color" value = "<%=product.getColor()%>">
			<input hidden="true"  type = "number" name = "productId" value = "<%=product.getProducts().getId()%>">
			<input readonly class="size-btn-input wishBag" type="text" name="checkout" value="Add to Bag">
			</form>
		</div>
		
		</div>
	</div>
	<%} %>
		<!-- Popup -->
	<%
	if (product1 != null) {
	%>
	<div class="popup1" id="popup">
		<div class="popup-container1 ">
			<div class="popup-top1 flex">
				<div class="popup-top-img1 flex">
					<img src="./resource/assests/pngwing.com (3).png" alt="green tick"
						width="20px">
					<p>Added to Bag</p>
				</div>
				<i class="bi bi-x-lg"></i>
			</div>
			<div class="popup-content1 flex">
				<img src="./resource/assests/<%=product1.getImage()%>"
					alt="green tick" width="100px">
				<div class="popup-text1">
					<p><%=product1.getProducts().getName()%></p>
					<p>Men's Football Shoes</p>
					<p>
						Size UK
						<%=product1.getSize()%></p>
					<p>MRP : $ <%=product1.getProducts().getPrice() %></p>
				</div>
			</div>
			<div class="popup-btn1 flex">
			<form action="./selectedCart">
			<input hidden  type="text" name="otherActive" value="null">
				<input hidden type="text" name="sortActive" value="null">
				<input hidden type="number" name="pageId" value="0">
				<input hidden type="number" name="id" value="0">
				<input hidden type="number" name="size" value="0">
				<input hidden type="text" name="color" value="null">
				<input class="popup-btn-input1" type="submit" name="view-bag"
					value="View Bag "> 
					</form>
					<form action="./checkout">
					<input class="popup-btn-input1"
					type="submit" name="Checkout" value="Checkout">
					</form>
			</div>
		</div>
	</div>
	<%
	}
	%>
	
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
		<p>©️ 2023 CreativePeoples.All rights reserved.</p>
	</footer>

</body>
<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
		<script type="text/javascript" src="./resource/js/Wishlist.js" defer></script>
		<script type="text/javascript">

			
			
		
	</script>
	
</html>