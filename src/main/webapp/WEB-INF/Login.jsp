<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
		<title>Login</title>
		<link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/signin.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body class="text-center d-flex align-items-center justify-content-center"
	      style="min-height:100vh; background-color: #10442E;">
	
	    <div class="form-signin border border-white p-4"
	          style="
	              background-color: rgba(33,37,41,1);
	              max-width: 400px;
	              width: 100%;
	              box-shadow: 0 0 20px rgba(0,0,0,.4);
	              border-radius: 1rem;
	          ">
	        
	        <img class="mb-4" src="${pageContext.request.contextPath}/assets/TeamUp_Logo.png" alt="TeamUp Logo"
	             width="200" height="130"
	             style="background-color: white; border-radius: .5rem; padding: 10px;">
	
	        <h1 class="h3 mb-3 font-weight-normal text-white">Iniciar Sesión</h1>
	
	        <% 
			    String flash = (String) request.getAttribute("flash");
			    String cssClass = (String) request.getAttribute("cssClass");
			
			    if (flash != null && cssClass == null) {

			    	cssClass = "alert-danger";
			    
			    }
			%>
			
			<% if (flash != null) { %>
			    <div class="alert <%= cssClass %> p-2 mb-3 shadow-sm" role="alert" style="font-size: 0.9rem;">
			        <%= flash %>
			    </div>
			<% } %>
	
	        <form action="${pageContext.request.contextPath}/login" method="post">
	        	<input type="hidden" name="origin" 
           			   value="<%= request.getAttribute("origin") != null ? request.getAttribute("origin") : "" %>">
	        
	            <div class="form-group mb-3 text-start">
	                <label for="email" class="text-white mb-1">Email</label>
	                <input type="email" id="email" name="email" class="form-control" placeholder="ejemplo@teamup.com" 
	                       value="<%= request.getAttribute("prevEmail") != null ? request.getAttribute("prevEmail") : "" %>" 
	                       required autofocus>
	            </div>
	
	            <div class="form-group mb-4 text-start">
	                <label for="password" class="text-white mb-1">Contraseña</label>
	                <input type="password" id="password" name="password" class="form-control" placeholder="******" required>
	            </div>
	
	            <button class="btn btn-lg btn-block text-white w-100"
	                    style="background-color:#1A6B32; border-color:#1A6B32;"
	                    type="submit">
	                Ingresar
	            </button>
	        </form>
	        
	        <div class="mt-4">
	            <a href="${pageContext.request.contextPath}/register" class="text-warning text-decoration-none" style="font-size: 0.9rem;">
	                ¿No tenés cuenta? Solicitá tu registro
	            </a>
	        </div>
	        
	        <div class="mt-3"> <a href="${pageContext.request.contextPath}/Home" class="text-white-50" style="font-size: 0.9rem;">
	                Volver al inicio (Entrar como Invitado)
	            </a>
	        </div>
	        
	    </div>
	
	</body>
</html>