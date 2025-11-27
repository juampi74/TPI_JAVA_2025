package data;

import entities.*;
import java.sql.*;
import java.time.*;
import java.util.LinkedList;

public class DataMatch {

    private static final String SELECT_ALL_MATCHES_JOINED
            = "SELECT "
            + "    m.id, m.home_goals, m.away_goals, m.home_goals, m.date, m.matchday, "
            + "    t.id AS id_tournament, t.name AS tournament_name, t.start_date AS tournament_start_date, t.end_date AS tournament_end_date, t.format AS tournament_format, t.season AS tournament_season, "
            + "    ass.id AS id_association, ass.name AS association_name, ass.creation_date AS association_creation_date,"
            + "    a.id AS id_away, a.name AS name_a, a.foundation_date AS foundation_date_a, a.phone_number AS phone_number_a, a.email AS email_a, a.badge_image AS badge_image_a, a.budget AS budget_a, "
            + "    h.id AS id_home, h.name, h.foundation_date, h.phone_number, h.email, h.badge_image, h.budget "
            + "FROM `match` m "
            + "INNER JOIN club h ON m.id_home = h.id "
            + "INNER JOIN club a ON m.id_away = a.id "
            + "INNER JOIN tournament t ON m.id_tournament = t.id "
            + "INNER JOIN association ass ON t.id_association = ass.id ";

    public LinkedList<Match> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_MATCHES_JOINED + " ORDER BY t.name ASC, m.matchday ASC, m.date ASC");

            while (rs.next()) {

                Match match = mapFullMatch(rs);
                matches.add(match);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return matches;

    }

    public Match getById(int id) throws SQLException {

        Match match = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE m.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                match = mapFullMatch(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return match;

    }

    public LinkedList<Match> getByClubId(int id) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE m.id_home = ? or m.id_away = ? ORDER BY t.name ASC, m.matchday ASC"
            );
            stmt.setInt(1, id);
            stmt.setInt(2, id);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Match match = mapFullMatch(rs);
                matches.add(match);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return matches;

    }
    
    public LinkedList<Match> getByTournamentId(int id) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {
            // ORDENAMIENTO AGREGADO: Jornada -> Fecha
            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE m.id_tournament = ? ORDER BY m.matchday ASC, m.date ASC"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Match match = mapFullMatch(rs);
                matches.add(match);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return matches;

    }
    
    public LinkedList<Match> getByClubAndTournamentId(int id_club, int id_tournament) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {
            // ORDENAMIENTO AGREGADO: Jornada
            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE (m.id_home = ? or m.id_away = ?) and m.id_tournament = ? ORDER BY m.matchday ASC"
            );
            stmt.setInt(1, id_club);
            stmt.setInt(2, id_club);
            stmt.setInt(3, id_tournament);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Match match = mapFullMatch(rs);
                matches.add(match);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return matches;

    }

    public void add(Match m) throws SQLException {

        PreparedStatement stmt = null;

        try {
            stmt = DbConnector.getInstance().getConn().prepareStatement(
                "INSERT INTO `match` "
                + "(home_goals, away_goals, matchday, date, id_away, id_home, id_tournament) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)"
            );
            if (m.getHomeGoals() != null && m.getAwayGoals() != null) {
                stmt.setInt(1, m.getHomeGoals());
                stmt.setInt(2, m.getAwayGoals());
            } else {
                stmt.setNull(1, java.sql.Types.INTEGER);
                stmt.setNull(2, java.sql.Types.INTEGER);
            }
            stmt.setDouble(3, m.getMatchday());
            stmt.setObject(4, m.getDate());
            stmt.setInt(5, m.getAway().getId());
            stmt.setInt(6, m.getHome().getId());
            stmt.setInt(7, m.getTournament().getId());
            
            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(null, stmt);

        }

    }

    public void update(Match m) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                "UPDATE `match` SET home_goals = ?, away_goals = ?, matchday = ?, date = ?, id_away = ?, id_home = ?, id_tournament = ? WHERE id = ?"
            );
            stmt.setObject(1, m.getHomeGoals());
            stmt.setObject(2, m.getAwayGoals());
            stmt.setDouble(3, m.getMatchday());
            stmt.setObject(4, m.getDate());
            stmt.setInt(5, m.getAway().getId());
            stmt.setInt(6, m.getHome().getId());
            stmt.setInt(7, m.getTournament().getId());
            stmt.setInt(8, m.getId());
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
                "DELETE FROM `match` WHERE id = ?"
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

    private Match mapFullMatch(ResultSet rs) throws SQLException {

        Club home = new Club();
        home.setId(rs.getInt("id_home"));
        home.setName(rs.getString("name"));
        home.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
        home.setPhoneNumber(rs.getString("phone_number"));
        home.setEmail(rs.getString("email"));
        home.setBadgeImage(rs.getString("badge_image"));
        home.setBudget(rs.getDouble("budget"));
        
        Club away = new Club();
        away.setId(rs.getInt("id_away"));
        away.setName(rs.getString("name_a"));
        away.setFoundationDate(rs.getObject("foundation_date_a", LocalDate.class));
        away.setPhoneNumber(rs.getString("phone_number_a"));
        away.setEmail(rs.getString("email_a"));
        away.setBadgeImage(rs.getString("badge_image_a"));
        away.setBudget(rs.getDouble("budget_a"));
        
        Association association = new Association();
        association.setId(rs.getInt("id_association"));
        association.setName(rs.getString("association_name"));
        association.setCreationDate(rs.getObject("association_creation_date", LocalDate.class));
        
        Tournament tournament = new Tournament();
        tournament.setAssociation(association);
        tournament.setId(rs.getInt("id_tournament"));
        tournament.setName(rs.getString("tournament_name"));
        tournament.setStartDate(rs.getObject("tournament_start_date", LocalDate.class));
        tournament.setEndDate(rs.getObject("tournament_end_date", LocalDate.class));
        tournament.setFormat(rs.getString("tournament_format"));
        tournament.setSeason(rs.getString("tournament_season"));
        
        Match match = new Match();
        match.setAway(away);
        match.setHome(home);
        match.setTournament(tournament);
        match.setId(rs.getInt("id"));
        match.setDate(rs.getObject("date", LocalDateTime.class));
        match.setAwayGoals(rs.getInt("away_goals"));
        match.setHomeGoals(rs.getInt("home_goals"));
        match.setMatchday(rs.getInt("matchday"));

        return match;

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