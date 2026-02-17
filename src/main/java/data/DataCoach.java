package data;

import entities.*;
import enums.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;

public class DataCoach {

    private static final String SELECT_COACH_BASE
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, "
            + "    p.preferred_formation, p.coaching_license, p.license_obtained_date, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "WHERE p.role = 'COACH'";

    private static final String SELECT_AVAILABLE_COACHES
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, "
            + "    p.preferred_formation, p.coaching_license, p.license_obtained_date, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "WHERE p.role = 'COACH' "
            + "  AND NOT EXISTS ( "
            + "      SELECT 1 FROM contract c "
            + "      WHERE c.id_person = p.id "
            + "        AND c.release_date IS NULL "
            + "        AND c.end_date >= CURDATE() "
            + "  )";

    private Coach mapCoach(ResultSet rs) throws SQLException {

        Coach coach = new Coach();
        coach.setId(rs.getInt("id"));
        coach.setFullname(rs.getString("fullname"));
        coach.setBirthdate(rs.getObject("birthdate", LocalDate.class));
        coach.setAddress(rs.getString("address"));
        coach.setPhoto(rs.getString("photo"));
        
        try {
            coach.setRole(PersonRole.valueOf(rs.getString("role").toUpperCase()));
        } catch (Exception e) {
            throw new SQLException("Rol inválido: " + rs.getString("role"));
        }

        coach.setPreferredFormation(rs.getString("preferred_formation"));
        coach.setCoachingLicense(rs.getString("coaching_license"));
        coach.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));

        Nationality nationality = new Nationality();
        nationality.setId(rs.getInt("nat_id"));
        nationality.setName(rs.getString("nat_name"));
        nationality.setIsoCode(rs.getString("iso_code"));
        nationality.setFlagImage(rs.getString("flag_image"));

        String contStr = rs.getString("continent");
        if (contStr != null) nationality.setContinent(Continent.valueOf(contStr));

        coach.setNationality(nationality);

        return coach;
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

    public LinkedList<Coach> getAll() throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Coach> coaches = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_COACH_BASE);
            
        	rs = stmt.executeQuery();

            while (rs.next()) coaches.add(mapCoach(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return coaches;
    }

    public LinkedList<Coach> getAvailable() throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Coach> coaches = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_AVAILABLE_COACHES);
            
        	rs = stmt.executeQuery();

            while (rs.next()) coaches.add(mapCoach(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return coaches;
    
    }

    public Coach getById(int id) throws SQLException {
        
    	Coach coach = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_COACH_BASE + " AND p.id = ?"
            );
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            if (rs.next()) coach = mapCoach(rs);
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return coach;
    
    }

    public LinkedList<Coach> getByFullname(String fullname) throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Coach> coaches = new LinkedList<>();

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_COACH_BASE + " AND p.fullname = ?"
            );
            stmt.setString(1, fullname);
            
            rs = stmt.executeQuery();

            while (rs.next()) coaches.add(mapCoach(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return coaches;
    
    }
    
    public Map<Integer, Club> getCurrentClubs() throws SQLException {
        
    	Map<Integer, Club> clubsMap = new HashMap<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                "SELECT con.id_person, c.id, c.name, c.badge_image "
                + "FROM contract con "
                + "INNER JOIN club c ON con.id_club = c.id "
                + "WHERE con.release_date IS NULL "
                + "AND con.end_date >= CURDATE()");
            rs = stmt.executeQuery();

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

    public void add(Coach coach) throws SQLException {
    
    	PreparedStatement stmt = null;

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO person "
                    + "(id, fullname, birthdate, address, role, "
                    + " preferred_formation, coaching_license, license_obtained_date, photo, id_nationality) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, coach.getId());
            stmt.setString(2, coach.getFullname());
            stmt.setObject(3, coach.getBirthdate());
            stmt.setString(4, coach.getAddress());
            stmt.setString(5, coach.getRole().name());
            stmt.setString(6, coach.getPreferredFormation());
            stmt.setString(7, coach.getCoachingLicense());
            stmt.setObject(8, coach.getLicenseObtainedDate());
            stmt.setString(9, coach.getPhoto());
            stmt.setInt(10, coach.getNationality().getId());
            
            stmt.executeUpdate();

        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(null, stmt);
        
        }
    
    }

    public void update(Coach coach) throws SQLException {
        
    	PreparedStatement stmt = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE person "
                    + "SET fullname = ?, birthdate = ?, address = ?, role = ?, "
                    + "    preferred_formation = ?, coaching_license = ?, license_obtained_date = ?, photo = ?, id_nationality = ? "
                    + "WHERE id = ?"
            );
            stmt.setString(1, coach.getFullname());
            stmt.setObject(2, coach.getBirthdate());
            stmt.setObject(3, coach.getAddress());
            stmt.setString(4, coach.getRole().name());
            stmt.setObject(5, coach.getPreferredFormation());
            stmt.setObject(6, coach.getCoachingLicense());
            stmt.setObject(7, coach.getLicenseObtainedDate());
            stmt.setObject(8, coach.getPhoto());
            stmt.setInt(9, coach.getNationality().getId());
            
            stmt.setInt(10, coach.getId());
            
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