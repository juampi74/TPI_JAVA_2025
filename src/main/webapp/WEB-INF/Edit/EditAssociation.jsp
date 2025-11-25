<%@ page import="entities.Association"%>
<%@ page import="enums.*"%>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Association association = (Association) request.getAttribute("association");
    LinkedList<enums.Continent> availableContinents = (LinkedList<enums.Continent>) request.getAttribute("availableContinents");
    
    if (availableContinents == null) {
        
    	availableContinents = new LinkedList<>();
        for (enums.Continent c : enums.Continent.values()) availableContinents.add(c);
    
    }

    if (association.getContinent() != null && !availableContinents.contains(association.getContinent())) {
    
    	availableContinents.addFirst(association.getContinent());
    
    }
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
	        
	        .form-control:disabled, .form-control[readonly] {
	            background-color: #343a40;
	            cursor: not-allowed;
	            opacity: 0.60;
	            border: 1px solid #2b2b2b;
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
		        
		        <div class="form-group">
				    <label for="type">Tipo:</label>
				    <input type="hidden" name="type" value="<%= association.getType().name() %>">
				    <select class="form-control" id="type" disabled>
				        <option value="NATIONAL" <%= association.getType() == AssociationType.NATIONAL ? "selected" : "" %>>
				            Nacional (Un país)
				        </option>
				        <option value="CONTINENTAL" <%= association.getType() == AssociationType.CONTINENTAL ? "selected" : "" %>>
				            Continental (Confederación)
				        </option>
				        <option value="INTERNATIONAL" <%= association.getType() == AssociationType.INTERNATIONAL ? "selected" : "" %>>
				            Internacional (FIFA)
				        </option>
				    </select>
				</div>
				
				<div class="form-group" id="continentContainer" style="display: none;">
				    <label for="continent">Continente:</label>
				    <input type="hidden" name="continent" value="<%= association.getContinent() != null ? association.getContinent().name() : "" %>">
				    <select class="form-control" id="continent" disabled>
				        <option value="">-- Seleccioná un continente --</option>
				        <% for (enums.Continent c : availableContinents) { 
				            boolean isSelected = (association.getContinent() == c);
				        %>
				            <option value="<%= c.name() %>" <%= isSelected ? "selected" : "" %>>
				                <%= c.getDisplayName() %>
				            </option>
				        <% } %>
				    </select>
				</div>
		        
		        <div class="button-container mb-3">
			        <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>

		<script>
		    function toggleContinentField() {
		        var typeSelect = document.getElementById("type");
		        var continentDiv = document.getElementById("continentContainer");
		        var continentSelect = document.getElementById("continent");
		        
		        if (typeSelect.value === "CONTINENTAL") {
		            continentDiv.style.display = "block";
		            continentSelect.required = true;
		        } else {
		            continentDiv.style.display = "none";
		            continentSelect.required = false;
		        }
		    }
		    
		    window.onload = function() {
		        toggleContinentField();
		    };
		</script>
	</body>
</html>