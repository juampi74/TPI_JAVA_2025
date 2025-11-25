<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Director Técnico</title>
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
		    <h2 class="mt-4">Agregar Director Técnico</h2>
		    <form action="actioncoach" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="add" />
		        
		        <div class="form-group">
		            <label for="id">DNI:</label>
		            <input type="number" class="form-control" id="id" name="id" required />
		        </div>
		
		        <div class="form-group">
		            <label for="fullname">Apellido y Nombre:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" required />
		        </div>
		
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" required />
		        </div>
		
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="preferredFormation">Formación Preferida:</label>
		            <input type="text" class="form-control" id="preferredFormation" name="preferredFormation" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="coachingLicense">Licencia de Entrenador:</label>
		            <input type="text" class="form-control" id="coachingLicense" name="coachingLicense" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="licenseObtainedDate">Fecha de Obtención de Licencia:</label>
		            <input type="date" class="form-control" id="licenseObtainedDate" name="licenseObtainedDate" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="photo">Foto:</label>
		            <input type="file" class="form-control" id="photo" name="photo" maxlength="250" accept="image/*" required />
		            <small class="form-text text-white-50">Formatos recomendados: PNG o JPG.</small>
		        </div>
		        
		        <div class="button-container mb-3">
		            <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		            <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
		        </div>
		    </form>
		</div>
	</body>
</html>