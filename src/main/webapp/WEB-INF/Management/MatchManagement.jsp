<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Match"%>
<%@ page import="entities.Club"%>
<%@ page import="entities.Tournament"%>
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
    %>

</head>

<body style="background-color: #10442E;">

    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>

    <div class="container" style="color: white;">
        
        <div class="row">
            <div class="d-flex justify-content-between my-4 align-items-center">
                <h1>Partidos</h1>
				<form method="get" action="actionmatch" class="d-flex align-items-center gap-3 ms-auto m-0">
	          		<label for="clubFilter" class="form-label m-0 fs-5">Filtrar por torneo:</label>
	          		
	          			<input type="hidden" name="clubId" value="<%= request.getParameter("clubId") != null ? request.getParameter("clubId") : "" %>">
	          			<select name="tournamentId" id="tournamentFilter"
	                  		class="form-select form-select-sm w-auto fancy-select bg-dark text-white"
	                  		onchange="this.form.submit()">
	            			<option value="">Todos los torneos</option>
				            <%
				            	LinkedList<Tournament> tournaments = (LinkedList<Tournament>) request.getAttribute("tournamentsList");
				              	
				            	String selectedTournament = request.getParameter("tournamentId");
				              	
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
	        	<form method="get" action="actionmatch" class="d-flex align-items-center gap-3 ms-auto m-0">
	          		<label for="clubFilter" class="form-label m-0 fs-5">Filtrar por club:</label>
	          		<input type="hidden" name="tournamentId" value="<%= request.getParameter("tournamentId") != null ? request.getParameter("tournamentId") : "" %>">
	          			<select name="clubId" id="clubFilter"
	                  		class="form-select form-select-sm w-auto fancy-select bg-dark text-white"
	                  		onchange="this.form.submit()">
	            			<option value="">Todos los clubes</option>
				            <%
				            	LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubsList");
				              	
				            	String selectedClub = request.getParameter("clubId");
				              	
				            	int selectedId_club  = -1;
				              	
				            	if (selectedClub != null && !selectedClub.isEmpty()) {
				                
				            		selectedId_club = Integer.parseInt(selectedClub);
				              	}
				              
				            	if (clubs != null) {
				                	
				            		for (Club c : clubs) {
				            %>
	              						<option value="<%= c.getId() %>" <%= (c.getId() == selectedId_club) ? "selected" : "" %>><%= c.getName() %></option>
	            			<%
					                }
					            }
	            			%>
	          			</select>
	        	</form>
                <form action="actionmatch" method="get" style="margin:0;" class="m-0 ms-5">
                    <input type="hidden" name="action" value="add" />
                    <button type="submit" class="btn btn-dark btn-circular" style="border:none; background:none; padding:0;">
                        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" alt="Agregar" width="40" height="40">
                    </button>
                </form>
            </div>

            <% if (emptyList) { %>

                <div class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
                    <div class="col-12">
                        <div class="empty-state text-center py-5 px-4 my-2 text-white">
                            <div class="mx-auto mb-2 pulse" style="width:72px;height:72px;">
                                <svg viewBox="0 0 24 24" width="72" height="72" fill="none">
                                    <path d="M3 7.5a2 2 0 0 1 2-2h5l2 2h7a2 2 0 0 1 2 2V17a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7.5Z" 
                                          stroke="white" stroke-opacity=".9" stroke-width="1.5" />
                                    <path d="M12 12h6M15 9v6" 
                                          stroke="white" stroke-width="1.5" stroke-linecap="round" />
                                </svg>
                            </div>
                            <h3 class="fw-bold mb-2">Todavía no agregaste partidos</h3>
                            <p style="opacity:.85;">
                                No hay partidos registrados. Usá el botón de <strong>(+)</strong> cuando quieras agregar el primero.
                            </p>
                        </div>
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
                                    <th>Jornada</th>
                                    <th>Fecha y Hora</th>
                                    <th>Editar</th>
                                    <th>Eliminar</th>
                                </tr>
                            </thead>
                            <tbody>

                            <% for (Match m : ml) { %>
                                <tr>
                                    <td>
                                    	<img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getHome().getBadgeImage()%>" height="40" >
                                    </td>
                                    <td>
                                    	<h4>
                                    		<%= (m.getHomeGoals() != null ? m.getHomeGoals() : "") %> 
                                        	- 
                                        	<%= (m.getAwayGoals() != null ? m.getAwayGoals() : "") %>
                                    	</h4>
                                    </td>
                                    <td>
                                    	<img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getAway().getBadgeImage()%>" height="40" >
                                    </td>
                                    <td><%= m.getTournament().getName() %></td>
                                    <td><%= (m.getMatchday() != null ? m.getMatchday() : "-") %></td>
                                    <td><%= m.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></td>
                                    

                                    <td>
                                        <form method="get" action="actionmatch" class="d-flex justify-content-center align-items-center">
                                            <input type="hidden" name="action" value="edit" />
                                            <input type="hidden" name="id" value="<%= m.getId() %>" />
                                            <button type="submit" class="btn btn-sm" style="background-color: #0D47A1;">
                                                <img src="${pageContext.request.contextPath}/assets/edit.svg" width="25" height="25" alt="Editar">
                                            </button>
                                        </form>
                                    </td>

                                    <td>
                                        <form method="post" action="actionmatch" class="d-flex justify-content-center align-items-center">
                                            <input type="hidden" name="action" value="delete" />
                                            <input type="hidden" name="id" value="<%= m.getId() %>" />
                                            <button type="button" class="btn btn-sm btn-open-modal" 
                                                    style="background-color: #9B1C1C;"
                                                    data-id="<%= m.getId() %>">
                                                <img src="${pageContext.request.contextPath}/assets/delete.svg" width="25" height="25" alt="Eliminar">
                                            </button>
                                        </form>
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

    <!-- MODAL -->
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

    <!-- Scripts -->
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
