package data;

import entities.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataTournament {

    private static final String SELECT_ALL_TOURNAMENTS_JOINED =
            "SELECT "
            + "    t.id AS tournament_id, "
            + "    t.name AS tournament_name, "
            + "    t.start_date AS tournament_start_date, "
            + "    t.end_date AS tournament_end_date, "
            + "    t.format AS tournament_format, "
            + "    t.season AS tournament_season, "
            + "    t.id_association, "
            + "    a.id AS association_id, "
            + "    a.name AS association_name, "
            + "    a.creation_date AS association_creation_date "
            + "FROM tournament t "
            + "INNER JOIN association a ON t.id_association = a.id";

    public LinkedList<Tournament> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Tournament> tournaments = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_TOURNAMENTS_JOINED);

            if (rs != null) {

                while (rs.next()) {

                    Tournament tournament = mapFullTournament(rs);
                    tournaments.add(tournament);

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return tournaments;
        
    }

    public Tournament getById(int id) throws SQLException {

        Tournament tournament = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_TOURNAMENTS_JOINED + " WHERE t.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null && rs.next()) {

                tournament = mapFullTournament(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return tournament;
        
    }

    public LinkedList<Tournament> getByAssociationId(int id) throws SQLException {

        LinkedList<Tournament> tournaments = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_TOURNAMENTS_JOINED + " WHERE a.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    Tournament tournament = mapFullTournament(rs);
                    tournaments.add(tournament);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return tournaments;
        
    }

    public LinkedList<Tournament> getByName(String name) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Tournament> tournaments = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_TOURNAMENTS_JOINED + " WHERE t.name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    Tournament tournament = mapFullTournament(rs);
                    tournaments.add(tournament);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return tournaments;
        
    }

    public void add(Tournament t) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO tournament (name, start_date, end_date, format, season, id_association) "
                    + "VALUES (?, ?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, t.getName());
            stmt.setObject(2, t.getStartDate());
            stmt.setObject(3, t.getEndDate());
            stmt.setString(4, t.getFormat());
            stmt.setString(5, t.getSeason());
            stmt.setInt(6, t.getAssociation().getId());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                t.setId(keyResultSet.getInt(1));

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            try {

                if (keyResultSet != null) keyResultSet.close();

            } catch (SQLException e) {

                e.printStackTrace();

            }

            closeResources(null, stmt);

        }

    }

    public void update(Tournament t) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE tournament "
                    + "SET name = ?, start_date = ?, end_date = ?, format = ?, season = ?, id_association = ? "
                    + "WHERE id = ?"
            );
            stmt.setString(1, t.getName());
            stmt.setObject(2, t.getStartDate());
            stmt.setObject(3, t.getEndDate());
            stmt.setString(4, t.getFormat());
            stmt.setString(5, t.getSeason());
            stmt.setInt(6, t.getAssociation().getId());
            stmt.setInt(7, t.getId());
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
                    "DELETE FROM tournament WHERE id = ?"
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

    private Tournament mapFullTournament(ResultSet rs) throws SQLException {

        Tournament tournament = new Tournament();
        tournament.setId(rs.getInt("tournament_id"));
        tournament.setName(rs.getString("tournament_name"));
        tournament.setStartDate(rs.getObject("tournament_start_date", LocalDate.class));
        tournament.setEndDate(rs.getObject("tournament_end_date", LocalDate.class));
        tournament.setFormat(rs.getString("tournament_format"));
        tournament.setSeason(rs.getString("tournament_season"));

        Association association = new Association();
        association.setId(rs.getInt("association_id"));
        association.setName(rs.getString("association_name"));
        association.setCreationDate(rs.getObject("association_creation_date", LocalDate.class));

        tournament.setAssociation(association);

        return tournament;
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