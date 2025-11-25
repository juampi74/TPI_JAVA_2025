<%@ page import="enums.*" %>
<%@ page import="java.util.LinkedList" %>
<%
	LinkedList<enums.Continent> availableContinents = (LinkedList<enums.Continent>) request.getAttribute("availableContinents");
	
	if (availableContinents == null) {
	
		availableContinents = new LinkedList<>();
		for (enums.Continent c : enums.Continent.values()) availableContinents.add(c);
	
	}
%>
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
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Agregar Asociación</h2>
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
		        
		        <div class="form-group">
				    <label for="type">Tipo:</label>
				    <select class="form-control" id="type" name="type" onchange="toggleContinentField()" required>
				        <option value="NATIONAL">Nacional (Un país)</option>
				        <option value="CONTINENTAL">Continental (Confederación)</option>
				        <option value="INTERNATIONAL">Internacional (FIFA)</option>
				    </select>
				</div>
				
				<div class="form-group" id="continentContainer" style="display: none;">
				    <label for="continent">Continente:</label>
				    <select class="form-control" id="continent" name="continent">
				        <option value="">-- Seleccioná un continente --</option>
						<% for (enums.Continent c : availableContinents) { %>
							<option value="<%= c.name() %>"><%= c.getDisplayName() %></option>
						<% } %>
				    </select>
				</div>
		
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
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
		            continentSelect.value = "";
		        }
		    }
		    
		    window.onload = function() {
		        toggleContinentField();
		    };
		</script>
	</body>
</html>