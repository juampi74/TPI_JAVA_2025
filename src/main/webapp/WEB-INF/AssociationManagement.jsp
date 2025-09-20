<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Association"%>
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
		<title>Asociaciones</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Association> al = (LinkedList<Association>) request.getAttribute("associationsList");
		%>
		
	</head>
	<body>
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container">
			<div class="row">
				<div class="d-flex justify-content-between my-4">
	        		<h4>Asociaciones</h4>
		        	<form action="actionassociation" method="get" style="margin:0;">
		        		<input type="hidden" name="action" value="add" />
		        		<button type="submit" class="btn btn-success">Nueva Asociación</button>
		    		</form>				
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>Nombre</th>
                    		    	<th>Fecha de Creación</th>
                    		    	<th>Editar</th>
                        			<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (Association a : al) {
                    		%>
                    			<tr>
                    				<td><%=a.getName()%></td>
                    				<td><%=a.getCreationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td>
                    					<form method="get" action="actionassociation" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=a.getId()%>" />
		        							<button type="submit" class="btn btn-primary btn-sm">✏️</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actionassociation" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar esta asociación?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=a.getId()%>" />
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