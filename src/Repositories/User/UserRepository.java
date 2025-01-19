package Repositories.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Models.User;

public class UserRepository {
    private Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ? LIMIT 1";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int userId = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                return new User(userId, username, password, name);
            }
            return null;
        } catch (Exception e) {
            System.err.println("Failed to execute query " + e.getMessage());
        }
        return null;
    }
}
