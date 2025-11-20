<%@ page import="entities.Match"%>
<%@ page import="entities.Club"%>
<%@ page import="entities.Tournament"%>
<%@ page import="java.util.LinkedList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Match match = (Match) request.getAttribute("match");
    LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubs");
    LinkedList<Tournament> tournaments = (LinkedList<Tournament>) request.getAttribute("tournaments");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Editar Partido</title>
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

    <div class="container" style="color:white;">

        <h2 class="mt-4">Editar Partido</h2>

        <form action="actionmatch" method="post" class="mt-4">

            <input type="hidden" name="action" value="edit" />
            <input type="hidden" name="id" value="<%= match.getId() %>" />

            <div class="form-group mb-3">
                <label for="id_home">Club Local:</label>
                <select class="form-control" id="id_home" name="id_home" required>
                    <% for (Club c : clubs) { %>
                        <option value="<%= c.getId() %>"
                            <%= match.getHome().getId() == c.getId() ? "selected" : "" %>>
                            <%= c.getName() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="form-group mb-3">
                <label for="id_away">Club Visitante:</label>
                <select class="form-control" id="id_away" name="id_away" required>
                    <% for (Club c : clubs) { %>
                        <option value="<%= c.getId() %>"
                            <%= match.getAway().getId() == c.getId() ? "selected" : "" %>>
                            <%= c.getName() %>
                        </option>
                    <% } %>
                </select>
            </div>
            <div class="form-group mb-3">
                <label for="id_tournament">Torneo:</label>
                <select class="form-control" id="id_tournament" name="id_tournament" required>
                    <% for (Tournament t : tournaments) { %>
                        <option value="<%= t.getId() %>"
                            <%= match.getTournament().getId() == t.getId() ? "selected" : "" %>>
                            <%= t.getName() %>
                        </option>
                    <% } %>
                </select>
            </div>

            <div class="form-group mb-3">
                <label for="matchday">Jornada / Fecha:</label>
                <input type="number" class="form-control" id="matchday" name="matchday"
                       value="<%= match.getMatchday() != null ? match.getMatchday() : "" %>" />
            </div>

            <div class="form-group mb-3">
                <label for="date">Fecha y hora:</label>
                <input type="datetime-local" class="form-control" id="date" name="date"
                       value="<%= match.getDate().toString().replace(' ', 'T') %>" required />
            </div>

            <div class="form-group mb-3">
                <label for="home_goals">Goles Local:</label>
                <input type="number" class="form-control" id="home_goals" name="home_goals" min="0"
                       value="<%= match.getHomeGoals() != null ? match.getHomeGoals() : "" %>" />
            </div>

            <div class="form-group mb-3">
                <label for="away_goals">Goles Visitante:</label>
                <input type="number" class="form-control" id="away_goals" name="away_goals" min="0"
                       value="<%= match.getAwayGoals() != null ? match.getAwayGoals() : "" %>" />
            </div>

            <div class="button-container mb-3">
                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
                <button type="submit" class="btn text-white" style="background-color:#0D47A1;">Guardar Cambios</button>
            </div>

        </form>

    </div>

</body>
</html>
