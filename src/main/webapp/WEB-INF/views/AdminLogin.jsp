<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%
   String msg = (String) request.getAttribute("msg");
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
 

        	</div>
        	<form action ="./AdminLogin" method="post" >
        	
        	<div class="input">
        	<div class="input-field">
        	<label>UserID</label>
        	<input id="userID" type="text" placeholder="example123 (user)" name="userID" class="input-area" required="required">
        	<p></p>
        	</div>
        	<div class="input-field">
        	<label>Password</label>
        	<input id="password" type="password" placeholder="Your password (user123)" name="password" class="input-area" required="required">
        	<p></p>
        	</div>
        	</div>
        	<div class="checkbox">
        	<input type="checkbox">
        	<label>Remember me</label>
        	</div>
        	<input readonly type="submit" class="login" value ="Login In">
        	</form>
        	
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

</body>
</html>
