package servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import entities.*;
import enums.*;
import logic.Logic;
import utils.Config;

@MultipartConfig
@WebServlet("/my-profile")
public class MyProfile extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	private String nullIfEmpty(String value) {
	 
		return (value == null || value.trim().isEmpty()) ? null : value.trim();
	
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession(false);
		
		if (session != null) {

			User userInSession = (User) session.getAttribute("user");
			
			if (userInSession != null) {
				
				try {
					
					Logic ctrl = new Logic();
					
					User freshUser = ctrl.getUserByEmail(userInSession.getEmail()); 
					
					session.setAttribute("user", freshUser); 
					
				} catch (SQLException e) {
					
					request.setAttribute("errorMessage", "Error de base de datos: " + e.getMessage());
					request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
					
				}
				
			}

			String flash = (String) session.getAttribute("flash");
	        String cssClass = (String) session.getAttribute("cssClass");

	        if (flash != null) {
	            
	        	request.setAttribute("flash", flash);
	            session.removeAttribute("flash");
	        
	        }
	        
	        if (cssClass != null) {
	        
	        	request.setAttribute("cssClass", cssClass);
	            session.removeAttribute("cssClass");
	        
	        }
	    
		}

	    request.getRequestDispatcher("/WEB-INF/MyProfile.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		HttpSession session = request.getSession();
		
		User user = (User) session.getAttribute("user");
		
		Logic ctrl = new Logic();

		Person p = user.getPerson();

		try {

	        Part photoPart = request.getPart("photo");
	        
	        if (photoPart != null && photoPart.getSize() > 0) {

	            String fileName = p.getId() + "_" + photoPart.getSubmittedFileName();

	            String uploadPath = Config.get("uploads.path").replace("\"", "");
	            
	            File uploadDir = new File(uploadPath);
	            if (!uploadDir.exists()) uploadDir.mkdirs();
	            
	            File file = new File(uploadDir, fileName);
	                            
	            Files.copy(photoPart.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
	            
	            p.setPhoto(fileName);
	            
	        }

	        String birthdateParam = request.getParameter("birthdate");
	        
	        if (nullIfEmpty(birthdateParam) != null) {
	        
	        	p.setBirthdate(LocalDate.parse(birthdateParam));
	        
	        } else {

	            p.setBirthdate(null);
	        
	        }
			
	        p.setAddress(nullIfEmpty(request.getParameter("address")));

			if (user.getRole() == UserRole.PLAYER && p instanceof Player) {
				
				Player player = (Player) p;
				player.setDominantFoot(DominantFoot.valueOf(request.getParameter("dominant_foot")));

			    String heightParam = nullIfEmpty(request.getParameter("height"));
			    player.setHeight(heightParam != null ? Double.parseDouble(heightParam) : null);
			    
			    String weightParam = nullIfEmpty(request.getParameter("weight"));
			    player.setWeight(weightParam != null ? Double.parseDouble(weightParam) : null);
				
				ctrl.updatePlayer(player);

			} else if (user.getRole() == UserRole.COACH && p instanceof Coach) {
				
				Coach coach = (Coach) p;
				coach.setPreferredFormation(nullIfEmpty(request.getParameter("preferred_formation")));
				coach.setCoachingLicense(nullIfEmpty(request.getParameter("coaching_license")));
				
				String licenseObtainedDateParam = request.getParameter("license_date");
			    
				if (nullIfEmpty(licenseObtainedDateParam) != null) {
			        
					coach.setLicenseObtainedDate(LocalDate.parse(licenseObtainedDateParam));
			    
				} else {
			        
					coach.setLicenseObtainedDate(null);
			    
				}
				
				ctrl.updateCoach(coach);
			
			} else if (user.getRole() == UserRole.PRESIDENT && p instanceof President) {
				
				President president = (President) p;
				president.setManagementPolicy(nullIfEmpty(request.getParameter("management_policy")));
				
				ctrl.updatePresident(president);
			
			}

			session.setAttribute("user", user);
			
			session.setAttribute("flash", "¡Tu perfil ha sido actualizado correctamente!");
	        session.setAttribute("cssClass", "alert-success");
	        
	        response.sendRedirect("my-profile");

		} catch (SQLException e) {
			
			request.setAttribute("errorMessage", "Error de base de datos: " + e.getMessage());
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
		
		} catch (Exception e) {
			
			request.setAttribute("errorMessage", "Error al procesar la actualización: " + e.getMessage());
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
		
		}
	
	}

}