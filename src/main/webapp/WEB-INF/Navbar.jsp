<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>Navbar</title>
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css">
		<style>
	      
	      .navbar-nav .nav-link {
	        transition: color 0.3s, background-color 0.3s;
	        border-radius: 5px;
	        padding: 8px 15px;
	      }
	
	      .navbar-nav .nav-link:hover {
	        color: #1A6B32 !important;
	      }
	
	      
	      .navbar-nav .nav-link.active {
	        color: #1A6B32 !important;
	      }
	    </style>
	</head>
	<body>
	    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
	        <div class="container-fluid">
	            <a class="navbar-brand" href="Home">
	            	<img src="assets/TeamUp_LogoNavbar.png" alt="TeamUp Logo" width="80" height="50">
	            </a>
	            
	            <div class="collapse navbar-collapse">
	                <ul class="navbar-nav">
	                    <li class="nav-item">
	                        <a class="nav-link" href="actionplayer">Jugadores</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actioncoach">Directores Técnicos</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actionpresident">Presidentes</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actionstadium">Estadios</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actionclub">Clubes</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actionassociation">Asociaciones</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actiontournament">Torneos</a>
	                    </li>
	                    <li class="nav-item">
	                        <a class="nav-link" href="actioncontract">Contratos</a>
	                    </li>
	                </ul>
	            </div>
	        </div>
	    </nav>
	    
	    <script>
	    	const currentPage = window.location.pathname.split("/").pop();
		    const links = document.querySelectorAll(".navbar-nav .nav-link");
		
		    links.forEach(link => {
		      	if (link.getAttribute("href") === currentPage) {
		        	link.classList.add("active");
		      	}
		    });
	    </script>
	    
	</body>
</html>