<%@ page import="entities.Club"%>
<%@ page import="entities.Contract"%>
<%@ page import="entities.Tournament"%>
<%@ page import="java.util.LinkedList"%>
<%@ page import="java.time.format.DateTimeFormatter"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Home</title>
		<link href="style/bootstrap.css" rel="stylesheet">
	    <link href="style/signin.css" rel="stylesheet">
	    <link rel="icon" type="image/x-icon" href="assets/favicon.png">
	    
	    <style>
	    
	    	.fancy-select{
		      --bs-form-select-bg: rgba(255,255,255,.07);
		      --bs-form-select-border-color: rgba(255,255,255,.35);
		      --bs-form-select-focus-border-color: #0D6EFD;
		      --bs-form-select-focus-box-shadow: 0 0 0 .2rem rgba(13,110,253,.25);
		      background-color: var(--bs-form-select-bg) !important;
		      color:#fff !important;
		      border-color: var(--bs-form-select-border-color) !important;
		      border-radius:.5rem;
		      padding-right:2.25rem;
		      box-shadow:none;
		      background-image:
		        url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 16 16'%3e%3cpath fill='%23ffffff' d='M3.2 5.5 8 10.3l4.8-4.8.9.9L8 12.1 2.3 6.4l.9-.9z'/%3e%3c/svg%3e");
		      background-repeat:no-repeat;
		      background-position:right .6rem center;
		      background-size:1rem .8rem;
		    }
		    .fancy-select:hover{ border-color:#fff !important; }
	    
	    </style>
	    
	    <%
			Club club = (Club) request.getAttribute("clubWithMostContracts");
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
	            <div class="d-flex justify-content-center align-items-center col-auto text-dark border border-dark rounded p-3 me-4 border border-white" style="width: 300px; min-height: 200px; background-color: rgba(33,37,41,1);">
			        <form action="actionclub" method="get" style="margin:0;">
						<button type="submit" class="btn btn-outline-light d-flex flex-column align-items-center justify-content-center w-100 h-100" style="border:none; background:none;">
					    	<img alt="Escudo" src="<%= club != null ? club.getBadgeImage() : src_shield %>" width="85" height="95" style="margin-bottom:10px;">
					    	<h4 class="text-white m-0">Ver Clubes</h4>
					  	</button>
					</form>
	            </div>
	
                <%
                	if (contract == null) {
                %>
                		<div class="d-flex justify-content-center align-items-center col-auto text-center border border-dark rounded p-4 bg-dark border border-white" style="width: 300px; min-height: 200px;">
						    <form action="actioncontract" method="get" style="margin:0;">
						        <input type="hidden" name="action" value="add">
						        <button type="submit" class="btn btn-outline-light d-flex flex-column align-items-center justify-content-center w-100 h-100" style="border:none; background:none;">
						            <img alt="Agregar contrato" src="${pageContext.request.contextPath}/assets/contract.svg" width="85" height="95" style="margin-bottom: 10px;">
						            <h4 class="text-white m-0">Agregar Contrato</h4>
						        </button>
						    </form>
						</div>
                <%
                	} else {
                %>		
                		<div class="col-auto text-dark border border-dark rounded p-3 bg-dark border border-white" style="width: 300px; min-height: 200px;">
	                		<h3 style="color:white;">Extender Contrato</h3>
	                		<div class="d-flex my-3 align-items-center justify-content-between">
	                			<img alt="" src="<%= contract.getPerson().getPhoto() %>" width="70" height="90">
		                		<div class="text-left" style="vertical-align: middle !important;">
		                			<p class="text-white m-0" >Nombre: <b><%= contract.getPerson().getFullname() %></b></p>
		                			<p class="text-white m-0">Fecha Inicio: <b><%= contract.getStartDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></b></p>
		                			<p class="text-white m-0">Fecha Fin: <b><%= contract.getEndDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) %></b></p>
		                		</div>
	                		</div>
	                		
	                		<form action="actioncontract" method="post" class="text-start">
							    <input type="hidden" name="action" value="extend">
							    <input type="hidden" name="id" value="<%= contract.getId() %>">
							
							    <div class="d-flex justify-content-center align-items-center gap-2">
								    <select name="extension" id="extension"
				                  		class="form-select form-select-md w-auto fancy-select bg-dark text-white">
							            <option value="" disabled selected>Seleccionar duración</option>
							            <option value="6">6 meses</option>
							            <option value="12">1 año</option>
							            <option value="24">2 años</option>
							            <option value="36">3 años</option>
							        </select>
							
							        <button type="submit" class="btn btn-success p-1" style="">
							            <img src="${pageContext.request.contextPath}/assets/judge.svg" alt="Extender" width="28" height="28">
							        </button>
							    </div>
							</form>
                		</div>
                <%		
                	}
                %>
            
        	</div>

	        <div  class="row justify-content-center">
	        	<div class="d-flex justify-content-center align-items-center col-auto text-dark border border-dark rounded p-3 border border-white" style="width: 625px; min-height: 120px; background-color: rgba(33,37,41,1);">
	        		
	        		<%
	        			if (ts.isEmpty()) {
	        				
	        				String src_trophy = request.getContextPath() + "/assets/trophy.svg";
	        		%>
		        			<form action="actiontournament" method="get" style="margin:0;">
						        <input type="hidden" name="action" value="add">
						        <button type="submit" class="btn btn-outline-light d-flex flex-column align-items-center justify-content-center w-100 h-100" style="border:none; background:none;">
						            <img alt="Agregar torneo" src="<%= src_trophy %>" width="85" height="95" style="margin-bottom: 10px;">
						            <h4 class="text-white m-0">Agregar Torneo</h4>
						        </button>
						    </form>
	        		<%
	        			} else {
	        		%>
		        			<form action="actiontournament" method="get" style="margin:0;">
								<button type="submit" class="btn btn-outline-light d-flex flex-column align-items-center justify-content-center w-100 h-100" style="border:none; background:none;">
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
									
			        				}
								%>
							  	</button>
							</form>	
	        	</div>  
	        </div>
        </main>
	</body>
</html>