<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Persona</title>
    <link href="style/bootstrap.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Navbar.jsp"></jsp:include>
<div class="container">
    <h2>Agregar Persona</h2>
    <form action="actionperson" method="post">
    	<input type="hidden" name="action" value="add" />
        <div class="form-group">
            <label for="id">DNI:</label>
            <input type="text" class="form-control" id="id" name="id" required />
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
            <label for="adress">Dirección:</label>
            <input type="text" class="form-control" id="adress" name="adress" required />
        </div>

        <button type="submit" class="btn btn-primary">Agregar</button>
    </form>
    
    <form action="signin" method="post" style="margin-top: 10px;">
        <button type="submit" class="btn btn-secondary">Cancelar</button>
    </form>
</div>
</body>
</html>