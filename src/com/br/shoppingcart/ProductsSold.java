package com.br.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ProductsSold {
    private static List<Product> productsSold = new ArrayList<>();

    public static List<Product> getProductsSold() {
        return productsSold;
    }

    public static void setProductsSold(List<Product> productsSold) {
        ProductsSold.productsSold = productsSold;
    }
}
