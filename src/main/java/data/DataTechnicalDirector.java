package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataTechnicalDirector {
	
	private TechnicalDirector createTechnicalDirectorFromResultSet(ResultSet rs) throws SQLException {
        
		TechnicalDirector technicalDirector = new TechnicalDirector();
		technicalDirector.setId(rs.getInt("id"));
		technicalDirector.setFullname(rs.getString("fullname"));
		technicalDirector.setBirthdate(rs.getObject("birthdate", LocalDate.class));
		technicalDirector.setAddress(rs.getString("address"));
		technicalDirector.setRole(PersonRole.valueOf(rs.getString("role")));
		technicalDirector.setPreferredFormation(rs.getString("preferred_formation"));
		technicalDirector.setCoachingLicense(rs.getString("coaching_license"));
		technicalDirector.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));
	    
        return technicalDirector;
    
	}
	
	private static final String SELECT_TECHNICAL_DIRECTOR_FIELDS = 
		"SELECT id, fullname, birthdate, address, role, preferred_formation, coaching_license, license_obtained_date FROM person";
	
	public LinkedList<TechnicalDirector> getAll(){
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<TechnicalDirector> technicalDirectors = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(SELECT_TECHNICAL_DIRECTOR_FIELDS + " WHERE role = 'TECHNICAL_DIRECTOR'");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					TechnicalDirector technicalDirector = createTechnicalDirectorFromResultSet(rs);

	                if (technicalDirector != null) {
	                    
	                	technicalDirectors.add(technicalDirector);
	                    
	                }
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return technicalDirectors;
		
	}

	public TechnicalDirector getById(int id) {
		
		TechnicalDirector technicalDirector = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_TECHNICAL_DIRECTOR_FIELDS + " WHERE role = 'TECHNICAL_DIRECTOR' AND id = ?"
			);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				technicalDirector = createTechnicalDirectorFromResultSet(rs);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		}
		
		return technicalDirector;
	
	}
	
	public LinkedList<TechnicalDirector> getByFullname(String fullname){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<TechnicalDirector> technicalDirectors = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_TECHNICAL_DIRECTOR_FIELDS + " WHERE role = 'TECHNICAL_DIRECTOR' AND fullname = ?"
			);
			stmt.setString(1, fullname);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					TechnicalDirector technicalDirector = createTechnicalDirectorFromResultSet(rs);
                    
					if (technicalDirector != null) {
                    
						technicalDirectors.add(technicalDirector);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return technicalDirectors;
		
	}
	
	public void add(TechnicalDirector td) {
		
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
		
		} finally {
            
			closeResources(null, stmt);
		
		}
    
	}
	
	public void update(TechnicalDirector td) {
		
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
		
		} finally {
            
			closeResources(null, stmt);
		
		}
	
	}
	
	public void delete(int id) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"DELETE FROM person WHERE id = ?"
			);
			stmt.setInt(1, id);
			stmt.executeUpdate();
	
		} catch (SQLException e) {
	        
			e.printStackTrace();
		
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