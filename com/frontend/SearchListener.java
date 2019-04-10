package com.frontend;

import com.shared.Item;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SearchListener implements ActionListener {

    private GUI gui;

    SearchListener(GUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.searchButton) {
            // id param is request id
            String params = "Items;Select;0;" + gui.getSearchBoxText() + ";0;0;0";
            Item item = new Item(gui.getIp(),params.split("[;]"));
            gui.items = new ArrayList<>();
            gui.suppliers = new ArrayList<>();
            gui.items.add(item);
            gui.updateToggle = true;
        }
    }
}