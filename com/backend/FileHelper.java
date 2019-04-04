package com.backend;

import com.shared.Item;
import com.shared.Supplier;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

public class FileHelper {
    private String path;

    FileHelper() {
        this.path = "";
    }

    public String getFilePath() {
        return path;
    }

    public void setFilePath(String path) {
        this.path = path;
    }

    /**
     *
     * TO DO: Refactor into array list implementation
     *  should not have been array type. Single method
     *  for any file with specified format.
     */
    public ArrayList<Supplier> supplierFromFile(String logger){
        final int paramNum = 4;
        String[] content = getFileContent("[;\n]");
        try{
            Supplier[] suppliers = new Supplier[content.length/paramNum];
            Supplier tmpSupplier = new Supplier();
            for(int i = 0; i <= content.length - 1; i++) {
                switch ( i%paramNum ){
                    case 0:
                        tmpSupplier.setSupId(
                                Integer.parseInt(
                                        content[i].replaceAll("\\s+", "")
                                )
                        );
                        break;
                    case 1:
                        tmpSupplier.setSupName(content[i]);
                        break;
                    case 2:
                        tmpSupplier.setSupAddress(content[i]);
                        break;
                    case 3:
                        tmpSupplier.setSupContactName(content[i]);
                        suppliers[((i + 1)/paramNum)-1] = new Supplier(
                                tmpSupplier.getSupId(),
                                tmpSupplier.getSupName(),
                                tmpSupplier.getSupAddress(),
                                tmpSupplier.getSupContactName()
                        );

                        if(logger.equals("verbose"))
                          System.out.println(suppliers[((i + 1)/paramNum)-1]);

                        tmpSupplier = new Supplier();
                        break;
                    default:
                        System.out.println("Supplier file violates line format of id, name, quantity, price, supplier_id");
                        return null;
                }
            }
            return new ArrayList<Supplier>(Arrays.asList(suppliers));
        }
        catch (ArithmeticException e){
            System.out.println("Text file violates format");
            return null;
        }
    }

    /**
     *
     * TO DO: Refactor into array list implementation
     *  should not have been array type. Single method
     *  for any file with specified format.
     */
    public ArrayList<Item> itemsFromFile(String logger){
        final int paramNum = 5;
        String[] content = getFileContent("[;\n]");
        try{
            Item[] items = new Item[content.length/paramNum];
            Item tmpItem = new Item();
            for(int i = 0; i <= content.length - 1; i++) {
                switch ( i%paramNum ){
                    case 0:
                        tmpItem.setItemId(
                                Integer.parseInt(
                                        content[i].replaceAll("\\s+", "")
                                )
                        );
                        break;
                    case 1:
                        tmpItem.setItemName(content[i]);
                        break;
                    case 2:
                        tmpItem.setItemQuantity(
                                Integer.parseInt(
                                        content[i].replaceAll("\\s+", "")
                                )
                        );
                        break;
                    case 3:
                        tmpItem.setItemPrice(
                                Double.parseDouble(
                                        content[i].replaceAll("\\s+", "")
                                )
                        );
                        break;
                    case 4:
                        tmpItem.setItemSupplierId(
                                Integer.parseInt(
                                        content[i].replaceAll("\\s+", "")
                                )
                        );
                        items[((i + 1)/paramNum)-1] = new Item(
                                tmpItem.getItemId(),
                                tmpItem.getItemName(),
                                tmpItem.getItemQuantity(),
                                tmpItem.getItemQuantity(),
                                tmpItem.getItemSupplierId()
                        );

                        if(logger.equals("verbose"))
                            System.out.println(items[((i + 1)/paramNum)-1]);

                        tmpItem = new Item();
                        break;
                    default:
                        System.out.println("Item file violates line format of id, name, quantity, price, supplier_id");
                        return null;
                }
            }
            return new ArrayList<Item>(Arrays.asList(items));
        }
        catch (ArithmeticException e){
            System.out.println("Text file violates format");
            return null;
        }
    }

    private String[] getFileContent(String splitRegex)
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

        return content.split(splitRegex);
    }
}
