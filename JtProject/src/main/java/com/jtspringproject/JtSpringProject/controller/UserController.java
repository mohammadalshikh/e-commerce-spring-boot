package com.jtspringproject.JtSpringProject.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController{
	static String usernameforclass = "";

	public static void setUsername(String usernameforclass) {
		UserController.usernameforclass = usernameforclass;
	}


	@GetMapping("/customcart")
	public String customCart(){
		return "customcart";
	}

	@GetMapping("/register")
	public String registerUser()
	{
		return "register";
	}
	@GetMapping("/contact")
	public String contact()
	{
		return "contact";
	}
	@GetMapping("/buy")
	public String buy()	{
		// Set up prerequisite variables
		int transactionID = 0;
		Set<Integer> products = new HashSet<Integer>();

		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","12345678");
			Statement stmt = con.createStatement();

			// Select items from Cart
			ResultSet cartItemsRst = stmt.executeQuery("SELECT userId, productID, quantity FROM Cart WHERE userId = (SELECT user_id FROM users WHERE username = '" + usernameforclass + "');");

			// Get last transactionID used
			PreparedStatement tIDPst = con.prepareStatement("SELECT MAX(transactionID) AS max FROM TransactionHistory WHERE userID = (SELECT user_id FROM users WHERE username = '" + usernameforclass + "'");
			tIDPst.setString(1, usernameforclass);
			ResultSet tIDRst = tIDPst.executeQuery();
			// Verify that there was a previous transactionID
			if(tIDRst.next()) {
				// Update transactionID
				transactionID = tIDRst.getInt("max");
			}
			// Update transactionID for next transaction
			transactionID++;

			// Parse through cartItemsRst
			while(cartItemsRst.next()) {
				// Insert Cart tuples into TransactionHistory
				PreparedStatement insertHistoryPst = con.prepareStatement("INSERT INTO TransactionHistory (userID, productID, quantity, transactionID) VALUES (?, ?, ?, '" + transactionID + "')");
				insertHistoryPst.setInt(1,cartItemsRst.getInt("userId"));
				insertHistoryPst.setInt(2,cartItemsRst.getInt("productID"));
				insertHistoryPst.setInt(3,cartItemsRst.getInt("quantity"));
				insertHistoryPst.executeQuery();
				// Insert productID values into products set
				products.add(cartItemsRst.getInt("productID"));
			}

			// Verify that there are at least two items in the set
			if(products.size() >= 2) {
				// Parse through the set
				for(int p : products) {
					// Parse through the set
					for(int q : products) {
						// Verify that the two items pointed at are not the same
						if(p != q) {
							// Update ProductMatrix
							PreparedStatement insertMatrixPst = con.prepareStatement("UPDATE ProductMatrix SET ? = ? WHERE productID = ?");
							ResultSet oldCountRst = stmt.executeQuery("SELECT " + q + "FROM TransactionHistory WHERE productID = " + p + ";");
							int oldCount = oldCountRst.getInt(q);
							insertMatrixPst.setInt(1, q);
							insertMatrixPst.setInt(2, (oldCount + 1));
							insertMatrixPst.setInt(3, p);
							insertMatrixPst.executeQuery();
						}
					}
				}
			}

			// Update product stock
			AdminController.updateProductStock(usernameforclass);
			// Update user coupons
			AdminController.updateUserCoupons(usernameforclass);
			// Delete items from Cart
			this.clearcart();
		}
		catch(Exception e) {
			System.out.println("Exception:"+e);
		}
		return "buy";
	}
	
	@GetMapping("/user/products")
	public String getproduct(Model model) {
		return "uproduct";
	}

	@ResponseBody
	@GetMapping("/checkUsernameAvailability")
	public Map<String, Boolean> checkUsernameAvailability(@RequestParam("username") String username) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?");
			pst.setString(1, username);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				response.put("exists", count > 0);
			} else {
				response.put("exists", false);
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			response.put("exists", false);
		}
		return response;
	}

	@ResponseBody
	@GetMapping("/checkEmailAvailability")
	public Map<String, Boolean> checkEmailAvailability(@RequestParam("email") String email) {
		Map<String, Boolean> response = new HashMap<>();
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
			PreparedStatement pst = con.prepareStatement("SELECT COUNT(*) FROM users WHERE email = ?");
			pst.setString(1, email);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				int count = rs.getInt(1);
				response.put("exists", count > 0);
			} else {
				response.put("exists", false);
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
			response.put("exists", false);
		}
		return response;
	}

	@RequestMapping(value = "newuserregister", method = RequestMethod.POST)
	public String newUseRegister(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("email") String email, @RequestParam("address") String address)
	{
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","12345678");
			PreparedStatement pst = con.prepareStatement("insert into users(username,password,email,address) values(?,?,?,?);");
			pst.setString(1,username);
			pst.setString(2, password);
			pst.setString(3, email);
			pst.setString(4, address);

			//pst.setString(4, address);
			int i = pst.executeUpdate();
			System.out.println("data base updated"+i);
			
		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/";
	}
	@GetMapping("clearcart")
	public String clearcart() {
		try
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject","root","12345678");
			Statement stmt = con.createStatement();
			ResultSet rst = stmt.executeQuery("SELECT * from Cart where userID = (select user_id from users where username = '" + usernameforclass + "');");

			if (rst.next()) {
				int userID = rst.getInt("userID");
				stmt.executeUpdate("DELETE FROM Cart WHERE userID = " + userID + ";");
			}

		}
		catch(Exception e)
		{
			System.out.println("Exception:"+e);
		}
		return "redirect:/cart";
	}

	@GetMapping("movecustomtocart")
	public String moveCustomToCart(Model model) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
			Statement stmt = con.createStatement();

			Map<String, Integer> cartIts = new HashMap<>();
			ResultSet cartRs = stmt.executeQuery("SELECT productID, quantity FROM Cart WHERE userID = (SELECT user_id FROM users WHERE username = '" + usernameforclass + "');");
			while (cartRs.next()) {
				String productID = cartRs.getString("productID");
				int quantity = cartRs.getInt("quantity");
				cartIts.put(productID, quantity);
			}


			ResultSet customCartRs = stmt.executeQuery("SELECT productID, quantity FROM CustomCart WHERE userID = (SELECT user_id FROM users WHERE username = '" + usernameforclass + "');");
			while (customCartRs.next()) {
				String productID = customCartRs.getString("productID");
				int quantity = customCartRs.getInt("quantity");
				if (cartIts.containsKey(productID)) {
					int currentQuantity = cartIts.get(productID);
					int newQuantity = currentQuantity + quantity;
					Statement stmt2 = con.createStatement();
					stmt2.executeUpdate("update Cart set quantity = " + newQuantity + " where userID = (select user_id from users where username = '" + usernameforclass + "') and productID = '" + productID + "';");
				} else {
					Statement stmt3 = con.createStatement();
					stmt3.executeUpdate("insert into Cart (userID, productID, quantity) values ((select user_id FROM users where username = '" + usernameforclass + "'), '" + productID + "', " + quantity + ");");
				}
			}

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		return "redirect:/cart";
	}

	@GetMapping("deleteitem")
	public String deleteItemFromCart(@RequestParam("productID") int productID) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
			Statement stmt = con.createStatement();

			ResultSet userResultSet = stmt.executeQuery("select user_id from users where username = '" + usernameforclass + "';");
			if (userResultSet.next()) {
				int userID = userResultSet.getInt("user_id");
				stmt.executeUpdate("delete from Cart where userID = " + userID + " and productID = " + productID + ";");
			}

		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		return "redirect:/cart";
	}

	@GetMapping("addtocart")
	public String addItemToCart(@RequestParam("productID") int productID, @RequestParam("quantity") int quantity) {
		try {
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/springproject", "root", "12345678");
			Statement stmt = con.createStatement();
			ResultSet userResultSet = stmt.executeQuery("select user_id from users where username = '" + usernameforclass + "';");
			if (userResultSet.next()) {
				int userID = userResultSet.getInt("user_id");
				ResultSet cartItemResultSet = stmt.executeQuery("select * from Cart where userID = " + userID + " and productID = " + productID + ";");
				if (cartItemResultSet.next()) {
					int existingQuantity = cartItemResultSet.getInt("quantity");
					quantity += existingQuantity;
					stmt.executeUpdate("update Cart set quantity = " + quantity + " where userID = " + userID + " and productID = " + productID + ";");
				} else {
					stmt.executeUpdate("insert into Cart (userID, productID, quantity) values (" + userID + ", " + productID + ", " + quantity + ");");
				}
			}
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		return "cart";
	}

}