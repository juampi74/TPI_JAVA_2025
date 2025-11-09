package data;

import enums.PersonRole;

import java.sql.*;

public class DataPerson {
	
	public PersonRole getRoleByPersonId(int id) {
		
		PersonRole role = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
		
			stmt = DbConnector.getInstance().getConn().prepareStatement(
				"SELECT role FROM person WHERE id = ?"
			);
			stmt.setInt(1, id);
			rs = stmt.executeQuery();
			
			if (rs != null && rs.next()) {
			
				role = PersonRole.valueOf(rs.getString("role"));
				
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		
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