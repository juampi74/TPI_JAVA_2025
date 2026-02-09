package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import entities.*;
import logic.Logic;

@WebServlet("/admin/users")
public class AdminUsers extends HttpServlet {
    
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	    Logic ctrl = new Logic();
	    
	    try {

	        HttpSession session = request.getSession(false);
	        
	        if (session != null) {
	            
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

	        LinkedList<User> pendingUsers = ctrl.getPendingUsers();
	        request.setAttribute("pendingUsersList", pendingUsers);
	        
	        request.getRequestDispatcher("/WEB-INF/admin/PendingUsers.jsp").forward(request, response);
	        
	    } catch (SQLException e) {
	        
	    	request.setAttribute("errorMessage", "Error de base de datos");
	        request.getRequestDispatcher("/WEB-INF/ErrorMessage.jsp").forward(request, response);
	    
	    }
	
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        String idStr = request.getParameter("userId");
        
        HttpSession session = request.getSession();

        if (idStr != null && !idStr.isEmpty()) {
            
            try {
                
            	Logic ctrl = new Logic();
                int userId = Integer.parseInt(idStr);

                if ("approve".equals(action)) {
                    
                    ctrl.approveUser(userId);
                    
                    session.setAttribute("flash", "Usuario aprobado correctamente!");
                    session.setAttribute("cssClass", "alert-success");

                } else if ("reject".equals(action)) {
                    
                    ctrl.rejectUser(userId); 
                    
                    session.setAttribute("flash", "Solicitud rechazada! El usuario ha sido eliminado");
                    session.setAttribute("cssClass", "alert-danger");
                
                }

            } catch (NumberFormatException e) {
                
            	session.setAttribute("flash", "ID de usuario inválido");
                session.setAttribute("cssClass", "alert-danger");
            
            } catch (SQLException e) {

                e.printStackTrace();
                session.setAttribute("flash", "Error en el sistema!");
                session.setAttribute("cssClass", "alert-warning");
            
            }
        
        }

        response.sendRedirect(request.getRequestURI());
    
    }
    
}