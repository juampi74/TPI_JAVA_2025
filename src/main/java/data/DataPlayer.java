package data;

import entities.*;
import enums.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class DataPlayer {

    private static final String SELECT_PLAYER_BASE
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, "
            + "    p.dominant_foot, p.jersey_number, p.height, p.weight, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "WHERE p.role = 'PLAYER'";

    private static final String SELECT_AVAILABLE_PLAYERS
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, "
            + "    p.dominant_foot, p.jersey_number, p.height, p.weight, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "WHERE p.role = 'PLAYER' "
            + "  AND NOT EXISTS ( "
            + "      SELECT 1 FROM contract c "
            + "      WHERE c.id_person = p.id "
            + "        AND c.release_date IS NULL "
            + "        AND c.end_date >= CURDATE() "
            + "  )";

    private static final String SELECT_PLAYERS_BY_CLUB
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, "
            + "    p.dominant_foot, p.jersey_number, p.height, p.weight, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "INNER JOIN contract c ON p.id = c.id_person "
            + "WHERE c.id_club = ? "
            + "  AND c.release_date IS NULL "
            + "  AND c.end_date >= CURDATE() "
            + "  AND p.role = 'PLAYER'";

    private Player mapPlayer(ResultSet rs) throws SQLException {

        Player player = new Player();
        player.setId(rs.getInt("id"));
        player.setFullname(rs.getString("fullname"));
        player.setBirthdate(rs.getObject("birthdate", LocalDate.class));
        player.setAddress(rs.getString("address"));

        String roleStr = rs.getString("role");
        try {
            player.setRole(PersonRole.valueOf(roleStr.toUpperCase()));
        } catch (Exception e) {
            throw new SQLException("Rol inválido en la BD: " + roleStr, e);
        }

        String dfStr = rs.getString("dominant_foot");
        if (dfStr != null) player.setDominantFoot(DominantFoot.valueOf(dfStr));

        player.setJerseyNumber(rs.getInt("jersey_number"));
        player.setHeight(rs.getDouble("height"));
        player.setWeight(rs.getDouble("weight"));
        player.setPhoto(rs.getString("photo"));

        Nationality nationality = new Nationality();
        nationality.setId(rs.getInt("nat_id"));
        nationality.setName(rs.getString("nat_name"));
        nationality.setIsoCode(rs.getString("iso_code"));
        nationality.setFlagImage(rs.getString("flag_image"));

        String contStr = rs.getString("continent");
        if (contStr != null) nationality.setContinent(Continent.valueOf(contStr));

        player.setNationality(nationality);

        return player;
        
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

    public LinkedList<Player> getAll() throws SQLException {
        
    	Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Player> players = new LinkedList<>();

        try {
            
        	stmt = DbConnector.getInstance().getConn().createStatement();
            
        	rs = stmt.executeQuery(SELECT_PLAYER_BASE);

            while (rs.next()) players.add(mapPlayer(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return players;
    
    }

    public LinkedList<Player> getAvailable() throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Player> players = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_AVAILABLE_PLAYERS);
            
        	rs = stmt.executeQuery();

            while (rs.next()) players.add(mapPlayer(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        return players;
    
    }

    public Player getById(int id) throws SQLException {
        
    	Player player = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_PLAYER_BASE + " AND p.id = ?"
            );
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            if (rs.next()) player = mapPlayer(rs);
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return player;
    
    }

    public LinkedList<Player> getByFullname(String fullname) throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Player> players = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_PLAYER_BASE + " AND p.fullname = ?"
            );
            stmt.setString(1, fullname);
            
            rs = stmt.executeQuery();

            while (rs.next()) players.add(mapPlayer(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return players;
    
    }

    public LinkedList<Player> getByClub(int idClub) throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Player> players = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_PLAYERS_BY_CLUB);
            stmt.setInt(1, idClub);
            
            rs = stmt.executeQuery();

            while (rs.next()) players.add(mapPlayer(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return players;
    
    }
    
    public Map<Integer, String> getAllPrimaryPositions() throws SQLException {
        
    	Map<Integer, String> positionsMap = new HashMap<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(
        		"SELECT pp.id_player, pos.description " +
        			"FROM player_position pp " +
                    "INNER JOIN position pos ON pp.id_position = pos.id " +
                    "WHERE pp.is_primary = 1"
            );

            while (rs.next()) positionsMap.put(rs.getInt("id_player"), rs.getString("description"));

        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return positionsMap;
        
    }
    
    public Map<Integer, Club> getCurrentClubs() throws SQLException {
        
    	Map<Integer, Club> clubsMap = new HashMap<>();
        Statement stmt = null;
        ResultSet rs = null;

        try {
            
            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(
            	"SELECT con.id_person, c.id, c.name, c.badge_image " +
            		"FROM contract con " +
                    "INNER JOIN club c ON con.id_club = c.id " +
                    "WHERE con.release_date IS NULL " +
                    "AND con.end_date >= CURDATE()"
            );

            while (rs.next()) {

                Club club = new Club();
                club.setId(rs.getInt("id"));
                club.setName(rs.getString("name"));
                club.setBadgeImage(rs.getString("badge_image"));
                
                clubsMap.put(rs.getInt("id_person"), club);
                
            }

        } catch (SQLException e) {
        	
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }

        return clubsMap;
        
    }

    public void add(Player p) throws SQLException {
        
    	PreparedStatement stmt = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                "INSERT INTO person "
                + "(id, fullname, birthdate, address, role, "
                + " dominant_foot, jersey_number, height, weight, photo, id_nationality) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getFullname());
            stmt.setObject(3, p.getBirthdate());
            stmt.setString(4, p.getAddress());
            stmt.setString(5, p.getRole().name());
            stmt.setString(6, p.getDominantFoot().name());
            stmt.setInt(7, p.getJerseyNumber());
            stmt.setDouble(8, p.getHeight());
            stmt.setDouble(9, p.getWeight());
            stmt.setString(10, p.getPhoto());   
            stmt.setInt(11, p.getNationality().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
        	
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(null, stmt);
        
        }
    
    }

    public void update(Player p) throws SQLException {
        
    	PreparedStatement stmt = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE person "
                    + "SET fullname = ?, birthdate = ?, address = ?, role = ?, "
                    + "    dominant_foot = ?, jersey_number = ?, height = ?, weight = ?, photo = ?, id_nationality = ? "
                    + "WHERE id = ?"
            );
            stmt.setString(1, p.getFullname());
            stmt.setObject(2, p.getBirthdate());
            stmt.setString(3, p.getAddress());
            stmt.setString(4, p.getRole().name());
            stmt.setString(5, p.getDominantFoot().name());
            stmt.setInt(6, p.getJerseyNumber());
            stmt.setDouble(7, p.getHeight());
            stmt.setDouble(8, p.getWeight());
            stmt.setString(9, p.getPhoto());
            stmt.setInt(10, p.getNationality().getId());
            stmt.setInt(11, p.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            
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

}