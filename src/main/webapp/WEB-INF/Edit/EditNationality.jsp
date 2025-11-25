<%@ page import="entities.Nationality"%>
<%@ page import="enums.Continent" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Nationality nationality = (Nationality) request.getAttribute("nationality");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Nacionalidad</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <style>
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	        .current-flag {
	            max-width: 100px;
	            border: 1px solid white;
	            margin-bottom: 10px;
	            display: block;
	        }
	    </style>
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Editar Nacionalidad</h2>
		    
		    <form action="actionnationality" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=nationality.getId()%>" />
		        
		        <input type="hidden" name="currentFlagImage" value="<%= (nationality.getFlagImage() != null) ? nationality.getFlagImage() : "" %>" />
		        
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" value="<%=nationality.getName()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="isoCode">Código ISO:</label>
				    <input type="text" 
				           class="form-control" 
				           id="isoCode" 
				           name="isoCode" 
				           required 
				           minlength="3" 
				           maxlength="3" 
				           style="text-transform: uppercase;"
				           pattern="[A-Za-z]{3}" 
				           title="Debe contener exactamente 3 letras"
				           value="<%=nationality.getIsoCode()%>" />
				    
				    <small class="form-text text-white-50">Debe ser exactamente de 3 letras (Ej: ARG, BRA).</small>
				</div>
				
				<div class="form-group">
				    <label for="continent">Continente:</label>
				    <select class="form-control" name="continent" id="continent" required>
				        <option value="">-- Seleccioná un continente --</option>
				        <% for (Continent c : Continent.values()) { 
				            boolean isSelected = (nationality.getContinent() == c);
				        %>
				            <option value="<%= c.name() %>" <%= isSelected ? "selected" : "" %>>
				                <%= c.getDisplayName() %>
				            </option>
				        <% } %>
				    </select>
				</div>
		        
		        <div class="form-group">
		            <label for="flagImage">Bandera:</label>
		            
		            <% if (nationality.getFlagImage() != null && !nationality.getFlagImage().isEmpty()) { %>
		                <div class="mb-2">
		                    <img src="<%=request.getContextPath() + "/images?id=" + nationality.getFlagImage()%>" class="current-flag" alt="Bandera actual" height="55">
		                </div>
		            <% } %>
		            
		            <input type="file" class="form-control" id="flagImage" name="flagImage" maxlength="250" accept="image/*" />
		            <small class="form-text text-white-50">Dejar vacío para mantener la bandera actual. Formatos recomendados: PNG o JPG.</small>
		            <input type="hidden" name="currentFlagImage" value="<%= nationality.getFlagImage() %>" />
		        </div>
		        
		        <div class="button-container mb-3">
		        	<button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>