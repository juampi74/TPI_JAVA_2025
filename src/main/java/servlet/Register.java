package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import utils.Security;

import entities.Nationality;
import entities.User;
import enums.UserRole;
import logic.Logic;

@WebServlet("/register")
public class Register extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Logic ctrl = new Logic();
		
		try {
		
			LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
			nationalities.sort(Comparator.comparing(Nationality::getName));
			
			request.setAttribute("nationalitiesList", nationalities);
			request.getRequestDispatcher("WEB-INF/Register.jsp").forward(request, response);
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			request.setAttribute("errorMessage", "Error al cargar el formulario de registro (Nacionalidades)");
			request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
		
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		String idPerStr = request.getParameter("idPerson");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String confirmPassword = request.getParameter("confirmPassword");
		String idNatStr = request.getParameter("idNationality");
		String roleStr = request.getParameter("role");
		
		String fullname = firstname + " " + lastname;

		Logic ctrl = new Logic();

		try {
			
			if (idPerStr == null || idPerStr.isEmpty()) {
				
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "Por favor, ingresá tu DNI");
				return;
				
			}
			
			int idPerson = Integer.parseInt(idPerStr);

			if (idNatStr == null || idNatStr.isEmpty()) {
				
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "Por favor, seleccioná una nacionalidad");
				return;
			
			}
			
			int idNationality = Integer.parseInt(idNatStr);

			if (roleStr == null || roleStr.isEmpty()) {
			
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "Por favor, seleccioná un rol");
				return;
			
			}
			
			if ("ADMIN".equals(roleStr)) {
			
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "Rol no válido");
				return;
			
			}

			if (!password.equals(confirmPassword)) {

				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "Las contraseñas no coinciden");
				return;
			
			}
			
			User existingUser = ctrl.getUserByEmail(email);
			
			if (existingUser != null) {
			
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, "El email ya está registrado");
				return;
			
			}

			User newUser = new User();

			newUser.setEmail(email);
			newUser.setPassword(Security.hashPassword(password));
			newUser.setRole(UserRole.valueOf(roleStr));
			newUser.setActive(false);

			ctrl.registerUser(newUser, idPerson, fullname, idNationality);

			HttpSession session = request.getSession();
			session.setAttribute("flash", "¡Solicitud enviada! Tu cuenta está pendiente de aprobación por un Administrador");
			session.setAttribute("cssClass", "alert-success");
			
			request.setAttribute("prevEmail", email);
			
			response.sendRedirect(request.getContextPath() + "/login");

		} catch (Exception e) {
			
			e.printStackTrace();
			
			String errorMsg = "Ocurrió un error inesperado";
			
			if (e instanceof NumberFormatException) {
			
				errorMsg = "Error en el formato de datos";
			
			} else if (e instanceof SQLException) {
			
				errorMsg = "Error de base de datos al registrar";
			
			}
			
			try {
			
				reloadFormOnError(request, response, ctrl, idPerStr, firstname, lastname, email, roleStr, errorMsg);
			
			} catch (Exception ex) {
			
				ex.printStackTrace();
				request.setAttribute("errorMessage", "Error crítico del sistema: " + ex.getMessage());
				request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
			
			}
			
		}
		
	}
	
	private void reloadFormOnError(HttpServletRequest request, HttpServletResponse response, Logic ctrl, 
			String idPerson, String name, String lastname, String email, String role, String errorMsg) throws ServletException, IOException, SQLException {
		
		request.setAttribute("prevId", idPerson);
		request.setAttribute("prevName", name);
		request.setAttribute("prevLastname", lastname);
		request.setAttribute("prevEmail", email);
		request.setAttribute("prevRole", role);
		request.setAttribute("flash", errorMsg);
		
		LinkedList<Nationality> nationalities = ctrl.getAllNationalities();
		nationalities.sort(Comparator.comparing(Nationality::getName));
		request.setAttribute("nationalitiesList", nationalities);
		
		request.getRequestDispatcher("WEB-INF/Register.jsp").forward(request, response);
	
	}

}