<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Player"%>
<%@ page import="entities.Club"%>
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
    		body{ background-color:#10442E; }

		    .table{ text-align:center; }
		    .table th, .table td{ vertical-align:middle !important; }

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
		    .fancy-select:hover{ border-color:#fff !important; }
		
		    .empty-state{ max-width:840px; }
		    .text-balance{ text-wrap: balance; }
		    .empty-state h3{ font-size: clamp(1.25rem, 2vw + 1rem, 1.75rem); }
		    .empty-state p{ font-size:.95rem; line-height:1.5; }
		</style>

		<%
			LinkedList<Player> pll = (LinkedList<Player>) request.getAttribute("playersList");
		    boolean emptyList = (pll == null || pll.isEmpty());
		%>
	</head>
	<body style="background-color: #10442E;">
  		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container text-white">
			<div class="row">
		    	<div class="d-flex flex-wrap align-items-center justify-content-between gap-3 my-4">
		        	<h1 class="m-0">Jugadores</h1>
		        	<form method="get" action="actionplayer" class="d-flex align-items-center gap-3 ms-auto m-0">
		          		<label for="clubFilter" class="form-label m-0 fs-5">Filtrar por club:</label>
		          			<select name="clubId" id="clubFilter"
		                  		class="form-select form-select-sm w-auto fancy-select bg-dark text-white"
		                  		onchange="this.form.submit()">
		            			<option value="">Todos los clubes</option>
					            <%
					            	LinkedList<Club> clubs = (LinkedList<Club>) request.getAttribute("clubsList");
					              	
					            	String selectedClub = request.getParameter("clubId");
					              	
					            	int selectedId = -1;
					              	
					            	if (selectedClub != null && !selectedClub.isEmpty()) {
					                
					            		selectedId = Integer.parseInt(selectedClub);
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
		        	</form>
		
			        <form action="actionplayer" method="get" class="m-0 ms-5">
			        	<input type="hidden" name="action" value="add" />
			          	<button type="submit" class="btn btn-dark btn-circular" style="border:none;background:none;padding:0;">
			            	<img src="${pageContext.request.contextPath}/assets/add-button2.svg" alt="Add" width="40" height="40">
			          	</button>
			        </form>
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
		              	
		            	}
			
			            String title;
			            String subtitle;
			
			            if (hasFilter) {
			              
			            	title = (clubName != null)
			            		? "No hay jugadores con contrato activo en “" + clubName + "”"
			            		: "No hay jugadores con contrato activo para el club seleccionado";
			           
			            	subtitle = "Agregá uno con el botón (+) o creá un contrato en la sección "
			                         + "<strong>Contratos</strong>.";
			            
			            } else {
			            
			            	title = "Todavía no agregaste jugadores";

			                subtitle = "Agregá uno con el botón (+) o creá un contrato en la sección "
			                         + "<strong>Contratos</strong>.";
			            
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
		
		            	<h3 class="fw-semibold mb-2 lh-sm text-balance"><%= title %></h3>
		            	<p class="mb-0 opacity-75"><%= subtitle %></p>
		          	</div>
		        </div>
		
		        <%
		        	} else {
		        %>
		
				        <div class="table-responsive rounded-3 border overflow-hidden mb-5 mt-2">
				        	<table class="table table-dark mb-0">
				            	<thead>
				              		<tr>
				                		<th>DNI</th>
	                    		    	<th>Apellido y Nombre</th>
	                        			<th>Fecha Nacimiento</th>
	                        			<th>Dirección</th>
	                        			<th>Pie Dominante</th>
	                        			<th>Número de Camiseta</th>
	                        			<th>Altura (mts)</th>
	                        			<th>Peso (kg)</th>
	                        			<th>Editar</th>
	                        			<th>Eliminar</th>
					              	</tr>
					            </thead>
					           	<tbody>
					            <%
					            	for (Player pl : pll) {
					            %>
						            	<tr>
							                <td><%=pl.getId()%></td>
		                    				<td><%=pl.getFullname()%></td>
		                    				<td><%=pl.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
		                    				<td><%=pl.getAddress()%></td>
		                    				<td><%=pl.getDominantFoot()%></td>
		                    				<td><%=pl.getJerseyNumber()%></td>
		                    				<td><%=pl.getHeight()%></td>
		                    				<td><%=pl.getWeight()%></td>
							                <td>
							                  	<form method="get" action="actionplayer" class="d-flex justify-content-center align-items-center m-0">
								                    <input type="hidden" name="action" value="edit" />
								                    <input type="hidden" name="id" value="<%= pl.getId() %>" />
								                    <button type="submit" class="btn btn-sm" style="background-color:#0D47A1;">
								                    	<img src="${pageContext.request.contextPath}/assets/edit.svg" alt="Edit" width="25" height="25" style="display:block;">
								                    </button>
							                  	</form>
						                	</td>
						                	<td>
							                  	<form method="post" action="actionplayer" class="d-flex justify-content-center align-items-center m-0">
							                    	<input type="hidden" name="action" value="delete" />
							                    	<input type="hidden" name="id" value="<%= pl.getId() %>" />
							                    	<button type="button" class="btn btn-sm btn-open-modal" data-action="delete" data-id="<%= pl.getId() %>" style="background-color:#9B1C1C;">
							                      		<img src="${pageContext.request.contextPath}/assets/delete.svg" alt="Delete" width="25" height="25" style="display:block;">
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

  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
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