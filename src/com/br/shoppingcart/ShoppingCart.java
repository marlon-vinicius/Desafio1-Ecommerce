package com.br.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private static List<Product> cart = new ArrayList<>();

    public static List<Product> getCart() {
        return cart;
    }

    public static void setCart(List<Product> cart) {
        ShoppingCart.cart = cart;
    }

    public static void add(Product product) {
        cart.add(product);
    }

    public static boolean isEmpty() {
        cart.isEmpty();
        return true;
    }

    public static void remove(Integer id) {
        if (cart.contains(id)) {
            cart.remove(Integer.valueOf(id));
            System.out.println("Product removed from cart successfully!");
        } else {
            System.out.println("Product not found in cart!");
        }
    }
}
