package com.frontend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoadListener implements ActionListener {

    private GUI gui;

    LoadListener(GUI gui) {
        this.gui = gui;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.loadButton) {

        }
    }
}