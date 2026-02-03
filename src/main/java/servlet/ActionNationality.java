package servlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import entities.*;
import enums.Continent;
import logic.Logic;
import utils.Config;

@MultipartConfig
@WebServlet("/actionnationality")
public class ActionNationality extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
       
	private Nationality buildNationalityFromRequest(HttpServletRequest request, String action) throws IOException, ServletException {
        
		Nationality nationality = new Nationality();
    	if ("edit".equals(action)) nationality.setId(Integer.parseInt(request.getParameter("id")));
    	nationality.setName(request.getParameter("name"));
    	nationality.setIsoCode(request.getParameter("isoCode"));
    	
        Part flag = request.getPart("flagImage");
        if (flag != null && flag.getSize() > 0) {
        	String filename = "flag_" + nationality.getIsoCode().toUpperCase() + "_" + flag.getSubmittedFileName();

        	String uploadPath = Config.get("uploads.path").replace("\"", "");

        	File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();
            
            File file = new File(uploadDir, filename);
            
            Files.copy(flag.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            
            nationality.setFlagImage(filename);
            
        } else {
            
        	if ("edit".equals(action)) {
            
        		String oldFlagImage = request.getParameter("currentFlagImage");
                nationality.setFlagImage(oldFlagImage);
            
        	} else {
            
        		nationality.setFlagImage("-");
            
        	}
        
        }
        
        nationality.setContinent(Continent.valueOf(request.getParameter("continent")));
    	
        return nationality;
    
	}
	
	private boolean checkIsoCode(String isoCode) {
		
		return isoCode.length() == 3;
		
	}
	
	private boolean checkPeopleAndAssociations(Integer id, Logic ctrl) throws SQLException {
    	
    	LinkedList<Person> people = ctrl.getPeopleByNationalityId(id);
    	LinkedList<Association> associations = ctrl.getAssociationsByNationalityId(id);

    	return people.size() == 0 && associations.size() == 0;
    
    }
	
	private String checkUniqueness(Nationality n, Logic ctrl) throws SQLException {
	    
	    Nationality matchName = ctrl.getNationalityByName(n.getName());
	    
	    if (matchName != null) {
	        
	    	if (n.getId() == 0 || matchName.getId() != n.getId()) {
	        
	        	return "Ya existe una nacionalidad con el nombre '" + n.getName() + "'";
	    
	        }
	    
	    }

	    Nationality matchIso = ctrl.getNationalityByIsoCode(n.getIsoCode());
	    
	    if (matchIso != null) {
	        
	    	if (n.getId() == 0 || matchIso.getId() != n.getId()) {
	        
	        	return "Ya existe una nacionalidad con el Código ISO '" + n.getIsoCode() + "'";
	    
	        }
	    
	    }

	    return null;
 
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		try {
			
			if ("edit".equals(action)) {
				
				Nationality nationality = ctrl.getNationalityById(Integer.parseInt(request.getParameter("id")));
				
				if (nationality != null) {
				
					request.setAttribute("nationality", nationality);
					request.getRequestDispatcher("WEB-INF/Edit/EditNationality.jsp").forward(request, response);
			
				} else {
			        
			        request.setAttribute("errorMessage", "La nacionalidad solicitada no existe");
			        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			    
			    }
					
			} else if ("add".equals(action)) {
				
				request.getRequestDispatcher("WEB-INF/Add/AddNationality.jsp").forward(request, response);
			
			} else {
				
				LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
			    request.setAttribute("nationalitiesList", nationalities);
			    request.getRequestDispatcher("/WEB-INF/Management/NationalityManagement.jsp").forward(request, response);
			
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
            	
        		Nationality nationality = buildNationalityFromRequest(request, action);
        		
        		if (!checkIsoCode(nationality.getIsoCode())) {
                    
        			request.setAttribute("errorMessage", "Error en el Código ISO, debe tener 3 caracteres");
                    request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
                    return;
                    
        		}
        		
        		nationality.setIsoCode(nationality.getIsoCode().toUpperCase());
        		
        		String duplicateError = checkUniqueness(nationality, ctrl);
                
        		if (duplicateError != null) {
                    
                	request.setAttribute("errorMessage", duplicateError);
                    request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
                    return;
                
                }
        		
                ctrl.addNationality(nationality);
        		        		
            } else if ("edit".equals(action)) {
            	
            	Nationality nationality = buildNationalityFromRequest(request, action);
            	
            	if (!checkIsoCode(nationality.getIsoCode())) {
                    
            		request.setAttribute("errorMessage", "Error en el Código ISO, debe tener 3 caracteres");
                    request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
                    return;
                
            	}
            	
            	nationality.setIsoCode(nationality.getIsoCode().toUpperCase());
                
                String duplicateError = checkUniqueness(nationality, ctrl);
                
                if (duplicateError != null) {
                    
                	request.setAttribute("errorMessage", duplicateError);
                    request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
                    return;
                
                }
            	
                ctrl.updateNationality(nationality);
            	
            } else if ("delete".equals(action)){
            	
            	Integer id = Integer.parseInt(request.getParameter("id"));

            	if (checkPeopleAndAssociations(id, ctrl)) {

            		ctrl.deleteNationality(id);

            	} else {
            		
					request.setAttribute("errorMessage", "No se puede eliminar una nacionalidad vinculada a personas o asociaciones");
            		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
            		return;
            	
				}
            	
            }
    	    
            response.sendRedirect("actionnationality");

        	
        } catch (SQLException e) {

        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
        } 
		
	}

}