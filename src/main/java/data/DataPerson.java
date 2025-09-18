package data;
//orig
import entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataPerson {
	
	public LinkedList<Person> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Person> pers= new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,fullname,birthdate,adress from person");
			if(rs!=null) {
				while(rs.next()) {
					Person p=new Person();
					p.setId(rs.getInt("id"));
					p.setFullname(rs.getString("fullname"));
					p.setBirthdate(rs.getObject("birthdate", LocalDate.class)); // Para que no de error si birthdate es null
					p.setAdress(rs.getString("adress"));

					
					pers.add(p);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return pers;
	}

	
	public Person getById(Person per) {
		Person p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,fullname,birthdate,adress from person where id=?"
					);
			stmt.setInt(1, per.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Person();
				p.setId(rs.getInt("id"));
				p.setFullname(rs.getString("fullname"));
				p.setBirthdate(rs.getObject("birthdate", LocalDate.class));
				p.setAdress(rs.getString("adress"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return p;
	}
	
	
	public LinkedList<Person> getByFullname(String apellido_nombre){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Person> pers= new LinkedList<>();
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,fullname,birthdate,adress from person where fullname=?"
					);
			stmt.setString(1, apellido_nombre);
			rs=stmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Person p=new Person();
					p.setId(rs.getInt("id"));
					p.setFullname(rs.getString("fullname"));
					p.setBirthdate(rs.getObject("birthdate", LocalDate.class)); // Para que no de error si birthdate es null
					p.setAdress(rs.getString("adress"));
					
					pers.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(stmt!=null) {stmt.close();}
				DbConnector.getInstancia().releaseConn();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		return pers;
		
	}
	
	public void add(Person p) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into person(id,fullname,birthdate,adress) values(?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setInt(1, p.getId());
			stmt.setString(2, p.getFullname());
			stmt.setObject(3, p.getBirthdate());
			stmt.setString(4, p.getAdress());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                p.setId(keyResultSet.getInt(1));
            }

			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(keyResultSet!=null)keyResultSet.close();
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
    }
	
	public void updatePersona(Person p) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"UPDATE person SET fullname=?, birthdate=?, adress=? WHERE id=?"
							);
			stmt.setString(1, p.getFullname());
			stmt.setObject(2, p.getBirthdate());
			stmt.setString(3, p.getAdress());
			stmt.setInt(4, p.getId());
			stmt.executeUpdate();

			
		}  catch (SQLException e) {
            e.printStackTrace();
		} finally {
            try {
                if(stmt!=null)stmt.close();
                DbConnector.getInstancia().releaseConn();
            } catch (SQLException e) {
            	e.printStackTrace();
            }
		}
	}
	
	
	public void deletePersona(Person p) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM person WHERE id=?"
							);
			
			stmt.setInt(1, p.getId());
			stmt.executeUpdate();
	
			
		}  catch (SQLException e) {
	        e.printStackTrace();
		} finally {
	        try {
	            if(stmt!=null)stmt.close();
	            DbConnector.getInstancia().releaseConn();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        }
		}
	}
	
}