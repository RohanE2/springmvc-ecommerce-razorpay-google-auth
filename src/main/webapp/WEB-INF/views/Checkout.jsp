<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
List<CartPOJO> cartItems = (List<CartPOJO>) request.getAttribute("cartItems");
int totalQuantity = (int) request.getAttribute("totalQuantity");
long subtotal = (long) request.getAttribute("subtotal");
String userActive = (String) request.getAttribute("userActive");
String orderId = (String) request.getAttribute("orderId");
%>
<!DOCTYPE html>
<html>
<head>
 <meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="ISO-8859-1">
<title>Nike-checkout</title>
<link rel="stylesheet" href="<c:url value='/resource/css/Checkout.css'/>">
<style type="text/css">
.main{
display: flex;
justify-content:center;
position: relative;
top:100px;
gap:70px;
}
.main-box2{
width: 415px;
}
.checkout {
    background-color: #6262622e;
    border-radius: 15px;
    padding: 15px;
    position: relative;
}
.checkout h2 {
    font-weight: 500;
}
.checkout .subtotal {
    justify-content: space-between;
    gap: 30px;
    margin-top: 30px;
}
.checkout p {
    font-size: 1rem;
    line-height: 1.7rem;
}
.subtotal-symbol {
    gap: 5px;
}
.checkout .delivery-handling {
    gap: 30px;
    justify-content: space-between;
    margin-top: 10px;
    margin-bottom: 20px;
}
.line {
    border: 1px solid #494949d1;
}
.checkout .total {
    gap: 30px;
    margin-block: 15px;
    justify-content: space-between;
}
.total-symbol {
    gap: 5px;
}
.bag-content{
    margin-block: 20px;
    background-color: #6262622e;
    padding: 10px;
    border-radius: 15px;
    gap:20px;
}
 .bag-img {
    width: 125px;
    height: 125px;
    background-color: #3a3a3a66;
    border-radius: 10px;
}
 .bag-img img {
    width: 125px;
    height: 125px;
    transform: scaleX(-1);
}
.date-cart-products{
    position: relative;
    top: 10px;
    left: 10px;
}
.date-text{
	font-size: 1rem;
    line-height: 1.5;
    font-weight: 700;
}
.bag-text p{
	font-size: 1rem;
    line-height: 1.4rem;
}
.bag-text p:nth-child(3), 
.bag-text p:nth-child(4), 
.bag-text p:nth-child(5) {
    color: gray;
    font-size: 0.9rem;
    line-height: 1.3rem;
}
.green-button-input-wrapper{

}
.green-btn{
	width: 8px;
    height: 8px;
    border-radius: 50%;
    background-color: #19ab20;
	position: relative;
	left: 92%;
	display: none;
}
.checkout .note{
	font-size:0.7rem;
}
.checkbox{
gap:10px;
margin-block:10px; 
}
.checkbox label{
font-size:0.8rem;
line-height: 1.1rem;
}

.checkbox input[type="checkbox"] {
accent-color: black;
width: 35px;
height: 35px;
}
.continue-btn{
	width: 100%;
    height: 58px;
    border-radius: 60px;
    border: none;
    background-color: #fffffff0;
    color: black;
    font-size: 1.2rem;
    font-family: 'Poppins', sans-serif;
    cursor: pointer;
    text-align: center;
   	margin-top: 10px; 
   	outline: none;
}
.continue-btn:hover{
opacity: 0.7;
}
@media screen and (max-width:995px) {
	.nav-toggle{
		display:block;
	}
	.nav-links{
	display: none;
	}
	.search {
	display: none;
	}
	
	.hover-link{
	margin-inline:5px;
	}
	.toggle-add-wrapper {
    display: flex;
    gap: 10px;
}
.nav-links ul{
	flex-direction: column;
	gap:15px;
	}
	.nav-links-active {
    display: block;
    position: absolute;
    padding-block: 20px;
    top: -650px;
    height: 40vh;
    width: 100vw;
    left:0;
    background: #1f1f1fe8;
    border-radius: 45px;
    transition: .3s;
    backdrop-filter: blur(13px);
}
.nav-links-active-top{
top: 120px;
}
.main{
gap:30px;
}
}
@media screen and (max-width:800px){
.main{
flex-direction:column-reverse;
}
.main-box2{
	width: 100%;
}
.checkout{
width:100%;
}
.bag-content{
width:100%;
}
.info{
width:100%;
}
}
@media screen and (max-width:425px){
.container{
padding-inline:10px;
}
}
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
	<form action="./createOrder">
	<div class="container main">
	<div class="info ">
		<div><h3>Enter Your Name and Address</h3></div>
		<div class="input-field ">
			<label>First Name</label>
			<input class="input-area " type="text" placeholder="First Name" name="firstName" required="required">
			<div class="green-btn"></div>
			<p></p>
		</div>
		<div class="input-field">
			<label>Last Name</label> 
			<input class="input-area " type="text" placeholder="Last Name" name="lastName" required="required">
			<div class="green-btn"></div>
			<p></p>
		</div>
		<div class="input-field ">
			<label>Email</label>
			<input id="email" class="input-area " type="email" placeholder="Email" name="email" required="required">
			<div class="green-btn"></div>
			<p></p>
			</div>
		<div class="input-field">
			<label>Phone Number</label> 
			<input id="phone" class="input-area " type="number" placeholder="Phone Number" name="phone" required="required">
			<div class="green-btn"></div>
			<p></p>
		</div>						
		<div class="input-field">
			<label>Address</label> 
			<input  class="input-area " type="text" placeholder="Address" value="" name="address" required="required">
			<div class="green-btn"></div>
			<p></p>
		</div>
		<div class="input-name">
			<div class="input-field input-width">
				<label>Postal Code</label> 
				<input class="input-area " type="text" placeholder="Postal Code" name="postalcode" required="required">
				<div class="green-btn"></div>
				<p></p>
			</div>
			<div class="input-field input-width">
				<label>Locality</label> 
				<input class="input-area " type="text" placeholder="Locality" name="locality" required="required">
				<div class="green-btn"></div>
				<p></p>
			</div>
		</div>
		<div class="input-name">
			<div class="input-field input-width">
				<label>State</label>
				<input class="input-area" type="text" placeholder="State" name="state" required="required">
				<div class="green-btn"></div>
				<p></p>
			</div>
			<div class="input-field input-width">
				<label>Country</label> 
				<input class="input-area " type="text" placeholder="Country" name="country" required="required">
				<div class="green-btn"></div>
				<p></p>
			</div>
		</div>
		<div class="checkbox flex ">
		<input type="checkbox"   name="men">
		<label>I have read and consent to eShopWorld processing my information in accordance with the Privacy Statement and Cookie Policy. eShopWorld is a trusted Nike partner.</label>
		</div>
		
		<input id="rzp-button1" type="text" readonly value="Continue" class="continue-btn">
		<input hidden type = "number"name = "amount" value="<%=subtotal%>" class="amount">
		<input hidden type = "text" value="<%=orderId%>" class="orderId">
		</form>
	</div>
	
	<div class="main-box2">
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
		<input hidden = "true" readonly class= "alltotal" type = "text" value ="<%=subtotal  %>">
		<p class= "alltotal-p"><%=subtotal %>.00</p>
		</div>
		</div>
		<div class="line"></div>
	<p class="note">(The total reflects the price of your order, including all duties and taxes)</p>	
	</div>
	
		<div class="date-cart-products">
		<p class="date-text"></p>
		</div>
				<%
				for (CartPOJO items : cartItems) {	
				%>
				<div class="bag-content flex">
					<div class="bag-img ">
						<img src="./resource/assests/<%=items.getImage()%>"
							alt="green tick">
					</div>
					<div class="bag-text">
						<p><%=items.getProducts().getName()%></p>
						<p>Men's Football Shoes</p>
						<p>QTY <%=items.getQuantity()%></p>
						<p>Size <%=items.getSize() %></p>
						<p>Price &#8377;<%=items.getProducts().getPrice()%></p>
					</div>
				</div>		
				<%} %>			
		</div>
	</div>
	</div>
		<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
	
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
<script type="text/javascript" src="./resource/js/Checkout.js" defer></script>
</body>
</html>