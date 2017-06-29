package ru.dmop.windows;

import javax.swing.*;

/**
 * Created by vend on 29.06.2017.
 */
public class ErrorFrame extends JFrame {
    public ErrorFrame (String message, String title){
        JOptionPane.showMessageDialog(ErrorFrame.this,
                message,
                title,
                JOptionPane.ERROR_MESSAGE);
    }
}
