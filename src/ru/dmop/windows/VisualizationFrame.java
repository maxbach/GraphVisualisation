package ru.dmop.windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 // Экран, на котором будет визуализация алгоритма
 */
public class VisualizationFrame extends JFrame {
    public VisualizationFrame() throws HeadlessException {

        super("Тут будет визуализация алгоритма");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

        try {
            BufferedImage img = ImageIO.read(new File("res/wt7e1p.jpg"));
            ImageIcon icon = new ImageIcon(img);
            JLabel label = new JLabel(icon);
            panel.add(label);

        } catch (IOException e) {
            e.printStackTrace();
        }

        setContentPane(panel);
        pack();
        setVisible(true);

    }
}
