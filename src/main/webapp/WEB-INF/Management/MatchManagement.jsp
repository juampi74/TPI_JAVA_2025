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
	                                </tr>
	                            </thead>
	                            <tbody>
	
	                            <% for (Match m : ml) { %>
	                                <tr>
	                                    <td>
	                                    	<img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getHome().getBadgeImage()%>" height="40" >
	                                    </td>
	                                    <td>
										    <% 
										       boolean isPending = m.getDate().isAfter(LocalDateTime.now());
										       
										       if (isPending) { 
										    %>
										    
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
	                                </tr>
	                            <% } %>
	                            </tbody>
	                        </table>
	                    </div>
	                </div>
	            <% } %>
	        	</div>
	    	</div>
	    
	    <div class="modal fade" id="confirmModal" tabindex="-1">
	        <div class="modal-dialog modal-dialog-centered">
	            <div class="modal-content text-dark">
	                <div class="modal-header">
	                    <h5 class="modal-title">Confirmar eliminación</h5>
	                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
	                </div>
	
	                <div class="modal-body" id="confirmModalBody">
	                    ¿Estás seguro que querés eliminar este partido?
	                </div>
	
	                <div class="modal-footer">
	                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
	                    <button type="button" class="btn btn-danger" id="confirmModalYes">Eliminar</button>
	                </div>
	            </div>
	        </div>
	    </div>

	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
	
	    <script>
	        document.addEventListener("DOMContentLoaded", () => {
	
	            const modal = new bootstrap.Modal(document.getElementById("confirmModal"));
	            const confirmBtn = document.getElementById("confirmModalYes");
	
	            let currentForm = null;
	
	            document.querySelectorAll(".btn-open-modal").forEach(btn => {
	                btn.addEventListener("click", function () {
	                    currentForm = this.closest("form");
	                    modal.show();
	                });
	            });
	
	            confirmBtn.addEventListener("click", () => {
	                if (currentForm) currentForm.submit();
	                modal.hide();
	            });
	
	        });
	    </script>
	
	</body>
</html>