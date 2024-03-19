<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@page import="com.jspiders.mvcproject1.pojo.WishlistPOJO"%>
<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
int totalQuantity = (int) request.getAttribute("totalQuantity");
PopularProductsAddPOJO product = (PopularProductsAddPOJO) request.getAttribute("product");
List<PopularProductsAddPOJO> products = (List<PopularProductsAddPOJO>) request.getAttribute("productList");
WishlistPOJO wishlist = (WishlistPOJO) request.getAttribute("wishlist");
PopularProductsAddPOJO product1 = (PopularProductsAddPOJO) request.getAttribute("product1");
CartPOJO cart = (CartPOJO) request.getAttribute("cart");

int size = (int) request.getAttribute("size");

String getSize  = (String) request.getAttribute("getSize");
String selectSize  = (String) request.getAttribute("selectSize");
String selectSizemsg  = (String) request.getAttribute("selectSizemsg");
String Ssize = (String) request.getAttribute("Ssize");
String Msize = (String) request.getAttribute("Msize");
String Lsize = (String) request.getAttribute("Lsize");
String Xsize = (String) request.getAttribute("Xsize");
String fillIcon = (String) request.getAttribute("fillIcon");
String color = (String) request.getAttribute("color");

String imgIndex = (String) request.getAttribute("imgIndex");
String wishProd = (String) request.getAttribute("wishProd");

String userActive = (String) request.getAttribute("userActive");
int pageId = (int) request.getAttribute("pageId");
int id = (int) request.getAttribute("id");
String msg1 = (String) request.getAttribute("msg");
String loginMsg = (String) request.getAttribute("loginMsg");
String loginPopup =  (String) request.getAttribute("loginPopup");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nike-product</title>
<link rel="stylesheet" href="<c:url value='/resource/css/product.css'/>">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<style>


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
					<li>
						<a href="./order" class="hover-link">Orders</a>
					</li>
					<%if(userActive != null){ %>
							<li><a href="./logoutPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=<%=id%>&size=<%=size%>&color=<%=color %>" class="hover-link">Sign Out</a></li>
				<% 	}else{ %>
							<li><a href="./loginPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=<%=id%>&size=<%=size%>&color=<%=color %>" class="hover-link">Sign In</a></li>
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
				<a href="./wishlist?otherActive=null&sortActive=null&pageId=<%=pageId%>&id=<%=id%>&size=<%=size%>&color=<%=color %>" class="search-like hover-link"> <i
					class="fa-regular fa-heart"></i>
				</a>
				 <a href="./selectedCart?otherActive=null&sortActive=null&pageId=<%=pageId%>&id=<%=id%>&size=<%=size%>&color=<%=color %>" class="search-bag hover-link"> 
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
		 <%if(loginMsg != null ){ %>
	 <div class = "msg login-msg">
	<p class="danger1"><%=loginMsg%></p>
	</div>
	<%} %>
	<section>
		<div class="container product flex">
			<div class="product-img-wrapper flex">
			<div class="imgIndex"><%=imgIndex %></div>
				<div class="product-img flex">
					<%if(product.getImage() != null ){ %>
					<div class="product-img-card">
						<img src="./resource/assests/<%=product.getImage()%>" alt="cleats">
					</div>
					<%} %>
					<%if(product.getImage2() != null ){ %>
					<div class="product-img-card">
						<img src="./resource/assests/<%=product.getImage2()%>" alt="cleats">
					</div>
					<%} %>
					<%if(product.getImage3() != null ){ %>
					<div class="product-img-card">
						<img src="./resource/assests/<%=product.getImage3()%>" alt="cleats">
					</div>
						<%} %>
					
					
					<div class="product-img-card">
						<img class="images" src="./resource/assests/box.png" alt="cleats">
					</div>
					
				</div>
				<div class = "main-card-icon-wrapper">
				<div class="main-img-card flex ">
					
					<div class="main-img-card-img ">
					<% 
					if(product.getOrColour().equals(color)) {%>
					<img src="./resource/assests/<%=product.getImage()%>" alt="cleats">
			<% 	}else if(product.getBColour().equals(color)) {%>
					<img src="./resource/assests/<%=product.getImage2()%>" alt="cleats">
			<%	}else if(product.getYColour().equals(color)) {%>
				<img src="./resource/assests/<%=product.getImage3()%>" alt="cleats">
				<%	}%>
					</div>
					<div class="main-img-card-img ">
						<img src="./resource/assests/box.png" alt="cleats">
					</div>
				
				</div>

				<div class="controls flex">
					<div id = "left" class="box1 ">
					<i class="fa-solid fa-angle-left"></i>
					</div>
					<div id = "right" class="box1 ">
						<i class="fa-solid fa-angle-right"></i>
					</div>
				</div>
			</div>


			</div>
			<div class="product-content">
				<h2><%=product.getName()%></h2>
				<p>Firm-Ground Football Boot</p>
				<div class="product-price flex">
					<p>MRP:$<%=product.getPrice()%></p>
				</div>
				<p>incl. of taxes</p>
				<p>(Also includes all applicable duties)</p>
				<div class="product-content-img flex">
						<%if(product.getImage() != null ){ %>
					<div class="product-content-img-color">
						<img src="./resource/assests/<%=product.getImage()%>" alt="cleats">
						<input hidden type="text" name = "color" value ="<%=product.getOrColour()%>">
					</div>
					<%} %>
					<%if(product.getImage2() != null ){ %>
					<div class="product-content-img-color">
						<img src="./resource/assests/<%=product.getImage2()%>" alt="cleats">
						<input hidden type="text" name = "color" value ="<%=product.getBColour()%>">
					</div>
					<%} %>
					<%if(product.getImage3() != null ){ %>
					<div class="product-content-img-color">
						<img src="./resource/assests/<%=product.getImage3()%>" alt="cleats">
						<input hidden type="text" name = "color" value ="<%=product.getYColour()%>">
					</div>
						<%} %>
					
				<input hidden class="wishProd" type="text" name = "wishProd" value ="<%=wishProd%>">
				</div>
				<div class="size">
					<div class="size-heading flex ">
						<p  class= "<%=selectSize%>">Select Size</p>
						<p>Size Guide</p>
					</div>
						<input hidden ="true" type="number" name = "pageId" value = "1">
						<input hidden = "true" type="number" name="productId" value="<%=product.getId()%>">
						<div class="size-btn <%=getSize%>">
						<%if(product.getSize7() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="7">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="7">
						<%} %>
						<%if(product.getSize8() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="8">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="8">
						<%} %>
						<%if(product.getSize9() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="9">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="9">
						<%} %>
						<%if(product.getSize10() == 0){ %>
						<div class ="sizeCrossWrapper flex">
						<div class="crossSymbol"></div>
						<input  type="submit" name="size" value="10">
						</div>
						<%}else{ %>
						<input class="sizeNot"  type="submit" name="size" value="10">
						<%} %>
						
						</div>
			<%--		</form>	   --%>
					<%if(selectSizemsg != null){ %>
					<p id = "sizeMsg" class= "<%=selectSize%>"><%=selectSizemsg%></p>
					<%} %>
				</div>
				<form action="./addtobagProduct">
				<div class="checkout-btn flex">
				<input hidden type = "number" name = "productId" value = "<%=product.getId()%>">
				<input hidden class="getSizeValue"  type = "number" name = "size" value = "<%=size%>">
				<input hidden class ="colorChoice" type="text" name="color" value="<%=color%>">	
				
				<input class="checkout-btn-input" type="submit" name="checkout" value="Add to Bag">
				</div>
				</form>
				<form action="./favourite">
				<div class="checkout-btn fav flex">
					<input hidden class="getSizeValue"  type = "number" name = "size" value = "<%=size%>">
					<input hidden type="number" name="id" value="<%=product.getId()%>">
					<input hidden class="colorChoice" type="text" name="color" value="<%=color%>">
					<input  class="checkout-btn-input" type="submit" name="checkout" value="Favourite"> 
						<i class=" fa-heart fa-regular"></i>
				</div>
				</form>
				<div class="desc">
				<p>Inspired by the original AJ1, this mid-top edition maintains
					the iconic look you love while choice colours and crisp leather
					give it a distinct identity.</p>
					</div>
				<ul class="ul">
					<li>Colour Shown: Bright Crimson/Black/White</li>
					<li>Style: DJ4978-600</li>
				</ul>
				<div class="product-details">
				<p>View Product Details</p>
				<div class="hr"></div>
				</div>
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Size & Fit</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">
						<ul class="ul">
							<li>Snug fit; if you prefer a slightly looser fit, we
								recommend ordering a half-size up</li>
							<li>Size Guide</li>
						</ul>
					</div>
				</div>


				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Delivery & Returns</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">
						<p>All purchases are subject to delivery fees.</p>
						<ul class="ul">
							<li>Standard delivery 4-9 business days</li>
						</ul>
						<p>Orders are processed and delivered Monday-Friday (excluding
							public holidays)</p>
						<p class="acc-content-text">Nike Members enjoy free returns.</p>
					</div>
				</div>

				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Product Information</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">
						<div class="acc-content-text-p">
						<p>Declaration of Importer: Direct import by the individual
							customer</p>
						<p>Marketed by: Nike Global Trading B.V. Singapore Branch, 30
							Pasir Panjang Road, #10-31/32, Mapletree Business City, Singapore
							117 440</p>
						<p>Net Quantity: 1 Pair</p>
						</div>
					</div>
				</div>
				<div class="hr"></div>
			</div>
		</div>
		</div>
		</section>
		
			<!-- Popup  -->
	<%
	if (cart != null) {
	%>
	<div class="popup1" id="popup">
		<div class="popup-container1 ">
			<div class="popup-top1 flex">
				<div class="popup-top-img1 flex">
					<img src="./resource/assests/pngwing.com (3).png" alt="green tick"
						width="20px">
					<p>Added to Bag</p>
				</div>
					<div class="close-icon">
				<i class="bi bi-x-lg"></i>
				</div>
			</div>
			<div class="popup-content1 flex">
				<img src="./resource/assests/<%=cart.getImage()%>"
					alt="green tick" width="100px">
				<div class="popup-text1">
					<p><%=cart.getProducts().getName()%></p>
					<p>Men's Football Shoes</p>
					<p>
						Size UK
						<%=cart.getSize()%></p>
					<p>MRP : $ <%=cart.getProducts().getPrice() %></p>
				</div>
			</div>
			<div class="popup-btn1 flex">
			<form action="./selectedCart">
				<input hidden  type="text" name="otherActive" value="null">
				<input hidden type="text" name="sortActive" value="null">
				<input hidden type="number" name="pageId" value="<%=pageId%>">
				<input hidden type="number" name="id" value="0">
				<input hidden type="number" name="size" value="0">
				<input hidden type="text" name="color" value="null">
				<input class="popup-btn-input1" type="submit" name="view-bag" value="View Bag (8)">
				</form>
				<form action="./checkout">
				 <input class="popup-btn-input1" type="submit" name="Checkout" value="Checkout">
				 	</form>
			</div>
		</div>
	</div>
	<%

	}
	%>
	
	
		
		
		<!-- Popup -->
	<%
	if (wishlist != null) {
	
	%>
	<div class="popup" id="popup">
		<div class="popup-container ">
			<div class="popup-top flex">
				<div class="popup-top-img flex">
					<p>Added to Favourites</p>
				</div>
				<div class="close-icon">
				<i class="bi bi-x-lg"></i>
				</div>
			</div>
			<div class="popup-content flex">
				<div class="popup-content-img-card">
				<img src="./resource/assests/<%=wishlist.getImage()%>"
					alt="green tick" >
					</div>
				<div class="popup-text">
					<p><%=wishlist.getProducts().getName()%></p>
					<p>Men's Football Shoes</p>
					<%if(size != 0){ %>
					<p>Size UK <%=size%></p>
					<%} %>
					<p>MRP : $ <%=wishlist.getProducts().getPrice() %></p>
				</div>
			</div>
			
			<form action="./wishlist">
			<div class="popup-btn flex">
			<input hidden  type="text" name="otherActive" value="null">
				<input hidden type="text" name="sortActive" value="null">
				<input hidden type="number" name="pageId" value="<%=pageId%>">
				<input hidden type="number" name="id" value="0">
				<input hidden type="number" name="size" value="0">
				<input hidden type="text" name="color" value="null">
				 <input class="popup-btn-input" type="submit" name="Checkout" value="View Favourites">
				 
			</div>
			 </form>
		</div>
	</div>
	<%

	}
	%>
		
		   <!-- login popup -->
    <% 
    if(loginPopup != null){
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
        	<form action ="./login" >
        	<input hidden="true" type="number" name="pageId" value="2">
        		<input hidden type = "number" name = "id" value = "<%=product.getId()%>">
				<input hidden class="getSizeValue"  type = "number" name = "size" value = "<%=size%>">
				<input hidden class ="colorChoice" type="text" name="color" value="<%=color%>">
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
        	<input type="checkbox">
        	<label>Remember me</label>
        	</div>
        	<input readonly type="text" class="login" value ="Login In">
        	</form>
        	<div class="create">
        	<div class="signup">
        	<label>Don't have account yet?</label>
        	<a href="./signupPage?pageId=<%=pageId%>&otherActive=null&sortActive=null&id=<%=id%>&size=<%=size%>&color=<%=color%>">Sign up</a>
        	</div>
        	<div class="signup">
        	<a>Forgot your password?</a>
        	</div>
        	</div>
	<%
    if(msg1 != null){
    %>
    <h3 class = "msg1"><%=msg1 %></h3>	
    <% 
    } 
    %>
        </div>
   
     <%} %>
		
	
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


	<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
		<script type="text/javascript" src="./resource/js/Product.js" defer></script>
	<script type="text/javascript">
	
	</script>
</body>
</html>