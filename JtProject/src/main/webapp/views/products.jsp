<%@page import="java.sql.*" %>
<%@page import="java.util.*" %>
<%@page import="java.text.*" %>
<%@page import="java.io.FileOutputStream" %>
<%@page import=" java.io.ObjectOutputStream" %>
<%@ page import="javax.swing.plaf.nimbus.State" %>
<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet"
          href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">

    <title>Document</title>
    <style>
        .btn-action {
            background-color: #2980b9;
            color: #fff;
            border: none;
            padding: 5px 10px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 100px;
        }

        .btn-action:hover {
            color: #fff;
        }


    </style>
</head>
<body class="bg-light">
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="#"> <img
                th:src="@{/images/logo.png}" src="../static/images/logo.png"
                width="auto" height="40" class="d-inline-block align-top" alt=""/>
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse"
                data-target="#navbarSupportedContent"
                aria-controls="navbarSupportedContent" aria-expanded="false"
                aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto"></ul>
            <ul class="navbar-nav">
                <li class="nav-item active"><a class="nav-link" href="/adminhome">Home</a></li>
                <li class="nav-item active"><a class="nav-link" href="/admin">Logout</a>
                </li>

            </ul>

        </div>
    </div>
</nav>
<br>
<div class="d-flex justify-content-end mb-3">
    <a href="/admin/products/add" id="addProduct" class="btn btn-action">Add product</a>
</div>
<div class="container-fluid">
    <table class="table">

        <tr>
            <th scope="col">Serial No.</th>
            <th scope="col">Product Name</th>
            <th scope="col">Category</th>
            <th scope="col">Preview</th>
            <th scope="col">Quantity</th>
            <th scope="col">Price</th>
            <th scope="col">Discount</th>
            <th scope="col">Weight</th>
            <th scope="col">Product pair</th>
            <th scope="col">Suggested Items</th>
            <th scope="col">Delete</th>
            <th scope="col">Update</th>
        </tr>
        <tbody>
        <tr>

            <%
                try {
                    String url = "jdbc:mysql://localhost:3306/springproject";
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url, "root", "12345678");
                    Statement stmt = con.createStatement();
                    Statement stmt2 = con.createStatement();
                    Statement stmt3 = con.createStatement();
                    Statement stmt4 = con.createStatement();
                    ResultSet rs = stmt.executeQuery("select * from products");
            %>
            <%
                while (rs.next()) {
            %>
            <td>
                <%= rs.getInt(1) %>
            </td>
            <td>
                <%= rs.getString(2) %>
            </td>
            <td>
                <%
                    int categoryid = rs.getInt(4);
                    ResultSet rs2 = stmt2.executeQuery("select * from categories where categoryid = " + categoryid + ";");
                    if (rs2.next()) {
                        out.print(rs2.getString(2));
                    }
                %>

            </td>

            <td><img src="<%= rs.getString(3) %>" height="100px" width="100px"></td>
            <td>
                <%= rs.getInt(5) %>
            </td>
            <td>$<%= rs.getInt(6) %>
            </td>
            <td>
                <%= rs.getDouble(9) %>
            </td>
            <td>
                <%= rs.getInt(7) %>
            </td>
            <td>
                <%
                    int productPairId = rs.getInt(10);
                    ResultSet rs3 = stmt3.executeQuery("select name from products where id = " + productPairId + ";");
                    if (rs3.next()) {
                        out.print(rs3.getString(1));
                    }
                %>
            </td>

            <td>

                    <%
							int currentItemId = rs.getInt(1);
							int currentSuggestedItem = rs.getInt(11);
							ResultSet rs4 = stmt4.executeQuery("select * from products where id <> 0 AND id <> " + currentItemId + ";");
							ArrayList<Integer> productIdArr = new ArrayList<Integer>();
							ArrayList<String> productNameArr = new ArrayList<String>();
							while(rs4.next()){
								productIdArr.add(rs4.getInt(1));
								productNameArr.add(rs4.getString(2));
							}
						%>
                <form action="/suggestItem" method="get">
                    <label for="suggestedItem"></label>
                    <select id="suggestedItem" name="suggestedID">
                        <%for (int i = 0; i < productIdArr.size(); i++) { %>
                        <% if (productIdArr.get(i) == currentSuggestedItem) { %>
                        <option value="<%=productIdArr.get(i)%>" selected><%=productNameArr.get(i)%>
                        </option>
                        <%} else {%>
                        <option value="<%=productIdArr.get(i)%>"><%=productNameArr.get(i)%>
                        </option>
                        <%
                                }
                            }
                        %>
                    </select>
                    <input type="hidden" name="productID" value="<%=rs.getInt(1)%>">
                    <input type="submit">
                </form>

            <td>
                <form action="products/delete" method="get">
                    <input type="hidden" name="id" value="<%=rs.getInt(1)%>">
                    <input id="delete" type="submit" value="Delete" class="btn btn-danger">
                </form>
            </td>
            <td>
                <form action="products/update" method="get">
                    <input type="hidden" name="pid" value="<%=rs.getInt(1)%>">
                    <input type="submit" value="Update" class="btn btn-warning">
                </form>

            </td>

        </tr>
        <%
            }
        %>

        </tbody>
    </table>
    <%
        } catch (Exception ex) {
            out.println("Exception Occurred:: " + ex.getMessage());
        }
    %>
</div>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
        integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>
</body>
</html>