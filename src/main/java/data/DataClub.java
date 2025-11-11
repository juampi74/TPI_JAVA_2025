package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataClub {

    public LinkedList<Club> getAll() {
        
    	Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {
            
        	stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery("SELECT id, name, foundation_date, email, badge_image, phone_number, budget, id_stadium FROM club");
            if (rs != null) {
                
            	while (rs.next()) {
                    
            		Club club = new Club();
            		club.setId(rs.getInt("id"));
            		club.setName(rs.getString("name"));
            		club.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
            		club.setPhoneNumber(rs.getString("phone_number"));
            		club.setEmail(rs.getString("email"));
            		club.setBadgeImage(rs.getString("badge_image"));
            		club.setBudget(rs.getDouble("budget"));

            		PreparedStatement stmt2 = null;
            	    ResultSet rs2 = null;
            		
            	    try {
            	    
	            	    stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	            			"SELECT id, name, capacity FROM stadium WHERE id = ?"
	                	);
	                    stmt2.setInt(1, rs.getInt("id_stadium"));
	                    rs2 = stmt2.executeQuery();
	                    
	                    if (rs2 != null && rs2.next()) {
	                    	
	                    	Stadium stadium = new Stadium();
	                    	stadium.setId(rs2.getInt("id"));
	                    	stadium.setName(rs2.getString("name"));
	                    	stadium.setCapacity(rs2.getInt("capacity"));
	                    	
	                    	club.setStadium(stadium);
	                    	
	                    }
	                    
	            		
	                    clubs.add(club);
            	    
            	    } catch (SQLException e) {
                        
                    	e.printStackTrace();

                    } finally {
                        
                    	try {
                            
                    		if (rs2 != null) rs2.close();
                            if (stmt2 != null) stmt2.close();
                            
                        } catch (SQLException e) {
                            
                        	e.printStackTrace();
                        
                        }
                    
                    }
            	    
            	}
            
            }

        } catch (SQLException e) {
            
        	e.printStackTrace();

        } finally {
            
        	try {
                
        		if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
                
            	e.printStackTrace();
            
            }
        
        }

        return clubs;
    }

    public Club getById(int id) {
        
    	Club club = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"SELECT id, name, foundation_date, phone_number, email, badge_image, budget, id_stadium FROM club WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs != null && rs.next()) {
                
            	club = new Club();
            	club.setId(rs.getInt("id"));
            	club.setName(rs.getString("name"));
            	club.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
            	club.setPhoneNumber(rs.getString("phone_number"));
            	club.setEmail(rs.getString("email"));
            	club.setBadgeImage(rs.getString("badge_image"));
            	club.setBudget(rs.getDouble("budget"));
            	
            	PreparedStatement stmt2 = null;
        	    ResultSet rs2 = null;
            	
        	    try {
        	    
	        	    stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	            		"SELECT id, name, capacity FROM stadium WHERE id = ?"
	            	);
	                stmt2.setInt(1, rs.getInt("id_stadium"));
	                rs2 = stmt2.executeQuery();
	                
	                if (rs2 != null && rs2.next()) {
	                	
	                	Stadium stadium = new Stadium();
	                	stadium.setId(rs2.getInt("id"));
	                	stadium.setName(rs2.getString("name"));
	                	stadium.setCapacity(rs2.getInt("capacity"));
	                	
	                	club.setStadium(stadium);
	                	
	                }
            
        	    } catch (SQLException e) {
                    
        	    	e.printStackTrace();

                } finally {
                    
                	try {
                        
                		if (rs2 != null) rs2.close();
                        if (stmt2 != null) stmt2.close();
                        
                    } catch (SQLException e) {
                        
                    	e.printStackTrace();
                    
                    }
                
                }
            }
        
        } catch (SQLException e) {
        
        	e.printStackTrace();
        
        } finally {
        
        	try {
            
        		if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
            
            	e.printStackTrace();
            
            }
        
        }
        
        return club;
    }
    
public Club getByStadiumId(int id) {
        
    	Club club = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"SELECT id, name, foundation_date, phone_number, email, badge_image, budget, id_stadium FROM club WHERE id_stadium = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs != null && rs.next()) {
                
            	club = new Club();
            	club.setId(rs.getInt("id"));
            	club.setName(rs.getString("name"));
            	club.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
            	club.setPhoneNumber(rs.getString("phone_number"));
            	club.setEmail(rs.getString("email"));
            	club.setBadgeImage(rs.getString("badge_image"));
            	club.setBudget(rs.getDouble("budget"));
            	
            	PreparedStatement stmt2 = null;
        	    ResultSet rs2 = null;
            	
        	    try {
        	    
	        	    stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	            		"SELECT id, name, capacity FROM stadium WHERE id = ?"
	            	);
	                stmt2.setInt(1, rs.getInt("id_stadium"));
	                rs2 = stmt2.executeQuery();
	                
	                if (rs2 != null && rs2.next()) {
	                	
	                	Stadium stadium = new Stadium();
	                	stadium.setId(rs2.getInt("id"));
	                	stadium.setName(rs2.getString("name"));
	                	stadium.setCapacity(rs2.getInt("capacity"));
	                	
	                	club.setStadium(stadium);
	                	
	                }
            
        	    } catch (SQLException e) {
                    
        	    	e.printStackTrace();

                } finally {
                    
                	try {
                        
                		if (rs2 != null) rs2.close();
                        if (stmt2 != null) stmt2.close();
                        
                    } catch (SQLException e) {
                        
                    	e.printStackTrace();
                    
                    }
                
                }
            }
        
        } catch (SQLException e) {
        
        	e.printStackTrace();
        
        } finally {
        
        	try {
            
        		if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
            
            	e.printStackTrace();
            
            }
        
        }
        
        return club;
    }

    public LinkedList<Club> getByName(String name) {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Club> clubs = new LinkedList<>();

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        		"SELECT id, name, foundation_date, phone_number, email, badge_image, budget, id_stadium FROM club WHERE name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            if (rs != null) {
            	
                while (rs.next()) {
                    
                	Club club = new Club();
                    club.setId(rs.getInt("id"));
                    club.setName(rs.getString("name"));
                    club.setFoundationDate(rs.getObject("foundation_date", LocalDate.class));
                    club.setPhoneNumber(rs.getString("phone_number"));
                    club.setEmail(rs.getString("email"));
                    club.setBadgeImage(rs.getString("badge_image"));
                    club.setBudget(rs.getDouble("budget"));
                    
                    PreparedStatement stmt2 = null;
            	    ResultSet rs2 = null;
                    
            	    try {
            	    
	            	    stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	                    	"SELECT id, name, capacity FROM stadium WHERE id = ?"
	                    );   
	                    stmt2.setInt(1, rs.getInt("id_stadium"));
	                    rs2 = stmt2.executeQuery();
	                    
	                    if (rs2 != null && rs2.next()) {
	                    	
	                    	Stadium stadium = new Stadium();
	                    	stadium.setId(rs2.getInt("id"));
	                    	stadium.setName(rs2.getString("name"));
	                    	stadium.setCapacity(rs2.getInt("capacity"));
	                    	
	                    	club.setStadium(stadium);
	                    	
	                    }
	                    
	                    clubs.add(club);
	                    
            	    } catch (SQLException e) {
                        
	                	e.printStackTrace();

	                } finally {
                    
	                	try {
	                        
	                		if (rs2 != null) rs2.close();
	                        if (stmt2 != null) stmt2.close();
	                        
	                    } catch (SQLException e) {
	                        
	                    	e.printStackTrace();
	                    
	                    }
                
	                }
                
                }
            
            }
        
        } catch (SQLException e) {
        
        	e.printStackTrace();
        
        } finally {
        
        	try {
                
        		if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
             
            	e.printStackTrace();
            
            }
        
        }

        return clubs;

    }

    public void add(Club c) {
        
    	PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        
        try {
        	
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO club (name, foundation_date, phone_number, email, badge_image, budget, id_stadium) VALUES (?, ?, ?, ?, ?, ?, ?)",
            	PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                
            	c.setId(keyResultSet.getInt(1));
            
            }
        
        } catch (SQLException e) {
            
        	e.printStackTrace();
        
        } finally {
        
        	try {
            
        		if (keyResultSet != null) keyResultSet.close();
                if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
                
            	e.printStackTrace();
            
            }
        
        }
        
    }

    public void update(Club c) {
        
    	PreparedStatement stmt = null;
        
    	try {
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"UPDATE club SET name = ?, foundation_date = ?, phone_number = ?, email = ?, badge_image = ?, budget = ?, id_stadium = ? WHERE id = ?"
            );
            stmt.setString(1, c.getName());
            stmt.setObject(2, c.getFoundationDate());
            stmt.setString(3, c.getPhoneNumber());
            stmt.setString(4, c.getEmail());
            stmt.setString(5, c.getBadgeImage());
            stmt.setDouble(6, c.getBudget());
            stmt.setInt(7, c.getStadium().getId());
            stmt.setInt(8, c.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
        
        } finally {
        
        	try {
            
        		if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
                
            	e.printStackTrace();
            
            }
        
        }
    
    }

    public void delete(int id) {
        
    	PreparedStatement stmt = null;
        
    	try {
        
    		stmt = DbConnector.getInstance().getConn().prepareStatement(
    			"DELETE FROM club WHERE id = ?"
            );
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            
        	e.printStackTrace();
        
        } finally {
        
        	try {
            
        		if (stmt != null) stmt.close();
                DbConnector.getInstance().releaseConn();
                
            } catch (SQLException e) {
                
            	e.printStackTrace();
            
            }
        
        }
    
    }

}