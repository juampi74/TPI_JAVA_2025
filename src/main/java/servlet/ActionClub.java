package servlet;

import java.io.IOException;
import java.time.LocalDate;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import logic.Logic;

@WebServlet("/actionclub")
public class ActionClub extends HttpServlet {

    private static final long serialVersionUID = 1L;
    
    private Club buildClubFromRequest(HttpServletRequest request, Logic ctrl) {
        
    	Club club = new Club();
    	club.setId(Integer.parseInt(request.getParameter("id")));
    	club.setName(request.getParameter("name"));
    	club.setFoundationDate(LocalDate.parse(request.getParameter("foundation_date")));
    	club.setPhoneNumber(request.getParameter("phone_number"));
    	club.setEmail(request.getParameter("email"));
    	club.setBadgeImage(request.getParameter("badge_image"));
    	club.setBudget(Double.parseDouble(request.getParameter("budget")));
    	club.setStadium(ctrl.getStadiumById(Integer.parseInt(request.getParameter("id_stadium"))));
		
        return club;
    
	}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("edit".equals(action)) {
            
        	Club club = ctrl.getClubById(Integer.parseInt(request.getParameter("id")));
			request.setAttribute("club", club);
            
            LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
        	request.setAttribute("stadiumsList", stadiums);
            
        	request.getRequestDispatcher("WEB-INF/EditClub.jsp").forward(request, response);
        
        } else if ("add".equals(action)) {
        
        	LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
        	request.setAttribute("stadiumsList", stadiums);
        	
        	request.getRequestDispatcher("WEB-INF/AddClub.jsp").forward(request, response);
        
        } else {
        
        	LinkedList<Club> clubs = ctrl.getAllClubs();
            request.setAttribute("clubsList", clubs);
            
            LinkedList<Stadium> stadiums = ctrl.getAllStadiums();
        	request.setAttribute("stadiumsList", stadiums);
        	
        	request.getRequestDispatcher("/WEB-INF/ClubManagement.jsp").forward(request, response);
        
        }
    
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("add".equals(action)) {
            
        	ctrl.addClub(buildClubFromRequest(request, ctrl));

        } else if ("edit".equals(action)) {
            
        	ctrl.updateClub(buildClubFromRequest(request, ctrl));

        } else if ("delete".equals(action)) {
            
        	ctrl.deleteClub(Integer.parseInt(request.getParameter("id")));
            
        }

        LinkedList<Club> clubs = ctrl.getAllClubs();
        request.setAttribute("clubsList", clubs);
        request.getRequestDispatcher("WEB-INF/ClubManagement.jsp").forward(request, response);
    
    }

}