package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.Nationality;
import entities.President;
import enums.PersonRole;
import logic.Logic;
import utils.Config;

@MultipartConfig
@WebServlet("/actionpresident")
public class ActionPresident extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	private President buildPresidentFromRequest(HttpServletRequest request, String action, Logic ctrl) throws IOException, ServletException, SQLException {
        
		President president = new President();
		president.setId(Integer.parseInt(request.getParameter("id")));
		president.setFullname(request.getParameter("fullname"));
		president.setBirthdate(LocalDate.parse(request.getParameter("birthdate")));
		president.setAddress(request.getParameter("address"));
		president.setRole(PersonRole.valueOf("PRESIDENT"));
		president.setManagementPolicy(request.getParameter("managementPolicy"));
		president.setNationality(ctrl.getNationalityById(Integer.parseInt(request.getParameter("id_nationality"))));
		
		Part photo = request.getPart("photo");
        if (photo != null && photo.getSize() > 0) {
        	
        	String filename = president.getId() + "_" + photo.getSubmittedFileName();
        	
        	String uploadPath = Config.get("uploads.path").replace("\"", "");

        	File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            File file = new File(uploadDir, filename);
            
            Files.copy(photo.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            president.setPhoto(filename);
            
        } else {
            
        	if ("edit".equals(action)) {
            
        		String oldPhoto = request.getParameter("currentPhoto");
                president.setPhoto(oldPhoto);
            
        	} else {
            
        		president.setPhoto("-");
            
        	}
        
        }
        
        return president;
    
	}
	
	private boolean checkBirthdate(LocalDate birthdate) {
		
		return birthdate.isBefore(LocalDate.now().minusYears(18));
	
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				President president = ctrl.getPresidentById(Integer.parseInt(request.getParameter("id")));
				request.setAttribute("president", president);
				
				LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
				request.setAttribute("nationalitiesList", nationalities);
				
				request.getRequestDispatcher("WEB-INF/Edit/EditPresident.jsp").forward(request, response);
			
			} else if ("add".equals(action)) {
				
				LinkedList<Nationality> nationalities = ctrl.getAllNationalities();

				if (nationalities.size() > 0) {
                	
					nationalities.sort(Comparator.comparing(Nationality::getName));

					request.setAttribute("nationalitiesList", nationalities);
                    request.getRequestDispatcher("WEB-INF/Add/AddPresident.jsp").forward(request, response);

                } else {

                    request.setAttribute("errorMessage", "Debés agregar una nacionalidad primero");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

                }
			
			} else {
				
				LinkedList<President> presidents = ctrl.getAllPresidents();
			    request.setAttribute("presidentsList", presidents);
			    request.getRequestDispatcher("/WEB-INF/Management/PresidentManagement.jsp").forward(request, response);
			
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
            	
            	President president = buildPresidentFromRequest(request, action, ctrl);

            	if (checkBirthdate(president.getBirthdate())) {

            		ctrl.addPresident(president);

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el presidente debe ser mayor a 18 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
            	
            } else if ("edit".equals(action)) {
            	
            	President president = buildPresidentFromRequest(request, action, ctrl);

            	if (checkBirthdate(president.getBirthdate())) {

            		ctrl.updatePresident(president);

            	} else {

            		request.setAttribute("errorMessage", "Error en las fecha de nacimiento (el presidente debe ser mayor a 18 años");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

            	}
        	    
            } else if ("delete".equals(action)){
            	
        	    ctrl.deletePresident(Integer.parseInt(request.getParameter("id")));
        	    
            }
    	    
    	    LinkedList<President> presidents = ctrl.getAllPresidents();
    		request.setAttribute("presidentsList", presidents);
    	    request.getRequestDispatcher("WEB-INF/Management/PresidentManagement.jsp").forward(request, response);
    	    
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        }
	    
	}

}