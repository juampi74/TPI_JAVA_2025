<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Match"%>
<%@ page import="entities.Club"%>
<%@ page import="entities.Tournament"%>
<%@ page import="enums.TournamentFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>Partidos</title>
	
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	
	    <style>
	        
	        .table {
	        	text-align: center;
	        }
	        
	        table th, table td {
	            vertical-align: middle !important;
	        }
	               
	    </style>
	
	    <%
			LinkedList<Match> ml = (LinkedList<Match>) request.getAttribute("matchList");
			boolean emptyList = (ml == null || ml.isEmpty());

			String selectedTournament = request.getParameter("tournamentId");
			String selectedClub = request.getParameter("clubId");
	    %>
	
	</head>

	<body style="background-color: #10442E;">

	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	
	    <div class="container" style="color: white;">
	        
	        <%
	            boolean hasFilters = (selectedTournament != null && !selectedTournament.isEmpty()) || 
	                                 (selectedClub != null && !selectedClub.isEmpty());
	            
	            boolean showFilters = !emptyList || hasFilters;
	        %>
	
	        <div class="row">
	            
	            <% if (showFilters) { %>
	                <div class="d-flex justify-content-between align-items-center my-4">
	                    <h1>Partidos</h1>
	                    
	                    <div class="d-flex gap-3 align-items-center">
	                        <form method="get" action="actionmatch" class="m-0">
	                            <input type="hidden" name="clubId" value="<%= request.getParameter("clubId") != null ? request.getParameter("clubId") : "" %>">
	                            <select name="tournamentId" id="tournamentFilter"
	                                    class="form-select form-select-sm fancy-select bg-dark text-white"
	                                    style="width: auto; min-width: 200px;"
	                                    onchange="this.form.submit()">
	                                <option value="">Todos los torneos</option>
	                                <%
	                                    LinkedList<Tournament> tournaments = (LinkedList<Tournament>) request.getAttribute("tournamentsList");
	                                    int selectedId = -1;
	                                    if (selectedTournament != null && !selectedTournament.isEmpty()) {
	                                        selectedId = Integer.parseInt(selectedTournament);
	                                    }
	                                    if (tournaments != null) {
	                                        for (Tournament t : tournaments) {
	                                %>
	                                            <option value="<%= t.getId() %>" <%= (t.getId() == selectedId) ? "selected" : "" %>><%= t.getName() %></option>
	                                <%
	                                        }
	                                    }
	                                %>
	                            </select>
	                        </form>
	
	                        <form method="get" action="actionmatch" class="m-0">
	                            <input type="hidden" name="tournamentId" value="<%= request.getParameter("tournamentId") != null ? request.getParameter("tournamentId") : "" %>">
	                            <select name="clubId" id="clubFilter"
	                                    class="form-select form-select-sm fancy-select bg-dark text-white"
	                                    style="width: auto; min-width: 200px;"
	                                    onchange="this.form.submit()">
	                                <option value="">Todos los clubes</option>
	                                <%
	                                    LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubsList");
	                                    int selectedClubId = -1;
	                                    if (selectedClub != null && !selectedClub.isEmpty()) {
	                                        selectedClubId = Integer.parseInt(selectedClub);
	                                    }
	                                    if (clubs != null) {
	                                        for (Club c : clubs) {
	                                %>
	                                            <option value="<%= c.getId() %>" <%= (c.getId() == selectedClubId) ? "selected" : "" %>><%= c.getName() %></option>
	                                <%
	                                        }
	                                    }
	                                %>
	                            </select>
	                        </form>
	                        
	                        <% if (hasFilters) { %>
	                            <a href="actionmatch" class="btn btn-sm btn-outline-light" title="Borrar filtros">
	                                <i class="fas fa-times"></i>
	                            </a>
	                        <% } %>
	                    </div>
	                </div>
	            <% } else { %>
	                <div class="my-4">
	                    <h1>Partidos</h1>
	                </div>
	            <% } %>
	
	            <% if (emptyList) { %>
	                
	                <div class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
	                
	                    <div class="col-12">
	                        
	                        <% if (hasFilters) { %>
	                            <div class="empty-state text-center py-5 px-4 my-2 text-white">
	                                <div class="mx-auto mb-3 text-white-50">
	                                    <i class="fas fa-search fa-3x"></i>
	                                </div>
	                                <h3 class="fw-bold mb-2">Sin resultados</h3>
	                                <p style="opacity:.85;">
	                                    No se encontraron partidos para los filtros seleccionados
	                                </p>
	                                <a href="actionmatch" class="btn btn-outline-light mt-2">Ver todos</a>
	                            </div>
	                        
	                        <% } else { %>
	                            <div class="empty-state text-center py-5 px-4 my-2 text-white">
	                                <div class="mx-auto mb-2 pulse" style="width:72px;height:72px;">
	                                    <svg viewBox="0 0 24 24" width="72" height="72" fill="none">
	                                        <path d="M3 7.5a2 2 0 0 1 2-2h5l2 2h7a2 2 0 0 1 2 2V17a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7.5Z" 
	                                              stroke="white" stroke-opacity=".9" stroke-width="1.5" />
	                                        <path d="M12 12h6M15 9v6" 
	                                              stroke="white" stroke-width="1.5" stroke-linecap="round" />
	                                    </svg>
	                                </div>
	                                <h3 class="fw-bold mb-2">Todavía no agregaste torneos</h3>
	                                <p style="opacity:.85;">
	                                    No hay partidos registrados. Dirigite a la <strong>Sección de Torneos</strong> para agregar el primero.
	                                </p>
	                            </div>
	                        <% } %>
	                        
	                    </div>
	                </div>
	
	            <% } else { %>
	                        
	                <div class="col-12">
	                    <div class="table-responsive rounded-3 border overflow-hidden mb-5">
	                        <table class="table table-dark mb-0">
	                            <thead>
	                                <tr>
	                                    <th>Local</th>
										<th>Resultado</th>
	                                    <th>Visitante</th>
	                                    <th>Torneo</th>
	                                    <th>Instancia</th>
	                                    <th>Grupo</th>
	                                    <th>Jornada</th>
	                                    <th>Fecha y Hora</th>
	                                    <th>Registrar Resultado</th>
	                                </tr>
	                            </thead>
	                            <tbody>
								    <% for (Match m : ml) { 
								    	boolean isTimePending = m.getDate().isAfter(LocalDateTime.now());
								        boolean isResultMissing = (m.getHomeGoals() == null || m.getAwayGoals() == null);							        
								        boolean showClock = isTimePending || isResultMissing;
								        boolean disableButton = isTimePending;
								    %>
								        <tr>
								            <td>
								                <img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getHome().getBadgeImage()%>" height="40" >
								            </td>
								            
								            <td>
								                
								                <% if (showClock) { %>
								                
								                    <div class="d-inline-flex align-items-center justify-content-center rounded px-3 py-1"
								                         style="min-width: 80px; background-color: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.1);">
								                        <i class="far fa-clock text-secondary" style="font-size: 1.2rem;"></i>
								                    </div>  
								                
								                <% } else { %>
								                
								                    <div class="d-inline-flex align-items-center justify-content-center bg-dark border border-secondary rounded px-3 py-1"
								                         style="min-width: 80px; box-shadow: inset 0 2px 4px rgba(0,0,0,0.5);">
								                        <span class="fs-5 fw-bold text-white"><%= m.getHomeGoals() %></span>
								                        <span class="mx-2 text-secondary" style="font-size: 0.8em;">-</span>
								                        <span class="fs-5 fw-bold text-white"><%= m.getAwayGoals() %></span>
								                    </div>
								                
								                <% } %>
								            
								            </td>
								            
								            <td>
								                <img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getAway().getBadgeImage()%>" height="40" >
								            </td>
								            <td><%= m.getTournament().getName() %></td>
								            <td><%= m.getTournament().getFormat().hasStages() ? m.getStage().getDisplayName() : "-" %></td>
								            <td><%= m.getTournament().getFormat().hasStages() ? m.getGroupName() : "-" %></td>
								            <td><%= (m.getMatchday() != null ? m.getMatchday() : "-") %></td>
								            <td><%= m.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></td>
								            
								            <td>
								                <span class="d-inline-block" tabindex="0" 
								                      title="<%= disableButton ? "El partido todavía no se jugó" : "Registrar Resultado" %>">
								                      
									                <button type="button" 
									                        class="btn btn-sm text-dark shadow-sm"
									                        style="background-color: #1D442E; <%= disableButton ? "opacity: 0.5; pointer-events: none;" : "" %>"
									                        <%= disableButton ? "disabled" : "" %>
									                        data-bs-toggle="modal" data-bs-target="#resultModal"
									                        onclick="openResultModal(
									                            <%= m.getId() %>, 
									                            '<%= m.getHome().getName() %>', 
									                            '<%= m.getHome().getBadgeImage() %>',
									                            <%= m.getHomeGoals() %>,
									                            '<%= m.getAway().getName() %>', 
									                            '<%= m.getAway().getBadgeImage() %>',
									                            <%= m.getAwayGoals() %>
									                        )">
									                    
									                    <img src="${pageContext.request.contextPath}/assets/scoreboard.svg" 
									                         style="display: block; <%= disableButton ? "filter: grayscale(100%);" : "" %>"
									                         alt="Registrar Resultado" width="25" height="25">
									                </button>
								                </span>
								            </td>                               
								        </tr>
								    <% } %>
								</tbody>
	                        </table>
	                    </div>
	                </div>
	            <% } %>
        	</div>
    	</div>
    	
    	<div class="modal fade" id="resultModal" tabindex="-1" aria-hidden="true">
		    <div class="modal-dialog modal-dialog-centered">
		        <form action="actionmatch" method="post" class="modal-content bg-dark text-white border border-secondary shadow-lg">
		            <input type="hidden" name="action" value="setResult"> 
		            <input type="hidden" name="id" id="resultMatchId">
		            
		            <input type="hidden" name="tournamentId" value="<%= request.getParameter("tournamentId") != null ? request.getParameter("tournamentId") : "" %>">
    				<input type="hidden" name="clubId" value="<%= request.getParameter("clubId") != null ? request.getParameter("clubId") : "" %>">
		            
		            <div class="modal-header border-secondary bg-black bg-opacity-25">
		                <h5 class="modal-title d-flex align-items-center gap-2">
		                    <i class="fas fa-clipboard-list text-warning me-2"></i> <span>Registrar Resultado</span>
		                </h5>
		                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
		            </div>
		            
		            <div class="modal-body py-4">
		                <div class="d-flex justify-content-between align-items-center px-2">
		                    
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgHome" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="nameHome" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Local</h6>
		                    </div>
		
		                    <div class="d-flex align-items-center justify-content-center gap-2 mx-4" style="width: 30%;">
		                        <input type="number" name="home_goals" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                        
		                        <span class="fw-bold text-white-50" style="font-size: 1.5rem;">-</span>
		                        
		                        <input type="number" name="away_goals" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                    </div>
		
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgAway" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="nameAway" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Visitante</h6>
		                    </div>
		                    
		                </div>
		                
		                <div class="text-center mt-4">
		                    <small class="text-white-50">
		                        <i class="fas fa-info-circle me-1"></i> Ingresá el resultado final del partido.
		                    </small>
		                </div>
		            </div>
		            
		            <div class="modal-footer border-secondary bg-black bg-opacity-25">
		                <button type="button" class="btn btn-outline-light btn-sm" data-bs-dismiss="modal">Cancelar</button>
		                <button type="submit" class="btn btn-success btn-sm fw-bold px-3">
		                    Confirmar
		                </button>
		            </div>
		        </form>
		    </div>
		</div>
    
    	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	
	    <script>
		    
	    	function openResultModal(matchId, homeName, homeBadge, homeGoals, awayName, awayBadge, awayGoals) {
		        
		        document.getElementById("resultMatchId").value = matchId;
		        
		        var imgBase = "images?id="; 
		        document.getElementById("imgHome").src = imgBase + homeBadge;
		        document.getElementById("imgAway").src = imgBase + awayBadge;
		
		        const limit = 15;
		
		        const displayHome = homeName.length > limit ? homeName.substring(0, limit) + "..." : homeName;
		        const displayAway = awayName.length > limit ? awayName.substring(0, limit) + "..." : awayName;
		
		        var lblHome = document.getElementById("nameHome");
		        var lblAway = document.getElementById("nameAway");
		        
		        lblHome.textContent = displayHome;
		        lblAway.textContent = displayAway;
		        
		        lblHome.title = homeName; 
		        lblAway.title = awayName;
		        
		        document.querySelector("input[name='home_goals']").value = 0;
		        document.querySelector("input[name='away_goals']").value = 0;
		
		        if (homeGoals !== null && awayGoals !== null) {
		            document.querySelector("input[name='home_goals']").value = homeGoals;
		            document.querySelector("input[name='away_goals']").value = awayGoals;
		        }
		    }
	    	
		</script>
	
	</body>
</html>