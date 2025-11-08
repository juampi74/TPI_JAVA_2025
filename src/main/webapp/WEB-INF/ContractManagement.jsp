<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>
<%@ page import="entities.Contract"%>
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
		
	    <link href="style/bootstrap.css" rel="stylesheet">
	
	    <link href="style/start.css" rel="stylesheet">
		
		<%
			LinkedList<Contract> cl = (LinkedList<Contract>) request.getAttribute("contractsList");
		%>
		
	</head>
	<body style="background-color: #10442E;">
		<jsp:include page="Navbar.jsp"></jsp:include>
		<div class="container" style="color: white;">
			<div class="row">
				<div class="d-flex justify-content-between my-4">
	        		<h1>Contratos</h1>
		        	<form action="actioncontract" method="get" style="margin:0;">
		        		<input type="hidden" name="action" value="add" />
		        		<button type="submit" class="btn btn-success">Nuevo Contrato</button>
		    		</form>				
				</div>
            	<div class="col-12 col-sm-12 col-lg-12">
                	<div class="table-responsive">
                    	<table class="table">
                    		<thead>
                    			<tr>
						            <th>Persona</th>
						            <th>Club</th>
						            <th>Fecha de Inicio</th>
						            <th>Fecha de Fin</th>
						            <th>Salario</th>
						            <th>Cláusula de Rescisión</th>
						            <th>Editar</th>
                       				<th>Eliminar</th>
                      			</tr>
                      		</thead>
                    		<tbody>
                    		<%
                    	    	for (Contract c : cl) {
                    		%>
                    			<tr>
                    				<td><%=c.getPerson().getFullname()%></td>
                    				<td><%=c.getClub().getName()%></td>
                    				<td><%=c.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=c.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
                    				<td><%=c.getSalary()%></td>
                    				<td><%=c.getReleaseClause()%></td>
                    				<td>
                    					<form method="get" action="actioncontract" style="display:inline;">
                    						<input type="hidden" name="action" value="edit" />
		        							<input type="hidden" name="id" value="<%=c.getId()%>" />
		        							<button type="submit" class="btn btn-primary btn-sm">✏️</button>
		    							</form>
                    				</td>
                    				<td>
                    					<form method="post" action="actioncontract" style="display:inline;" onsubmit="return confirm('¿Estás seguro que querés eliminar este contrato?');">
											<input type="hidden" name="action" value="delete" />
											<input type="hidden" name="id" value="<%=c.getId()%>" />
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