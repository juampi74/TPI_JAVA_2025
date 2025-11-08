<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Association"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	LinkedList<Association> associationsList = (LinkedList<Association>) request.getAttribute("associationsList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Torneo</title>
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
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Agregar Torneo</h2>
		    <form action="actiontournament" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" required />
		        </div>
		
		        <div class="form-group">
		            <label for="startDate">Fecha de Inicio:</label>
		            <input type="date" class="form-control" id="startDate" name="startDate" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="endDate">Fecha de Fin:</label>
		            <input type="date" class="form-control" id="endDate" name="endDate" required />
		        </div>
		
		        <div class="form-group">
		            <label for="format">Formato:</label>
		            <input type="text" class="form-control" id="format" name="format" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="season">Edición:</label>
		            <input type="text" class="form-control" id="season" name="season" required />
		        </div>
		               
		        <div class="form-group">
				    <label for="id_association">Asociación:</label>
				    <select name="id_association" id="id_association" class="form-control" required>
				        <option value="">-- Seleccioná una asociación --</option>
				        <%
				            if (associationsList != null && !associationsList.isEmpty()) {
				                for (Association a : associationsList) {
				                    out.print("<option value='" + a.getId() + "'>" + a.getName() + "</option>");
				                }
				            } else {
				                out.print("<option value='' disabled>No hay asociaciones cargadas</option>");
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