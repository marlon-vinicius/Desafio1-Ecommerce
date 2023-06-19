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
}
