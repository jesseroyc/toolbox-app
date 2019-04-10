package com.backend;

import com.shared.FileContent;
import com.shared.Item;
import com.shared.Supplier;

import java.net.InetAddress;
import java.util.ArrayList;

public class Worker {

    private boolean updateToggle;
    private InetAddress ip;
    private ArrayList<Item> items;
    private ArrayList<Supplier> suppliers;
    private DatabaseHelper databaseHelper;

    public Worker(InetAddress ip){
        this.ip = ip;
        this.updateToggle = false;
    }

    public InetAddress getIp(){return this.ip;}

    public void handleClientObject(Object clientObject){

        this.items = new ArrayList<>();
        this.suppliers = new ArrayList<>();

        ArrayList tmpArrayList = (ArrayList) clientObject;

        if(!tmpArrayList.isEmpty())
            tmpArrayList.forEach((temp) -> {
                switch(temp.toString().split("[;]")[1]){
                    case "Items":
                        Item item = (Item)temp;
                        this.items.add(itemOperation(item));
                        break;
                    case "Suppliers":
                        Supplier supplier = (Supplier) temp;
                        this.suppliers.add(supplierOperation(supplier));
                        break;
                    case "FileContents":
                        FileContent fileContent = (FileContent) temp;
                        if(fileContent.getContentType().equals("Items"))
                            fileContent.getContent().forEach((fileItem)->{
                                this.items.add(itemOperation((Item)fileItem));
                            });
                        else if (fileContent.getContentType().equals("Suppliers")) {
                            fileContent.getContent().forEach((fileSupplier)->{
                                this.suppliers.add(supplierOperation((Supplier)fileSupplier));
                            });
                        }
                        break;
                    default:
                        System.out.println(temp);
                }
            });
        else {
            // query all
        }
        this.updateToggle = true;
    }

    private Item itemOperation(Item item){
        String[] params = item.toString().split("[;]");
        switch(item.getOp()){
            case "Insert":
                databaseHelper = new DatabaseHelper();
                return new Item(item.getIp(),databaseHelper.insertOneItem(params));
            case "Select":
                databaseHelper = new DatabaseHelper();
                return new Item(item.getIp(),databaseHelper.selectOneItem(params));
            case "Update":
//                databaseHelper = new DatabaseHelper();
//                return new Item(item.getIp(),databaseHelper.updateOneItem(params));
            default:
                params[1] = "failed";
                return new Item(item.getIp(),params);
        }
    }

    private Supplier supplierOperation(Supplier supplier){
        String[] params = supplier.toString().split("[;]");
        switch(params[1]){
            case "Insert":
                databaseHelper = new DatabaseHelper();
                return new Supplier(supplier.getIp(),databaseHelper.selectOneSupplier(params));
            case "Select":
                databaseHelper = new DatabaseHelper();
                return new Supplier(supplier.getIp(),databaseHelper.insertOneSupplier(params));
            case "Update":
//                databaseHelper = new DatabaseHelper();
//                return new Supplier(supplier.getIp(),databaseHelper.insertOneSupplier(params));
            default:
                params[1] = "failed";
                return new Supplier(supplier.getIp(),params);
        }
    }

    ArrayList<Object> getInputObj() {
        ArrayList<Object> temp = new ArrayList<>();
        this.items.forEach((item) -> {
            temp.add((Object)item);
        });
        this.suppliers.forEach((supplier) -> {
            temp.add((Object)supplier);
        });
        return temp;
    }

    public void setUpdateToggle(boolean toggle) {
        this.updateToggle = toggle;
    }
    public boolean getUpdateToggle() {
        return this.updateToggle;
    }
}
