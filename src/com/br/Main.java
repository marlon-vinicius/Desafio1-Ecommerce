package com.br;

import com.br.connection.DBConnection;

import java.sql.*;
import java.util.Scanner;

import static com.br.shoppingcart.Customer.menuCustomer;
import static com.br.shoppingcart.Employee.menuEmployee;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getConnection();

        DBConnection.dropTable();
        DBConnection.createTable();
        DBConnection.generateProducts();

        menuMain();

        DBConnection.closeConnection();
    }

    public static void menuMain() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("============= MAIN MENU ============");
            System.out.println("Who will use the system ? ");
            System.out.println("1. Employee\n"+
                    "2. Customer\n"+
                    "0. Exit" );
            System.out.println("Choose your option:");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1: menuEmployee();
                    break;
                case 2: menuCustomer();
                    break;
                default:
                    System.out.println("Invalid option, try again !");
            }
        } while (option !=0);
        scanner.close();
    }
}