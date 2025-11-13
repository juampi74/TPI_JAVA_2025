package data;

import java.sql.*;

public class DbConnector {

	private static DbConnector instance;
	
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String host = "localhost";
	private String port = "3306";
	private String user = "java";
	private String password = "himitsu";
	
	private String db = "tpjava";
	private int connected = 0;
	private Connection conn = null;
	
	private DbConnector() {
		
		try {
			
			Class.forName(driver);
		
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		
		}
	
	}
	
	public static DbConnector getInstance() {
		
		if (instance == null) {
		
			instance = new DbConnector();
		
		}
		
		return instance;
		
	}
	
	public Connection getConn() throws SQLException {
		
		try {
			
			if (conn == null || conn.isClosed()) {
				
				conn=DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db, user, password);
				connected = 0;
				
			}
		
		} catch (SQLException e) {
		
			e.printStackTrace();
			throw new SQLException("No se pudo conectar a la base de datos.", e);
		
		}
		
		connected++;
		
		return conn;
		
	}
	
	public void releaseConn() throws SQLException {
		
		connected--;
		
		try {
			
			if (connected <= 0) {
				
				if (conn != null) {
					
					conn.close();
				
				}
			
			}
		
		} catch (SQLException e) {
		
			e.printStackTrace();
		
		}
	
	}

}