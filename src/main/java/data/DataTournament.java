package data;

import entities.*;

import java.sql.*;
import java.util.LinkedList;
import java.time.*;

public class DataTournament {

	public LinkedList<Tournament> getAll() {
        
    	Statement stmt = null;
        ResultSet rs = null;
        LinkedList<Tournament> tournaments = new LinkedList<>();

        try {
            
        	stmt = DbConnector.getInstance().getConn().createStatement();
            rs = stmt.executeQuery("SELECT id, name, start_date, end_date, format, season, id_association FROM tournament");
            if (rs != null) {
                
            	while (rs.next()) {
                    
            		Tournament tournament = new Tournament();
            		tournament.setId(rs.getInt("id"));
            		tournament.setName(rs.getString("name"));
            		tournament.setStartDate(rs.getObject("start_date", LocalDate.class));
            		tournament.setEndDate(rs.getObject("end_date", LocalDate.class));
            		tournament.setFormat(rs.getString("format"));
            		tournament.setSeason(rs.getString("season"));

            		PreparedStatement stmt2 = null;
            	    ResultSet rs2 = null;
            		
            	    try {
            	    
	            		stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	            			"SELECT id, name, creation_date FROM association WHERE id = ?"
	                	);
	                    stmt2.setInt(1, rs.getInt("id_association"));
	                    rs2 = stmt2.executeQuery();
	                    
	                    if (rs2 != null && rs2.next()) {
	                    	
	                    	Association association = new Association();
	                    	association.setId(rs2.getInt("id"));
	                    	association.setName(rs2.getString("name"));
	                    	association.setCreationDate(rs2.getObject("creation_date", LocalDate.class));
	                    	
	                    	tournament.setAssociation(association);
	                    	
	                    }
	            		
	                    tournaments.add(tournament);
                
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

        return tournaments;
    }

    public Tournament getById(int id) {
        
    	Tournament tournament = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"SELECT id, name, start_date, end_date, format, season, id_association FROM tournament WHERE id = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs != null && rs.next()) {
                
            	tournament = new Tournament();
        		tournament.setId(rs.getInt("id"));
        		tournament.setName(rs.getString("name"));
        		tournament.setStartDate(rs.getObject("start_date", LocalDate.class));
        		tournament.setEndDate(rs.getObject("end_date", LocalDate.class));
        		tournament.setFormat(rs.getString("format"));
        		tournament.setSeason(rs.getString("season"));

        		PreparedStatement stmt2 = null;
        	    ResultSet rs2 = null;
        	    
        	    try {
        		
	        		stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	        			"SELECT id, name, creation_date FROM association WHERE id = ?"
	            	);
	                stmt2.setInt(1, rs.getInt("id_association"));
	                rs2 = stmt2.executeQuery();
	                
	                if (rs2 != null && rs2.next()) {
	                	
	                	Association association = new Association();
	                	association.setId(rs2.getInt("id"));
	                	association.setName(rs2.getString("name"));
	                	association.setCreationDate(rs2.getObject("creation_date", LocalDate.class));
	                	
	                	tournament.setAssociation(association);
	                	
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
        
        return tournament;
    }
    
public LinkedList<Tournament> getByAssociationId(int id) {
        
    	LinkedList<Tournament> tournaments = new LinkedList<Tournament>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"SELECT t.* FROM tournament t  INNER JOIN association a ON t.id_association = a.id WHERE id_association = ?"
            );
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                
        		Tournament tournament = new Tournament();
        		tournament.setId(rs.getInt("id"));
        		tournament.setName(rs.getString("name"));
        		tournament.setStartDate(rs.getObject("start_date", LocalDate.class));
        		tournament.setEndDate(rs.getObject("end_date", LocalDate.class));
        		tournament.setFormat(rs.getString("format"));
        		tournament.setSeason(rs.getString("season"));

        		PreparedStatement stmt2 = null;
        	    ResultSet rs2 = null;
        		
        	    try {
        	    
            		stmt2 = DbConnector.getInstance().getConn().prepareStatement(
            			"SELECT id, name, creation_date FROM association WHERE id = ?"
                	);
                    stmt2.setInt(1, rs.getInt("id_association"));
                    rs2 = stmt2.executeQuery();
                    
                    if (rs2 != null && rs2.next()) {
                    	
                    	Association association = new Association();
                    	association.setId(rs2.getInt("id"));
                    	association.setName(rs2.getString("name"));
                    	association.setCreationDate(rs2.getObject("creation_date", LocalDate.class));
                    	
                    	tournament.setAssociation(association);
                    	
                    }
            		
                    tournaments.add(tournament);
        	    
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
        
        return tournaments;
    }

    public LinkedList<Tournament> getByName(String name) {
        
    	PreparedStatement stmt = null;
        ResultSet rs = null;
        LinkedList<Tournament> tournaments = new LinkedList<>();

        try {
            
        	stmt = DbConnector.getInstance().getConn().prepareStatement(
        		"SELECT id, name, start_date, end_date, format, season, id_association FROM tournament WHERE name = ?"
            );
            stmt.setString(1, name);
            rs = stmt.executeQuery();
            
            if (rs != null) {
            	
                while (rs.next()) {
                    
                	Tournament tournament = new Tournament();
            		tournament.setId(rs.getInt("id"));
            		tournament.setName(rs.getString("name"));
            		tournament.setStartDate(rs.getObject("start_date", LocalDate.class));
            		tournament.setEndDate(rs.getObject("end_date", LocalDate.class));
            		tournament.setFormat(rs.getString("format"));
            		tournament.setSeason(rs.getString("season"));

            		PreparedStatement stmt2 = null;
            	    ResultSet rs2 = null;
            		
            	    try {
            	    
	            		stmt2 = DbConnector.getInstance().getConn().prepareStatement(
	            			"SELECT id, name, creation_date FROM association WHERE id = ?"
	                	);
	                    stmt2.setInt(1, rs.getInt("id_association"));
	                    rs2 = stmt2.executeQuery();
	                    
	                    if (rs2 != null && rs2.next()) {
	                    	
	                    	Association association = new Association();
	                    	association.setId(rs2.getInt("id"));
	                    	association.setName(rs2.getString("name"));
	                    	association.setCreationDate(rs2.getObject("creation_date", LocalDate.class));
	                    	
	                    	tournament.setAssociation(association);
	                    	
	                    }
	            		
	                    tournaments.add(tournament);
	                    
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

        return tournaments;

    }
    
    public void add(Tournament t) {
        
    	PreparedStatement stmt = null;
        ResultSet keyResultSet = null;
        
        try {
        	
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"INSERT INTO tournament (name, start_date, end_date, format, season, id_association) VALUES (?, ?, ?, ?, ?, ?)",
            	PreparedStatement.RETURN_GENERATED_KEYS
            );
            stmt.setString(1, t.getName());
            stmt.setObject(2, t.getStartDate());
            stmt.setObject(3, t.getEndDate());
            stmt.setString(4, t.getFormat());
            stmt.setString(5, t.getSeason());
            stmt.setInt(6, t.getAssociation().getId());
            stmt.executeUpdate();

            keyResultSet = stmt.getGeneratedKeys();
            if (keyResultSet != null && keyResultSet.next()) {
                
            	t.setId(keyResultSet.getInt(1));
            
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

    public void update(Tournament t) {
        
    	PreparedStatement stmt = null;
        
    	try {
            stmt = DbConnector.getInstance().getConn().prepareStatement(
            	"UPDATE tournament SET name = ?, start_date = ?, end_date = ?, format = ?, season = ?, id_association = ? WHERE id = ?"
            );
            stmt.setString(1, t.getName());
            stmt.setObject(2, t.getStartDate());
            stmt.setObject(3, t.getEndDate());
            stmt.setString(4, t.getFormat());
            stmt.setString(5, t.getSeason());
            stmt.setInt(6, t.getAssociation().getId());
            stmt.setInt(7, t.getId());
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
    			"DELETE FROM tournament WHERE id = ?"
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