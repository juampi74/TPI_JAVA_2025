<%@page import="entities.Persona"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    Persona persona = (Persona) request.getAttribute("persona");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Persona</title>
    <link href="style/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <h2>Editar Persona</h2>
    <form action="editarpersona" method="post">
        <input type="hidden" name="id" value="<%=persona.getId()%>" />
        
        <div class="form-group">
            <label for="dnie">DNI:</label>
            <input type="text" class="form-control" id="dni" name="dni" value="<%=persona.getDni()%>" required />
        </div>
        
        <div class="form-group">
            <label for="apellido_nombre">Apellido y Nombre:</label>
            <input type="text" class="form-control" id="apellido_nombre" name="apellido_nombre" value="<%=persona.getApellido_nombre()%>" required />
        </div>
        
        <div class="form-group">
            <label for="fecha_nacimiento">Fecha de Nacimiento:</label>
            <input type="date" class="form-control" id="fecha_nacimiento" name="fecha_nacimiento" value="<%=persona.getFecha_nacimiento()%>" required />
        </div>
        
        <div class="form-group">
            <label for="direccion">Dirección:</label>
            <input type="text" class="form-control" id="direccion" name="direccion" value="<%=persona.getDireccion()%>" required />
        </div>
        
        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
    </form>
    <form action="signin" method="post">
    	<button type="submit" class="btn btn-secondary">Cancelar</button>
	</form>
</div>
</body>
</html>