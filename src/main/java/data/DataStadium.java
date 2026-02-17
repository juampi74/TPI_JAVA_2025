package data;

import entities.Stadium;

import java.sql.*;
import java.util.LinkedList;

public class DataStadium {

    private static final String SELECT_ALL_STADIUMS =
            "SELECT id, name, capacity FROM stadium";

    public LinkedList<Stadium> getAll() throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Stadium> stadiums = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ALL_STADIUMS);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Stadium stadium = mapStadium(rs);
                stadiums.add(stadium);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return stadiums;

    }

    public Stadium getById(int id) throws SQLException {

        Stadium stadium = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_STADIUMS + " WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                stadium = mapStadium(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return stadium;

    }

    public LinkedList<Stadium> getByName(String name) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Stadium> stadiums = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_STADIUMS + " WHERE name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Stadium stadium = mapStadium(rs);
                stadiums.add(stadium);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return stadiums;

    }

    public void add(Stadium s) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO stadium (name, capacity) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getCapacity());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                s.setId(keyResultSet.getInt(1));

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

    public void update(Stadium s) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE stadium SET name = ?, capacity = ? WHERE id = ?"
            );
            stmt.setString(1, s.getName());
            stmt.setInt(2, s.getCapacity());
            stmt.setInt(3, s.getId());
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
            	"DELETE FROM stadium WHERE id = ?"
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

    private Stadium mapStadium(ResultSet rs) throws SQLException {

        Stadium stadium = new Stadium();
        stadium.setId(rs.getInt("id"));
        stadium.setName(rs.getString("name"));
        stadium.setCapacity(rs.getInt("capacity"));

        return stadium;
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