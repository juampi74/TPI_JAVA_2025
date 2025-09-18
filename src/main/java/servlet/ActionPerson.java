package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Person;
import logic.Logic;

@WebServlet("/actionperson")
public class ActionPerson extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)){
			
			int id = Integer.parseInt(request.getParameter("id"));
			Person p = new Person();
			p.setId(id);
			Person person = ctrl.getPersonById(p);
			request.setAttribute("person", person);
			request.getRequestDispatcher("WEB-INF/EditPerson.jsp").forward(request, response);
		
		} else if ("add".equals(action)) {
			
			request.getRequestDispatcher("WEB-INF/AddPerson.jsp").forward(request, response);
		
		} else {
			
			LinkedList<Person> people = ctrl.getAllPeople();
		    request.setAttribute("peopleList", people);
		    request.getRequestDispatcher("/WEB-INF/PersonManagement.jsp").forward(request, response);
		
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        if ("add".equals(action)) {
        	
        	int id = Integer.parseInt(request.getParameter("id"));
    		String fullname = request.getParameter("fullname");
    		LocalDate birthdate = LocalDate.parse(request.getParameter("birthdate"));
    	    String address = request.getParameter("address");
    	    
    	    Person p = new Person();
    	    p.setId(id);
    	    p.setFullname(fullname);
    	    p.setBirthdate(birthdate);
    	    p.setAddress(address);
    	    
    	    ctrl.addPerson(p);
        	
        } else if ("edit".equals(action)) {
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	String fullname = request.getParameter("fullname");
    		LocalDate birthdate = LocalDate.parse(request.getParameter("birthdate"));
    	    String address = request.getParameter("address");
    	       		
    	    Person p = new Person();
    	    p.setId(id);
    	    p.setFullname(fullname);
    	    p.setBirthdate(birthdate);
    	    p.setAddress(address);
    	    
    	    ctrl.updatePerson(p);
    	    
        } else if ("delete".equals(action)){
        	
        	int id = Integer.parseInt(request.getParameter("id"));
        	
    	    Person p = new Person();
    	    p.setId(id);
    	    ctrl.deletePerson(p);
    	    
        }
	    
	    LinkedList<Person> people = ctrl.getAllPeople();
		request.setAttribute("peopleList", people);
	    request.getRequestDispatcher("WEB-INF/PersonManagement.jsp").forward(request, response);
	    
	}

}