package Cli;

import java.sql.Connection;
import java.util.Scanner;

import Models.User;
import Repositories.Account.AccountRepository;
import Repositories.Auth.AuthRepository;
import Repositories.User.UserRepository;
import Utils.Helper;
import Models.Account;

public class CommandHandler {

    private static Scanner scanner;
    private static Connection connection;
    private static AccountRepository accountRepository;
    private static UserRepository userRepository;

    public CommandHandler(Scanner scanner, Connection connection) {
        CommandHandler.scanner = scanner;
        CommandHandler.connection = connection;
        CommandHandler.accountRepository = new AccountRepository(connection);
        CommandHandler.userRepository = new UserRepository(connection);
    }

    public static void start() {
        Helper.printLines(
                "========================================",
                "        Welcome to the Bank of Mars!",
                "========================================",
                "   Automatic Teller Machine (ATM) System",
                "----------------------------------------",
                "   Experience seamless banking with us.",
                "   This is a simulation of an ATM at the",
                "   fictional Bank of Mars.",
                "----------------------------------------",
                "     Please select an option to begin.",
                "========================================");
    }

    public static User login() {
        Helper.printLines(
                "========================================",
                "               User Login",
                "========================================");

        AuthRepository auth = new AuthRepository(connection);
        int attempts = 0;
        final int maxAttempts = 3;

        while (attempts < maxAttempts) {
            String username = Helper.inputText(scanner, "Enter Username: ");
            String password = Helper.inputPassword(scanner, "Enter Password: ");

            Helper.loadingText("Signing in...");

            User user = auth.authenticate(username, password);

            if (user == null) {
                attempts++;
                Helper.clear();
                Helper.printLines(
                        "----------------------------------------",
                        "Error: Invalid credentials. Please try again.",
                        "Attempts remaining: " + (maxAttempts - attempts),
                        "----------------------------------------");
            } else {
                Helper.printLines(
                        "----------------------------------------",
                        "Welcome back, " + user.name + "!",
                        "You have successfully logged in.",
                        "----------------------------------------");
                return user;
            }
        }

        Helper.printLines(
                "----------------------------------------",
                "Error: Maximum login attempts exceeded. Please try again later.",
                "----------------------------------------");
        return null;
    }

    public static int menu() {
        Helper.printLines(
                "========================================",
                "                 Main Menu",
                "========================================",
                "1. Check Balance",
                "2. Deposit Money",
                "3. Withdraw Money",
                "4. Transfer Money",
                "5. Exit",
                "========================================");

        return Helper.inputNumber(scanner, "Please enter your choice (1-5): ");
    }

    public void checkBalance(User user) {
        Helper.printLines(
                "========================================",
                "             Check Balance",
                "========================================");
        Helper.loadingText("Retrieving account details...");
        Helper.clear();

        Account account = accountRepository.getUserAccount(user.id);

        Helper.printLines(
                "----------------------------------------",
                "Account Holder: " + user.name,
                "Account Number: " + account.accountNumber,
                "Current Balance: " + account.getBalance(),
                "----------------------------------------",
                "Thank you for using our services!",
                "========================================");
    }

    public static void depositMoney(User user) {
        Helper.printLines(
                "========================================",
                "          Deposit Money Menu",
                "========================================");

        Helper.loadingText("Loading account details...");
        Helper.clear();

        Account account = accountRepository.getUserAccount(user.id);

        Helper.printLines(
                "Current Balance: " + account.getBalance(),
                "========================================");

        double amount;
        while (true) {
            amount = Helper.inputNumber(scanner, "Enter the amount to deposit: ");
            if (amount <= 0) {
                Helper.printLines("Invalid amount. Please enter a positive number.");
            } else {
                break;
            }
        }

        Helper.printLines(
                "----------------------------------------",
                "You are depositing: " + amount);

        String confirmation = Helper.inputText(scanner, "Do you want to proceed? (y/n): ");
        if (!confirmation.equals("y")) {
            System.out.println("Deposit canceled.");
            return;
        }

        Helper.loadingText("Processing deposit...");
        double newBalance = account.balance + amount;

        if (accountRepository.updateAccountBalance(account.id, newBalance)) {
            System.out.println("\nDeposit successful!");
            account = accountRepository.getUserAccount(user.id);
            Helper.printLines("Your new balance is: " + account.getBalance());
        } else {
            Helper.printLines("An error occurred. Please try again.");
        }

        Helper.printLines("========================================");
    }

    public static void withdrawMoney(User user) {
        Helper.printLines(
                "========================================",
                "            Withdraw Money",
                "========================================");

        Account account = accountRepository.getUserAccount(user.id);

        Helper.printLines(
                "Your current balance: " + account.getBalance(),
                "----------------------------------------");

        double amount;
        while (true) {
            amount = Helper.inputNumber(scanner, "Enter the amount to withdraw: ");
            if (amount <= 0) {
                Helper.printLines("Invalid amount. Please enter a positive value.");
            } else if (amount > account.balance) {
                Helper.printLines("Insufficient funds. Your balance is: " + account.getBalance());
            } else {
                break;
            }
        }

        Helper.printLines(
                "----------------------------------------",
                "You are withdrawing: " + String.format("%.2f", amount));

        String confirmation = Helper.inputText(scanner, "Do you want to proceed? (y/n): ");
        ;
        if (!confirmation.equals("y")) {
            System.out.println("Withdrawal canceled.");
            return;
        }

        Helper.loadingText("Processing your transaction...");
        double newBalance = account.balance - amount;

        if (accountRepository.updateAccountBalance(account.id, newBalance)) {
            Helper.printLines(
                    "\nWithdrawal successful!",
                    "Your new balance: " + String.format("%.2f", newBalance));
        } else {
            Helper.printLines("An error occurred while processing your transaction. Please try again.");
        }

        System.out.println("========================================");
    }

    public static void transferMoney(User user) {
        Helper.printLines(
                "========================================",
                "            Transfer Money",
                "========================================");

        Account fromAccount = accountRepository.getUserAccount(user.id);

        String accountNumber = Helper.inputText(scanner, "Enter Destination Account Number:");
        Helper.loadingText("Loading account details");
        Helper.clear();

        Account toAccount = accountRepository.getUserAccountByAccountNumber(accountNumber);

        if (toAccount == null) {
            Helper.printLines("Account not found. Please try again.");
            return;
        }

        User toUser = userRepository.getUserById(toAccount.user_id);
        Helper.printLines(
                "----------------------------------------",
                "Transferring to:",
                "Account Holder: \t" + toUser.name,
                "Account Number: \t" + toAccount.accountNumber,
                "----------------------------------------");

        double amount;
        while (true) {
            amount = Helper.inputNumber(scanner, "Enter the amount to transfer: ");
            if (amount <= 0) {
                System.out.println("Invalid amount. Please enter a positive value.");
            } else if (amount > fromAccount.balance) {
                System.out.println(
                        "Insufficient funds. Your balance is: Rp." + fromAccount.getBalance());
            } else {
                break;
            }
        }

        System.out.println("----------------------------------------");
        System.out.println("You are transferring: Rp." + String.format("%.2f", amount));
        System.out.print("Do you want to proceed? (y/n): ");
        String confirmation = scanner.nextLine().trim().toLowerCase();
        if (!confirmation.equals("y")) {
            System.out.println("Transfer canceled.");
            return;
        }

        Helper.clear();

        Helper.loadingText("Processing your transfer");
        double newSenderBalance = fromAccount.balance - amount;
        double newRecipientBalance = toAccount.balance + amount;

        boolean senderUpdated = accountRepository.updateAccountBalance(fromAccount.id, newSenderBalance);
        boolean recipientUpdated = accountRepository.updateAccountBalance(toAccount.id, newRecipientBalance);

        Helper.clear();

        if (senderUpdated && recipientUpdated) {
            System.out.println("\nTransfer successful!");
            System.out.println("Your new balance: Rp." + String.format("%.2f", newSenderBalance));
        } else {
            System.out.println("An error occurred during the transfer. Please try again.");
        }

        System.out.println("========================================");
    }

}
