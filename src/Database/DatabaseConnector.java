package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static Connection connect() {
        System.out.println("\033c");
        Connection connection = null;

        try {
            System.out.println("Attempting Connect to the database...");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/algo",
                    "root",
                    "");
            System.out.println("Connection to the Database established successfully!");
            System.out.println("\033c");
        } catch (SQLException e) {
            System.err.println("Connection Failed : " + e.getMessage());
        }

        return connection;
    }
}
