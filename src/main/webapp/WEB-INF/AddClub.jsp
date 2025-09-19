<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Club</title>
    <link href="style/bootstrap.css" rel="stylesheet">
</head>
<body>
<jsp:include page="Navbar.jsp"></jsp:include>
<div class="container">
    <h2>Agregar Club</h2>
    <form action="actionclub" method="post">
    	<input type="hidden" name="action" value="add" />

        <div class="form-group">
            <label for="name">Nombre:</label>
            <input type="text" class="form-control" id="name" name="name" required />
        </div>

        <div class="form-group">
            <label for="foundationDate">Fecha de Fundacion:</label>
            <input type="date" class="form-control" id="foundationDate" name="foundationDate" required />
        </div>

        <div class="form-group">
            <label for="phoneNumber">Numero de telefono:</label>
            <input type="text" class="form-control" id="phoneNumber" name="phoneNumber" required />
        </div>
        
        <div class="form-group">
            <label for="email">Email:</label>
            <input type="text" class="form-control" id="email" name="email" required />
        </div>
        
        <div class="form-group">
            <label for="badgeImage">Escudo:</label>
            <input type="text" class="form-control" id="badgeImage" name="badgeImage" required />
        </div>
        
        <div class="form-group">
            <label for="budget">Presupuesto:</label>
            <input type="text" class="form-control" id="budget" name="budget" required />
        </div>

        <button type="submit" class="btn btn-primary">Agregar</button>
    </form>
    
    <form action="signin" method="post" style="margin-top: 10px;">
        <button type="submit" class="btn btn-secondary">Cancelar</button>
    </form>
</div>
</body>
</html>