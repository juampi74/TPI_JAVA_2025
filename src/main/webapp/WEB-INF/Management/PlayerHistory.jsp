<%@ page import="entities.*" %>
<%@ page import="java.util.LinkedList" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <title>Trayectoria</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/start.css" rel="stylesheet">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	    
	    <style>
	    	body { background-color: #10442E; }
	    
	    	.custom-table-dark {
		      --bs-table-bg: rgba(0, 0, 0, 0.25);
		      --bs-table-color: #fff;
		      --bs-table-hover-bg: rgba(255, 255, 255, 0.05);
		      --bs-table-hover-color: #fff;
		      --bs-table-border-color: rgba(255, 255, 255, 0.1);
		    }
		    
		    .table th {
		      font-size: 0.85rem;
		      text-transform: uppercase;
		      letter-spacing: 1px;
		      color: #adb5bd;
		      border-bottom: 2px solid rgba(255,255,255,0.1) !important;
		      padding-bottom: 15px;
		      background-color: rgba(0,0,0,0.4) !important;
		    }
		    
		    .table td {
		      vertical-align: middle !important;
		      padding: 15px 10px;
		      font-size: 0.95rem;
		      color: #e0e0e0;
		    }
		    
		    .glass-panel {
		    	background: rgba(0, 0, 0, 0.2);
		    	backdrop-filter: blur(10px);
		    	border: 1px solid rgba(255, 255, 255, 0.05);
		    	border-radius: 12px;
		    	padding: 20px;
		    	box-shadow: 0 4px 6px rgba(0,0,0,0.1);
		    }
	    
		    .profile-pic {
			  width: 90px;
			  height: 90px;
			  object-fit: contain; 
			  object-position: center; 
			  border-radius: 50%;
			  background-color: #e9ecef;
	   		  border: 3px solid #fff;
	   		  box-shadow: 0 4px 15px rgba(0,0,0,0.3);
			}
			
			.date-cell {
				font-family: 'Consolas', 'Monaco', monospace;
				letter-spacing: -0.5px;
			}
			
			.club-badge-small {
				width: 32px;
				height: 32px;
				object-fit: contain;
				filter: drop-shadow(0 2px 2px rgba(0,0,0,0.3));
			}
	    </style>
	</head>
	<body style="background-color: #10442E;">
	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	    
	    <%
	        Player p = (Player) request.getAttribute("player");
	        LinkedList<Contract> history = (LinkedList<Contract>) request.getAttribute("history");
	        
	        String fromParam = request.getParameter("from");
	        String toParam = request.getParameter("to");
	        boolean hasFilters = (fromParam != null && !fromParam.isEmpty()) || 
	                             (toParam != null && !toParam.isEmpty());
	        
	        boolean showFilters = (history != null && !history.isEmpty()) || hasFilters;
	    %>
	
	    <div class="container text-white mt-4 mb-5">
	        
	        <div class="d-flex align-items-center mb-4 glass-panel">
	            <img src="<%=request.getContextPath() + "/images?id=" + p.getPhoto()%>" class="profile-pic me-4" alt="">
	            <div>
	                <h2 class="m-0 fw-bold"><%= p.getFullname() %></h2>
	                <div class="d-flex align-items-center mt-1">
	                	<span class="text-white-50 me-3"><i class="fas fa-history me-1"></i> Historial de Transferencias</span>
	                </div>
	            </div>
	            
	            <a href="actionplayer" class="btn btn-outline-light btn-sm ms-auto px-3">
	                <i class="fas fa-arrow-left me-1"></i> Volver
	            </a>
	        </div>
	
	        <% if (showFilters) { %>
		        <div class="glass-panel mb-4 py-3">
			        <form action="actionplayer" method="get" class="row g-3 align-items-end">
			            <input type="hidden" name="action" value="history">
			            <input type="hidden" name="id" value="<%= p.getId() %>">
			            
			            <div class="col-auto">
			                <label class="form-label small text-uppercase text-white-50 mb-1">Desde</label>
			                <input type="date" name="from" class="form-control form-control-sm bg-dark text-white border-secondary" value="<%= request.getParameter("from") != null ? request.getParameter("from") : "" %>">
			            </div>
			            <div class="col-auto">
			                <label class="form-label small text-uppercase text-white-50 mb-1">Hasta</label>
			                <input type="date" name="to" class="form-control form-control-sm bg-dark text-white border-secondary" value="<%= request.getParameter("to") != null ? request.getParameter("to") : "" %>">
			            </div>
			            <div class="col-auto">
			                <button type="submit" class="btn btn-outline-light btn-sm px-3">Filtrar</button>
			                <a href="actionplayer?action=history&id=<%= p.getId() %>" class="btn btn-link btn-sm text-white-50 text-decoration-none">Limpiar</a>
			            </div>
			        </form>
		        </div>
	        <% } %>
	
	        <% if (history == null || history.isEmpty()) { %>
	        
	            <div class="alert alert-dark border-0 text-center py-5" style="background-color: rgba(0,0,0,0.3);">
	            	
	            	<i class="fas <%= hasFilters ? "fa-search-minus" : "fa-history" %> fa-3x mb-3 text-white-50"></i><br>
	            	
	            	<span class="text-white-50 d-block fs-5 fw-bold mb-2">
	            		<%= hasFilters ? "Sin resultados" : "Historial Vacío" %>
	            	</span>
	            	
	            	<span class="text-white-50 small">
	            		<%= hasFilters 
	            			? "No hay contratos que coincidan con el rango de fechas seleccionado." 
	            			: "Este jugador todavía no ha registrado contratos en ningún club." %>
	            	</span>
	            	
	            	<% if (hasFilters) { %>
		            	<div class="mt-3">
		            		<a href="actionplayer?action=history&id=<%= p.getId() %>" class="btn btn-sm btn-outline-light">
		            			Borrar filtros
		            		</a>
		            	</div>
	            	<% } %>
	            </div>
	            
	        <% } else { %>
	        
	            <div class="table-responsive rounded-3 overflow-hidden border border-secondary">
	                <table class="table table-dark table-hover mb-0 custom-table-dark">
	                    <thead>
	                        <tr class="text-center">
	                            <th class="text-start ps-4">Club</th>
	                            <th>Inicio</th>
	                            <th>Fin</th>
	                            <th>Estado</th>
	                        </tr>
	                    </thead>
	                    <tbody class="text-center">
	                        <% for (Contract c : history) { 
	                             boolean isActive = (c.getReleaseDate() == null && (c.getEndDate().isAfter(java.time.LocalDate.now()) || c.getEndDate().isEqual(java.time.LocalDate.now())));
	                        %>
	                        <tr>
	                            <td class="text-start ps-4">
	                                <div class="d-flex align-items-center">
	                                    <% if(c.getClub().getBadgeImage() != null) { %>
	                                        <img src="<%=request.getContextPath() + "/images?id=" + c.getClub().getBadgeImage()%>" class="club-badge-small me-3">
	                                    <% } %>
	                                    <span class="fw-bold" style="font-size: 1.05rem;"><%= c.getClub().getName() %></span>
	                                </div>
	                            </td>
	                            
	                            <td class="date-cell"><%= c.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></td>
	                            
	                            <td class="date-cell">
	                                <% if (c.getReleaseDate() != null) { %>
	                                    <div class="d-flex flex-column align-items-center">
	                                    	<span><%= c.getReleaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></span>
											<span class="badge rounded-pill bg-danger bg-opacity-25 text-white-50 border border-danger border-opacity-25 fw-bold" 
											      style="font-size: 0.65em; margin-top: 4px; letter-spacing: 0.25px;"> RESCINDIDO
											</span>
										</div>
	                                <% } else { %>
	                                    <%= c.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %>
	                                <% } %>
	                            </td>
	                            
	                            <td>
	                                <% if (isActive) { %>
	                                    <span class="badge rounded-pill bg-success bg-opacity-75 text-white border border-success border-opacity-25 fw-bold px-3"
	                                          style="font-size: 0.65em; margin-top: 4px; letter-spacing: 1px;"> VIGENTE
										</span>             
	                                <% } else { %>
	                                    <span class="badge rounded-pill bg-secondary bg-opacity-75 text-white border border-secondary border-opacity-25 fw-bold px-3"
	                                          style="font-size: 0.65em; margin-top: 4px; letter-spacing: 1px;"> FINALIZADO
	                                    </span>
	                                <% } %>
	                            </td>
	                        </tr>
	                        <% } %>
	                    </tbody>
	                </table>
	            </div>
	        <% } %>
	    </div>
	</body>
</html>