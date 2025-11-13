package data;

import entities.Coach;
import enums.PersonRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataCoach {

    private static final String SELECT_COACH_BASE =
            "SELECT id, fullname, birthdate, address, role, " +
            "       preferred_formation, coaching_license, license_obtained_date " +
            "FROM person " +
            "WHERE role = 'COACH'";

    private static final String SELECT_AVAILABLE_COACHES =
            "SELECT id, fullname, birthdate, address, role, " +
            "       preferred_formation, coaching_license, license_obtained_date " +
            "FROM person p " +
            "WHERE p.role = 'COACH' " +
            "  AND NOT EXISTS ( " +
            "      SELECT 1 FROM contract c " +
            "      WHERE c.id_person = p.id " +
            "        AND c.release_date IS NULL " +
            "        AND c.end_date >= CURDATE() " +
            "  )";

    private Coach mapCoach(ResultSet rs) throws SQLException {

        Coach coach = new Coach();
        coach.setId(rs.getInt("id"));
        coach.setFullname(rs.getString("fullname"));
        coach.setBirthdate(rs.getObject("birthdate", LocalDate.class));
        coach.setAddress(rs.getString("address"));

        coach.setRole(PersonRole.valueOf(rs.getString("role").toUpperCase()));

        coach.setPreferredFormation(rs.getString("preferred_formation"));
        coach.setCoachingLicense(rs.getString("coaching_license"));
        coach.setLicenseObtainedDate(rs.getObject("license_obtained_date", LocalDate.class));

        return coach;
        
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

    public LinkedList<Coach> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Coach> coaches = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_COACH_BASE);

            while (rs.next()) {

                Coach coach = mapCoach(rs);
                coaches.add(coach);

            }

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

            while (rs.next()) {

                Coach coach = mapCoach(rs);
                coaches.add(coach);

            }

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
                    SELECT_COACH_BASE + " AND id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                coach = mapCoach(rs);

            }

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
                    SELECT_COACH_BASE + " AND fullname = ?"
            );
            stmt.setString(1, fullname);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Coach coach = mapCoach(rs);
                coaches.add(coach);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return coaches;

    }
    
    public void add(Coach coach) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO person " +
                    "(id, fullname, birthdate, address, role, " +
                    " preferred_formation, coaching_license, license_obtained_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, coach.getId());
            stmt.setString(2, coach.getFullname());
            stmt.setObject(3, coach.getBirthdate());
            stmt.setString(4, coach.getAddress());
            stmt.setString(5, coach.getRole().name());
            stmt.setString(6, coach.getPreferredFormation());
            stmt.setString(7, coach.getCoachingLicense());
            stmt.setObject(8, coach.getLicenseObtainedDate());

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
                    "UPDATE person " +
                    "SET fullname = ?, birthdate = ?, address = ?, role = ?, " +
                    "    preferred_formation = ?, coaching_license = ?, license_obtained_date = ? " +
                    "WHERE id = ?"
            );
            stmt.setString(1, coach.getFullname());
            stmt.setObject(2, coach.getBirthdate());
            stmt.setString(3, coach.getAddress());
            stmt.setString(4, coach.getRole().name());
            stmt.setString(5, coach.getPreferredFormation());
            stmt.setString(6, coach.getCoachingLicense());
            stmt.setObject(7, coach.getLicenseObtainedDate());
            stmt.setInt(8, coach.getId());

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