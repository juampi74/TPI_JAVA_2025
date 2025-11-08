package servlet;

import java.io.IOException;
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
	
	private Tournament buildTournamentFromRequest(HttpServletRequest request, String action, Logic ctrl) {
        
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
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("edit".equals(action)) {
        	
        	Tournament tournament = ctrl.getTournamentById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("tournament", tournament);
            
            LinkedList<Association> associations = ctrl.getAllAssociations();
        	request.setAttribute("associationsList", associations);
            
        	request.getRequestDispatcher("WEB-INF/EditTournament.jsp").forward(request, response);
        
        } else if ("add".equals(action)) {
        
        	LinkedList<Association> associations = ctrl.getAllAssociations();
        	request.setAttribute("associationsList", associations);
        	
        	request.getRequestDispatcher("WEB-INF/AddTournament.jsp").forward(request, response);
        
        } else {
        
        	LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
            request.setAttribute("tournamentsList", tournaments);
            
            LinkedList<Association> associations = ctrl.getAllAssociations();
        	request.setAttribute("associationsList", associations);
            
        	request.getRequestDispatcher("/WEB-INF/TournamentManagement.jsp").forward(request, response);
        
        }
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("add".equals(action)) {
            
        	ctrl.addTournament(buildTournamentFromRequest(request, action, ctrl));

        } else if ("edit".equals(action)) {
            
        	ctrl.updateTournament(buildTournamentFromRequest(request, action, ctrl));

        } else if ("delete".equals(action)) {
            
        	ctrl.deleteTournament(Integer.parseInt(request.getParameter("id")));
            
        }

        LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
        request.setAttribute("tournamentsList", tournaments);
        request.getRequestDispatcher("WEB-INF/TournamentManagement.jsp").forward(request, response);
    
	}

}