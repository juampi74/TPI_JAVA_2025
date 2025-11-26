<%@ page import="entities.*" %>
<%@ page import="enums.*" %>
<%@ page import="java.util.LinkedList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    Association association = (Association) request.getAttribute("association");
    
	LinkedList<Nationality> allNationalitiesList = (LinkedList<Nationality>) request.getAttribute("allNationalitiesList");
    LinkedList<Nationality> currentMembers = (LinkedList<Nationality>) request.getAttribute("currentMembers");
    LinkedList<Nationality> displayedNationalities = (LinkedList<Nationality>) request.getAttribute("displayedNationalities");

    if (displayedNationalities == null) {
    
    	displayedNationalities = new LinkedList<>();
        
        boolean isContinental = (association.getType() == AssociationType.CONTINENTAL);
        boolean isNational = (association.getType() == AssociationType.NATIONAL);
        
        if ((isContinental || isNational) && association.getContinent() != null) {
        
        	for (Nationality n : allNationalitiesList) {
                
        		if (n.getContinent() == association.getContinent()) {
                
        			displayedNationalities.add(n);
                
        		}
            
        	}
        
        } else {
        
        	displayedNationalities = allNationalitiesList;
        
        }
    
    }

    String inputType = "checkbox";

    if (association.getType() == AssociationType.NATIONAL) {
    
    	inputType = "radio";
    
    }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Gestionar Miembros - <%= association.getName() %></title>
    <link href="style/bootstrap.css" rel="stylesheet" />
    <link rel="icon" type="image/x-icon" href="assets/favicon.png" />
    
    <style>
        .button-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-top: 20px;
        }

        .countries-container {
            max-height: 400px;
            overflow-y: auto;
            background-color: rgba(0, 0, 0, 0.18);
            padding: 15px;
            border-radius: 8px;
            border: 1px solid rgba(255, 255, 255, 0.03);
            scrollbar-width: thin;
            scrollbar-color: rgba(255, 255, 255, 0.12) rgba(255, 255, 255, 0.03);
        }

        .countries-container::-webkit-scrollbar {
        	width: 10px;
        	height: 10px;
        }
        
        .countries-container::-webkit-scrollbar-track {
        	background: rgba(255, 255, 255, 0.03);
        	border-radius: 8px;
        }
        
        .countries-container::-webkit-scrollbar-thumb {
        	background: rgba(255, 255, 255, 0.12);
        	border-radius: 8px;
        	border: 2px solid rgba(0, 0, 0, 0);
        }
        
        .countries-container:hover::-webkit-scrollbar-thumb {
        	background: rgba(255, 255, 255, 0.18);
        }

        .nation-item {
            background: linear-gradient(90deg, rgba(255, 255, 255, 0.02), rgba(255, 255, 255, 0.01));
            border-radius: 8px;
            padding: 10px 18px;
            position: relative;
            transition: transform 120ms ease, box-shadow 120ms ease, background 120ms ease;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }

        .nation-item:hover {
            transform: translateY(-3px);
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.35);
            background: rgba(255, 255, 255, 0.06);
        }

        .nation-item:hover .nation-name,
        .nation-item:hover .text-muted {
            color: #ffffff !important;
        }

        .flag-thumb {
            display: block;
            height: 26px;
            max-width: 40px;
            width: auto;
            object-fit: cover;
            border-radius: 4px;
            background-color: transparent;
            border: none;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.45);
        }

        .flag-placeholder {
            width: 44px;
            height: 26px;
            background: linear-gradient(180deg, rgba(255, 255, 255, 0.02), rgba(255, 255, 255, 0.01));
            border-radius: 4px;
            display: inline-block;
        }

        .nation-name {
            font-weight: 600;
            color: #000;
            font-size: 14px;
        }

        .list-group-item {
            border: none;
            padding: 0;
            background: transparent;
        }

        .form-check-label, .form-check-input {
        	cursor: pointer;
        }

        .nation-item .form-check-input {
            position: absolute;
            right: 12px;
            top: 50%;
            transform: translateY(-50%);
            margin: 0;
            display: inline-block;
            width: auto;
            z-index: 2;
        }

        .nation-item .form-check-input[type='radio'] {
            -webkit-appearance: radio;
            width: 18px; height: 18px;
            border-radius: 50%;
            background: transparent; border: none;
        }

        .nation-item .form-check-input[type='checkbox'] {
            -webkit-appearance: checkbox;
            width: 16px; height: 16px;
            border-radius: 3px;
            background: transparent; border: none;
        }

        .nation-item .form-check-input:focus {
        	outline: none; box-shadow: none;
        }
    </style>
  </head>
  <body style="background-color: #10442e">
    
    <jsp:include page="/WEB-INF/Navbar.jsp"></jsp:include>

    <div class="container text-white">
        <h2 class="mt-4">
            Gestionar Miembros: <%= association.getName() %>
        </h2>
        
        <p class="text-white-50">
            Tipo: <strong><%= association.getType().getDisplayName() %></strong>
            <%= association.getContinent() != null ? "(" + association.getContinent().getDisplayName() + ")" : "" %>
        </p>

        <form action="actionassociation" method="post" class="mt-4">
            <input type="hidden" name="action" value="members" />
            <input type="hidden" name="id_association" value="<%= association.getId() %>" />

            <div class="countries-container mb-3" style="max-height: 400px; overflow-y: auto; border: 1px solid #444; padding: 10px;">
                <div class="list-group">
                    <% 
                    for (Nationality nationality : displayedNationalities) { 
                        
                    	boolean isMember = false; 
                        
                    	if (currentMembers != null) { 
                        
                    		for (Nationality member : currentMembers) { 
                            
                    			if (member.getId() == nationality.getId()) { 
                                
                    				isMember = true; 
                                    break; 
                                
                    			} 
                            
                    		} 
                        
                    	} 
                    %>
                    
                    <label class="list-group-item d-flex align-items-center justify-content-between nation-item mb-2" 
                           for="chk_<%= nationality.getId() %>">
                        
                        <span class="d-flex align-items-center">
                            
                            <% if(nationality.getFlagImage() != null) { %> 
                            
                                <img class="flag-thumb mr-3" 
                                     src="<%=request.getContextPath() + "/images?id=" + nationality.getFlagImage()%>" 
                                     alt="flag <%= nationality.getName() %>"> 
                            
                            <% } else { %>
                            
                                <span class="flag-placeholder mr-3"></span>
                            
                            <% } %>
                            
                            <span>
                                <span class="nation-name d-block">
                                    <%= nationality.getName() %>
                                </span>
                                <small class="text-muted">
                                    <%= nationality.getIsoCode() %>
                                </small>
                            </span>
                        </span>
                        
                        <input class="form-check-input align-self-center" 
                               type="<%= inputType %>" 
                               name="ids_nationalities" 
                               value="<%= nationality.getId() %>" 
                               id="chk_<%= nationality.getId() %>" 
                               <%= isMember ? "checked" : "" %> />
                    </label>
                    
                    <% } %> 
                    
                    <% if (displayedNationalities.isEmpty()) { %>
                        
                        <div class="alert alert-warning">
                            No hay nacionalidades cargadas para el continente 
                            <b><%= (association.getContinent() != null) ? association.getContinent().getDisplayName() : "seleccionado" %></b>.
                        </div>
                    
                    <% } %>
                </div>
            </div>

            <div class="button-container">
                <button type="button" class="btn btn-dark border border-white" onclick="history.back()">
                    Cancelar
                </button>
                <button type="submit" class="btn text-white" style="background-color: #0d47a1">
                    Guardar Cambios
                </button>
            </div>
        </form>
    </div>
  </body>
</html>