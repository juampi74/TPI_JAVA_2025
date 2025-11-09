<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.TechnicalDirector"%>
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
		<title>Directores Técnicos</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<TechnicalDirector> tdl = (LinkedList<TechnicalDirector>) request.getAttribute("technicalDirectorsList");
		%>
		
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Directores Técnicos</h1>
		        	<form action="actiontechnicaldirector" method="get" style="margin:0;">
		        		<input type="hidden" name="action" value="add" />
					    <button type="submit" class="btn btn-dark btn-circular" style="border:none; background:none; padding:0;">
					        <img src="${pageContext.request.contextPath}/assets/add-button2.svg" style="display: block;" alt="Agregar" width="40" height="40">
					    </button>
		    		</form>				
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>DNI</th>
                    		    	<th>Apellido y Nombre</th>
                        			<th>Fecha Nacimiento</th>
                        			<th>Dirección</th>
                        			<th>Formación Preferida</th>
                        			<th>Licencia de Entrenador</th>
                        			<th>Fecha de Obtención de Licencia</th>
                        			<th>Editar</th>
                        			<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (TechnicalDirector td : tdl) {
                    		%>
                    			<tr>
                    				<td><%=td.getId()%></td>
                    				<td><%=td.getFullname()%></td>
                    				<td><%=td.getBirthdate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=td.getAddress()%></td>
                    				<td><%=td.getPreferredFormation()%></td>
                    				<td><%=td.getCoachingLicense()%></td>
                    				<td><%=td.getLicenseObtainedDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td>
                    					<form method="get" action="actiontechnicaldirector" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=td.getId()%>" />
		        							<button type="submit" class="btn btn-primary btn-sm">✏️</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actiontechnicaldirector" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este director técnico?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=td.getId()%>" />
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