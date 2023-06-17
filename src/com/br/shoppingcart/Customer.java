package com.br.shoppingcart;

import com.br.connection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
                    "4. Remove all products from cart\n"+
                    "5. Show cart\n"+
                    "0. Return");
            System.out.print("Choose your option: ");
            option = scanner.nextInt();
            scanner.nextLine();
            switch (option) {
                case 0: menuMain();
                    break;
                case 1: DBConnection.getAllProducts();
                    break;
                case 2: addProduct(scanner);
                    break;
                case 3: removeProduct(scanner);
                    break;
                case 4: 
                    break;
                case 5: showCart();
                    break;    
            }
        } while (option != 0);
        scanner.close();
    }

    private static void addProduct(Scanner scanner) throws SQLException {
        System.out.println("=========== Adding a product ===========");
        DBConnection.getAllProducts();
        System.out.print("Product ID to be added: ");
        int id = scanner.nextInt();
        System.out.print("Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        ResultSet resultSet = DBConnection.getProduct(id);

        if (resultSet.next()) {
            if (resultSet.getInt("quantity") < quantity) {
                do {
                    System.out.println("Quantity cannot be greater than the total in stock");
                    System.out.print("New quantity: ");
                    quantity = scanner.nextInt();
                } while (resultSet.getInt("quantity") < quantity);
            }

            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");

            Product product = new Product(id, name, price, quantity);
            ShoppingCart.add(product);

            DBConnection.removeQuantity(id, quantity);

            System.out.println("Product added to the cart successfully!\n");
        } else {
            System.out.println("Product not found!\n");
        }
    }

    private static void removeProduct(Scanner scanner) {
        System.out.println("========== Removing a product =========");
        showCart();
        System.out.print("Product ID to be removed: ");
        int id = scanner.nextInt();

        List<Product> cart = ShoppingCart.getCart();
        if (cart.contains(id)){
            for (Product product : cart) {
                if (product.getId() == id){
                    cart.remove(id);
                    System.out.println("Product removed from cart successfully!");
                }
            }
        } else {
            System.out.println("Product not found in cart!");
        }

        //ShoppingCart.remove(id);
    }

    private static void showCart() {
        System.out.println("\n================ Cart =============");
        List<Product> cart = ShoppingCart.getCart();
        if (cart.isEmpty()) {
            System.out.println("Empty cart");
        } else {
            for (Product product : cart) {
                System.out.println("ID: " + product.getId() + ", Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
            }
        }
    }
}
