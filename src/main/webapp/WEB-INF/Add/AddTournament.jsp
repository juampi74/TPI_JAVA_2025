<%@ page import="java.util.LinkedList"%>
<%@ page import="java.util.HashMap" %>
<%@ page import="entities.*"%>
<%@ page import="enums.TournamentFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    LinkedList<Association> associationsList = (LinkedList<Association>) request.getAttribute("associationsList");
	HashMap<Integer, LinkedList<Club>> clubsMap = (HashMap<Integer, LinkedList<Club>>) request.getAttribute("clubsMap");
%>

<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Agregar Torneo</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
	    
	    <style>
	        
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	        
	        .club-static {
	        	opacity: 0.8;
	        	background-color: #e9ecef !important;
	        	border: 1px solid #adb5bd !important;
	        	cursor: default !important;
	        }
	        
			#loadingOverlay {
			    position: fixed;
			    top: 0;
			    left: 0;
			    width: 100%;
			    height: 100%;
			    background-color: rgba(0, 0, 0, 0.85);
			    z-index: 9999;
			    display: none;
			    flex-direction: column;
			    justify-content: center;
			    align-items: center;
			    backdrop-filter: blur(5px);
			}
					
			@keyframes spin-ball {
			    0% { transform: rotate(0deg); }
			    100% { transform: rotate(360deg); }
			}
			
			.soccer-spinner {
			    font-size: 4rem;
			    color: #ffffff;
			    animation: spin-ball 1.5s linear infinite; 
			    filter: drop-shadow(0 0 10px rgba(255, 255, 255, 0.5));
			}
	        
		</style>
	</head>

	<body style="background-color: #10442E;">
	    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
	
	    <div class="container" style="color: white;">
	        <h2 class="mt-4">Agregar Torneo</h2>
	
	        <form action="actiontournament" method="post" class="mt-4" onsubmit="return validateClubs()">
	            <input type="hidden" name="action" value="add" />
	            <div class="form-group">
	                <label for="name">Nombre:</label>
	                <input type="text" class="form-control" id="name" name="name" required />
	            </div>
	            <div class="form-group">
				    <label for="startDate">Fecha de Inicio (Aproximada):</label>
				    <input type="date" class="form-control" id="startDate" name="startDate" required />
				    <small class="form-text text-white-50 d-flex justify-content-start gap-2 mt-1">
				        <span><i class="fas fa-info-circle"></i></span>
				        <span>Se ajustará automáticamente al <b>viernes</b> de la semana de la fecha seleccionada. Si ese viernes ya pasó, se moverá al de la semana siguiente.</span>
				    </small>
				</div>
	            <div class="form-group">
	                <label for="format">Formato:</label>
	                <select name="format" id="format" class="form-control" required>
	                    <option value="">-- Seleccioná un formato --</option>
	                    <% for (TournamentFormat format : TournamentFormat.values()) { %>
				            <option value="<%= format.name() %>">
				                <%= format.getDescription() %>
				            </option>
				        <% } %>
	                </select>
	            </div>
	            <div class="form-group">
	                <label for="season">Edición:</label>
	                <input type="text" class="form-control" id="season" name="season" required />
	            </div>
	           <div class="form-group">
			        <label for="id_association">Asociación:</label>
			        <select name="id_association" id="id_association" class="form-control" required onchange="showClubs(this.value)">
			            <option value="">-- Seleccioná una asociación --</option>
			            <% for (Association a : associationsList) { %>
			                <option value="<%= a.getId() %>"><%= a.getName() %></option>
			            <% } %>
			        </select>
			    </div>
			    
			    <div class="mt-4">
				    <% for (Association a : associationsList) { 
				         LinkedList<Club> clubs = clubsMap.get(a.getId());
				         boolean hasClubs = (clubs != null && !clubs.isEmpty());
				         boolean isInsufficient = (hasClubs && clubs.size() < 2);
				    %>
				        <div id="list_<%= a.getId() %>" class="clubs-list" style="display: none;">
				            
				            <% if (hasClubs) { %>
				                
				                <% if (!isInsufficient) { %>
				                	<label class="text-white fs-5">Seleccioná los clubes participantes:</label>
					                
					                <div class="my-2">
						                <button type="button" 
										        id="btnToggle_<%= a.getId() %>" 
										        class="btn btn-sm text-white" 
										        style="background-color:#1A6B32;" 
										        onclick="toggleAllClubs()">
										    <i class="fas fa-check-square me-1"></i> Seleccionar todos
										</button>
									</div>
								<% } else { %>
								    <div class="alert alert-warning d-flex align-items-center mt-2 py-2" role="alert">
								        <i class="fas fa-exclamation-circle fs-5 me-3 flex-shrink-0"></i>
								        <div>
								            Esta asociación solo tiene <strong>1</strong> club registrado. 
								            Necesitás al menos <strong>2</strong> para crear un torneo.
								        </div>
								    </div>
								    
								    <label class="text-white-50 fs-6 mb-1 mt-2">Único integrante:</label> 
								<% } %>
								
								<div class="row row-cols-2 row-cols-md-3 row-cols-lg-4 g-3 mt-1 mb-3">
				                    <% for (Club c : clubs) { %>
				                        <div class="col">
				                        	<% if (!isInsufficient) { %>
				                        		<label class="p-2 rounded d-flex align-items-center gap-2 bg-white text-dark border border-success w-100 h-100"
					                                   style="cursor: pointer;">
					                                <input type="checkbox" name="selectedClubs" value="<%= c.getId() %>" class="club-checkbox" onchange="updateCount()">
					                                <img src="<%=request.getContextPath() + "/images?id=" + c.getBadgeImage()%>" width="30" height="30" style="object-fit:contain;">
					                                <small class="m-0 fw-bold"><%= c.getName() %></small>
					                            </label>
				                            <% } else { %>
				                            	<div class="p-2 rounded d-flex align-items-center gap-2 club-static w-100 h-100 text-dark">
					                                <img src="<%=request.getContextPath() + "/images?id=" + c.getBadgeImage()%>" width="30" height="30" style="object-fit:contain; opacity: 0.75;">
					                                <small class="m-0 fw-bold"><%= c.getName() %></small>
					                            </div>
				                            <% } %>
				                        </div>
				                    <% } %>
				                </div>
				
								<% if (!isInsufficient) { %>
									<p class="mt-2 text-white">
					                    <strong>Clubes seleccionados:</strong> <span class="count-display">0</span>
					                    <span class="error-msg text-warning ms-3" style="display:none; font-size: 0.9em;">
					                        <i class="fas fa-exclamation-triangle me-1"></i> Debés seleccionar al menos 2 equipos.
					                    </span>
					                </p>
				                <% } %>
				                
				            <% } else { %>
				            	<p class="text-warning mt-3 p-3 border border-warning rounded" style="background-color: rgba(255, 193, 7, 0.1);">
				                    <i class="fas fa-exclamation-circle me-1"></i> No hay clubes registrados para <b><%= a.getName() %></b>.
				                </p>
				            <% } %>
				            
				        </div>
				    <% } %>
				</div>
				
	            <div class="button-container my-4">
	                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
	                <button type="submit" class="btn text-white" style="background-color: #0D47A1">Agregar</button>
	            </div>
	
	        </form>
	    </div>
	    
	    <div id="loadingOverlay">
		    <i class="fas fa-futbol soccer-spinner mb-4"></i>
		    
		    <h3 class="text-white fw-bold">Creando Torneo...</h3>
		    <p class="text-white-50">Generando el fixture, esto puede demorar unos segundos</p>
		</div>
		
        <script>
		    
        	function validateClubs() {
		    
        		var associationId = document.getElementById("id_association").value;
		        if (!associationId) return true; 
		
		        var container = document.getElementById('list_' + associationId);
		        
		        if (container) {

		        	var checkboxes = container.querySelectorAll(".club-checkbox");
		        	
		        	if (checkboxes.length < 2) return false;

		        	const totalMarked = container.querySelectorAll(".club-checkbox:checked").length;
		            const errorSpan = container.querySelector(".error-msg");
		
		            if (totalMarked < 2) {

		            	if (errorSpan) {
		            	
		            		errorSpan.style.display = "inline";
		                    errorSpan.scrollIntoView({ behavior: 'smooth', block: 'center' });
		            	
		            	}
		                
		            	return false;
		            
		            }
		            
		            if (errorSpan) errorSpan.style.display = "none";
		        
		        }
		        
		        document.getElementById("loadingOverlay").style.display = "flex";
		        
		        return true;
		    
        	}
		
        	function updateCount() {
        	    
        		var associationId = document.getElementById("id_association").value;
        	    if (!associationId) return;

        	    var container = document.getElementById('list_' + associationId);
        	    
        	    if (container) {
        	        
        	        var checkboxes = container.querySelectorAll(".club-checkbox");
        	        
        	        if (checkboxes.length > 0) {
        	            
        	        	const total = container.querySelectorAll(".club-checkbox:checked").length;
        	            const totalCheckboxes = checkboxes.length;
        	            
        	            var counterSpan = container.querySelector(".count-display");
        	            var errorSpan = container.querySelector(".error-msg");

        	            if (counterSpan) counterSpan.textContent = total;

        	            if (total >= 2 && errorSpan) errorSpan.style.display = "none";

        	            var btn = document.getElementById('btnToggle_' + associationId);
        	            if (btn) {
        	                
        	            	const allAreChecked = (total > 0 && total === totalCheckboxes);
        	                
        	                if (allAreChecked) {
        	                
        	                	btn.innerHTML = '<i class="fas fa-minus-square me-1"></i> Quitar selección';
        	                    btn.style.backgroundColor = "#c0392b";
        	                
        	                } else {
        	                
        	                	btn.innerHTML = '<i class="fas fa-check-square me-1"></i> Seleccionar todos';
        	                    btn.style.backgroundColor = "#1A6B32";
        	                
        	                }
        	            
        	            }
        	        
        	        }
        	    
        	    }
        	
        	}
		
		    function toggleAllClubs() {
		    	
		    	var associationId = document.getElementById("id_association").value;
		        if (!associationId) return;

		        var container = document.getElementById('list_' + associationId);
		        
		        if (container) {
		        
		        	const checkboxes = container.querySelectorAll(".club-checkbox");
		        	if(checkboxes.length === 0) return;

		            const allChecked = Array.from(checkboxes).every(cb => cb.checked);
		            checkboxes.forEach(cb => cb.checked = !allChecked);
		            
		            updateCount();
		        
		        }
		    
		    }
		    
		    function showClubs(associationId) {
		    	
		    	var lists = document.querySelectorAll('.clubs-list');
		    	
		    	lists.forEach(function(div) {
		        
		    		div.style.display = 'none';
		        	
		        	var checkboxes = div.querySelectorAll('input[type="checkbox"]');
		            checkboxes.forEach(function(cb) { cb.checked = false; });
		            
		            var counterSpan = div.querySelector(".count-display");
		            if (counterSpan) counterSpan.textContent = "0";
		            
		            var errorSpan = div.querySelector(".error-msg");
		            if (errorSpan) errorSpan.style.display = "none";
		            
		            var btn = div.querySelector("button[id^='btnToggle_']");
		            if (btn) {
		            
		            	btn.innerHTML = '<i class="fas fa-check-square me-1"></i> Seleccionar todos';
		                btn.style.backgroundColor = "#1A6B32";
		            
		            }
		    	
		    	});
		
		        if (associationId) {
		        
		        	var chosenList = document.getElementById('list_' + associationId);
		            if (chosenList) chosenList.style.display = 'block';
		        
		        }
		    
		    }
		        
		    window.onload = updateCount;
		
		</script>
		
	</body>
</html>