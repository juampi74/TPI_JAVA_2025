package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.*;
import logic.Logic;

@WebServlet({"/home", "/HOME", "/Home"})
public class Home extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Logic ctrl = new Logic();
            
            Club club = ctrl.getClubWithMostContracts();
            Contract contract = ctrl.getNextExpiringContract();
            LinkedList<Tournament> tournaments = ctrl.getAllTournaments();

            request.setAttribute("nextExpiringContract", contract);
            request.setAttribute("clubWithMostContracts", club);
            request.setAttribute("tournaments", tournaments);
            
            request.getRequestDispatcher("/WEB-INF/Home.jsp").forward(request, response);

        } catch (SQLException e) {
            
        	request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        
        }

    }

}