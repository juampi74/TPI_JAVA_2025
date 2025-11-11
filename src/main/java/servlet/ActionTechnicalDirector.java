package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Contract;
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
	
	private boolean checkDates(LocalDate birthdate, LocalDate licenseObtainedDate) {
		
		return (birthdate.isBefore(LocalDate.now().minusYears(18)) && licenseObtainedDate.isBefore(LocalDate.now()) && licenseObtainedDate.isAfter(birthdate.plusYears(18)));
	}
	
	private boolean checkContracts(Integer id) {
    	Logic ctrl = new Logic();
    	LinkedList<Contract> contracts = ctrl.getContractsByPersonId(id);
    	return 0 == contracts.size();	
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
        	
        	TechnicalDirector td = buildTechnicalDirectorFromRequest(request);
        	if (checkDates(td.getBirthdate(), td.getLicenseObtainedDate())) {
        		ctrl.addTechnicalDirector(td);
        	} else {
        		request.setAttribute("errorMessage", "Error en las fechas introducidas (el tecnico debe ser mayor a 18 años y su licencia debe ser 6 meses posterior a su mayoria de edad)");
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
        	
        } else if ("edit".equals(action)) {
        	
        	TechnicalDirector td = buildTechnicalDirectorFromRequest(request);
        	if (checkDates(td.getBirthdate(), td.getLicenseObtainedDate())) {
            	ctrl.updateTechnicalDirector(td);
        	} else {
        		request.setAttribute("errorMessage", "Error en las fechas introducidas (el tecnico debe ser mayor a 18 años y su licencia debe ser 6 meses posterior a su mayoria de edad)");
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
    	    
        } else if ("delete".equals(action)){
        	
        	Integer id_td = Integer.parseInt(request.getParameter("id")); 
    	    if (checkContracts(id_td)) {
        		ctrl.deleteTechnicalDirector(id_td);
    	    } else {
    	    	request.setAttribute("errorMessage", "No se puede eliminar un técnico con contratos activos");
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
    	    }
    	    
        }
	    
	    LinkedList<TechnicalDirector> technicalDirectors = ctrl.getAllTechnicalDirectors();
		request.setAttribute("technicalDirectorsList", technicalDirectors);
	    request.getRequestDispatcher("WEB-INF/Management/TechnicalDirectorManagement.jsp").forward(request, response);
	    
	}

}