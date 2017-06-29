package ru.dmop.windows;


import ru.dmop.finderWays.FloydFinder;
import ru.dmop.finderWays.Triple;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class VisualisationFloydFrame extends JFrame {
    public VisualisationFloydFrame(Graph graph, WayInGraph way) throws HeadlessException {

        super("Алгоритм Флойда. Обработка матрицы");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        FloydFinder finder = new FloydFinder(graph);
        JTable table = new JTable(finder);
        JPanel panel = new JPanel();
        panel.add(getRows(graph.getNumberOfNodes()));
        JScrollPane pane = new JScrollPane(table);
        panel.add(pane);
        JButton nextButton = getNextButton(finder, graph, way);
        panel.add(nextButton);
        setContentPane(panel);
        pack();
        setVisible(true);
    }

    private JButton getNextButton(final FloydFinder finder, Graph graph, WayInGraph way) {
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
                    new FloydGraphFrame(graph, way);
                }

            }
        });
        return nextButton;
    }

    private Box getRows(int size) {
        Box rows = Box.createVerticalBox();
        rows.setAlignmentY(TOP_ALIGNMENT);
        rows.add(Box.createVerticalGlue());
        JLabel label;
        for (int i = 0; i < size; ++i) {
            label = new JLabel(new String((char) (i + 'A') + ""));
            rows.add(label);
        }
        return rows;
    }
}
