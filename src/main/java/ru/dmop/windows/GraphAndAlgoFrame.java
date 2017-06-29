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

    private mxGraphComponent graphComponent;
    private Object node1 = null;
    private Object node2 = null;

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
                    manageVertex(obj);
                }
            }
        });

        mainBox.add(Box.createHorizontalStrut(10));
        mainBox.add(getButtons());
        setContentPane(mainBox);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);


    }

    public GraphAndAlgoFrame(int numberOfNodes, int density) {
        this(GraphBuilder.getRandomGraph(numberOfNodes, density));

    }

    private Box getButtons() {
        Box box = Box.createVerticalBox();
        box.add(getDejkstraButton());
        box.add(Box.createVerticalStrut(10));

        Graph graph = (Graph) graphComponent.getGraph();
        if (graph.getNumberOfNodes() <= 25) {
            box.add(getFloydButton());
            box.add(Box.createVerticalStrut(10));
        }

        box.add(getWriteGraphButton());
        box.add(Box.createVerticalStrut(10));

        box.setAlignmentY(JComponent.CENTER_ALIGNMENT);
        return box;
    }

    private JButton getDejkstraButton() {
        final String name = "Алгоритм Дейкстры";

        JButton button = new JButton(name);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Object obj1 = GraphAndAlgoFrame.this.node1;
                Object obj2 = GraphAndAlgoFrame.this.node2;

                if (obj1 != null && obj2 != null) {
                    Graph graph = (Graph) graphComponent.getGraph();
                    Graph helpGraph = new Graph(graph);
                    int id1 = graph.getIdOfNode(obj1);
                    int id2 = graph.getIdOfNode(obj2);

                    DejkstraFinder finder = new DejkstraFinder(helpGraph, id1, id2);
                    new VisualisationDejkstraFrame(finder);

                    clear();
                }


            }
        });

        return button;
    }

    private JButton getFloydButton() {
        final String name = "Алгоритм Флойда";
        FloydFinder finder;

        JButton button = new JButton(name);

        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                Object obj1 = GraphAndAlgoFrame.this.node1;
                Object obj2 = GraphAndAlgoFrame.this.node2;

                if (obj1 != null && obj2 != null) {
                    Graph graph = (Graph) graphComponent.getGraph();
                    Graph helpGraph = new Graph(graph);
                    FloydFinder finder = new FloydFinder(helpGraph);


                    int id1 = graph.getIdOfNode(obj1);
                    int id2 = graph.getIdOfNode(obj2);

                    WayInGraph way = finder.getShortestPath(id1, id2);
                    if (way != null && way.isOk()) {
                        new VisualisationFloydFrame(helpGraph, way, name);
                    } else {
                        JOptionPane.showMessageDialog(GraphAndAlgoFrame.this,
                                "Нет пути между двумя вершинами",
                                "Find way error",
                                JOptionPane.ERROR_MESSAGE);
                    }

                    clear();
                }


            }
        });

        return button;
    }

    private JButton getWriteGraphButton() {
        final String name = "Записать граф в файл";

        JButton button = new JButton(name);
        /*button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Graph graph = (Graph) graphComponent.getGraph();
                graph.writeGraph();
                JOptionPane.showMessageDialog(GraphAndAlgoFrame.this,
                        "Граф записан в файл",
                        "",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });*/
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }
        });

        return button;
    }

    private void manageVertex(Object obj) {
        if (node1 == null) {
            node1 = obj;
            setGreen(obj);
        } else if (node2 == null) {
            if (node1 != obj) {
                node2 = obj;
                setRed(obj);
            }
        } else {
            setDefault(node1);
            setDefault(node2);
            setGreen(obj);
            node1 = obj;
            node2 = null;
        }
    }

    private void clear() {
        setDefault(node1);
        node1 = null;

        setDefault(node2);
        node2 = null;

    }

    private void setGreen(Object obj) {
        setStyleToObj(obj, GREEN_STYLE);
    }

    private void setRed(Object obj) {
        setStyleToObj(obj, RED_STYLE);
    }

    private void setDefault(Object obj) {
        setStyleToObj(obj, DEFAULT_STYLE);
    }

    private void setStyleToObj(Object obj, String style) {
        graphComponent.getGraph().getModel().setStyle(obj, style);
    }
}
