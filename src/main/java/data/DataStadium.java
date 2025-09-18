package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Stadium;

public class DataStadium {
	
	public LinkedList<Stadium> getAll(){
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Stadium> stadiums = new LinkedList<>();
		
		try {
			
			stmt= DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT id, name, capacity FROM stadium");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Stadium stadium = new Stadium();
					stadium.setId(rs.getInt("id"));
					stadium.setName(rs.getString("name"));
					stadium.setCapacity(rs.getInt("capacity"));

					stadiums.add(stadium);
				
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
		
		return stadiums;

	}

	public Stadium getById(Stadium s) {
		
		Stadium stadium = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			
			stmt=DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, name, capacity FROM stadium WHERE id = ?"
			);
			stmt.setInt(1, s.getId());
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
				
				stadium = new Stadium();
				stadium.setId(rs.getInt("id"));
				stadium.setName(rs.getString("name"));
				stadium.setCapacity(rs.getInt("capacity"));
				
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
		
		return stadium;
		
	}
	
	
	public LinkedList<Stadium> getByName(String name) {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Stadium> stadiums = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, name, capacity FROM stadium WHERE name = ?"
			);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Stadium stadium = new Stadium();
					stadium.setId(rs.getInt("id"));
					stadium.setName(rs.getString("name"));
					stadium.setCapacity(rs.getInt("capacity"));

					stadiums.add(stadium);
					
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
		
		return stadiums;
		
	}
	
	public void add(Stadium s) {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO stadium (name, capacity) VALUES (?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS
			);
			stmt.setString(1, s.getName());
			stmt.setInt(2, s.getCapacity());
			stmt.executeUpdate();
			
			keyResultSet = stmt.getGeneratedKeys();
            if(keyResultSet != null && keyResultSet.next()) {
                
            	s.setId(keyResultSet.getInt(1));
            
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
	
	public void update(Stadium s) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE stadium SET name = ?, capacity = ? WHERE id = ?"
			);
			stmt.setString(1, s.getName());
			stmt.setInt(2, s.getCapacity());
			stmt.setInt(3, s.getId());
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
	
	public void delete(Stadium s) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"DELETE FROM stadium WHERE id = ?"
			);
			stmt.setInt(1, s.getId());
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