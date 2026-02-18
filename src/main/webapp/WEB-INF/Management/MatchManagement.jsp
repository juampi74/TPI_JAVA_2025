<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.LocalDateTime"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.*"%>
<%@ page import="enums.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <title>Partidos</title>
	
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
	
	    <style>
	        .table {
	        	text-align: center;
	        }
	        table th, table td {
	            vertical-align: middle !important;
	        }
	    </style>
	
	    <%
	    	User userLogged = (User) session.getAttribute("user");
    		boolean isAdmin = (userLogged != null && userLogged.getRole() == UserRole.ADMIN);
	    
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
	                            <a href="actionmatch?clear=true" class="btn btn-sm btn-outline-light" title="Borrar filtros">
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
	                                    <th>Zona / Llave</th>
	                                    <th>Jornada</th>
	                                    <th>Fecha y Hora</th>
										
										<% if (isAdmin) { %>
								            <th>Registrar Resultado</th>
								        <% } %>
									</tr>
	                            </thead>
	                            <tbody>
								    <% for (Match m : ml) { 
									    boolean isTimePending = m.getDate().isAfter(LocalDateTime.now());
									    boolean isResultMissing = (m.getHomeGoals() == null || m.getAwayGoals() == null);							        
									    boolean showClock = isTimePending || isResultMissing;
									    
									    boolean isTournamentFinished = m.getTournament().isFinished(); 
									
									    TournamentStage highest = m.getTournament().getHighestStage();
									    
									    boolean isPhaseClosed = false;
									    
									    if (highest != null) {
									        
									    	isPhaseClosed = m.getStage().ordinal() < highest.ordinal();
									        
									        boolean isFinalMoment = (highest == TournamentStage.FINAL);
									        boolean isThirdPlaceMatch = (m.getStage() == TournamentStage.THIRD_PLACE);
									        
									        if (isFinalMoment && isThirdPlaceMatch) {
									            
									        	isPhaseClosed = false;
									        
									        }
									    
									    }
									    
									    boolean disableButton = isTimePending || isPhaseClosed || isTournamentFinished;
									    
									    String tooltipText = "Registrar Resultado";
									    
									    if (isTournamentFinished) {
									
									        tooltipText = "Torneo finalizado. No se pueden modificar resultados.";
									
									    } else if (isTimePending) {
									    
									        tooltipText = "El partido todavía no se jugó";
									    
									    } else if (isPhaseClosed) {
									    
									        tooltipText = "Fase cerrada. Instancia actual: " + highest.getDisplayName();
									    
									    }
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
								                	       											        
												    <% if (m.getHomePenalties() != null) { %>
												    
												    	<div style="font-size: 0.75rem;" class="text-warning mt-1">
												        	(Pen: <%= m.getHomePenalties() %> - <%= m.getAwayPenalties() %>)
														</div>
													
													<% } %>
								                
								                <% } %>
								            
								            </td>
								            
								            <td>
								                <img alt="" src="<%=request.getContextPath() + "/images?id=" + m.getAway().getBadgeImage()%>" height="40" >
								            </td>
								            <td><%= m.getTournament().getName() %></td>
								            <td><%= m.getTournament().getFormat().hasStages() ? m.getStage().getDisplayName() : "-" %></td>
								            <td>
											    <%= (m.getGroupName() != null) 
											        ? m.getGroupName() 
											        : (m.getBracketCode() != null ? "Llave " + m.getBracketCode() : "-") 
											    %>
											</td>
								            <td><%= (m.getMatchday() != null ? m.getMatchday() : "-") %></td>
								            <td><%= m.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) %></td>
								            
								            <td>
								            	<% if (isAdmin) { %>
								            
												    <span class="d-inline-block" tabindex="0" title="<%= tooltipText %>">
												          
												        <button type="button" 
												                class="btn btn-sm text-dark shadow-sm"
												                style="background-color: #1D442E; <%= disableButton ? "opacity: 0.5; pointer-events: none;" : "" %>"
												                <%= disableButton ? "disabled" : "" %>
												                data-bs-toggle="modal" data-bs-target="#resultModal"										                
												                data-id="<%= m.getId() %>"
												                data-stage="<%= m.getStage().name() %>"
												                data-home-name="<%= m.getHome().getName() %>"
												                data-home-badge="<%= m.getHome().getBadgeImage() %>"
												                data-home-goals="<%= m.getHomeGoals() != null ? m.getHomeGoals() : "" %>"
												                data-away-name="<%= m.getAway().getName() %>"
												                data-away-badge="<%= m.getAway().getBadgeImage() %>"
												                data-away-goals="<%= m.getAwayGoals() != null ? m.getAwayGoals() : "" %>"										                
												                onclick="openResultModal(this)">
												            <img src="${pageContext.request.contextPath}/assets/scoreboard.svg" 
												                 style="display: block; <%= disableButton ? "filter: grayscale(100%);" : "" %>"
												                 alt="Registrar Resultado" width="25" height="25">
												        </button>
												        
												    </span>
												
												<% } %>
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
		    
		    <div class="modal-content bg-dark text-white border-secondary shadow-lg">
		      
		      <div class="modal-header border-secondary bg-black bg-opacity-25">
		        <h5 class="modal-title d-flex align-items-center gap-2" id="resultModalLabel">
		            <i class="fas fa-clipboard-list text-warning me-2"></i> <span>Registrar Resultado</span>
		        </h5>
		        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
		      </div>
		      
		      <form action="actionmatch" method="post" id="formResult">
		          <input type="hidden" name="action" value="setResult">
		          <input type="hidden" name="id" id="modalMatchId">
		          <input type="hidden" name="tournamentId" value="${param.tournamentId}"> 
		          <input type="hidden" id="modalMatchStage"> 
		          
		          <div class="modal-body py-4">
		            
		            <div id="step-match">
		                
		                <div class="d-flex justify-content-between align-items-center px-2">
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgHomeMatch" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="homeName" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Local</h6>
		                    </div>
		                    
		                    <div class="d-flex align-items-center justify-content-center gap-2 mx-4" style="width: 30%;">
		                        <input type="number" name="homeGoals" id="inputHomeGoals" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                        
		                        <span class="fw-bold text-white-50" style="font-size: 1.5rem;">-</span>
		                        
		                        <input type="number" name="awayGoals" id="inputAwayGoals" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                    </div>
		                    
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgAwayMatch" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="awayName" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Visitante</h6>
		                    </div>
		                </div>
		                
		                <div class="text-center mt-4">
		                    <small class="text-white-50">
		                        <i class="fas fa-info-circle me-1"></i> <span id="helpText">Ingresá el resultado final del partido.</span>
		                    </small>
		                </div>
		            </div>
		
		            <div id="step-penalties" class="d-none">
		                
		                <div class="text-center mb-4">
		                    <span class="badge bg-secondary fs-6 px-3 py-2" id="displayScore">Empate 0 - 0</span>
		                </div>
		
		                <h6 class="text-center text-info mb-3 text-uppercase fw-bold" style="letter-spacing: 1px;">Definición por Penales</h6>
		                
		                <div id="penaltiesError" class="alert alert-danger d-none mx-auto text-center py-2 mb-3" role="alert" style="font-size: 0.9rem;">
						    <i class="fas fa-exclamation-circle me-1"></i> 
						    <span id="penaltiesErrorText">No puede haber empate en los penales.</span>
						</div>
		
		                <div class="d-flex justify-content-between align-items-center">
		                    
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgHomePen" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="displayHomeNamePen" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Local</h6>
		                    </div>
		                    	                    
		                    <div class="d-flex align-items-center justify-content-center gap-2 mx-4" style="width: 30%;">
		                        <input type="number" name="homePenalties" id="inputHomePenalties" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                        
		                        <span class="fw-bold text-white-50" style="font-size: 1.5rem;">-</span>
		                        
		                        <input type="number" name="awayPenalties" id="inputAwayPenalties" class="form-control bg-secondary text-white text-center border-0 fw-bold p-0" 
		                               style="width: 50px; height: 50px; font-size: 1.5rem;" 
		                               min="0" placeholder="0" required>
		                    </div>
		                    	                    
		                    <div class="d-flex flex-column align-items-center" style="width: 35%;">
		                        <img id="imgAwayPen" src="" class="mb-2" 
		                             style="width: 64px; height: 64px; object-fit: contain; filter: drop-shadow(0 4px 6px rgba(0,0,0,0.5));">
		                        <h6 id="displayAwayNamePen" class="text-center fw-bold mb-0 text-truncate w-100" style="font-size: 0.9rem;">Visitante</h6>
		                    </div>
		                    
		                </div>
		            </div>
		          </div>    
		            
		          <div class="modal-footer border-secondary bg-black bg-opacity-25 justify-content-between">
		            <button type="button" class="btn btn-secondary d-none btn-sm" id="btnBackStep">
		                <i class="fas fa-arrow-left me-1"></i> Volver
		            </button>
		            
		            <div class="d-flex gap-2 ms-auto">
		            	<button type="button" class="btn btn-outline-light btn-sm" data-bs-dismiss="modal">Cancelar</button>
		                <button type="button" class="btn btn-success btn-sm fw-bold px-3" id="btnConfirmResult">Confirmar</button>
		            </div>
		          </div>
		      </form>
		    </div>
		  </div>
		</div>
			
		<script>
		    
			document.addEventListener("DOMContentLoaded", function() {
		        
		        const modalElement = document.getElementById('resultModal');
		        const modal = new bootstrap.Modal(modalElement);
		        
		        const stepMatch = document.getElementById('step-match');
		        const stepPenalties = document.getElementById('step-penalties');
		        const btnConfirm = document.getElementById('btnConfirmResult');
		        const btnBack = document.getElementById('btnBackStep');
		        const form = document.getElementById('formResult');
		        
		        const inputHomeGoals = document.getElementById('inputHomeGoals');
		        const inputAwayGoals = document.getElementById('inputAwayGoals');
		        const inputHomePen = document.getElementById('inputHomePenalties');
		        const inputAwayPen = document.getElementById('inputAwayPenalties');
		        const inputStage = document.getElementById('modalMatchStage');
		        
		        const imgHomeMatch = document.getElementById('imgHomeMatch');
		        const imgAwayMatch = document.getElementById('imgAwayMatch');
		        const txtHomeName = document.getElementById('homeName');
		        const txtAwayName = document.getElementById('awayName');
		        const helpText = document.getElementById('helpText');
		        
		        const imgHomePen = document.getElementById('imgHomePen');
		        const imgAwayPen = document.getElementById('imgAwayPen');
		        const txtHomeNamePen = document.getElementById('displayHomeNamePen');
		        const txtAwayNamePen = document.getElementById('displayAwayNamePen');
		        const txtDisplayScore = document.getElementById('displayScore');
		        
		        const penaltiesErrorContainer = document.getElementById('penaltiesError');
        		const penaltiesErrorMsg = document.getElementById('penaltiesErrorText');
		
		        let currentStep = 1;
		
		        window.openResultModal = function(btn) {
		            
		            const id = btn.getAttribute('data-id');
		            const home = btn.getAttribute('data-home-name');
		            const away = btn.getAttribute('data-away-name');
		            const homeG = btn.getAttribute('data-home-goals');
		            const awayG = btn.getAttribute('data-away-goals');
		            const stage = btn.getAttribute('data-stage');
		            
		            const badgeIdHome = btn.getAttribute('data-home-badge');
		            const badgeIdAway = btn.getAttribute('data-away-badge');
		            
		            const pathHome = "images?id=" + badgeIdHome;
		            const pathAway = "images?id=" + badgeIdAway;
		            
		            document.getElementById('modalMatchId').value = id;
		            inputStage.value = stage;
					
		            inputHomeGoals.value = (homeG !== '') ? homeG : 0;
		            inputAwayGoals.value = (awayG !== '') ? awayG : 0;
		            inputHomePen.value = '';
		            inputAwayPen.value = '';

		            currentStep = 1;
		            penaltiesErrorContainer.classList.add('d-none');
		            
		            if (stage === 'GROUP_STAGE') {
		            
		            	helpText.textContent = "Ingresá el resultado final del partido.";
		            
		            } else {
		            
		            	helpText.textContent = "Ingresá el resultado final del partido (90' o 120').";
		            
		            }
		
		            updateUI();
		            
		            txtHomeName.textContent = home;
		            txtAwayName.textContent = away;
		            imgHomeMatch.src = pathHome;
		            imgAwayMatch.src = pathAway;
		            
		            txtHomeNamePen.textContent = home;
		            txtAwayNamePen.textContent = away;
		            imgHomePen.src = pathHome;
		            imgAwayPen.src = pathAway;
		            
		
		            modal.show();
		            
		        };
		
		        btnConfirm.addEventListener('click', function() {
		            
		            const hGoals = parseInt(inputHomeGoals.value);
		            const aGoals = parseInt(inputAwayGoals.value);
		            const stage = inputStage.value;
		                            
		            if (currentStep === 1) {

		                if (isNaN(hGoals) || isNaN(aGoals)) {
		                    
		                	form.reportValidity();
		                    return; 
		                
		                }
		
		                if (stage === 'GROUP_STAGE') {
		                
		                	form.submit();
		                    return;
		                
		                }
		
		                if (hGoals === aGoals) {
		                    
		                	currentStep = 2;
		                    txtDisplayScore.textContent = "Empate " + hGoals + " - " + aGoals;
		                    updateUI();
		                    btnConfirm.blur();
		                    inputHomePen.focus();
		                
		                } else {
		                
		                	form.submit();
		                
		                }
		
		            } else if (currentStep === 2) {
		                
		                const hPen = parseInt(inputHomePen.value);
		                const aPen = parseInt(inputAwayPen.value);
		                
		                if (isNaN(hPen) || isNaN(aPen)) {
		                	
		                	penaltiesErrorMsg.textContent = "Ingresá el resultado de los penales.";
		                	penaltiesErrorContainer.classList.remove('d-none');
		                    return;
		                
		                }

		                if (hPen === aPen) {
		                
		                	penaltiesErrorMsg.textContent = "No puede haber empate en los penales.";
		                	penaltiesErrorContainer.classList.remove('d-none');
		                    return;
		                
		                }
		
		                form.submit();
		            
		            }
		        
		        });
		        
		        inputHomePen.addEventListener('input', () => penaltiesErrorContainer.classList.add('d-none'));
		        inputAwayPen.addEventListener('input', () => penaltiesErrorContainer.classList.add('d-none'));
		
		        btnBack.addEventListener('click', function() {

		        	currentStep = 1;
		            penaltiesErrorContainer.classList.add('d-none');
		            updateUI();
		        
		        });
		
		        function updateUI() {

		        	if (currentStep === 1) {
		            
		        		stepMatch.classList.remove('d-none');
		                stepPenalties.classList.add('d-none');
		                btnBack.classList.add('d-none');
		                checkMatchInputs();
		            
		        	} else {
		            
		        		stepMatch.classList.add('d-none');
		                stepPenalties.classList.remove('d-none');
		                btnBack.classList.remove('d-none');
		                btnConfirm.textContent = "Registrar Penales";
		                btnConfirm.classList.remove('btn-primary');
		                btnConfirm.classList.add('btn-success');
		            
		        	}
		        
		        }
		
		        function checkMatchInputs() {

		        	if (currentStep !== 1) return;
		
		            const h = inputHomeGoals.value;
		            const a = inputAwayGoals.value;
		            const isKnockout = (inputStage.value !== 'GROUP_STAGE');
		
		            if (h !== '' && a !== '' && h === a && isKnockout) {
		            
		            	btnConfirm.textContent = "Continuar";
		                btnConfirm.classList.remove('btn-success');
		                btnConfirm.classList.add('btn-primary');
		            
		            } else {
		            
		            	btnConfirm.textContent = "Confirmar";
		                btnConfirm.classList.remove('btn-primary');
		                btnConfirm.classList.add('btn-success');
		            
		            }
		        
		        }
		
		        inputHomeGoals.addEventListener('input', checkMatchInputs);
		        inputAwayGoals.addEventListener('input', checkMatchInputs);
		    
			});
			
		</script>
	
	</body>
</html>