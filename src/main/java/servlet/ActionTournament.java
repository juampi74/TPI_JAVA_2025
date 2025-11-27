package servlet;

import java.io.IOException;
import java.math.BigInteger;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
		tournament.setFormat(request.getParameter("format"));
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
	
	private LinkedList<Match> generateCombinations(LinkedList<Club> clubs, LocalDate startDate) {
		
		LinkedList<Match> fixture = new LinkedList<>();

		int n = clubs.size();
		
		boolean addedGhost = false;
		
		if (n % 2 != 0) {
		    
			clubs.add(null);
		    n++;
		    addedGhost = true;
		
		}
		
		int totalMatchdays = n - 1;
		int matchesPerMatchday = n / 2;
		
		LinkedList<Club> rotated = new LinkedList<>(clubs);
		
		LocalDateTime dateTime = startDate.atTime(16, 00);
		
		Long matchesInDay = Math.round(clubs.size() / 8.0);
		
		for (int day = 1; day <= totalMatchdays; day++) {
			
			Integer matchdayDay = 0;
			Integer round = 0;
			
		    for (int i = 0; i < matchesPerMatchday; i++) {
		
		        Club home = rotated.get(i);
		        Club away = rotated.get(n - 1 - i);
		
		        if (home == null || away == null) continue;
		
		        if (day % 2 == 0) {
		            
		        	Club temp = home;
		            home = away;
		            away = temp;
		        
		        }
		        
		        if (i > (matchesInDay + matchesInDay * matchdayDay)) {
		        	
		        	matchdayDay = matchdayDay + 1;
		        	round = 0;
		        
		        }
		        
		        Match match = new Match();
		        
		        match.setAway(away);
		        match.setHome(home);
			    
		        match.setDate(dateTime
			    	.plusDays(matchdayDay + 7 * (day - 1))
			    	.plusHours(2 * round));
			    
		        match.setMatchday(day);
		        fixture.add(match);

		        round = round + 1;
		    
		    }
		
		    rotated.add(1, rotated.remove(rotated.size() - 1));
		
		}
		
		if (addedGhost) {
		  
			clubs.remove(null);
		
		}
		
		return fixture;
	
	}
	
	private LinkedList<Match> drawTournamentMatchdays(Integer id_format, LinkedList<Club> clubs, Tournament tournament){
		
		LinkedList<Match> matches = new LinkedList<>();
		
		switch (id_format) {
		
			case 1:
			
				matches = generateCombinations(clubs, tournament.getStartDate());
				break;
			
			case 2:
			
				LinkedList<Match> matches_leg_one = generateCombinations(clubs, tournament.getStartDate());
				
				matches.addAll(matches_leg_one);
				
				LocalDateTime start_date = matches_leg_one.getLast().getDate();
				LocalDateTime end_date = matches_leg_one.getLast().getDate();
				
				long days = ChronoUnit.DAYS.between(start_date.toLocalDate(), end_date.toLocalDate());
				
				for (Match m : matches_leg_one) {
					Match match = new Match();
					match.setAway(m.getHome());
					match.setHome(m.getAway());
					match.setDate(m.getDate().plusDays(days + 7));
					match.setMatchday(m.getMatchday() + (clubs.size() - 1));
					matches.add(match);
				}
				
				break;
		}
		
		return matches;
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        try {
        	
        	if ("edit".equals(action)) {
            	
            	Tournament tournament = ctrl.getTournamentById(Integer.parseInt(request.getParameter("id")));
    			request.setAttribute("tournament", tournament);
                
                LinkedList<Association> associations = ctrl.getAllAssociations();
                associations.sort(Comparator.comparing(Association::getName));
            	request.setAttribute("associationsList", associations);
                
            	request.getRequestDispatcher("WEB-INF/Edit/EditTournament.jsp").forward(request, response);
            
            } else if ("add".equals(action)) {

            	LinkedList<Association> associations = ctrl.getAllAssociations();
				
            	if (associations.size() > 0) {
        		
					associations.sort(Comparator.comparing(Association::getName));
					request.setAttribute("associationsList", associations);
					
					LinkedList<Club> clubs = ctrl.getAllClubs();
					
					if (clubs.size() > 0) {
		        		
						request.setAttribute("clubsList", clubs);
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
            	
            	if (checkStartDate(tournament.getStartDate())) {
            		
					LinkedList<Match> fixture = new LinkedList<>();
					
					Integer id_format = Integer.parseInt(request.getParameter("format"));
					
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
					
					fixture = drawTournamentMatchdays(id_format, clubs, tournament);
					tournament.setEndDate(fixture.getLast().getDate().toLocalDate());

					ctrl.addTournament(tournament);
					
					for (Match match : fixture) {
            			
						match.setTournament(tournament);
            			ctrl.addMatch(match);
            		
					}
            	
	        	} else {
							
					request.setAttribute("errorMessage", "Error en las fechas introducidas (el torneo debe empezar a partir de hoy y durar, al menos, 4 meses)");
					request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
	        	}

            } else if ("edit".equals(action)) {
                
            	Tournament tournament = buildTournamentFromRequest(request, action, ctrl);
            	
            	if (checkStartDate(tournament.getStartDate())) {
            	
            		ctrl.updateTournament(tournament);
            	
            	} else {
            	
            		request.setAttribute("errorMessage", "Error en las fechas introducidas (el torneo debe empezar a partir de hoy y durar, al menos, 4 meses)");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	
            	}

            } else if ("delete".equals(action)) {
                
            	ctrl.deleteTournament(Integer.parseInt(request.getParameter("id")));
                
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