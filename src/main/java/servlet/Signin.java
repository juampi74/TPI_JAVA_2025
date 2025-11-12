package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Club;
import entities.Contract;
import entities.Player;
import entities.Tournament;
import logic.Logic;

@WebServlet({"/signin", "/SIGNIN", "/Signin", "/SignIn", "/signIn"})
public class Signin extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {

            Logic ctrl = new Logic();
            Club club = ctrl.getClubMoreContracts();
            Contract contract = ctrl.getNextExpiringContract();
            LinkedList<Tournament> tournaments = ctrl.getAllTournaments();

            request.setAttribute("nextExpiringContract", contract);
            request.setAttribute("clubMoreContracts", club);
            request.setAttribute("tournaments", tournaments);
            request.getRequestDispatcher("/WEB-INF/main.jsp").forward(request, response);

        } catch (SQLException e) {
            request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        }

    }

}
