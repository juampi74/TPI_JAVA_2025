package data;

import entities.*;
import enums.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashSet;

public class DataClub {

	private static final String SELECT_ALL_CLUBS_JOINED =
        "SELECT "
        + "    cl.id AS club_id, "
        + "    cl.name AS club_name, "
        + "    cl.foundation_date AS club_foundation_date, "
        + "    cl.phone_number AS club_phone_number, "
        + "    cl.email AS club_email, "
        + "    cl.badge_image AS club_badge_image, "
        + "    cl.budget AS club_budget, "
        + "    cl.id_stadium, "
        + "    cl.id_nationality, "
        + "    s.id AS stadium_id, s.name AS stadium_name, s.capacity AS stadium_capacity, "
        + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
        + "FROM club cl "
        + "INNER JOIN stadium s ON cl.id_stadium = s.id "
        + "INNER JOIN nationality n ON cl.id_nationality = n.id ";
	
		

    private static final String SELECT_ALL_CLUBS_TOURNAMENT =
        "SELECT DISTINCT "
        + "    cl.id AS club_id, "
        + "    cl.name AS club_name, "
        + "    cl.foundation_date AS club_foundation_date, "
        + "    cl.phone_number AS club_phone_number, "
        + "    cl.email AS club_email, "
        + "    cl.badge_image AS club_badge_image, "
        + "    cl.budget AS club_budget, "
        + "    cl.id_stadium, "
        + "    cl.id_nationality, "
        + "    s.id AS stadium_id, s.name AS stadium_name, s.capacity AS stadium_capacity, "
        + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
        + "FROM club cl "
        + "INNER JOIN stadium s ON cl.id_stadium = s.id "
        + "INNER JOIN nationality n ON cl.id_nationality = n.id "
        + "INNER JOIN `match` m ON cl.id = m.id_home or cl.id = m.id_away ";

    private static final String SELECT_CLUB_WITH_MOST_CONTRACTS =
        "SELECT "
        + "    cl.id AS club_id, "
        + "    cl.name AS club_name, "
        + "    cl.foundation_date AS club_foundation_date, "
        + "    cl.phone_number AS club_phone_number, "
        + "    cl.email AS club_email, "
        + "    cl.badge_image AS club_badge_image, "
        + "    cl.budget AS club_budget, "
        + "    cl.id_stadium, "
        + "    cl.id_nationality, "
        + "    s.id AS stadium_id, s.name AS stadium_name, s.capacity AS stadium_capacity, "
        + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent, "
        + "    COUNT(c.id) AS contracts_count "
        + "FROM club cl "
        + "INNER JOIN stadium s ON cl.id_stadium = s.id "
        + "INNER JOIN nationality n ON cl.id_nationality = n.id "
        + "INNER JOIN contract c ON c.id_club = cl.id "
        + "WHERE c.release_date IS NULL "
        + "  AND c.end_date > CURDATE() "
        + "GROUP BY "
        + "    cl.id, cl.name, cl.foundation_date, cl.phone_number, cl.email, "
        + "    cl.badge_image, cl.budget, cl.id_stadium, cl.id_nationality, "
        + "    s.id, s.name, s.capacity, "
        + "    n.id, n.name, n.iso_code, n.flag_image, n.continent "
        + "ORDER BY contracts_count DESC "
        + "LIMIT 1";
    
    private static final String SELECT_ALL_CLUBS_FROM_ASSOCIATION =
        "SELECT "
        + "    cl.id AS club_id, "
        + "    cl.name AS club_name, "
        + "    cl.foundation_date AS club_foundation_date, "
        + "    cl.phone_number AS club_phone_number, "
        + "    cl.email AS club_email, "
        + "    cl.badge_image AS club_badge_image, "
        + "    cl.budget AS club_budget, "
        + "    cl.id_stadium, "
        + "    cl.id_nationality, "
        + "    s.id AS stadium_id, s.name AS stadium_name, s.capacity AS stadium_capacity, "
        + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
        + "FROM club cl "
        + "INNER JOIN stadium s ON cl.id_stadium = s.id "
        + "INNER JOIN nationality n ON cl.id_nationality = n.id "
        + "INNER JOIN association_nationality an ON n.id = an.id_nationality "
        + "WHERE an.id_association = ? "
        + "ORDER BY cl.name";

    public LinkedList<Club> getAll() throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ALL_CLUBS_JOINED + " ORDER BY cl.name");
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    Club club = mapFullClub(rs);
                    clubs.add(club);

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return clubs;
        
    }
    
    public LinkedList<Club> getAllByTournamentId(int id) throws SQLException {
        
    	LinkedList<Club> clubs = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        	
        	if (id != -1) {
	            stmt = DbConnector.getInstance().getConn().prepareStatement(
	            	SELECT_ALL_CLUBS_TOURNAMENT + " WHERE m.id_tournament = ? ORDER BY cl.name"
	            );
	            stmt.setInt(1, id);
	            
	            rs = stmt.executeQuery();

        	} else {
        		
        		stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_CLUBS_JOINED
                );

                rs = stmt.executeQuery();
        	
        	}
        	
            if (rs != null) {

                while (rs.next()) {

                    Club club = mapFullClub(rs);
                    clubs.add(club);

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return clubs;
        
    }

    public Club getById(int id) throws SQLException {

        Club club = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_CLUBS_JOINED + " WHERE cl.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {

                club = mapFullClub(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return club;
        
    }
    
    public Club getByPersonId(int id) throws SQLException {

        Club club = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_CLUBS_JOINED + " INNER JOIN contract c ON c.id_club = cl.id INNER JOIN person p ON c.id_person = p.id" + " WHERE p.id = ? and release_date is null and end_date > curdate()"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {

                club = mapFullClub(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return club;
        
    }

    public Club getClubWithMostContracts() throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        Club club = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_CLUB_WITH_MOST_CONTRACTS);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {

                club = mapFullClub(rs);

            }

        } catch (SQLException e) {

        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return club;
        
    }
    
    public HashMap<Integer, Club> getClassicRivalsMap() throws SQLException {
        
    	HashMap<Integer, Club> classicRivalsMap = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
    		    "SELECT r.id_club_1 AS origin_id, c.id, c.name, c.badge_image "
    		    + "FROM classic_rivalry r "
    		    + "INNER JOIN club c ON r.id_club_2 = c.id "
    		    
    		    + "UNION "
    		    
    		    + "SELECT r.id_club_2 AS origin_id, c.id, c.name, c.badge_image "
    		    + "FROM classic_rivalry r "
    		    + "INNER JOIN club c ON r.id_club_1 = c.id"
            );
        	rs = stmt.executeQuery();
            
            while (rs.next()) {

            	Club classicRival = new Club();
            	classicRival.setId(rs.getInt("id"));
            	classicRival.setName(rs.getString("name"));
            	classicRival.setBadgeImage(rs.getString("badge_image"));
                
            	classicRivalsMap.put(rs.getInt("origin_id"), classicRival);
            
            }
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("Error al recuperar mapa de rivales", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return classicRivalsMap;
    
    }

    public Club getByStadiumId(int id) throws SQLException {

        Club club = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_CLUBS_JOINED + " WHERE s.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {

                club = mapFullClub(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return club;
        
    }

    public LinkedList<Club> getByName(String name) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_CLUBS_JOINED + " WHERE cl.name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    Club club = mapFullClub(rs);
                    clubs.add(club);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return clubs;
        
    }
    
    public LinkedList<Club> getByAssociationId(int id) throws SQLException {
        
    	LinkedList<Club> clubs = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ALL_CLUBS_FROM_ASSOCIATION);
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            while (rs.next()) clubs.add(mapFullClub(rs));
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return clubs;
    
    }

    public void add(Club c) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO club (name, foundation_date, phone_number, email, badge_image, budget, id_stadium, id_nationality) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.setInt(8, c.getNationality().getId());
            
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                c.setId(keyResultSet.getInt(1));

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(keyResultSet, stmt);

        }

    }
    
    public void addClassicRival(int id1, int id2) throws SQLException {
        
    	PreparedStatement stmt = null;
        
    	try {
            
            int min = Math.min(id1, id2);
            int max = Math.max(id1, id2);
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO classic_rivalry (id_club_1, id_club_2) VALUES (?, ?)"
            );
            stmt.setInt(1, min);
            stmt.setInt(2, max);
            
            stmt.executeUpdate();
        
    	} catch (SQLException e) {
    		
    		e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
    	} finally {
        
    		closeResources(null, stmt);
        
    	}
    
    }

    public void update(Club c) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"UPDATE club "
                + "SET name = ?, foundation_date = ?, phone_number = ?, email = ?, badge_image = ?, budget = ?, id_stadium = ?, id_nationality = ? "
                + "WHERE id = ?"
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.setInt(8, c.getNationality().getId());
            
            stmt.setInt(9, c.getId());
            
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
            	"DELETE FROM club WHERE id = ?"
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
    
    public void removeClassicRival(int id) throws SQLException {
        
    	PreparedStatement stmt = null;
        
    	try {
    		          
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"DELETE FROM classic_rivalry WHERE id_club_1 = ? OR id_club_2 = ?"
            );
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
        	
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(null, stmt);
        
        }
    
    }

    private Club mapFullClub(ResultSet rs) throws SQLException {

        Club club = new Club();
        club.setId(rs.getInt("club_id"));
        club.setName(rs.getString("club_name"));
        club.setFoundationDate(rs.getObject("club_foundation_date", LocalDate.class));
        club.setPhoneNumber(rs.getString("club_phone_number"));
        club.setEmail(rs.getString("club_email"));
        club.setBadgeImage(rs.getString("club_badge_image"));
        club.setBudget(rs.getDouble("club_budget"));

        Stadium stadium = new Stadium();
        stadium.setId(rs.getInt("stadium_id"));
        stadium.setName(rs.getString("stadium_name"));
        stadium.setCapacity(rs.getInt("stadium_capacity"));
        
        Nationality nationality = new Nationality();
        nationality.setId(rs.getInt("nat_id"));
        nationality.setName(rs.getString("nat_name"));
        nationality.setIsoCode(rs.getString("iso_code"));
        nationality.setFlagImage(rs.getString("flag_image"));

        String contStr = rs.getString("continent");
        if (contStr != null) nationality.setContinent(Continent.valueOf(contStr));

        club.setNationality(nationality);

        club.setStadium(stadium);

        return club;
    }

    private void closeResources(ResultSet rs, PreparedStatement stmt) {

        try {

            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            DbConnector.getInstance().releaseConn();

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }

}