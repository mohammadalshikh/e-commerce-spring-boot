package com.jtspringproject.JtSpringProject.controller;

import java.sql.*;
import java.util.ArrayList;

import com.jtspringproject.JtSpringProject.CartItem;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    int adminlogcheck = 0;
    public static String usernameforclass = "";

    public static void setUsername(String usernameforclass) {
        AdminController.usernameforclass = usernameforclass;
    }

    @RequestMapping(value = {"/", "/logout"})
    public String returnIndex() {
        adminlogcheck = 0;
        usernameforclass = "";
        UserController.setUsername("");
        return "userLogin";
    }


    @GetMapping("/index")
    public String index(Model model) {
        if (usernameforclass.equalsIgnoreCase(""))
            return "userLogin";
        else {
            model.addAttribute("username", usernameforclass);
            UserController.setUsername(usernameforclass);
            return "index";
        }

    }

    @GetMapping("/userloginvalidate")
    public String userlog(Model model) {

        return "userLogin";
    }

    @RequestMapping(value = "userloginvalidate", method = RequestMethod.POST)
    public String userlogin(@RequestParam("username") String username, @RequestParam("password") String pass, Model model) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from users where username = '" + username + "' and password = '" + pass + "' ;");
            if (rst.next()) {
                usernameforclass = rst.getString(2);
                UserController.setUsername(usernameforclass);
                AdminController.setUsername(usernameforclass);
                return "redirect:/index";
            } else {
                model.addAttribute("failMessage", "Invalid Username or Password");
                return "userLogin";
            }

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "userLogin";


    }

    public static int getUserID() {
        int userID = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from users where username = '" + usernameforclass + "';");
            if (rst.next()) {
                userID = rst.getInt(1);
            }
        } catch (Exception e) {

        }
        return userID;
    }

    @GetMapping("/admin")
    public String adminlogin(Model model) {

        return "adminlogin";
    }

    @GetMapping("/adminhome")
    public String adminHome(Model model) {
        if (adminlogcheck != 0)
            return "adminHome";
        else
            return "redirect:/admin";
    }

    @GetMapping("/loginvalidate")
    public String adminlog(Model model) {

        return "adminlogin";
    }

    @RequestMapping(value = "loginvalidate", method = RequestMethod.POST)
    public String adminlogin(@RequestParam("username") String username, @RequestParam("password") String pass, Model model) {

        if (username.equalsIgnoreCase("admin") && pass.equalsIgnoreCase("123")) {
            adminlogcheck = 1;
            return "redirect:/adminhome";
        } else {
            model.addAttribute("message", "Invalid Username or Password");
            return "adminlogin";
        }
    }

    @GetMapping("/admin/categories")
    public String getcategory() {
        return "categories";
    }

    @RequestMapping(value = "admin/sendcategory", method = RequestMethod.GET)
    public String addcategorytodb(@RequestParam("categoryname") String catname) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            PreparedStatement pst = con.prepareStatement("insert into categories(name) values(?);");
            pst.setString(1, catname);
            int i = pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete")
    public String removeCategoryDb(@RequestParam("id") int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            // Remove category from categories
            PreparedStatement pst = con.prepareStatement("delete from categories where categoryid = ? ;");
            pst.setInt(1, id);
            int i = pst.executeUpdate();

            // Update suggested item
            AdminController.updateProductPair();

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update")
    public String updateCategoryDb(@RequestParam("categoryid") int id, @RequestParam("categoryname") String categoryname) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            PreparedStatement pst = con.prepareStatement("update categories set name = ? where categoryid = ?");
            pst.setString(1, categoryname);
            pst.setInt(2, id);
            int i = pst.executeUpdate();

        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/products")
    public String getproduct(Model model) {
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String addproduct(Model model) {
        return "productsAdd";
    }

    @GetMapping("/admin/products/update")
    public String updateproduct(@RequestParam("pid") int id, Model model) {
        String pname, pdescription, pimage;
        int pid, pprice, pweight, pquantity, pcategory;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from products where id = " + id + ";");

            if (rst.next()) {
                pid = rst.getInt(1);
                pname = rst.getString(2);
                pimage = rst.getString(3);
                pcategory = rst.getInt(4);
                pquantity = rst.getInt(5);
                pprice = rst.getInt(6);
                pweight = rst.getInt(7);
                pdescription = rst.getString(8);
                model.addAttribute("pid", pid);
                model.addAttribute("pname", pname);
                model.addAttribute("pimage", pimage);
                ResultSet rst2 = stmt.executeQuery("select * from categories where categoryid = " + pcategory + ";");
                if (rst2.next()) {
                    model.addAttribute("pcategory", rst2.getString(2));
                }
                model.addAttribute("pquantity", pquantity);
                model.addAttribute("pprice", pprice);
                model.addAttribute("pweight", pweight);
                model.addAttribute("pdescription", pdescription);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "productsUpdate";
    }

    @RequestMapping(value = "admin/products/updateData", method = RequestMethod.POST)
    public String updateproducttodb(@RequestParam("id") int id, @RequestParam("name") String name, @RequestParam("price") int price, @RequestParam("weight") int weight, @RequestParam("quantity") int quantity, @RequestParam("description") String description, @RequestParam("productImage") String picture) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");

            PreparedStatement pst = con.prepareStatement("update products set name= ?,image = ?,quantity = ?, price = ?, weight = ?,description = ? where id = ?;");
            pst.setString(1, name);
            pst.setString(2, picture);
            pst.setInt(3, quantity);
            pst.setInt(4, price);
            pst.setInt(5, weight);
            pst.setString(6, description);
            pst.setInt(7, id);
            int i = pst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/products";
    }

    public static void updateProductStockFromCart(String username) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "dominopassword");
            Statement stmt = con.createStatement();

            // Select items from Cart
            ResultSet cartItemsRst = stmt.executeQuery("SELECT productID, quantity FROM Cart WHERE userId = (SELECT user_id FROM users WHERE username = '" + username + "');");

            // Parse through cartItemsRst
            while (cartItemsRst.next()) {
                // Verify that the product is not a coupon product
                if (cartItemsRst.getInt("productID") != 0) {
                    // Update product quantities
                    PreparedStatement updateProductPst = con.prepareStatement("UPDATE products SET quantity = quantity - ? WHERE productID = ?");
                    updateProductPst.setInt(1, cartItemsRst.getInt("quantity"));
                    updateProductPst.setInt(2, cartItemsRst.getInt("productID"));
                    updateProductPst.executeQuery();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    public static void updateUserCouponsFromCart(String username) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "dominopassword");
            Statement stmt = con.createStatement();

            // Select coupons from Cart
            ResultSet cartItemsRst = stmt.executeQuery("SELECT userID, quantity FROM Cart WHERE productID = 0 AND userId = (SELECT user_id FROM users WHERE username = '" + username + "');");

            // Verify that there was a coupon used
            if (cartItemsRst.next()) {
                // Update user coupon quantity
                PreparedStatement updateProductPst = con.prepareStatement("UPDATE users SET coupons = coupons - ? WHERE  userID = ?");
                updateProductPst.setInt(1, cartItemsRst.getInt("quantity"));
                updateProductPst.setInt(2, cartItemsRst.getInt("userID"));
                updateProductPst.executeQuery();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    public static void updateUserTotalAndCoupons(String username) {
        // Set up prerequisite variables
        float currentTransaction = 0f;
        float cumulativeTotal = 0f;

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "dominopassword");
            Statement stmt = con.createStatement();

            // Get total spent from current transaction
            currentTransaction = AdminController.getCartPrice(username);

            // Check user's cumulativeTotal
            ResultSet cumulativeTotalRst = stmt.executeQuery("SELECT cumulativeTotal FROM users WHERE username = '" + username + "';");
            if (cumulativeTotalRst.next()) {
                cumulativeTotal = cumulativeTotalRst.getFloat("cumulativeTotal");
            }
            cumulativeTotal += currentTransaction;

            // Verify that cumulativeTotal surpassed $100
            if ((int) cumulativeTotal / 100 != 0) {
                // Set up prerequisite variables
                int newCoupons = 0;

                // Find number of coupons redeemed and new cumulativeTotal
                newCoupons = (int) cumulativeTotal / 100;
                cumulativeTotal = cumulativeTotal % 100;

                // Update user's coupons
                PreparedStatement userCouponsPst = con.prepareStatement("UPDATE users SET coupons = (coupons + ?) WHERE username = '" + username + "';");
                userCouponsPst.setInt(1, newCoupons);
                userCouponsPst.executeUpdate();
            }

            // Update user's cumulativeTotal
            PreparedStatement cumulativeTotalPst = con.prepareStatement("UPDATE users SET cumulativeTotal = ? WHERE username = '" + username + "';");
            cumulativeTotalPst.setFloat(1, cumulativeTotal);
            cumulativeTotalPst.executeUpdate();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    @GetMapping("/admin/products/delete")
    public String removeProductDb(@RequestParam("id") int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");


            // Remove product as tuple from ProductMatrix
            PreparedStatement removeFromPMRowPst = con.prepareStatement("DELETE FROM ProductMatrix WHERE product = ?;");
            removeFromPMRowPst.setInt(1, id);
            removeFromPMRowPst.executeQuery();

            // Remove product as attribute from ProductMatrix
            PreparedStatement addToPMColumnPst = con.prepareStatement("ALTER TABLE ProductMatrix DROP COLUMN '?';");
            String productName = "p" + id;
            addToPMColumnPst.setString(1, productName);
            addToPMColumnPst.executeQuery();

            // Remove product from products
            PreparedStatement pst = con.prepareStatement("delete from products where id = ? ;");
            pst.setInt(1, id);
            int i = pst.executeUpdate();

            // Update suggested item
            AdminController.updateProductPair();
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/products";
    }

    @PostMapping("/admin/products")
    public String postproduct() {
        return "redirect:/admin/categories";
    }

    @RequestMapping(value = "admin/products/sendData", method = RequestMethod.POST)
    public String addproducttodb(@RequestParam("name") String name, @RequestParam("categoryid") String catid, @RequestParam("price") int price, @RequestParam("weight") int weight, @RequestParam("quantity") int quantity, @RequestParam("description") String description, @RequestParam("productImage") String picture) {

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from categories where name = '" + catid + "';");
            if (rs.next()) {
                // Add product to products
                int categoryid = rs.getInt(1);

                PreparedStatement pst = con.prepareStatement("insert into products(name,image,categoryid,quantity,price,weight,description) values(?,?,?,?,?,?,?);");
                pst.setString(1, name);
                pst.setString(2, picture);
                pst.setInt(3, categoryid);
                pst.setInt(4, quantity);
                pst.setInt(5, price);
                pst.setInt(6, weight);
                pst.setString(7, description);
                int i = pst.executeUpdate();


                // Get id of newly added product
                PreparedStatement getItemIDPst = con.prepareStatement("SELECT id FROM products WHERE name = /'?';");
                getItemIDPst.setString(1, name);
                ResultSet getItemIDRst = getItemIDPst.executeQuery();
                int itemID = getItemIDRst.getInt("id");

                // Add newly added product as tuple in ProductMatrix
                PreparedStatement addToPMRowPst = con.prepareStatement("INSERT INTO ProductMatrix (product) VALUES (?);");
                addToPMRowPst.setInt(1, itemID);
                addToPMRowPst.executeQuery();

                // Add newly added product as attribute in ProductMatrix
                PreparedStatement addToPMColumnPst = con.prepareStatement("ALTER TABLE ProductMatrix ADD '?' int default 0;");
                String productName = "p" + itemID;
                addToPMColumnPst.setString(1, productName);
                addToPMColumnPst.executeQuery();
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/admin/products";
    }

    public static void updateProductPair() {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            // Parse through all tuples of ProductMatrix
            ResultSet allProductsRst = stmt.executeQuery("SELECT * FROM ProductMatrix;");
            while (allProductsRst.next()) {
                // Get the ID and attribute name of the current product
                int productID = allProductsRst.getInt("product");
                String productName = "p" + productID;

                // Find the other product that sold the most with the current product
                PreparedStatement eachProductPst = con.prepareStatement("SELECT product FROM ProductMatrix GROUP BY product HAVING MAX(?);");
                eachProductPst.setString(1, productName);
                ResultSet eachProductRst = eachProductPst.executeQuery();

                // Check if the query resulted in a tuple
                if (eachProductRst.next()) {
                    // Get the id of the suggested product
                    int pairID = eachProductRst.getInt("product");

                    // Update the productPair value of the current product
                    PreparedStatement newPairPst = con.prepareStatement("UPDATE products SET productPair = ? WHERE id = ?;");
                    newPairPst.setInt(1, pairID);
                    newPairPst.setInt(2, productID);
                    newPairPst.executeQuery();
                }
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
    }

    public static float getProductPrice(int productID, int quantity) {

        // Create a variable to hold the running total
        float productPrice = 30;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            // Obtain all details of this product
            ResultSet productDetails = stmt.executeQuery("SELECT * FROM products WHERE id = " + productID + ";");

            double discountFromPrice = 69;

            if (productDetails.next()) {
                productPrice = productDetails.getFloat("price");
                discountFromPrice = 1 - productDetails.getDouble("discount");
            }

            // Multiply by quantity
            productPrice *= quantity;

            // Multiply by 1 - discount (discount will be 0 in the case that there is no discount)
            productPrice *= discountFromPrice;
            return productPrice;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return productPrice;
    }

    public static float getCartPrice(String username) {

        // Create a variable to hold the running total
        float runningTotal = 0;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            // Select items from Cart
            ResultSet cartItemsRst = stmt.executeQuery("SELECT productID, quantity FROM Cart WHERE userId = (SELECT user_id FROM users WHERE username = '" + username + "');");

            // Iterate through the cart, calling getProductPrice for each item
            while (cartItemsRst.next()) {
                int productID = cartItemsRst.getInt("productID");
                int quantity = cartItemsRst.getInt("quantity");
                runningTotal += getProductPrice(productID, quantity);
            }

            if (runningTotal < 0) {
                return 0;
            }
            return runningTotal;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return runningTotal;
    }

    public static float getOrderTotal(String username) {
        float orderTotal = getCartPrice(username);
        orderTotal *= 1.15;
        int noOfCoupons = getCouponsApplied(username);
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();

            for (int i = 0; i < noOfCoupons; i++) {
                orderTotal -= 5;
                if (orderTotal <= 0) {
                    stmt.executeQuery("UPDATE Cart SET quantity = " + i + " WHERE productID = 0 AND userId = (SELECT user_id FROM users WHERE username = '" + username + "');");
                    orderTotal = 0;
                }
            }

        }
        catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return orderTotal;
    }

    @GetMapping("/admin/customers")
    public String getCustomerDetail() {
        return "displayCustomers";
    }

    @RequestMapping(value = "updateuser", method = RequestMethod.POST)
    public String updateUserProfile(@RequestParam("userid") int userid, @RequestParam("username") String username, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("address") String address) {
        System.out.println(userid);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            PreparedStatement pst = con.prepareStatement("update users set username= ?,email = ?,password= ?, address= ? where user_id = '" + userid + "'");
            pst.setString(1, username);
            pst.setString(2, email);
            pst.setString(3, password);
            pst.setString(4, address);
            int i = pst.executeUpdate();
            usernameforclass = username;
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "redirect:/index";
    }

    @GetMapping("profileDisplay")
    public String profileDisplay(Model model) {
        String displayusername, displaypassword, displayemail, displayaddress;
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
            Statement stmt = con.createStatement();
            ResultSet rst = stmt.executeQuery("select * from users where username = '" + usernameforclass + "';");

            if (rst.next()) {
                int userid = rst.getInt(1);
                displayusername = rst.getString(2);
                displayemail = rst.getString(6);
                displaypassword = rst.getString(3);
                displayaddress = rst.getString(5);
                model.addAttribute("userid", userid);
                model.addAttribute("username", displayusername);
                model.addAttribute("email", displayemail);
                model.addAttribute("password", displaypassword);
                model.addAttribute("address", displayaddress);
            }
        } catch (Exception e) {
            System.out.println("Exception:" + e);
        }
        return "updateProfile";
    }
}
