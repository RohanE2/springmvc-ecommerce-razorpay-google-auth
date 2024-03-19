
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Set"%>
<%@page import="org.hibernate.internal.build.AllowSysOut"%>
<%@page import="com.jspiders.mvcproject1.pojo.CartPOJO"%>
<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.PopularProductsAddPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
int totalQuantity = (int) request.getAttribute("totalQuantity");
List<PopularProductsAddPOJO> products = (List<PopularProductsAddPOJO>) request.getAttribute("productList");
List<Object[]> cartItems = (List<Object[]>) request.getAttribute("cartItems");

CartPOJO cart = (CartPOJO) request.getAttribute("cart");
int size = (int) request.getAttribute("size");

Integer selectedProductId = (Integer) request.getAttribute("selectedProductId");
ArrayList<String> cartItem = (ArrayList) request.getAttribute("cartItem");

String getsize = (String) request.getAttribute("getsize");
String selectSizeMsg = (String) request.getAttribute("selectSizeMsg");
String cross = "cross";

String fillterSizeStyle7 = (String) request.getAttribute("fillterSizeStyle7");
String fillterSizeStyle8 = (String) request.getAttribute("fillterSizeStyle8");
String getStyle7 = (String) request.getAttribute("getStyle7");
String getStyle8 = (String) request.getAttribute("getStyle8");
String getStyle9 = (String) request.getAttribute("getStyle9");
String getStyle10 = (String) request.getAttribute("getStyle10");
String sizeActive = (String) request.getAttribute("sizeActive");

String orange = (String) request.getAttribute("orange");
String blue = (String) request.getAttribute("blue");
String yellow = (String) request.getAttribute("yellow");
String green = (String) request.getAttribute("green");
String red = (String) request.getAttribute("red");
String purple = (String) request.getAttribute("purple");
String activeColor = (String) request.getAttribute("activeColor");

String featured = (String) request.getAttribute("Featured");
String newest = (String) request.getAttribute("Newest");
String highToLow = (String) request.getAttribute("HighLow");
String lowToHigh = (String) request.getAttribute("LowHigh");
String sortActive = (String) request.getAttribute("sortActive");

String flag = (String) request.getAttribute("flag");
String Pbtn = (String) request.getAttribute("Pbtns");

String genderfilltermale = (String) request.getAttribute("genderfilltermale");
String genderfillterfemale = (String) request.getAttribute("genderfillterfemale");
String genderActive = (String) request.getAttribute("genderActive");
String pricefillter1 = (String) request.getAttribute("pricefillter1");
String pricefillter2 = (String) request.getAttribute("pricefillter2");
String pricefillter3 = (String) request.getAttribute("pricefillter3");
String active = (String) request.getAttribute("active");

String saleActive = (String) request.getAttribute("saleActive");
String sale = (String) request.getAttribute("sale");

String otherActive = (String) request.getAttribute("otherActive");

String loginPopup = (String) request.getAttribute("loginPopup");
String msg1 = (String) request.getAttribute("msg");

String userActive = (String) request.getAttribute("userActive");
String loginMsg = (String) request.getAttribute("loginMsg");
int pageId = (int) request.getAttribute("pageId");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
<title>Products View</title>
<link rel="stylesheet"
	href="<c:url value='/resource/css/PopularProductsStyle.css'/>">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
<link
	href="https://cdn.jsdelivr.net/npm/remixicon@3.5.0/fonts/remixicon.css"
	rel="stylesheet">
</head>
<style>
.accordian .acc-content .orange, .accordian .acc-content .blue, .accordian .acc-content .yellow, .accordian .acc-content .green, .accordian .acc-content .red, .accordian .acc-content .purple {
    background-image: url(./resource/assests/clipart4137128.png);
    background-size: 15px;
    background-position: center;
    background-repeat: no-repeat;
  
}
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
					<li><a href="./sort?sortBy=Newest&otherActive=null"
						class="hover-link">New Releases</a></li>
					<li><a
						href="./menFillter?gender=male&active=null&otherActive=null&sortActive=null"
						class="hover-link">Men</a></li>
					<li><a
						href="./menFillter?gender=female&active=null&otherActive=null&sortActive=null"
						class="hover-link">Woman</a></li>
					<li><a href="./view" class="hover-link">collections</a></li>
					<li>
						<a href="./order" class="hover-link">Orders</a>
					</li>
					<%
					if (userActive != null) {
					%>
					<li><a
						href="./logoutPage?pageId=<%=pageId%>&otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&id=0&size=0&color=null"
						class="hover-link">Sign Out</a></li>
					<%
					} else {
					%>
					<li><a
						href="./loginPage?pageId=<%=pageId%>&otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&id=0&size=0&color=null"
						class="hover-link">Sign In</a></li>
					<%
					}
					%>
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
				<a
					href="./wishlist?otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&pageId=<%=pageId%>&id=0&size=0&color=null"
					class="search-like hover-link"> <i class="fa-regular fa-heart"></i>
				</a> <a
					href="./selectedCart?otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&pageId=<%=pageId%>&id=0&size=0&color=null"
					class="search-bag hover-link">
					<div class="cart">
						<i class="fa-solid fa-bag-shopping"></i>
						<div id="cartAmount" class="cartAmount"><%=totalQuantity > 0 ? totalQuantity : ""%></div>
					</div>
				</a>
			</div>
			<a href="#" class="nav-toggle hover-link" id="nav-toggle"> <i
				class="fa-solid fa-bars"></i>
			</a>
			</div> 
		</div>
	</nav>

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

	<!-- Fillter section -->
	<div class="wall ">
		<aside>
			<div class="aside-container">
			<div class="aside-close-wrapper flex">
				<h3>Filters</h3>
				<div class="close-icon aside-close">
				<i class="bi bi-x-lg"></i>
				</div>
			</div>	
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Gender</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">
						<form action="./menFillter">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input class="<%=genderfilltermale%>" type="submit" value=""
										id="menCheckbox" name="men"> <i class="fas fa-check"></i>
								</div>
								<label for="menCheckbox">Men</label>
							</div>
							<input hidden type="text" name="gender" value="male">
							 <input hidden type="text" name="active" value="<%=genderActive%>"> <input
							hidden	type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
						<form action="./menFillter">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input class="<%=genderfillterfemale%>" type="submit" value=""
										id="womenCheckbox" name="Women"> <i
										class="fas fa-check"></i>
								</div>
								<label for="womenCheckbox">Women</label>
							</div>
							<input hidden type="text" name="gender" value="female"> <input hidden
								type="text" name="active" value="<%=genderActive%>"> <input hidden
								type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
					</div>
				</div>
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Price</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content ">
						<form action="./priceFillter">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input class="<%=pricefillter1%>" type="submit" value=""
										name="price" id="priceCheckbox1"> <i
										class="fas fa-check"></i>
								</div>
								<label name="price" for="priceCheckbox1">$2501.00 -
									$7500.00</label>
							</div>
							<input hidden type="text" name="checkedPrice" value="1"> <input hidden
								type="text" name="active" value="<%=active%>"> <input hidden
								type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
						<form action="./priceFillter">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input class="<%=pricefillter2%>" type="submit" value=""
										name="price" id="priceCheckbox2"> <i
										class="fas fa-check"></i>
								</div>
								<label name="price" for="priceCheckbox2">$7501.00 -
									$12999.00</label>
							</div>
							<input hidden type="text" name="checkedPrice" value="2"> <input hidden
								type="text" name="active" value="<%=active%>"> <input hidden
								type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
						<form action="./priceFillter">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input  class="<%=pricefillter3%>" type="submit" value=""
										name="price" id="priceCheckbox3"> <i
										class="fas fa-check"></i>
								</div>
								<label name="price" for="priceCheckbox3">Over $13000.00</label>
							</div>
							<input hidden type="text" name="checkedPrice" value="3"> <input hidden
								type="text" name="active" value="<%=active%>"> <input hidden
								type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
					</div>
				</div>
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Sales & Offers</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">
						<form action="./sale">
							<div class="acc-content-input">
								<div class="acc-content-checkbox">
									<input class="<%=sale%>" type="submit" name="men"
										id="saleCheckbox" class="checkbox" value=""> <i
										class="fas fa-check"></i>
								</div>
								<label for="saleCheckbox">Sale</label>
							</div>
							<input hidden type="text" name="btn" value="sale"> <input hidden
								type="text" value="<%=saleActive%>" name="saleActive"> <input hidden
								type="text" name="otherActive" value="<%=otherActive%>">
							<input hidden type="text" name="sortActive" value="<%=sortActive%>">
						</form>
					</div>
				</div>
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Size</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<div class="acc-content">

						<div class="btnpair">
							<form action="./sizeFillter">
								<input  type="submit" name="btn" value="7" class="<%=getStyle7%>">
								<input hidden type="text" name="active" value="<%=sizeActive%>">
								<input hidden type="text" name="otherActive" value="<%=otherActive%>">
								<input hidden type="text" name="sortActive" value="<%=sortActive%>">
							</form>
							<form action="./sizeFillter">
								<input type="submit" name="btn" value="8" class="<%=getStyle8%>">
								<input hidden type="text" name="active" value="<%=sizeActive%>">
								<input hidden type="text" name="otherActive" value="<%=otherActive%>">
								<input hidden type="text" name="sortActive" value="<%=sortActive%>">
							</form>

						</div>
						<div class="btnpair">
							<form action="./sizeFillter">
								<input type="submit" name="btn" value="9" class="<%=getStyle9%>">
								<input hidden type="text" name="active" value="<%=sizeActive%>">
								<input hidden type="text" name="otherActive" value="<%=otherActive%>">
								<input hidden type="text" name="sortActive" value="<%=sortActive%>">
							</form>
							<form action="./sizeFillter">
								<input  type="submit" name="btn" value="10"
									class="<%=getStyle10%>"> <input hidden type="text"
									name="active" value="<%=sizeActive%>"> <input hidden
									type="text" name="otherActive" value="<%=otherActive%>">
								<input  hidden type="text" name="sortActive" value="<%=sortActive%>">
							</form>
						</div>
					</div>
				</div>
				<div class="hr"></div>
				<div class="accordian">
					<div class="footwear">
						<p>Colour</p>
						<i class="icon  fa-solid fa-caret-down"></i>
					</div>
					<form action="./colorFillter">
						<div class="acc-content colors ">
							<div class="color">
								<div class="round <%=orange%>"></div>
								<p>Orange</p>
								<input  type="submit" name="color" value="orange">
							</div>
							<div class="color">
								<div class="round <%=blue%>"></div>
								<p>Blue</p>
								<input type="submit" name="color" value="blue">
							</div>
							<div class="color">
								<div class="round <%=yellow%>"></div>
								<p>Yellow</p>
								<input type="submit" name="color" value="yellow">
							</div>
							<div class="color">
								<div class="round <%=green%>"></div>
								<p>Green</p>
								<input type="submit" name="color" value="green">
							</div>
							<div class="color">
								<div class="round <%=red%>"></div>
								<p>Red</p>
								<input type="submit" name="color" value="red">
							</div>
							<div class="color">
								<div class="round <%=purple%>"></div>
								<p>Purple</p>
								<input type="submit" name="color" value="purple">
							</div>
							<div class="spacer"></div>
						</div>
						<input hidden type="text" name="activeColor" value="<%=activeColor%>">
						<input hidden type="text" name="otherActive" value="<%=otherActive%>">
						<input hidden type="text" name="sortActive" value="<%=sortActive%>">
					</form>
				</div>
				<div class="hr"></div>
			</div>
		</aside>

		<!-- cards -->
		<section class="cards-section flex ">
			<div class=" sort flex">
				<div class="hide flex">
					<p class="hide-filters">Hide Filters</p>
					<i class="ri-equalizer-line"></i>
				</div>
				<div class="show-wrapper">
				<div class="show flex">
					<p class="filters">Filters</p>
					<i class="ri-equalizer-line"></i>
				</div>
				</div>
				<div class="flex">
					<div class="dropdown">
						<div class="select flex">
							<%
							if (sortActive != null) {
							%>
							<span class="selected">Sort By:<span class="selected1"><%=sortActive%></span></span>
							<%
							} else {
							%>
							<span class="selected">Sort By<span class="selected1"></span></span>
							<%
							}
							%>

							<div class="caret">
								<i class=" fa-solid fa-chevron-up"></i>
							</div>
						</div>
						<ul class="menu">
							<form action="./sort">
								<div class="menu-items flex">
									<input class="<%=featured%>" type="submit" name="sortBy" value="Featured">
									<input class="<%=newest%>" type="submit" name="sortBy" value="Newest">
									<input class="<%=highToLow%>" type="submit" name="sortBy" value="Price: High-Low">
									<input class="<%=lowToHigh%>" type="submit" name="sortBy" value="Price: Low-High">
									<input hidden type="text" name="otherActive" value="<%=otherActive%>">
								</div>
							</form>
						</ul>
					</div>
				</div>
			</div>
			
			<div class=" card-container  flex" id="card-container">
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
							<img class="imgBoxDisplay mainImg"
								src="./resource/assests/<%=pojo.getImage()%>" alt="cleats">
							<%
							}
							%>
							<%
							if (pojo.getImage2() != null) {
							%>
							<img class="imgBoxDisplay"
								src="./resource/assests/<%=pojo.getImage2()%>" alt="cleats">
							<%
							}
							%>
							<%
							if (pojo.getImage3() != null) {
							%>
							<img class="imgBoxDisplay"
								src="./resource/assests/<%=pojo.getImage3()%>" alt="cleats">
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
						<input hidden="true" type="number" name="productId"
							value="<%=pojo.getId()%>">
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
									<input  type="submit"
										value="<%=pojo.getSize7()%>" name="size">
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
									<input type="submit" value="<%=pojo.getSize10()%>" name="size">
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
								<h3>
									Price:$<%=pojo.getPrice()%></h3>
							</div>
							<div class="buttons">
								<input hidden="true" type="number" name="size" value="<%=size%>">
								<input hidden="true" type="number" name="id" value=<%=pojo.getId()%>>
								<input hidden="true" type="text" name="btnId" value="0">
								<input type="submit" value="-" class="btn">
								<form action="./addCart">
									<input hidden="true" type="number" name="pageId" value="1">
									<input hidden="true" type="number" name="id" value=<%=pojo.getId()%>>
									<input hidden="true" class="buttons-size" type="number" name="size" value="0">
									<input hidden="true" class="buttons-color" type="text" name="color" value="">
									<input hidden="true" class="buttons-quantity" type="text" name="prodQuantity" value="1">
									<input hidden type="text" name="otherActive" value="<%=otherActive%>">
									<input hidden type="text" name="sortActive" value="<%=sortActive%>">
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
								System.out.println(i);
								System.out.println(item);
								String ProdId = item.substring(0, i);
								int id = Integer.parseInt(ProdId);
							%>
							
							<%
							if (pojo.getId() == id) {
							%>
							<input hidden="true" class="productDetails" type="text" name="" value="<%=item%>">
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
								if ( items[0].equals(pojo.getId())) {
									
							%>
							<div id="<%=pojo.getId()%>" class="quantity"><%=items[1]%></div>
							<p class="stock">
								InStock Only
								<%=pojo.getQuantity()%>
								Left
							</p>
							<%
							}
							%>
							<% 	
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
				
		</section>

	</div>

	<!-- Popup -->
	<%
	if (cart != null) {
	%>
	<div class="popup" id="popup">
		<div class="popup-container ">
			<div class="popup-top flex">
				<div class="popup-top-img flex">
					<img src="./resource/assests/pngwing.com (3).png" alt="green tick"
						width="20px">
					<p>Added to Bag</p>
				</div>
				<div class="close-icon">
					<i class="bi bi-x-lg"></i>
				</div>
			</div>
			<div class="popup-content flex">
				<img src="./resource/assests/<%=cart.getImage()%>" alt="green tick"
					width="100px">
				<div class="popup-text">
					<p><%=cart.getProducts().getName()%></p>
					<p>Men's Football Shoes</p>
					<p>
						Size UK
						<%=cart.getSize()%></p>
					<p>MRP : $ 12 795.00</p>
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
		<form action="./login" class="form-input">
			<input hidden="true" type="number" name="pageId" value="1">
			<input hidden type="number" name="id" value="0">
			<input hidden class="getSizeValue" type="number" name="size" value="0">
			<input hidden class="colorChoice" type="text" name="color" value="null">
			<input hidden type="text" name="otherActive" value="<%=otherActive%>">
			<input hidden type="text" name="sortActive" value="<%=sortActive%>">
			<input hidden type="number" name="loginPageReturn" value="1">
			<div class="input">
				<div class="input-field">
					<label>Email</label>
					 <input id="email" type="email" placeholder="example123@gmail.com" name="email" class="input-area" required="required">
					<p></p>
				</div>
				<div class="input-field">
					<label>Password</label> <input id="password" type="password" placeholder="Your password" name="password" class="input-area" required="required">
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
				 <a href="./signupPage?pageId=<%=pageId%>&otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&id=0&size=0&color=null">Sign up</a>
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


	<script type="text/javascript" src="./resource/js/products.js"></script>
	<script src="https://kit.fontawesome.com/cb23ed1dd0.js"
		crossorigin="anonymous"></script>
	<script type="text/javascript">

	</script>

</body>
</html>