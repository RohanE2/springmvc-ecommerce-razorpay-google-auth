<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%
   String msg = (String) request.getAttribute("msg");
   int pageId = (int) request.getAttribute("pageId");
   String otherActive = (String) request.getAttribute("otherActive");
   String sortActive = (String) request.getAttribute("sortActive");
   int size = (int) request.getAttribute("size");
   String color = (String) request.getAttribute("color");
   int id = (int) request.getAttribute("id");

   %> 
 
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
  
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Welcome to Nike - Sign-In</title>
<link rel="stylesheet" href="<c:url value='/resource/css/Login.css'/>">
</head>
<style>

</style>
<body>
	<div class="outer-box">
    <div class="inner-box">
        <div class="details">
        	<div class="info">
        	<h2>Welcome back!</h2>
        	<p>Enter your username and password to log in to your admin panel.</p>
        	</div>
        	<div>
    <!--     	<a href="https://accounts.google.com/o/oauth2/auth?client_id=217084231230-aigo48lncb4amfnogq1sn5heuca154ol.apps.googleusercontent.com&redirect_uri=http://localhost:8080/mvcproject1/&scope=openid%20email%20profile&response_type=code">  
 <a href=""> -->
		<a href="https://accounts.google.com/o/oauth2/auth?client_id=217084231230-aigo48lncb4amfnogq1sn5heuca154ol.apps.googleusercontent.com&redirect_uri=http://localhost:8080/mvcproject1/callback&scope=openid%20email%20profile&response_type=code">  
    		<button>
    			<img alt="" src="./resource/assests/icons8-google-48.png">
    			Log in with Google
    		</button>
			</a>

        	</div>
        	<form action ="./login" >
        	<input hidden="true" type="number" name="pageId" value="<%=pageId%>">
        	<input hidden type = "number" name = "id" value = "<%=id%>">
			<input hidden class="getSizeValue"  type = "number" name = "size" value = "<%=size%>">
				<input hidden class ="colorChoice" type="text" name="color" value="<%=color%>">
				<input hidden type="text" name="otherActive" value="<%=otherActive%>">
				<input hidden type="text" name="sortActive" value="<%=sortActive%>">
				<input hidden type="number" name="loginPageReturn" value="0">
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
        	<a href="./signupPage?pageId=<%=pageId%>&otherActive=<%=otherActive%>&sortActive=<%=sortActive%>&id=<%=id%>&size=<%=size%>&color=<%=color%>">Sign up</a>
        	</div>
        	<div class="signup">
        	<a>Forgot your password?</a>
        	</div>
        	</div>
        </div>
        <div class="card">
        <img alt="" src="./resource/assests/8dac2a147371627.62c187c24482e.jpg">
        </div>
         <%
    if(msg != null){
    %>
    <h3 class = "msg"><%=msg %></h3>	
    <% 
    } 
    %>
    </div> 
    <div class="gradient"></div>
   
    
</div>
<script type="text/javascript" src="./resource/js/Login.js" defer></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>

</body>
</html>
