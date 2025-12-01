<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Stadium"%>
<%@ page import="entities.Nationality"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	LinkedList<Stadium> stadiumsList = (LinkedList<Stadium>) request.getAttribute("stadiumsList");
	LinkedList<Nationality> nationalitiesList = (LinkedList<Nationality>) request.getAttribute("nationalitiesList");
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
		    
		    <form action="actionclub" method="post" enctype="multipart/form-data" class="mt-4">
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
		            <input type="file" class="form-control" id="badgeImage" name="badgeImage" maxlength="250" accept="image/*" required />
		            <small class="form-text text-white-50">Formatos recomendados: PNG o JPG.</small>
		        </div>
		        
		        <div class="form-group">
		            <label for="budget">Presupuesto:</label>
		            <input type="number" class="form-control" id="budget" name="budget" required />
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
				
				<div class="form-group">
				    <label for="id_nationality">Nacionalidad:</label>
				    <select name="id_nationality" id="id_nationality" class="form-control" required>
				        <option value="">-- Seleccioná una nacionalidad --</option>
				        <%
				            if (nationalitiesList != null && !nationalitiesList.isEmpty()) {
				                for (Nationality n : nationalitiesList) {
				                    out.print("<option value='" + n.getId() + "'>" + n.getName() + "</option>");
				                }
				            } else {
				                out.print("<option value='' disabled>No hay nacionalidades cargadas</option>");
				            }
				        %>
				    </select>
				</div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>