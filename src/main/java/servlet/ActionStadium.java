package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Stadium;
import logic.Logic;

@WebServlet("/actionstadium")
public class ActionStadium extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Stadium buildStadiumFromRequest(HttpServletRequest request) {
        
    	Stadium stadium = new Stadium();
    	stadium.setId(Integer.parseInt(request.getParameter("id")));
    	stadium.setName(request.getParameter("name"));
    	stadium.setCapacity(Integer.parseInt(request.getParameter("capacity")));
    	
        return stadium;
    
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			Stadium stadium = ctrl.getStadiumById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("stadium", stadium);
			request.getRequestDispatcher("WEB-INF/EditStadium.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/AddStadium.jsp").forward(request, response);
		
		} else {
			
			LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
		    request.setAttribute("stadiumsList", stadiums);
		    request.getRequestDispatcher("/WEB-INF/StadiumManagement.jsp").forward(request, response);
		
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
    		
        	ctrl.addStadium(buildStadiumFromRequest(request));
        	
        } else if ("edit".equals(action)) {
        	
        	ctrl.updateStadium(buildStadiumFromRequest(request));
    	    
        } else if ("delete".equals(action)){
        	
        	ctrl.deleteStadium(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
		request.setAttribute("stadiumsList", stadiums);
	    request.getRequestDispatcher("WEB-INF/StadiumManagement.jsp").forward(request, response);
	
	}

}