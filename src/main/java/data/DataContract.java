package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataContract {
	
	private static final String SELECT_ALL_CONTRACTS_JOINED= 
		"SELECT " +
	    "    c.id AS contract_id, c.start_date, c.end_date, c.salary, c.release_clause, c.release_date, " +
	    "    p.id AS person_id, p.fullname, p.birthdate, p.address, p.role, " +
	    "    p.dominant_foot, p.jersey_number, p.height, p.weight, " +
	    "    p.preferred_formation, p.coaching_license, p.license_obtained_date, " +
	    "    cl.id AS club_id, cl.name, cl.foundation_date, cl.phone_number, cl.email, cl.badge_image, cl.budget " +
	    "FROM contract c " +
	    "INNER JOIN person p ON c.id_person = p.id " +
	    "INNER JOIN club cl ON c.id_club = cl.id";
	
	public LinkedList<Contract> getAll() {
	    
	    LinkedList<Contract> contracts = new LinkedList<>();
	    Connection conn = null;

	    try {
	        
	        conn = DbConnector.getInstance().getConn(); 
	        
	        try (Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(SELECT_ALL_CONTRACTS_JOINED)) {

	            while (rs.next()) {
	                
	                try {
	                    
	                    Contract contract = mapFullContract(rs);
	                    
	                    contracts.add(contract);
	                    
	                } catch (SQLException e) {
	                    
	                    System.err.println(e.getMessage() + ". Omitiendo contrato.");
	                    	                
	                }
	            }
	            
	        }
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        
	    } finally {
	        
	        if (conn != null) DbConnector.getInstance().releaseConn();
	        
	    }
	    
	    return contracts;
	    
	}
    
	public Contract getById(int id) {
        
        Contract contract = null;
        Connection conn = null;
        String query = SELECT_ALL_CONTRACTS_JOINED + " WHERE c.id = ?";

        try {
            
            conn = DbConnector.getInstance().getConn();
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, id);
                
                try (ResultSet rs = stmt.executeQuery()) {
                    
                    if (rs != null && rs.next()) {
                        
                    	contract = mapFullContract(rs);
                    
                    }
                }
            }
            
        } catch (SQLException e) {
            
            e.printStackTrace();
        
        } finally {
            
            if (conn != null) DbConnector.getInstance().releaseConn();
        
        }
        
        return contract;
    }

    public void add(Contract c) {
        
        Connection conn = null;
        String query = "INSERT INTO contract (start_date, end_date, salary, release_clause, release_date, id_person, id_club) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            
            conn = DbConnector.getInstance().getConn();
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setObject(1, c.getStartDate());
                stmt.setObject(2, c.getEndDate());
                stmt.setDouble(3, c.getSalary());
                stmt.setDouble(4, c.getReleaseClause());
                stmt.setObject(5, c.getReleaseDate());
                
                stmt.setInt(6, c.getPerson().getId());
                stmt.setInt(7, c.getClub().getId());
                
                stmt.executeUpdate();
                
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        
        } finally {
            
            if (conn != null) DbConnector.getInstance().releaseConn();
        
        }
    }

    public void update(Contract c) {
        
        Connection conn = null;
        String query = "UPDATE contract SET start_date = ?, end_date = ?, salary = ?, release_clause = ?, release_date = ?, id_person = ?, id_club = ? WHERE id = ?";
        
        try {
            
            conn = DbConnector.getInstance().getConn();
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setObject(1, c.getStartDate());
                stmt.setObject(2, c.getEndDate());
                stmt.setDouble(3, c.getSalary());
                stmt.setDouble(4, c.getReleaseClause());
                stmt.setObject(5, c.getReleaseDate());
                stmt.setInt(6, c.getPerson().getId());
                stmt.setInt(7, c.getClub().getId());
                
                stmt.setInt(8, c.getId());
                
                stmt.executeUpdate();
                
            }

        } catch (SQLException e) {
            
            e.printStackTrace();
        
        } finally {
            
            if (conn != null) DbConnector.getInstance().releaseConn();
        
        }
        
    }
    
    public void release(int id) {
    	
    	Connection conn = null;
        String query = "UPDATE contract SET release_date = curdate() WHERE id = ?";
        
        try {
            
            conn = DbConnector.getInstance().getConn();
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, id);
                stmt.executeUpdate();
                
            }
    
        } catch (SQLException e) {
            
            e.printStackTrace();
        
        } finally {
            
            if (conn != null) DbConnector.getInstance().releaseConn();
        
        }
    	
    }

    public void delete(int id) {
        
        Connection conn = null;
        String query = "DELETE FROM contract WHERE id = ?";
        
        try {
            
            conn = DbConnector.getInstance().getConn();
            
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                
                stmt.setInt(1, id);
                stmt.executeUpdate();
                
            }
    
        } catch (SQLException e) {
            
            e.printStackTrace();
        
        } finally {
            
            if (conn != null) DbConnector.getInstance().releaseConn();
        
        }
        
    }
	
    private Contract mapFullContract(ResultSet rs) throws SQLException {
        
        Person person = null;
        PersonRole role = null;
        
        try {
        	
            role = PersonRole.valueOf(rs.getString("role").toUpperCase());
        
        } catch (Exception e) {
            
            throw new SQLException("Rol inválido en la BD: " + rs.getString("role"), e);
        
        }
        
        if (role.equals(PersonRole.PLAYER)) {
            
        	Player player = new Player();
            player.setDominantFoot(rs.getString("dominant_foot"));
            player.setJerseyNumber(rs.getInt("jersey_number"));
            player.setHeight(rs.getDouble("height"));
            player.setWeight(rs.getDouble("weight"));
            person = player;
            
        } else if (role.equals(PersonRole.TECHNICAL_DIRECTOR)) {
            
        	TechnicalDirector td = new TechnicalDirector();
            td.setPreferredFormation(rs.getString("preferred_formation"));
            td.setCoachingLicense(rs.getString("coaching_license"));
            td.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));
            person = td;
        
        }
        
        if (person == null) {
        
        	throw new SQLException("Rol no manejado en la lógica: " + role);
        
        }
        
        person.setId(rs.getInt("person_id"));
        person.setFullname(rs.getString("fullname"));
        person.setBirthdate(rs.getObject("birthdate", LocalDate.class));
        person.setAddress(rs.getString("address"));
        person.setRole(role);
        
        Club club = new Club();
        club.setId(rs.getInt("club_id"));
        club.setName(rs.getString("name"));
        club.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
        club.setPhoneNumber(rs.getString("phone_number"));
        club.setEmail(rs.getString("email"));
        club.setBadgeImage(rs.getString("badge_image"));
        club.setBudget(rs.getDouble("budget"));
        
        Contract contract = new Contract();
        contract.setId(rs.getInt("contract_id"));
        contract.setStartDate(rs.getObject("start_date", LocalDate.class));
        contract.setEndDate(rs.getObject("end_date", LocalDate.class));
        contract.setSalary(rs.getDouble("salary"));
        contract.setReleaseClause(rs.getDouble("release_clause"));
        contract.setReleaseDate(rs.getObject("release_date", LocalDate.class));

        contract.setPerson(person);
        contract.setClub(club);
        
        return contract;
        
    }
	
}