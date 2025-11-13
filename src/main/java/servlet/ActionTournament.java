package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import logic.Logic;

@WebServlet("/actiontournament")
public class ActionTournament extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Tournament buildTournamentFromRequest(HttpServletRequest request, String action, Logic ctrl) throws SQLException {
        
		Tournament tournament = new Tournament();
		if ("edit".equals(action)) tournament.setId(Integer.parseInt(request.getParameter("id")));
		tournament.setName(request.getParameter("name"));
		tournament.setStartDate(LocalDate.parse(request.getParameter("startDate")));
		tournament.setEndDate(LocalDate.parse(request.getParameter("endDate")));
		tournament.setFormat(request.getParameter("format"));
		tournament.setSeason(request.getParameter("season"));
        tournament.setAssociation(ctrl.getAssociationById(Integer.parseInt(request.getParameter("id_association"))));
		
        return tournament;
    
	}
	
	private boolean checkDates(LocalDate startDate, LocalDate endDate) {
		
		 LocalDate today = LocalDate.now();

		    if (startDate.isBefore(today)) {
		        
		    	return false;
		    
		    }

		    if (endDate.isBefore(startDate.plusMonths(4))) {
		    
		    	return false;
		    
		    }

		    return true;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        try {
        	
        	if ("edit".equals(action)) {
            	
            	Tournament tournament = ctrl.getTournamentById(Integer.parseInt(request.getParameter("id")));
    			request.setAttribute("tournament", tournament);
                
                LinkedList<Association> associations = ctrl.getAllAssociations();
            	request.setAttribute("associationsList", associations);
                
            	request.getRequestDispatcher("WEB-INF/Edit/EditTournament.jsp").forward(request, response);
            
            } else if ("add".equals(action)) {
            
            	LinkedList<Association> associations = ctrl.getAllAssociations();
            	
				if (associations.size() > 0) {
        		
					request.setAttribute("associationsList", associations);
					request.getRequestDispatcher("WEB-INF/Add/AddTournament.jsp").forward(request, response);
				
				} else {
					
					request.setAttribute("errorMessage", "Debe agregar una asociación primero");
					request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
				}
            
            } else {
            
            	LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
                request.setAttribute("tournamentsList", tournaments);
                
                LinkedList<Association> associations = ctrl.getAllAssociations();
            	request.setAttribute("associationsList", associations);
                
            	request.getRequestDispatcher("/WEB-INF/Management/TournamentManagement.jsp").forward(request, response);
            
            }
        	
        } catch(SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
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
                
            	Tournament tournament = buildTournamentFromRequest(request, action, ctrl);
            	if (checkDates(tournament.getStartDate(), tournament.getEndDate())) {
            		ctrl.addTournament(tournament);
            	} else {
            		request.setAttribute("errorMessage", "Error en las fechas introducidas (el torneo debe empezar a partir de hoy y durar, al menos, 4 meses)");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	}

            } else if ("edit".equals(action)) {
                
            	Tournament tournament = buildTournamentFromRequest(request, action, ctrl);
            	if (checkDates(tournament.getStartDate(), tournament.getEndDate())) {
            		ctrl.updateTournament(tournament);
            	} else {
            		request.setAttribute("errorMessage", "Error en las fechas introducidas (el torneo debe empezar a partir de hoy y durar, al menos, 4 meses)");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	}

            } else if ("delete".equals(action)) {
                
            	ctrl.deleteTournament(Integer.parseInt(request.getParameter("id")));
                
            }

            LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
            request.setAttribute("tournamentsList", tournaments);
            request.getRequestDispatcher("WEB-INF/Management/TournamentManagement.jsp").forward(request, response);
        	
        } catch(SQLException e) {
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        }
    
	}

}