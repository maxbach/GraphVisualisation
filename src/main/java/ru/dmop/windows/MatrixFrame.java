package ru.dmop.windows;

import ru.dmop.finderWays.FloydFinder;
import ru.dmop.finderWays.Triple;
import ru.dmop.graph.Graph;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by vend on 29.06.2017.
 */
public class MatrixFrame extends JFrame {
    public MatrixFrame(Graph graph) {
        super("Matrix");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        FloydFinder finder = new FloydFinder(graph);
        JTable table = new JTable(finder);
        JPanel panel = new JPanel();
        panel.add(getRows(graph.getNumberOfNodes()));
        panel.add(new JScrollPane(table));
        panel.add(getNextButton(finder));
        super.setContentPane(panel);
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
                    JOptionPane.showMessageDialog(MatrixFrame.this,
                            "Обработка матрицы закончена",
                            "",
                            JOptionPane.INFORMATION_MESSAGE);
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
            label.setVerticalAlignment(JLabel.TOP);
            rows.add(label);
        }
        return rows;
    }

}
