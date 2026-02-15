<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Nationality"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Registro</title>
    <link href="style/bootstrap.css" rel="stylesheet">
    <link href="style/signin.css" rel="stylesheet">
    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
    
    <style>
        select.form-control-sm {
            height: calc(1.5em + .5rem + 2px) !important;
            background-image: none;
        }
        
        .card-custom {
            background-color: rgba(33,37,41,0.95);
            border: 1px solid rgba(255,255,255,1) !important;
            border-radius: 1rem !important;
            box-shadow: 0 0 20px rgba(0,0,0,.4);
        }

        .form-label {
            font-weight: 500;
            color: #e0e0e0;
            margin-bottom: 2px;
            font-size: 0.9rem;
            display: block;
            text-align: left;
        }
        
        body {
            min-height: 100vh;
            background-color: #10442E;
            padding-top: 20px;
            padding-bottom: 20px;
        }
    </style>
</head>
<body class="text-center d-flex align-items-center justify-content-center">

    <div class="card-custom p-4 w-100" style="max-width: 750px;">
        
        <img class="mb-3 bg-white rounded p-2" src="${pageContext.request.contextPath}/assets/TeamUp_Logo.png" 
             alt="TeamUp Logo" width="100" style="border-radius: .5rem;">

        <h2 class="h4 mb-3 fw-normal text-white">Registro</h2>

        <% String flash = (String) request.getAttribute("flash");
           if (flash != null) { %>
            <div class="alert alert-warning text-start p-1 mb-3 shadow-sm" role="alert" style="font-size: 0.85rem;">
                <strong>Atención:</strong> <%= flash %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/register" method="post">
            
            <div class="row mb-2">
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="idPerson" class="form-label">DNI / Documento</label>
                        <input type="number" id="idPerson" name="idPerson" class="form-control form-control-sm" placeholder="Ej: 30123456" 
                               value="<%= request.getAttribute("prevId") != null ? request.getAttribute("prevId") : "" %>" 
                               required autofocus>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="idNationality" class="form-label">Nacionalidad</label>
                        <select name="idNationality" id="idNationality" class="form-control form-control-sm" required>
                            <option value="">-- Seleccionar --</option>
                            <% LinkedList<Nationality> nats = (LinkedList<Nationality>) request.getAttribute("nationalitiesList");
                        	   String prevNationality = (String) request.getAttribute("prevNationality");
                               if (nats != null) {
                                   for (Nationality n : nats) { 
                                       String isSelected = "";
                                       if (prevNationality != null && prevNationality.equals(String.valueOf(n.getId()))) {
                            		      isSelected = "selected";
                        			   } %>
                                       <option value="<%= n.getId() %>" <%= isSelected %>><%= n.getName() %></option>
                            <%     }
                               } %>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row mb-2">
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="firstname" class="form-label">Nombre</label>
                        <input type="text" id="firstname" name="firstname" class="form-control form-control-sm" placeholder="Ej: Juan" 
                               value="<%= request.getAttribute("prevName") != null ? request.getAttribute("prevName") : "" %>" 
                               required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="lastname" class="form-label">Apellido</label>
                        <input type="text" id="lastname" name="lastname" class="form-control form-control-sm" placeholder="Ej: Pérez" 
                               value="<%= request.getAttribute("prevLastname") != null ? request.getAttribute("prevLastname") : "" %>" 
                               required>
                    </div>
                </div>
            </div>

            <div class="row mb-2">
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="email" class="form-label">Correo Electrónico</label>
                        <input type="email" id="email" name="email" class="form-control form-control-sm" placeholder="nombre@ejemplo.com" 
                               value="<%= request.getAttribute("prevEmail") != null ? request.getAttribute("prevEmail") : "" %>" 
                               required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <% String prevRole = (String) request.getAttribute("prevRole"); %>
                        <label for="role" class="form-label">Rol Solicitado</label>
                        <select name="role" id="role" class="form-control form-control-sm" required>
                            <option value="" disabled <%= prevRole == null ? "selected" : "" %>>-- Seleccionar --</option>
                            <option value="PLAYER" <%= "PLAYER".equals(prevRole) ? "selected" : "" %>>Jugador</option>
                            <option value="COACH" <%= "COACH".equals(prevRole) ? "selected" : "" %>>Director Técnico</option>
                            <option value="PRESIDENT" <%= "PRESIDENT".equals(prevRole) ? "selected" : "" %>>Presidente de Club</option>
                        </select>
                    </div>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="password" class="form-label">Contraseña</label>
                        <input type="password" id="password" name="password" class="form-control form-control-sm" placeholder="******" required>
                    </div>
                </div>
                <div class="col-md-6">
                    <div class="form-group text-start">
                        <label for="confirmPassword" class="form-label">Repetir Contraseña</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" class="form-control form-control-sm" placeholder="******" required>
                    </div>
                </div>
            </div>

            <button class="btn btn-sm btn-success w-100 fw-bold shadow-sm mb-3" 
                    style="background-color: #1A6B32; border: none; padding: 10px; font-size: 1rem;"
                    type="submit">
                Solicitar Registro
            </button>
            
            <div class="d-flex justify-content-center mb-2">
            	<a href="${pageContext.request.contextPath}/login" class="text-warning text-decoration-none fw-bold small">
	                ¿Ya tenés cuenta? Iniciar Sesión
	            </a>
	        </div>
	        
	        <div class="d-flex justify-content-center">
                <a href="${pageContext.request.contextPath}/Home" class="text-white-50 text-decoration-none small">
                    Volver al inicio (Entrar como Invitado)
                </a>
            </div>

        </form>
    </div>

</body>
</html>