package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.LinkedList;

import entities.Association;

public class DataAssociation {
	
	public LinkedList<Association> getAll(){
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Association> associations = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT id, name, creation_date FROM association");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Association association = new Association();
					association.setId(rs.getInt("id"));
					association.setName(rs.getString("name"));
					association.setCreationDate(rs.getObject("creation_date", LocalDate.class));

					associations.add(association);
				
				}
			
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
			
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				DbConnector.getInstance().releaseConn();
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			
			}
		
		}
		
		return associations;

	}

	public Association getById(Association a) {
		
		Association association = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, name, creation_date FROM association WHERE id = ?"
			);
			stmt.setInt(1, a.getId());
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				
				association = new Association();
				association.setId(rs.getInt("id"));
				association.setName(rs.getString("name"));
				association.setCreationDate(rs.getObject("creation_date", LocalDate.class));
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			try {
				
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				DbConnector.getInstance().releaseConn();
			
			} catch (SQLException e) {
			
				e.printStackTrace();
			
			}
		
		}
		
		return association;
		
	}
	
	
	public LinkedList<Association> getByName(String name) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Association> associations = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, name, creation_date FROM association WHERE name = ?"
			);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Association association = new Association();
					association.setId(rs.getInt("id"));
					association.setName(rs.getString("name"));
					association.setCreationDate(rs.getObject("creation_date", LocalDate.class));

					associations.add(association);
					
				}
			
			}
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			try {
			
				if (rs != null) rs.close();
				if (stmt != null) stmt.close();
				DbConnector.getInstance().releaseConn();
				
			} catch (SQLException e) {
			
				e.printStackTrace();
			
			}
		
		}
		
		return associations;
		
	}
	
	public void add(Association a) {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO association (name, creation_date) VALUES (?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS
			);
			stmt.setString(1, a.getName());
			stmt.setObject(2, a.getCreationDate());
			stmt.executeUpdate();
			
			keyResultSet = stmt.getGeneratedKeys();
            if(keyResultSet != null && keyResultSet.next()) {
                
            	a.setId(keyResultSet.getInt(1));
            
            }

		} catch (SQLException e) {
            
			e.printStackTrace();
		
		} finally {
			
			try {
            
				if (keyResultSet != null) keyResultSet.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
            	
            	e.printStackTrace();
            
            }
		
		}
    
	}
	
	public void update(Association a) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE association SET name = ?, creation_date = ? WHERE id = ?"
			);
			stmt.setString(1, a.getName());
			stmt.setObject(2, a.getCreationDate());
			stmt.setInt(3, a.getId());
			stmt.executeUpdate();

		} catch (SQLException e) {
            
			e.printStackTrace();
		
		} finally {
            
			try {
				
				if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
            	
            	e.printStackTrace();
            
            }
		
		}
	
	}
	
	public void delete(Association a) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"DELETE FROM association WHERE id = ?"
			);
			stmt.setInt(1, a.getId());
			stmt.executeUpdate();
	
		} catch (SQLException e) {
	        
			e.printStackTrace();
		
		} finally {
	        
			try {
	            
				if (stmt != null) stmt.close();
	            DbConnector.getInstance().releaseConn();
	            
	        } catch (SQLException e) {
	        	
	        	e.printStackTrace();
	        
	        }
		
		}
	
	}

}