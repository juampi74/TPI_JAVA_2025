package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Tournament;
import entities.Association;
import entities.Club;
import entities.Stadium;
import logic.Logic;

@WebServlet("/actiontournament")
public class ActionTournament extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("edit".equals(action)) {
            
        	Tournament t = new Tournament();
            t.setId(Integer.parseInt(request.getParameter("id")));
            Tournament tournament = ctrl.getTournamentById(t);
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
            
        	Tournament t = new Tournament();
            t.setName(request.getParameter("name"));
            t.setStartDate(LocalDate.parse(request.getParameter("startnDate")));
            t.setEndDate(LocalDate.parse(request.getParameter("endDate")));
            t.setFormat(request.getParameter("format"));
            t.setSeason(request.getParameter("season"));
            
            Association tournamentAssociation = new Association();
            tournamentAssociation.setId(Integer.parseInt(request.getParameter("id_association")));
            t.setAssociation(ctrl.getAssociationById(tournamentAssociation));

            ctrl.addTournament(t);

        } else if ("edit".equals(action)) {
            
        	Tournament t = new Tournament();
            t.setName(request.getParameter("name"));
            t.setStartDate(LocalDate.parse(request.getParameter("startnDate")));
            t.setEndDate(LocalDate.parse(request.getParameter("endDate")));
            t.setFormat(request.getParameter("format"));
            t.setSeason(request.getParameter("season"));
            
            Association tournamentAssociation = new Association();
            tournamentAssociation.setId(Integer.parseInt(request.getParameter("id_association")));
            t.setAssociation(ctrl.getAssociationById(tournamentAssociation));

            ctrl.updateTournament(t);

        } else if ("delete".equals(action)) {
            
        	Tournament t = new Tournament();
            t.setId(Integer.parseInt(request.getParameter("id")));
            ctrl.deleteTournament(t);
            
        }

        LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
        request.setAttribute("tournamentsList", tournaments);
        request.getRequestDispatcher("WEB-INF/TournamentManagement.jsp").forward(request, response);
    
	}

}
