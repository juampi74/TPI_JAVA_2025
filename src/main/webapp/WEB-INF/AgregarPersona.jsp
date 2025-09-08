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
<div class="container">
    <h2>Agregar Persona</h2>
    <form action="accionpersona" method="post">
    	<input type="hidden" name="action" value="add" />
        <div class="form-group">
            <label for="dni">DNI:</label>
            <input type="text" class="form-control" id="dni" name="dni" required />
        </div>

        <div class="form-group">
            <label for="apellido_nombre">Apellido y Nombre:</label>
            <input type="text" class="form-control" id="apellido_nombre" name="apellido_nombre" required />
        </div>

        <div class="form-group">
            <label for="fecha_nacimiento">Fecha de Nacimiento:</label>
            <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" required />
        </div>

        <div class="form-group">
            <label for="direccion">Dirección:</label>
            <input type="text" class="form-control" id="direccion" name="direccion" required />
        </div>

        <button type="submit" class="btn btn-primary">Agregar</button>
    </form>
    
    <form action="signin" method="post" style="margin-top: 10px;">
        <button type="submit" class="btn btn-secondary">Cancelar</button>
    </form>
</div>
</body>
</html>