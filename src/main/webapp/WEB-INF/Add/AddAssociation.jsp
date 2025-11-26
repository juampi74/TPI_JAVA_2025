<%@ page import="enums.Continent" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    List<Continent> availableContinents = (List<Continent>) request.getAttribute("availableContinents");
    if (availableContinents == null) availableContinents = new LinkedList<>();
%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Agregar Asociación</title>
    <link href="style/bootstrap.css" rel="stylesheet">
    
    <style>

        .button-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }
        
        .form-text {
            font-size: 0.85em;
            margin-top: 5px;
        }
    </style>
    
    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
  </head>
  <body style="background-color: #10442E;">
    
    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
    
    <div class="container" style="color: white;">
        <h2 class="mt-4">Agregar Asociación</h2>
        
        <form action="actionassociation" method="post" class="mt-4">
            <input type="hidden" name="action" value="add" />
            
            <div class="form-group">
                <label for="name">Nombre:</label>
                <input type="text" class="form-control" id="name" name="name" required />
            </div>
    
            <div class="form-group">
                <label for="creationDate">Fecha de Creación:</label>
                <input type="date" class="form-control" id="creationDate" name="creationDate" required />
            </div>
            
            <div class="form-group">
                <label for="type">Tipo:</label>
                <select class="form-control" id="type" name="type" onchange="updateContinentOptions()" required>
                    <option value="">-- Seleccioná el tipo --</option>
                    <option value="NATIONAL">Nacional (Ej: AFA, CBF)</option>
                    <option value="CONTINENTAL">Continental (Ej: CONMEBOL)</option>
                    <option value="INTERNATIONAL">Internacional (Ej: FIFA)</option>
                </select>
            </div>
            
            <div class="form-group" id="continentContainer" style="display: none;">
                <label for="continent">Continente:</label>
                <select class="form-control" id="continent" name="continent">
                    <option value="">-- Seleccioná un continente --</option>
                    </select>
                <small class="form-text text-white-50" id="continentHelp">Indica a qué región pertenece.</small>
            </div>
    
            <div class="button-container mb-3">
                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
                <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
            </div>
        </form>
    </div>

    <script>
        const allContinentsList = [
            <% for (Continent c : Continent.values()) { %>
                { val: "<%= c.name() %>", text: "<%= c.getDisplayName() %>" },
            <% } %>
        ];

        const availableContinentsList = [
            <% for (Continent c : availableContinents) { %>
                { val: "<%= c.name() %>", text: "<%= c.getDisplayName() %>" },
            <% } %>
        ];

        function updateContinentOptions() {
            var typeSelect = document.getElementById("type");
            var continentDiv = document.getElementById("continentContainer");
            var continentSelect = document.getElementById("continent");
            var helpText = document.getElementById("continentHelp");
            
            var selectedType = typeSelect.value;
            
            continentSelect.innerHTML = '<option value="">-- Seleccioná un continente --</option>';

            if (selectedType === "INTERNATIONAL" || selectedType === "") {
                continentDiv.style.display = "none";
                continentSelect.required = false;
                continentSelect.value = "";
                return;
            }

            continentDiv.style.display = "block";
            continentSelect.required = true;
            
            var listSource = [];

            if (selectedType === "CONTINENTAL") {

                listSource = availableContinentsList;
                helpText.textContent = "Solo se muestran los continentes que todavía NO tienen una Asociación.";
                
                if (listSource.length === 0) {
                    var opt = document.createElement('option');
                    opt.text = "No hay continentes disponibles (Cupo lleno)";
                    opt.disabled = true;
                    continentSelect.add(opt);
                }

            } else {

                listSource = allContinentsList;
                helpText.textContent = "Indicá a qué continente corresponde esta Asociación.";
                
            }

            listSource.forEach(function(c) {
                var opt = document.createElement('option');
                opt.value = c.val;
                opt.innerHTML = c.text;
                continentSelect.appendChild(opt);
            });
        }
        
        window.onload = function() {
            updateContinentOptions();
        };
    </script>
  </body>
</html>