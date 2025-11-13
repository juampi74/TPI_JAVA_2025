package data;

import enums.PersonRole;

import java.sql.*;

public class DataPerson {

    private static final String SELECT_ROLE_BY_ID =
            "SELECT role FROM person WHERE id = ?";

    public PersonRole getRoleByPersonId(int id) throws SQLException {

        PersonRole role = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ROLE_BY_ID);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) {

                String roleStr = rs.getString("role");

                try {

                    role = PersonRole.valueOf(roleStr.toUpperCase());

                } catch (Exception e) {

                    throw new SQLException("Rol inválido en la BD: " + roleStr, e);

                }
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return role;

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