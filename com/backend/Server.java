package com.backend;

import com.shared.Item;
import com.shared.Supplier;

import java.util.ArrayList;

public class Server {
    private FileHelper fileManager = new FileHelper();
    private DatabaseHelper databaseManager = new DatabaseHelper();

    private class Worker {

    }

    public static void main(String[] args) {
        System.out.println("Server: Initializing database connection");
        Server server = new Server();

        server.fileManager.setFilePath("C:\\Users\\power\\IdeaProjects\\409FinalProject-JesseRoycote-10175968\\src\\items.txt");
        ArrayList<Item> items = server.fileManager.itemsFromFile(/*"silent" or "verbose"*/"silent");
        server.fileManager.setFilePath("C:\\Users\\power\\IdeaProjects\\409FinalProject-JesseRoycote-10175968\\src\\suppliers.txt");
        ArrayList<Supplier> suppliers = server.fileManager.supplierFromFile(/*"silent" or "verbose"*/"silent");

        Item testItem = items.get(0);
        server.databaseManager.insertOneItem(testItem);
        server.databaseManager.selectOneItem("Knock Bits");

    }
}
