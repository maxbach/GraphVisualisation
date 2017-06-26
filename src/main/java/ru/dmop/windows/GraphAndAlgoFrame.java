package ru.dmop.windows;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Экран, на котором будет картинка графа и пользователю будет предоставлен выбор алгоритма и вершины
 */

public class GraphAndAlgoFrame extends JFrame {
    public GraphAndAlgoFrame() throws HeadlessException {
        super("Выбор вершины и алгоритма");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box mainBox = Box.createHorizontalBox();
        mainBox.add(getImage());
        mainBox.add(Box.createHorizontalStrut(10));
        mainBox.add(getButtons());
        setContentPane(mainBox);
        pack();
        setVisible(true);

    }

    private JLabel getImage() {
        JLabel label = new JLabel("Не загрузилось. Вы остались без мемаса.");
        try {
            BufferedImage img = ImageIO.read(new File("res/wt7e1p.jpg"));
            ImageIcon icon = new ImageIcon(img);
            label = new JLabel(icon);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return label;
    }

    private Box getButtons() {
        Box box = Box.createVerticalBox();
        box.add(getDejkstraButton());
        box.add(Box.createVerticalStrut(10));
        box.add(getFloydButton());
        box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        return box;


    }

    private JButton getDejkstraButton() {
        JButton button = new JButton("Алгоритм Дейкстра");
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // открываем новое окно
                new VisualizationFrame();
            }
        });
        return button;
    }

    private JButton getFloydButton() {
        JButton button = new JButton("Алгоритм Флойда");
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // открываем новое окно
                new VisualizationFrame();
            }
        });
        return button;
    }
}
