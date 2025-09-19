package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Association;
import logic.Logic;

@WebServlet("/actionassociation")
public class ActionAssociation extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)){
			
			Association a = new Association();
			a.setId(Integer.parseInt(request.getParameter("id")));
			Association association = ctrl.getAssociationById(a);
			request.setAttribute("association", association);
			request.getRequestDispatcher("WEB-INF/EditAssociation.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/AddAssociation.jsp").forward(request, response);
		
		} else {
			
			LinkedList<Association> associations = ctrl.getAllAssociations();
		    request.setAttribute("associationsList", associations);
		    request.getRequestDispatcher("/WEB-INF/AssociationManagement.jsp").forward(request, response);
		
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
    		
        	Association a = new Association();   
    	    a.setName(request.getParameter("name"));
    	    a.setCreationDate(LocalDate.parse(request.getParameter("creationDate")));
    	    
    	    ctrl.addAssociation(a);
        	
        } else if ("edit".equals(action)) {
        	
        	Association a = new Association();
    	    a.setId(Integer.parseInt(request.getParameter("id")));
    	    a.setName(request.getParameter("name"));
    	    a.setCreationDate(LocalDate.parse(request.getParameter("creationDate")));    	    
    	    
    	    ctrl.updateAssociation(a);
    	    
        } else if ("delete".equals(action)){
        	
        	Association a = new Association();
    	    a.setId(Integer.parseInt(request.getParameter("id")));
    	    
    	    ctrl.deleteAssociation(a);
    	    
        }
	    
	    LinkedList<Association> associations = ctrl.getAllAssociations();
		request.setAttribute("associationsList", associations);
	    request.getRequestDispatcher("WEB-INF/AssociationManagement.jsp").forward(request, response);
	
	}

}