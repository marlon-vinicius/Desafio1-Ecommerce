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
            System.out.println("============= MENU EMPLOYEE =============");
            System.out.println("1. Register product \n"+
                    "2. Update product\n"+
                    "3. List all products\n"+
                    "0. Return");
            System.out.print("Choose your option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0: menuMain();
                    break;
                case 1: registerProduct(DBConnection.getConnection(), scanner);
                    break;
                case 2: updateProduct(DBConnection.getConnection(),scanner);
                    break;
                case 3: DBConnection.getAllProducts();
                    break;
            }
        } while (option != 0);
        scanner.close();
    }
    private static void registerProduct(Connection connection, Scanner scanner) throws SQLException, SQLException {
        System.out.println("============= REGISTER PRODUCT =============");
        System.out.print("Name of product: ");
        String name = scanner.nextLine();
        System.out.print("Price of product: ");
        double price = scanner.nextDouble();
        System.out.print("Quantity of product: ");
        int quantity = scanner.nextInt();

        String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, name);
        statement.setDouble(2, price);
        statement.setInt(3, quantity);
        statement.executeUpdate();

        System.out.println("Product has been successfully registered !");
    }

    private static void updateProduct(Connection connection, Scanner scanner) throws SQLException {
        System.out.println("============= UPDATE PRODUCT =============");
        System.out.print("Product name to be updated: ");
        String name = scanner.nextLine();

        System.out.print("Set new name: ");
        String nameUpdate = scanner.nextLine();
        System.out.print("Set new price: ");
        double price = scanner.nextDouble();
        System.out.print("Set new quantity: ");
        int quantity = scanner.nextInt();

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
