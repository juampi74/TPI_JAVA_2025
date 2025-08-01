<%@page import="java.util.LinkedList"%>
<%@page import="entities.Persona"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="http://getbootstrap.com/favicon.ico">
	<title>Personas</title>
	
	<!-- Bootstrap core CSS -->
    <link href="style/bootstrap.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="style/start.css" rel="stylesheet">
	
	<%
    	LinkedList<Persona> lp = (LinkedList<Persona>)request.getAttribute("listaPersonas");
	%>
	
</head>
<body>
	<div class="container">
		<div class="row">
        	<h4>Personas</h4>
        	<form action="agregarpersona" method="get" style="margin:0;">
        		<button type="submit" class="btn btn-success">Nueva Persona</button>
    		</form>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
                    				<th>id</th>
                    		    	<th>apellido y nombre</th>
                        			<th>fecha nacimiento</th>
                        			<th>direccion</th>
                        			<th>editar</th>
                        			<th>eliminar</th> <!-- eliminar -->
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<% for (Persona per : lp) { %>
                    			<tr>
                    				<td><%=per.getId()%></td>
                    				<td><%=per.getApellido_nombre()%></td>
                    				<td><%=per.getFecha_nacimiento()%></td>
                    				<td><%=per.getDireccion()%></td>
                    				<td>
                    					<form method="get" action="editarpersona" style="display:inline;">
		        							<input type="hidden" name="id" value="<%=per.getId()%>" />
		        							<button type="submit" class="btn btn-warning btn-sm">!</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="eliminarpersona" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar esta persona?');">
											<input type="hidden" name="id" value="<%=per.getId()%>" />
											<button type="submit" class="btn btn-danger btn-sm">X</button>
										</form>
                    				</td>
                    			</tr>
                    		<% } %>
                    		</tbody>
						</table>        		
					</div>
				</div>
		</div>          	
	</div> <!-- /container -->
</body>
</html>