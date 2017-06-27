package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

/**
 * // Экран, на котором будет визуализация алгоритма
 */
public class VisualizationFrame extends JFrame {
    public VisualizationFrame(mxGraph graph, String name) throws HeadlessException {

        super(name);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();

        panel.add(new mxGraphComponent(graph));
        panel.add(new JButton("Next"));
        setContentPane(panel);
        pack();
        setVisible(true);

    }
}
