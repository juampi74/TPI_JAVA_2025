<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Club"%>
<%@ page import="entities.Stadium"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Club club = (Club) request.getAttribute("club");
	LinkedList<Stadium> stadiumsList = (LinkedList<Stadium>) request.getAttribute("stadiumsList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Club</title>
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
		    <h2 class="mt-4">Editar Club</h2>
			<form action="actionclub" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=club.getId()%>" />
		        
		        <div class="form-group">
		            <label for="name">Nombre:</label>
		            <input type="text" class="form-control" id="name" name="name" value="<%=club.getName()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="foundationDate">Fecha de Fundación:</label>
		            <input type="date" class="form-control" id="foundationDate" name="foundationDate" value="<%=club.getFoundationDate()%>" required />
		        </div>
		
		        <div class="form-group">
		            <label for="phoneNumber">Número de Teléfono:</label>
		            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" value="<%=club.getPhoneNumber()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="email">Email:</label>
		            <input type="text" class="form-control" id="email" name="email" value="<%=club.getEmail()%>" required />
		        </div>
		        	        
		        <div class="form-group">
		            <label for="badgeImage">Escudo:</label>
		            
		            <% if (club.getBadgeImage() != null && !club.getBadgeImage().isEmpty()) { %>
		                <div class="mb-2">
		                    <img src="<%=request.getContextPath() + "/images?id=" + club.getBadgeImage()%>" class="current-badge" alt="Escudo actual" height="55">
		                </div>
		            <% } %>
		            
		            <input type="file" class="form-control" id="badgeImage" name="badgeImage" maxlength="250" accept="image/*" />
		            <small class="form-text text-white-50">Dejar vacío para mantener el escudo actual. Formatos recomendados: PNG o JPG.</small>
		            <input type="hidden" name="currentBadgeImage" value="<%= club.getBadgeImage() %>" />
		        </div>
		        
		        <div class="form-group">
		            <label for="budget">Presupuesto:</label>
		            <input type="number" class="form-control" id="budget" name="budget" value="<%=club.getBudget()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_stadium">Estadio:</label>
				    <select name="id_stadium" id="id_stadium" class="form-control" required>
					    <option value="">-- Seleccioná un estadio --</option>
					    <%
					        if (stadiumsList != null && !stadiumsList.isEmpty()) {
					            int selectedStadiumId = (club != null && club.getStadium() != null) ? club.getStadium().getId() : -1;
					            for (Stadium s : stadiumsList) {
					                String selected = (s.getId() == selectedStadiumId) ? "selected" : "";
					                out.print("<option value='" + s.getId() + "' " + selected + ">" + s.getName() + "</option>");
					            }
					        } else {
					            out.print("<option value='' disabled>No hay estadios cargados</option>");
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