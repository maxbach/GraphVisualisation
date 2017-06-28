package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.*;

import static ru.dmop.graph.StyleConstants.EDGE_STYLE;

/**
 * // Экран, на котором будет визуализация алгоритма
 */
public class VisualizationFrame extends JFrame {
    public VisualizationFrame(Graph graph, WayInGraph way, String name) throws HeadlessException {

        super(name);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        //graph.highLightThePath(way);
        panel.add(new mxGraphComponent(graph));
        panel.add(new JButton("Next"));
        setContentPane(panel);
        pack();
        setVisible(true);
        JOptionPane.showMessageDialog(VisualizationFrame.this,
                "Длина пути:" + way.getWayLength(),
                "Way length",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
