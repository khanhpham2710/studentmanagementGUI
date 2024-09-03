package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A class to manage the database connection.
 */
public class MyConnection {
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Bin@2710";
    private static final String DATA_CONN = "jdbc:mysql://localhost:3306/student_management";
    private static Connection con = null;
    private static final Logger LOGGER = Logger.getLogger(MyConnection.class.getName());
   
    public static Connection getConnection() {
        if (con == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(DATA_CONN, USERNAME, PASSWORD);
            } catch (ClassNotFoundException e) {
                LOGGER.log(Level.SEVERE, "JDBC Driver not found", e);
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE, "SQL Exception occurred", e);
            }
        }
        return con;
    }
}
