package data;

import entities.*;
import enums.PersonRole;
import enums.UserRole;

import java.sql.*;
import java.time.LocalDate;

public class DataUser {

    private static final String SELECT_USER_BY_EMAIL = 
        "SELECT u.id AS u_id, u.email, u.password, u.role AS u_role, u.id_person, " +
        "p.id AS p_id, p.fullname, p.birthdate, p.address, p.photo, p.role AS p_role " +
        "FROM user u " +
        "LEFT JOIN person p ON u.id_person = p.id " +
        "WHERE u.email = ?";

    public User getByEmail(String email) throws SQLException {
        
    	User user = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(SELECT_USER_BY_EMAIL);
            stmt.setString(1, email);
            
            rs = stmt.executeQuery();

            if (rs.next()) user = mapFullUser(rs);

        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmt);
        
        }

        return user;
    
    }

    private User mapFullUser(ResultSet rs) throws SQLException {
        
    	User u = new User();
        
        u.setId(rs.getInt("u_id"));
        u.setEmail(rs.getString("email"));
        u.setPassword(rs.getString("password"));
        
        String userRoleStr = rs.getString("u_role");
        
        try {

            u.setRole(UserRole.valueOf(userRoleStr.toUpperCase()));

        } catch (Exception e) {

            throw new SQLException("Rol inválido en la BD: " + userRoleStr, e);

        }
        
        int personId = rs.getInt("p_id");
        
        if (personId > 0) {

            String personRoleStr = rs.getString("p_role");
            
            if (personRoleStr != null) {
                
                PersonRole roleEnum = PersonRole.valueOf(personRoleStr.toUpperCase());
                Person p = null;

                switch (roleEnum) {

                	case PLAYER:
                
                		p = new Player();
                    
                		break;
                    
                	case COACH:
                    
                		p = new Coach();
                        
                		break;
                    
                	case PRESIDENT:
                    
                		p = new President();
                        
                		break;
                    
                	default:

                        break;
                
                }

                if (p != null) {

                	p.setId(personId);
                    p.setFullname(rs.getString("fullname"));
                    p.setBirthdate(rs.getObject("birthdate", LocalDate.class));
                    p.setAddress(rs.getString("address"));
                    p.setPhoto(rs.getString("photo"));               
                    p.setRole(roleEnum); 
                    
                    u.setPerson(p); 

                }
            
            }
        
        } else {

            u.setPerson(null);

        }
        
        return u;
    
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