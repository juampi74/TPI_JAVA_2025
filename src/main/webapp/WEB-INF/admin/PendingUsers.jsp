<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.User"%>
<%@ page import="entities.Person"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
    <title>Solicitudes de Registro</title>
    
    <link href="/style/bootstrap.css" rel="stylesheet">
    
    <link href="/style/signin.css" rel="stylesheet">
    
    <style>
	    
	    .table {
    		text-align: center;
    	}
    	
    	table th, table td {
    		vertical-align: middle !important;
    	}
    
    </style>
    
    <%
		LinkedList<User> pul = (LinkedList<User>) request.getAttribute("pendingUsersList");
		boolean emptyList = (pul == null || pul.isEmpty());
	%>
</head>
<body style="background-color: #10442E;">
    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
    <div class="container" style="color: white;">
	    <div class="row">
	    	<div class="d-flex justify-content-between my-4 align-items-center">
		        <h2 class="mb-4">Solicitudes de Registro</h2>
		
		        <% 
		            String flash = (String) request.getAttribute("flash");
		            String cssClass = (String) request.getAttribute("cssClass");
		            if (flash != null) { 
		        %>
		            <div id="flash-message" class="alert <%= cssClass != null ? cssClass : "alert-info" %> alert-dismissible fade show" role="alert">
		                <%= flash %>
		                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
		            </div>
		        <% } %>
		    </div>
		    
		    <% if (emptyList) { %>
				<div class="d-flex justify-content-center align-items-center" style="min-height: 60vh;">
					<div class="col-12">
				  		<div class="empty-state text-center py-5 px-4 my-2 text-white">
					      	<i class="fas fa-search-minus fa-3x mb-3 text-white-50"></i>
						    <h3 class="fw-bold mb-2">No hay solicitudes pendientes</h3>
					    	<p class="mb-0" style="opacity:.85;">
					        	En este momento no hay usuarios a la espera de aprobación de su solicitud de registro.
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
			                        <th>DNI</th>
			                        <th>Nombre Completo</th>
			                        <th>Email</th>
			                        <th>Rol Solicitado</th>
			                        <th class="text-center">Acciones</th>
			                    </tr>
			                </thead>
			                <tbody>
			                    <%
		                            for (User u : pul) {
		                                
		                            	Person p = u.getPerson();
			                    %>
					                    <tr>
					                    	<td></td>
					                        <td><%= p.getId() %></td>
					                        <td><%= p.getFullname() %></td>
					                        <td><%= u.getEmail() %></td>
					                        <td>
					                            <span class="badge bg-secondary"><%= u.getRole().getDisplayName() %></span>
					                        </td>
					                        <td class="text-center">
    
											    <form action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;">
											        <input type="hidden" name="action" value="approve">
											        <input type="hidden" name="userId" value="<%= u.getId() %>">
											        <button type="submit" class="btn btn-success btn-sm fw-bold me-1">
											            <i class="fas fa-check"></i> Aprobar
											        </button>
											    </form>
											    									
											    <form action="${pageContext.request.contextPath}/admin/users" method="post" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés rechazar esta solicitud? Se eliminarán los datos del usuario permanentemente');">
											        <input type="hidden" name="action" value="reject">
											        <input type="hidden" name="userId" value="<%= u.getId() %>">
											        
											        <button type="button" class="btn btn-danger btn-sm btn-open-modal fw-bold" data-action="reject" data-id="<%= u.getId() %>">
											            <i class="fas fa-times"></i> Rechazar
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
		
		            modalBody.textContent = "¿Estás seguro que querés rechazar esta solicitud? Se eliminarán los datos del usuario permanentemente";
		           
		
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
    
    <script>
        setTimeout(function() {
            var alertElement = document.getElementById('flash-message');
            if (alertElement) {
                var alert = new bootstrap.Alert(alertElement);
                alert.close();
            }
        }, 2500);
    </script>
</body>
</html>