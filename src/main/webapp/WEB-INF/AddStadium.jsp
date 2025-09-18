<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Estadio</title>
    <link href="style/bootstrap.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Navbar.jsp"></jsp:include>
<div class="container">
    <h2>Agregar Estadio</h2>
    <form action="actionstadium" method="post">
    	<input type="hidden" name="action" value="add" />
        <div class="form-group">
            <label for="name">Nombre:</label>
            <input type="text" class="form-control" id="name" name="name" required />
        </div>

        <div class="form-group">
            <label for="capacity">Capacidad:</label>
            <input type="number" class="form-control" id="capacity" name="capacity" required />
        </div>

        <button type="submit" class="btn btn-primary">Agregar</button>
    </form>
    
    <form action="actionstadium" method="post" style="margin-top: 10px;">
        <button type="submit" class="btn btn-secondary">Cancelar</button>
    </form>
</div>
</body>
</html>