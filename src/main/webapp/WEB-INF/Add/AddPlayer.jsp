<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Position"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	LinkedList<Position> positionsList = (LinkedList<Position>) request.getAttribute("positionsList");
%>
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
		            <label for="height">Altura (mts):</label>
		            <input type="number" class="form-control" id="height" name="height" step="0.01" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="weight">Peso (kg):</label>
		            <input type="number" class="form-control" id="weight" name="weight" step="0.01" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="photo">Foto:</label>
		            <input type="text" class="form-control" id="photo" name="photo" maxlength="250" required />
		        </div>
		        
		        <div class="form-group">
				    <label>Posiciones:</label><br>
				
				    <%
				        if (positionsList != null && !positionsList.isEmpty()) {
				            for (Position p : positionsList) {
				                out.print("<div class='form-check'>");
				                out.print("<input type='checkbox' class='form-check-input' "
				                        + "name='positions' id='pos_" + p.getId() + "' "
				                        + "value='" + p.getId() + "'>");
				                out.print("<label class='form-check-label' for='pos_" + p.getId() + "'>"
				                        + p.getDescription() + "</label>");
				                out.print("</div>");
				            }
				        } else {
				            out.print("<p class='text-white'>No hay posiciones cargadas</p>");
				        }
				    %>
				</div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>