package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.*;
import enums.*;
import logic.Logic;
import utils.Config;

@MultipartConfig
@WebServlet("/actionplayer")
public class ActionPlayer extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private Player buildPlayerFromRequest(HttpServletRequest request, String action, Logic ctrl) throws IOException, ServletException, SQLException {
        
    	Player player = new Player();
    	player.setId(Integer.parseInt(request.getParameter("id")));
    	player.setFullname(request.getParameter("fullname"));
    	player.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
    	player.setAddress(request.getParameter("address"));
    	player.setRole(PersonRole.valueOf("PLAYER"));
        player.setDominantFoot(DominantFoot.valueOf(request.getParameter("dominantFoot")));
        player.setJerseyNumber(Integer.parseInt(request.getParameter("jerseyNumber")));
        player.setHeight(Double.parseDouble(request.getParameter("height")));
        player.setWeight(Double.parseDouble(request.getParameter("weight")));
        player.setNationality(ctrl.getNationalityById(Integer.parseInt(request.getParameter("id_nationality"))));
        
        Part photo = request.getPart("photo");
        if (photo != null && photo.getSize() > 0) {
        	
        	String filename = player.getId() + "_" + photo.getSubmittedFileName();

        	String uploadPath = Config.get("uploads.path").replace("\"", "");

        	File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            File file = new File(uploadDir, filename);
            
            Files.copy(photo.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            player.setPhoto(filename);
            
        } else {
            
        	if ("edit".equals(action)) {
            
        		String oldPhoto = request.getParameter("currentPhoto");
                player.setPhoto(oldPhoto);
            
        	} else {
            
        		player.setPhoto("-");
            
        	}
        
        }
        
        return player;
    
	}
	
	private LinkedList<Integer> buildPlayerPositionFromRequest(HttpServletRequest request) {

	    LinkedList<Integer> positionsIds = new LinkedList<>();

	    String[] selectedPositions = request.getParameterValues("positions");

	    if (selectedPositions != null) {
	        for (String posIdStr : selectedPositions) {
	            try {
	                int posId = Integer.parseInt(posIdStr);
	                positionsIds.add(posId);
	            } catch (NumberFormatException e) {
	                System.err.println("ID de posición inválido: " + posIdStr);
	            }
	        }
	    }

	    return positionsIds;
	}
	
	private Integer getPrimaryPositionFromRequest(HttpServletRequest request) {
	    
		String primaryPos = request.getParameter("primaryPosition");
	    
		if (primaryPos != null) {
	        try {
	            return Integer.parseInt(primaryPos);
	        } catch (NumberFormatException e) {
	            System.err.println("ID posición principal inválida: " + primaryPos);
	        }
	    }
	    return null;
	}
	
	private boolean checkBirthdate(LocalDate birthdate) {
		
		return birthdate.isBefore(LocalDate.now().minusYears(15));
	
	}
	
	private boolean checkContracts(Integer id, Logic ctrl) throws SQLException {

    	LinkedList<Contract> contracts = ctrl.getContractsByPersonId(id);
    	return contracts.size() == 0;	
    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				int id = Integer.parseInt(request.getParameter("id"));
				Player player = ctrl.getPlayerById(id);
				request.setAttribute("player", player);
				
				LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
				request.setAttribute("nationalitiesList", nationalities);
				
				LinkedList<Position> positions = ctrl.getAllPositions();
            	request.setAttribute("positionsList", positions);
            	
            	LinkedList<Integer> playerPositions = ctrl.getPlayerPositions(id);
            	request.setAttribute("playerPositionsList", playerPositions);
            	
            	Integer primaryPos = ctrl.getPlayerPrincipalPosition(id);
            	request.setAttribute("playerPrimary", primaryPos);
            	
				request.getRequestDispatcher("WEB-INF/Edit/EditPlayer.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
				LinkedList<Position> positions = ctrl.getAllPositions();
				
				if (positions.size() > 0) {
					
					request.setAttribute("positionsList", positions);  
				
				}
				
				if (nationalities.size() > 0) {
                	
					nationalities.sort(Comparator.comparing(Nationality::getName));

					request.setAttribute("nationalitiesList", nationalities);
                    request.getRequestDispatcher("WEB-INF/Add/AddPlayer.jsp").forward(request, response);

                } else {

                    request.setAttribute("errorMessage", "Debés agregar una nacionalidad primero");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

                }
			
			} else if ("history".equals(action)) {
			    
			    int idPlayer = Integer.parseInt(request.getParameter("id"));
			    
			    Player player = ctrl.getPlayerById(idPlayer);
			    
			    LocalDate from = null;
			    LocalDate to = null;
			    
			    String fromStr = request.getParameter("from");
			    String toStr = request.getParameter("to");
			    
			    if (fromStr != null && !fromStr.isEmpty()) from = LocalDate.parse(fromStr);
			    if (toStr != null && !toStr.isEmpty()) to = LocalDate.parse(toStr);
			    
			    LinkedList<Contract> history = ctrl.getPlayerHistory(idPlayer, from, to);
			    		    
			    request.setAttribute("player", player);
			    request.setAttribute("history", history);
			    
			    request.getRequestDispatcher("WEB-INF/Management/PlayerHistory.jsp").forward(request, response);

			} else {
				
				String clubIdParam = request.getParameter("clubId");
				
				LinkedList<Player> players;
				
				if (clubIdParam != null && !clubIdParam.isEmpty()) {

					if ("free".equals(clubIdParam)) {
						players = ctrl.getAvailablePlayers();
					} else {
						try {
							int clubId = Integer.parseInt(clubIdParam);
							players = ctrl.getPlayersByClub(clubId);
						} catch (NumberFormatException nfe) {
							players = ctrl.getAllPlayers();
						}
					}

				} else {

					players = ctrl.getAllPlayers();

				}

			    request.setAttribute("playersList", players);
			    
			    Map<Integer, String> primaryPositions = ctrl.getAllPrimaryPositions();
			    request.setAttribute("positionsMap", primaryPositions);
			    
			    Map<Integer, Club> currentClubsMap = ctrl.getPlayersCurrentClubs();
			    request.setAttribute("currentClubsMap", currentClubsMap);
			    
			    LinkedList<Club> clubs = ctrl.getAllClubs();
            	clubs.sort(Comparator.comparing(Club::getName));
			    request.setAttribute("clubsList", clubs);
			    
			    request.getRequestDispatcher("/WEB-INF/Management/PlayerManagement.jsp").forward(request, response);
			
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
            	
            	Player player = buildPlayerFromRequest(request, action, ctrl);

            	if (checkBirthdate(player.getBirthdate())) {

            		ctrl.addPlayer(player);
            		int playerId = player.getId();
            		
            		LinkedList<Integer> posIds = buildPlayerPositionFromRequest(request);
            		Integer mainPosId = getPrimaryPositionFromRequest(request);

            	    for (Integer posId : posIds) {
            	        
            	    	ctrl.addPlayerPosition(playerId, posId);
            	    
            	    }
            	    
            	    if (mainPosId != null) {
            	    
            	    	ctrl.setPlayerPrimaryPosition(playerId, mainPosId);
            	    
            	    }

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el jugador debe ser mayor a 15 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            	
            } else if ("edit".equals(action)) {
            	
            	Player player = buildPlayerFromRequest(request, action, ctrl);

            	if (checkBirthdate(player.getBirthdate())) {

            		ctrl.updatePlayer(player);
            		int playerId = player.getId();
            		
            		LinkedList<Integer> posIds = buildPlayerPositionFromRequest(request);
            		Integer mainPosId = getPrimaryPositionFromRequest(request);
            		ctrl.deletePlayerPositions(playerId);
            		
            		for (Integer posId : posIds) {
        	        
            			ctrl.addPlayerPosition(playerId, posId);
            		
            		}
            		
            		if (mainPosId != null) {
            	        
            			ctrl.setPlayerPrimaryPosition(playerId, mainPosId);
            	    
            		}

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el jugador debe ser mayor a 15 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
        	    
            } else if ("delete".equals(action)){

            	Integer id_player = Integer.parseInt(request.getParameter("id")); 

        	    if (checkContracts(id_player, ctrl)) {

        	    	ctrl.deletePlayerPositions(id_player);
        	    	ctrl.deletePlayer(id_player);

        	    } else {

        	    	request.setAttribute("errorMessage", "No se puede eliminar un jugador con contratos activos");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

        	    }
        	    
            }
    	    
    	    LinkedList<Player> players = ctrl.getAllPlayers();
    		request.setAttribute("playersList", players);
    		
    		Map<Integer, String> primaryPositions = ctrl.getAllPrimaryPositions();
			request.setAttribute("positionsMap", primaryPositions);
			
			Map<Integer, Club> currentClubsMap = ctrl.getPlayersCurrentClubs();
		    request.setAttribute("currentClubsMap", currentClubsMap);
    		
    		LinkedList<Club> clubs = ctrl.getAllClubs();
    		request.setAttribute("clubsList", clubs);
		
		    request.getRequestDispatcher("WEB-INF/Management/PlayerManagement.jsp").forward(request, response);
    	    
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        }
	    
	}

}