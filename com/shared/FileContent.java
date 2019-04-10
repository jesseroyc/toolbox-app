package com.shared;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileContent extends Model implements Serializable {

    String path;
    String contentType;
    ArrayList content;

    public FileContent(InetAddress ip,String type, String op, String path){
        super(ip,type,op,0);
        this.path = path;

        if(type == "Item")
            itemsFromFile(op);
        if(type == "Supplier")
            suppliersFromFile(op);
        this.contentType = type;
        this.type = "FileContent";
    }

    private void suppliersFromFile(String op){

        final int paramNum = 7;
        String[] content = getFileContent();
        String[] params = new String[paramNum];

        params[0] = "Supplier";
        params[1] = op;

        try{
            ArrayList<Supplier> suppliers = new ArrayList<>();

            for(int i = 0; i <= content.length - 1; i++) {
                switch ( i%(paramNum-3) ){
                    case 0:
                        params[2] = content[i].replaceAll("\\s+", "");
                        break;
                    case 1:
                        params[3] = content[i].replaceAll("\\s+", "");
                        break;
                    case 2:
                        params[4] = content[i].replaceAll("\\s+", "");
                        break;
                    case 3:
                        params[5] = content[i].replaceAll("\\s+", "");
                        System.out.println(params[0] + " " + params[1] + " " + params[2] + " " + params[3] + " " + params[4] + " " + params[5]);
                        suppliers.add(new Supplier(getIp(),params));

                        params = new String[paramNum];
                        params[0] = "Supplier";
                        params[1] = op;

                        break;
                    default:
                        System.out.println("Supplier file violates line format of id, name, address, contact");
                        this.content = null;
                }
            }
            this.content = suppliers;
        }
        catch (ArithmeticException e){
            System.out.println("Text file violates format");
            this.content = null;
        }
    }

    private void itemsFromFile(String op){

        final int paramNum = 7;
        String[] content = getFileContent();
        String[] params = new String[paramNum];

        params[0] = "Item";
        params[1] = op;

        try{
            ArrayList<Item> items = new ArrayList<>();

            for(int i = 0; i <= content.length - 1; i++) {
                switch ( i%(paramNum-2) ){
                    case 0:
                        params[2] = content[i].replaceAll("\\s+", "");
                        break;
                    case 1:
                        params[3] = content[i].replaceAll("\\s+", "");
                        break;
                    case 2:
                        params[4] = content[i].replaceAll("\\s+", "");
                        break;
                    case 3:
                        params[5] = content[i].replaceAll("\\s+", "");
                        break;
                    case 4:
                        params[6] = content[i].replaceAll("\\s+", "");
                        System.out.println(params[0] + " " + params[1] + " " + params[2] + " " + params[3] + " " + params[4] + " " + params[5] + " " + params[6]);
                        items.add(new Item(getIp(),params));

                        params = new String[paramNum];
                        params[0] = "Item";
                        params[1] = op;

                        break;
                    default:
                        System.out.println("Item file violates line format of id, name, quantity, price, supplier_id");
                        this.content = null;
                }
            }
            this.content = items;
        }
        catch (ArithmeticException e){
            System.out.println("Text file violates format");
            this.content = null;
        }
    }

    private String[] getFileContent()
    {
        String content = "";

        try
        {
            content = new String ( Files.readAllBytes( Paths.get(this.path) ) );
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return content.split("[;\n]");
    }
    public ArrayList getContent(){
        return this.content;
    }
    public String getContentType(){
        return this.contentType;
    }
}
