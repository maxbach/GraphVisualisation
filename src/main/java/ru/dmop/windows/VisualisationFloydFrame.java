package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.*;

public class VisualisationFloydFrame extends JFrame {
    public VisualisationFloydFrame(Graph graph, WayInGraph way, String name) throws HeadlessException {

        super(name);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        graph.highLightThePath(way);
        panel.add(new mxGraphComponent(graph));
        panel.add(new JButton("Next"));
        setContentPane(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
        JOptionPane.showMessageDialog(VisualisationFloydFrame.this,
                "Длина пути:" + way.getWayLength(),
                "Way length",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
