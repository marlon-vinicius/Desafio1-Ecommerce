package com.br.connection;

import java.sql.*;

public class DBConnection {
     private static String url = "jdbc:mysql://localhost:3306/";
     private static String user = "YOUR_USERNAME";
     private static String password = "YOUR_PASSWORD";

    private static Connection connection;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(url, user, password);

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

    public static void createDatabase() {
        Connection connection = getConnection();
        try {
            String sql = "CREATE DATABASE IF NOT EXISTS products_DB_Desafio";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void useDatabase() {
        Connection connection = getConnection();
        try {
            String sql = "USE products_DB_Desafio";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void dropDatabase() {
        Connection connection = getConnection();
        try {
            String sql = "DROP DATABASE products_DB_Desafio";
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
                    {"Product 1", "10.00", "30"},
                    {"Product 2", "20.00", "20"},
                    {"Product 3", "30.00", "10"}
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

            System.out.println("\n======================== LIST OF PRODUCTS ========================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                if (quantity > 0) {
                    System.out.printf("%s%s%s%s%s%.2f%s%s%n","ID: ", id,"   Name: ",name,"   Price: R$",price, "   Quantity in stock: ",quantity);
                }
            }
            System.out.println("==================================================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getStock() {
        Connection connection = getConnection();
        try {
            String sql = "SELECT * FROM products";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            System.out.println("\n============================== STOCK ==============================");
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("price");
                int quantity = resultSet.getInt("quantity");

                System.out.printf("%s%s%s%s%s%.2f%s%s%n","ID: ", id,"   Name: ",name,"   Price: R$",price, "   Quantity in stock: ",quantity);
            }
            System.out.println("===================================================================");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeQuantity(Integer id, Integer quantity) throws SQLException {
        Connection connection = getConnection();

        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            int oldQuantity = resultSet.getInt("quantity");
            int newQuantity = oldQuantity - quantity ;

            String updateQuantity = "UPDATE products SET quantity = ? WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(updateQuantity);
            statement2.setInt(1, newQuantity);
            statement2.setInt(2, id);
            statement2.executeUpdate();
        }
    }

    public static void addQuantity(Integer id, Integer quantity) throws SQLException {
        Connection connection = getConnection();

        String sql = "SELECT * FROM products WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet != null && resultSet.next()) {
            int oldQuantity = resultSet.getInt("quantity");
            int newQuantity = oldQuantity + quantity ;

            String updateQuantity = "UPDATE products SET quantity = ? WHERE id = ?";
            PreparedStatement statement2 = connection.prepareStatement(updateQuantity);
            statement2.setInt(1, newQuantity);
            statement2.setInt(2, id);
            statement2.executeUpdate();
        }
    }
}