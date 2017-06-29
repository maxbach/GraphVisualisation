package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ru.dmop.graph.StyleConstants.HIGHLIGHTED_EDGE_STYLE;

public class VisualisationFloydFrame extends JFrame {
    public VisualisationFloydFrame(Graph graph, WayInGraph way, String name) throws HeadlessException {

        super(name);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JPanel panel = new JPanel();
        visualisationMatrix(graph);
        visualisationWay(graph, way, panel);
        setContentPane(panel);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }

    private void visualisationWay(final Graph graph, final WayInGraph way, JPanel panel) {

        panel.add(new mxGraphComponent(graph));
        JButton nextButton = new JButton("Next");
        nextButton.addMouseListener(new MouseAdapter() {
            int i = 0;

            @Override
            public void mouseClicked(MouseEvent e) {
                int size = way.getWay().size();
                if (i < size - 1) {
                    Object help = graph.getEdge(way.getWay().get(i), way.getWay().get(i + 1));
                    graph.getModel().setStyle(help, HIGHLIGHTED_EDGE_STYLE);
                    i++;
                } else {
                    if (i == size - 1) {
                        JOptionPane.showMessageDialog(VisualisationFloydFrame.this,
                                "Путь найден. Длина пути - " + way.getWayLength(),
                                "",
                                JOptionPane.INFORMATION_MESSAGE);
                        i++;
                    }
                }
            }
        });
        panel.add(nextButton);

    }

    private void visualisationMatrix(Graph graph) {
        new MatrixFrame(graph);
    }
}
