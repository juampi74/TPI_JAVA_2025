package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Club;
import entities.Contract;
import entities.Player;
import enums.PersonRole;
import logic.Logic;
import utils.Config;

@WebServlet("/actionplayer")
@MultipartConfig
public class ActionPlayer extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private Player buildPlayerFromRequest(HttpServletRequest request) throws IOException, ServletException {
        
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
        
        String action = request.getParameter("action");
        Part photo = request.getPart("photo");
        if (photo != null && photo.getSize() > 0) {
        	String filename = player.getId() + "_" + photo.getSubmittedFileName();

        	String uploadPath = Config.get("uploads.path").replace("\"", "");

        	System.out.println("UPLOAD PATH >>> " + uploadPath);
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
				
				Player player = ctrl.getPlayerById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("player", player);
				request.getRequestDispatcher("WEB-INF/Edit/EditPlayer.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				request.getRequestDispatcher("WEB-INF/Add/AddPlayer.jsp").forward(request, response);
			
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
            	
            	Player player = buildPlayerFromRequest(request);

            	if (checkBirthdate(player.getBirthdate())) {

            		ctrl.addPlayer(player);

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el jugador debe ser mayor a 15 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            	
            } else if ("edit".equals(action)) {
            	
            	Player player = buildPlayerFromRequest(request);

            	if (checkBirthdate(player.getBirthdate())) {

            		ctrl.updatePlayer(player);

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el jugador debe ser mayor a 15 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
        	    
            } else if ("delete".equals(action)){

            	Integer id_player = Integer.parseInt(request.getParameter("id")); 

        	    if (checkContracts(id_player, ctrl)) {

            		ctrl.deletePlayer(id_player);

        	    } else {

        	    	request.setAttribute("errorMessage", "No se puede eliminar un jugador con contratos activos");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

        	    }
            }
    	    
    	    LinkedList<Player> players = ctrl.getAllPlayers();
    		request.setAttribute("playersList", players);
    		
    		LinkedList<Club> clubs = ctrl.getAllClubs();
    		request.setAttribute("clubsList", clubs);
    		
    	    request.getRequestDispatcher("WEB-INF/Management/PlayerManagement.jsp").forward(request, response);
    	    
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        }
	    
	}

}