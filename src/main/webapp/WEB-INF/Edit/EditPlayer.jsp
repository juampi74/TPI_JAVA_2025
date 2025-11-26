<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.*"%>
<%@ page import="enums.DominantFoot"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	Player player = (Player) request.getAttribute("player");

	LinkedList<Nationality> nationalitiesList = (LinkedList<Nationality>) request.getAttribute("nationalitiesList");
	LinkedList<Position> positionsList = (LinkedList<Position>) request.getAttribute("positionsList");
	LinkedList<Integer> playerPositionsList = (LinkedList<Integer>) request.getAttribute("playerPositionsList");
	Integer primary = (Integer) request.getAttribute("playerPrimary");
%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Editar Jugador</title>
	    <link href="style/bootstrap.css" rel="stylesheet">
	    <style>
	        .button-container {
	            display: flex;
	            justify-content: space-between;
	            align-items: center;
	            margin-top: 20px;
	        }
	    </style>
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
		    <h2 class="mt-4">Editar Jugador</h2>
		    <form action="actionplayer" method="post" enctype="multipart/form-data" class="mt-4">
		    	<input type="hidden" name="action" value="edit" />
		        <input type="hidden" name="id" value="<%=player.getId()%>" />
		        
		        <div class="form-group">
		            <label for="fullname">Nombre y Apellido:</label>
		            <input type="text" class="form-control" id="fullname" name="fullname" value="<%=player.getFullname()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="birthdate">Fecha de Nacimiento:</label>
		            <input type="date" class="form-control" id="birthdate" name="birthdate" value="<%=player.getBirthdate()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="address">Dirección:</label>
		            <input type="text" class="form-control" id="address" name="address" value="<%=player.getAddress()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="dominantFoot">Pie Dominante:</label>
				    <select class="form-control" id="dominantFoot" name="dominantFoot" required>
				        <option value="">-- Seleccioná una opción --</option>
				        
				        <% for (DominantFoot df : DominantFoot.values()) { 
				            boolean isSelected = (player.getDominantFoot() == df);
				        %>
				            <option value="<%= df.name() %>" <%= isSelected ? "selected" : "" %>>
				                <%= df.getDisplayName() %>
				            </option>
				        <% } %>
				        
				    </select>
				</div>
		        
		        <div class="form-group">
		            <label for="jerseyNumber">Número de Camiseta:</label>
		            <input type="number" class="form-control" id="jerseyNumber" name="jerseyNumber" value="<%=player.getJerseyNumber()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="height">Altura (mts):</label>
		            <input type="number" class="form-control" id="height" name="height" step="0.01" value="<%=player.getHeight()%>" required />
		        </div>
		        
		        <div class="form-group">
		            <label for="weight">Peso (kg):</label>
		            <input type="number" class="form-control" id="weight" name="weight" step="0.01" value="<%=player.getWeight()%>" required />
		        </div>
		        
		        <div class="form-group">
				    <label for="id_nationality">Nacionalidad:</label>
				    <select name="id_nationality" id="id_nationality" class="form-control" required>
					    <option value="">-- Seleccioná una nacionalidad --</option>
					    <%
					        if (nationalitiesList != null && !nationalitiesList.isEmpty()) {
					            int selectedNationalityId = (player != null && player.getNationality() != null) ? player.getNationality().getId() : -1;
					            for (Nationality n : nationalitiesList) {
					                String selected = (n.getId() == selectedNationalityId) ? "selected" : "";
					                out.print("<option value='" + n.getId() + "' " + selected + ">" + n.getName() + "</option>");
					            }
					        } else {
					            out.print("<option value='' disabled>No hay nacionalidades cargadas</option>");
					        }
					    %>
					</select>
				</div>
		               
		        <div class="form-group">
		            <label for="photo">Foto:</label>
		            
		            <% if (player.getPhoto() != null && !player.getPhoto().isEmpty()) { %>
		                <div class="mb-2">
		                    <img src="<%=request.getContextPath() + "/images?id=" + player.getPhoto()%>" class="current-photo" alt="Foto actual" height="80">
		                </div>
		            <% } %>
		            
		            <input type="file" class="form-control" id="photo" name="photo" maxlength="250" accept="image/*" />
		            <small class="form-text text-white-50">Dejar vacío para mantener la foto actual. Formatos recomendados: PNG o JPG.</small>
		            <input type="hidden" name="currentPhoto" value="<%= player.getPhoto() %>" />
		        </div>
		        
		        <div class="form-group">
				    <label>Posiciones:</label><br>
				    <p style="font-size: 0.9rem; color:#ccc; margin-top:-4px;">
				        Marque con los <b>checkbox</b> todas las posiciones en las que juega el jugador,<br>
				        y seleccione con el <b>radio</b> cuál es la posición principal.
				    </p>
				
				    <%
				        if (positionsList != null && !positionsList.isEmpty()) {
				            for (Position p : positionsList) {
				
				                // si el jugador tiene esta posición
				                boolean checked = (playerPositionsList != null && playerPositionsList.contains(p.getId()));
				
				                // si es la principal
				                boolean isPrimary = (primary != null && primary == p.getId());
				    %>
				        <div style="display:flex; align-items:center; gap:8px; margin-bottom:6px;">
				
				            <!-- RADIO -->
				            <input type="radio"
				                   name="primaryPosition"
				                   id="primary_<%= p.getId() %>"
				                   value="<%= p.getId() %>"
				                   <%= isPrimary ? "checked" : "" %>
				                   <%= checked ? "" : "disabled" %> />
				
				            <!-- CHECKBOX -->
				            <input type="checkbox"
				                   name="positions"
				                   id="pos_<%= p.getId() %>"
				                   value="<%= p.getId() %>"
				                   <%= checked ? "checked" : "" %>
				                   onchange="toggleRadio(<%= p.getId() %>)" />
				
				            <!-- ETIQUETA -->
				            <label for="pos_<%= p.getId() %>" style="margin:0;">
				                <%= p.getDescription() %>
				            </label>
				
				        </div>
				    <%
				            }
				        } else {
				    %>
				        <p class='text-white'>No hay posiciones cargadas</p>
				    <%
				        }
				    %>
				</div>
		        
		        <div class="button-container mb-3">
			        <button type="button" class="btn btn-dark border border-white" onclick="history.back()">Cancelar</button>
		        	<button type="submit" class="btn text-white" style="background-color: #0D47A1">Guardar Cambios</button>
		        </div>
		    </form>
		</div>
		<script>
		function toggleRadio(id) {
			
		    const checkbox = document.getElementById("pos_" + id);
		    const radio = document.getElementById("primary_" + id);
		
		    if (checkbox.checked) {
		    	
		        radio.disabled = false;
		        
		    } else {
		        
		        if (radio.checked) {
		        	
		            radio.checked = false;
		            
		        }
		        
		        radio.disabled = true;
		        
		    }
		    
		}
		</script>
	</body>
</html>