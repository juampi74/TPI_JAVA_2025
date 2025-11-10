package data;

import entities.*;
import enums.PersonRole;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataPlayer {
	
	private Player createPlayerFromResultSet(ResultSet rs) throws SQLException {
        
		Player player = new Player();
		player.setId(rs.getInt("id"));
		player.setFullname(rs.getString("fullname"));
		player.setBirthdate(rs.getObject("birthdate", LocalDate.class));
		player.setAddress(rs.getString("address"));
		player.setRole(PersonRole.valueOf(rs.getString("role")));
	    player.setDominantFoot(rs.getString("dominant_foot"));
	    player.setJerseyNumber(rs.getInt("jersey_number"));
	    player.setHeight(rs.getDouble("height"));
	    player.setWeight(rs.getDouble("weight"));
	    
        return player;
    
	}
	
	private static final String SELECT_PLAYER_FIELDS = 
		"SELECT id, fullname, birthdate, address, role, dominant_foot, jersey_number, height, weight FROM person";
	
	public LinkedList<Player> getAll(){
		
		Statement stmt = null;
		ResultSet rs = null;
		LinkedList<Player> players = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().createStatement();
			rs = stmt.executeQuery(SELECT_PLAYER_FIELDS + " WHERE role = 'PLAYER'");
			
			if (rs != null) {
				
				while (rs.next()) {
					
					Player player = createPlayerFromResultSet(rs);

	                if (player != null) {
	                    
	                	players.add(player);
	                    
	                }
					
				}
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return players;
		
	}
	
	public LinkedList<Player> getAvailable(){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Player> players = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT p.* FROM person p WHERE p.role = 'PLAYER' AND NOT EXISTS (SELECT 1 FROM contract c WHERE c.id_person = p.id AND c.release_date IS NULL AND c.end_date >= CURDATE())"
			);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Player player = createPlayerFromResultSet(rs);
                    
					if (player != null) {
                    
						players.add(player);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return players;
		
	}

	public Player getById(int id) {
		
		Player player = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_PLAYER_FIELDS + " WHERE role = 'PLAYER' AND id = ?"
			);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				player = createPlayerFromResultSet(rs);
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		}
		
		return player;
	
	}
	
	public LinkedList<Player> getByFullname(String fullname){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Player> players = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				SELECT_PLAYER_FIELDS + " WHERE role = 'PLAYER' AND fullname = ?"
			);
			stmt.setString(1, fullname);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Player player = createPlayerFromResultSet(rs);
                    
					if (player != null) {
                    
						players.add(player);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return players;
		
	}
	
	public LinkedList<Player> getByClub(int id_club){
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		LinkedList<Player> players = new LinkedList<>();
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"select p.* from person p inner join contract c on p.id = c.id_person where c.id_club = ? and c.release_date is null and c.end_date >= curdate() and p.role = 'PLAYER'"
			);
			stmt.setInt(1, id_club);
			rs = stmt.executeQuery();
			
			if (rs != null) {
				
				while (rs.next()) {
				
					Player player = createPlayerFromResultSet(rs);
                    
					if (player != null) {
                    
						players.add(player);
                    
					}
					
				}
				
			}
			
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		} finally {
			
			closeResources(rs, stmt);
		
		}
		
		return players;
		
	}
	
	
	public void add(Player p) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"INSERT INTO person (id, fullname, birthdate, address, role, dominant_foot, jersey_number, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
			);
			stmt.setInt(1, p.getId());
			stmt.setString(2, p.getFullname());
			stmt.setObject(3, p.getBirthdate());
			stmt.setString(4, p.getAddress());
			stmt.setString(5, p.getRole().name());
			stmt.setString(6, p.getDominantFoot());
		    stmt.setInt(7, p.getJerseyNumber());
		    stmt.setDouble(8, p.getHeight());
		    stmt.setDouble(9, p.getWeight());
			
			stmt.executeUpdate();

		}  catch (SQLException e) {
            
			e.printStackTrace();
		
		} finally {
            
			closeResources(null, stmt);
		
		}
    
	}
	
	public void update(Player p) {
		
		PreparedStatement stmt = null;
		
		try {
			
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"UPDATE person SET fullname = ?, birthdate = ?, address = ?, role = ?, dominant_foot = ?, jersey_number = ?, height = ?, weight = ? WHERE id = ?"
			);
			stmt.setString(1, p.getFullname());
			stmt.setObject(2, p.getBirthdate());
			stmt.setString(3, p.getAddress());
			stmt.setString(4, p.getRole().name());
			stmt.setString(5, p.getDominantFoot());
		    stmt.setInt(6, p.getJerseyNumber());
		    stmt.setDouble(7, p.getHeight());
		    stmt.setDouble(8, p.getWeight());
			
			stmt.setInt(9, p.getId());
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