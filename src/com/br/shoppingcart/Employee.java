package com.br.shoppingcart;

import com.br.connection.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

import static com.br.Main.menuMain;
public class Employee {

    public static void menuEmployee() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("\n============= MENU EMPLOYEE =============");
            System.out.println("1. Register product \n"+
                    "2. Update product\n"+
                    "3. List all products in stock\n"+
                    "0. Return");
            System.out.print("Choose your option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                System.out.print("Choose your option: ");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0: menuMain(scanner);
                    break;
                case 1: registerProduct(DBConnection.getConnection(), scanner);
                    break;
                case 2: updateProduct(DBConnection.getConnection(),scanner);
                    break;
                case 3: DBConnection.getStock();
                    break;
                default: System.out.println("Invalid option, try again !");
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    private static void registerProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n============= REGISTER PRODUCT =============");
        System.out.print("Name of product: ");
        String name = scanner.nextLine();

        double price = 0.0;
        while (true) {
            System.out.print("Price of product: ");
            String input = scanner.nextLine();

            try {
                price = Double.parseDouble(input);
                if (price > 0) {
                    break;
                } else {
                    System.out.println("Invalid input. Price cannot be negative");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Quantity of product: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Please enter a positive number");
                }
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next();
            }
        }

        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setDouble(2, price);
        statement.setInt(3, quantity);
        statement.executeUpdate();

        System.out.println("\nProduct has been successfully registered !");
    }

    private static void updateProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("\n============= UPDATE PRODUCT =============");
        DBConnection.getStock();
        System.out.print("Product name to be updated: ");
        String name = scanner.nextLine();

        System.out.print("Set new name: ");
        String nameUpdate = scanner.nextLine();

        double price = 0.0;
        while (true) {
            System.out.print("Set new price: ");
            String input = scanner.nextLine();

            try {
                price = Double.parseDouble(input);
                if (price > 0) {
                    break;
                } else {
                    System.out.println("Invalid input. Price cannot be negative");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }

        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Set new quantity: ");
            if (scanner.hasNextInt()) {
                quantity = scanner.nextInt();
                if (quantity <= 0) {
                    System.out.println("Please enter a positive number");
                }
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next();
            }
        }

        String sql = "UPDATE products SET name = ?, price = ?, quantity = ? WHERE name = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nameUpdate);
        statement.setDouble(2, price);
        statement.setInt(3, quantity);
        statement.setString(4, name);
        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Product has been updated successfully!");
        } else {
            System.out.println("Product not found !");
        }
    }
}