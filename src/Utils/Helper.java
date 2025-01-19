package Utils;

import java.io.Console;
import java.util.Scanner;

public class Helper {
    public static void loadingText(String text, int milisecond) {
        try {
            for (int i = 0; i < 6; i++) {
                String dots = ".".repeat(i % 6);
                System.out.print("\r" + text + dots);
                Thread.sleep(milisecond);
            }
        } catch (Exception e) {
        }
    }

    public static void loadingText(String text) {
        loadingText(text, 500);
    }

    public static String inputText(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt + " ");
            input = scanner.nextLine();

            if (input != null && !input.isBlank()) {
                return input;
            }
            System.out.println("Input cannot be empty. Please try again.");
        }
    }

    public static int inputNumber(Scanner scanner, String prompt) {
        int num;
        while (true) {
            System.out.print(prompt + " ");
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
                scanner.nextLine();
                return num;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    // make a input password function
    public static String inputPassword(Scanner scanner, String prompt) {
        Console console = System.console();
        char[] passwordArray = console.readPassword(prompt);
        String password = new String(passwordArray);
        java.util.Arrays.fill(passwordArray, ' ');
        return password;
    }

    public static void printLines(String... lines) {
        for (String line : lines) {
            System.out.println(line);
        }
    }

    public static void clear() {
        System.out.print("\033[2J\033[1;1H");
    }
}
