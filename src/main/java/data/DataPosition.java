package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import entities.Position;

public class DataPosition {

	private static final String SELECT_ALL_POSITIONS =
            "SELECT id, description, abbreviation FROM position";

    public LinkedList<Position> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Position> positions = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_POSITIONS);

            while (rs.next()) {

                Position position = mapPosition(rs);
                positions.add(position);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return positions;

    }

    public Position getById(int id) throws SQLException {

    	Position position = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_POSITIONS + " WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                position = mapPosition(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return position;

    }

    public LinkedList<Position> getByDescription(String description) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Position> positions = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_POSITIONS + " WHERE description = ?"
            );
            stmt.setString(1, description);
            rs = stmt.executeQuery();

            while (rs.next()) {

            	Position position = mapPosition(rs);
            	positions.add(position);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return positions;

    }

    public void add(Position p) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO position (description, abbreviation) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, p.getDescription());
            stmt.setString(2, p.getAbbreviation());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                p.setId(keyResultSet.getInt(1));

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

    public void update(Position p) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE position SET description = ?, abbreviation = ? WHERE id = ?"
            );
            stmt.setString(1, p.getDescription());
            stmt.setString(2, p.getAbbreviation());
            stmt.setInt(3, p.getId());
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
                    "DELETE FROM position WHERE id = ?"
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

    private Position mapPosition(ResultSet rs) throws SQLException {

    	Position position = new Position();
    	position.setId(rs.getInt("id"));
    	position.setDescription(rs.getString("description"));
    	position.setAbbreviation(rs.getString("abbreviation"));

        return position;
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
