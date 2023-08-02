package com.jtspringproject.JtSpringProject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShopItemTest {

    @Test
    public void testShopItem() {
        String image = "item.jpg";
        String productName = "Product 1";
        int price = 50;
        int productID = 101;
        String suggestedItem = "Suggested Product";

        ShopItem shopItem = new ShopItem(image, productName, price, productID, suggestedItem);

        assertEquals(image, shopItem.getImage());
        assertEquals(productName, shopItem.getProductName());
        assertEquals(price, shopItem.getPrice());
        assertEquals(productID, shopItem.getProductID());
        assertEquals(suggestedItem, shopItem.getSuggestedItem());
    }

    @Test
    public void testGettersWithDifferentValues() {
        String image = "other_item.jpg";
        String productName = "Product 2";
        int price = 25;
        int productID = 102;
        String suggestedItem = "Other Suggested Product";

        ShopItem shopItem = new ShopItem(image, productName, price, productID, suggestedItem);

        assertEquals(image, shopItem.getImage());
        assertEquals(productName, shopItem.getProductName());
        assertEquals(price, shopItem.getPrice());
        assertEquals(productID, shopItem.getProductID());
        assertEquals(suggestedItem, shopItem.getSuggestedItem());
    }
}