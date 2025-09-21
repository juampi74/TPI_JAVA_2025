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
	
	private Association buildAssociationFromRequest(HttpServletRequest request) {
        
		Association association = new Association();
		association.setId(Integer.parseInt(request.getParameter("id")));
		association.setName(request.getParameter("name"));
		association.setCreationDate(LocalDate.parse(request.getParameter("creation_date")));

        return association;
    
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			Association association = ctrl.getAssociationById(Integer.parseInt(request.getParameter("id")));
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
    		
        	ctrl.addAssociation(buildAssociationFromRequest(request));
        	
        } else if ("edit".equals(action)) {
        	
        	ctrl.updateAssociation(buildAssociationFromRequest(request));
    	    
        } else if ("delete".equals(action)){
        	
        	ctrl.deleteAssociation(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<Association> associations = ctrl.getAllAssociations();
		request.setAttribute("associationsList", associations);
	    request.getRequestDispatcher("WEB-INF/AssociationManagement.jsp").forward(request, response);
	
	}

}