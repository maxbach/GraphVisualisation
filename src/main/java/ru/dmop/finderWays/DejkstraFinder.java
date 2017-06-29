package ru.dmop.finderWays;


import ru.dmop.graph.Graph;

import java.util.ArrayList;

public class DejkstraFinder implements BaseFinder {

    private Boolean[] isAlso;
    private Integer[] prev;
    private Integer[] ways;
    private Graph graph;

    private Integer currentNode = -1;
    private Integer currentRelatedNode = -1;
    private ArrayList<Integer> relatedNodes;

    public DejkstraFinder() {
    }

    public DejkstraFinder(Graph graph) {
        setGraph(graph);
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        isAlso = new Boolean[graph.getNumberOfNodes()];
        prev = new Integer[graph.getNumberOfNodes()];
        ways = new Integer[graph.getNumberOfNodes()];
    }

    public void doNextStep() {

    }

    private void relax(int pointA, int pointB) {
        int length = graph.getWeightOfEdge(pointA, pointB);
        if (!isAlso[pointB] && ways[pointA] + length < ways[pointB]) {
            ways[pointB] = ways[pointA] + length;
            prev[pointB] = pointA;
        }
    }

    public WayInGraph getShortestPath(int pointA, int pointB) {
        for (int i = 0; i < graph.getNumberOfNodes(); ++i) {
            isAlso[i] = false;
            prev[i] = pointA;
            if (i == pointA) {
                ways[i] = 0;
            } else {
                ways[i] = Integer.MAX_VALUE;
            }
        }

        for (int j = 0; j < graph.getNumberOfNodes(); ++j) {
            Integer current = j == 0 ? pointA : findmin();
            isAlso[current] = true;
            if (ways[current] == Integer.MAX_VALUE) {
                continue;
            }
            ArrayList<Integer> relatedNodes = graph.getRelatedNodes(current);
            for (Integer node : relatedNodes) {
                relax(current, node);
            }

            if (current == pointB) {
                return findWay(pointA, pointB);
            }
        }
        return null;
    }


    private Integer findmin() {
        int minWay = Integer.MAX_VALUE;
        Integer min = 0;

        for (int i = 0; i < graph.getNumberOfNodes(); ++i) {
            if (ways[i] < minWay && !isAlso[i]) {
                minWay = ways[i];
                min = i;
            }
        }

        return min;
    }

    private WayInGraph findWay(int pointA, int pointB) {
        Integer end = pointB;
        if (ways[pointB] == Integer.MAX_VALUE) {
            return null;
        }
        WayInGraph w = new WayInGraph(ways[pointB]);
        while (end != pointA) {
            w.addToTheEnd(end);
            end = prev[end];
        }
        w.addToTheEnd(pointA);
        return w;

    }
}
