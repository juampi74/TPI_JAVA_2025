package data;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import entities.Nationality;
import enums.Continent;
import enums.PersonRole;

public class DataNationality {

	private static final String SELECT_ALL_NATIONALITIES =
		"SELECT id, name, iso_code, flag_image, continent FROM nationality";
	
	private static final String SELECT_NATIONALITIES_BY_ASSOCIATION = 
		"SELECT n.id, n.name, n.iso_code, n.flag_image, n.continent " +
	    "FROM nationality n " +
	    "INNER JOIN association_nationality an ON n.id = an.id_nationality " +
	    "WHERE an.id_association = ?";

    public LinkedList<Nationality> getAll() throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Nationality> nationalities = new LinkedList<>();

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_ALL_NATIONALITIES);
            rs = stmt.executeQuery();

            while (rs.next()) nationalities.add(mapNationality(rs));

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return nationalities;

    }

    public Nationality getById(int id) throws SQLException {

    	Nationality nationality = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_NATIONALITIES + " WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (rs.next()) nationality = mapNationality(rs);

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return nationality;

    }

    public Nationality getByName(String name) throws SQLException {

    	Nationality nationality = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_NATIONALITIES + " WHERE name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs.next()) {

            	nationality = mapNationality(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return nationality;

    }
    
    public Nationality getByIsoCode(String isoCode) throws SQLException {

    	Nationality nationality = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_ALL_NATIONALITIES + " WHERE iso_code = ?"
            );
            stmt.setString(1, isoCode);
            rs = stmt.executeQuery();

            if (rs.next()) {

            	nationality = mapNationality(rs);

            }

        } catch (SQLException e) {

            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);

        } finally {

            closeResources(rs, stmt);

        }

        return nationality;

    }
    
    public LinkedList<Nationality> getAllByAssociationId(int id) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Nationality> nationalities = new LinkedList<>();

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_NATIONALITIES_BY_ASSOCIATION);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                
                nationalities.add(mapNationality(rs));
            
            }

        } catch (SQLException e) {
        	
            e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        	
            closeResources(rs, stmt);
        
        }

        return nationalities;
    
    }

    public void add(Nationality n) throws SQLException {

        PreparedStatement stmt = null;
        ResultSet keyResultSet = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO nationality (name, iso_code, flag_image, continent) VALUES (?, ?, ?, ?)",
            	PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, n.getName());
            stmt.setString(2, n.getIsoCode());
            stmt.setString(3, n.getFlagImage());
            stmt.setString(4, n.getContinent().name());
            
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {

                n.setId(keyResultSet.getInt(1));

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

    public void update(Nationality n) throws SQLException {

        PreparedStatement stmt = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"UPDATE nationality SET name = ?, iso_code = ?, flag_image = ?, continent = ? WHERE id = ?"
            );
            stmt.setString(1, n.getName());
            stmt.setString(2, n.getIsoCode());
            stmt.setString(3, n.getFlagImage());
            stmt.setString(4, n.getContinent().name());
            stmt.setInt(5, n.getId());
            
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
            	"DELETE FROM nationality WHERE id = ?"
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

    private Nationality mapNationality(ResultSet rs) throws SQLException {

    	Nationality nationality = new Nationality();
    	nationality.setId(rs.getInt("id"));
    	nationality.setName(rs.getString("name"));
    	nationality.setIsoCode(rs.getString("iso_code"));
    	nationality.setFlagImage(rs.getString("flag_image"));
    	String contStr = rs.getString("continent");
        if (contStr != null) nationality.setContinent(Continent.valueOf(contStr));

        return nationality;

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