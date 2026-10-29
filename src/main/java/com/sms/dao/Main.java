package com.sms.dao;

import javax.swing.*;

import com.sms.dao.gui.LoginFrame;

public class Main {
    public static void main(String[] args) {
        // Launch the graphical Login window safely on the Swing Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            LoginFrame loginFrame = new LoginFrame();
            loginFrame.setVisible(true);
        });
    }
}