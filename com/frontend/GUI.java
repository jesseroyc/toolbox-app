package com.frontend;

import com.shared.FileContent;
import com.shared.Item;
import com.shared.Supplier;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.util.ArrayList;

public class GUI {

    InetAddress ip;

    ArrayList<Item> items = new ArrayList<>();
    ArrayList<Supplier> suppliers = new ArrayList<>();

    boolean updateToggle;

    final String SEARCHPANEL = "Search";
    final String ORDERSPANEL = "Orders";
    final String LOADPANEL = "Load";
    final int extraWindowWidth = 20;

    JButton searchButton;
    JButton loadButton;

    JTextField searchBox;
    JTextField loadBox;

    ArrayList<JButton> orderButtons;
    ArrayList<JLabel> resultSet;
    ArrayList<JLabel> orderSet;
    JTextField resultValue;

    JLabel clientMessage;

    SearchListener searchListener;
    LoadListener loadListener;

    GUI(InetAddress ip){

        this.ip = ip;

        // check all ops on server if any failed return all by default with message "Not Found" or "Invalid"
        this.updateToggle = true;

        this.searchListener = new SearchListener(this);
        this.loadListener = new LoadListener(this);

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    InetAddress getIp(){
        return this.ip;
    }
    boolean getUpdateToggle(){
        return this.updateToggle;
    }
    void setUpdateToggle(boolean toggle){
        this.updateToggle = toggle;
    }

    ArrayList<Object> sendObjects() {
        ArrayList<Object> temp = new ArrayList<>();
        this.items.forEach((item) -> {
            temp.add((Object)item);
        });
        this.suppliers.forEach((supplier) -> {
            temp.add((Object)supplier);
        });
        updateToggle = false;
        return temp;
    }
    public void receiveObjects(Object objects){

        this.items = new ArrayList<>();
        this.suppliers = new ArrayList<>();

        ArrayList tmpArrayList = (ArrayList) objects;

        tmpArrayList.forEach((temp) -> {
            switch(temp.toString().split("[;]")[1]){
                case "Items":
                    Item item = (Item)temp;
                    this.items.add(item);
                    break;
                case "Suppliers":
                    Supplier supplier = (Supplier) temp;
                    this.suppliers.add(supplier);
                    break;
                case "FileContents":
                    FileContent fileContent = (FileContent) temp;
                    if(fileContent.getContentType().equals("Items"))
                        fileContent.getContent().forEach((fileItem)->{
                            this.items.add((Item)fileItem);
                        });
                    else if (fileContent.getContentType().equals("Suppliers")) {
                        fileContent.getContent().forEach((fileSupplier)->{
                            this.suppliers.add((Supplier)fileSupplier);
                        });
                    }
                    break;
                default:
                    System.out.println(temp);
            }
        });

        this.updateToggle = false;
        // update all coponents with values
    }

    String getSearchBoxText(){
        return this.searchBox.getText();
    }

    void addComponentToPane(Container pane) {
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel searchPanel = new JPanel(){
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
          };
          searchBox = new JTextField("",20);
          searchButton = new JButton("Search");
          searchButton.addActionListener(this.searchListener);
          JPanel searchResults = new JPanel(){
              public Dimension getPreferredSize() {
                  Dimension size = super.getPreferredSize();
                  size.width += extraWindowWidth;
                  return size;
              }
            };
            resultValue = new JTextField("Item and supplier values",20);
            JButton orderButton = new JButton("Order");

            searchResults.setLayout(new GridLayout(1,0));

            searchResults.add(orderButton);
            searchResults.add(resultValue);

          searchPanel.setLayout(new GridLayout(2,1));

          searchPanel.add(searchBox);
          searchPanel.add(searchButton);
          searchPanel.add(searchResults);

        JPanel orderPanel = new JPanel(){
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
          };
          JLabel orders = new JLabel("Order Values");

          orderPanel.setLayout(new GridLayout(2,1));

          orderPanel.add(orders);

        JPanel loadPanel = new JPanel(){
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                size.width += extraWindowWidth;
                return size;
            }
          };
          loadBox = new JTextField("C:\\Users\\power\\IdeaProjects\\409FinalProject-JesseRoycote-10175968\\src\\items.txt",20);
          loadButton = new JButton("Load");
          loadButton.addActionListener(this.loadListener);
          clientMessage = new JLabel("Failed");

          loadPanel.setLayout(new GridLayout(2,1));

          loadPanel.add(loadBox);
          loadPanel.add(loadButton);
          loadPanel.add(clientMessage);
        tabbedPane.addTab(SEARCHPANEL, searchPanel);
        tabbedPane.addTab(ORDERSPANEL, orderPanel);
        tabbedPane.addTab(LOADPANEL, loadPanel);

        pane.add(tabbedPane, BorderLayout.CENTER);

    }

    private void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("TabDemo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        addComponentToPane(frame.getContentPane());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
