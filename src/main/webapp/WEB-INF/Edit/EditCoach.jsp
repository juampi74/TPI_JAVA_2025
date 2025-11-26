<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Coach"%>
<%@ page import="entities.Nationality"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Coach coach = (Coach) request.getAttribute("coach");
	LinkedList<Nationality> nationalitiesList = (LinkedList<Nationality>) request.getAttribute("nationalitiesList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Director Técnico</title>
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
		    <h2 class="mt-4">Editar Director Técnico</h2>
		    <form action="actioncoach" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=coach.getId()%>" />
		        
		        <div class="form-group">
		            <label for="fullname">Nombre y Apellido:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" value="<%=coach.getFullname()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<%=coach.getBirthdate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" value="<%=coach.getAddress()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="preferredFormation">Formación Preferida:</label>
		            <input type="text" class="form-control" id="preferredFormation" name="preferredFormation" value="<%=coach.getPreferredFormation()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="coachingLicense">Licencia de Entrenador:</label>
		            <input type="text" class="form-control" id="coachingLicense" name="coachingLicense" value="<%=coach.getCoachingLicense()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="licenseObtainedDate">Fecha de Obtención de Licencia:</label>
		            <input type="date" class="form-control" id="licenseObtainedDate" name="licenseObtainedDate" value="<%=coach.getLicenseObtainedDate()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_nationality">Nacionalidad:</label>
				    <select name="id_nationality" id="id_nationality" class="form-control" required>
					    <option value="">-- Seleccioná una nacionalidad --</option>
					    <%
					        if (nationalitiesList != null && !nationalitiesList.isEmpty()) {
					            int selectedNationalityId = (coach != null && coach.getNationality() != null) ? coach.getNationality().getId() : -1;
					            for (Nationality n : nationalitiesList) {
					                String selected = (n.getId() == selectedNationalityId) ? "selected" : "";
					                out.print("<option value='" + n.getId() + "' " + selected + ">" + n.getName() + "</option>");
					            }
					        } else {
					            out.print("<option value='' disabled>No hay nacionalidades cargadas</option>");
					        }
					    %>
					</select>
				</div>
		        
		        <div class="form-group">
		            <label for="photo">Foto:</label>
		            
		            <% if (coach.getPhoto() != null && !coach.getPhoto().isEmpty()) { %>
		                <div class="mb-2">
		                    <img src="<%=request.getContextPath() + "/images?id=" + coach.getPhoto()%>" class="current-photo" alt="Foto actual" height="80">
		                </div>
		            <% } %>
		            
		            <input type="file" class="form-control" id="photo" name="photo" maxlength="250" accept="image/*" />
		            <small class="form-text text-white-50">Dejar vacío para mantener la foto actual. Formatos recomendados: PNG o JPG.</small>
		            <input type="hidden" name="currentPhoto" value="<%= coach.getPhoto() %>" />
		        </div>
		        
		        <div class="button-container mb-3">
			        <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>