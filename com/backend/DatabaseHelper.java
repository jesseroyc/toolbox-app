package com.backend;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;

public class DatabaseHelper {

    private Connection conn;
    private Statement stmt;
    private ResultSet rs;

    // Oracle express Docker image with port binds 1521:1521
    // The instance is a free-tier Ubuntu ec2 on AWS marketplace
    private class CredentialStore {
        final static String USER = "sct_user";
        final static String PASSWORD = "reinvent_Dms_Sct_17";
        final static String HOSTNAME = "ec2-18-216-46-195.us-east-2.compute.amazonaws.com";
        final static String PORT = "1521";
        final static String SID = "XE";
    }

    DatabaseHelper(){
        String logger = "silent";
        initializeConnection(logger);
        close(logger);
    }

    public String[] insertOneItem(String[] params){
        String logger = "silent";
        initializeConnection(logger);
        params = insertItemPreparedStatement(params);
        close(logger);
        return params;
    }

    public String[] selectOneItem(String[] params){
        String logger = "silent";
        initializeConnection(logger);
        params = selectPreparedStatement(params);
        close(logger);
        return params;
    }

    public String[] selectOneSupplier(String[] params){
        String logger = "silent";
        initializeConnection(logger);
        params = selectPreparedStatement(params);
        close(logger);
        return params;
    }

    public String[] insertOneSupplier(String[] params){
        String logger = "silent";
        initializeConnection(logger);
        params = insertSupplierPreparedStatement(params);
        close(logger);
        return params;
    }

    public ArrayList<String[]> insertManyItem(ArrayList<String[]> paramsArray){

        String logger = "silent";
        Iterator<String[]> paramsIterator = paramsArray.iterator();

        initializeConnection(logger);

        ArrayList<String[]> tmpParams = new ArrayList<>();
        while (paramsIterator.hasNext()) {
            String[] params = paramsIterator.next();
            tmpParams.add(insertItemPreparedStatement(params));
        }

        close(logger);

        return tmpParams;
    }

    public ArrayList<String[]> insertManySupplier(ArrayList<String[]> paramsArray){

        String logger = "silent";
        Iterator<String[]> paramsIterator = paramsArray.iterator();

        initializeConnection(logger);

        ArrayList<String[]> tmpParams = new ArrayList<>();
        while (paramsIterator.hasNext()) {
            String[] params = paramsIterator.next();
            tmpParams.add(insertSupplierPreparedStatement(params));
        }

        close(logger);

        return tmpParams;
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

    // TODO
    private String[] insertItemPreparedStatement(String[] params) {
        try {
            String query = "INSERT INTO items (ID,name,quantity,price,supplier_id) values (?,?,?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, Integer.parseInt(params[2]));
            pStat.setString(2, params[3]);
            pStat.setInt(3, Integer.parseInt(params[4]));
            pStat.setDouble(4, Double.parseDouble(params[5]));
            pStat.setDouble(5, Integer.parseInt(params[6]));
            pStat.executeUpdate();
            pStat.close();
            params[1] = "success";
            System.out.println("Database: Item Insert.");
            return params;
        } catch (SQLException e) {

            if(e.getErrorCode() == 1)
                System.out.println("Database: Item insert failed, item ID and NAME must be unique.");
            else
                e.printStackTrace();
            params[1] = "failed";
            return params;
        }
    }

    private String[] insertSupplierPreparedStatement(String[] params) {
        try {
            String query = "INSERT INTO suppliers (ID,name,address,contact) values (?,?,?,?)";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setInt(1, Integer.parseInt(params[2]));
            pStat.setString(2, params[3]);
            pStat.setString(3, params[4]);
            pStat.setString(4, params[5]);
            pStat.executeUpdate();
            pStat.close();
            params[2] = "success";
            System.out.println("Database: Supplier Insert.");
            return params;
        } catch (SQLException e) {
            if(e.getErrorCode() == 1)
                System.out.println("Database: Supplier insert failed, supplier ID, NAME, and ADDRESS must be unique.");
            else
                e.printStackTrace();
            params[2] = "failed";
            return params;
        }
    }

    private String[] selectPreparedStatement(String[] params) {
        String resultParams = new String();
        String type = params[1];
        String name = params[4];
        try {
            String query = "SELECT * FROM items where name= ?";
            PreparedStatement pStat = conn.prepareStatement(query);
            pStat.setString(1, name);
            rs = pStat.executeQuery();
            System.out.println("Database: Item Query.");
            while (rs.next()) {
                if(type.equals("Items")) {
                    resultParams = type + ";" + "success" + ";" +
                            rs.getInt("ID") + ";" +
                            rs.getString("name") + ";" +
                            rs.getInt("quantity") + ";" +
                            rs.getDouble("price") + ";" +
                            rs.getInt("supplier_id");
                }
                if(type.equals("Suppliers"))
                resultParams =
                        type + ";" + "success" + ";" +
                        rs.getString("ID") + ";" +
                        rs.getString("name") + ";" +
                        rs.getString("address") + ";" +
                        rs.getString("contact");
            }
            pStat.close();
            System.out.println(resultParams);
            return resultParams.split("[;]");
        } catch (SQLException e) {
            e.printStackTrace();
            params[2] = "failed";
            return params;
        }
    }
}
