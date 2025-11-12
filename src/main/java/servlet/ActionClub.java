package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import logic.Logic;

@WebServlet("/actionclub")
public class ActionClub extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private Club buildClubFromRequest(HttpServletRequest request, String action,Logic ctrl) throws SQLException {
        
    	Club club = new Club();
    	if ("edit".equals(action)) club.setId(Integer.parseInt(request.getParameter("id")));
    	club.setName(request.getParameter("name"));
    	club.setFoundationDate(LocalDate.parse(request.getParameter("foundationDate")));
    	club.setPhoneNumber(request.getParameter("phoneNumber"));
    	club.setEmail(request.getParameter("email"));
    	club.setBadgeImage(request.getParameter("badgeImage"));
    	club.setBudget(Double.parseDouble(request.getParameter("budget")));
    	club.setStadium(ctrl.getStadiumById(Integer.parseInt(request.getParameter("id_stadium"))));
		
        return club;
    
	}
    
    private boolean checkFoundationDate(LocalDate foundationDate) {
		
		return foundationDate.isBefore(LocalDate.now());
	}
    
    private boolean checkContracts(Integer id, Logic ctrl) throws SQLException {
    	
    	LinkedList<Contract> contracts = ctrl.getContractsByClubId(id);
    	return contracts.size() == 0;
    
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        try {
        	
        	if ("edit".equals(action)) {
                
            	Club club = ctrl.getClubById(Integer.parseInt(request.getParameter("id")));
    			request.setAttribute("club", club);
                
                LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
            	request.setAttribute("stadiumsList", stadiums);
                
            	request.getRequestDispatcher("WEB-INF/Edit/EditClub.jsp").forward(request, response);
            
            } else if ("add".equals(action)) {
            
            	LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
            	
				if (stadiums.size() > 0) {
        		
        			request.setAttribute("stadiumsList", stadiums);        		
        			request.getRequestDispatcher("WEB-INF/Add/AddClub.jsp").forward(request, response);
        	
        		} else {
        		
        			request.setAttribute("errorMessage", "Debe agregar un estadio primero");
        			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        		
        		}
            
            } else {
            
            	LinkedList<Club> clubs = ctrl.getAllClubs();
                request.setAttribute("clubsList", clubs);
                
                LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
            	request.setAttribute("stadiumsList", stadiums);
            	
            	request.getRequestDispatcher("/WEB-INF/Management/ClubManagement.jsp").forward(request, response);
            
            }
        	
        } catch(SQLException e) {
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        }

        
    
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        try {
        	
        	if ("add".equals(action)) {
        		
        		Club club = buildClubFromRequest(request, action, ctrl);
        	
        		if (checkFoundationDate(club.getFoundationDate())) {
        	
        			ctrl.addClub(club);
        	
        		} else {
        	
        			request.setAttribute("errorMessage", "Error en las fechas introducidas (la fecha de fundación debe ser mayor a hoy)");
        			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	
        		}

        	} else if ("edit".equals(action)) {
        	
        		Club club = buildClubFromRequest(request, action, ctrl);
        	
        		if (checkFoundationDate(club.getFoundationDate())) {
        	
        			ctrl.updateClub(club);
        	
        		} else {
        	
        			request.setAttribute("errorMessage", "Error en las fechas introducidas (la fecha de fundación debe ser mayor a hoy)");
        			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	
        		}

            } else if ("delete".equals(action)) {
                
            	Integer club_id = Integer.parseInt(request.getParameter("id"));
            	
            	if (checkContracts(club_id, ctrl)) {

            		ctrl.deleteClub(club_id);

            	} else {
            		
					request.setAttribute("errorMessage", "No se puede eliminar un club con contratos activos");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	
				}
            }

            LinkedList<Club> clubs = ctrl.getAllClubs();
            request.setAttribute("clubsList", clubs);
            request.getRequestDispatcher("WEB-INF/Management/ClubManagement.jsp").forward(request, response);
            
        } catch(SQLException e) {
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        }
    
    }

}