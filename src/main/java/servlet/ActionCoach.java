package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Contract;
import entities.Coach;
import enums.PersonRole;
import logic.Logic;
import utils.Config;

@MultipartConfig
@WebServlet("/actioncoach")
public class ActionCoach extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private Coach buildCoachFromRequest(HttpServletRequest request) throws IOException, ServletException {
                        
        Coach coach = new Coach();
        coach.setId(Integer.parseInt(request.getParameter("id")));
        coach.setFullname(request.getParameter("fullname"));
        coach.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
        coach.setAddress(request.getParameter("address"));
        coach.setRole(PersonRole.valueOf("COACH"));
        coach.setPreferredFormation(request.getParameter("preferredFormation"));
        coach.setCoachingLicense(request.getParameter("coachingLicense"));
        coach.setLicenseObtainedDate(LocalDate.parse(request.getParameter("licenseObtainedDate")));
        
        String action = request.getParameter("action");
        Part photo = request.getPart("photo");
        if (photo != null && photo.getSize() > 0) {
        	String filename = coach.getId() + "_" + photo.getSubmittedFileName();

        	String uploadPath = Config.get("uploads.path").replace("\"", " ");

        	System.out.println("UPLOAD PATH >>> " + uploadPath);
        	File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            File file = new File(uploadDir, filename);
            
            Files.copy(photo.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            coach.setPhoto(filename);
            
        } else {
            if ("edit".equals(action)) {
                String oldPhoto = request.getParameter("currentPhoto");
                coach.setPhoto(oldPhoto);
            } else {
                coach.setPhoto("-");
            }
        }
        
        return coach;
    
	}
	
	private boolean checkDates(LocalDate birthdate, LocalDate licenseObtainedDate) {
		
		return (birthdate.isBefore(LocalDate.now().minusYears(18)) && licenseObtainedDate.isBefore(LocalDate.now()) && licenseObtainedDate.isAfter(birthdate.plusYears(18)));
	
	}
	
	private boolean checkContracts(Integer id, Logic ctrl) throws SQLException {

    	LinkedList<Contract> contracts = ctrl.getContractsByPersonId(id);
    	return contracts.size() == 0;	
    
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				Coach coach = ctrl.getCoachById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("coach", coach);
				request.getRequestDispatcher("WEB-INF/Edit/EditCoach.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				request.getRequestDispatcher("WEB-INF/Add/AddCoach.jsp").forward(request, response);
			
			} else {
				
				LinkedList<Coach> coaches = ctrl.getAllCoaches();
			    request.setAttribute("coachesList", coaches);
			    request.getRequestDispatcher("/WEB-INF/Management/CoachManagement.jsp").forward(request, response);
			
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
            	
            	Coach c = buildCoachFromRequest(request);
            	if (checkDates(c.getBirthdate(), c.getLicenseObtainedDate())) {
            		ctrl.addCoach(c);
            	} else {
            		request.setAttribute("errorMessage", "Error en las fechas introducidas (el técnico debe ser mayor a 18 años y su licencia debe ser 6 meses posterior a su mayoria de edad)");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	}
            	
            } else if ("edit".equals(action)) {
            	
            	Coach c = buildCoachFromRequest(request);
            	if (checkDates(c.getBirthdate(), c.getLicenseObtainedDate())) {
                	ctrl.updateCoach(c);
            	} else {
            		request.setAttribute("errorMessage", "Error en las fechas introducidas (el técnico debe ser mayor a 18 años y su licencia debe ser 6 meses posterior a su mayoria de edad)");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            	}
        	    
            } else if ("delete".equals(action)){
            	
            	Integer id_c = Integer.parseInt(request.getParameter("id")); 
        	    if (checkContracts(id_c, ctrl)) {

					ctrl.deleteCoach(id_c);

        	    } else {
        	    	
					request.setAttribute("errorMessage", "No se puede eliminar un técnico con contratos activos");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	    
				}
        	    
            }
    	    
    	    LinkedList<Coach> coaches = ctrl.getAllCoaches();
    		request.setAttribute("coachesList", coaches);
    	    request.getRequestDispatcher("WEB-INF/Management/CoachManagement.jsp").forward(request, response);
        	
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        }        
	    
	}

}