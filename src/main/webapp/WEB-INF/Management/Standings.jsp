<%@ page import="java.util.*"%>
<%@ page import="entities.*"%>
<%@ page import="enums.TournamentFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    Tournament t = (Tournament) request.getAttribute("tournament");
    TreeMap<String, LinkedList<TeamStats>> tablesMap = (TreeMap<String, LinkedList<TeamStats>>) request.getAttribute("tablesMap");
    
    if (tablesMap == null) tablesMap = new TreeMap<>();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta charset="utf-8">
	    <title>Tabla de Posiciones</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet"> 
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	    
	    <style>
	        
	        .table {
	        	text-align: center;
	        }
	        
	        table th, table td {
	        	vertical-align: middle !important;
	        }
	        
	        .nav-pills .nav-link {
	            color: white;
	            background-color: rgba(255,255,255,0.1);
	            margin-right: 5px;
	            cursor: pointer;
	        }
	        
	        .nav-pills .nav-link.active {
	            background-color: #F57F17;
	            color: white;
	            font-weight: bold;
	        }
	        
	        .table-pos {
		        text-align: center;
		        table-layout: fixed;
		    }
		
		    .pos-number {
		        width: 50px; 
		        font-weight: bold;
		    }
		
		    .team-cell {
		        text-align: left;
		        width: 45%;	        
		        white-space: nowrap;
		        overflow: hidden;
		        text-overflow: ellipsis;
		    }
	        
	    </style>
	</head>

	<body style="background-color: #10442E;">
	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	
	    <div class="container text-white mt-4">
	        
	        <div class="d-flex justify-content-between align-items-center mb-4">
	            <div class="d-flex align-items-center gap-3">
	                <a href="actiontournament" class="btn btn-secondary border border-white me-3" title="Volver a Torneos">
	                    <i class="fas fa-arrow-left"></i>
	                </a>
	                <div>
	                    <h1 class="mb-2">Tabla de Posiciones</h1>
	                    <h4 class="text-white-50"><%= t.getName() %></h4>
	                </div>
	            </div>
	            
	            <a href="actionmatch?tournamentId=<%= t.getId() %>" class="btn text-white" style="background-color: #192A56;">
	                <i class="fas fa-calendar-alt me-2"></i> Ver Fixture
	            </a>
	        </div>
	
	        <% if (tablesMap.size() > 1) { %>
	            <ul class="nav nav-pills mb-3" id="pills-tab" role="tablist">
	                <% 
	                    int i = 0;
	                    for (String groupName : tablesMap.keySet()) { 
	                        String activeClass = (i == 0) ? "active" : "";
	                %>
	                    <li class="nav-item" role="presentation">
	                        <button class="nav-link <%= activeClass %>" id="tab-<%= i %>" 
	                                data-bs-toggle="pill" data-bs-target="#content-<%= i %>" 
	                                type="button" role="tab">
	                            <%= groupName %>
	                        </button>
	                    </li>
	                <% i++; } %>
	            </ul>
	        <% } %>
	
	        <div class="tab-content" id="pills-tabContent">
	            <% 
	                int j = 0;
	                
	            	for (Map.Entry<String, LinkedList<TeamStats>> entry : tablesMap.entrySet()) {
	                
	            		String groupName = entry.getKey();
	                    LinkedList<TeamStats> statsList = entry.getValue();
	                    String showClass = (j == 0) ? "show active" : "";
	                    
	                    int qualificationLimit = 0;

	                    if (t.getFormat() == TournamentFormat.ZONAL_ELIMINATION) {

	                    	qualificationLimit = (statsList.size() >= 12) ? 8 : 4;
	                        
	                    } else if (t.getFormat() == TournamentFormat.WORLD_CUP) {

	                    	qualificationLimit = 2;
	                    
	                    }
	            %>
	                <div class="tab-pane fade <%= showClass %>" id="content-<%= j %>" role="tabpanel">
	                    
	                    <div class="table-responsive rounded-3 border overflow-hidden mb-4">
	                        <table class="table table-dark mb-0 table-pos">
	                            <thead style="background-color: #192A56;">
	                                <tr>
	                                    <th class="pos-number">#</th>
	                                    <th class="team-cell">Equipo</th>
	                                    <th title="Puntos">PTS</th>
	                                    <th title="Partidos Jugados">PJ</th>
	                                    <th title="Partidos Ganados">PG</th>
	                                    <th title="Partidos Empatados">PE</th>
	                                    <th title="Partidos Perdidos">PP</th>
	                                    <th title="Goles a Favor">GF</th>
	                                    <th title="Goles en Contra">GC</th>
	                                    <th title="Diferencia de Gol">DG</th>
	                                </tr>
	                            </thead>
	                            <tbody>
	                                <% 
	                                    int pos = 1;

	                                	for (TeamStats s : statsList) { 
	                                    
	                                		String rowClass = ""; 
	                                        
	                                        if (pos <= qualificationLimit && tablesMap.size() > 1) rowClass = "table-active"; 
	                                %>
	                                    <tr class="<%= rowClass %>">
                                            <td class="pos-number"><%= pos %></td>
	                                        
	                                        <td class="team-cell">
	                                            <div class="d-flex align-items-center gap-2">
	                                                <img src="<%=request.getContextPath() + "/images?id=" + s.getClub().getBadgeImage()%>" width="30" height="30" style="object-fit: contain;">
	                                                <span class="fw-bold"><%= s.getClub().getName() %></span>
	                                            </div>
	                                        </td>
	                                        
	                                        <td class="fw-bold text-warning fs-5"><%= s.getPoints() %></td>
	                                        <td><%= s.getPlayed() %></td>                                       
	                                        <td><%= s.getWon() %></td>
	                                        <td><%= s.getDrawn() %></td>
	                                        <td><%= s.getLost() %></td>
	                                        <td><%= s.getGoalsFor() %></td>
	                                        <td><%= s.getGoalsAgainst() %></td>
	                                        
	                                        <td class="fw-bold <%= s.getGoalDifference() > 0 ? "text-success" : (s.getGoalDifference() < 0 ? "text-danger" : "") %>">
	                                            <%= s.getGoalDifference() > 0 ? "+" + s.getGoalDifference() : s.getGoalDifference() %>
	                                        </td>
	                                    </tr>
	                                <% pos++; } %>
	                            </tbody>
	                        </table>
	                    </div>
	                    
	                </div>
	            <% j++; } %>
	        </div>
	
	    </div>
	
	</body>
</html>