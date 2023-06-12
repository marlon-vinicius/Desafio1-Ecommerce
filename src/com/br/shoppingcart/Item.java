package com.br.shoppingcart;

public class Item {
    private Product product;
    private int quantityItems;

    public Item(Product product, int quantityItems) {
        this.product = product;
        this.quantityItems = quantityItems;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantityItems() {
        return quantityItems;
    }

    public void setQuantityItems(int quantityItems) {
        this.quantityItems = quantityItems;
    }
}
