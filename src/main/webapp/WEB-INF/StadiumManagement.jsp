<%@ page import="java.util.LinkedList"%>
<%@ page import="entities.Stadium"%>
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
		<title>Estadios</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Stadium> sl = (LinkedList<Stadium>) request.getAttribute("stadiumsList");
		%>
		
	</head>
	<body>
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container">
			<div class="row">
	        	<h4>Estadios</h4>
	        	<form action="actionstadium" method="get" style="margin:0;">
	        		<input type="hidden" name="action" value="add" />
	        		<button type="submit" class="btn btn-success">Nuevo Estadio</button>
	    		</form>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>Nombre</th>
                    		    	<th>Capacidad</th>
                    		    	<th>Editar</th>
                        			<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (Stadium s : sl) {
                    		%>
                    			<tr>
                    				<td><%=s.getName()%></td>
                    				<td><%=s.getCapacity()%></td>
                    				<td>
                    					<form method="get" action="actionstadium" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=s.getId()%>" />
		        							<button type="submit" class="btn btn-warning btn-sm">!</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actionstadium" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este estadio?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=s.getId()%>" />
											<button type="submit" class="btn btn-danger btn-sm">X</button>
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