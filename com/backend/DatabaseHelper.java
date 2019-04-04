package com.backend;

import com.shared.*;

import java.sql.*;

public class DatabaseHelper {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    private class CredentialStore {
        final static String USER = "SYSTEM";
        final static String PASSWORD = "@dmin";
        final static String HOSTNAME = "127.0.0.1";
        final static String PORT = "1521";
        final static String SID = "XE";
    }

    DatabaseHelper(){
        initializeConnection("silent");
        close("silent");
    }

    public void selectOneItem(String name){
        initializeConnection("silent");
        selectItemPreparedStatement(name);
        close("silent");
    }

    public void selectOneSupplier(String name){
        initializeConnection("silent");
        selectSupplierPreparedStatement(name);
        close("silent");
    }

    public void insertOneItem(Item item){
        initializeConnection("silent");
        insertItemPreparedStatement(
                item.getItemId(),
                item.getItemName(),
                item.getItemQuantity(),
                item.getItemPrice(),
                item.getItemSupplierId()
        );
        close("silent");
    }

    public void insertOneSupplier(Supplier supplier){
        initializeConnection("silent");
        insertSupplierPreparedStatement(
                supplier.getSupId(),
                supplier.getSupName(),
                supplier.getSupAddress(),
                supplier.getSupContactName()
        );
        close("silent");
    }

    public void selectManyItem(String name){
        initializeConnection("silent");
        selectItemPreparedStatement(name);
        close("silent");
    }

    public void selectManySupplier(String name){
        initializeConnection("silent");
        selectSupplierPreparedStatement(name);
        close("silent");
    }

    public void insertManyItem(Item item){
        initializeConnection("silent");
        insertItemPreparedStatement(
                item.getItemId(),
                item.getItemName(),
                item.getItemQuantity(),
                item.getItemPrice(),
                item.getItemSupplierId()
        );
        close("silent");
    }

    public void insertManySupplier(Supplier supplier){
        initializeConnection("silent");
        insertSupplierPreparedStatement(
                supplier.getSupId(),
                supplier.getSupName(),
                supplier.getSupAddress(),
                supplier.getSupContactName()
        );
        close("silent");
    }

    private void initializeConnection(String logger) {
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
            if(logger == "verbose")
              System.out.println("Database: Connection opened");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void close(String logger) {
        try {
            if(stmt != null)
              stmt.close();

            if(rs != null)
              rs.close();

            if(conn != null)
              conn.close();

            if(logger == "verbose")
              System.out.println("Database: Connection closed");
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
            System.out.println("Database: row Count = " + rowCount);
            pStat.close();
            System.out.println("Database: Successful item insert");
        } catch (SQLException e) {

            if(e.getErrorCode() == 1)
                System.out.println("Database: Item insert failed, item ID and NAME must be unique.");
            else
                e.printStackTrace();
        }
    }

    private void insertSupplierPreparedStatement(int id,String name,String address,String contact) {
        try {
            String query = "INSERT INTO suppliers (ID,name,address,contact) values (?,?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, id);
            pStat.setString(2, name);
            pStat.setString(3, address);
            pStat.setString(4, contact);
            int rowCount = pStat.executeUpdate();
            System.out.println("Database: Row Count = " + rowCount);
            pStat.close();
            System.out.println("Database: Successful supplier insert");
        } catch (SQLException e) {
            if(e.getErrorCode() == 1)
                System.out.println("Database: Supplier insert failed, supplier ID, NAME, and ADDRESS must be unique.");
            else
                e.printStackTrace();
        }
    }

    private void selectItemPreparedStatement(String name) {
        try {
            String query = "SELECT * FROM items where name= ?";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setString(1, name);
            rs = pStat.executeQuery();
            System.out.println("Database: Item query.");
            while (rs.next()) {
                System.out.println(
                        "  ID: " + rs.getString("ID") + " " +
                        "Name: " + rs.getString("name") + " " +
                        "Quantity: " + rs.getString("quantity") + " " +
                        "Price: " + rs.getString("price") + " " +
                        "Supplier ID: " + rs.getString("supplier_id")
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
            System.out.println("Database: Supplier query.");
            while (rs.next()) {
                System.out.println(
                        "  ID: " + rs.getString("ID") + " " +
                        "Name: " + rs.getString("name") + " " +
                        "Address: " + rs.getString("address") + " " +
                        "Contact: " + rs.getString("contact")
                );

            }
            pStat.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
