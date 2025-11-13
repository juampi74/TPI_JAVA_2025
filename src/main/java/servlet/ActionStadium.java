package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import entities.Stadium;
import logic.Logic;

@WebServlet("/actionstadium")
public class ActionStadium extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Stadium buildStadiumFromRequest(HttpServletRequest request, String action) {
        
    	Stadium stadium = new Stadium();
    	if ("edit".equals(action)) stadium.setId(Integer.parseInt(request.getParameter("id")));
    	stadium.setName(request.getParameter("name"));
    	stadium.setCapacity(Integer.parseInt(request.getParameter("capacity")));
    	
        return stadium;
    
	}
	
	private boolean checkCapacity(Integer capacity) {
		
		return capacity > 0;
	
	}
	
	private boolean checkClub(Integer id, Logic ctrl) throws SQLException {
		
		Club club = ctrl.getClubByStadiumId(id);
		return club == null;
	
	}
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				Stadium stadium = ctrl.getStadiumById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("stadium", stadium);
				request.getRequestDispatcher("WEB-INF/Edit/EditStadium.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				request.getRequestDispatcher("WEB-INF/Add/AddStadium.jsp").forward(request, response);
			
			} else {
				
				LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
			    request.setAttribute("stadiumsList", stadiums);
			    request.getRequestDispatcher("/WEB-INF/Management/StadiumManagement.jsp").forward(request, response);
			
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
            	
            	Stadium stadium = buildStadiumFromRequest(request, action);

        		if (checkCapacity(stadium.getCapacity())) {

        			ctrl.addStadium(stadium);

        		} else {

        			request.setAttribute("errorMessage", "Error en la capacidad, la misma debe ser mayor a 0");
        			request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);

        		}
            } else if ("edit".equals(action)) {
            	
            	Stadium stadium = buildStadiumFromRequest(request, action);

        		if (checkCapacity(stadium.getCapacity())) {

        			ctrl.updateStadium(stadium);

        		} else {

        			request.setAttribute("errorMessage", "Error en la capacidad, la misma debe ser mayor a 0");
        			request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);

        		}
        	    
            } else if ("delete".equals(action)){
            	
            	Integer id = Integer.parseInt(request.getParameter("id"));

            	if (checkClub(id, ctrl)) {

            		ctrl.deleteStadium(id);

            	} else {

            		request.setAttribute("errorMessage", "No se puede eliminar un estadio que pertenece a un club");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            }
    	    
    	    LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
    		request.setAttribute("stadiumsList", stadiums);
    	    request.getRequestDispatcher("WEB-INF/Management/StadiumManagement.jsp").forward(request, response);
        	
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        } 
	
	}

}