package data;

import entities.*;
import enums.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataAssociation {

    private static final String SELECT_ALL_ASSOCIATIONS =
    	"SELECT id, name, creation_date, type, continent FROM association";
    
    private static final String SELECT_ALL_ASSOCIATIONS_JOINED =
		"SELECT a.id, a.name, a.creation_date, a.type, a.continent " +
		"FROM association a " +
		"INNER JOIN association_nationality an ON a.id = an.id_association " +
		"INNER JOIN nationality n ON an.id_nationality = n.id";

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

    public LinkedList<Association> getByNationalityId(int id) throws SQLException {

        LinkedList<Association> associations = new LinkedList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_ASSOCIATIONS_JOINED + " WHERE n.id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs != null) {

                while (rs.next()) {

                    associations.add(mapAssociation(rs));

                }
                
            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return associations;
        
    }

    public void add(Association a) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO association (name, creation_date, type, continent) VALUES (?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, a.getName());
            stmt.setObject(2, a.getCreationDate());
            stmt.setString(3, a.getType().name());
            if (a.getContinent() != null) {
                
            	stmt.setString(4, a.getContinent().name());
            
            } else {
            
            	stmt.setNull(4, java.sql.Types.VARCHAR);
            
            }
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
            	"UPDATE association SET name = ?, creation_date = ?, type = ?, continent = ? WHERE id = ?"
            );
            stmt.setString(1, a.getName());
            stmt.setObject(2, a.getCreationDate());
            stmt.setString(3, a.getType().name());
            if (a.getContinent() != null) {
                
            	stmt.setString(4, a.getContinent().name());
            
            } else {
            
            	stmt.setNull(4, java.sql.Types.VARCHAR);
            
            }
            stmt.setInt(5, a.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(null, stmt);

        }

    }
    
    public void updateNationalities(int idAssociation, LinkedList<Integer> countriesIds) throws SQLException {

        PreparedStatement stmtDelete = null;
        PreparedStatement stmtInsert = null;
        Connection conn = null;

        try {
        	
            conn = DbConnector.getInstance().getConn();
            conn.setAutoCommit(false);

            stmtDelete = conn.prepareStatement(
            	"DELETE FROM association_nationality WHERE id_association = ?"
            );
            stmtDelete.setInt(1, idAssociation);
            stmtDelete.executeUpdate();

            if (countriesIds != null && !countriesIds.isEmpty()) {
                
            	stmtInsert = conn.prepareStatement(
                	"INSERT INTO association_nationality (id_association, id_nationality) VALUES (?, ?)"
                );

                for (Integer countryId : countriesIds) {
                    stmtInsert.setInt(1, idAssociation);
                    stmtInsert.setInt(2, countryId);
                    stmtInsert.addBatch();
                }
                
                stmtInsert.executeBatch();
            
            }

            conn.commit();

        } catch (SQLException e) {
            
            try {
              
            	if (conn != null) conn.rollback();
            
            } catch (SQLException ex) {
            
            	ex.printStackTrace();
            
            }
            
            e.printStackTrace();
            throw new SQLException("Error al actualizar las nacionalidades de la asociación.", e);

        } finally {
            
            if (conn != null) {
                
            	try {
                
            		conn.setAutoCommit(true);
                
            	} catch (SQLException ex) {
                
            		ex.printStackTrace();
                
            	}
            
            }
            
            closeResources(null, stmtDelete);
            closeResources(null, stmtInsert);
            
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

        Association a= new Association();
        a.setId(rs.getInt("id"));
        a.setName(rs.getString("name"));
        a.setCreationDate(rs.getObject("creation_date", LocalDate.class));

        String typeStr = rs.getString("type");
        if (typeStr != null) a.setType(AssociationType.valueOf(typeStr));
        
        String contStr = rs.getString("continent");
        if (contStr != null) a.setContinent(Continent.valueOf(contStr));
        
        return a;
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