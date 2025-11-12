package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataCoach {
	
	private Coach createCoachFromResultSet(ResultSet rs) throws SQLException {
        
		Coach coach = new Coach();
		coach.setId(rs.getInt("id"));
		coach.setFullname(rs.getString("fullname"));
		coach.setBirthdate(rs.getObject("birthdate", LocalDate.class));
		coach.setAddress(rs.getString("address"));
		coach.setRole(PersonRole.valueOf(rs.getString("role")));
		coach.setPreferredFormation(rs.getString("preferred_formation"));
		coach.setCoachingLicense(rs.getString("coaching_license"));
		coach.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));
	    
        return coach;
    
	}
	
	private static final String SELECT_COACH_FIELDS = 
		"SELECT id, fullname, birthdate, address, role, preferred_formation, coaching_license, license_obtained_date FROM person";
	
	public LinkedList<Coach> getAll() throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Coach> coaches = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(SELECT_COACH_FIELDS + " WHERE role = 'COACH'");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Coach coach = createCoachFromResultSet(rs);

	                if (coach != null) {
	                    
	                	coaches.add(coach);
	                    
	                }
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
			
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return coaches;
		
	}
	
	public LinkedList<Coach> getAvailable() throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Coach> coaches = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT p.* FROM person p WHERE p.role = 'COACH' AND NOT EXISTS (SELECT 1 FROM contract c WHERE c.id_person = p.id AND c.release_date IS NULL AND c.end_date >= CURDATE())"
			);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Coach coach = createCoachFromResultSet(rs);
                    
					if (coach != null) {
                    
						coaches.add(coach);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return coaches;
		
	}

	public Coach getById(int id) throws SQLException {
		
		Coach coach = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_COACH_FIELDS + " WHERE role = 'COACH' AND id = ?"
			);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				coach = createCoachFromResultSet(rs);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
			
			closeResources(rs, stmt);
		}
		
		return coach;
	
	}
	
	public LinkedList<Coach> getByFullname(String fullname) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Coach> coaches = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_COACH_FIELDS + " WHERE role = 'COACH' AND fullname = ?"
			);
			stmt.setString(1, fullname);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Coach coach = createCoachFromResultSet(rs);
                    
					if (coach != null) {
                    
						coaches.add(coach);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return coaches;
		
	}
	
	public void add(Coach td) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO person (id, fullname, birthdate, address, role, preferred_formation, coaching_license, license_obtained_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
			);
			stmt.setInt(1, td.getId());
			stmt.setString(2, td.getFullname());
			stmt.setObject(3, td.getBirthdate());
			stmt.setString(4, td.getAddress());
			stmt.setString(5, td.getRole().name());
			stmt.setString(6, td.getPreferredFormation());
	        stmt.setString(7, td.getCoachingLicense());
	        stmt.setObject(8, td.getLicenseObtainedDate());
			
			stmt.executeUpdate();

		}  catch (SQLException e) {
            
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
            
			closeResources(null, stmt);
		
		}
    
	}
	
	public void update(Coach td) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE person SET fullname = ?, birthdate = ?, address = ?, role = ?, preferred_formation = ?, coaching_license = ?, license_obtained_date = ? WHERE id = ?"
			);
			stmt.setString(1, td.getFullname());
			stmt.setObject(2, td.getBirthdate());
			stmt.setString(3, td.getAddress());
			stmt.setString(4, td.getRole().name());
			stmt.setString(5, td.getPreferredFormation());
	        stmt.setString(6, td.getCoachingLicense());
	        stmt.setObject(7, td.getLicenseObtainedDate());

			stmt.setInt(8, td.getId());
			stmt.executeUpdate();

		}  catch (SQLException e) {
          
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
				"DELETE FROM person WHERE id = ?"
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