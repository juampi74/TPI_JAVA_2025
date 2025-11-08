<%@ page import="entities.TechnicalDirector"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	TechnicalDirector technicalDirector = (TechnicalDirector) request.getAttribute("technicalDirector");
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
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Editar Director Técnico</h2>
		    <form action="actiontechnicaldirector" method="post" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=technicalDirector.getId()%>" />
		        
		        <div class="form-group">
		            <label for="fullname">Apellido y Nombre:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" value="<%=technicalDirector.getFullname()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<%=technicalDirector.getBirthdate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" value="<%=technicalDirector.getAddress()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="preferredFormation">Formación Preferida:</label>
		            <input type="text" class="form-control" id="preferredFormation" name="preferredFormation" value="<%=technicalDirector.getPreferredFormation()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="coachingLicense">Licencia de Entrenador:</label>
		            <input type="text" class="form-control" id="coachingLicense" name="coachingLicense" value="<%=technicalDirector.getCoachingLicense()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="licenseObtainedDate">Fecha de Obtención de Licencia:</label>
		            <input type="date" class="form-control" id="licenseObtainedDate" name="licenseObtainedDate" value="<%=technicalDirector.getLicenseObtainedDate()%>" required />
		        </div>
		        
		        <div class="button-container mb-3">
			          <button type="button" class="btn btn-secondary" onclick="history.back()">Cancelar</button>
			          <button type="submit" class="btn btn-primary">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
	</body>
</html>