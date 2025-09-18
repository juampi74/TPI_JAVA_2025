<%@page import="entities.Stadium"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%


    Stadium stadium = (Stadium) request.getAttribute("stadium");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Estadio</title>
    <link href="style/bootstrap.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Navbar.jsp"></jsp:include>
<div class="container">
    <h2>Editar Estadio</h2>
    <form action="actionstadium" method="post">
    	<input type="hidden" name="action" value="edit" />
        <input type="hidden" name="id" value="<%=stadium.getId()%>" />
        
        <div class="form-group">
            <label for="name">Nombre:</label>
            <input type="text" class="form-control" id="name" name="name" value="<%=stadium.getName()%>" required />
        </div>
        
        <div class="form-group">
            <label for="capacity">Capacidad:</label>
            <input type="text" class="form-control" id="capacity" name="capacity" value="<%=stadium.getCapacity()%>" required />
        </div>
        
        <button type="submit" class="btn btn-primary">Guardar Cambios</button>
    </form>
    <form action="actionstadium" method="post">
    	<button type="submit" class="btn btn-secondary">Cancelar</button>
	</form>
</div>
</body>
</html>