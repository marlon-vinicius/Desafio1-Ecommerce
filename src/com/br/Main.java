package com.br;

import com.br.connection.DBConnection;

import java.sql.*;
import java.util.Scanner;

import static com.br.shoppingcart.Customer.menuCustomer;
import static com.br.shoppingcart.Employee.menuEmployee;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();

        DBConnection.createDatabase();
        DBConnection.useDatabase();
        DBConnection.dropTable();
        DBConnection.createTable();
        DBConnection.generateProducts();

        Scanner scanner = new Scanner(System.in);
        menuMain(scanner);
        scanner.close();
        DBConnection.dropDatabase();
        DBConnection.closeConnection();
    }

    public static void menuMain( Scanner scanner) throws SQLException {
        int option;
        do {
            System.out.println("\n============= MAIN MENU ============");
            System.out.println("Who will use the system ? ");
            System.out.println("1. Employee\n"+
                    "2. Customer\n"+
                    "0. Exit" );
            System.out.print("Choose your option: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter an integer.");
                System.out.print("Choose your option: ");
                scanner.next();
            }
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    menuEmployee();
                    break;
                case 2:
                    menuCustomer();
                    break;
                case 0:
                    System.out.println("Leaving the program...");
                    break;
                default:
                    System.out.println("Invalid option, try again !");
                    break;
            }
        }while (option != 0);

    }
}