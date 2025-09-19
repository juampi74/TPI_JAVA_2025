<%@ page import="entities.Person"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Person person = (Person) request.getAttribute("person");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Persona</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <style>
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	    </style>
	</head>
	<body>
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container">
		    <h2 class="mt-4">Editar Persona</h2>
		    <form action="actionperson" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=person.getId()%>" />
		        
		        <div class="form-group">
		            <label for="fullname">Apellido y Nombre:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" value="<%=person.getFullname()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<%=person.getBirthdate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" value="<%=person.getAddress()%>" required />
		        </div>
		        
		        <div class="button-container mb-3">
			          <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
			          <button type="submit" class="btn btn-primary">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>