package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import enums.*;
import logic.Logic;

@WebServlet("/actionassociation")
public class ActionAssociation extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Association buildAssociationFromRequest(HttpServletRequest request, String action) {
        
		Association association = new Association();
		if("edit".equals(action)) association.setId(Integer.parseInt(request.getParameter("id")));
		association.setName(request.getParameter("name"));
		association.setCreationDate(LocalDate.parse(request.getParameter("creationDate")));
		String type = request.getParameter("type");
	    if (type != null && !type.isEmpty()) {
	        association.setType(AssociationType.valueOf(type));
	    }
	    String continent = request.getParameter("continent");
	    if (association.getType() == AssociationType.CONTINENTAL && continent != null && !continent.isEmpty()) {
	        association.setContinent(Continent.valueOf(continent));
	    } else {
	        association.setContinent(null);
	    }

        return association;
    
	}
	
	private boolean checkCreationDate(LocalDate creationDate) {
		
		return creationDate.isBefore(LocalDate.now().plusDays(1));
	
	}
	
	private boolean checkTournaments(Integer id, Logic ctrl) throws SQLException {
		
		LinkedList<Tournament> tournaments = ctrl.getTournamentsByAssociationId(id);
		return tournaments.size() == 0;
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		
		try {
			
			if ("edit".equals(action)) {
				
				Association association = ctrl.getAssociationById(Integer.parseInt(request.getParameter("id")));
				
				LinkedList<Continent> availableContinents = new LinkedList<>();
				HashSet<Continent> used = new HashSet<>();
				LinkedList<Association> all = ctrl.getAllAssociations();
				for (Association a : all) {
					if (a.getType() == AssociationType.CONTINENTAL && a.getContinent() != null && a.getId() != association.getId()) {
						used.add(a.getContinent());
					}
				}
				for (Continent c : Continent.values()) {
					if (!used.contains(c)) availableContinents.add(c);
				}
				
				request.setAttribute("availableContinents", availableContinents);
				request.setAttribute("association", association);
				request.getRequestDispatcher("WEB-INF/Edit/EditAssociation.jsp").forward(request, response);
			
			} else if ("members".equals(action)) {
				
				int associationId = Integer.parseInt(request.getParameter("id"));
				
				Association association = ctrl.getAssociationById(associationId);
				LinkedList<Nationality> allNationalitiesList = ctrl.getAllNationalities();
				LinkedList<Nationality> currentMembers = ctrl.getAllNationalitiesByAssociationId(associationId);
				
				LinkedList<Nationality> displayedNationalities = new LinkedList<>();
				for (Nationality n : allNationalitiesList) {
					boolean include = true;
					if (association.getType() == AssociationType.CONTINENTAL && association.getContinent() != null) {
						if (n.getContinent() != association.getContinent()) include = false;
					} else if (association.getType() == AssociationType.NATIONAL) {
						LinkedList<Association> assocs = ctrl.getAssociationsByNationalityId(n.getId());
						if (assocs != null) {
							for (Association a : assocs) {
								if (a.getType() == AssociationType.NATIONAL && a.getId() != associationId) {
									include = false;
									break;
								}
							}
						}
					}
					if (include) displayedNationalities.add(n);
				}
				
				request.setAttribute("association", association);
				request.setAttribute("allNationalitiesList", allNationalitiesList);
				request.setAttribute("currentMembers", currentMembers);
				request.setAttribute("displayedNationalities", displayedNationalities);
				
				request.getRequestDispatcher("WEB-INF/Edit/EditAssociationNationalities.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				LinkedList<Continent> availableContinents = new LinkedList<>();
				HashSet<Continent> used = new HashSet<>();
				LinkedList<Association> all = ctrl.getAllAssociations();
				for (Association a : all) {
					if (a.getType() == AssociationType.CONTINENTAL && a.getContinent() != null) {
						used.add(a.getContinent());
					}
				}
				for (enums.Continent c : enums.Continent.values()) {
					if (!used.contains(c)) availableContinents.add(c);
				}
				
				request.setAttribute("availableContinents", availableContinents);
				request.getRequestDispatcher("WEB-INF/Add/AddAssociation.jsp").forward(request, response);
			
			} else {
				
				LinkedList<Association> associations = ctrl.getAllAssociations();
			    request.setAttribute("associationsList", associations);
			    request.getRequestDispatcher("/WEB-INF/Management/AssociationManagement.jsp").forward(request, response);
			
			}
			
		} catch(SQLException e) {

			request.setAttribute("errorMessage", e.getMessage());
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        try {
        	
        	if ("add".equals(action)) {
            	
            	Association association = buildAssociationFromRequest(request, action);

            	if (checkCreationDate(association.getCreationDate())) {

            		ctrl.addAssociation(association);
            		
            		response.sendRedirect("actionassociation?action=members&id=" + association.getId());
                    return;

            	} else {

            		request.setAttribute("errorMessage", "Error en la fecha de fundación, la misma debe ser maximo hoy");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            	
            } else if ("edit".equals(action)) {
            	
            	Association association = buildAssociationFromRequest(request, action);

            	if (checkCreationDate(association.getCreationDate())) {

            		ctrl.updateAssociation(association);

            	} else {

            		request.setAttribute("errorMessage", "Error en la fecha de fundación, la misma debe ser maximo hoy");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            	
            } else if ("members".equals(action)) {
                
                int idAssociation = Integer.parseInt(request.getParameter("id_association"));
                
                String[] selectedCountries = request.getParameterValues("ids_nationalities");
                
                ctrl.updateAssociationNationalities(idAssociation, selectedCountries);
                
            } else if ("delete".equals(action)){
            	
            	Integer id = Integer.parseInt(request.getParameter("id"));

            	if (checkTournaments(id, ctrl)) {

            		ctrl.deleteAssociation(id);

            	} else {

            		request.setAttribute("errorMessage", "No se puede eliminar una asociación que organiza torneos");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
            	}
            }
    	    
    	    LinkedList<Association> associations = ctrl.getAllAssociations();
    		request.setAttribute("associationsList", associations);
    	    request.getRequestDispatcher("WEB-INF/Management/AssociationManagement.jsp").forward(request, response);
        	
        } catch(SQLException e) {
        
        	request.setAttribute("errorMessage", e.getMessage());
        	request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	
        }       
	
	}

}