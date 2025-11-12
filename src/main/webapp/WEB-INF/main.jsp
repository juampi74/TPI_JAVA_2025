<%@ page import="entities.Club"%>
<%@ page import="entities.Contract"%>
<%@ page import="entities.Tournament"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home</title>
		<link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/signin.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    
	    <%
			Club club = (Club) request.getAttribute("clubMoreContracts");
	    	Contract contract = (Contract) request.getAttribute("nextExpiringContract");
	    	LinkedList<Tournament> ts = (LinkedList<Tournament>) request.getAttribute("tournaments");
	    	String src_shield = request.getContextPath() + "/assets/shield.svg";
	    	String src_contract = request.getContextPath() + "/assets/contract.svg";
		%>
	    
	</head>
	<body class="d-block m-0 p-0" style="min-height:100vh; background-color: #10442E;">

	    <div class="w-100">
	        <jsp:include page="/WEB-INF/Navbar.jsp" />
	    </div>
		<main class="container text-center mt-5">
		        <div class="row justify-content-center mb-4">
		            <div class="col-auto text-dark border border-dark rounded p-3 me-4" style="width: 300px; min-height: 200px; background-color: rgba(33,37,41,1);">
		                <img alt="" src="<%=club != null ? club.getBadgeImage() : src_shield%>" width="85" height="95" style="margin-bottom: 10px;">
		                <h3 style="color:white;">Ver Clubes</h3>
		            </div>
		
		            
		                <%
		                	if (contract == null) {
		                %>
		                		<div class="col-auto text-center border border-dark rounded p-4 bg-dark" style="width: 300px; min-height: 200px;">
								    <form action="actioncontract" method="get" style="margin:0;">
								        <input type="hidden" name="action" value="add">
								        <button type="submit" class="btn btn-outline-light d-flex flex-column align-items-center justify-content-center w-100 h-100" style="border:none; background:none;">
								            <img alt="Añadir contrato" src="${pageContext.request.contextPath}/assets/contract.svg" width="85" height="95" style="margin-bottom: 10px;">
								            <h4 class="text-white m-0">Añadir Contrato</h4>
								        </button>
								    </form>
								</div>
		                <%
		                	} else {
		                %>		
		                		<div class="col-auto text-dark border border-dark rounded p-3 bg-dark" style="width: 300px; min-height: 200px;">
			                		<h3 style="color:white;">Extender Contrato</h3>
			                		<div class="text-left">
			                			<p class="text-white m-0" >Nombre: <%= contract.getPerson().getFullname() %></p>
			                			<p class="text-white m-0">Fecha Inicio: <%= contract.getStartDate() %></p>
			                			<p class="text-white">Fecha Fin: <%= contract.getEndDate() %></p>
			                		</div>
			                		
			                		<form action="actioncontract" method="post" class="text-start">
									    <input type="hidden" name="action" value="extend">
									    <input type="hidden" name="id" value="<%= contract.getId() %>">
									
									    <div class="d-flex align-items-center gap-2">
									        <select class="form-select w-auto" name="extension" id="extension" required>
									            <option value="" disabled selected>Seleccionar duración</option>
									            <option value="6">6 meses</option>
									            <option value="12">1 año</option>
									            <option value="24">2 años</option>
									            <option value="36">3 años</option>
									        </select>
									
									        <button type="submit" class="btn btn-success p-1" style="">
									            <img src="${pageContext.request.contextPath}/assets/judge.svg" alt="Extender" width="30" height="30">
									        </button>
									    </div>
									</form>
		                		</div>
		                <%		
		                	}
		                %>
		            
		        </div>
		
		        <div  class="row justify-content-center">
		        	<div class="col-auto text-dark border border-dark rounded p-3" style="width: 650px; min-height: 120px; background-color: rgba(33,37,41,1);">
		        		
		        		<%
		        			if (ts.isEmpty()){
		        				String src_trophy= request.getContextPath() + "/assets/trophy.svg";
		        		%>
		        			<h4 class="text-white">Añadir Torneos</h4>
		        			<img alt="" src="<%= src_trophy %>" width="85" height="95" style="margin-bottom: 10px;">
		        			
		        		<%
		        			} else {
		        		%>
		        				<h4 class="text-white">Ver Torneos</h4>
					        	<table class="table table-dark">
		                    		<thead>
		                    			<tr >
								            <th>Nombre</th>
								            <th>Fecha de Inicio</th>
								            <th>Fecha de Fin</th>  
		                      			</tr>
		                      		</thead>
		                    		<tbody>
		                    		<%
		                    			
		                    			int max = Math.min(3, ts.size());
		                    	    	for (int i = 0 ; i < max ; i++) {
		                    		%>
		                    			<tr>
		                    				<td><%=ts.get(i).getName()%></td>
		                    				<td><%=ts.get(i).getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
		                    				<td><%=ts.get(i).getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))%></td>
		                    			</tr>
		                    		<% 
		                    			}
		                    		%>
		                    		</tbody>
								</table>
								<%
								    if (ts.size() > 3) {
								%>
								    <p id="mensaje" style="display:block; color:white; margin-bottom: 0;">
								        Y <%= ts.size() - max %> torneo/s más
								    </p>
								<%
								    } else {
								%>
								    <p id="mensaje" style="display:none; margin-bottom: 0;"></p>
								<%
								    }
								%>
							<%
		        				}
							%>
		        	</div>  
		        </div>
	        </main>
	</body>
</html>