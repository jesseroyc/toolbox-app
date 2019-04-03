package com.backend;

import com.shared.*;

import java.sql.*;

public class DatabaseHelper {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    private class CredentialStore {
        final static String USER = "root";
        final static String PASSWORD = "@dmin";
        final static String HOSTNAME = "localhost";
        final static String PORT = "3306";
        final static String SID = "";
    }

    DatabaseHelper(){
        initializeConnection();
        close();
    }

    public void selectItem(String name){
        initializeConnection();
        selectItemPreparedStatement(name);
        close();
    }

    public void selectSupplier(String name){
        initializeConnection();
        selectSupplierPreparedStatement(name);
        close();
    }

    public void insertItem(Item item){
        initializeConnection();
        insertItemPreparedStatement(
                item.getItemId(),
                item.getItemName(),
                item.getItemQuantity(),
                item.getItemPrice(),
                item.getTheSupplier().getSupId()
        );
        close();
    }

    public void insertSupplier(Supplier supplier){
        initializeConnection();
        insertSupplierPreparedStatement(
                supplier.getSupId(),
                supplier.getSupName(),
                supplier.getSupAddress(),
                supplier.getSupContactName()
        );
        close();
    }

    private void initializeConnection() {
        try {

            Driver driver = new oracle.jdbc.OracleDriver();
            DriverManager.registerDriver(driver);

            String url = "jdbc:oracle:thin:@"
                    + CredentialStore.HOSTNAME + ":"
                    + CredentialStore.PORT + ":"
                    + CredentialStore.SID;

            // jdbc:oracle:thin:@HOSTNAME:PORT:SID
            conn = DriverManager.getConnection(
                    url,
                    CredentialStore.USER,
                    CredentialStore.PASSWORD
            );
            System.out.println("Connection made to database");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close() {
        try {
            stmt.close();
            rs.close();
            conn.close();
            System.out.println("All connection was closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertItemPreparedStatement(int id,String name,int quantity,Double price,int supplier_id) {
        try {
            String query = "INSERT INTO items (ID,name,quantity,price,supplier_id) values (?,?,?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, id);
            pStat.setString(2, name);
            pStat.setInt(3, quantity);
            pStat.setDouble(4, price);
            pStat.setDouble(5, supplier_id);
            int rowCount = pStat.executeUpdate();
            System.out.println("row Count = " + rowCount);
            pStat.close();
            System.out.println("Successful item insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertSupplierPreparedStatement(int id,String name,String address,String contact) {
        try {
            String query = "INSERT INTO items (ID,name,address,contact) values (?,?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, id);
            pStat.setString(2, name);
            pStat.setString(3, address);
            pStat.setString(4, contact);
            int rowCount = pStat.executeUpdate();
            System.out.println("row Count = " + rowCount);
            pStat.close();
            System.out.println("Successful supplier insert");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectItemPreparedStatement(String name) {
        try {
            String query = "SELECT * FROM items where name= ?";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setString(1, name);
            rs = pStat.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getString("ID") + " " +
                        rs.getString("name") + " " +
                        rs.getString("quantity") + " " +
                        rs.getString("price") + " " +
                        rs.getString("supplier_id")
                );

            }
            pStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectSupplierPreparedStatement(String name) {
        try {
            String query = "SELECT * FROM suppliers where name= ?";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setString(1, name);
            rs = pStat.executeQuery();
            while (rs.next()) {
                System.out.println(
                        rs.getString("ID") + " " +
                        rs.getString("name") + " " +
                        rs.getString("address") + " " +
                        rs.getString("contact")
                );

            }
            pStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
