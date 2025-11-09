<%@ page import="entities.Association"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Association association = (Association) request.getAttribute("association");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Asociación</title>
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
		    <h2 class="mt-4">Editar Asociación</h2>
		    <form action="actionassociation" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=association.getId()%>" />
		        
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" value="<%=association.getName()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="creationDate">Fecha de Creación:</label>
		            <input type="date" class="form-control" id="creationDate" name="creationDate" value="<%=association.getCreationDate()%>" required />
		        </div>
		        
		        <div class="button-container mb-3">
			          <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
			          <button type="submit" class="btn btn-primary">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>