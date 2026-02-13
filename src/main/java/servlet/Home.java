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
        	    System.out.print(userLogged);
        	    if (userLogged != null) {
	        	    if (userLogged.getRole().name().equals("COACH")) {
	        	    	Map<Integer, Club> clubsMap = ctrl.getCoachesCurrentClubs();
	                    Club coachClub = clubsMap.get(userLogged.getPerson().getId());
	                    if (coachClub != null) {
	                    	Match nextMatchClub = ctrl.getNextMatchClub(coachClub.getId());
	                    	LinkedList<Tournament> tournaments = ctrl.getTournamentsByClubId(coachClub.getId());
	                    	
	                        request.setAttribute("tournaments", tournaments);
	                    	request.setAttribute("club", coachClub);
	                    	request.setAttribute("nextMatchClub", nextMatchClub);
	                    }
	        	    } else if (userLogged.getRole().name().equals("PLAYER")) {
	        	    	
	        	    } else if (userLogged.getRole().name().equals("PRESIDENT")) {
	        	    	Contract contract = ctrl.getNextExpiringContract();
	        	    	request.setAttribute("nextExpiringContract", contract);
	        	    	
	        	    } else if (userLogged.getRole().name().equals("ADMIN")) {
	        	    	Contract contract = ctrl.getNextExpiringContract();
	        	    	Club club = ctrl.getClubWithMostContracts();
	        	    	LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
	        	    	
	                    request.setAttribute("tournaments", tournaments);
	        	    	request.setAttribute("club", club);
	        	    	request.setAttribute("nextExpiringContract", contract);
	        	    }
        	    
        	} else {
        		Club club = ctrl.getClubWithMostContracts();
                LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
                Match nextMatch = ctrl.getNextMatch();                
                request.setAttribute("club", club);
                request.setAttribute("tournaments", tournaments);
                request.setAttribute("nextMatch", nextMatch);
    	    }}
   
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);

        } catch (SQLException e) {
            
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        
        }

    }

}