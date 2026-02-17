package data;

import entities.*;
import enums.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataPresident {

	private static final String SELECT_PRESIDENT_BASE
            = "SELECT "
            + "    p.id, p.fullname, p.birthdate, p.address, p.role, p.management_policy, p.photo, "
            + "    n.id AS nat_id, n.name AS nat_name, n.iso_code, n.flag_image, n.continent "
            + "FROM person p "
            + "INNER JOIN nationality n ON p.id_nationality = n.id "
            + "WHERE p.role = 'PRESIDENT'";

    private President mapPresident(ResultSet rs) throws SQLException {

        President president = new President();
        president.setId(rs.getInt("id"));
        president.setFullname(rs.getString("fullname"));
        president.setBirthdate(rs.getObject("birthdate", LocalDate.class));
        president.setAddress(rs.getString("address"));
        president.setPhoto(rs.getString("photo"));

        try {
            president.setRole(PersonRole.valueOf(rs.getString("role").toUpperCase()));
        } catch (Exception e) {
            throw new SQLException("Rol inválido en la BD: " + rs.getString("role"), e);
        }

        president.setManagementPolicy(rs.getString("management_policy"));

        Nationality nationality = new Nationality();
        nationality.setId(rs.getInt("nat_id"));
        nationality.setName(rs.getString("nat_name"));
        nationality.setIsoCode(rs.getString("iso_code"));
        nationality.setFlagImage(rs.getString("flag_image"));

        String contStr = rs.getString("continent");
        if (contStr != null) nationality.setContinent(Continent.valueOf(contStr));

        president.setNationality(nationality);

        return president;
    
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

    public LinkedList<President> getAll() throws SQLException {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<President> presidents = new LinkedList<>();

        try {
        
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_PRESIDENT_BASE);
        	rs = stmt.executeQuery();

            while (rs.next()) presidents.add(mapPresident(rs));
            
        } catch (SQLException e) {
        
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return presidents;
    
    }

    public President getById(int id) throws SQLException {
    
    	President president = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_PRESIDENT_BASE + " AND p.id = ?"
            );
            stmt.setInt(1, id);
            
            rs = stmt.executeQuery();

            if (rs.next()) president = mapPresident(rs);
            
        } catch (SQLException e) {
        
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return president;
    
    }

    public LinkedList<President> getByFullname(String fullname) throws SQLException {
    
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<President> presidents = new LinkedList<>();

        try {
            
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	SELECT_PRESIDENT_BASE + " AND p.fullname = ?"
            );
            stmt.setString(1, fullname);
            
            rs = stmt.executeQuery();

            while (rs.next()) presidents.add(mapPresident(rs));
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }
        
        return presidents;
    
    }

    public void add(President p) throws SQLException {
        
    	PreparedStatement stmt = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        		"INSERT INTO person "
                + "(id, fullname, birthdate, address, role, management_policy, photo, id_nationality) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
            );
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getFullname());
            stmt.setObject(3, p.getBirthdate());
            stmt.setString(4, p.getAddress());
            stmt.setString(5, p.getRole().name());
            stmt.setString(6, p.getManagementPolicy());
            stmt.setString(7, p.getPhoto());
            stmt.setInt(8, p.getNationality().getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(null, stmt);
        
        }
    
    }

    public void update(President p) throws SQLException {
        
    	PreparedStatement stmt = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        		"UPDATE person "
                + "SET fullname = ?, birthdate = ?, address = ?, role = ?, management_policy = ?, photo = ?, id_nationality = ? "
                + "WHERE id = ?"
            );
            stmt.setString(1, p.getFullname());
            stmt.setObject(2, p.getBirthdate());
            stmt.setObject(3, p.getAddress());
            stmt.setString(4, p.getRole().name());
            stmt.setObject(5, p.getManagementPolicy());
            stmt.setObject(6, p.getPhoto());
            stmt.setInt(7, p.getNationality().getId());
            
            stmt.setInt(8, p.getId());

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
            	"DELETE FROM person WHERE id = ?"
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

}