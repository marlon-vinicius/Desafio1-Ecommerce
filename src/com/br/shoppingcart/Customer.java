package com.br.shoppingcart;

import java.sql.SQLException;
import java.util.Scanner;

import static com.br.Main.menuMain;

public class Customer {
    public static void menuCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("============= MENU CUSTOMER ============");
            System.out.println("1. List all products \n"+
                    "2. Add product to cart\n"+
                    "3. Remove product from cart\n"+
                    "0. Return");
            System.out.println("Choose your option:");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0: menuMain();
                    break;
                case 1: ;
                    break;
                case 2: ;
                    break;
            }
        } while (option != 0);
        scanner.close();
    }
}
