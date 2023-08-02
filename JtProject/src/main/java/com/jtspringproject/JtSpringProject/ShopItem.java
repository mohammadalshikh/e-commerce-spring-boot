package com.jtspringproject.JtSpringProject;

public class ShopItem {

    private String image;
    private String productName;
    private int price;
    private int productID;

    private String suggestedItem;

    public ShopItem(String image, String productName, int price, int productID, String suggestedItem) {
        this.image = image;
        this.productName = productName;
        this.price = price;
        this.productID = productID;
        this.suggestedItem = suggestedItem;
    }

    public String getProductName() {
        return productName;
    }

    public String getImage() {
        return image;
    }

    public int getPrice() {
        return price;
    }

    public int getProductID() {return productID;}

    public String getSuggestedItem() {return suggestedItem;}
}