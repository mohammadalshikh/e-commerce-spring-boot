package com.jtspringproject.JtSpringProject;

public class CartItem {
    private String productName;
    private int quantity;
    private double totalPrice;

    public CartItem(String productName, int quantity, double totalPrice) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public String getProductName() {
        return productName;
    }
    public int getQuantity() {
        return quantity;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
}