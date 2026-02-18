<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Tournament"%>
<%@ page import="entities.User"%>
<%@ page import="enums.TournamentFormat"%>
<%@ page import="enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
		<title>Torneos</title>
		
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
		
			LinkedList<Tournament> tl = (LinkedList<Tournament>) request.getAttribute("tournamentsList");
			boolean emptyList = (tl == null || tl.isEmpty());
		%>
		
	</head>
	
	<body style="background-color: #10442E;">
	
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Torneos</h1>
		        	<% if (userLogged != null && userLogged.getRole() == UserRole.ADMIN) { %>
			        	<form action="actiontournament" method="get" style="margin:0;">
			        		<input type="hidden" name="action" value="add" />
						    <button type="submit" class="btn btn-dark btn-circular" style="border:none; background:none; padding:0;">
						        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" style="display: block;" alt="Agregar" width="40" height="40">
						    </button>
			    		</form>
		    		<% } %>		
				</div>
				<% if (emptyList) { %>
					<div class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
						<div class="col-12">
					  		<div class="empty-state text-center py-5 px-4 my-2 text-white">
					      		<div class="mx-auto mb-2 pulse" style="width:72px;height:72px;">
						        <svg viewBox="0 0 24 24" width="72" height="72" fill="none" aria-hidden="true">
						          <path d="M3 7.5a2 2 0 0 1 2-2h5l2 2h7a2 2 0 0 1 2 2V17a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7.5Z" stroke="white" stroke-opacity=".9" stroke-width="1.5"/>
						          <path d="M12 12h6M15 9v6" stroke="white" stroke-width="1.5" stroke-linecap="round"/>
						        </svg>
					      	</div>
						    <h3 class="fw-bold mb-2">Todavía no agregaste torneos</h3>
						    	<p class="mb-0" style="opacity:.85;">
						        	No hay torneos registrados. Usá el botón de <strong>(+)</strong> cuando quieras agregar el primero.
						      	</p>
					    	</div>
					  	</div>
					</div>
				<% } else { %>
	            	<div class="col-12 col-sm-12 col-lg-12">
	                	<div class="table-responsive rounded-3 border overflow-hidden mb-5">
	                    	<table class="table table-dark mb-0">
	                    		<thead>
	                    			<tr>
							            <th>Nombre</th>
							            <th>Edición</th>
							            <th>Fecha de Inicio</th>
							            <th>Fecha de Fin</th>
							            <th>Formato</th>
							            <th>Asociación</th>
							            <th>Acciones</th>
	                      			</tr>
	                      		</thead>
	                    		<tbody>
	                    		<%
	                    	    	for (Tournament t : tl) {
	                    		%>
	                    			<tr>
	                    				<td><%=t.getName()%></td>
	                    				<td><%=t.getSeason()%></td>
	                    				<td><%=t.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
	                    				<td><%=t.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
	                    				<td title="<%= t.getFormat().getDescription() %>" style="cursor: help;">
									        <%= t.getFormat().getShortName() %>
									    </td>
	                    				<td><%=t.getAssociation().getName()%></td>
	                    				<td>
										    <div class="d-flex justify-content-center align-items-center gap-2">
										        
										        <a href="actionmatch?tournamentId=<%= t.getId() %>" 
										           class="btn btn-sm p-0 d-flex justify-content-center align-items-center" 
										           style="background-color: #192A56; width: 40px; height: 40px;"
										           title="Ver Fixture">
										           <img src="${pageContext.request.contextPath}/assets/football-pitch.svg" 
										                 alt="Fixture" width="25" height="25">
										        </a>
										
										        <form method="get" action="actiontournament" class="m-0">
										            <input type="hidden" name="action" value="standings" />
										            <input type="hidden" name="id" value="<%=t.getId()%>" />
										            <button type="submit" style="background-color: #B35900; width: 40px; height: 40px;" 
										                    class="btn btn-sm p-0 d-flex justify-content-center align-items-center" 
										                    title="Ver Tabla de Posiciones">
										                <img src="${pageContext.request.contextPath}/assets/ranking.svg" alt="Posiciones" width="25" height="25">
										            </button>
										        </form>
										    
										        <% 
										           if (isAdmin) { 

										               boolean isFinished = t.isFinished();
										               
										               boolean isLeague = (t.getFormat() == TournamentFormat.ROUND_ROBIN_ONE_LEG || 
										                                   t.getFormat() == TournamentFormat.ROUND_ROBIN_TWO_LEGS);
										               
										               boolean isCupFormat = (t.getFormat() == TournamentFormat.ZONAL_ELIMINATION || 
										                                      t.getFormat() == TournamentFormat.WORLD_CUP);
										               
										               boolean playoffsExists = t.isPlayoffsAlreadyGenerated();
										               
										               boolean groupsFinished = t.isAllGroupMatchesPlayed();
										               
										               boolean canGenerateNext = t.isCanGenerateNextStage();
										               
										               String nextStageLabel = t.getNextStageLabel();
										               
										               boolean isFinishAction = canGenerateNext && "Finalizar Torneo".equals(nextStageLabel);
										
										               boolean showInitButton = isCupFormat && !playoffsExists && !isFinished;
										               
										               boolean enableInitButton = groupsFinished;
										               
										               String initTooltip = "";
										               
										               if (showInitButton) {
										               
										            	   if (enableInitButton) {
										                   
										            		   initTooltip = "Generar Fase de Eliminación";
										                   
										            	   } else {
										                   
										            		   String phaseName = (t.getFormat() == TournamentFormat.WORLD_CUP) ? "Fase de Grupos" : "Fase de Zonas";
										                       initTooltip = "Generar Fase de Eliminación (Faltan resultados de " + phaseName + ")";
										                   
										            	   }
										               
										               }
										
										               boolean showNextStageButton = canGenerateNext && !isFinishAction && !isFinished;
										               
										               boolean showFinishButton = false;
										               
										               boolean enableFinishButton = false;
										               
										               String finishTooltip = "Finalizar Torneo";
										               
										               if (!isFinished) {
										               
										            	   if (isLeague) {
										                   
										            		   showFinishButton = true;
										                       
										            		   enableFinishButton = isFinishAction;
										                       
										            		   if (!enableFinishButton) {
										                       
										            			   finishTooltip = "Finalizar Torneo (Hay partidos que todavía no se jugaron)";
										                       
										            		   }
										                   
										            	   } else {
										                   
										            		   showFinishButton = isFinishAction;
										                       
										            		   enableFinishButton = true;
										                   
										            	   }
										               
										               }
										        %>
										            
										            <div class="d-flex justify-content-center align-items-center" style="width: 40px; height: 40px;">
										
										                <% if (isFinished) { %>
										                    
										                    <span class="text-success d-flex justify-content-center align-items-center" 
										                          title="Torneo Finalizado" 
										                          style="width: 40px; height: 40px; cursor: help;"> 
										                          <i class="fas fa-check-circle fa-lg"></i>
										                    </span>
										                
										                <% } else if (showFinishButton) { %>
										                    
										                    <form method="post" action="actiontournament" class="m-0">
										                        <input type="hidden" name="action" value="finishTournament"> 
										                        <input type="hidden" name="id" value="<%= t.getId() %>">
										                        <span class="d-inline-block" tabindex="0" title="<%= finishTooltip %>">
										                            <button type="button" class="btn btn-sm btn-open-modal btn-success text-white fw-bold p-0 d-flex justify-content-center align-items-center"
										                                    data-action="finish" 
										                                    data-id="<%= t.getId() %>" 
										                                    data-name="<%= t.getName() %>"
										                                    style="width: 40px; height: 40px; <%= !enableFinishButton ? "cursor: not-allowed; opacity: 0.6;" : "" %>"
										                                    <%= !enableFinishButton ? "disabled" : "" %>>
										                                <img src="${pageContext.request.contextPath}/assets/finish.svg" alt="" width="25" height="25">
										                            </button>
										                        </span>
										                    </form>
										
										                <% } else if (showInitButton) { %>
										                    
										                    <form method="post" action="actiontournament" class="m-0">
										                        <input type="hidden" name="action" value="generatePlayoffs">
										                        <input type="hidden" name="id" value="<%= t.getId() %>">
										                        <span class="d-inline-block" tabindex="0" title="<%= initTooltip %>">
										                            <button type="button" class="btn btn-sm btn-open-modal btn-warning text-dark fw-bold p-0 d-flex justify-content-center align-items-center"
										                                    data-action="playoffs" 
										                                    data-id="<%= t.getId() %>" 
										                                    data-name="<%= t.getName() %>"
										                                    style="width: 40px; height: 40px; <%= !enableInitButton ? "cursor: not-allowed; opacity: 0.6;" : "" %>"
										                                    <%= !enableInitButton ? "disabled" : "" %>>
										                                <img src="${pageContext.request.contextPath}/assets/trophy-2.svg" alt="" width="25" height="25">
										                            </button>
										                        </span>
										                    </form>
										                
										                <% } else if (showNextStageButton) { %>
										                    
										                    <form method="post" action="actiontournament" class="m-0">
										                        <input type="hidden" name="action" value="generateNextStage">
										                        <input type="hidden" name="id" value="<%= t.getId() %>">
										                        <span class="d-inline-block" tabindex="0" title="<%= t.getNextStageLabel() %>">
										                            <button type="button" class="btn btn-sm btn-open-modal btn-info text-white fw-bold p-0 d-flex justify-content-center align-items-center"
										                                    data-action="nextStage" 
										                                    data-id="<%= t.getId() %>" 
										                                    data-name="<%= t.getName() %>"
										                                    data-label="<%= t.getNextStageLabel() %>"
										                                    style="width: 40px; height: 40px;">
										                                <img src="${pageContext.request.contextPath}/assets/forward.svg" alt="" width="35" height="35">
										                            </button>
										                        </span>
										                    </form>
										                    
										                <% } else if (isCupFormat && playoffsExists) { %>
										                    
										                    <span class="text-warning d-flex justify-content-center align-items-center" 
										                          title="<%= (t.getCurrentStatusLabel() != null) ? t.getCurrentStatusLabel() : "Fase en disputa" %>" 
										                          style="width: 40px; height: 40px; cursor: help;">
										                          <i class="fas fa-hourglass-half fa-fade fa-lg" style="--fa-animation-duration: 2.5s;"></i>
										                    </span>
										                
										                <% } else { %>
										                
										                    <span class="text-white-50 fw-bold d-flex justify-content-center align-items-center" style="width: 40px; height: 40px;">-</span>
										                
										                <% } %>
										            
										            </div>
										
										            <% boolean hasMatchesPlayed = t.isHasMatchesPlayed(); %>
										            
										            <form method="post" action="actiontournament" class="m-0">
										                <input type="hidden" name="action" value="delete" />
										                <input type="hidden" name="id" value="<%=t.getId()%>" />
										                <span class="d-inline-block" tabindex="0" 
										                      title="<%= hasMatchesPlayed ? "No se puede eliminar: el torneo ya tiene partidos jugados" : "Eliminar Torneo" %>">
										                    <button type="button" style="background-color: #9B1C1C; width: 40px; height: 40px; border: none; <%= hasMatchesPlayed ? "opacity: 0.5;" : "" %>" 
										                            class="btn btn-sm btn-open-modal p-0 d-flex justify-content-center align-items-center" 
										                            data-action="delete" 
										                            data-id="<%= t.getId() %>" 
										                            data-name="<%= t.getName() %>"
										                            <%= hasMatchesPlayed ? "disabled" : "" %>> 
										                        <img src="${pageContext.request.contextPath}/assets/delete.svg" alt="" width="25" height="25">
										                    </button>
										                </span>
										            </form>
										            
										        <% } %>
										        
										    </div>
										</td>
	                    			</tr>
	                    		<% 
	                    			}
	                    		%>
	                    		</tbody>
							</table>        		
						</div>
					</div>
				<% } %>
			</div>
		</div>     	
		
		<div class="modal fade" id="confirmModal" tabindex="-1" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content text-dark">
		      <div class="modal-header">
		        <h5 class="modal-title" id="modalTitle">Confirmación</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
		      </div>
		      <div class="modal-body" id="modalBody">
		        </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
		        <button type="button" class="btn" id="modalConfirmBtn">Confirmar</button>
		      </div>
		    </div>
		  </div>
		</div>
			
		<script>
		    
			document.addEventListener("DOMContentLoaded", function() {
		        
				const modalElement = document.getElementById('confirmModal');
		        const modal = new bootstrap.Modal(modalElement);
		        
		        const modalTitle = document.getElementById('modalTitle');
		        const modalBody = document.getElementById('modalBody');
		        const confirmBtn = document.getElementById('modalConfirmBtn');
		
		        let currentForm = null;
		
		        document.querySelectorAll('.btn-open-modal').forEach(button => {
		            
		        	button.addEventListener('click', function() {

		                const actionType = this.getAttribute('data-action');
		                const name = this.getAttribute('data-name');
		                
		                currentForm = this.closest('form');
		                
		                if (actionType === 'delete') {

		                	modalTitle.textContent = 'Eliminar Torneo';
		                    confirmBtn.className = 'btn btn-danger fw-bold';
		                    confirmBtn.textContent = 'Eliminar';
		
		                    modalBody.innerHTML = 
		                    	"<p class='mt-1 mb-3 text-center'>¿Estás seguro que querés eliminar el torneo?</p>" +
				                "<div class='alert alert-danger border-danger d-flex align-items-center mt-4' role='alert'>" +
				                    "<i class='fas fa-exclamation-triangle fs-4 me-3 flex-shrink-0'></i>" +
				                    "<div>" +
				                        "<strong>¡Atención!</strong> Esta acción es <b><u>irreversible</u></b>.<br>" +
				                        "Se borrarán permanentemente todos sus partidos programados." +
				                    "</div>" +
				                "</div>";
		
		                } else if (actionType === 'playoffs') {

		                	modalTitle.textContent = 'Generar Fase de Eliminación';
		                    confirmBtn.className = 'btn btn-success fw-bold'; 
		                    confirmBtn.textContent = 'Confirmar Generación';
		
		                    modalBody.innerHTML = 
		                    	"<p class='text-center mb-3'>¿Estás seguro que querés generar los cruces eliminatorios para <b>" + name + "</b>?</p>" +
		                        "<div class='alert alert-primary d-flex align-items-center' role='alert'>" +
		                            "<i class='fas fa-info-circle fs-4 me-3 flex-shrink-0'></i>" +
		                            "<div>" +
		                                "Se utilizará la tabla de posiciones actual para generar los partidos de la <b>primera instancia eliminatoria</b>." +
		                            "</div>" +
		                        "</div>";
		                        
		                } else if (actionType === 'nextStage') {

		                	const label = this.getAttribute('data-label');
		                	modalTitle.textContent = 'Avanzar de Fase de Eliminación';
		                    confirmBtn.className = 'btn btn-info text-white fw-bold'; 
		                    confirmBtn.textContent = 'Confirmar';
		
		                    modalBody.innerHTML = 
		                    	"<p class='text-center mb-3'>¿Estás seguro que querés: <br><b>" + label + "</b> para <b>" + name + "</b>?</p>" +
		                        "<div class='alert alert-info d-flex align-items-center' role='alert'>" +
		                            "<i class='fas fa-forward fs-4 me-3 flex-shrink-0'></i>" +
		                            "<div>" +
		                                "Se generarán los cruces basándose en los ganadores de la fase de eliminación anterior." +
		                            "</div>" +
		                        "</div>";
		
		                } else if (actionType === 'finish') {
							
		                	modalTitle.textContent = 'Finalizar Torneo';
		                    confirmBtn.className = 'btn btn-success text-dark fw-bold'; 
		                    confirmBtn.innerHTML = '<span class="text-white">Finalizar Torneo</span>';
		
		                    modalBody.innerHTML = 
		                    	"<p class='text-center mb-3'>¿Estás seguro que querés dar por finalizado: <br><b>" + name + "</b>?</p>" +
		                        "<div class='alert alert-success d-flex align-items-center' role='alert'>" +
		                            "<i class='fas fa-trophy fs-4 me-3 flex-shrink-0'></i>" +
		                            "<div>" +
		                                "El torneo pasará al historial de finalizados y se bloqueará la edición de resultados." +
		                            "</div>" +
		                        "</div>";
		                }
		                
		                modal.show();

		        	});
		        
		        });
		        
		        confirmBtn.addEventListener('click', function() {
		        	
		        	if (currentForm) currentForm.submit();
		        
		        	modal.hide();
		        
		        });
		    
			});
		
		</script>
	
	</body>
</html>