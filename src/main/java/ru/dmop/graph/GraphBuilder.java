package ru.dmop.graph;

import com.mxgraph.view.mxGraph;
import ru.dmop.windows.ErrorFrame;
import java.io.InputStream;
import java.util.Scanner;

public class GraphBuilder {

    private static final double width = 30, height = 30;
    private static double x;
    private static double y;
    private static double sin;
    private static double cos;
    private static double centerX;
    private static double centerY;

    public static mxGraph getGraph() {
        mxGraph graph = new mxGraph();
        Object parent = graph.getDefaultParent();

        graph.getModel().beginUpdate();
        try {
            Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
                    30);
            Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
                    80, 30);
            graph.insertEdge(parent, null, "Edge", v1, v2);
        } finally {
            graph.getModel().endUpdate();
        }

        return graph;
    }

    public static mxGraph getRandomGraph(int numberOfNodes, int density) {
        countConsts(numberOfNodes);
        Graph graph = new Graph();
        Object parent = graph.getDefaultParent();
        graph.addStyles();

        graph.getModel().beginUpdate();
        double realDensity = (double) density / (double) 100;
        int maxEdges = numberOfNodes * numberOfNodes;
        long numberOfEdges = Math.round(maxEdges * realDensity);


        try {

            for (int i = 0; i < numberOfNodes; i++) {
                graph.insertVertex(parent, i, (char) ('A' + i) + "", x, y, width, height);
                conversion();
            }

            Boolean[] bools = new Boolean[maxEdges];
            for (int i = 0; i < maxEdges; i++) {
                bools[i] = false;
            }

            if (numberOfEdges * 2 <= maxEdges) {
                for (int i = 0; i < numberOfEdges; i++) {
                    while (true) {
                        int p = (int) Math.round(Math.random() * (maxEdges - 1));
                        if (!bools[p]) {
                            bools[p] = true;
                            int first = p / numberOfNodes;
                            int second = p % numberOfNodes;
                            int weight = (int) ((Math.random()) * 10) + 1;
                            graph.insertEdge(parent, first, second, weight);
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < maxEdges - numberOfEdges; i++) {
                    while (true) {
                        int p = (int) Math.round(Math.random() * (maxEdges - 1));
                        if (!bools[p]) {
                            bools[p] = true;
                            break;
                        }
                    }
                }

                for (int i = 0; i < maxEdges; i++) {
                    if (!bools[i]) {
                        int first = i / numberOfNodes;
                        int second = i % numberOfNodes;
                        int weight = (int) ((Math.random()) * 10) + 1;
                        graph.insertEdge(parent, first, second, weight);
                    }
                }
            }


        } finally {
            graph.getModel().endUpdate();
        }

        return graph;
    }

    public static Graph getGraphFromFile(InputStream stream){
        int numberOfEdgesFromAGivenVertex, indexOfSecondVertex, edgeWeight;

        Graph graph = new Graph();
        Object parent = graph.getDefaultParent();
        graph.addStyles();
        graph.getModel().beginUpdate();

        try {
            Scanner scanner = new Scanner(stream);
            int numberOfNodes = scanner.nextInt();
            countConsts(numberOfNodes);

            for (int i = 0; i < numberOfNodes; i++) {
                if (graph.getVertex(i) == null){
                    graph.insertVertex(parent, i, (char) ('A' + i) + "", x, y, width, height);
                    conversion();
                }
                numberOfEdgesFromAGivenVertex = scanner.nextInt();
                for (int j = 0; j < numberOfEdgesFromAGivenVertex; ++j){
                    indexOfSecondVertex = scanner.nextInt();
                    if (graph.getVertex(indexOfSecondVertex) == null){
                        graph.insertVertex(parent, indexOfSecondVertex, (char) ('A' + indexOfSecondVertex) + "", x, y, width, height);
                        conversion();
                    }
                    edgeWeight = scanner.nextInt();
                    if (edgeWeight < 0){
                        new ErrorFrame("Некорректный формат входных данных", "Ошибка ввода графа");
                        return null;
                    }
                    graph.insertEdge(parent, i, indexOfSecondVertex, edgeWeight);
                }
            }
        } catch (Exception el){
            new ErrorFrame("Некорректный формат входных данных", "Ошибка ввода графа");
            return null;
        }
        finally {
            graph.getModel().endUpdate();
        }

        return graph;
    }

    private static void countConsts(int numberOfNodes) {
        double radius = width + numberOfNodes * 10;
        centerX = radius + 10;
        centerY = radius + 10;
        double angle = 2 * Math.PI / numberOfNodes;
        sin = Math.sin(angle);
        cos = Math.cos(angle);
        x = centerX;
        y = 10;
    }

    private static void conversion () {
        double tempX = centerX + (x - centerX) * cos - (y - centerY) * sin;
        double tempY = centerY + (y - centerY) * cos + (x - centerX) * sin;
        x = tempX;
        y = tempY;
    }
}

