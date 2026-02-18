<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="entities.User" %>
<%@ page import="enums.UserRole" %>

<%
    User userLogged = (User) session.getAttribute("user");
    String path = request.getContextPath();
%>

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8" />
	  	<title>Navbar</title>
	  	<link
	   		rel="stylesheet"
	    	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css"
	  	/>
	  	<link
	    	rel="stylesheet"
	    	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
	  	/>
	  	<style>
	    	.navbar-nav .nav-link {
	      		transition: color 0.3s, background-color 0.3s;
	      		border-radius: 5px;
	      		padding: 8px 15px;
	    	}
	
	    	.navbar-nav .nav-link:hover {
	      		color: #1a6b32 !important;
	    	}
	
	    	.navbar-nav .nav-link.active {
	      		color: #1a6b32 !important;
	      		font-weight: bold;
	    	}
	    
	    	.user-greeting {
	      		font-size: 0.9rem;
	      		color: white;
	      		margin-right: 15px;
	    	}
	    	
	    	.nav-profile-link {
			  transition: transform 0.2s;
			  display: flex;
			  align-items: center;
			  text-decoration: none;
			}
			
			.nav-profile-link:hover {
			  transform: scale(1.05);
			}
			
			.nav-profile-pic {
			  width: 38px;
			  height: 38px;
			  object-fit: contain;
			  border-radius: 50%;
			  border: 2px solid #198754;
			}
			
			.nav-profile-placeholder {
			  width: 38px;
			  height: 38px;
			  background-color: #e9ecef;
			  color: #2C3034;
			  border-radius: 50%;
			  display: flex;
			  justify-content: center;
			  align-items: center;
			  font-size: 1.1rem;
			  border: 2px solid #198754;
			}
	  	</style>
	</head>
  	<body>
    	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	      	
	      	<div class="container-fluid">
	        
	        	<a class="navbar-brand" href="<%=path%>/Home">
	          		<img
	            		src="<%=path%>/assets/TeamUp_LogoNavbar.png"
	            		alt="TeamUp Logo"
	            		width="80"
	            		height="50"
	          		/>
	        	</a>
	
	        	<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarContent" aria-controls="navbarContent" aria-expanded="false" aria-label="Toggle navigation">
	            	<span class="navbar-toggler-icon"></span>
	        	</button>
	
		        <div class="collapse navbar-collapse" id="navbarContent">
		        	<ul class="navbar-nav me-auto mb-2 mb-lg-0">
    
					    <li class="nav-item"><a class="nav-link" href="<%=path%>/actiontournament">Torneos</a></li>
					    <li class="nav-item"><a class="nav-link" href="<%=path%>/actionmatch">Partidos</a></li>
					
					    <li class="nav-item dropdown">
					        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownParticipants" role="button" data-bs-toggle="dropdown" aria-expanded="false">
					            Participantes
					        </a>
					        <ul class="dropdown-menu" aria-labelledby="navbarDropdownParticipants">
					            <li><a class="dropdown-item" href="<%=path%>/actionclub">Clubes</a></li>
					            <li><hr class="dropdown-divider"></li>
					            <li><a class="dropdown-item" href="<%=path%>/actionplayer">Jugadores</a></li>
					            <li><a class="dropdown-item" href="<%=path%>/actioncoach">Directores Técnicos</a></li>
					            <li><a class="dropdown-item" href="<%=path%>/actionpresident">Presidentes</a></li>
					        </ul>
					    </li>
					<% if (userLogged != null) { %>
					    <% if (userLogged.getRole() == UserRole.ADMIN) { %>
					        
					        <li class="nav-item border-start ms-2 ps-2 border-secondary d-none d-lg-block"></li>
					
					        <li class="nav-item dropdown">
					            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownManagement" role="button" data-bs-toggle="dropdown" aria-expanded="false">
					                Gestión
					            </a>
					            <ul class="dropdown-menu" aria-labelledby="navbarDropdownManagement">
					                <li><a class="dropdown-item" href="<%=path%>/actioncontract">Contratos</a></li>
					                <li><a class="dropdown-item" href="<%=path%>/actionstadium">Estadios</a></li>
					                <li><a class="dropdown-item" href="<%=path%>/actionassociation">Asociaciones</a></li>
					                <li><hr class="dropdown-divider"></li>
					                <li><a class="dropdown-item" href="<%=path%>/actionposition">Posiciones</a></li>
					                <li><a class="dropdown-item" href="<%=path%>/actionnationality">Nacionalidades</a></li>
					            </ul>
					        </li>
					
					        <% if (userLogged.getRole() == UserRole.ADMIN) { %>
							    <li class="nav-item ms-lg-2 d-flex align-items-center">
							        
							        <a class="nav-link fw-bold border border-secondary rounded px-3 py-1" href="<%=path%>/admin/users">
							            <i class="fas fa-user-check me-1"></i> Solicitudes de Registro
							        </a>
							        
							    </li>
							<% } %>
					
					    <% } else if (userLogged.getRole() == UserRole.COACH) { %>
								<li class="nav-item border-start ms-2 ps-2 border-secondary d-none d-lg-block"></li>
					
						        <li><a class="dropdown-item" href="<%=path%>/actioncontract">Contratos</a></li>
						<% } } %>
					</ul>

		          	<div class="ms-auto d-flex align-items-center mt-3 mt-lg-0">
					    <% if (userLogged != null) { %>
					        
					        <% if (userLogged.getRole() == UserRole.ADMIN) { %>

					            <span class="user-greeting d-none d-lg-block">
					                <strong><%= userLogged.getEmail() %></strong>
					            </span>

					        <% } else { %>

					            <a href="<%=path%>/my-profile" class="nav-profile-link me-3" title="Mi Perfil">
					                
					                <% if (userLogged.getPerson().getPhoto() != null && !userLogged.getPerson().getPhoto().isEmpty()) { %>
					                
					                    <img src="<%=path%>/images?id=<%= userLogged.getPerson().getPhoto() %>" 
					                         class="nav-profile-pic" 
					                         alt="Foto de Perfil">
					                         
					                <% } else { %>
					                    
					                    <div class="nav-profile-placeholder">
					                        <i class="fas fa-user"></i>
					                    </div>
					                
					                <% } %>
					            
					            </a>
					        
					        <% } %>
					
					        <a href="<%=path%>/login?logout=true" class="btn btn-outline-danger btn-sm">
					            <i class="fas fa-sign-out-alt"></i> Salir
					        </a>
					
					    <% } else { %>
					        
					        <a href="<%=path%>/login" class="btn btn-success btn-sm">
					            <i class="fas fa-sign-in-alt"></i> Ingresar
					        </a>
					    
					    <% } %>
					</div>

        		</div>
      		</div>
    	</nav>

	    <script>
	      
	    	const currentPath = window.location.pathname;
	      	const links = document.querySelectorAll('.navbar-nav .nav-link');
	
	      	links.forEach((link) => {
	        
	    		const linkHref = link.getAttribute('href');
	        
	        	if (currentPath.endsWith(linkHref) || linkHref.endsWith(currentPath)) {

	        		link.classList.add('active');
	        
	        	}
	      
	      	});
	      	
	    </script>
	    
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
  	</body>
</html>