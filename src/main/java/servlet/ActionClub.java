package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import logic.Logic;

/**
 * Servlet implementation class ActionClub
 */
@WebServlet("/actionclub")
public class ActionClub extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ActionClub() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		Logic ctrl = new Logic();
		
		if ("edit".equals(action)){
			int id = Integer.parseInt(request.getParameter("id"));
			Club c = new Club();
			c.setId(id);
			Club club = ctrl.getClubById(c);
			request.setAttribute("club", club);
			request.getRequestDispatcher("WEB-INF/EditClub.jsp").forward(request, response);
		} else if ("add".equals(action)) {
			request.getRequestDispatcher("WEB-INF/AddClub.jsp").forward(request, response);
		} else {
			LinkedList<Club> clubs = ctrl.getAllClubs();
		    request.setAttribute("clubsList", clubs);
		    request.getRequestDispatcher("/WEB-INF/ClubManagement.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");

        Logic ctrl = new Logic();
        
        
        if ("add".equals(action)) {
        	Club c = new Club();
            c.setName(request.getParameter("name"));
            c.setFoundation_date(java.time.LocalDate.parse(request.getParameter("foundation_date")));
            c.setPhone_number(request.getParameter("phone_number"));
            c.setEmail(request.getParameter("email"));
            c.setBadge_image(request.getParameter("badge_image"));
            c.setBudget(Double.parseDouble(request.getParameter("budget")));
    	    
    	    ctrl.addClub(c);
        	
        } else if ("edit".equals(action)) {
        	Club c = new Club();
        	c.setId(Integer.parseInt(request.getParameter("id")));
            c.setName(request.getParameter("name"));
            c.setFoundation_date(java.time.LocalDate.parse(request.getParameter("foundation_date")));
            c.setPhone_number(request.getParameter("phone_number"));
            c.setEmail(request.getParameter("email"));
            c.setBadge_image(request.getParameter("badge_image"));
            c.setBudget(Double.parseDouble(request.getParameter("budget")));
    	    
    	    ctrl.updateClub(c);
    	    
        } else if ("delete".equals(action)){
        	Club c = new Club();
        	int id = Integer.parseInt(request.getParameter("id"));
        	
    	    
    	    c.setId(id);
    	    ctrl.deleteClub(c);
        }
	    
	    LinkedList<Club> clubs = ctrl.getAllClubs();
		request.setAttribute("clubsList", clubs);
	    request.getRequestDispatcher("WEB-INF/ClubManagement.jsp").forward(request, response);
	}

}
