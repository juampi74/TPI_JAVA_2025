package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
                    "INSERT INTO player_position (id_player, id_position) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setInt(1, playerId);
            stmt.setInt(2, positionId);
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
