<%@page import="com.jspiders.mvcproject1.pojo.CartAdminPOJO"%>
<%@page import="java.util.List"%>
<%@page import="com.jspiders.mvcproject1.pojo.OrdersPOJO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%
 List<OrdersPOJO> allOrders = (List<OrdersPOJO>) request.getAttribute("allOrders");
 OrdersPOJO orderDetail = (OrdersPOJO) request.getAttribute("orderDetail");
 Long totalAmount = (Long) request.getAttribute("totalAmount");
 double totalBudget = (double) request.getAttribute("totalBudget");
 %>   
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Nike-DashBorad</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
 <!-- Material Icons -->
    <link href="https://fonts.googleapis.com/css2?family=Material+Icons+Sharp" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/resource/css/adminDashborad.css'/>">
</head>
<style>

</style>
<body>
<div class="container">
        <aside>
            <div class="top">
                <div class="logo">
                    <img src="./resource/assests/black-and-white-brand-pattern-nike-logo-png-f10610cd223765f9cb76402f10be85ca.png" alt="logo">
                    <h2>Nike</h2>
                </div>
                <div class="close" id="close-btn">
                    <span class="material-icons-sharp">close </span>
                </div>
            </div>


            <div class="sidebar">
                <a href="#">
                    <span class="material-icons-sharp">grid_view</span>
                    <h3>Dashboard</h3>
                </a>
                <a href="#" class="customer active" >
                    <span class="material-icons-sharp">person_outline</span>
                    <h3>Customers *</h3>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">receipt_long</span>
                    <h3>Orders</h3>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">insights</span>
                    <h3>Analytics</h3>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">mail_outline</span>
                    <h3>Messages</h3>
                    <span class="message-count">26</span>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">inventory</span>
                    <h3>Products</h3>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">report</span>
                    <h3>Reports</h3>
                </a>
                <a href="#">
                    <span class="material-icons-sharp">settings</span>
                    <h3>Settings</h3>
                </a>
                <a href="#" class="add-product">
                    <span class="material-icons-sharp">add</span>
                    <h3>Add Product *</h3>
                </a>
                <a href="./adminLogout">
                    <span class="material-icons-sharp">logout</span>
                    <h3>Logout *</h3>
                </a>
            </div>
        </aside>
        <!-- ------------- End Of Aside---------------->
        <main class="dassboard">
            <h1>Dashboard</h1>
            <div class="date">
                <input type="date">
            </div>
            <div class="insights">
                <div class="sales">
                    <span class="material-icons-sharp">analytics</span>
                    <div class="middle">
                        <div class="left">
                            <h3>Total Sales</h3>
                            <h1>$<%=totalAmount%></h1>
                        </div>
                        <div class="progress">
                            <svg>
                                <circle cx="38" cy="38" r="36"></circle>
                            </svg>
                            <div class="number">
                                <p><%= (long)((totalAmount / totalBudget) * 100) %>%</p>
                            </div>
                        </div>
                    </div>
                    <small class="text-muted">Last 24 Hours </small>
                </div>
                <!----------------- End Of Sales--------------- -->
                <div class="expenses">
                    <span class="material-icons-sharp">bar_chart</span>
                    <div class="middle">
                        <div class="left">
                            <h3>Total Expenses</h3>
                            <h1>$14,160</h1>
                        </div>
                        <div class="progress">
                            <svg>
                                <circle cx="38" cy="38" r="36"></circle>
                            </svg>
                            <div class="number">
                                <p>62%</p>
                            </div>
                        </div>
                    </div>
                    <small class="text-muted">Last 24 Hours </small>
                </div>
                <!----------------- End Of EXPENSES--------------- -->
                <div class="income">
                    <span class="material-icons-sharp">stacked_line_chart</span>
                    <div class="middle">
                        <div class="left">
                            <h3>Total Income</h3>
                          <% 
                          double income = totalAmount - (totalAmount * 0.5);
                          %>
                            <h1>$<%=(long)income%></h1>
                        </div>
                        <div class="progress">
                            <svg>
                                <circle cx="38" cy="38" r="36"></circle>
                            </svg>
                            <div class="number">
                            	 <% 
                          double TotalIncome = totalBudget - (totalBudget * 0.5);
                          %>
                                 <p><%= (long)((income / TotalIncome) * 100) %>%</p>
                       </div>                                         
                        </div>
                    </div>
                    <small class="text-muted">Last 24 Hours </small>
                </div>
                <!----------------- End Of Income--------------- -->
            </div>
            <!-------------------END of Insights------------->
            <div class="recent-orders">
                <h2>Recent Orders</h2>
                <div class="recent-wrapper">
                <table>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Order Id</th>
                            <th>Payment Id</th>
                            <th>Status</th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                    <%for(OrdersPOJO order : allOrders){ %>
                    
                        <tr>
                            <td><%=order.getFirstName() %> <%= order.getLastName() %></td>
                            <td><%=order.getOrderId() %></td>
                            <td><%=order.getPaymentId() %></td>
                            <td class="warning">Pending</td>
                            <td class="primary details"><a href="./details?orderId=<%=order.getId()  %>">Details *</a></td>
                         
                        </tr>
                      
                        <%} %> 
                    </tbody>
                </table>
                </div>
                <a class = "showAll" href="#">Show All *</a>
            </div>
            
            
        </main>
        
        <main class="main-add-products">
        <h1>Add Products</h1>
        <form action="./addPopularProducts" enctype="multipart/form-data"
			method="post">
        <div class="add-products-page">
		<div class="add-products-content">
		    <label for="productType">Type</label>
		    <input type="text" id="productType" name="productType" placeholder="Enter the product type" required  >
		</div>
		<div class="add-products-content">
		    <label for="name">Name</label>
		    <input type="text" id="name" name="name" placeholder="Enter the product name"   required>
		</div>
		<div class="add-products-content">
		    <label for="description">Description</label>
		    <input type="text" id="description" name="description" placeholder="Enter the product description"  required   >
		</div>
		<div class="add-products-content">
		    <label for="brand">Brand</label>
		    <input type="text" id="brand" name="brand" placeholder="Enter the product brand"  required >
		</div>
		<div class="add-products-content">
		    <label for="sSize">sSize</label>
		    <select id="sSize" name="sSize"  required >
		        <option value="" disabled selected>Select sSize</option>
		        <option value="7">7</option>
		        <option value="0">Not available</option>
		    </select>
		</div>
		<div class="add-products-content">
		    <label for="mSize">mSize</label>
		    <select id="mSize" name="mSize"  required>
		        <option value="" disabled selected>Select mSize</option>
		        <option value="8">8</option>
		        <option value="0">Not available</option>
		    </select>
		</div>
		<div class="add-products-content">
		    <label for="lSize">lSize</label>
		    <select id="lSize" name="lSize"  required >
		        <option value="" disabled selected>Select lSize</option>
		        <option value="9">9</option>
		        <option value="0">Not available</option>
		    </select>
		</div>
		<div class="add-products-content">
		    <label for="xSize">xSize</label>
		    <select id="xSize" name="xSize"  required >
		        <option value="" disabled selected>Select xSize</option>
		        <option value="10">10</option>
		        <option value="0">Not available</option>
		    </select>
		</div>
		<div class="add-products-content">
		    <label for="price">Price</label>
		    <input type="number" id="price" name="price" placeholder="Enter the product price"  required >
		</div>
			<div class="add-products-content">
		    <label for="quantity">Quantity</label>
		    <input type="number" id="quantity" name="quantity" placeholder="Enter the product quantity"  required >
		</div>
			
		<div class="add-products-content">
		    <label for="gender">Gender</label>
		    <select id="gender" name="gender"  required>
		        <option value="" disabled selected>Select gender</option>
		        <option value="male">Male</option>
		        <option value="female">Female</option>
		    </select>
		</div>
		
			<div class="add-products-content">
		    <label for="sale">Sale</label>
		    <select id="sale" name="sale"  required >
		        <option value="" disabled selected>Add as sale</option>
		        <option value="sale">Yes</option>
		        <option value="">No</option>
		    </select>
		</div>
		
			<div class="add-products-content">
		    <label for="newest">Newest</label>
		    <select id="newest" name="newest"  required>
		        <option value="" disabled selected>Add as newest</option>
		        <option value="newest">Yes</option>
		        <option value="">No</option>
		    </select>
		</div>
		
			<div class="add-products-content">
		    <label for="image">Image</label>
		    <input type="file" id="image" name="images" placeholder="Add the product image"  required >
			</div>
			<div class="add-products-content">
		    <label for="orColor">Color</label>
		    <input type="text" id="orColor" name="orColor" placeholder="Enter the product Color (1)"  required >
			</div>
			<div class="add-products-content">
		    <label for="image2">Image2</label>
		    <input type="file" id="image2" name="image2" placeholder="Add the product image"  required >
			</div>
			<div class="add-products-content">
		    <label for="bColor">Color2</label>
		    <input type="text" id="bColor" name="bColor" placeholder="Enter the product Color (2)"  required >
			</div>
		
			<div class="add-products-content">
		    <label for="image3">Image3</label>
		    <input type="file" id="image3" name="image3" placeholder="Add the product image"  required  >
			</div>
			<div class="add-products-content">
		    <label for="yColor">Color3</label>
		    <input type="text" id="yColor" name="yColor" placeholder="Enter the product Color (3)"  required >
			</div>
	
		
			
			<input class="submit-btn" type="submit" value="Add">
		
        </div>
        </form>
        </main>
        
        
        <div class="body overlay " id="body"></div>
        
        <% 
        if(orderDetail != null){
        	%>
        
        <div class="detail-info">
        <div class="close-icon">
		<i class="bi bi-x-lg"></i>
		</div>
        <div class="detail-box">
        <table >
        <thead>
        <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Amount</th>
        <th>Address</th>
        <th>Date</th>
        <th>Order Id</th>
        <th>Payment Id</th>
        </tr>
 		</thead>
         <tbody>
         <tr>
         <td><%=orderDetail.getFirstName() %> <%=orderDetail.getLastName() %></td>
        <td><%=orderDetail.getEmail() %></td>
        <td><%=orderDetail.getPhone() %></td>
        <td><%=orderDetail.getAmount() %></td>
        <td><%=orderDetail.getAddress() %> <%=orderDetail.getPostalCode() %> <%=orderDetail.getLocality() %> <%=orderDetail.getCountry() %></td>
        <td><%=orderDetail.getDate() %></td>
        <td><%=orderDetail.getOrderId() %></td>
        <td><%=orderDetail.getPaymentId() %></td>
         </tr>
       </tbody>
       </table>
       </div>
       <div class="detail-products">
       <table>
       <thead>
       <tr>
       <th>Product Image</th>
       <th>Product Id</th>
       <th>Price</th>
       <th>Quantity</th>
       <th>Size</th>
       <th>Color</th>
       </tr>
       </thead>
  
       <%for(CartAdminPOJO item : orderDetail.getCart()){ %>
       <tbody>
       <td class="detail-img"><img alt="cleats" src="./resource/assests/<%=item.getImage()%>" ></td>
       <td><%=item.getProducts().getId() %></td>
        <td><%=item.getProducts().getPrice() %></td>
       <td><%=item.getQuantity() %></td>
       <td><%=item.getSize() %></td>
       <td><%=item.getColor() %></td>
      
       </tbody>
       <%} %>
      
       </table>
       </div>
      
       </div>
        <% 
        }
        %>
        
        <!---------------------END OF MAIN--------------------->
        <div class="right">
            <div class="top">
                <button id="menu-btn">
                    <span class="material-icons-sharp">
                        menu </span>
                </button>
                <div class="theme-toggler">
                    <span class="material-icons-sharp ">light_mode</span>
                    <span class="material-icons-sharp active">dark_mode</span>
                </div>
                <div class="profile">
                    <div class="info">
                        <p>Hey, <b>Daniel</b></p>
                        <small class="text-muted">Admin</small>
                    </div>
                    <div class="profile-photo">
                        <img src="./resource/assests/profile-1.jpg" alt="profile">
                    </div>
                </div>
            </div>
            <!-- END OF TOP -->
            <div class="recent-updates">
                <h2>Recent Updates</h2>
                <div class="updates">
                    <div class="update">
                        <div class="profile-photo">
                            <img src="./resource/assests//profile-2.jpg" alt="">
                        </div>
                        <div class="message">
                            <p><b>Mike Tyson</b> recived his order of 
							Nike Mercurial Vapor xI(FG)Firm-Ground</p>
                            <small class="text-muted">2 Minutes Ago</small>
                        </div>
                    </div>
                    <div class="update">
                        <div class="profile-photo">
                            <img src="./resource/assests/profile-3.jpg" alt="">
                        </div>
                        <div class="message">
                            <p><b>Mike Tyson</b> recived his order of 
							Nike Mercurial Vapor xI(FG)Firm-Ground</p>
                            <small class="text-muted">2 Minutes Ago</small>
                        </div>
                    </div>
                    <div class="update">
                        <div class="profile-photo">
                            <img src="./resource/assests//profile-4.jpg" alt="">
                        </div>
                        <div class="message">
                            <p><b>Mike Tyson</b> recived his order of 
								Nike Mercurial Vapor xI(FG)Firm-Ground</p>
                            <small class="text-muted">2 Minutes Ago</small>
                        </div>
                    </div>
                </div>
            </div>
            <!-- --------------END OF RECENT UPDATES -->
            <div class="sales-analytics">
                <h2>Sales Analytics</h2>
                <div class="item online">
                    <div class="icon">
                        <span class="material-icons-sharp">shopping_cart</span>
                    </div>
                    <div class="right">
                        <div class="info">
                            <h3>ONLINE ORDERS</h3>
                            <small class="text-muted">Last 24 Hours</small>
                        </div>
                        <h5 class="success">+39%</h5>
                        <h3>3849</h3>
                    </div>
                </div>
                <div class="item offline">
                    <div class="icon">
                        <span class="material-icons-sharp">local_mall</span>
                    </div>
                    <div class="right"> 
                        <div class="info">
                            <h3>OFFLINE ORDERS</h3>
                            <small class="text-muted">Last 24 Hours</small>
                        </div>
                        <h5 class="danger">-17%</h5>
                        <h3>1100</h3>
                    </div>
                </div>
                <div class="item customers">
                    <div class="icon">
                        <span class="material-icons-sharp">person</span>
                    </div>
                    <div class="right"> 
                        <div class="info">
                            <h3>NEW CUSTOMERS</h3>
                            <small class="text-muted">Last 24 Hours</small>
                        </div>
                        <h5 class="success">+25%</h5>
                        <h3>849</h3>
                    </div>
                </div>
                <div class="item add-product">
                    <div>
                        <span class="material-icons-sharp">add</span>
                        <h3>Add Products *</h3>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript" src="./resource/js/adminDashborad.js" defer></script>
    
</body>
</html>