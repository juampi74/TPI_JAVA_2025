package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.TechnicalDirector;
import enums.PersonRole;
import logic.Logic;

@WebServlet("/actiontechnicaldirector")
public class ActionTechnicalDirector extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private TechnicalDirector buildTechnicalDirectorFromRequest(HttpServletRequest request) {
                        
        TechnicalDirector technicalDirector = new TechnicalDirector();
        technicalDirector.setId(Integer.parseInt(request.getParameter("id")));
        technicalDirector.setFullname(request.getParameter("fullname"));
        technicalDirector.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
        technicalDirector.setAddress(request.getParameter("address"));
        technicalDirector.setRole(PersonRole.valueOf("TECHNICAL_DIRECTOR"));
        technicalDirector.setPreferredFormation(request.getParameter("preferredFormation"));
        technicalDirector.setCoachingLicense(request.getParameter("coachingLicense"));
        technicalDirector.setLicenseObtainedDate(LocalDate.parse(request.getParameter("licenseObtainedDate")));

        return technicalDirector;
    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)) {
			
			TechnicalDirector technicalDirector = ctrl.getTechnicalDirectorById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("technicalDirector", technicalDirector);
			request.getRequestDispatcher("WEB-INF/Edit/EditTechnicalDirector.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/Add/AddTechnicalDirector.jsp").forward(request, response);
		
		} else {
			
			LinkedList<TechnicalDirector> technicalDirectors = ctrl.getAllTechnicalDirectors();
		    request.setAttribute("technicalDirectorsList", technicalDirectors);
		    request.getRequestDispatcher("/WEB-INF/Management/TechnicalDirectorManagement.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	ctrl.addTechnicalDirector(buildTechnicalDirectorFromRequest(request));
        	
        } else if ("edit".equals(action)) {
        	
        	ctrl.updateTechnicalDirector(buildTechnicalDirectorFromRequest(request));
    	    
        } else if ("delete".equals(action)){
        	
    	    ctrl.deleteTechnicalDirector(Integer.parseInt(request.getParameter("id")));
    	    
        }
	    
	    LinkedList<TechnicalDirector> technicalDirectors = ctrl.getAllTechnicalDirectors();
		request.setAttribute("technicalDirectorsList", technicalDirectors);
	    request.getRequestDispatcher("WEB-INF/Management/TechnicalDirectorManagement.jsp").forward(request, response);
	    
	}

}