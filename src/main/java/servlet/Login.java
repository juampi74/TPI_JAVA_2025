package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import utils.Security;

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
            
            moveAttribute(session, request, "flash");
            moveAttribute(session, request, "cssClass");
            moveAttribute(session, request, "tempOrigin", "origin");
            
        }
        
        request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String origin = request.getParameter("origin");
        
        Logic ctrl = new Logic();
        User user = null;
        
        try {
        
        	user = ctrl.getUserByEmail(email);
        
        } catch (SQLException e) {
        	
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
            return;
        
        }
        
        String errorFlash = null;

        if (user == null || !Security.checkPassword(password, user.getPassword())) {
            
            errorFlash = "Email o contraseña incorrectos";
        
        } else if (!user.isActive()) {
            
            errorFlash = "Tu cuenta está pendiente de aprobación por un Administrador";
        
        }

        if (errorFlash != null) {

            request.setAttribute("flash", errorFlash);
            request.setAttribute("cssClass", "alert-danger");
            request.setAttribute("prevEmail", email);
            request.setAttribute("origin", origin);
            
            request.getRequestDispatcher("/WEB-INF/Login.jsp").forward(request, response);
            
        } else {

            HttpSession oldSession = request.getSession(false);
            
            if (oldSession != null) oldSession.invalidate();
            
            HttpSession session = request.getSession(true);
            
            session.setAttribute("user", user);
            
            if (origin != null && !origin.isEmpty()) {
                
            	response.sendRedirect(origin);
            
            } else {
                
            	response.sendRedirect(request.getContextPath() + "/Home"); 
            
            }
        
        }
    
    }
    
    private void moveAttribute(HttpSession session, HttpServletRequest request, String key) {
        
    	moveAttribute(session, request, key, key);
    
    }

    private void moveAttribute(HttpSession session, HttpServletRequest request, String sessionKey, String requestKey) {
    
    	Object value = session.getAttribute(sessionKey);
        
        if (value != null) {
        
        	request.setAttribute(requestKey, value);
            session.removeAttribute(sessionKey);
        
        }
    
    }

}