package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.President;
import enums.PersonRole;
import logic.Logic;

@WebServlet("/actionpresident")
public class ActionPresident extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private President buildPresidentFromRequest(HttpServletRequest request) {
        
		President president = new President();
		president.setId(Integer.parseInt(request.getParameter("id")));
		president.setFullname(request.getParameter("fullname"));
		president.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
		president.setAddress(request.getParameter("address"));
		president.setRole(PersonRole.valueOf("PRESIDENT"));
		president.setManagementPolicy(request.getParameter("managementPolicy"));

        return president;
    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			President president = ctrl.getPresidentById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("president", president);
			request.getRequestDispatcher("WEB-INF/EditPresident.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/AddPresident.jsp").forward(request, response);
		
		} else {
			
			LinkedList<President> presidents = ctrl.getAllPresidents();
		    request.setAttribute("presidentsList", presidents);
		    request.getRequestDispatcher("/WEB-INF/PresidentManagement.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	ctrl.addPresident(buildPresidentFromRequest(request));
        	
        } else if ("edit".equals(action)) {
        	
        	ctrl.updatePresident(buildPresidentFromRequest(request));
    	    
        } else if ("delete".equals(action)){
        	
    	    ctrl.deletePresident(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<President> presidents = ctrl.getAllPresidents();
		request.setAttribute("presidentsList", presidents);
	    request.getRequestDispatcher("WEB-INF/PresidentManagement.jsp").forward(request, response);
	    
	}

}