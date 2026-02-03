<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>¡Error!</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/signin.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body style="background-color: #10442E;">
		
		<%
    		String referer = request.getHeader("referer");
		%>
	
		<form class="form-signin border border-white border-1 rounded rounded-3" style="text-align: center; background-color: rgba(33,37,41,1)" action="<%= referer != null ? referer : "Home" %>" method="get">
	    	<h1 class="h3 mb-3 font-weight-normal" style="color: white">¡Error!</h1>
	      	<label style="color:white;"><%= request.getAttribute("errorMessage") %></label>
	      	<button class="btn btn-lg btn-block text-white" style="background-color:#1A6B32; border-color:#1A6B32;" type="submit">Volver</button>
	    </form>
	</body>
</html>