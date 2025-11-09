<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Person"%>
<%@ page import="entities.Club"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	LinkedList<Person> peopleList = (LinkedList<Person>) request.getAttribute("peopleList");
	LinkedList<Club> clubsList = (LinkedList<Club>) request.getAttribute("clubsList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Contrato</title>
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
		    <h2 class="mt-4">Agregar Contrato</h2>
		    <form action="actioncontract" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		        
		        <div class="form-group">
		            <label for="startDate">Fecha de Inicio:</label>
		            <input type="date" class="form-control" id="startDate" name="startDate" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="endDate">Fecha de Fin:</label>
		            <input type="date" class="form-control" id="endDate" name="endDate" required />
		        </div>
		
		        <div class="form-group">
		            <label for="salary">Salario:</label>
		            <input type="text" class="form-control" id="salary" name="salary" required />
		        </div>
		
		        <div class="form-group">
		            <label for="releaseClause">Cláusula de Rescisión:</label>
		            <input type="text" class="form-control" id="releaseClause" name="releaseClause" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_person">Persona:</label>
				    <select name="id_person" id="id_person" class="form-control" required>
				        <option value="">-- Seleccioná una persona --</option>
				        <%
				            if (peopleList != null && !peopleList.isEmpty()) {
				                for (Person p : peopleList) {
				                    out.print("<option value='" + p.getId() + "'>" + p.getFullname() + "</option>");
				                }
				            } else {
				                out.print("<option value='' disabled>No hay personas cargadas</option>");
				            }
				        %>
				    </select>
				</div>
				
				<div class="form-group">
				    <label for="id_club">Club:</label>
				    <select name="id_club" id="id_club" class="form-control" required>
				        <option value="">-- Seleccioná un club --</option>
				        <%
				            if (clubsList != null && !clubsList.isEmpty()) {
				                for (Club c : clubsList) {
				                    out.print("<option value='" + c.getId() + "'>" + c.getName() + "</option>");
				                }
				            } else {
				                out.print("<option value='' disabled>No hay clubes cargados</option>");
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