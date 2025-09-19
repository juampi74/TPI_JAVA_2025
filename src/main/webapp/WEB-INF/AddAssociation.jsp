<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Asociación</title>
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
		    <h2 class="mt-4">Agregar Asociacion</h2>
		    <form action="actionassociation" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		        
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" required />
		        </div>
		
		        <div class="form-group">
		            <label for="creationDate">Fecha de Creación:</label>
		            <input type="date" class="form-control" id="creationDate" name="creationDate" required />
		        </div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn btn-primary">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>