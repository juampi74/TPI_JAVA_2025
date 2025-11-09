<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Player"%>
<%@ page import="entities.Club"%>
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
		<title>Jugadores</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Player> pll = (LinkedList<Player>) request.getAttribute("playersList");
		%>
		
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1 >Jugadores</h1>
		        	<form action="actionplayer" method="get" style="margin:0;">
					    <input type="hidden" name="action" value="add" />
					    <button type="submit" class="btn btn-dark btn-circular" style="border:none; background:none; padding:0;">
					        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" style="display: block;" alt="Agregar" width="40" height="40">
					    </button>
					</form>			
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                		<form method="get" action="actionplayer">
						    <label for="clubFilter">
								<h4>Filtrar por club:</h4>
							</label>
						    <select name="clubId" id="clubFilter" onchange="this.form.submit()">
						        <option value="">-- Todos los clubes --</option>
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
						         			<option value="<%= c.getId() %>" <%= (c.getId() == selectedId) ? "selected" : "" %>>
						                        <%= c.getName() %>
						                    </option>
			                   	 <%
						                }
						            }
						         %>           	
						    </select>
						</form>
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>DNI</th>
                    		    	<th>Apellido y Nombre</th>
                        			<th>Fecha Nacimiento</th>
                        			<th>Dirección</th>
                        			<th>Pie Dominante</th>
                        			<th>Número de Camiseta</th>
                        			<th>Altura</th>
                        			<th>Peso</th>
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
                    					<form method="get" action="actionplayer" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=pl.getId()%>" />
		        							<button type="submit" class="btn btn-primary btn-sm">✏️</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actionplayer" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este jugador?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=pl.getId()%>" />
											<button type="submit" class="btn btn-dark btn-sm">❌</button>
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
	</body>
</html>