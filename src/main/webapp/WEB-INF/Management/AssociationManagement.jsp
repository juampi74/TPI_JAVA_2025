<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Association"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=edge">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <meta name="description" content="">
	    <meta name="author" content="">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <title>Asociaciones</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
	    
	    <style>
	    
	    	.table {
	    		text-align: center;	
	    	}
	    	
	    	table th,
			table td {
			  	vertical-align: middle !important;
			}
	    
	    </style>
		
		<%
			LinkedList<Association> al = (LinkedList<Association>) request.getAttribute("associationsList");
			boolean emptyList = (al == null || al.isEmpty());
		%>
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Asociaciones</h1>
		        	<form action="actionassociation" method="get" style="margin:0;">
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
						    <h3 class="fw-bold mb-2">Todavía no agregaste asociaciones</h3>
						    	<p class="mb-0" style="opacity:.85;">
						        	No hay asociaciones registradas. Usá el botón de <strong>(+)</strong> cuando quieras agregar la primera.
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
	                    		    	<th>Fecha de Creación</th>
	                    		    	<th>Editar</th>
	                        			<th>Eliminar</th>
	                      			</tr>
	                      		</thead>
	                    		<tbody>
	                    		<%
	                    	    	for (Association a : al) {
	                    		%>
	                    			<tr>
	                    				<td><%=a.getName()%></td>
	                    				<td><%=a.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
	                    				<td>
	                    					<form method="get" action="actionassociation" style="display:inline;" class="d-flex justify-content-center align-items-center">
	                    						<input type="hidden" name="action" value="edit" />
			        							<input type="hidden" name="id" value="<%=a.getId()%>" />
			        							<button type="submit" style="background-color: #0D47A1" class="btn btn-sm">
													<img src="${pageContext.request.contextPath}/assets/edit.svg" style="display: block;" alt="Agregar" width="25" height="25">
												</button>
			    							</form>
	                    				</td>
	                    				<td>
	                    					<form method="post" action="actionassociation" class="d-flex justify-content-center align-items-center" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar esta asociación?');">
												<input type="hidden" name="action" value="delete" />
												<input type="hidden" name="id" value="<%=a.getId()%>" />
												<button type="button" style="background-color: #9B1C1C" class="btn btn-sm btn-open-modal" data-action="delete" data-id="<%= a.getId() %>" >
													<img src="${pageContext.request.contextPath}/assets/delete.svg" style="display: block;" alt="Agregar" width="25" height="25">
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
		
			            currentForm = this.closest('form');
			
			            modalBody.textContent = "¿Estás seguro que querés eliminar esta asociación?";
			           
			
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
		</script>
	</body>
</html>