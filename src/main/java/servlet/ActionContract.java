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
import enums.PersonRole;
import logic.Logic;

@WebServlet("/actioncontract")
public class ActionContract extends HttpServlet {

	private static final long serialVersionUID = 1L;
    
	private Contract buildContractFromRequest(HttpServletRequest request, String action, Logic ctrl) {
	    
	    Contract contract = new Contract();
	    if (action.equals("edit")) contract.setId(Integer.parseInt(request.getParameter("id")));
	    contract.setStartDate(LocalDate.parse(request.getParameter("startDate")));
	    contract.setEndDate(LocalDate.parse(request.getParameter("endDate")));
	    contract.setSalary(Double.parseDouble(request.getParameter("salary")));
	    contract.setReleaseClause(Double.parseDouble(request.getParameter("releaseClause")));
	    if (action.equals("release")) contract.setReleaseDate(LocalDate.parse(request.getParameter("releaseDate")));
	    
	    PersonRole personRole = ctrl.getRoleByPersonId(Integer.parseInt(request.getParameter("id_person")));
	    
	    if (personRole.equals(PersonRole.PLAYER)) {
	    	
	    	contract.setPerson(ctrl.getPlayerById(Integer.parseInt(request.getParameter("id_person"))));
	    	
	    } else if (personRole.equals(PersonRole.TECHNICAL_DIRECTOR)) {
	    	
	    	contract.setPerson(ctrl.getTechnicalDirectorById(Integer.parseInt(request.getParameter("id_person"))));
	    	
	    }
	    
	    contract.setClub(ctrl.getClubById(Integer.parseInt(request.getParameter("id_club"))));

	    return contract;
	    
	}
	
	private boolean checkDates(LocalDate startDate, LocalDate endDate) {
		
		 LocalDate today = LocalDate.now();

		    if (startDate.isBefore(today)) {
		        return false;
		    }

		    if (endDate.isBefore(startDate.plusMonths(6))) {
		        return false;
		    }

		    return true;
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		Logic ctrl = new Logic();
		
		if ("add".equals(action)) {
			
			LinkedList<Person> people = new LinkedList<>();
			
			LinkedList<Player> players = ctrl.getAvailablePlayers();
			LinkedList<TechnicalDirector> technicalDirectors = ctrl.getAllTechnicalDirectors();
			
			people.addAll(players);
			people.addAll(technicalDirectors);
			
			request.setAttribute("peopleList", people);
			
			LinkedList<Club> clubs = ctrl.getAllClubs();
			request.setAttribute("clubsList", clubs);
			
			request.getRequestDispatcher("WEB-INF/Add/AddContract.jsp").forward(request, response);
		
		} else {
			
			LinkedList<Contract> contracts = ctrl.getAllContracts();
		    request.setAttribute("contractsList", contracts);
		    
			LinkedList<Person> people = new LinkedList<>();
			
			LinkedList<Player> players = ctrl.getAllPlayers();
			LinkedList<TechnicalDirector> technicalDirectors = ctrl.getAllTechnicalDirectors();
			
			people.addAll(players);
			people.addAll(technicalDirectors);
			
			request.setAttribute("peopleList", people);
			
			LinkedList<Club> clubs = ctrl.getAllClubs();
			request.setAttribute("clubsList", clubs);
		    
		    request.getRequestDispatcher("/WEB-INF/Management/ContractManagement.jsp").forward(request, response);
		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
        Logic ctrl = new Logic();
        
    	if ("add".equals(action)) {
        	
    		Contract contract = buildContractFromRequest(request, action, ctrl);
        	if (checkDates(contract.getStartDate(), contract.getEndDate())) {
        		ctrl.addContract(contract);
        	} else {
        		request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
        	}
        	
        } else if ("release".equals(action)) {
        	
        	ctrl.releaseContract(Integer.parseInt(request.getParameter("id")));
        	
        } else if ("delete".equals(action)){
        	
        	ctrl.deleteContract(Integer.parseInt(request.getParameter("id")));
        	
        }

    	LinkedList<Contract> contracts = ctrl.getAllContracts();
		request.setAttribute("contractsList", contracts);
	    request.getRequestDispatcher("WEB-INF/Management/ContractManagement.jsp").forward(request, response);
	    
	}
}