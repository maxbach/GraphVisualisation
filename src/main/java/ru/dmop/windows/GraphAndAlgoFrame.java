package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import ru.dmop.graph.GraphBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Экран, на котором будет картинка графа и пользователю будет предоставлен выбор алгоритма и вершины
 */

public class GraphAndAlgoFrame extends JFrame {
    mxGraphComponent graphComponent;

    public GraphAndAlgoFrame(mxGraph graph) throws HeadlessException {
        super("Выбор вершины и алгоритма");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box mainBox = Box.createHorizontalBox();
        graphComponent = new mxGraphComponent(graph);
        mainBox.add(graphComponent);
        mainBox.add(Box.createHorizontalStrut(10));
        mainBox.add(getButtons());
        setContentPane(mainBox);
        pack();
        setVisible(true);

    }

    public GraphAndAlgoFrame(int numberOfNodes, int density) {
        this(GraphBuilder.getRandomGraph(numberOfNodes, density));

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
                new VisualizationFrame(graphComponent.getGraph());
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
                new VisualizationFrame(graphComponent.getGraph());
            }
        });
        return button;
    }
}
