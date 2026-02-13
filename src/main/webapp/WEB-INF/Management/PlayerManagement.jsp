<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.Map" %>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="java.time.Period"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="entities.*"%>
<%@ page import="enums.UserRole"%>
<%@ page import="enums.DominantFoot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta charset="utf-8">
  		<meta http-equiv="X-UA-Compatible" content="IE=edge">
  		<meta name="viewport" content="width=device-width, initial-scale=1">
  		<link rel="icon" type="image/x-icon" href="assets/favicon.png">
  		<title>Jugadores</title>

  		<link href="style/bootstrap.css" rel="stylesheet">
  		<link href="style/start.css" rel="stylesheet">

  		<style>
		    .table {
		      text-align:center;
		    }
		    
		    .table th, .table td {
		      vertical-align: middle !important;
		    }

		    .fancy-select{
		      --bs-form-select-bg: rgba(255,255,255,.07);
		      --bs-form-select-border-color: rgba(255,255,255,.35);
		      --bs-form-select-focus-border-color: #0D6EFD;
		      --bs-form-select-focus-box-shadow: 0 0 0 .2rem rgba(13,110,253,.25);
		      background-color: var(--bs-form-select-bg) !important;
		      color:#fff !important;
		      border-color: var(--bs-form-select-border-color) !important;
		      border-radius:.5rem;
		      padding-right:2.25rem;
		      box-shadow:none;
		      background-image:
		        url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='%23ffffff' d='M3.2 5.5 8 10.3l4.8-4.8.9.9L8 12.1 2.3 6.4l.9-.9z'/%3e%3c/svg%3e");
		      background-repeat:no-repeat;
		      background-position:right .6rem center;
		      background-size:1rem .8rem;
		    }
		    
		    .fancy-select:hover {
		      border-color:#fff !important;
		    }
		
		    .empty-state {
		      max-width:840px;
		    }
		    
		    .text-balance {
		      text-wrap: balance;
		    }
		    
		    .empty-state h3 {
		      font-size: clamp(1.25rem, 2vw + 1rem, 1.75rem);
		    }
		    
		    .empty-state p {
		      font-size: .95rem;
		      line-height: 1.5;
		    }
		    
		    .jersey-number-cell {
			  min-width: 65px;
			}
		    
			.tm-badge {
			  background-color: #004e7d;
			  color: white;
			  font-weight: 700;
			  font-size: 0.95rem;
			  width: 36px;
			  height: 36px;
			  min-width: 36px;
			  display: inline-flex;
			  align-items: center;
			  justify-content: center;
			  border-radius: 50%;
			  box-shadow: 0 1px 2px rgba(0,0,0,0.3);
			}
			
			.tm-badge-empty {
			  background-color: rgba(255, 255, 255, 0.05);
			  color: #6c757d;
			  border: 1px dashed #6c757d;
			  box-shadow: none;
			}

			.player-position {
			  font-size: 0.85rem;
		  	  color: #adb5bd;
		  	  font-weight: 400;
		  	  margin-top: 2px;
		  	  text-align: center;
			}

			.player-name {
				font-size: 1.05rem;
				font-weight: 700;
				color: #fff;
			}

			.national-flag {
			  height: 18px; 
			  width: auto; 
			  border-radius: 2px; 
			  opacity: 0.9; 
			  margin-left: 8px;
			  box-shadow: 0 1px 3px rgba(0,0,0,0.3);
			}
			
			.profile-pic-cell {
			  min-width: 110px;
			  margin: 0 auto;
			  display: flex;
			  justify-content: center;
			  align-items: center;
			}
			
			.profile-pic {
			  width: 65px;
			  height: 65px;
			  object-fit: contain; 
			  object-position: center; 
			  border-radius: 50%;
			  background-color: #e9ecef;
    		  border: 2px solid #2C632D;
			}
			
			.fullname-cell {
			    max-width: 200px;
			    margin: 0 auto;
			}
			
			.stat-value {
			    font-weight: 700;
			    font-size: 1rem;
			    color: #fff;
			}
			
			.stat-unit {
			    font-size: 0.75rem;
			    color: #adb5bd;
			    font-weight: 400;
			    margin-left: 2px;
			}
			
			.foot-badge {
			    padding: 4px 10px;
			    border-radius: 12px;
			    font-size: 0.8rem;
			    font-weight: 600;
			    text-transform: uppercase;
			    letter-spacing: 0.5px;
			}
			
			.foot-right {
			  background-color: rgba(13, 110, 253, 0.2);
			  color: #6ea8fe;
			  border: 1px solid rgba(13, 110, 253, 0.3);
			}
			
			.foot-left {
			  background-color: rgba(25, 135, 84, 0.2);
			  color: #75b798;
			  border: 1px solid rgba(25, 135, 84, 0.3);
			}
			
			.foot-ambi {
			  background-color: rgba(255, 193, 7, 0.2);
			  color: #ffda6a;
			  border: 1px solid rgba(255, 193, 7, 0.3);
			}
			
			.foot-unknown {
			  background-color: rgba(108, 117, 125, 0.2);
			  color: #adb5bd;
			  border: 1px solid rgba(108, 117, 125, 0.3);
			}
			
			.age-text {
			  color: #adb5bd;
			  font-size: 0.9em;
			  margin-left: 5px;
			}
			
			.club-badge {
			  width: 40px;
			  height: 40px;
			  object-fit: contain;
			  filter: drop-shadow(0 2px 2px rgba(0,0,0,0.3));
			  transition: transform 0.2s;
			  cursor: help;
			}
			
			.club-badge:hover {
			  transform: scale(1.2);
			}
			
			.free-agent-badge {
			  width: 40px;
			  height: 40px;
			  background-color: rgba(255, 255, 255, 0.05);
			  border-radius: 50%;
			  display: flex;
			  align-items: center;
			  justify-content: center;
			  color: #6c757d;
			  border: 1px dashed #6c757d;
			  margin: 0 auto;
			  transition: transform 0.2s;
			  cursor: help;
			}
			
			.free-agent-badge:hover {
			  transform: scale(1.2);
			}

			.select-free {
			  box-shadow: 0 0 0 .15rem rgba(255,193,7,0.12) !important;
			  border-color: rgba(255,193,7,0.45) !important;
			  background-color: rgba(255,193,7,0.06) !important;
			}

			.free-indicator {
			  display: inline-flex;
			  align-items: center;
			  gap: .4rem;
			  padding: .25rem .55rem;
			  border-radius: .45rem;
			  background: rgba(255,193,7,0.12);
			  color: #ffda6a;
			  border: 1px solid rgba(255,193,7,0.25);
			  font-weight: 600;
			  margin-left: .5rem;
			  white-space: nowrap;
			}
		</style>

		<%
			User userLogged = (User) session.getAttribute("user");
			boolean isAdmin = (userLogged != null && userLogged.getRole() == UserRole.ADMIN);
		
			LinkedList<Player> pll = (LinkedList<Player>) request.getAttribute("playersList");
		    boolean emptyList = (pll == null || pll.isEmpty());
		    
		    Map<Integer, String> positionsMap = (Map<Integer, String>) request.getAttribute("positionsMap");
		    Map<Integer, Club> currentClubsMap = (Map<Integer, Club>) request.getAttribute("currentClubsMap");
		%>
	</head>
	<body style="background-color: #10442E;">
  		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container text-white">
			<div class="row">
		    	<div class="d-flex flex-wrap align-items-center justify-content-between gap-3 my-4">
		        	<h1 class="m-0">Jugadores</h1>
		        	<form method="get" action="actionplayer" class="d-flex align-items-center gap-3 ms-auto m-0">
						<select name="clubId" id="clubFilter"
							class="form-select form-select-sm w-auto fancy-select bg-dark text-white"
							onchange="this.form.submit()">
								<option value="">Todos los clubes</option>
								<option value="free" <%= "free".equals(request.getParameter("clubId")) ? "selected" : "" %>>🔓 Agente Libre (Sin Club)</option>
				            <%
								LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubsList");
                     
								String selectedClub = request.getParameter("clubId");
                     
								int selectedId = -1;
                     
								if (selectedClub != null && !selectedClub.isEmpty()) {
									
									if (!"free".equals(selectedClub)) {
									
										try {
										
											selectedId = Integer.parseInt(selectedClub);
										
										} catch (NumberFormatException ex) {
										
											selectedId = -1;
										
										}
									
									}
								
								}
				              
				            	if (clubs != null) {
				                	
				            		for (Club c : clubs) {
				            %>
	              						<option value="<%= c.getId() %>" <%= (c.getId() == selectedId) ? "selected" : "" %>><%= c.getName() %></option>
	            			<%
					                }
					            }
	            			%>
						</select>
						<span id="freeIndicator" class="free-indicator d-none" title="Filtrando Agente Libre"><i class="fas fa-user-slash"></i>Agente Libre</span>
					</form>
		
					<% if (isAdmin) { %>
				        
				        <form action="actionplayer" method="get" class="m-0 ms-5">
				        	<input type="hidden" name="action" value="add" />
				          	<button type="submit" class="btn btn-dark btn-circular" style="border:none;background:none;padding:0;">
				            	<img src="${pageContext.request.contextPath}/assets/add-button2.svg" alt="Add" width="40" height="40">
				          	</button>
				        </form>
				        
					<% } %>
					
		      	</div>
		    </div>
		
		    <div class="row">
		    	<div class="col-12">
		        <%
		        	if (emptyList) {
		
						String clubParam = request.getParameter("clubId");
						boolean hasFilter = (clubParam != null && !clubParam.trim().isEmpty());

						String clubName = null;
                
						if (hasFilter) {

							if ("free".equals(clubParam)) {
							
								clubName = "Agente Libre";
							
							} else {
							
								try {
								
									int filterId = Integer.parseInt(clubParam);
									LinkedList<Club> clubsList = (LinkedList<Club>) request.getAttribute("clubsList");

									if (clubsList != null) {
									
										for (Club c : clubsList) {
										
											if (c.getId() == filterId) {
											
												clubName = c.getName();
												break;
											
											}
										
										}
									
									}
								
								} catch (NumberFormatException nfe) {}
							
							}

						}

						String title;
						String subtitle;

						if (hasFilter) {
						    
							if ("free".equals(clubParam)) {
						        
								title = "No hay agentes libres";
						        subtitle = "Todos los jugadores registrados pertenecen a un club actualmente.";
						    
							} else {
						        
								title = String.format("El club \"%s\" no tiene plantel asignado", clubName);
						        subtitle = "Podés registrar un nuevo fichaje desde la sección "
						                 + "<strong>Contratos</strong>.";
						    }

						} else {
						    
							title = "Todavía no agregaste jugadores";

						    subtitle = "No hay jugadores registrados. Usá el botón de <strong>(+)</strong> cuando quieras agregar el primero.";
						             
						}
		        %>
		
		        <div class="d-flex justify-content-center align-items-center" style="min-height:60vh;">
		        	<div class="empty-state text-center text-white px-4 py-5 mx-auto">
		            	<div class="mx-auto mb-3" style="width:72px;height:72px;">
		              		<svg viewBox="0 0 24 24" width="72" height="72" fill="none" aria-hidden="true">
		                		<path d="M3 7.5a2 2 0 0 1 2-2h5l2 2h7a2 2 0 0 1 2 2V17a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V7.5Z"
		                      		stroke="white" stroke-opacity=".9" stroke-width="1.5"/>
		                		<path d="M12 12h6M15 9v6" stroke="white" stroke-width="1.5" stroke-linecap="round"/>
		              		</svg>
		            	</div>
		
		            	<h3 class="fw-bold mb-2"><%= title %></h3>
		            	<p class="mb-0" style="opacity:.85;"><%= subtitle %></p>
		          	</div>
		        </div>
		
		        <%
		        	} else {
		        %>
		
				        <div class="table-responsive rounded-3 border overflow-hidden mb-5 mt-2">
				        	<table class="table table-dark mb-0">
				            	<thead>
				              		<tr>
				              			<th>#</th>
				                		<th><i class="fas fa-camera"></i></th>
				                		<th><i class="fas fa-shield-alt"></i></th>
	                    		    	<th>Jugador</th>
	                        			<th>Fecha de Nacimiento</th>
	                        			<th>Pie Dominante</th>
	                        			<th>Altura</th>
	                        			<th>Peso</th>
	                        			<th>Ver Trayectoria</th>
	                        			
	                        			<% if (isAdmin) { %>
		                        			<th>Editar</th>
		                        			<th>Eliminar</th>
	                        			<% } %>
	                        			
					              	</tr>
					            </thead>
					           	<tbody>
					            	<% for (Player p : pll) { %>
						            	<tr>
						            		<td class="jersey-number-cell">
											    <% if (p.getJerseyNumber() > 0) { %>
											        <div class="tm-badge">
											            <%= p.getJerseyNumber() %>
											        </div>
											    <% } else { %>
											        <div class="tm-badge tm-badge-empty" title="Sin dorsal asignado">
											            <i class="fas fa-tshirt" style="font-size: 0.8em;"></i>
											        </div>
											    <% } %>
											</td>
							                <td class="profile-pic-cell">
											    <% if (p.getPhoto() != null && !p.getPhoto().isEmpty()) { %>
											        <img src="<%=request.getContextPath() + "/images?id=" + p.getPhoto()%>" 
											             class="profile-pic" 
											             alt="">
											    <% } else { %>
											        <div class="profile-pic d-flex justify-content-center align-items-center" style="font-size: 2.2rem; color: #2C3034;">
											            <i class="fas fa-solid fa-user"></i>
											        </div>
											    <% } %>
											</td>
		                    				<td>
											    <% 
											       Club currentClub = (currentClubsMap != null) ? currentClubsMap.get(p.getId()) : null;
											       
											       if (currentClub != null && currentClub.getBadgeImage() != null) { 
											    %>
											        <img src="<%=request.getContextPath() + "/images?id=" + currentClub.getBadgeImage()%>" 
											             title="<%= currentClub.getName() %>" 
											             class="club-badge" 
											             alt="">
											    <% } else { %>
											        <div class="free-agent-badge" title="Agente Libre (Sin Club)">
											            <i class="fas fa-user-slash" style="font-size: 0.7em;"></i>
											        </div>
											    <% } %>
											</td>
		                    				<td class="fullname-cell pl-3">
												<div class="d-flex flex-column align-items-center text-center">
													<div class="d-flex align-items-center justify-content-center">
														<span class="player-name"><%= p.getFullname() %></span>
														<% if (p.getNationality() != null && p.getNationality().getFlagImage() != null) { %>
															<img src="<%=request.getContextPath() + "/images?id=" + p.getNationality().getFlagImage()%>" 
																 title="<%=p.getNationality().getName()%>"
																 class="national-flag" alt="">
														<% } %>
													</div>

													<div class="player-position mt-1">
														<% 
														   String positionDescription = (positionsMap != null) ? positionsMap.get(p.getId()) : null;
                                               
														   if (positionDescription != null) { 
														%>
															<%= positionDescription %>
														<% } else { %>
															<span class="text-warning font-italic" style="font-size: 0.8em;">
																<i class="fas fa-exclamation-circle"></i> Posición sin asignar
															</span>
														<% } %>
													</div>
								                    
												</div>
								            </td>
		                    				<td>
											    <% if (p.getBirthdate() != null) { %>
											        <%= p.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
											        <% 
											           int age = Period.between(p.getBirthdate(), LocalDate.now()).getYears();
											        %>
											        <span class="age-text">(<%= age %>)</span>
											    <% } else { %>
											        -
											    <% } %>
											</td>
		                    				<td>
											    <% 
											       String footClass = "foot-unknown";
											       String footText = "Sin Determinar";
											       DominantFoot foot = p.getDominantFoot();
											       
											       if (foot != null) {
											           
											    	   footText = foot.getDisplayName();
											           
											           if (foot == DominantFoot.RIGHT) {
											           
											        	   footClass = "foot-right";
											           
											           } else if (foot == DominantFoot.LEFT) {
											           
											        	   footClass = "foot-left";
											           
											           } else if (foot == DominantFoot.AMBIDEXTROUS) {
											           
											        	   footClass = "foot-ambi";
											           
											           }
											       
											       }
											    %>
											    <span class="foot-badge <%= footClass %>"><%= footText %></span>
											</td>
		                    				<td>
											    <% if (p.getHeight() > 0) { %>
											        <span class="stat-value">
											            <%= String.format(java.util.Locale.US, "%.2f", p.getHeight()) %>
											        </span>
											        <span class="stat-unit">mts</span>
											    <% } else { %>
											        -
											    <% } %>
											</td>	
										    <td>
											    <% if (p.getWeight() > 0) { %>
											        <span class="stat-value">
											            <%= Math.round(p.getWeight()) %>
											        </span>
											        <span class="stat-unit">kg</span>
											    <% } else { %>
											        -
											    <% } %>
											</td>
										    <td>
											    <form method="get" action="actionplayer" class="d-flex justify-content-center align-items-center m-0">
											        <input type="hidden" name="action" value="history" />
											        <input type="hidden" name="id" value="<%= p.getId() %>" />
											        <button type="submit" class="btn btn-sm" style="background-color: #8f5300;">
											            <img src="${pageContext.request.contextPath}/assets/history.svg" alt="" width="25" height="25" style="display:block;">
											        </button>
											    </form>    
											</td>
							                
							                <% if (isAdmin) { %>
								                
								                <td>
								                  	<form method="get" action="actionplayer" class="d-flex justify-content-center align-items-center m-0">
									                    <input type="hidden" name="action" value="edit" />
									                    <input type="hidden" name="id" value="<%= p.getId() %>" />
									                    <button type="submit" class="btn btn-sm" style="background-color:#0D47A1;">
									                    	<img src="${pageContext.request.contextPath}/assets/edit.svg" alt="" width="25" height="25" style="display:block;">
									                    </button>
								                  	</form>
							                	</td>
							                	
							                	<td>
								                  	<form method="post" action="actionplayer" class="d-flex justify-content-center align-items-center m-0">
								                    	<input type="hidden" name="action" value="delete" />
								                    	<input type="hidden" name="id" value="<%= p.getId() %>" />
								                    	<button type="button" class="btn btn-sm btn-open-modal" data-action="delete" data-id="<%= p.getId() %>" style="background-color:#9B1C1C;">
								                      		<img src="${pageContext.request.contextPath}/assets/delete.svg" alt="" width="25" height="25" style="display:block;">
								                    	</button>
								                  	</form>
							                	</td>
							                
							                <% } %>
						              	
						              	</tr>
					            	
					            	<% } %>
				            	
				            	</tbody>
				          	</table>
				        </div>
		        <%
		        	}
		        %>
		      </div>
		 </div>
	</div>

  <div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
      	  <div class="modal-content text-dark">
              <div class="modal-header">
                  <h5 class="modal-title" id="confirmModalLabel">Confirmar acción</h5>
                  <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
              </div>
              <div class="modal-body" id="confirmModalBody">¿Estás seguro que querés continuar?</div>
              <div class="modal-footer">
                  <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                  <button type="button" class="btn btn-danger" id="confirmModalYes">Aceptar</button>
              </div>
          </div>
      </div>
  </div>

  <script>
  	  document.addEventListener("DOMContentLoaded", function() {
          const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
          const modalBody = document.getElementById('confirmModalBody');
          const confirmBtn = document.getElementById('confirmModalYes');
          let currentForm = null;

          document.querySelectorAll('.btn-open-modal').forEach(btn => {
             btn.addEventListener('click', function() {
                currentForm = this.closest('form');
                modalBody.textContent = "¿Estás seguro que querés eliminar este jugador?";
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