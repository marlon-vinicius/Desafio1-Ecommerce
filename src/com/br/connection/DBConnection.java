package com.br.connection;

import com.br.shoppingcart.Product;
import com.br.shoppingcart.ShoppingCart;

import java.sql.*;
import java.util.List;

public class DBConnection {
     private static String url = "jdbc:mysql://localhost:3306/products_database";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Establish Connection Object
                connection = DriverManager.getConnection(url, "root", "admin123");

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void dropTable() {
        Connection connection = getConnection();
        try {
            String sql = "DROP TABLE IF EXISTS products";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createTable() {
        Connection connection = getConnection();
        try {
            String sql = "CREATE TABLE IF NOT EXISTS products (" +
                    "id INT NOT NULL PRIMARY KEY AUTO_INCREMENT," +
                    "name VARCHAR(150) NOT NULL," +
                    "price DECIMAL(10,2) NOT NULL," +
                    "quantity INT" +
                    ")";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void generateProducts() {
        Connection connection = getConnection();
        try {
            String sql = "INSERT INTO products (name, price, quantity) VALUES (?, ?, ?)";

            String[][] products = {
                    {"Product 1", "10.00", "10"},
                    {"Product 2", "20.00", "7"},
                    {"Product 3", "30.00", "15"}
            };

            PreparedStatement statement = connection.prepareStatement(sql);

            for (String[] product : products) {
                statement.setString(1, product[0]);
                statement.setString(2, product[1]);
                statement.setString(3, product[2]);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResultSet getProduct(Integer id) throws SQLException {
        Connection connection = getConnection();

        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        return resultSet;
    }

    public static void getAllProducts() {
        Connection connection = getConnection();
        try {
            String sql = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n===================== LIST OF PRODUCTS =====================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                System.out.println("ID: " + id + ", Name: " + name + ", Price: " + price + ", Quantity in stock: "+ quantity);
            }
            System.out.println("============================================================\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeQuantity(Integer id, Integer quantity) throws SQLException {
        Connection connection = getConnection();

        String sql = "SELECT * FROM products";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            int oldQuantity = resultSet.getInt("quantity");
            int newQuantity = oldQuantity - quantity ;

            System.out.println(oldQuantity);
            System.out.println(newQuantity);

            String updateQuantity = "UPDATE products SET quantity = ? WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(updateQuantity);
            statement2.setInt(1, newQuantity);
            statement2.setInt(2, id);
            statement2.executeUpdate();
        }
    }
}

