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
	
	private boolean checkBirthdate(LocalDate birthdate) {
		
		return birthdate.isBefore(LocalDate.now().minusYears(18));
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			President president = ctrl.getPresidentById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("president", president);
			request.getRequestDispatcher("WEB-INF/Edit/EditPresident.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/Add/AddPresident.jsp").forward(request, response);
		
		} else {
			
			LinkedList<President> presidents = ctrl.getAllPresidents();
		    request.setAttribute("presidentsList", presidents);
		    request.getRequestDispatcher("/WEB-INF/Management/PresidentManagement.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	President president =buildPresidentFromRequest(request);
        	if (checkBirthdate(president.getBirthdate())) {
        		ctrl.addPresident(president);
        	} else {
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
        	
        } else if ("edit".equals(action)) {
        	
        	President president =buildPresidentFromRequest(request);
        	if (checkBirthdate(president.getBirthdate())) {
        		ctrl.updatePresident(president);
        	} else {
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
    	    
        } else if ("delete".equals(action)){
        	
    	    ctrl.deletePresident(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<President> presidents = ctrl.getAllPresidents();
		request.setAttribute("presidentsList", presidents);
	    request.getRequestDispatcher("WEB-INF/Management/PresidentManagement.jsp").forward(request, response);
	    
	}

}