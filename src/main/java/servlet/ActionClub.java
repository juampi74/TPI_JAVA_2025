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

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");

        Logic ctrl = new Logic();

        if ("edit".equals(action)) {
            
            Club c = new Club();
            c.setId(Integer.parseInt(request.getParameter("id")));
            Club club = ctrl.getClubById(c);
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
            
        	Club c = new Club();
            c.setName(request.getParameter("name"));
            c.setFoundationDate(LocalDate.parse(request.getParameter("foundationDate")));
            c.setPhoneNumber(request.getParameter("phoneNumber"));
            c.setEmail(request.getParameter("email"));
            c.setBadgeImage(request.getParameter("badgeImage"));
            c.setBudget(Double.parseDouble(request.getParameter("budget")));
            
            Stadium clubStadium = new Stadium();
            clubStadium.setId(Integer.parseInt(request.getParameter("id_stadium")));
            c.setStadium(ctrl.getStadiumById(clubStadium));

            ctrl.addClub(c);

        } else if ("edit".equals(action)) {
            
        	Club c = new Club();
            c.setId(Integer.parseInt(request.getParameter("id")));
            c.setName(request.getParameter("name"));
            c.setFoundationDate(LocalDate.parse(request.getParameter("foundationDate")));
            c.setPhoneNumber(request.getParameter("phoneNumber"));
            c.setEmail(request.getParameter("email"));
            c.setBadgeImage(request.getParameter("badgeImage"));
            c.setBudget(Double.parseDouble(request.getParameter("budget")));
            
            Stadium clubStadium = new Stadium();
            clubStadium.setId(Integer.parseInt(request.getParameter("id_stadium")));
            c.setStadium(ctrl.getStadiumById(clubStadium));

            ctrl.updateClub(c);

        } else if ("delete".equals(action)) {
            
        	Club c = new Club();
            c.setId(Integer.parseInt(request.getParameter("id")));
            ctrl.deleteClub(c);
            
        }

        LinkedList<Club> clubs = ctrl.getAllClubs();
        request.setAttribute("clubsList", clubs);
        request.getRequestDispatcher("WEB-INF/ClubManagement.jsp").forward(request, response);
    
    }

}