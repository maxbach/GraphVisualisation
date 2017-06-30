package ru.dmop.windows;


import com.mxgraph.swing.mxGraphComponent;
import ru.dmop.finderWays.FloydFinder;
import ru.dmop.finderWays.Triple;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VisualisationFloydFrame extends JFrame {

    private Box graphPictureAndLength;

    public VisualisationFloydFrame(Graph graph, WayInGraph way) throws HeadlessException {

        super("Алгоритм Флойда. Обработка матрицы");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        FloydFinder finder = new FloydFinder(graph);
        JTable table = new JTable(finder);
        Box box = Box.createHorizontalBox();

        box.add(getRows(graph.getNumberOfNodes()));

        JScrollPane pane = new JScrollPane(table);
        pane.setAlignmentY(TOP_ALIGNMENT);
        box.add(pane);

        graph.highLightThePath(way);
        graphPictureAndLength = Box.createVerticalBox();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        graphPictureAndLength.add(graphComponent);
        Label label = new Label ("Длина пути - " + "\"" +way.getWayLength() + "\"");
        label.setAlignment(Label.CENTER);
        graphPictureAndLength.add(label);
        graphPictureAndLength.setAlignmentY(TOP_ALIGNMENT);
        graphPictureAndLength.setVisible(false);
        box.add(graphPictureAndLength);

        JButton nextButton = getNextButton(finder);
        nextButton.setAlignmentY(Component.TOP_ALIGNMENT);
        box.add(nextButton);

        setContentPane(box);
        pack();
        setVisible(true);
    }

    private JButton getNextButton(final FloydFinder finder) {
        JButton nextButton = new JButton("Next");
        nextButton.addMouseListener(new MouseAdapter() {
            Triple triple = new Triple();

            public void mouseClicked(MouseEvent e) {
                if (((triple.i < finder.getSize()) && (triple.j < finder.getSize()) && (triple.k < finder.getSize()))) {
                    finder.change_matrix(triple);
                    finder.fireTableDataChanged();
                    triple.j++;
                } else {
                    JOptionPane.showMessageDialog(VisualisationFloydFrame.this,
                            "Обработка матрицы закончена",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
                    nextButton.setVisible(false);
                    graphPictureAndLength.setVisible(true);
                }

            }
        });
        return nextButton;
    }

    private Box getRows(int size) {
        Box rows = Box.createVerticalBox();
        rows.setAlignmentY(TOP_ALIGNMENT);
        JLabel label;
        rows.add(new JLabel(" "));
        for (int i = 0; i < size; ++i) {
            label = new JLabel((char) (i + 'A') + "");
            rows.add(label);
        }
        return rows;
    }
}
