package data;

import entities.*;
import enums.TournamentFormat;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataTournament {
  
    private static final String BASE_QUERY =
            "SELECT "
            + "    t.id AS tournament_id, "
            + "    t.name AS tournament_name, "
            + "    t.start_date AS tournament_start_date, "
            + "    t.end_date AS tournament_end_date, "
            + "    t.format AS tournament_format, "
            + "    t.season AS tournament_season, "
            + "	   t.finished AS tournament_finished, "
            + "    t.id_association, "
            + "    a.id AS association_id, "
            + "    a.name AS association_name, "
            + "    a.creation_date AS association_creation_date, "
            + "    SUM(CASE WHEN m.stage = 'GROUP_STAGE' AND (m.home_goals IS NULL OR m.away_goals IS NULL) THEN 1 ELSE 0 END) as pending_group_matches, "
            + "    SUM(CASE WHEN m.stage = 'GROUP_STAGE' THEN 1 ELSE 0 END) as total_group_matches, "
            + "    SUM(CASE WHEN m.stage != 'GROUP_STAGE' THEN 1 ELSE 0 END) as playoff_matches_count, "
            + "    SUM(CASE WHEN m.home_goals IS NOT NULL THEN 1 ELSE 0 END) as matches_played_count "
            + "FROM tournament t "
            + "INNER JOIN association a ON t.id_association = a.id "
            + "LEFT JOIN `match` m ON t.id = m.id_tournament ";
    
    private static final String GROUP_BY_CLAUSE =
            " GROUP BY t.id, t.name, t.start_date, t.end_date, t.format, t.season, "
            + "        t.finished, t.id_association, a.id, a.name, a.creation_date ";

    public LinkedList<Tournament> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Tournament> tournaments = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(BASE_QUERY + GROUP_BY_CLAUSE + " ORDER BY t.start_date, t.end_date");

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
            	BASE_QUERY + "WHERE t.id = ?" + GROUP_BY_CLAUSE
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

    public LinkedList<Tournament> getByClubId(int id) throws SQLException {

        LinkedList<Tournament> tournaments = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	BASE_QUERY + "WHERE m.id_home = ? or m.id_away = ?" + GROUP_BY_CLAUSE
            );
            stmt.setInt(1, id);
            stmt.setInt(2, id);
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
    		
    public LinkedList<Tournament> getByAssociationId(int id) throws SQLException {

        LinkedList<Tournament> tournaments = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	BASE_QUERY + "WHERE a.id = ?" + GROUP_BY_CLAUSE
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
            	BASE_QUERY + " WHERE t.name = ?" + GROUP_BY_CLAUSE
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
            stmt.setString(4, t.getFormat().name());
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
            stmt.setString(4, t.getFormat().name());
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
    
    public void finishTournament(int id) throws SQLException {
        
    	PreparedStatement stmt = null;
        
    	try {
        
    		stmt = DbConnector.getInstance().getConn().prepareStatement(
                "UPDATE tournament SET finished = 1 WHERE id = ?"
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
        tournament.setFormat(TournamentFormat.valueOf(rs.getString("tournament_format")));
        tournament.setSeason(rs.getString("tournament_season"));
        tournament.setFinished(rs.getBoolean("tournament_finished"));;
        
        int pending = rs.getInt("pending_group_matches");
        int totalGroup = rs.getInt("total_group_matches");
        int playoffCount = rs.getInt("playoff_matches_count");
        int playedCount = rs.getInt("matches_played_count");
        
        boolean allGroupMatchesPlayed = (totalGroup > 0) && (pending == 0);
        tournament.setAllGroupMatchesPlayed(allGroupMatchesPlayed);
        
        tournament.setPlayoffsAlreadyGenerated(playoffCount > 0);
        tournament.setHasMatchesPlayed(playedCount > 0);
        
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