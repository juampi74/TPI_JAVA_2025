<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Jugador</title>
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
		    <h2 class="mt-4">Agregar Jugador</h2>
		    <form action="actionplayer" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		        
		        <div class="form-group">
		            <label for="id">DNI:</label>
		            <input type="number" class="form-control" id="id" name="id" required />
		        </div>
		
		        <div class="form-group">
		            <label for="fullname">Apellido y Nombre:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" required />
		        </div>
		
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" required />
		        </div>
		
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="dominantFoot">Pie Dominante:</label>
		            <input type="text" class="form-control" id="dominantFoot" name="dominantFoot" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="jerseyNumber">Número de Camiseta:</label>
		            <input type="number" class="form-control" id="jerseyNumber" name="jerseyNumber" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="height">Altura (cm):</label>
		            <input type="number" class="form-control" id="height" name="height" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="weight">Peso (kg):</label>
		            <input type="number" class="form-control" id="weight" name="weight" required />
		        </div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>