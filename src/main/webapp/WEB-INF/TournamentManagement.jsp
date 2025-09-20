<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Tournament"%>
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
	    <link rel="icon" href="http://getbootstrap.com/favicon.ico">
		<title>Torneos</title>
		
	    <link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Tournament> tl = (LinkedList<Tournament>) request.getAttribute("tournamentsList");
		%>
		
	</head>
	<body>
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container">
			<div class="row">
				<div class="d-flex justify-content-between my-4">
	        		<h4>Torneos</h4>
		        	<form action="actiontournament" method="get" style="margin:0;">
		        		<input type="hidden" name="action" value="add" />
		        		<button type="submit" class="btn btn-success">Nuevo Torneo</button>
		    		</form>				
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
						            <th>Nombre</th>
						            <th>Fecha de Inicio</th>
						            <th>Fecha de Fin</th>
						            <th>Formato</th>
						            <th>Edición</th>
						            <th>Asociación</th>
						            <th>Editar</th>
                       				<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (Tournament t : tl) {
                    		%>
                    			<tr>
                    				<td><%=t.getName()%></td>
                    				<td><%=t.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=t.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=t.getFormat()%></td>
                    				<td><%=t.getSeason()%></td>
                    				<td><%=t.getAssociation().getName()%></td>
                    				<td>
                    					<form method="get" action="actiontournament" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=t.getId()%>" />
		        							<button type="submit" class="btn btn-primary btn-sm">✏️</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actiontournament" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este torneo?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=t.getId()%>" />
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