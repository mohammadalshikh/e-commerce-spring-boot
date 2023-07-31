package com.jtspringproject.JtSpringProject;

public class CartItem {
    private String productName;
    private int quantity;
    private double totalPrice;
    private int productID;

    public CartItem(String productName, int quantity, double totalPrice, int productID) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.productID = productID;
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
    public int getProductID() {return productID;}
}