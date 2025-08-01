package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Persona;
import logic.Logic;

/**
 * Servlet implementation class AgregarPersona
 */
@WebServlet("/agregarpersona")
public class AgregarPersona extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AgregarPersona() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/AgregarPersona.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String dni = request.getParameter("dni");
		String apellido_nombre = request.getParameter("apellido_nombre");
		LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fecha_nacimiento"));
	    String direccion = request.getParameter("direccion");
		
	    Persona p = new Persona();
	    p.setDni(dni);
	    p.setApellido_nombre(apellido_nombre);
	    p.setFecha_nacimiento(fechaNacimiento);
	    p.setDireccion(direccion);
	    
	    Logic ctrl = new Logic();
	    ctrl.addPersona(p);
	    
	    LinkedList<Persona> personas = ctrl.getAll();
		
		request.setAttribute("listaPersonas", personas);
	    
	    request.getRequestDispatcher("WEB-INF/PersonaManagement.jsp").forward(request, response);
	}

}
