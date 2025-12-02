package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import enums.*;
import logic.Logic;

@WebServlet("/actiontournament")
public class ActionTournament extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private Tournament buildTournamentFromRequest(HttpServletRequest request, String action, Logic ctrl) throws SQLException {
        
		Tournament tournament = new Tournament();
		if ("edit".equals(action)) tournament.setId(Integer.parseInt(request.getParameter("id")));
		tournament.setName(request.getParameter("name"));
		tournament.setStartDate(LocalDate.parse(request.getParameter("startDate")));
		tournament.setFormat(TournamentFormat.valueOf(request.getParameter("format")));
		tournament.setSeason(request.getParameter("season"));
        tournament.setAssociation(ctrl.getAssociationById(Integer.parseInt(request.getParameter("id_association"))));
		
        return tournament;
    
	}
	
	private boolean checkStartDate(LocalDate startDate) {
		
		 LocalDate today = LocalDate.now();

		    if (startDate.isBefore(today)) {
		        
		    	return false;
		    
		    }

		    return true;
		    
	}
	
	private boolean checkMatches(Integer id, Logic ctrl) throws SQLException {
			
		return ctrl.getMatchesByTournamentId(id).isEmpty();
	
	}
	
	private int calculateHour(int matchIndex, int[] allowedHours) {

		return allowedHours[matchIndex % allowedHours.length];
	
	}
	
	private LocalDate adjustStartDateToFriday(LocalDate inputDate) {

	    LocalDate fridayOfThatWeek = inputDate.with(DayOfWeek.FRIDAY);
	    
	    if (fridayOfThatWeek.isBefore(LocalDate.now())) {
	        
	    	return fridayOfThatWeek.plusWeeks(1);
	    
	    }
	    
	    return fridayOfThatWeek;
	    
	}
	
	private LinkedList<Match> generateZonalPhase(LinkedList<Club> allClubs, LocalDate startDate, Logic ctrl) throws SQLException {
	    
		LinkedList<Match> allMatches = new LinkedList<>();

	    HashMap<Integer, Club> classicRivalries = ctrl.getClassicRivalsMap();
	    
	    LinkedList<Club> zoneA = new LinkedList<>();
	    LinkedList<Club> zoneB = new LinkedList<>();
	    
	    HashMap<Integer, Club> clubsMap = new HashMap<>();
	    for (Club c : allClubs) clubsMap.put(c.getId(), c);
	    
	    HashSet<Integer> assignedIds = new HashSet<>();

	    for (Club c : allClubs) {

	        if (assignedIds.contains(c.getId())) continue;

	        if (classicRivalries.containsKey(c.getId())) {
	            
	        	Club classicRivalObj = classicRivalries.get(c.getId());
	            int classicRivalId = classicRivalObj.getId();
	        	            
	            if (clubsMap.containsKey(classicRivalId)) {
	                
	            	Club classicRival = clubsMap.get(classicRivalId);
	                
	                zoneA.add(c);
	                zoneB.add(classicRival);
	                
	                assignedIds.add(c.getId());
	                assignedIds.add(classicRivalId);
	                
	            }
	        
	        }
	    
	    }
	    
	    LinkedList<Club> remaining = new LinkedList<>();
	    
	    for (Club c : allClubs) {
	        
	    	if (!assignedIds.contains(c.getId())) {
	        
	    		remaining.add(c);
	        
	    	}
	    
	    }
	    
	    Collections.shuffle(remaining);
	    
	    for (Club c : remaining) {

	    	if (zoneA.size() <= zoneB.size()) {
	        
	    		zoneA.add(c);
	        
	    	} else {
	        
	    		zoneB.add(c);
	        
	    	}
	    
	    }
	        
	    LinkedList<Match> matchesA = generateFirstLeg(zoneA, startDate);
	    LinkedList<Match> matchesB = generateFirstLeg(zoneB, startDate);
	    
	    allMatches.addAll(matchesA);
	    allMatches.addAll(matchesB);
	    
	    allMatches.sort(Comparator.comparing(Match::getDate));

	    return allMatches;
	
	}
	
	private LinkedList<Match> generateFirstLeg(LinkedList<Club> clubs, LocalDate startDate) {
		
		LinkedList<Match> fixture = new LinkedList<>();
		
		int[] fridayHours = {22, 20, 18, 16}; 
	    int[] weekendHours = {22, 20, 18, 16, 14};

		LinkedList<Club> rotated = new LinkedList<>(clubs);
		
		int n = rotated.size();
			
		if (n % 2 != 0) {
		    
			rotated.add(null);
		    n++;
		
		}
		
		int totalMatchdays = n - 1;
		int matchesPerMatchday = n / 2;
		
	    int quotaFriday = (int) Math.round(matchesPerMatchday * (2.0 / 8.0));
	    int quotaSaturday = (int) Math.round(matchesPerMatchday * (3.0 / 8.0));
	    if (quotaFriday == 0 && matchesPerMatchday >= 2) quotaFriday = 1;
			
	    for (int day = 1; day <= totalMatchdays; day++) {
	    	
	    	LocalDate currentFriday = startDate.plusWeeks(day - 1);
			
	    	int matchesScheduledReal = 0;
			
		    for (int i = 0; i < matchesPerMatchday; i++) {
		
		        Club home = rotated.get(i);
		        Club away = rotated.get(n - 1 - i);
		
		        if (home == null || away == null) continue;
		
		        if (day % 2 == 0) {
		            
		        	Club temp = home;
		            home = away;
		            away = temp;
		        
		        }
		        
		        LocalDateTime matchDateTime;
		        int assignedHour;
		        
		        if (matchesScheduledReal < quotaFriday) {
	                
		        	assignedHour = calculateHour(matchesScheduledReal, fridayHours);
		        	matchDateTime = currentFriday.atTime(assignedHour, 0);
	                
	            } else if (matchesScheduledReal < (quotaFriday + quotaSaturday)) {

	            	int indexOnSaturday = matchesScheduledReal - quotaFriday;
	            	assignedHour = calculateHour(indexOnSaturday, weekendHours);
	                matchDateTime = currentFriday.plusDays(1).atTime(assignedHour, 0);
	                
	            } else {

	            	int indexOnSunday = matchesScheduledReal - (quotaFriday + quotaSaturday);
	                assignedHour = calculateHour(indexOnSunday, weekendHours);
	                matchDateTime = currentFriday.plusDays(2).atTime(assignedHour, 0);
	                
	            }
		        
		        Match match = new Match();
		        
		        match.setAway(away);
		        match.setHome(home);
		        match.setDate(matchDateTime);
		        match.setMatchday(day);
		        
		        fixture.add(match);

		        matchesScheduledReal++;
		    
		    }
		
		    rotated.add(1, rotated.remove(rotated.size() - 1));
		
		}
	    
	    fixture.sort(Comparator.comparing(Match::getDate));
		
		return fixture;
	
	}
	
	private LinkedList<Match> generateSecondLeg(LinkedList<Match> firstLegMatches, int numberOfClubs) {
	    
		LinkedList<Match> secondLegMatches = new LinkedList<>();
	       
		int totalMatchdaysFirstLeg = (numberOfClubs % 2 == 0) ? (numberOfClubs - 1) : numberOfClubs;
		
	    for (Match m : firstLegMatches) {
	        
	    	Match returnMatch = new Match();
	        
	        returnMatch.setHome(m.getAway());
	        returnMatch.setAway(m.getHome());
	        
	        returnMatch.setDate(m.getDate().plusWeeks(totalMatchdaysFirstLeg));
	        
	        returnMatch.setMatchday(m.getMatchday() + totalMatchdaysFirstLeg);
	        
	        secondLegMatches.add(returnMatch);
	        
	    }
	    
	    secondLegMatches.sort(Comparator.comparing(Match::getDate));
	    
	    return secondLegMatches;
	    
	}
	
	private LinkedList<Match> drawTournamentMatchdays(LinkedList<Club> clubs, Tournament tournament, Logic ctrl) throws SQLException {
		
		LinkedList<Match> matches = new LinkedList<>();
		
		switch (tournament.getFormat()) {
		
			case ZONAL_ELIMINATION:
			
				matches = generateZonalPhase(clubs, tournament.getStartDate(), ctrl);
				break;
		
			case ROUND_ROBIN_ONE_LEG:
			
				matches = generateFirstLeg(clubs, tournament.getStartDate());
				break;
			
			case ROUND_ROBIN_TWO_LEGS:
			
	            LinkedList<Match> firstLeg = generateFirstLeg(clubs, tournament.getStartDate());
	            matches.addAll(firstLeg);
	            
	            LinkedList<Match> secondLeg = generateSecondLeg(firstLeg, clubs.size());
	            matches.addAll(secondLeg);
	            break;
		
			case WORLD_CUP:
				
				break;
		
		}
		
		return matches;
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String action = request.getParameter("action");
	    Logic ctrl = new Logic();

	    try {
	    
	    	if ("add".equals(action)) {

	    		LinkedList<Association> associations = ctrl.getAllAssociations();
				
            	if (associations.size() > 0) {
        		
					associations.sort(Comparator.comparing(Association::getName));
					request.setAttribute("associationsList", associations);
					
					HashMap<Integer, LinkedList<Club>> clubsMap = new HashMap<>();
					
					for (Association a : associations) {

				        LinkedList<Club> associationClubs = ctrl.getClubsByAssociationId(a.getId());
				        clubsMap.put(a.getId(), associationClubs);
				        
				    }
					
					if (clubsMap.size() > 0) {
		        		
						request.setAttribute("clubsMap", clubsMap);
						request.getRequestDispatcher("WEB-INF/Add/AddTournament.jsp").forward(request, response);
					
					} else {
						
						request.setAttribute("errorMessage", "Debe agregar clubes primero");
						request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
						
					}
					
				} else {
					
					request.setAttribute("errorMessage", "Debés agregar una asociación primero");
					request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
				}
	    		
	        } else {
	            
	        	LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
            	tournaments.sort(Comparator.comparing(Tournament::getName));
                request.setAttribute("tournamentsList", tournaments);
                
                LinkedList<Association> associations = ctrl.getAllAssociations();
            	request.setAttribute("associationsList", associations);
                
            	request.getRequestDispatcher("/WEB-INF/Management/TournamentManagement.jsp").forward(request, response);
	        
	        }

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
                
            	Tournament tournament = buildTournamentFromRequest(request, action, ctrl);
            	
            	if (checkStartDate(tournament.getStartDate())) {
            		
            		LocalDate realStartDate = adjustStartDateToFriday(tournament.getStartDate());
            		tournament.setStartDate(realStartDate);
            		
					LinkedList<Match> fixture = new LinkedList<>();
					
					LinkedList<Club> clubs = new LinkedList<>();
					String[] selectedClubs = request.getParameterValues("selectedClubs");
					
					if (selectedClubs != null) {
						
						for (String clubIdStr : selectedClubs) {
							
							try {
							
								int clubId = Integer.parseInt(clubIdStr);
								Club club = ctrl.getClubById(clubId);
								clubs.add(club);
							
							} catch (NumberFormatException e) {
							
								System.err.println("ID de posición inválido: " + clubIdStr);
						
							}
					
						}
					
					}
					
					fixture = drawTournamentMatchdays(clubs, tournament, ctrl);

					LocalDate endDate;

					if (tournament.getFormat() == TournamentFormat.ZONAL_ELIMINATION) {

                    	endDate = fixture.getLast().getDate().toLocalDate().plusWeeks(4);
                    
                    } else {

                    	endDate = fixture.getLast().getDate().toLocalDate();
                    
                    }
					
					tournament.setEndDate(endDate);

					ctrl.addTournament(tournament);
					
					for (Match match : fixture) {
            			
						match.setTournament(tournament);
            			ctrl.addMatch(match);
            		
					}
            	
	        	} else {
							
					request.setAttribute("errorMessage", "Error en las fechas introducidas (el torneo debe empezar a partir de hoy y durar, al menos, 4 meses)");
					request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
	        	}

            } else if ("delete".equals(action)) {
                
            	Integer id = Integer.parseInt(request.getParameter("id"));

            	if (checkMatches(id, ctrl)) {

            		ctrl.deleteTournament(id);

            	} else {

            		request.setAttribute("errorMessage", "No se puede eliminar un torneo que tiene partidos organizados");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
                
            }

            LinkedList<Tournament> tournaments = ctrl.getAllTournaments();
            tournaments.sort(Comparator.comparing(Tournament::getName));
            request.setAttribute("tournamentsList", tournaments);
            request.getRequestDispatcher("WEB-INF/Management/TournamentManagement.jsp").forward(request, response);
        	
        } catch(SQLException e) {
        	
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        
        }
    
	}

}