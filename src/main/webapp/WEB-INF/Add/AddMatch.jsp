<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Club"%>
<%@ page import="entities.Tournament"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Agregar Partido</title>
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

    <%
        LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubs");
        LinkedList<Tournament> tournaments = (LinkedList<Tournament>) request.getAttribute("tournaments");
    %>

    <div class="container" style="color: white;">
        <h2 class="mt-4">Agregar Partido</h2>

        <form action="actionmatch" method="post" class="mt-4">
            <input type="hidden" name="action" value="add" />
            <div class="form-group mb-3">
                <label for="id_home">Club Local:</label>
                <select class="form-control" id="id_home" name="id_home" required>
                    <option value="" disabled selected>Seleccionar club local</option>
                    <% for (Club c : clubs) { %>
                        <option value="<%= c.getId() %>"><%= c.getName() %></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group mb-3">
                <label for="id_away">Club Visitante:</label>
                <select class="form-control" id="id_away" name="id_away" required>
                    <option value="" disabled selected>Seleccionar club visitante</option>
                    <% for (Club c : clubs) { %>
                        <option value="<%= c.getId() %>"><%= c.getName() %></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group mb-3">
                <label for="id_tournament">Torneo:</label>
                <select class="form-control" id="id_tournament" name="id_tournament" required>
                    <option value="" disabled selected>Seleccionar torneo</option>
                    <% for (Tournament t : tournaments) { %>
                        <option value="<%= t.getId() %>"><%= t.getName() %></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group mb-3">
                <label for="matchday">Jornada / Fecha:</label>
                <input type="number" class="form-control" id="matchday" name="matchday" min="1" />
            </div>
            <div class="form-group mb-3">
                <label for="date">Fecha y hora del partido:</label>
                <input type="datetime-local" class="form-control" id="date" name="date" required />
            </div>
            <div class="form-group mb-3">
                <label for="home_goals">Goles Local:</label>
                <input type="number" min="0" class="form-control" id="home_goals" name="home_goals" />
            </div>
            <div class="form-group mb-3">
                <label for="away_goals">Goles Visitante:</label>
                <input type="number" min="0" class="form-control" id="away_goals" name="away_goals" />
            </div>
            <div class="button-container mb-3">
                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
                <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
            </div>

        </form>

    </div>

</body>
</html>
