package database;
import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

	public static Connection getConnection() {
	    try {
	        String url = "jdbc:mysql://localhost:3306/axis_db"; 
	        Connection freshConnection = DriverManager.getConnection(url, "root", "");    
	        return freshConnection;
	    } catch (Exception e) {
	        System.out.println("Connection Failed: " + e.getMessage());
	        return null;
	    }
	}
}