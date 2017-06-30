package ru.dmop.windows;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class NumberTextField extends JTextField {
    public NumberTextField() {
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char ch = e.getKeyChar();

                if (getText().equals("0")) {
                    setText("");
                }
                if (!isNumber(ch) && ch != '\b') {
                    e.consume();
                }
            }
        });

    }

    private boolean isNumber(char ch) {
        return ch >= '0' && ch <= '9';
    }

}

