package com.backend;

public class Server {
    private FileHelper fileManager = new FileHelper();
    private DatabaseHelper databaseManager = new DatabaseHelper();

    private class Worker {

    }
    public static void main(String[] args) {
        Server server = new Server();

        // STEP ONE
        // Should setup empty DB first
        // Item ID NAME QUANTITY PRICE SUPPLIER
        // Supplier ID NAME ADDRESS CONTACT

        // STEP TWO
        // User inputs file path

        // STEP THREE
        // Add contents to db

        // Testing Purposes
        String [] content;
        server.fileManager.setFilePath("C:\\Users\\power\\IdeaProjects\\409FinalProject-JesseRoycote-10175968\\src\\items.txt");
        content = server.fileManager.getFileContent();
        // db helper handles insertion of content
        System.out.println("Item ID: " + content[0]);
        System.out.println("Item Name: " + content[1]);
        System.out.println("Item Quantity: " + content[2]);
        System.out.println("Item Price: " + content[3]);
        System.out.println("Item Supplier ID: " + content[4]);
        System.out.println(" ");
        server.fileManager.setFilePath("C:\\Users\\power\\IdeaProjects\\409FinalProject-JesseRoycote-10175968\\src\\suppliers.txt");
        content = server.fileManager.getFileContent();
        // db helper handles insertion of content
        System.out.println("Supplier ID: " + content[0]);
        System.out.println("Supplier Name: " + content[1]);
        System.out.println("Supplier Address: " + content[2]);
        System.out.println("Supplier Contact: " + content[3]);
    }
}
