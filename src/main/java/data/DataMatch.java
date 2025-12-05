package data;

import entities.*;
import enums.AssociationType;
import enums.Continent;
import enums.TournamentFormat;
import enums.TournamentStage;

import java.sql.*;
import java.time.*;
import java.util.LinkedList;

public class DataMatch {

    private static final String SELECT_ALL_MATCHES_JOINED
            = "SELECT "
            + "    m.id AS match_id, m.date AS match_date, m.stage AS match_stage, m.group_name AS match_group_name, m.matchday AS match_matchday, m.home_goals AS match_home_goals, m.away_goals AS match_away_goals, "
            + "    h.id AS home_id, h.name AS home_name, h.foundation_date AS home_foundation_date, h.phone_number AS home_phone_number, h.email AS home_email, h.badge_image AS home_badge_image, h.budget AS home_budget, "
            + "    a.id AS away_id, a.name AS away_name, a.foundation_date AS away_foundation_date, a.phone_number AS away_phone_number, a.email AS away_email, a.badge_image AS away_badge_image, a.budget AS away_budget, "
            + "    t.id AS tournament_id, t.name AS tournament_name, t.start_date AS tournament_start_date, t.end_date AS tournament_end_date, t.format AS tournament_format, t.season AS tournament_season, "
            + "    ass.id AS association_id, ass.name AS association_name, ass.creation_date AS association_creation_date, ass.type AS association_type, ass.continent AS association_continent "
            + "FROM `match` m "
            + "INNER JOIN club h ON m.id_home = h.id "
            + "INNER JOIN club a ON m.id_away = a.id "
            + "INNER JOIN tournament t ON m.id_tournament = t.id "
            + "INNER JOIN association ass ON t.id_association = ass.id";

    public LinkedList<Match> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_MATCHES_JOINED + " ORDER BY m.date ASC");

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
                SELECT_ALL_MATCHES_JOINED + " WHERE m.id_home = ? or m.id_away = ? ORDER BY m.date ASC"
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

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE m.id_tournament = ? ORDER BY m.date ASC"
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
    
    public LinkedList<Match> getByClubAndTournamentId(int clubId, int tournamentId) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Match> matches = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                SELECT_ALL_MATCHES_JOINED + " WHERE (m.id_home = ? or m.id_away = ?) and m.id_tournament = ? ORDER BY m.date ASC"
            );
            stmt.setInt(1, clubId);
            stmt.setInt(2, clubId);
            stmt.setInt(3, tournamentId);
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
                + "(date, stage, group_name, matchday, home_goals, away_goals, id_tournament, id_home, id_away) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
            );
            
        	stmt.setObject(1, m.getDate());
        	stmt.setString(2, m.getStage().name());
        	stmt.setString(3, m.getGroupName());
        	stmt.setInt(4, m.getMatchday());
        	
        	if (m.getHomeGoals() != null && m.getAwayGoals() != null) {
            
        		stmt.setInt(5, m.getHomeGoals());
                stmt.setInt(6, m.getAwayGoals());
            
        	} else {
            
        		stmt.setNull(5, java.sql.Types.INTEGER);
                stmt.setNull(6, java.sql.Types.INTEGER);
            
        	}
            
        	stmt.setInt(7, m.getTournament().getId());
        	stmt.setInt(8, m.getHome().getId());
            stmt.setInt(9, m.getAway().getId());
            
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
                "UPDATE `match` SET date = ?, stage = ?, group_name = ?, matchday = ?, home_goals = ?, away_goals = ?, id_tournament = ?, id_home = ?, id_away = ? WHERE id = ?"
            );
            stmt.setObject(1, m.getDate());
            stmt.setString(2, m.getStage().name());
            stmt.setString(3, m.getGroupName());
            stmt.setInt(4, m.getMatchday());
            stmt.setObject(5, m.getHomeGoals());
            stmt.setObject(6, m.getAwayGoals());
            stmt.setInt(7, m.getTournament().getId());
            stmt.setInt(8, m.getHome().getId());
            stmt.setInt(9, m.getAway().getId());
            
            stmt.setInt(10, m.getId());
            
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
        home.setId(rs.getInt("home_id"));
        home.setName(rs.getString("home_name"));
        home.setFoundationDate(rs.getObject("home_foundation_date", LocalDate.class));
        home.setPhoneNumber(rs.getString("home_phone_number"));
        home.setEmail(rs.getString("home_email"));
        home.setBadgeImage(rs.getString("home_badge_image"));
        home.setBudget(rs.getDouble("home_budget"));
        
        Club away = new Club();
        away.setId(rs.getInt("away_id"));
        away.setName(rs.getString("away_name"));
        away.setFoundationDate(rs.getObject("away_foundation_date", LocalDate.class));
        away.setPhoneNumber(rs.getString("away_phone_number"));
        away.setEmail(rs.getString("away_email"));
        away.setBadgeImage(rs.getString("away_badge_image"));
        away.setBudget(rs.getDouble("away_budget"));
        
        Association association = new Association();
        association.setId(rs.getInt("association_id"));
        association.setName(rs.getString("association_name"));
        association.setCreationDate(rs.getObject("association_creation_date", LocalDate.class));
        association.setType(AssociationType.valueOf(rs.getString("association_type")));
        association.setContinent(Continent.valueOf(rs.getString("association_continent")));
        
        Tournament tournament = new Tournament();
        tournament.setId(rs.getInt("tournament_id"));
        tournament.setName(rs.getString("tournament_name"));
        tournament.setStartDate(rs.getObject("tournament_start_date", LocalDate.class));
        tournament.setEndDate(rs.getObject("tournament_end_date", LocalDate.class));
        tournament.setFormat(TournamentFormat.valueOf(rs.getString("tournament_format")));
        tournament.setSeason(rs.getString("tournament_season"));
        tournament.setAssociation(association);
        
        Match match = new Match();
        match.setId(rs.getInt("match_id"));
        match.setDate(rs.getObject("match_date", LocalDateTime.class));
        match.setStage(TournamentStage.valueOf(rs.getString("match_stage")));
        match.setGroupName(rs.getString("match_group_name"));
        match.setMatchday(rs.getInt("match_matchday"));
        match.setHomeGoals(rs.getInt("match_home_goals"));
        match.setAwayGoals(rs.getInt("match_away_goals"));
        match.setTournament(tournament);
        match.setHome(home);
        match.setAway(away);

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