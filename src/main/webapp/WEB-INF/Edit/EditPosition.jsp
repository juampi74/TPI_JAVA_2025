<%@ page import="entities.Position"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Position position = (Position) request.getAttribute("position");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Posición</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <style>
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	    </style>
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Editar Posición</h2>
		    <form action="actionposition" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=position.getId()%>" />
		        
		        <div class="form-group">
		            <label for="description">Descripción:</label>
		            <input type="text" class="form-control" id="description" name="description" value="<%=position.getDescription()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="abbreviation">Abreviatura:</label>
		            <input type="text" class="form-control" id="abbreviation" name="abbreviation" value="<%=position.getAbbreviation()%>" required />
		        </div>
		        
		        <div class="button-container mb-3">
		        	<button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>