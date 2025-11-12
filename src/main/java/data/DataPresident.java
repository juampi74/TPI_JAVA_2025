package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataPresident {
	
	private President createPresidentFromResultSet(ResultSet rs) throws SQLException {
        
		President president = new President();
		president.setId(rs.getInt("id"));
		president.setFullname(rs.getString("fullname"));
		president.setBirthdate(rs.getObject("birthdate", LocalDate.class));
		president.setAddress(rs.getString("address"));
		president.setRole(PersonRole.valueOf(rs.getString("role")));
		president.setManagementPolicy(rs.getString("management_policy"));
	    
        return president;
    
	}
	
	private static final String SELECT_PRESIDENT_FIELDS = 
		"SELECT id, fullname, birthdate, address, role, management_policy FROM person";
	
	public LinkedList<President> getAll() throws SQLException {
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<President> presidents = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(SELECT_PRESIDENT_FIELDS + " WHERE role = 'PRESIDENT'");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					President president = createPresidentFromResultSet(rs);

	                if (president != null) {
	                    
	                	presidents.add(president);
	                    
	                }
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
			
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return presidents;
		
	}

	public President getById(int id) throws SQLException {
		
		President president = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_PRESIDENT_FIELDS + " WHERE role = 'PRESIDENT' AND id = ?"
			);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				president = createPresidentFromResultSet(rs);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
			
			closeResources(rs, stmt);
		}
		
		return president;
	
	}
	
	public LinkedList<President> getByFullname(String fullname) throws SQLException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<President> presidents = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_PRESIDENT_FIELDS + " WHERE role = 'PRESIDENT' AND fullname = ?"
			);
			stmt.setString(1, fullname);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					President president = createPresidentFromResultSet(rs);
                    
					if (president != null) {
                    
						presidents.add(president);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return presidents;
		
	}
	
	public void add(President p) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO person (id, fullname, birthdate, address, role, management_policy) VALUES (?, ?, ?, ?, ?, ?)"
			);
			stmt.setInt(1, p.getId());
			stmt.setString(2, p.getFullname());
			stmt.setObject(3, p.getBirthdate());
			stmt.setString(4, p.getAddress());
			stmt.setString(5, p.getRole().name());
			stmt.setString(6, p.getManagementPolicy());
			
			stmt.executeUpdate();

		}  catch (SQLException e) {
            
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		} finally {
            
			closeResources(null, stmt);
		
		}
    
	}
	
	public void update(President p) throws SQLException {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE person SET fullname = ?, birthdate = ?, address = ?, role = ?, management_policy = ? WHERE id = ?"
			);
			stmt.setString(1, p.getFullname());
			stmt.setObject(2, p.getBirthdate());
			stmt.setString(3, p.getAddress());
			stmt.setString(4, p.getRole().name());
			stmt.setString(5, p.getManagementPolicy());
			
			stmt.setInt(6, p.getId());
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