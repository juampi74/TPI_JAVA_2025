<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Stadium"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	LinkedList<Stadium> stadiumsList = (LinkedList<Stadium>) request.getAttribute("stadiumsList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Club</title>
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
		    <h2 class="mt-4">Agregar Club</h2>
		    <form action="actionclub" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" required />
		        </div>
		
		        <div class="form-group">
		            <label for="foundationDate">Fecha de Fundación:</label>
		            <input type="date" class="form-control" id="foundationDate" name="foundationDate" required />
		        </div>
		
		        <div class="form-group">
		            <label for="phoneNumber">Número de Teléfono:</label>
		            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="email">Email:</label>
		            <input type="text" class="form-control" id="email" name="email" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="badgeImage">Escudo:</label>
		            <input type="text" class="form-control" id="badgeImage" name="badgeImage" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="budget">Presupuesto:</label>
		            <input type="text" class="form-control" id="budget" name="budget" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_stadium">Estadio:</label>
				    <select name="id_stadium" id="id_stadium" class="form-control" required>
				        <option value="">-- Seleccioná un estadio --</option>
				        <%
				            if (stadiumsList != null && !stadiumsList.isEmpty()) {
				                for (Stadium s : stadiumsList) {
				                    out.print("<option value='" + s.getId() + "'>" + s.getName() + "</option>");
				                }
				            } else {
				                out.print("<option value='' disabled>No hay estadios cargados</option>");
				            }
				        %>
				    </select>
				</div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn btn-primary">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>