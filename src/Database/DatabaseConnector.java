package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Utils.Helper;

public class DatabaseConnector {
    public static Connection connect() {
        System.out.println("\033c");
        Connection connection = null;
        try {
            System.out.println("Attempting Connect to the database...");
            Helper.loadingText("loading", 500);
            System.out.println("\033c");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://127.0.0.1:3306/automatic_teller_machine",
                    "root",
                    "");
            System.out.println("Connection to the Database established successfully!");
            Thread.sleep(2000);
            System.out.println("\033c");
        } catch (SQLException e) {
            System.err.println("Connection Failed : " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error Occured : " + e.getMessage());
        }

        return connection;
    }
}
