package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Stadium;

public class DataStadium {
	public LinkedList<Stadium> getAll(){
		Statement stmt=null;
		ResultSet rs=null;
		LinkedList<Stadium> stadiums = new LinkedList<>();
		
		try {
			stmt= DbConnector.getInstancia().getConn().createStatement();
			rs= stmt.executeQuery("select id,name,capacity from stadium");
			if(rs!=null) {
				while(rs.next()) {
					Stadium st=new Stadium();
					st.setId(rs.getInt("id"));
					st.setName(rs.getString("name"));
					st.setCapacity(rs.getInt("capacity"));

					stadiums.add(st);
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
		
		
		return stadiums;
	}

	
	public Stadium getById(Stadium stad) {
		Stadium st=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,name,capacity from stadium where id=?"
					);
			stmt.setInt(1, stad.getId());
			rs=stmt.executeQuery();
			if(rs!=null && rs.next()) {
				st=new Stadium();
				st.setId(rs.getInt("id"));
				st.setName(rs.getString("name"));
				st.setCapacity(rs.getInt("capacity"));
				
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
		
		return st;
	}
	
	
	public LinkedList<Stadium> getByName(String name){
		PreparedStatement stmt=null;
		ResultSet rs=null;
		LinkedList<Stadium> stadiums= new LinkedList<>();
		
		try {
			stmt=DbConnector.getInstancia().getConn().prepareStatement(
					"select id,name,capacity from stadium where name=?"
					);
			stmt.setString(1, name);
			rs=stmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) {
					Stadium st=new Stadium();
					st.setId(rs.getInt("id"));
					st.setName(rs.getString("name"));
					st.setCapacity(rs.getInt("capacity"));

					stadiums.add(st);
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
		
		
		return stadiums;
		
	}
	
	public void addStadium(Stadium st) {
		PreparedStatement stmt= null;
		ResultSet keyResultSet=null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"insert into stadium(name,capacity) values(?,?)",
							PreparedStatement.RETURN_GENERATED_KEYS
							);
			stmt.setString(1, st.getName());
			stmt.setInt(2, st.getCapacity());
			stmt.executeUpdate();
			
			keyResultSet=stmt.getGeneratedKeys();
            if(keyResultSet!=null && keyResultSet.next()){
                st.setId(keyResultSet.getInt(1));
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
	
	public void updateStadium(Stadium st) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"UPDATE stadium SET name=?, capacity=? WHERE id=?"
							);
			stmt.setString(1, st.getName());
			stmt.setInt(2, st.getCapacity());
			stmt.setInt(3, st.getId());
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
	
	
	public void deleteStadium(Stadium st) {
		PreparedStatement stmt= null;
		try {
			stmt=DbConnector.getInstancia().getConn().
					prepareStatement(
							"DELETE FROM stadium WHERE id=?"
							);
			
			stmt.setInt(1, st.getId());
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
