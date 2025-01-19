package Repositories.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Models.Account;

public class AccountRepository {
    private Connection connection;

    public AccountRepository(Connection connection) {
        this.connection = connection;
    }

    public Account getUserAccount(int user_id) {
        String query = "SELECT * FROM accounts WHERE user_id = ? LIMIT 1";
        Account account = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, user_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                double balance = resultSet.getDouble("balance");
                String accountNumber = resultSet.getString("account_number");
                account = new Account(id, user_id, balance, accountNumber);
            }
            return account;
        } catch (Exception e) {
            return null;
        }
    }

    public Account getUserAccountByAccountNumber(String accountNumber) {
        String query = "SELECT * FROM accounts WHERE account_number =? LIMIT 1";
        Account account = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, accountNumber);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int user_id = resultSet.getInt("user_id");
                double balance = resultSet.getDouble("balance");
                account = new Account(id, user_id, balance, accountNumber);
            }
            return account;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean updateAccountBalance(int id, double newBalance) {
        String query = "UPDATE accounts SET balance = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDouble(1, newBalance);
            preparedStatement.setInt(2, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}
