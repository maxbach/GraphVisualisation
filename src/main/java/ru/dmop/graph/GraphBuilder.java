package ru.dmop.graph;

import com.mxgraph.view.mxGraph;

import javax.imageio.stream.FileImageInputStream;
import java.io.*;
import java.util.ArrayList;


public class GraphBuilder implements Serializable {

    private static double x;
    private static double y;
    private static double sin;
    private static double cos;
    private static double centerX;
    private static double centerY;
    private static final double width = 30, height = 30;


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

        graph.getModel().beginUpdate();
        double realDensity = (double) density / (double) 100;
        int maxEdges = numberOfNodes * numberOfNodes;
        long numberOfEdges = Math.round(maxEdges * realDensity);


        try {

            for (int i = 0; i < numberOfNodes; i++) {
                graph.insertVertex(parent, i, (char) ('A' + i) + "", x, y, width, height);
                double tempX = centerX + (x - centerX) * cos - (y - centerY) * sin;
                double tempY = centerY + (y - centerY) * cos + (x - centerX) * sin;
                x = tempX;
                y = tempY;
            }

            ArrayList<Boolean> bools = new ArrayList<Boolean>();
            for (int i = 0; i < maxEdges; i++) {
                bools.add(false);
            }

            if (numberOfEdges * 2 <= maxEdges) {
                for (int i = 0; i < numberOfEdges; i++) {
                    while (true) {
                        int p = (int) Math.round(Math.random() * maxEdges) - 1;
                        if (!bools.get(p)) {
                            bools.remove(p);
                            bools.add(p, true);
                            int first = p / numberOfNodes;
                            int second = p % numberOfNodes;
                            int weight = (int) ((Math.random()) * 10);
                            graph.insertEdge(parent, first, second, weight);
                            break;
                        }
                    }
                }
            } else {
                for (int i = 0; i < maxEdges - numberOfEdges; i++) {
                    while (true) {
                        int p = (int) Math.round(Math.random() * maxEdges) - 1;
                        if (!bools.get(p)) {
                            bools.remove(p);
                            bools.add(p, true);
                            break;
                        }
                    }
                }

                for (int i = 0; i < maxEdges; i++) {
                    if (!bools.get(i)) {
                        int first = i / numberOfNodes;
                        int second = i % numberOfNodes;
                        int weight = (int) ((Math.random()) * 10);
                        graph.insertEdge(parent, first, second, weight);
                    }
                }
            }


        } finally {
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

    public static mxGraph getGraphFromFile (final InputStream stream) throws IOException {
        Graph graph = null;
        ObjectInputStream objectinputstream = null;
        try {
            objectinputstream = new ObjectInputStream(stream);
            graph = (Graph) objectinputstream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(objectinputstream != null){
                objectinputstream .close();
            }
        }
        return graph;
    }

}
