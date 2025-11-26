<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	President president = (President) request.getAttribute("president");

	LinkedList<Nationality> nationalitiesList = (LinkedList<Nationality>) request.getAttribute("nationalitiesList");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Presidente</title>
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
	<body style="background-color: #10442E;" >
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Editar Presidente</h2>
		    <form action="actionpresident" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=president.getId()%>" />
		        
		        <div class="form-group">
		            <label for="fullname">Nombre y Apellido:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" value="<%=president.getFullname()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<%=president.getBirthdate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" value="<%=president.getAddress()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="managementPolicy">Política de Gestión:</label>
		            <input type="text" class="form-control" id="managementPolicy" name="managementPolicy" value="<%=president.getManagementPolicy()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_nationality">Nacionalidad:</label>
				    <select name="id_nationality" id="id_nationality" class="form-control" required>
					    <option value="">-- Seleccioná una nacionalidad --</option>
					    <%
					        if (nationalitiesList != null && !nationalitiesList.isEmpty()) {
					            int selectedNationalityId = (president != null && president.getNationality() != null) ? president.getNationality().getId() : -1;
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
		            
		            <% if (president.getPhoto() != null && !president.getPhoto().isEmpty()) { %>
		                <div class="mb-2">
		                    <img src="<%=request.getContextPath() + "/images?id=" + president.getPhoto()%>" class="current-photo" alt="Foto actual" height="80">
		                </div>
		            <% } %>
		            
		            <input type="file" class="form-control" id="photo" name="photo" maxlength="250" accept="image/*" />
		            <small class="form-text text-white-50">Dejar vacío para mantener la foto actual. Formatos recomendados: PNG o JPG.</small>
		            <input type="hidden" name="currentPhoto" value="<%= president.getPhoto() %>" />
		        </div>
		        
		        <div class="button-container mb-3">
			        <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>