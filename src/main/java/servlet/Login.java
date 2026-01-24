package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.mindrot.jbcrypt.BCrypt;

import entities.User;
import logic.Logic;

@WebServlet({"/login", "/LOGIN", "/Login"})
public class Login extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        if (request.getParameter("logout") != null) {
            
        	HttpSession session = request.getSession(false);
            
        	if (session != null) {
            
        		session.invalidate();
            
        	}
        	
        	response.sendRedirect(request.getContextPath() + "/login");
            return;
        
        }
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            
            String flashMessage = (String) session.getAttribute("flash");
            
            if (flashMessage != null) {

            	request.setAttribute("flash", flashMessage);
                session.removeAttribute("flash");
            
            }
            
            String tempOrigin = (String) session.getAttribute("tempOrigin");
            
            if (tempOrigin != null) {
                
            	request.setAttribute("origin", tempOrigin);
                session.removeAttribute("tempOrigin");
            }
            
        }
        
        request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
              
    	String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        Logic ctrl = new Logic();
        User user = null;
        
        try {
            
            user = ctrl.getUserByEmail(email);
                       
        } catch (SQLException e) {
        	
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
	        request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
	        return;
        
        }
        
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            
            HttpSession session = request.getSession();
            
            session.setAttribute("user", user);
            
            String origin = request.getParameter("origin");
            
            if (origin != null && !origin.isEmpty()) {
                
                response.sendRedirect(origin);
                
            } else {
                
                response.sendRedirect(request.getContextPath() + "/Home"); 
            
            }
            
        } else {
            
        	String origin = request.getParameter("origin");
            request.setAttribute("origin", origin);
        	
        	request.setAttribute("flash", "Email o contraseña incorrectos");
            
            request.setAttribute("prevEmail", email);
            
            request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);

        }
    
    }

}