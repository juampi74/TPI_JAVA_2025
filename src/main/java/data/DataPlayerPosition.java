package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class DataPlayerPosition {
	
	public LinkedList<Integer> getPlayerPositions(int playerId) throws SQLException {
		
		PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Integer> positions = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "SELECT id_position FROM player_position WHERE id_player = ?"
            );
            stmt.setInt(1, playerId);
            rs = stmt.executeQuery();
            

            if (rs != null) {

                while (rs.next()) {

                    Integer position = rs.getInt("id_position");
                    positions.add(position);

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return positions;
        
	}
	
	public Integer getPrincipalPosition(int playerId) throws SQLException { 
		
		PreparedStatement stmt = null;
        ResultSet rs = null;
        Integer primaryPosition = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "SELECT id_position FROM player_position WHERE id_player = ? and is_primary = ?"
            );
            stmt.setInt(1, playerId);
            stmt.setBoolean(2, true);
            rs = stmt.executeQuery();
            

            if (rs.next()) {

            	primaryPosition = rs.getInt("id_position");
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return primaryPosition;
		
	}
		
	
	public int getNumberPlayersWithPosition(int idPosition) throws SQLException {
		
		PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Integer> positions = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "SELECT id_player FROM player_position WHERE id_position = ?"
            );
            stmt.setInt(1, idPosition);
            rs = stmt.executeQuery();
            

            if (rs != null) {

                while (rs.next()) {

                    Integer position = rs.getInt("id_player");
                    positions.add(position);

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return positions.size();
		
	}
	
	
	public void add(int playerId, int positionId) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO player_position (id_player, id_position, is_primary) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, playerId);
            stmt.setInt(2, positionId);
            stmt.setBoolean(3, false);
            stmt.executeUpdate();

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
	
	public void setPrimary(int idPlayer, int idMainPos) throws SQLException {
		
		PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE player_position SET is_primary = ? WHERE id_player = ? and id_position = ?"
            );
            stmt.setBoolean(1, true);
            stmt.setInt(2, idPlayer);
            stmt.setInt(3, idMainPos);
            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(null, stmt);

        }
		
	}
	
	public void deleteAllFromPlayer(int id) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "DELETE FROM player_position WHERE id_player = ?"
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
