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

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)){
			
			int id = Integer.parseInt(request.getParameter("id"));
			Stadium s = new Stadium();
			s.setId(id);
			Stadium stadium = ctrl.getStadiumById(s);
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
    		
        	String name = request.getParameter("name");
    		int capacity = Integer.parseInt(request.getParameter("capacity"));
    		
    	    Stadium s = new Stadium();   
    	    s.setName(name);
    	    s.setCapacity(capacity);
    	    
    	    ctrl.addStadium(s);
        	
        } else if ("edit".equals(action)) {
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	String name = request.getParameter("name");
    		int capacity = Integer.parseInt(request.getParameter("capacity"));
    	    
    		Stadium s = new Stadium();
    	    s.setId(id);
    	    s.setName(name);
    	    s.setCapacity(capacity);
    	    
    	    ctrl.updateStadium(s);
    	    
        } else if ("delete".equals(action)){
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	
    	    Stadium s = new Stadium();
    	    s.setId(id);
    	    ctrl.deleteStadium(s);
        }
	    
	    LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
		request.setAttribute("stadiumsList", stadiums);
	    request.getRequestDispatcher("WEB-INF/StadiumManagement.jsp").forward(request, response);
	
	}

}