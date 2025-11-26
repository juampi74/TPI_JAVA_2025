package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Comparator;
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

    private Contract buildContractFromRequest(HttpServletRequest request, String action, Logic ctrl) throws SQLException {

        Contract contract = new Contract();
        if (action.equals("edit")) contract.setId(Integer.parseInt(request.getParameter("id")));
        contract.setStartDate(LocalDate.parse(request.getParameter("startDate")));
        contract.setEndDate(LocalDate.parse(request.getParameter("endDate")));
        String releaseClause = request.getParameter("releaseClause");
        if (releaseClause != null && !releaseClause.isEmpty()) {

            contract.setReleaseClause(Double.parseDouble(request.getParameter("releaseClause")));

        } else {

            contract.setReleaseClause(null);

        }

        contract.setSalary(Double.parseDouble(request.getParameter("salary")));
        if (action.equals("release")) {
            contract.setReleaseDate(LocalDate.parse(request.getParameter("releaseDate")));
        }

        PersonRole personRole = ctrl.getRoleByPersonId(Integer.parseInt(request.getParameter("id_person")));

        if (personRole.equals(PersonRole.PLAYER)) {

            contract.setPerson(ctrl.getPlayerById(Integer.parseInt(request.getParameter("id_person"))));

        } else if (personRole.equals(PersonRole.COACH)) {

            contract.setPerson(ctrl.getCoachById(Integer.parseInt(request.getParameter("id_person"))));

        }

        contract.setClub(ctrl.getClubById(Integer.parseInt(request.getParameter("id_club"))));

        return contract;

    }

    private boolean checkDates(LocalDate startDate, LocalDate endDate) {

        LocalDate today = LocalDate.now();

        if (startDate.isBefore(today.minusDays(1))) {
            return false;
        }

        if (endDate.isBefore(startDate.plusMonths(6))) {
            return false;
        }

        return true;
    }

    // No debería ser necesario porque si la persona tiene un contrato activo no se muestra en el droplist
    private boolean checkContracts(int id, Logic ctrl) throws SQLException {

        LinkedList<Contract> contracts = ctrl.getContractsByPersonId(id);
        
        boolean hasActiveContract = false;
        
        for (Contract c : contracts) {
            
        	if (c.getEndDate().isAfter(LocalDate.now()) && c.getReleaseDate() == null) {
            
            	hasActiveContract = true;
        
            }
    
        }
        
        return hasActiveContract;
    
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	String action = request.getParameter("action");
        Logic ctrl = new Logic();

        try {

            if ("add".equals(action)) {

                LinkedList<Person> people = new LinkedList<>();

                LinkedList<Player> players = ctrl.getAvailablePlayers();
                LinkedList<Coach> coaches = ctrl.getAvailableCoaches();

                people.addAll(players);
                people.addAll(coaches);

                LinkedList<Club> clubs = ctrl.getAllClubs();

                if (people.size() > 0 && clubs.size() > 0) {
                	
                	people.sort(Comparator.comparing(Person::getFullname));
                	clubs.sort(Comparator.comparing(Club::getName));

                    request.setAttribute("peopleList", people);
                    request.setAttribute("clubsList", clubs);
                    request.getRequestDispatcher("WEB-INF/Add/AddContract.jsp").forward(request, response);

                } else {

                    request.setAttribute("errorMessage", "Debés agregar un club y un jugador o un director técnico primero");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

                }

            } else {

                LinkedList<Contract> contracts = ctrl.getAllContracts();
                request.setAttribute("contractsList", contracts);

                LinkedList<Person> people = new LinkedList<>();

                LinkedList<Player> players = ctrl.getAllPlayers();
                LinkedList<Coach> coaches = ctrl.getAllCoaches();

                people.addAll(players);
                people.addAll(coaches);

                request.setAttribute("peopleList", people);

                LinkedList<Club> clubs = ctrl.getAllClubs();
                request.setAttribute("clubsList", clubs);

                request.getRequestDispatcher("/WEB-INF/Management/ContractManagement.jsp").forward(request, response);

            }

        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
    	
        String action = request.getParameter("action");
        Logic ctrl = new Logic();

        try {

            if ("add".equals(action)) {

                Contract contract = buildContractFromRequest(request, action, ctrl);

                if (!checkDates(contract.getStartDate(), contract.getEndDate())) {

                    request.setAttribute("errorMessage", "Error en las fechas introducidas (el contrato debe empezar a partir de hoy y durar, al menos, 6 meses)");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

                } else if (checkContracts(contract.getPerson().getId(), ctrl)) {

                    request.setAttribute("errorMessage", "La persona ya tiene un contrato activo");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

                } else {

                    ctrl.addContract(contract);

                }

            } else if ("release".equals(action)) {

                ctrl.releaseContract(Integer.parseInt(request.getParameter("id")));

            } else if ("delete".equals(action)) {

                ctrl.deleteContract(Integer.parseInt(request.getParameter("id")));

            } else if ("extend".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                int months = Integer.parseInt(request.getParameter("extension"));
                Contract c = ctrl.getContractById(id);
                LocalDate newEndDate = c.getEndDate().plusMonths(months);
                c.setEndDate(newEndDate);
                ctrl.updateContract(c);
            }

            LinkedList<Contract> contracts = ctrl.getAllContracts();
            request.setAttribute("contractsList", contracts);
            request.getRequestDispatcher("WEB-INF/Management/ContractManagement.jsp").forward(request, response);

        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

        }

    }
}
