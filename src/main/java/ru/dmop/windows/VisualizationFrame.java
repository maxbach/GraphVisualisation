package ru.dmop.windows;

import ru.dmop.graph.GraphBuilder;

import javax.swing.*;
import java.awt.*;

/**
 * // Экран, на котором будет визуализация алгоритма
 */
public class VisualizationFrame extends JFrame {
    public VisualizationFrame() throws HeadlessException {

        super("Тут будет визуализация алгоритма");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.add(GraphBuilder.getGraph());

        setContentPane(panel);
        pack();
        setVisible(true);

    }
}
