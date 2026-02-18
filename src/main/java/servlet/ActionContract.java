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
import javax.servlet.http.HttpSession;

import entities.*;
import enums.PersonRole;
import enums.UserRole;
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

        } else if (personRole.equals(PersonRole.PRESIDENT)) {

            contract.setPerson(ctrl.getPresidentById(Integer.parseInt(request.getParameter("id_person"))));

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
                LinkedList<President> presidents = ctrl.getAvailablePresidents();

                people.addAll(players);
                people.addAll(coaches);
                people.addAll(presidents);
                
                HttpSession session = request.getSession(false);
            	User userLogged = null;
            	LinkedList<Contract> contracts = null;
            	if (session != null) {
            	    userLogged = (User) session.getAttribute("user");
            	}
            	
            	LinkedList<Club> clubs = new LinkedList<>();
            	
            	if (userLogged != null) {
            		if (userLogged.getRole() != UserRole.ADMIN) {
	            		Club club = ctrl.getClubByPersonId(userLogged.getPerson().getId());
	            		if (club != null) {	            			
	            			clubs.add(club);
	            		}
            		} else {
            			clubs = ctrl.getAllClubs();
            		}
            	} else {
            		clubs = ctrl.getAllClubs();
            	}

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
            	HttpSession session = request.getSession(false);
            	User userLogged = null;
            	LinkedList<Contract> contracts = null;
            	if (session != null) {
            	    userLogged = (User) session.getAttribute("user");
            	}
            	
            	if (userLogged != null) {
            		if (userLogged.getRole() != UserRole.ADMIN) {
	            		Club club = ctrl.getClubByPersonId(userLogged.getPerson().getId());
	            		if (club != null) {
	            			contracts = ctrl.getContractsByClubId(club.getId());
	            		}
            		} else {
            			contracts = ctrl.getAllContracts();
            		}
            	} else {
            		contracts = ctrl.getAllContracts();
            	}
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
                    return;

                } else if (checkContracts(contract.getPerson().getId(), ctrl)) {

                    request.setAttribute("errorMessage", "La persona ya tiene un contrato activo");
                    request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);
                    return;

                } else {

                    ctrl.addContract(contract);

                }

            } else if ("release".equals(action)) {
            	Integer release_id = Integer.parseInt(request.getParameter("id"));
            	Contract contract = ctrl.getContractById(release_id);
            	HttpSession session = request.getSession(false);
            	User userLogged = null;
            	if (session != null) {
            	    userLogged = (User) session.getAttribute("user");
            	}
            	
            	if (userLogged != null) {
            		if (userLogged.getRole() != UserRole.ADMIN) {
	            		if(contract.getPerson().getId() == userLogged.getPerson().getId()) {
	            			ctrl.releaseContract(release_id);
	            			response.sendRedirect("home");
	            			return;
	            		} else {
	            			ctrl.releaseContract(release_id);
	            		}
	            		
            		} else {
            			ctrl.releaseContract(release_id);
            		}
            	}
            } else if ("delete".equals(action)) {
                Integer release_id = Integer.parseInt(request.getParameter("id"));
            	Contract contract = ctrl.getContractById(release_id);
            	HttpSession session = request.getSession(false);
            	User userLogged = null;
            	if (session != null) {
            	    userLogged = (User) session.getAttribute("user");
            	}
            	
            	if (userLogged != null) {
            		if (userLogged.getRole() != UserRole.ADMIN) {
	            		if(contract.getPerson().getId() == userLogged.getPerson().getId() && contract.getReleaseDate() == null && contract.getEndDate().isAfter(LocalDate.now())) {
	            			ctrl.deleteContract(release_id);
	            			response.sendRedirect("home");
	            			return;
	            		} else {
	            			ctrl.deleteContract(release_id);
	            		}
	            		
            		} else {
            			ctrl.deleteContract(release_id);
            		}
            	}
            } else if ("extend".equals(action)) {
                
            	int id = Integer.parseInt(request.getParameter("id"));
                int months = Integer.parseInt(request.getParameter("extension"));
                
                Contract c = ctrl.getContractById(id);
                LocalDate newEndDate = c.getEndDate().plusMonths(months);
                
                c.setEndDate(newEndDate);
                
                ctrl.updateContract(c);
            
            }

            response.sendRedirect("actioncontract");

        } catch (SQLException e) {

            request.setAttribute("errorMessage", "Error al conectarse a la base de datos");
            request.getRequestDispatcher("WEB-INF/ErrorMessage.jsp").forward(request, response);

        }

    }

}