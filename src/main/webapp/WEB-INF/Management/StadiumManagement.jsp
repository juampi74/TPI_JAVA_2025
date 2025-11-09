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
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
		<title>Estadios</title>
		
		<link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Stadium> sl = (LinkedList<Stadium>) request.getAttribute("stadiumsList");
		%>
		
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4 align-items-center">
	        		<h1>Estadios</h1>
		        	<form action="actionstadium" method="get" style="margin:0;">
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
                    					<form method="get" action="actionstadium" style="display:inline;" class="d-flex justify-content-center align-items-center">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=s.getId()%>" />
		        							<button type="submit" class="btn btn-warning btn-sm">
												<img src="${pageContext.request.contextPath}/assets/edit.svg" style="display: block;" alt="Agregar" width="25" height="25">
											</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actionstadium" style="display:inline;" class="d-flex justify-content-center align-items-center" onsubmit="return confirm('¿Estás seguro que querés eliminar este estadio?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=s.getId()%>" />
											<button type="submit" class="btn btn-danger btn-sm">
												<img src="${pageContext.request.contextPath}/assets/delete.svg" style="display: block;" alt="Agregar" width="25" height="25">
											</button>
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