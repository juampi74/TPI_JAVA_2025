package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import entities.Player;
import enums.PersonRole;
import logic.Logic;

@WebServlet("/actionplayer")
public class ActionPlayer extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private Player buildPlayerFromRequest(HttpServletRequest request) {
        
    	Player player = new Player();
    	player.setId(Integer.parseInt(request.getParameter("id")));
    	player.setFullname(request.getParameter("fullname"));
    	player.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
    	player.setAddress(request.getParameter("address"));
    	player.setRole(PersonRole.valueOf("PLAYER"));
        player.setDominantFoot(request.getParameter("dominantFoot"));
        player.setJerseyNumber(Integer.parseInt(request.getParameter("jerseyNumber")));
        player.setHeight(Double.parseDouble(request.getParameter("height")));
        player.setWeight(Double.parseDouble(request.getParameter("weight")));

        return player;
    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			Player player = ctrl.getPlayerById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("player", player);
			request.getRequestDispatcher("WEB-INF/EditPlayer.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/AddPlayer.jsp").forward(request, response);
		
		} else {
			String clubIdParam = request.getParameter("clubId");
			LinkedList<Player> players;
			if (clubIdParam != null && !clubIdParam.isEmpty()) {
		        int clubId = Integer.parseInt(clubIdParam);
		        players = ctrl.getPlayersByClub(clubId);
		    } else {
		        players = ctrl.getAllPlayers();
		    }

		    request.setAttribute("playersList", players);
		    
		    LinkedList<Club> clubs = ctrl.getAllClubs();
		    request.setAttribute("clubsList", clubs);
		    
		    request.getRequestDispatcher("/WEB-INF/PlayerManagement.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	ctrl.addPlayer(buildPlayerFromRequest(request));
        	
        } else if ("edit".equals(action)) {
        	
        	ctrl.updatePlayer(buildPlayerFromRequest(request));
    	    
        } else if ("delete".equals(action)){
        	
    	    ctrl.deletePlayer(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<Player> players = ctrl.getAllPlayers();
		request.setAttribute("playersList", players);
		
		LinkedList<Club> clubs = ctrl.getAllClubs();
		request.setAttribute("clubsList", clubs);
		
	    request.getRequestDispatcher("WEB-INF/PlayerManagement.jsp").forward(request, response);
	    
	}

}