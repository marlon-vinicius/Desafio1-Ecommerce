package com.br.shoppingcart;

import com.br.connection.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import static com.br.Main.menuMain;

public class Customer {
    public static void menuCustomer() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int option;
        do {
            System.out.println("\n============= MENU CUSTOMER ============");
            System.out.println("1. List all products \n"+
                    "2. Add product to cart\n"+
                    "3. Remove product from cart\n"+
                    "4. Remove all products from cart\n"+
                    "5. Show cart\n"+
                    "6. Checkout\n"+
                    "7. Show sold products\n"+
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
                case 1: DBConnection.getAllProducts();
                    break;
                case 2: addProduct(scanner);
                    break;
                case 3: removeProduct(scanner);
                    break;
                case 4: clearCart();
                    break;
                case 5: showCart();
                    break;
                case 6: checkout(scanner);
                    break;
                case 7: showProductsSold();
                    break;
                default:
                    System.out.println("Invalid option, try again !");
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    private static void addProduct(Scanner scanner) throws SQLException {
        System.out.println("\n=========== Adding a product ===========");
        DBConnection.getAllProducts();

        int id = 0;
        while (id <= 0) {
            System.out.print("\nProduct ID to be added: ");
            if (scanner.hasNextInt()) {
                id = scanner.nextInt();
                if (id <= 0) {
                    System.out.println("Please enter a positive number");
                }
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next();
            }
        }

        int quantity = 0;
        while (quantity <= 0) {
            System.out.print("Quantity : ");
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

        ResultSet resultSet = DBConnection.getProduct(id);

        if (resultSet.next()) {
            if (resultSet.getInt("quantity") < quantity) {
                System.out.println("\nQuantity cannot be greater than the total in stock");
                return;
            }

            String name = resultSet.getString("name");
            double price = resultSet.getDouble("price");

            Product product = new Product(id, name, price, quantity);
            ShoppingCart.add(product);

            DBConnection.removeQuantity(id, quantity);

            System.out.println("\nProduct added to the cart successfully!");
        } else {
            System.out.println("\nProduct not found!");
        }
    }

    private static void removeProduct(Scanner scanner) throws SQLException {
        System.out.println("\n========== Removing a product =========");
        showCart();

        List<Product> cart = ShoppingCart.getCart();
        if (!cart.isEmpty()) {
            int id = 0;
            while (id <= 0) {
                System.out.print("\nProduct ID to be removed: ");
                if (scanner.hasNextInt()) {
                    id = scanner.nextInt();
                    if (id <= 0) {
                        System.out.println("Please enter a positive number");
                    }
                } else {
                    System.out.println("Please enter a valid integer.");
                    scanner.next();
                }
            }

            Iterator<Product> iterator = ShoppingCart.getCart().iterator();
            while (iterator.hasNext()) {
                Product product = iterator.next();
                if (product.getId() == id) {
                    DBConnection.addQuantity(id, product.getQuantity());
                    iterator.remove();
                    System.out.println("\nProduct removed from cart successfully!");
                    return;
                }
            }
            System.out.println("\nProduct not found in cart!");
        } else {
            System.out.println("\nUnable to remove from an empty cart");
        }
    }

    private static void clearCart() throws SQLException {
        List<Product> cart = ShoppingCart.getCart();

        if (cart.isEmpty()) {
            System.out.println("\nCart is already empty!");
        } else {
            for (Product product : cart) {
                System.out.println("Cleaning the shopping cart...");
                DBConnection.addQuantity(product.getId(), product.getQuantity());
            }
        }
        cart.clear();
        System.out.println("\nShopping cart emptied!");
    }

    private static void showCart() {
        System.out.println("\n========================= Cart ======================");
        List<Product> cart = ShoppingCart.getCart();
        double total = 0;
        if (cart.isEmpty()) {
            System.out.println("\nEmpty cart");
        } else {
            for (Product product : cart) {
                if (product.getQuantity() == 0){
                    continue;
                }
                System.out.printf("%s%s%s%s%s%.2f%s%s%n","ID: ", product.getId(),"   Name: ",product.getName(),"   Price: R$",product.getPrice(), "   Quantity: ",product.getQuantity());
                total += totalToPay(product.getPrice(), product.getQuantity());
            }
        }
        System.out.printf("\n%s%.2f \n","TOTAL: R$",total );
        System.out.println("=====================================================");
    }

    private static double totalToPay(Double price, Integer quantity) {
        double total = price * quantity;
        return total;
    }
    private static void checkout(Scanner scanner) {
        System.out.println("\n================ Checkout =============");

        List<Product> cart = ShoppingCart.getCart();
        if (cart.isEmpty()){
            System.out.println("\nCart is empty!");
        }else {
            showCart();
            String answer = "";

            while (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("N")) {
                System.out.print("\nDo you want to complete the purchase? (Y/N): ");
                answer = scanner.nextLine();

                if (!answer.equalsIgnoreCase("Y") && !answer.equalsIgnoreCase("N")) {
                    System.out.println("Invalid input. Please enter 'Y' or 'N'.");
                }
            }

            if (answer.equalsIgnoreCase("Y")) {
                List<Product> productsSold = ProductsSold.getProductsSold();
                productsSold.addAll(cart);
                cart.clear();
                System.out.println("\nPurchase completed! Products removed from cart.");
            } else {
                System.out.println("\nUnfinished purchase. Returning to the customer menu.");
            }
        }
    }

    private static void showProductsSold() {
        System.out.println("\n================ Products Sold =============");
        List<Product> productsSold = ProductsSold.getProductsSold();
        if (productsSold.isEmpty()) {
            System.out.println("Empty records");
        } else {
            for (Product product : productsSold) {
                System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice() + ", Quantity: " + product.getQuantity());
            }
        }
        System.out.println("============================================");
    }
}
