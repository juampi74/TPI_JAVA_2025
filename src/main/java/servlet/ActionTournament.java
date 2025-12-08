package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.DayOfWeek;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
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
	
	private int calculateHour(int matchIndex, LinkedList<Integer> allowedHours) {

		return allowedHours.get(matchIndex % allowedHours.size());
	
	}
	
	private LocalDate adjustStartDateToFriday(LocalDate inputDate) {

	    LocalDate fridayOfThatWeek = inputDate.with(DayOfWeek.FRIDAY);
	    
	    if (fridayOfThatWeek.isBefore(LocalDate.now())) {
	        
	    	return fridayOfThatWeek.plusWeeks(1);
	    
	    }
	    
	    return fridayOfThatWeek;
	    
	}
	
	private void redistributeMatchesByMatchday(LinkedList<Match> allMatches, LocalDate startDate, boolean isWorldCupFormat) {

	    HashMap<Integer, LinkedList<Match>> matchesByDay = new HashMap<>();
	    
	    for (Match m : allMatches) {
	        
	    	matchesByDay.computeIfAbsent(m.getMatchday(), k -> new LinkedList<>()).add(m);
	    
	    }
	    
	    LinkedList<Integer> fridayHours = new LinkedList<Integer>(List.of(22, 20, 18, 16)); 
	    LinkedList<Integer> weekendHours = new LinkedList<Integer>(List.of(22, 20, 18, 16, 14));
	    
	    if (isWorldCupFormat) {
	        
	    	Collections.reverse(fridayHours);
	    	fridayHours.add(22);
	        
	    	Collections.reverse(weekendHours);
	        weekendHours.add(22);
	    
	    }

	    for (Map.Entry<Integer, LinkedList<Match>> entry : matchesByDay.entrySet()) {
	        
	    	int matchdayNum = entry.getKey();
	        LinkedList<Match> matchesOfThisDate = entry.getValue();
	        
	        if (isWorldCupFormat) {

	        	matchesOfThisDate.sort(Comparator.comparing(Match::getGroupName));
	        
	        } else {

	            Collections.shuffle(matchesOfThisDate);

	        }
	        
	        int totalMatches = matchesOfThisDate.size();
	        
	        int quotaFriday = (int) Math.round(totalMatches * 0.25);
	        if (quotaFriday == 0 && totalMatches >= 2) quotaFriday = 1;
	        
	        int quotaSaturday = (int) Math.round(totalMatches * 0.375);
	        
	        LocalDate fridayOfMatchday = startDate.plusWeeks(matchdayNum - 1);
	        
	        for (int i = 0; i < totalMatches; i++) {
	            
	        	Match m = matchesOfThisDate.get(i);
	            LocalDateTime finalDate;
	            
	            if (i < quotaFriday) {

	                int hour = calculateHour(i, fridayHours);
	                finalDate = fridayOfMatchday.atTime(hour, 0);
	                
	            } else if (i < (quotaFriday + quotaSaturday)) {

	                int idxSat = i - quotaFriday;
	                int hour = calculateHour(idxSat, weekendHours);
	                finalDate = fridayOfMatchday.plusDays(1).atTime(hour, 0);
	                
	            } else {

	            	int idxSun = i - (quotaFriday + quotaSaturday);
	                int hour = calculateHour(idxSun, weekendHours);
	                finalDate = fridayOfMatchday.plusDays(2).atTime(hour, 0);
	            }
	            
	            m.setDate(finalDate);
	        
	        }
	    
	    }
	
	}
	
	private LinkedList<Match> generateInterzonalMatches(LinkedList<Club> zoneA, LinkedList<Club> zoneB, int matchday, HashMap<Integer, Club> classicRivalries) {
		
		LinkedList<Match> interzonalMatches = new LinkedList<>();
		
		HashSet<Integer> assignedA = new HashSet<>();
		HashSet<Integer> assignedB = new HashSet<>();
		
		for (Club cA : zoneA) {
			
			if (classicRivalries.containsKey(cA.getId())) {
				
				int classicRivalId = classicRivalries.get(cA.getId()).getId();
				
				for (Club cB : zoneB) {
					
					if (cB.getId() == classicRivalId && !assignedB.contains(cB.getId())) {

						Match m = new Match();
						
						if (Math.random() < 0.5) {
	                        
							m.setHome(cA);
	                        m.setAway(cB);
	                    
						} else {
	                    
							m.setHome(cB);
	                        m.setAway(cA);
	                    
						}
						
						m.setMatchday(matchday);
						
						m.setStage(TournamentStage.GROUP_STAGE);
						m.setGroupName("Interzonal");
						
						interzonalMatches.add(m);
						
						assignedA.add(cA.getId());
						assignedB.add(cB.getId());
						
						break;
					
					}
				
				}
			
			}
		
		}
		
		LinkedList<Club> remainingA = new LinkedList<>();
		for (Club c : zoneA) if (!assignedA.contains(c.getId())) remainingA.add(c);
		
		LinkedList<Club> remainingB = new LinkedList<>();
		for (Club c : zoneB) if (!assignedB.contains(c.getId())) remainingB.add(c);
		
		Collections.shuffle(remainingA);
		Collections.shuffle(remainingB);
		
		int count = Math.min(remainingA.size(), remainingB.size());

		for (int i = 0; i < count; i++) {
			
			Match m = new Match();
			
			m.setHome(remainingA.get(i));
			m.setAway(remainingB.get(i));
			
			m.setMatchday(matchday);
			
			m.setStage(TournamentStage.GROUP_STAGE);
			m.setGroupName("Interzonal");
			
			interzonalMatches.add(m);
		
		}
		
		return interzonalMatches;
	
	}
	
	private LinkedList<Match> generateZonalPhase(LinkedList<Club> allClubs, LocalDate startDate, Logic ctrl) throws SQLException {
	    
		if (allClubs.size() < 16 || allClubs.size() > 32 || allClubs.size() % 2 != 0) {
	        
			throw new SQLException("El formato ZONAL requiere una cantidad PAR de equipos, entre 16 y 32 en total. (Seleccionados: " + allClubs.size() + ")");
	    
		}
		
		
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
	    
	    int totalZonalDates = matchesA.getLast().getMatchday();
	    int interzonalDay = (totalZonalDates / 2) + 1;
	    
	    for (Match m : matchesA) {
	    	
	    	if (m.getMatchday() >= interzonalDay) {
	    		
	    		m.setMatchday(m.getMatchday() + 1);
	    	
	    	}
	        
	    	m.setStage(TournamentStage.GROUP_STAGE);
	        m.setGroupName("Zona A");
	        	    
	    }
	    
	    for (Match m : matchesB) {
	    	
	    	if (m.getMatchday() >= interzonalDay) {
	    		
	    		m.setMatchday(m.getMatchday() + 1);
	    	
	    	}
	    	
	    	m.setStage(TournamentStage.GROUP_STAGE);
	        m.setGroupName("Zona B");
	            
	    }
	    
	    LinkedList<Match> interzonalMatches = generateInterzonalMatches(zoneA, zoneB, interzonalDay, classicRivalries);
	    
	    allMatches.addAll(matchesA);
	    allMatches.addAll(matchesB);
	    
	    allMatches.addAll(interzonalMatches);
	    	    
	    return allMatches;
	
	}
	
	private LinkedList<Match> generateFirstLeg(LinkedList<Club> clubs, LocalDate startDate) {
		
		LinkedList<Match> fixture = new LinkedList<>();

		LinkedList<Club> rotated = new LinkedList<>(clubs);
		
		int n = rotated.size();
			
		if (n % 2 != 0) {
		    
			rotated.add(null);
		    n++;
		
		}
		
		int totalMatchdays = n - 1;
		int matchesPerMatchday = n / 2;
		
	    for (int day = 1; day <= totalMatchdays; day++) {
	    	
	    	LocalDate baseDate = startDate.plusWeeks(day - 1);
			
		    for (int i = 0; i < matchesPerMatchday; i++) {
		
		        Club home = rotated.get(i);
		        Club away = rotated.get(n - 1 - i);
		
		        if (home == null || away == null) continue;
		
		        if (day % 2 == 0) {
		            
		        	Club temp = home;
		            home = away;
		            away = temp;
		        
		        }
		        
		        Match match = new Match();
		        
		        match.setHome(home);
		        match.setAway(away);
		        
		        match.setStage(TournamentStage.GROUP_STAGE);
		        match.setMatchday(day);
		        
		        match.setDate(baseDate.atStartOfDay());
		        
		        fixture.add(match);
	    
		    }
		
		    rotated.add(1, rotated.remove(rotated.size() - 1));
		
		}
		
		return fixture;
	
	}
	
	private LinkedList<Match> generateSecondLeg(LinkedList<Match> firstLegMatches, int numberOfClubs) {
	    
		LinkedList<Match> secondLegMatches = new LinkedList<>();
	       
		int totalMatchdaysFirstLeg = (numberOfClubs % 2 == 0) ? (numberOfClubs - 1) : numberOfClubs;
		
	    for (Match m : firstLegMatches) {
	        
	    	Match returnMatch = new Match();
	        
	    	returnMatch.setHome(m.getAway());
	    	returnMatch.setAway(m.getHome());

	    	returnMatch.setStage(TournamentStage.GROUP_STAGE);
	    	returnMatch.setMatchday(m.getMatchday() + totalMatchdaysFirstLeg);

	    	returnMatch.setDate(m.getDate().plusWeeks(totalMatchdaysFirstLeg));
                   
	        secondLegMatches.add(returnMatch);
	        
	    }
	    	    
	    return secondLegMatches;
	    
	}
	
	private LinkedList<Match> generateWorldCupFixture(LinkedList<Club> allClubs, LocalDate startDate, Logic ctrl) throws SQLException {
	    
		if (allClubs.size() != 32) {
	        
			throw new SQLException("El formato MUNDIAL requiere exactamente 32 equipos seleccionados. (Seleccionados: " + allClubs.size() + ")");
	    
		}
		
		LinkedList<Match> allMatches = new LinkedList<>();
	    
	    List<LinkedList<Club>> groups = new ArrayList<>();
	    
	    for (int i = 0; i < 8; i++) groups.add(new LinkedList<>());

	    HashMap<Integer, Club> classicRivalries = ctrl.getClassicRivalsMap();
	    
	    Collections.shuffle(allClubs);

	    for (Club c : allClubs) {
	    	
	        boolean assigned = false;
	        
	        List<Integer> groupIndexes = new ArrayList<>();
	        
	        for (int i = 0; i < 8; i++) groupIndexes.add(i);
	        
	        groupIndexes.sort(Comparator.comparingInt(i -> groups.get(i).size()));

	        for (int i : groupIndexes) {
	            
	        	LinkedList<Club> currentGroup = groups.get(i);
	            
	            if (currentGroup.size() >= 4) continue;
	            
	            boolean conflict = false;

	            if (classicRivalries.containsKey(c.getId())) {
	                
	            	Club classicRivalObj = classicRivalries.get(c.getId());
	                int classicRivalId = classicRivalObj.getId();
	                
	                for (Club member : currentGroup) {
	                    
	                	if (member.getId() == classicRivalId) {
	                        
	                		conflict = true;
	                        break;
	                    
	                	}
	                
	                }
	            
	            }
	            
	            if (!conflict) {

	            	currentGroup.add(c);
	                assigned = true;
	                break;
	            
	            }
	        
	        }
	        
	        if (!assigned) {

	        	groups.get(groupIndexes.get(0)).add(c);
	        
	        }
	    
	    }
	    
	    char groupChar = 'A';

	    for (LinkedList<Club> group : groups) {
	        
	    	if (group.size() < 2) {
	    		
	    		groupChar++;
	    		continue;
	    	
	    	}
	        
	        LinkedList<Match> groupMatches = generateFirstLeg(group, startDate);
	        
	        String groupName = "Grupo " + groupChar;
	        
	        for (Match m : groupMatches) {
	        
	        	m.setStage(TournamentStage.GROUP_STAGE);
	            m.setGroupName(groupName);
	        
	        }
	        
	        allMatches.addAll(groupMatches);
	        groupChar++;
	    
	    }
	        
	    return allMatches;
	
	}
	
	private LinkedList<Match> drawTournamentMatchdays(LinkedList<Club> clubs, Tournament tournament, Logic ctrl) throws SQLException {
		
		LinkedList<Match> matches = new LinkedList<>();
		
		LinkedList<Club> shuffledClubs = new LinkedList<>(clubs);
	    Collections.shuffle(shuffledClubs);

	    boolean isWorldCup = false;
		
		switch (tournament.getFormat()) {
		
			case ZONAL_ELIMINATION:
			
				matches = generateZonalPhase(shuffledClubs, tournament.getStartDate(), ctrl);
				
				break;
		
			case ROUND_ROBIN_ONE_LEG:
			    
				if (shuffledClubs.size() < 8 || shuffledClubs.size() < 32) {
					
					throw new SQLException("El formato TODOS CONTRA TODOS requiere entre 8 y 32 equipos seleccionados. (Seleccionados: " + shuffledClubs.size() + ")");
					
				}
				
				matches = generateFirstLeg(shuffledClubs, tournament.getStartDate());
				
				break;
			
			case ROUND_ROBIN_TWO_LEGS:
				
				if (shuffledClubs.size() < 8 || shuffledClubs.size() < 32) {
					
					throw new SQLException("El formato TODOS CONTRA TODOS requiere entre 8 y 32 equipos seleccionados. (Seleccionados: " + shuffledClubs.size() + ")");
					
				}
					
	            LinkedList<Match> firstLeg = generateFirstLeg(shuffledClubs, tournament.getStartDate());
	            matches.addAll(firstLeg);
	            
	            LinkedList<Match> secondLeg = generateSecondLeg(firstLeg, shuffledClubs.size());
	            matches.addAll(secondLeg);
	            
	            break;
		
			case WORLD_CUP:
				
				matches = generateWorldCupFixture(shuffledClubs, tournament.getStartDate(), ctrl);
				
				isWorldCup = true;
				
				break;
		
		}
		
		redistributeMatchesByMatchday(matches, tournament.getStartDate(), isWorldCup);
	    
	    matches.sort(Comparator.comparing(Match::getDate));
		
		return matches;
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    
		String action = request.getParameter("action");
	    Logic ctrl = new Logic();

	    try {
	    
	    	if ("standings".equals(action)) {
	    		
	    		int id = Integer.parseInt(request.getParameter("id"));
	    		
	    		Tournament tournament = ctrl.getTournamentById(id);
	    		TreeMap<String, LinkedList<TeamStats>> tables = ctrl.calculateTables(id);
	    		
	    		request.setAttribute("tournament", tournament);
	    		request.setAttribute("tablesMap", tables);
	    		request.getRequestDispatcher("WEB-INF/Management/Standings.jsp").forward(request, response);
	    		
	    	} else if ("add".equals(action)) {

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
					
					if (selectedClubs == null || selectedClubs.length < 2) {
				        
						throw new SQLException("Todos los torneos requieren de al menos 2 equipos.");
				    
					}
					
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

					TournamentFormat format = tournament.getFormat();
					LocalDate endDate;

					if (format == TournamentFormat.ZONAL_ELIMINATION || format == TournamentFormat.WORLD_CUP) {

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

            	ctrl.deleteTournament(id);
                
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