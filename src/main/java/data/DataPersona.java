package data;
//orig
import entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataPersona {
	
	public LinkedList<Persona> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers= new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,dni,apellido_nombre,fecha_nacimiento,direccion from persona");
			if(rs!=null) {
				while(rs.next()) {
					Persona p=new Persona();
					p.setId(rs.getInt("id"));
					p.setDni(rs.getString("dni"));
					p.setApellido_nombre(rs.getString("apellido_nombre"));
					p.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class)); // Para que no de error si fecha_nacimiento es null
					p.setDireccion(rs.getString("direccion"));

					
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

	
	public Persona getById(Persona per) {
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,dni,apellido_nombre,fecha_nacimiento,direccion from persona where id=?"
					);
			stmt.setInt(1, per.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setId(rs.getInt("id"));
				p.setDni(rs.getString("dni"));
				p.setApellido_nombre(rs.getString("apellido_nombre"));
				p.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class));
				p.setDireccion(rs.getString("direccion"));
				
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
	
	public Persona getByDni(Persona per) {
		Persona p=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,dni,apellido_nombre,fecha_nacimiento,direccion from persona where dni=?"
					);
			stmt.setString(1, per.getDni());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				p=new Persona();
				p.setId(rs.getInt("id"));
				p.setDni(rs.getString("dni"));
				p.setApellido_nombre(rs.getString("apellido_nombre"));
				p.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class)); // Para que no de error si fecha_nacimiento es null
				p.setDireccion(rs.getString("direccion"));
				
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
	
	public LinkedList<Persona> getByApellidoNombre(String apellido_nombre){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Persona> pers= new LinkedList<>();
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,dni,apellido_nombre,fecha_nacimiento,direccion from persona where apellido_nombre=?"
					);
			stmt.setString(1, apellido_nombre);
			rs=stmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Persona p=new Persona();
					p.setId(rs.getInt("id"));
					p.setDni(rs.getString("dni"));
					p.setApellido_nombre(rs.getString("apellido_nombre"));
					p.setFecha_nacimiento(rs.getObject("fecha_nacimiento", LocalDate.class)); // Para que no de error si fecha_nacimiento es null
					p.setDireccion(rs.getString("direccion"));
					
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
	
	public void add(Persona p) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into persona(dni,apellido_nombre,fecha_nacimiento,direccion) values(?,?,?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, p.getDni());
			stmt.setString(2, p.getApellido_nombre());
			stmt.setObject(3, p.getFecha_nacimiento());
			stmt.setString(4, p.getDireccion());
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
	
	public void updatePersona(Persona p) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"UPDATE persona SET dni=?, apellido_nombre=?, fecha_nacimiento=?, direccion=? WHERE id=?"
							);
			stmt.setString(1, p.getDni());
			stmt.setString(2, p.getApellido_nombre());
			stmt.setObject(3, p.getFecha_nacimiento());
			stmt.setString(4, p.getDireccion());
			stmt.setInt(5, p.getId());
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
	
	
	public void deletePersona(Persona p) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM persona WHERE id=?"
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