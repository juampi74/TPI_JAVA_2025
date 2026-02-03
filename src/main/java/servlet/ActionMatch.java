package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import entities.Match;
import entities.Tournament;
import enums.TournamentStage;
import logic.Logic;

@WebServlet("/actionmatch")
public class ActionMatch extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	private Match buildMatchFromRequest(HttpServletRequest request, String action, Logic ctrl) throws SQLException {

	    Match match = new Match();
	    
	    if ("edit".equals(action)) match.setId(Integer.parseInt(request.getParameter("id")));

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

	private Integer parseNullableInteger(String value) {
	    
		return (value == null || value.trim().isEmpty()) ? null : Integer.parseInt(value);
	
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

		Logic ctrl = new Logic();

		try {

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
            
            Map<Integer, TournamentStage> maxStageMap = new HashMap<>();

            if (tournamentId != null) {
            
            	TournamentStage realMaxStage = ctrl.getHighestStageByTournamentId(tournamentId);
                maxStageMap.put(tournamentId, realMaxStage);
            
            } else {
                
            	for (Match m : matches) {
                
            		int tId = m.getTournament().getId();
                    
            		TournamentStage currentStage = m.getStage();
                    
            		if (!maxStageMap.containsKey(tId) || currentStage.ordinal() > maxStageMap.get(tId).ordinal()) {
                    
            			maxStageMap.put(tId, currentStage);
                    
            		}
                
            	}
            
            }
            
            for (Match m : matches) {
                
            	int tId = m.getTournament().getId();
                
            	if (maxStageMap.containsKey(tId)) {
                
            		m.getTournament().setHighestStage(maxStageMap.get(tId));
                
            	}
            
            }

            request.setAttribute("matchList", matches);           
            request.setAttribute("tournamentsList", tournaments);
            request.setAttribute("clubsList", clubs);
            
            request.setAttribute("selectedClubId", clubId);
            request.setAttribute("selectedTournamentId", tournamentId);
            
            request.getRequestDispatcher("/WEB-INF/Management/MatchManagement.jsp").forward(request, response);

		} catch (SQLException e) {

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

				Match match = buildMatchFromRequest(request, action, ctrl);
				ctrl.addMatch(match);

			} else if ("setResult".equals(action)) {

				Match match = new Match();
				
				match.setId(Integer.parseInt(request.getParameter("id")));
				match.setHomeGoals(Integer.parseInt(request.getParameter("homeGoals")));
				match.setAwayGoals(Integer.parseInt(request.getParameter("awayGoals")));
				
				String hpParam = request.getParameter("homePenalties");
			    String apParam = request.getParameter("awayPenalties");
			    
			    if (hpParam != null && !hpParam.isEmpty() && apParam != null && !apParam.isEmpty()) {
			        
			    	match.setHomePenalties(Integer.parseInt(hpParam));
			        match.setAwayPenalties(Integer.parseInt(apParam));
			    
			    } else {

			    	match.setHomePenalties(null);
			        match.setAwayPenalties(null);
			    
			    }
				
				ctrl.setMatchResult(match);
						    
			    StringBuilder redirectUrl = new StringBuilder("actionmatch");		    
		        
			    String tId = request.getParameter("tournamentId");
			    String cId = request.getParameter("clubId");
			    
			    if (tId == null) tId = "";
			    if (cId == null) cId = "";
			    
		    	redirectUrl.append("?tournamentId=").append(tId)
		    			   .append("&clubId=").append(cId);
	            
	            response.sendRedirect(redirectUrl.toString());
	            return;

			}

            response.sendRedirect("actionmatch");

		} catch (SQLException e) {

			request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
		
		}

	}

}