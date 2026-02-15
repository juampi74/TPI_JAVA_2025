package data;

import entities.*;
import enums.Continent;
import enums.PersonRole;
import enums.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.LinkedList;

public class DataUser {

    private static final String BASE_QUERY = 
        "SELECT u.id AS u_id, u.email, u.password, u.role AS u_role, u.active, u.id_person, " +
        "p.id AS p_id, p.fullname, p.birthdate, p.address, p.photo, p.role AS p_role " +
        "FROM user u " +
        "LEFT JOIN person p ON u.id_person = p.id ";
    
    private static final String SELECT_PENDING_USERS = 
	    "SELECT u.id AS u_id, u.email, u.role, p.id AS p_id, p.fullname, " +
    	"n.id AS n_id, n.name AS n_name, n.iso_code, n.flag_image, n.continent " +
	    "FROM user u " +
	    "INNER JOIN person p ON u.id_person = p.id " +
	    "INNER JOIN nationality n ON p.id_nationality = n.id " +
	    "WHERE u.active = 0";
    
    private static final String UPDATE_APPROVE_USER = 
    	"UPDATE user SET active = 1 WHERE id = ?";
    
    private static final String GET_ID_PERSON_BY_USER = "SELECT id_person FROM user WHERE id = ?";
    private static final String DELETE_USER = "DELETE FROM user WHERE id = ?";
    private static final String DELETE_PERSON = "DELETE FROM person WHERE id = ?";
    
    public User getById(int id) throws SQLException {

    	User user = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {

            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	BASE_QUERY + "WHERE u.id = ?"
            );
            stmt.setInt(1, id);
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

    public User getByEmail(String email) throws SQLException {
        
    	User user = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(BASE_QUERY + "WHERE u.email = ?");
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
    
    public void register(User user, int idPerson, String fullname, int idNationality) throws SQLException {
        
    	Connection conn = null;
        PreparedStatement stmtPerson = null;
        PreparedStatement stmtUser = null;

        try {
            
        	conn = DbConnector.getInstance().getConn();
            conn.setAutoCommit(false);

            stmtPerson = conn.prepareStatement(
            	"INSERT INTO person (id, fullname, role, id_nationality) VALUES (?, ?, ?, ?)"
            );
            
            stmtPerson.setInt(1, idPerson);
            stmtPerson.setString(2, fullname);
            stmtPerson.setString(3, user.getRole().name());
            stmtPerson.setInt(4, idNationality);
            
            stmtPerson.executeUpdate();

            stmtUser = conn.prepareStatement(
            	"INSERT INTO user (email, password, role, id_person) VALUES (?, ?, ?, ?)"
            );
            stmtUser.setString(1, user.getEmail());
            stmtUser.setString(2, user.getPassword());
            stmtUser.setString(3, user.getRole().name());
            stmtUser.setInt(4, idPerson);
            
            stmtUser.executeUpdate();

            conn.commit();

        } catch (SQLException e) {
            
        	if (conn != null) {
                
        		try {
        		
        			conn.rollback();
        		
        		} catch (SQLException ex) {
        			
        			ex.printStackTrace();
        		
        		}
            
        	}
            
        	throw e;
        
        } finally {
            
        	if (conn != null) {
                
        		try {
        			
        			conn.setAutoCommit(true);
        		
        		} catch (SQLException e) {
        			
        			e.printStackTrace();
        			
        		}
            
        	}
            
        	closeResources(null, stmtPerson, stmtUser);
        
        }
    
    }
    
    public LinkedList<User> getPendingUsers() throws SQLException {
        
    	LinkedList<User> users = new LinkedList<>();
        Statement stmt = null;
        ResultSet rs = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery(SELECT_PENDING_USERS);
            
            while (rs.next()) {
                
            	User u = new User();
                u.setId(rs.getInt("u_id"));
                u.setEmail(rs.getString("email"));
                
                String roleStr = rs.getString("role");
                UserRole roleEnum = UserRole.valueOf(roleStr);
                
                u.setRole(roleEnum);
                u.setActive(false);
                
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
                
	                p.setId(rs.getInt("p_id"));
	                p.setFullname(rs.getString("fullname"));
	                
	                Nationality n = new Nationality();
	                n.setId(rs.getInt("n_id"));
	                n.setName(rs.getString("n_name"));
	                n.setIsoCode(rs.getString("iso_code"));
	                n.setFlagImage(rs.getString("flag_image"));
	                n.setContinent(Continent.valueOf(rs.getString("continent")));
	                
	                p.setNationality(n); 
	
	                u.setPerson(p);
	                
	                users.add(u);
	                
                }
	                
            }
        
        } catch (SQLException e) {
        
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
            
        	closeResources(rs, stmt);
        
        }
        
        return users;
    
    }

    public void approveUser(int userId) throws SQLException {
        
    	PreparedStatement stmt = null;
        
    	try {
        
    		stmt = DbConnector.getInstance().getConn().prepareStatement(UPDATE_APPROVE_USER);
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
            
        } finally {
            
        	closeResources(null, stmt);
        
        }
    
    }
    
    public void rejectUser(int userId) throws SQLException {
        
    	Connection conn = null;
    	PreparedStatement stmtUser = null;
        PreparedStatement stmtPerson = null;
        PreparedStatement stmtGetId = null;
        ResultSet rs = null;
        
        try {

        	conn = DbConnector.getInstance().getConn();
        	
            stmtGetId = conn.prepareStatement(GET_ID_PERSON_BY_USER);
            stmtGetId.setInt(1, userId);
            rs = stmtGetId.executeQuery();
            
            int personId = -1;
            if (rs.next()) {
                personId = rs.getInt("id_person");
            }
            
            stmtUser = conn.prepareStatement(DELETE_USER);
            stmtUser.setInt(1, userId);
            stmtUser.executeUpdate();
            
            if (personId != -1) {
                
            	stmtPerson = conn.prepareStatement(DELETE_PERSON);
                stmtPerson.setInt(1, personId);
                stmtPerson.executeUpdate();
            
            }
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
            throw new SQLException("No se pudo conectar a la base de datos.", e);
        
        } finally {
        
        	closeResources(rs, stmtGetId, stmtUser, stmtPerson);
        
        }
    
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
        
        u.setActive(rs.getBoolean("active"));
        
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

    private void closeResources(ResultSet rs, Statement... stmts) {
        
    	try {
        
    		if (rs != null) rs.close();
			
            for (Statement stmt : stmts) {
            	
            	if (stmt != null) stmt.close();
            
            }
            
            DbConnector.getInstance().releaseConn();
        
    	} catch (SQLException e) {
        
    		e.printStackTrace();
        
    	}
    
    }

}