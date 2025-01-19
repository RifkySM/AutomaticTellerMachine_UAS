package Repositories.Auth;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Models.User;
import Utils.Encrypt;

public class AuthRepository {
    private Connection connection;

    public AuthRepository(Connection connection) {
        this.connection = connection;
    }

    public User authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? LIMIT 1";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                String storedPassword = resultSet.getString("password");
                if (Encrypt.check(storedPassword, password)) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    return new User(id, username, password, name);
                }
            }
            System.out.println("Invalid username or password for user: " + username);
        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
        }

        return null;
    }

}
