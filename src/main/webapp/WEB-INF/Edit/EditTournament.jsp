<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Tournament"%>
<%@ page import="entities.Association"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Tournament tournament = (Tournament) request.getAttribute("tournament");
	LinkedList<Association> associationsList = (LinkedList<Association>) request.getAttribute("associationsList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Torneo</title>
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
		    <h2 class="mt-4">Editar Torneo</h2>
		    <form action="actiontournament" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=tournament.getId()%>" />
		        
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" value="<%=tournament.getName()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="startDate">Fecha de Inicio:</label>
		            <input type="date" class="form-control" id="startDate" name="startDate" value="<%=tournament.getStartDate()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="endDate">Fecha de Fin:</label>
		            <input type="date" class="form-control" id="endDate" name="endDate" value="<%=tournament.getEndDate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="format">Formato:</label>
		            <input type="text" class="form-control" id="format" name="format" value="<%=tournament.getFormat()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="season">Edición:</label>
		            <input type="text" class="form-control" id="season" name="season" value="<%=tournament.getSeason()%>" required />
		        </div>
		              
		        <div class="form-group">
				    <label for="id_association">Asociación:</label>
				    <select name="id_association" id="id_association" class="form-control" required>
					    <option value="">-- Seleccioná una asociación --</option>
					    <%
					        if (associationsList != null && !associationsList.isEmpty()) {
					            int selectedAssociationId = (tournament != null && tournament.getAssociation() != null) ? tournament.getAssociation().getId() : -1;
					            for (Association a : associationsList) {
					                String selected = (a.getId() == selectedAssociationId) ? "selected" : "";
					                out.print("<option value='" + a.getId() + "' " + selected + ">" + a.getName() + "</option>");
					            }
					        } else {
					            out.print("<option value='' disabled>No hay asociaciones cargadas</option>");
					        }
					    %>
					</select>
				</div>
				
		        <div class="button-container mb-3">
			        <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>