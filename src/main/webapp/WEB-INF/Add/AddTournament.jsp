<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Association"%>
<%@ page import="entities.Club"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    LinkedList<Association> associationsList = (LinkedList<Association>) request.getAttribute("associationsList");
    LinkedList<Club> clubsList = (LinkedList<Club>) request.getAttribute("clubsList");
%>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Torneo</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <style>
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	    </style>
	</head>

	<body style="background-color: #10442E;">
	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	
	    <div class="container" style="color: white;">
	        <h2 class="mt-4">Agregar Torneo</h2>
	
	        <form action="actiontournament" method="post" class="mt-4">
	            <input type="hidden" name="action" value="add" />
	            <div class="form-group">
	                <label for="name">Nombre:</label>
	                <input type="text" class="form-control" id="name" name="name" required />
	            </div>
	            <div class="form-group">
	                <label for="startDate">Fecha de Inicio:</label>
	                <input type="date" class="form-control" id="startDate" name="startDate" required />
	            </div>
	            <div class="form-group">
	                <label for="format">Formato:</label>
	                <select name="format" id="format" class="form-control" required>
	                    <option value="">-- Seleccioná un formato --</option>
	                    <option value="0">Dividir los equipos en dos zonas + eliminación</option>
	                    <option value="1">Todos contra todos (solo ida)</option>
	                    <option value="2">Todos contra todos (ida y vuelta)</option>
	                    <option value="3">Formato mundial</option>
	                </select>
	            </div>
	            <div class="form-group">
	                <label for="season">Edición:</label>
	                <input type="text" class="form-control" id="season" name="season" required />
	            </div>
	            <div class="form-group">
	                <label for="id_association">Asociación:</label>
	                <select name="id_association" id="id_association" class="form-control" required>
	                    <option value="">-- Seleccioná una asociación --</option>
	                    <%
	                        if (associationsList != null && !associationsList.isEmpty()) {
	                            
	                        	for (Association a : associationsList) {
	                    %>
	                        		<option value="<%= a.getId() %>"><%= a.getName() %></option>
	                    <%
	                            }
	                        	
	                        } else {
	                    %>
	                        	<option value="" disabled>No hay asociaciones cargadas</option>
	                    <%
	                        }
	                    %>
	                </select>
	            </div>
	            <div class="form-group mt-4">
				    <label class="text-white fs-5">Seleccioná los clubes participantes:</label>
				
				    <div class="mt-2 mb-2">
				        <button type="button" class="btn btn-sm text-white" 
				                style="background-color:#1A6B32;" 
				                onclick="toggleAllClubs()">
				            Seleccionar todos
				        </button>
				    </div>
				
				    <div class="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-3 mt-2">
				
				        <%
				            if (clubsList != null && !clubsList.isEmpty()) {
				                
				            	for (Club c : clubsList) {
				        %>
				
							        <div class="col">
							            <div class="p-2 rounded d-flex align-items-center gap-2"
							                 style="background-color:white; color:black; border:2px solid #10442E;
							                        transition:0.2s;">
							                
							                <input type="checkbox" name="selectedClubs" value="<%= c.getId() %>" class="club-checkbox" onchange="updateCount()" style="margin:0; width:18px; height:18px; position:static; flex-shrink:0;">
				
							                <img src="<%=request.getContextPath() + "/images?id=" + c.getBadgeImage()%>" 
							                     alt="" 
							                     width="30"
							                     height="30"
							                     style="object-fit:contain;">
							
							                <label class="m-0" style="font-size:0.9rem;">
							                    <%= c.getName() %>
							                </label>
							            </div>
							        </div>
				
				        <%
				                }
				            	
				            } else {
				        %>
				
				        		<p class="text-white">No hay clubes cargados.</p>
				
				        <%
				            }
				        %>
				    </div>
				
				    <p class="mt-2 text-white">
				        <strong>Clubes seleccionados:</strong> <span id="count">0</span>
				    </p>
				</div>
				
	            <div class="button-container mb-3">
	                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
	                <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
	            </div>
	
	        </form>
	    </div>
		<script>

		    function updateCount() {
		        
		        const total = document.querySelectorAll(".club-checkbox:checked").length;
		        document.getElementById("count").textContent = total;
		    
		    }
	
		    function toggleAllClubs() {
		        
		    	const checkboxes = document.querySelectorAll(".club-checkbox");
		        
		        const allChecked = Array.from(checkboxes).every(cb => cb.checked);
		        
		        checkboxes.forEach(cb => cb.checked = !allChecked);
		        
		        updateCount();
		        
		    }
		    
		    window.onload = updateCount;
			
		</script>
	</body>
</html>
