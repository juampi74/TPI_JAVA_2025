package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import entities.*;
import logic.Logic;

@WebServlet({"/home", "/HOME", "/Home"})
public class Home extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
        	Logic ctrl = new Logic();
        	
        	HttpSession session = request.getSession(false);
        	User userLogged = null;

        	if (session != null) {
        	    userLogged = (User) session.getAttribute("user");
        	}
        	LinkedList<Tournament> tournaments = new LinkedList<>();
            Club club = null;
            Contract contract = null;
    	    if (userLogged != null) {
    	    	switch (userLogged.getRole()) {

                case COACH:
                	club = ctrl.getClubByPersonId(userLogged.getPerson().getId());
                    if (club != null) {
                        Match nextMatchClub = ctrl.getNextMatchClub(club.getId());
                        tournaments = ctrl.getTournamentsByClubId(club.getId());
                        request.setAttribute("nextMatch", nextMatchClub);
                    } else {
                    	tournaments = ctrl.getAllTournaments();
                        Match nextMatch = ctrl.getNextMatch();                
                        request.setAttribute("nextMatch", nextMatch);
                    }
                    
                    break;

                case ADMIN:
                    contract = ctrl.getNextExpiringContract();
                    club = ctrl.getClubWithMostContracts();
                    tournaments = ctrl.getAllTournaments();
                    request.setAttribute("nextExpiringContract", contract);
                    break;

                case PRESIDENT:
                	club = ctrl.getClubByPersonId(userLogged.getPerson().getId());
                	if (club != null) {
                		contract = ctrl.getNextExpiringContractByClub(club.getId());
                	}
                    tournaments = ctrl.getAllTournaments();
                    request.setAttribute("nextExpiringContract", contract);
                    break;

                case PLAYER:
                    club = ctrl.getClubByPersonId(userLogged.getPerson().getId());
                    if (club != null) {
                        Match nextMatchClub = ctrl.getNextMatchClub(club.getId());
                        tournaments = ctrl.getTournamentsByClubId(club.getId());
                        request.setAttribute("nextMatch", nextMatchClub);
                    } else {
                    	tournaments = ctrl.getAllTournaments();
                        Match nextMatch = ctrl.getNextMatch();                
                        request.setAttribute("nextMatch", nextMatch);
                    }
                    
                    break;
            }
    	    
    	} else {
    		club = ctrl.getClubWithMostContracts();
            tournaments = ctrl.getAllTournaments();
            Match nextMatch = ctrl.getNextMatch();                
            
            request.setAttribute("nextMatch", nextMatch);
	    }
    	    request.setAttribute("club", club);
            request.setAttribute("tournaments", tournaments);
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);

        } catch (SQLException e) {
            
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        
        }

    }

}