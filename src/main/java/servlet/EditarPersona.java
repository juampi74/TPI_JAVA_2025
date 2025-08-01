package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.Logic;
import entities.Persona;
/**
 * Servlet implementation class EditarPersona
 */
@WebServlet("/editarpersona")
public class EditarPersona extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditarPersona() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		Logic ctrl = new Logic();
		Persona p = new Persona();
		p.setId(id);
		Persona persona = ctrl.getById(p);
		request.setAttribute("persona", persona);
		request.getRequestDispatcher("WEB-INF/EditarPersona.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		String dni = request.getParameter("dni");
		String apellido_nombre = request.getParameter("apellido_nombre");
		LocalDate fechaNacimiento = LocalDate.parse(request.getParameter("fecha_nacimiento"));
	    String direccion = request.getParameter("direccion");
		
	    Persona p = new Persona();
	    p.setId(id);
	    p.setDni(dni);
	    p.setApellido_nombre(apellido_nombre);
	    p.setFecha_nacimiento(fechaNacimiento);
	    p.setDireccion(direccion);
	    
	    Logic ctrl = new Logic();
	    ctrl.updatePersona(p);
	    
	    LinkedList<Persona> personas = ctrl.getAll();
		
		request.setAttribute("listaPersonas", personas);
	    
	    request.getRequestDispatcher("WEB-INF/PersonaManagement.jsp").forward(request, response);
	}

}
