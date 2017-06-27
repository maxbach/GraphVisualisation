package ru.dmop.windows;

import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;
import ru.dmop.finderWays.DejkstraFinder;
import ru.dmop.finderWays.FloydFinder;
import ru.dmop.finderWays.WayInGraph;
import ru.dmop.graph.Graph;
import ru.dmop.graph.GraphBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static ru.dmop.graph.StyleConstants.*;


/**
 * Экран, на котором будет картинка графа и пользователю будет предоставлен выбор алгоритма и вершины
 */

public class GraphAndAlgoFrame extends JFrame {

    mxGraphComponent graphComponent;
    Object node1 = null;
    Object node2 = null;

    public GraphAndAlgoFrame(final mxGraph graph) throws HeadlessException {
        super("Выбор вершины и алгоритма");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Box mainBox = Box.createHorizontalBox();
        graphComponent = new mxGraphComponent(graph);
        mainBox.add(graphComponent);
        graphComponent.setEnabled(false);
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Object obj = graphComponent.getCellAt(e.getX(), e.getY());
                if (obj != null && graph.getModel().isVertex(obj)) {

                    if (node1 == null) {

                        node1 = obj;
                        setGreen(obj);
                        Graph help = (Graph) graph;
                       //graph.getModel().setStyle(help.getEdge(0,1), EDGE_STYLE);
                    } else if (node2 == null) {

                        node2 = obj;
                        setRed(obj);

                    } else {

                        setDefault(node1);
                        setGreen(obj);
                        setDefault(node2);
                        node1 = obj;
                        node2 = null;

                    }
                }
            }
        });

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
                Object obj1 = GraphAndAlgoFrame.this.node1;
                Object obj2 = GraphAndAlgoFrame.this.node2;

                if (obj1 != null && obj2 != null) {

                    Graph graph = (Graph) graphComponent.getGraph();
                    DejkstraFinder finder = new DejkstraFinder(graph);
                    int id1 = graph.getIdOfNode(obj1);
                    int id2 = graph.getIdOfNode(obj2);
                    WayInGraph way = finder.getShortestPath(id1, id2);
                    if (way.isOk()) {
                        graph.highLightThePath(way);
                        new VisualizationFrame(graph, "Алгоритм Дейкстра");
                    } else {
                        JOptionPane.showMessageDialog(GraphAndAlgoFrame.this,
                                "Нет пути между двумя вершинами",
                                "Find way error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                }


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
                Graph graph = (Graph) graphComponent.getGraph();
                FloydFinder finder = new FloydFinder(graph);
                    new VisualizationFrame(graphComponent.getGraph(), "Алгоритм Флойда");

            }
        });
        return button;
    }

    private void setGreen(Object obj) {

        graphComponent.getGraph().getModel().setStyle(obj, GREEN_STYLE);
    }

    private void setRed(Object obj) {
        graphComponent.getGraph().getModel().setStyle(obj, RED_STYLE);
    }

    private void setDefault(Object obj) {
        graphComponent.getGraph().getModel().setStyle(obj, DEFAULT_STYLE);
    }
}
