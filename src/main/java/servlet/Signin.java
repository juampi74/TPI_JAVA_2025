package servlet;

import java.io.IOException;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entities.Player;
import logic.Logic;

@WebServlet({ "/signin", "/SIGNIN", "/Signin", "/SignIn", "/signIn" })
public class Signin extends HttpServlet {

	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		System.out.println(">>> ENTRO AL SERVLET SIGNIN <<<");
		
		Logic ctrl = new Logic();
		
	    LinkedList<Player> players = ctrl.getAllPlayers();
	    
	    request.setAttribute("playersList", players);
	    
	    request.getRequestDispatcher("/WEB-INF/Management/PlayerManagement.jsp").forward(request, response);
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO
	}

}