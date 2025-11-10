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
import entities.Club;
import logic.Logic;

@WebServlet("/actionassociation")
public class ActionAssociation extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private Association buildAssociationFromRequest(HttpServletRequest request, String action) {
        
		Association association = new Association();
		if("edit".equals(action)) association.setId(Integer.parseInt(request.getParameter("id")));
		association.setName(request.getParameter("name"));
		association.setCreationDate(LocalDate.parse(request.getParameter("creationDate")));

        return association;
    
	}
	
	private boolean checkCreationDate(LocalDate creationDate) {
		
		return creationDate.isBefore(LocalDate.now());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			Association association = ctrl.getAssociationById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("association", association);
			request.getRequestDispatcher("WEB-INF/Edit/EditAssociation.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/Add/AddAssociation.jsp").forward(request, response);
		
		} else {
			
			LinkedList<Association> associations = ctrl.getAllAssociations();
		    request.setAttribute("associationsList", associations);
		    request.getRequestDispatcher("/WEB-INF/Management/AssociationManagement.jsp").forward(request, response);
		
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	Association association = buildAssociationFromRequest(request, action);
        	if (checkCreationDate(association.getCreationDate())) {
        		ctrl.addAssociation(association);
        	} else {
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
        	
        } else if ("edit".equals(action)) {
        	
        	Association association = buildAssociationFromRequest(request, action);
        	if (checkCreationDate(association.getCreationDate())) {
        		ctrl.updateAssociation(association);
        	} else {
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
    	    
        } else if ("delete".equals(action)){
        	
        	ctrl.deleteAssociation(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<Association> associations = ctrl.getAllAssociations();
		request.setAttribute("associationsList", associations);
	    request.getRequestDispatcher("WEB-INF/Management/AssociationManagement.jsp").forward(request, response);
	
	}

}