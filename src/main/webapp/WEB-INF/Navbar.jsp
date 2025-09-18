<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Navbar</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
</head>
<body>
    <!-- Barra de navegación -->
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">MiApp</a>
            
            <div class="collapse navbar-collapse">
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <!-- Enlace directo al JSP -->
                        <a class="nav-link" href="actionperson">Personas</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="actionstadium">Estadios</a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>
</body>
</html>