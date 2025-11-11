<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.LocalDate"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Contract"%>
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
		
	    <link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Contract> cl = (LinkedList<Contract>) request.getAttribute("contractsList");
		%>
		
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center" >
	        		<h1>Contratos</h1>
		        	<form action="actioncontract" method="get" style="margin:0;" class="d-flex justify-content-center align-items-center">
		        		<input type="hidden" name="action" value="add" />
					    <button type="submit" class="btn btn-dark" style="border:none; background:none; padding:0;">
					        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" style="display: block;" alt="Agregar" width="40" height="40">
					    </button>
		    		</form>				
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table justify-content-between">
                    		<thead>
                    			<tr>
						            <th>Persona</th>
						            <th>Club</th>
						            <th>Fecha de Inicio</th>
						            <th>Fecha de Fin</th>
						            <th>Salario</th>
						            <th>Cláusula de Rescisión</th>
						            <th>Fecha de Rescisión</th>
						            <th>Rescindir</th>
                       				<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (Contract c : cl) {
                    		%>
                    			<tr class="justify-content-between">
                    				<td><%=c.getPerson().getFullname()%></td>
                    				<td><%=c.getClub().getName()%></td>
                    				<td><%=c.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=c.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=c.getSalary()%></td>
                    				<td><%=c.getReleaseClause()%></td>
                    				<td><%= c.getReleaseDate() != null ? c.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "-" %></td>
                    				<td>
                    					<%
	                    					String disabledAttribute = "";
	                    				    java.time.LocalDate today = java.time.LocalDate.now();
	                    				    java.time.LocalDate contractEndDate = c.getEndDate();
	                    				    java.time.LocalDate contractStartDate = c.getStartDate();
	                    				    
	                    				    if (today.isAfter(contractEndDate) || c.getReleaseDate() != null || today.isBefore(contractStartDate)) {
	                    				        disabledAttribute = "disabled";
	                    				    }
									    %>
                    				
									    <form method="post" action="actioncontract" class="d-flex justify-content-center align-items-center" 
									          onsubmit="return confirm('¿Estás seguro que querés rescindir este contrato?');">
									        
									        <input type="hidden" name="action" value="release" />
									        <input type="hidden" name="id" value="<%=c.getId()%>" />
									        
									        <button type="button" class="btn btn-sm btn-open-modal" style="background-color: #8f5300; border-color: none;" data-action="release" data-id="<%= c.getId() %>" <%= disabledAttribute %>>
												<img src="${pageContext.request.contextPath}/assets/judge.svg" style="display: block;" alt="Agregar" width="25" height="25">
											</button>
									        
									    </form>
									</td>
									<td>
									    <form method="post" action="actioncontract" style="display:inline;" class="d-flex justify-content-center align-items-center">
									        <input type="hidden" name="action" value="delete" />
									        <input type="hidden" name="id" value="<%=c.getId()%>" />
									        
									        <button type="button" class="btn btn-danger btn-sm btn-open-modal" data-action="delete" data-id="<%= c.getId() %>">
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
		
		            if (actionType === "delete") {
		                modalBody.textContent = "¿Estás seguro que querés eliminar este contrato?";
		            } else if (actionType === "release") {
		                modalBody.textContent = "¿Estás seguro que querés rescindir este contrato?";
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
		</script>
	</body>
</html>