package data;

import entities.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

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
            + "    s.id AS stadium_id, "
            + "    s.name AS stadium_name, "
            + "    s.capacity AS stadium_capacity "
            + "FROM club cl "
            + "INNER JOIN stadium s ON cl.id_stadium = s.id";
    
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
            + "    s.id AS stadium_id, "
            + "    s.name AS stadium_name, "
            + "    s.capacity AS stadium_capacity "
            + "FROM club cl "
            + "INNER JOIN stadium s ON cl.id_stadium = s.id "
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
            + "    s.id AS stadium_id, "
            + "    s.name AS stadium_name, "
            + "    s.capacity AS stadium_capacity, "
            + "    COUNT(c.id) AS contracts_count "
            + "FROM club cl "
            + "INNER JOIN contract c ON c.id_club = cl.id "
            + "INNER JOIN stadium s ON cl.id_stadium = s.id "
            + "WHERE c.release_date IS NULL "
            + "  AND c.end_date > CURDATE() "
            + "GROUP BY "
            + "    cl.id, cl.name, cl.foundation_date, cl.phone_number, cl.email, "
            + "    cl.badge_image, cl.budget, cl.id_stadium, "
            + "    s.id, s.name, s.capacity "
            + "ORDER BY contracts_count DESC "
            + "LIMIT 1";

    public LinkedList<Club> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_CLUBS_JOINED);

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
	            	SELECT_ALL_CLUBS_TOURNAMENT + " WHERE m.id_tournament = ?"
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

    public Club getClubWithMostContracts() {

        Statement stmt = null;
        ResultSet rs = null;
        Club club = null;

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_CLUB_WITH_MOST_CONTRACTS);

            if (rs != null && rs.next()) {

                club = mapFullClub(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();

        } finally {

            closeResources(rs, stmt);

        }

        return club;
        
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

    public void add(Club c) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO club (name, foundation_date, phone_number, email, badge_image, budget, id_stadium) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                c.setId(keyResultSet.getInt(1));

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            try {

                if (keyResultSet != null) {
                    keyResultSet.close();
                }

            } catch (SQLException e) {

                e.printStackTrace();

            }

            closeResources(null, stmt);

        }

    }

    public void update(Club c) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"UPDATE club "
                + "SET name = ?, foundation_date = ?, phone_number = ?, email = ?, badge_image = ?, budget = ?, id_stadium = ? "
                + "WHERE id = ?"
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.setInt(8, c.getId());
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

        club.setStadium(stadium);

        return club;
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