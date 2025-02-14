import java.sql.Connection;
import java.util.Scanner;

import Cli.CommandHandler;
import Database.DatabaseConnector;
import Models.User;
import Utils.Helper;

public class Main {
    @SuppressWarnings("static-access")

    public static void main(String[] args) {
        Connection connection = DatabaseConnector.connect();
        Scanner scanner = new Scanner(System.in);
        CommandHandler commandHandler = new CommandHandler(scanner, connection);

        if (connection == null) {
            System.out.println("Failed to connect to the dqatabase. Exiting...");
            System.exit(1);
        }

        commandHandler.start();

        User user = commandHandler.login();

        if (user == null) {
            System.out.println("Authentication failed. Exiting...");
            System.exit(1);
        }

        Helper.clear();

        int menu = commandHandler.menu();
        do {
            Helper.clear();
            switch (menu) {
                case 1:
                    commandHandler.checkBalance(user);
                    break;
                case 2:
                    commandHandler.depositMoney(user);
                    break;
                case 3:
                    commandHandler.withdrawMoney(user);
                    break;
                case 4:
                    commandHandler.transferMoney(user);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(1);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }

            String confirmation = Helper.inputText(scanner, "Do you want to continue (y/n)?");

            Helper.clear();
            if (!confirmation.equals("y")) {
                System.out.println("Goodbye!");
                break;
            }

            menu = commandHandler.menu();
        } while (true);

    }
}