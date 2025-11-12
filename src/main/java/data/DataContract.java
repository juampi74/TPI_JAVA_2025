package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataContract {
	
	private static final String SELECT_ALL_CONTRACTS_JOINED = 
		"SELECT " +
	    "    c.id AS contract_id, c.start_date, c.end_date, c.salary, c.release_clause, c.release_date, " +
	    "    p.id AS person_id, p.fullname, p.birthdate, p.address, p.role, " +
	    "    p.dominant_foot, p.jersey_number, p.height, p.weight, " +
	    "    p.preferred_formation, p.coaching_license, p.license_obtained_date, " +
	    "    cl.id AS club_id, cl.name, cl.foundation_date, cl.phone_number, cl.email, cl.badge_image, cl.budget " +
	    "FROM contract c " +
	    "INNER JOIN person p ON c.id_person = p.id " +
	    "INNER JOIN club cl ON c.id_club = cl.id";
	
	public LinkedList<Contract> getAll() throws SQLException {
	    
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Contract> contracts = new LinkedList<>();

	    try {
	        
	    	stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(SELECT_ALL_CONTRACTS_JOINED);
	       
			if (rs != null) {
				 
				while (rs.next()) {
                    
                    Contract contract = mapFullContract(rs);
                    
                    if (contract != null) {
                    	contracts.add(contract);
                    }
                
	            }
			}
 
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        throw new SQLException("No se pudo conectar a la base de datos.", e);
	        
	    } finally {
	        
	    	closeResources(rs, stmt);
	        
	    }
	    
	    return contracts;
	    
	}
    
	public Contract getById(int id) throws SQLException {
        
        Contract contract = null;
        PreparedStatement stmt = null;
		ResultSet rs = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        			SELECT_ALL_CONTRACTS_JOINED + " WHERE c.id = ?"
    			);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
                    
            if (rs != null && rs.next()) {
                
            	contract = mapFullContract(rs);
            
            }
            
        } catch (SQLException e) {
            
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(rs, stmt);
        
        }
        
        return contract;
    }
	
	

	public LinkedList<Contract> getByPersonId(int id) throws SQLException {
	    
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    LinkedList<Contract> contracts = new LinkedList<Contract>();
	    
	    try {
	    	
	    	stmt = DbConnector.getInstance().getConn().prepareStatement(
	    		SELECT_ALL_CONTRACTS_JOINED + " WHERE c.id_person = ?"
			);
	        
	            stmt.setInt(1, id);
	            
	            rs = stmt.executeQuery();
	                
	            if (rs != null) {
	            	
	            	while (rs.next()) {
		                    
	                    Contract contract = mapFullContract(rs);
	                    if (contract != null) {
	                    	contracts.add(contract);
		                    
	                    }
	            	}
	            }
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        throw new SQLException("No se pudo conectar a la base de datos.", e);
	    
	    } finally {
	        
	    	closeResources(rs, stmt);
	    
	    }
	    
	    return contracts;
	}
	
	public LinkedList<Contract> getByClubId(int id) throws SQLException {
	    
		PreparedStatement stmt = null;
		ResultSet rs = null;
	    LinkedList<Contract> contracts = new LinkedList<Contract>();
	
	    try {
	        
	    	stmt = DbConnector.getInstance().getConn().prepareStatement(
	    		SELECT_ALL_CONTRACTS_JOINED + " WHERE c.id_club = ?"
			);	        
	       
	            
	            stmt.setInt(1, id);
	            rs = stmt.executeQuery();
	            
	            if( rs != null) {
	            			
	            	
	            	while (rs.next()) {
	            		
	            		Contract contract = mapFullContract(rs);    
	            		
	            		if(contract != null) {
		                    contracts.add(contract);
	            		}
		                    
		            }
	            }
	        
	    } catch (SQLException e) {
	        
	        e.printStackTrace();
	        throw new SQLException("No se pudo conectar a la base de datos.", e);
	    
	    } finally {
	        
	    	closeResources(rs, stmt);
	    
	    }
	    
	    return contracts;
	}

    public void add(Contract c) throws SQLException {
        
    	PreparedStatement stmt = null;
        
        try {
                  
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        		"INSERT INTO contract (start_date, end_date, salary, release_clause, release_date, id_person, id_club) VALUES (?, ?, ?, ?, ?, ?, ?)"
    		);
            stmt.setObject(1, c.getStartDate());
            stmt.setObject(2, c.getEndDate());
            stmt.setDouble(3, c.getSalary());
            
            if (c.getReleaseClause() != null) {
            	
                stmt.setDouble(4, c.getReleaseClause());
            
            } else {
            
            	stmt.setNull(4, java.sql.Types.DOUBLE);
            
            }

            stmt.setObject(5, c.getReleaseDate());
            
            stmt.setInt(6, c.getPerson().getId());
            stmt.setInt(7, c.getClub().getId());
            
            stmt.executeUpdate();
                
            

        } catch (SQLException e) {
            
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(null, stmt);
        
        }
    }
    
    public void release(int id) throws SQLException {
    	
    	PreparedStatement stmt = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        			"UPDATE contract SET release_date = curdate() WHERE id = ?"
    			);
            stmt.setInt(1, id);
            stmt.executeUpdate();
    
        } catch (SQLException e) {
            
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(null, stmt);
        
        }
    	
    }

    public void delete(int id) throws SQLException {
        
    	PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"DELETE FROM contract WHERE id = ?"
			);
			stmt.setInt(1, id);
			stmt.executeUpdate();
	
		} catch (SQLException e) {
	        
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
	    
			closeResources(null, stmt);
		
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
            
        } else if (role.equals(PersonRole.COACH)) {
            
        	Coach c = new Coach();
            c.setPreferredFormation(rs.getString("preferred_formation"));
            c.setCoachingLicense(rs.getString("coaching_license"));
            c.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));
            person = c;
        
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
        
        Double value = rs.getDouble("release_clause");
        if (rs.wasNull()) {
        	
            contract.setReleaseClause(null);
        
        } else {
        
        	contract.setReleaseClause(value);
        
        }

        contract.setReleaseDate(rs.getObject("release_date", LocalDate.class));

        contract.setPerson(person);
        contract.setClub(club);
        
        return contract;
        
    }
    
    private void closeResources(ResultSet rs, Statement stmt) {
        
		try {
            
			if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DbConnector.getInstance().releaseConn();
        
		} catch (SQLException e) {
        
			e.printStackTrace();
        
		}
    
	}
	
}