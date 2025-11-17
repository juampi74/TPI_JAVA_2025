package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Position;
import logic.Logic;

@WebServlet("/actionposition")
public class ActionPosition extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	private Position buildPositionFromRequest(HttpServletRequest request, String action) {
        
		Position position = new Position();
    	if ("edit".equals(action)) position.setId(Integer.parseInt(request.getParameter("id")));
    	position.setDescription(request.getParameter("description"));
    	position.setAbbreviation(request.getParameter("abbreviation"));
    	
        return position;
    
	}
	
	private boolean checkAbbreviation(String abbreviation) {
		
		return abbreviation.length() <= 5;
	}
	
	private boolean checkPlayersWithPosition(Integer idPosition, Logic ctrl) throws SQLException {
    	
    	return ctrl.getNumberPlayersWithPosition(idPosition) == 0;
    
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				Position position = ctrl.getPositionById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("position", position);
				request.getRequestDispatcher("WEB-INF/Edit/EditPosition.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				request.getRequestDispatcher("WEB-INF/Add/AddPosition.jsp").forward(request, response);
			
			} else {
				
				LinkedList<Position> positions = ctrl.getAllPositions();
			    request.setAttribute("positionsList", positions);
			    request.getRequestDispatcher("/WEB-INF/Management/PositionManagement.jsp").forward(request, response);
			
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
            	
        		Position position = buildPositionFromRequest(request, action);
        		
        		if(checkAbbreviation(position.getAbbreviation())) {
        			
        			ctrl.addPosition(position);
        			
        		} else {
        			
        			request.setAttribute("errorMessage", "Error en la abreviación, la misma debe tener como máximo 5 caracteres");
        			request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
        			
        		}
        		
            } else if ("edit".equals(action)) {
            	
            	Position position = buildPositionFromRequest(request, action);
            	
            	if(checkAbbreviation(position.getAbbreviation())) {
        			
            		ctrl.updatePosition(position);
        			
        		} else {
        			
        			request.setAttribute("errorMessage", "Error en la abreviación, la misma debe tener como máximo 5 caracteres");
        			request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
        			
        		}	
        	    
            } else if ("delete".equals(action)){
            	
            	Integer id = Integer.parseInt(request.getParameter("id"));

            	if (checkPlayersWithPosition(id, ctrl)) {

            		ctrl.deletePosition(id);

            	} else {
            		
					request.setAttribute("errorMessage", "No se puede eliminar una posición que tiene jugadores asociados");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	
				}
            	
            }
    	    
    	    LinkedList<Position> positions = ctrl.getAllPositions();
    		request.setAttribute("positionsList", positions);
    	    request.getRequestDispatcher("WEB-INF/Management/PositionManagement.jsp").forward(request, response);
        	
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        } 
		
	}

}
