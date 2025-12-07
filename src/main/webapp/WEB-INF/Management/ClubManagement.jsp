<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Club"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
	LinkedList<Club> cl = (LinkedList<Club>) request.getAttribute("clubsList");
	boolean emptyList = (cl == null || cl.isEmpty());
	
    HashMap<Integer, Club> classicRivalsMap = (HashMap<Integer, Club>) request.getAttribute("classicRivalsMap");
    if (classicRivalsMap == null) classicRivalsMap = new HashMap<>();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <title>Clubes</title>
		
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	    
	    <style>
	    
	    	.table {
	    		text-align: center;
	    	}
	    	
	    	table th, table td {
	    		vertical-align: middle !important;
	    	}
			
			.badge-cell {
				min-width: 100px;
				margin: 0 auto;
			}
			
			.classic-rival-badge {
			    width: 32px; 
			    height: 32px;
			    margin-right: 6px;
			    object-fit: contain; 
			    filter: drop-shadow(0 2px 2px rgba(0,0,0,0.4));
			    transition: transform 0.2s;
			}
			
			.classic-rival-badge:hover {
				transform: scale(1.2);
			}
			
	    </style>
	    
	    <script>
	    	const allClubsData = [
	        <% 
	           if (cl != null) {
	               for (Club c : cl) { 
	                   int nationalityId = (c.getNationality() != null) ? c.getNationality().getId() : 0;
	        %>
	            {
	                id: <%= c.getId() %>,
	                name: "<%= c.getName() %>",
	                nationalityId: <%= nationalityId %>, 
	                isOccupied: <%= classicRivalsMap.containsKey(c.getId()) %>
	            },
	        <% 
	               } 
	           }
	        %>
	        ];
	    </script>
	</head>
	
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Clubes</h1>
		        	<form action="actionclub" method="get" style="margin:0;">
		        		<input type="hidden" name="action" value="add" />
					    <button type="submit" class="btn btn-dark btn-circular" style="border:none; background:none; padding:0;">
					        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" style="display: block;" alt="Agregar" width="40" height="40">
					    </button>
		    		</form>				
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
						    <h3 class="fw-bold mb-2">Todavía no agregaste clubes</h3>
						    	<p class="mb-0" style="opacity:.85;">
						        	No hay clubes registrados. Usá el botón de <strong>(+)</strong> cuando quieras agregar el primero.
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
	                    				<th></th>
							            <th>Nombre</th>
							            <th>Fundación</th>
							            <th>Presupuesto</th>
							            <th>Estadio</th>
							            <th>Editar</th>
							            <th>Clásico Rival</th>
	                       				<th>Eliminar</th>
	                      			</tr>
	                      		</thead>
	                    		<tbody>
	                    		<%
	                    	    	for (Club c : cl) {
	                    		%>
	                    			<tr>
	                    				<td class="badge-cell">
	                    					<img alt="" src="<%=request.getContextPath() + "/images?id=" + c.getBadgeImage()%>" height="40">
	                    				</td>
	                    				<td><b><%=c.getName()%></b></td>
	                    				<td><%=c.getFoundationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
	                    				<td><%=c.getBudget()%></td>
	                    				<td><%=c.getStadium().getName()%></td>
	                    				
	                    				<td>
	                    					<form method="get" action="actionclub" style="display:inline;" class="d-flex justify-content-center align-items-center">
	                    						<input type="hidden" name="action" value="edit" />
			        							<input type="hidden" name="id" value="<%=c.getId()%>" />
			        							<button type="submit" style="background-color: #0D47A1" class="btn btn-sm">
													<img src="${pageContext.request.contextPath}/assets/edit.svg" style="display: block;" alt="Editar" width="25" height="25">
												</button>
			    							</form>
	                    				</td>
	                    				
	                    				<td>
						                    <% 
						                       Club classicRival = classicRivalsMap.get(c.getId());
						                       if (classicRival == null) { 
						                    %>
						                        <button type="button" class="btn btn-sm btn-warning text-dark fw-bold shadow-sm"
						                                data-bs-toggle="modal" data-bs-target="#classicRivalModal"
						                                onclick="openClassicRivalModal(<%= c.getId() %>, '<%= c.getName() %>', <%= (c.getNationality() != null) ? c.getNationality().getId() : 0 %>)"
						                                title="Asignar Clásico Rival">
						                            <img src="${pageContext.request.contextPath}/assets/duel.svg" style="display: block;" alt="Asignar Clásico Rival" width="25" height="25">
						                        </button>
						                    <% } else { %>
						                        <div class="d-flex align-items-center justify-content-start">
						                            <img src="<%=request.getContextPath() + "/images?id=" + classicRival.getBadgeImage()%>" 
						                                 title="Clásico Rival: <%= classicRival.getName() %>"
						                                 class="classic-rival-badge" alt="Clásico Rival">
						                            
						                            <form method="post" action="actionclub" class="d-flex justify-content-center align-items-center" style="display:inline;">
											            <input type="hidden" name="action" value="removeClassicRival">
											            <input type="hidden" name="id" value="<%= c.getId() %>">
											            <button type="button"
											            		class="btn btn-sm btn-secondary shadow-sm btn-open-modal"
											            		data-action="remove-classic-rival"
											            		data-id="<%= c.getId() %>"
											            		data-name="<%= c.getName() %>"
											            		data-classic-rival-name="<%= classicRival.getName() %>"
											            		title="Desvincular Rivalidad">
											                <img src="${pageContext.request.contextPath}/assets/unlink.svg" style="display: block;" alt="Remover Rivalidad" width="25" height="25">
											            </button>
											        </form>
						                        </div>
						                    <% } %>
						                </td>
	                    				
	                    				<td>
	                    					<form method="post" action="actionclub" class="d-flex justify-content-center align-items-center" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este club?');">
												<input type="hidden" name="action" value="delete" />
												<input type="hidden" name="id" value="<%=c.getId()%>" />
												<button type="button"
														style="background-color: #9B1C1C"
														class="btn btn-sm btn-open-modal"
														data-action="delete"
														data-id="<%= c.getId() %>" >
													<img src="${pageContext.request.contextPath}/assets/delete.svg" style="display: block;" alt="Eliminar" width="25" height="25">
												</button>
											</form>
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

		<div class="modal fade" id="confirmModal" tabindex="-1" aria-labelledby="confirmModalLabel" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered">
		    <div class="modal-content text-dark">
		      <div class="modal-header">
		        <h5 class="modal-title" id="confirmModalLabel">Confirmar acción</h5>
		        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Cerrar"></button>
		      </div>
		      <div class="modal-body" id="confirmModalBody">
		        ¿Estás seguro que querés continuar?
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
		        <button type="button" class="btn btn-danger" id="confirmModalYes">Aceptar</button>
		      </div>
		    </div>
		  </div>
		</div>

		<div class="modal fade" id="classicRivalModal" tabindex="-1" aria-hidden="true">
	        <div class="modal-dialog modal-dialog-centered">
	            <form action="actionclub" method="post" class="modal-content bg-dark text-white border border-secondary shadow-lg">
	                <input type="hidden" name="action" value="setClassicRival">
	                <input type="hidden" name="idClub1" id="modalClubId">
	                
	                <div class="modal-header border-secondary">
	                    <h5 class="modal-title">
	                        <i class="fas fa-bolt text-warning me-2"></i>
	                        Asignar Clásico a <span id="modalClubName" class="text-warning fw-bold"></span>
	                    </h5>
	                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
	                </div>
	                
	                <div class="modal-body">
	                    <div class="form-group">
	                        <label class="mb-2 fw-bold">Seleccioná el rival:</label>
	                        <select name="idClub2" id="classicRivalSelect" class="form-select bg-secondary text-white border-0" required>
	                            <option value="">-- Buscando clubes compatibles... --</option>
	                        </select>
	                        
	                        <div class="alert alert-info bg-opacity-10 border-0 text-dark mt-3 mb-0 py-2" style="font-size: 0.9rem;">
	                            <i class="fas fa-info-circle me-2"></i> 
	                            Solo se muestran clubes del <b>mismo país</b> que aún <b>no tienen</b> clásico asignado.
	                        </div>
	                    </div>
	                </div>
	                
	                <div class="modal-footer border-secondary">
	                    <button type="button" class="btn btn-outline-light btn-sm" data-bs-dismiss="modal">Cancelar</button>
	                    <button type="submit" class="btn btn-warning btn-sm fw-bold" id="btnSaveClassicRival">
	                        Guardar Rivalidad
	                    </button>
	                </div>
	            </form>
	        </div>
	    </div>

		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
		
		<script>
			
			document.addEventListener("DOMContentLoaded", function() {
		
				const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
			    const modalBody = document.getElementById('confirmModalBody');
			    const confirmBtn = document.getElementById('confirmModalYes');
			    
			    let currentForm = null;
			    let actionType = "";
			
			    document.querySelectorAll('.btn-open-modal').forEach(button => {
			        
			    	button.addEventListener('click', function() {
			            
			    		actionType = this.getAttribute('data-action');
			            const id = this.getAttribute('data-id');
			            const name = this.getAttribute('data-name');
			            const classicRivalName = this.getAttribute('data-classic-rival-name');
			            currentForm = this.closest('form');
			            
			            if (actionType === "delete") {
			            
			            	modalBody.textContent = "¿Estás seguro que querés eliminar este club?";
			            
			            } else {
			            
			            	modalBody.innerHTML = "¿Estás seguro que querés desvincular la rivalidad entre <b>" + name + "</b> y <b>" + classicRivalName + "</b>?";
			            
			            }
			            
			            modal.show();
			        
			    	});
			    
			    });
				
			    confirmBtn.addEventListener('click', function() {
			    
			    	if (currentForm) {
			        
			    		currentForm.submit();
			        
			    	}
			        
			    	modal.hide();
			    
			    });
			
			});

	        function openClassicRivalModal(id, name, nationalityId) {

	            document.getElementById("modalClubId").value = id;
	            document.getElementById("modalClubName").textContent = name;
	            
	            const select = document.getElementById("classicRivalSelect");
	            const btnSave = document.getElementById("btnSaveClassicRival");
	            
	            select.innerHTML = '<option value="">-- Seleccioná un rival --</option>';
	            
	            select.disabled = false;
	            btnSave.disabled = false;
	            
	            const candidates = allClubsData.filter(c => {
	                
	            	return c.id !== id && 
	                       c.nationalityId === nationalityId && 
	                       !c.isOccupied;
	            
	            });
	            
	            if (candidates.length > 0) {
	            
	            	candidates.forEach(c => {
	                
	            		const option = document.createElement("option");
	                    option.value = c.id;
	                    option.textContent = c.name;
	                    select.appendChild(option);
	                
	            	});
	            
	            } else {
	            
	            	select.innerHTML = ''; 
	                const option = document.createElement("option");
	                option.disabled = true;
	                option.textContent = "No hay rivales disponibles en este país";
	                select.appendChild(option);
	                
	                select.disabled = true;
	                btnSave.disabled = true;
	            
	            }
	        
	        }
	        
		</script>

	</body>
</html>