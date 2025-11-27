package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import entities.Match;
import entities.Player;
import entities.Tournament;
import logic.Logic;

@WebServlet("/actionmatch")
public class ActionMatch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Match buildMatchFromRequest(HttpServletRequest request, String action, Logic ctrl) throws SQLException {

	    Match match = new Match();
	    
	    if ("edit".equals(action))
	        match.setId(Integer.parseInt(request.getParameter("id")));

	    int idHome = Integer.parseInt(request.getParameter("id_home"));
        int idAway = Integer.parseInt(request.getParameter("id_away"));
        int idTournament = Integer.parseInt(request.getParameter("id_tournament"));

        Club home = ctrl.getClubById(idHome);
        Club away = ctrl.getClubById(idAway);
        Tournament tournament = ctrl.getTournamentById(idTournament);

        match.setHome(home);
        match.setAway(away);
        match.setTournament(tournament);

	    match.setHomeGoals(parseNullableInteger(request.getParameter("home_goals")));
	    match.setAwayGoals(parseNullableInteger(request.getParameter("away_goals")));
	    match.setMatchday(parseNullableInteger(request.getParameter("matchday")));

	    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	    match.setDate(LocalDateTime.parse(request.getParameter("date"), fmt));

	    return match;
	}

	private Integer parseNullableInteger(String val) {
	    
		return (val == null || val.isEmpty()) ? null : Integer.parseInt(val);
	
	}
	
	private Integer parseId(String param) {
	    
		if (param != null && !param.isEmpty()) {
	    
			try {
	        
				return Integer.parseInt(param);
	        
			} catch (NumberFormatException e) {
	        
				return null;
	        
			}
	    
		}
	    
		return null;
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");
		Logic ctrl = new Logic();

		try {

			if ("edit".equals(action)) {

				Match match = ctrl.getMatchById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("match", match);

				request.setAttribute("clubs", ctrl.getAllClubs());
				request.setAttribute("tournaments", ctrl.getAllTournaments());

				request.getRequestDispatcher("WEB-INF/Edit/EditMatch.jsp").forward(request, response);

			} else if ("add".equals(action)) {

				request.setAttribute("clubs", ctrl.getAllClubs());
				request.setAttribute("tournaments", ctrl.getAllTournaments());

				request.getRequestDispatcher("WEB-INF/Add/AddMatch.jsp").forward(request, response);

			} else {
				
				Integer clubId = parseId(request.getParameter("clubId"));
	            Integer tournamentId = parseId(request.getParameter("tournamentId"));
	            
	            LinkedList<Match> matches;

	            if (clubId != null && tournamentId != null) {

	            	matches = ctrl.getMatchesByClubAndTournamentId(clubId, tournamentId);
	            
	            } else if (clubId != null) {
	            
	            	matches = ctrl.getMatchesByClubId(clubId);
	            
	            } else if (tournamentId != null) {
	            
	            	matches = ctrl.getMatchesByTournamentId(tournamentId);
	            
	            } else {
	            
	            	matches = ctrl.getAllMatches(); 
	            
	            }
	            
	            LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
	            
	            LinkedList<Club> clubs;
	            if (tournamentId != null) {

	            	clubs = ctrl.getAllClubsByTournamentId(tournamentId);
	            
	            } else {
	            
	                clubs = ctrl.getAllClubs(); 
	            
	            }

	            request.setAttribute("matchList", matches);
	            request.setAttribute("tournamentsList", tournaments);
	            request.setAttribute("clubsList", clubs);
	            
	            request.setAttribute("selectedClubId", clubId);
	            request.setAttribute("selectedTournamentId", tournamentId);
	            
	            request.getRequestDispatcher("/WEB-INF/Management/MatchManagement.jsp").forward(request, response);
			
			}

		} catch (SQLException e) {

			request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String action = request.getParameter("action");
		Logic ctrl = new Logic();

		try {

			if ("add".equals(action)) {

				Match match = buildMatchFromRequest(request, action, ctrl);
				ctrl.addMatch(match);

			} else if ("edit".equals(action)) {

				Match match = buildMatchFromRequest(request, action, ctrl);
				ctrl.updateMatch(match);

			} else if ("delete".equals(action)) {

				Integer id = Integer.parseInt(request.getParameter("id"));
				ctrl.deleteMatch(id);

			}

			LinkedList<Match> matches = ctrl.getAllMatches();
			request.setAttribute("matchList", matches);
			request.getRequestDispatcher("WEB-INF/Management/MatchManagement.jsp").forward(request, response);

		} catch (SQLException e) {

			request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
		}

	}
}
