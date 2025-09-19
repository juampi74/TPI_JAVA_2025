package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataPerson {
	
	public LinkedList<Person> getAll(){
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Person> people = new LinkedList<>();
		
		try {
			
			stmt= DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery("SELECT id, fullname, birthdate, address FROM person");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Person person = new Person();
					person.setId(rs.getInt("id"));
					person.setFullname(rs.getString("fullname"));
					person.setBirthdate(rs.getObject("birthdate", LocalDate.class));
					person.setAddress(rs.getString("address"));

					people.add(person);
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			try {
				
				if (rs!=null) rs.close();
				if (stmt!=null) stmt.close();
				DbConnector.getInstance().releaseConn();
			
			} catch (SQLException e) {
			
				e.printStackTrace();
			
			}
		
		}
		
		return people;
		
	}

	public Person getById(Person p) {
		
		Person person = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, fullname, birthdate, address FROM person WHERE id = ?"
			);
			stmt.setInt(1, p.getId());
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				person = new Person();
				person.setId(rs.getInt("id"));
				person.setFullname(rs.getString("fullname"));
				person.setBirthdate(rs.getObject("birthdate", LocalDate.class));
				person.setAddress(rs.getString("address"));
				
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
		
		return person;
	
	}
	
	public LinkedList<Person> getByFullname(String fullname){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Person> people = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT id, fullname, birthdate, address FROM person WHERE fullname = ?"
			);
			stmt.setString(1, fullname);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Person person = new Person();
					person.setId(rs.getInt("id"));
					person.setFullname(rs.getString("fullname"));
					person.setBirthdate(rs.getObject("birthdate", LocalDate.class));
					person.setAddress(rs.getString("address"));
					
					people.add(person);
					
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
		
		return people;
		
	}
	
	public void add(Person p) {
		
		PreparedStatement stmt = null;
		ResultSet keyResultSet = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO person (id, fullname, birthdate, address) VALUES (?, ?, ?, ?)",
				PreparedStatement.RETURN_GENERATED_KEYS
			);
			stmt.setInt(1, p.getId());
			stmt.setString(2, p.getFullname());
			stmt.setObject(3, p.getBirthdate());
			stmt.setString(4, p.getAddress());
			stmt.executeUpdate();
			
			keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                
            	p.setId(keyResultSet.getInt(1));
            
            }

		}  catch (SQLException e) {
            
			e.printStackTrace();
		
		} finally {
            
			try {
                
				if(keyResultSet != null) keyResultSet.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
            
            	e.printStackTrace();
            
            }
		
		}
    
	}
	
	public void update(Person p) {
		
		System.out.println(p);		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE person SET fullname = ?, birthdate = ?, address = ? WHERE id = ?"
			);
			stmt.setString(1, p.getFullname());
			stmt.setObject(2, p.getBirthdate());
			stmt.setString(3, p.getAddress());
			stmt.setInt(4, p.getId());
			stmt.executeUpdate();

		}  catch (SQLException e) {
          
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
	
	public void delete(Person p) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"DELETE FROM person WHERE id = ?"
			);
			stmt.setInt(1, p.getId());
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