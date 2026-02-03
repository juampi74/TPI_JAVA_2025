<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.President"%>
<%@ page import="entities.User"%>
<%@ page import="enums.UserRole"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
		<title>Presidentes</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet">
		
		<style>
	    	body {
	    	  background-color: #10442E;
	    	}
	    
	    	.table {
	    	  text-align: center;
	    	}
	    	
	    	
	    	table th, table td {
			  vertical-align: middle !important;
			}
			
			.president-name {
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
			
			.profile-pic {
				width: 65px;
				height: 65px;
				object-fit: cover;
				object-position: top center;
				border-radius: 50%;
				border: 2px solid #444;
			}
			
			.policy-cell {
			    max-width: 300px;
			    margin: 0 auto;
			}
			
			.policy-text {
			    display: -webkit-box;
			    -webkit-line-clamp: 2;
			    -webkit-box-orient: vertical;
			    overflow: hidden;
			    text-overflow: ellipsis;
			    font-size: 0.9rem;
			    color: #DDD;
			    line-height: 1.4;
			}
			
			.profile-pic-cell {
			    min-width: 170px;
			    margin: 0 auto;
			}
	    </style>
		
		<%
			User userLogged = (User) session.getAttribute("user");
			boolean isAdmin = (userLogged != null && userLogged.getRole() == UserRole.ADMIN);
		
			LinkedList<President> prl = (LinkedList<President>) request.getAttribute("presidentsList");
			boolean emptyList = (prl == null || prl.isEmpty());
		%>
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container text-white">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Presidentes</h1>
		        	
		        	<% if (isAdmin) { %>
		        	
			        	<form action="actionpresident" method="get" style="margin:0;">
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
						    <h3 class="fw-bold mb-2">Todavía no agregaste presidentes</h3>
						    	<p class="mb-0" style="opacity:.85;">
						        	No hay presidentes registrados. Usá el botón de <strong>(+)</strong> cuando quieras agregar el primero.
						      	</p>
					    	</div>
					  	</div>
					</div>
				<% } else { %>
	            	<div class="col-12">
	                	<div class="table-responsive rounded-3 border overflow-hidden mb-5">
	                    	<table class="table table-dark mb-0">
	                    		<thead>
	                    			<tr class="text-center" style="background-color: rgba(0,0,0,0.2);">
	                    		    	<th><i class="fas fa-camera"></i></th>
	                    		    	<th>Presidente</th>
	                        			<th>Fecha Nacimiento</th>
	                        			<th>Política de Gestión</th>
	                        			
	                        			<% if (isAdmin) { %>
		                        			<th>Editar</th>
		                        			<th>Eliminar</th>
	                        			<% } %>

	                      			</tr>
	                      		</thead>
	                    		<tbody>
	                    		<% for (President pr : prl) { %>
	                    			<tr>                    				
	                    				<td class="profile-pic-cell">
	                    					<div class="d-flex justify-content-center align-items-center">
	                    						<img src="<%=request.getContextPath() + "/images?id=" + pr.getPhoto()%>" class="profile-pic" alt="">
	                    					</div>
	                    				</td>
	                    				
	                    				<td class="pl-3">
	                    					<div class="d-flex flex-column align-items-center text-center">
												<div class="d-flex align-items-center justify-content-center">
		                    						<span class="president-name"><%=pr.getFullname()%></span>
		                    						<% if (pr.getNationality() != null && pr.getNationality().getFlagImage() != null) { %>
							                			<img src="<%=request.getContextPath() + "/images?id=" + pr.getNationality().getFlagImage()%>" 
							                				 title="<%=pr.getNationality().getName()%>"
							                				 class="national-flag" alt="">
							                		<% } %>
	                    						</div>
	                    					</div>
	                    				</td>
	                    				
	                    				<td><%=pr.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
	                    				
	                    				<td class="policy-cell">
										    <div class="policy-text" title="<%=pr.getManagementPolicy()%>">
										        <%=pr.getManagementPolicy()%>
										    </div>
										</td>
										
										<% if (isAdmin) { %>
	                    				
		                    				<td>
		                    					<form method="get" action="actionpresident" class="d-flex justify-content-center m-0">
		                    						<input type="hidden" name="action" value="edit" />
				        							<input type="hidden" name="id" value="<%=pr.getId()%>" />
				        							<button type="submit" style="background-color: #0D47A1" class="btn btn-sm">
														<img src="${pageContext.request.contextPath}/assets/edit.svg" style="display: block;" alt="Editar" width="25" height="25">
													</button>
				    							</form>
		                    				</td>
		                    				
		                    				<td>
		                    					<form method="post" action="actionpresident" class="d-flex justify-content-center m-0">
													<input type="hidden" name="action" value="delete" />
													<input type="hidden" name="id" value="<%=pr.getId()%>" />
													<button type="button" style="background-color: #9B1C1C" class="btn btn-sm btn-open-modal" data-action="delete" data-id="<%= pr.getId() %>" >
														<img src="${pageContext.request.contextPath}/assets/delete.svg" style="display: block;" alt="Eliminar" width="25" height="25">
													</button>
												</form>
		                    				</td>
		                    				
		                    			<% } %>
		                    			
	                    			</tr>
	                    		<% } %>
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
		
		<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
		<script>
			document.addEventListener("DOMContentLoaded", function() {
			    const modal = new bootstrap.Modal(document.getElementById('confirmModal'));
			    const modalBody = document.getElementById('confirmModalBody');
			    const confirmBtn = document.getElementById('confirmModalYes');
			    let currentForm = null;
			
			    document.querySelectorAll('.btn-open-modal').forEach(button => {
			        button.addEventListener('click', function() {
			            currentForm = this.closest('form');
			            modalBody.textContent = "¿Estás seguro que querés eliminar este presidente?";
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