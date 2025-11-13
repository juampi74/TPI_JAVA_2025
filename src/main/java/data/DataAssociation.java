package data;

import entities.Association;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataAssociation {

    private static final String SELECT_ALL_ASSOCIATIONS =
            "SELECT id, name, creation_date FROM association";

    public LinkedList<Association> getAll() throws SQLException {

        Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Association> associations = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_ALL_ASSOCIATIONS);

            while (rs.next()) {

                Association association = mapAssociation(rs);
                associations.add(association);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return associations;

    }

    public Association getById(int id) throws SQLException {

        Association association = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_ASSOCIATIONS + " WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                association = mapAssociation(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return association;

    }

    public LinkedList<Association> getByName(String name) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Association> associations = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    SELECT_ALL_ASSOCIATIONS + " WHERE name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            while (rs.next()) {

                Association association = mapAssociation(rs);
                associations.add(association);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return associations;

    }

    // -------------------- INSERT / UPDATE / DELETE --------------------

    public void add(Association a) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "INSERT INTO association (name, creation_date) VALUES (?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, a.getName());
            stmt.setObject(2, a.getCreationDate());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                a.setId(keyResultSet.getInt(1));

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

    public void update(Association a) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
                    "UPDATE association SET name = ?, creation_date = ? WHERE id = ?"
            );
            stmt.setString(1, a.getName());
            stmt.setObject(2, a.getCreationDate());
            stmt.setInt(3, a.getId());
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
                    "DELETE FROM association WHERE id = ?"
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

    private Association mapAssociation(ResultSet rs) throws SQLException {

        Association association = new Association();
        association.setId(rs.getInt("id"));
        association.setName(rs.getString("name"));
        association.setCreationDate(rs.getObject("creation_date", LocalDate.class));

        return association;
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