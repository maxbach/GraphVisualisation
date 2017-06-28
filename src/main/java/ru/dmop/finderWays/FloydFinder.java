package ru.dmop.finderWays;

import ru.dmop.graph.Graph;

import java.util.ArrayList;

public class FloydFinder implements BaseFinder {

    class Pair {
        int weight;
        int help_vertex;
    }

    private int size;
    private Pair[][] matrix;
    private Graph graph;

    public FloydFinder() {
    }

    public FloydFinder(Graph graph) {
        setGraph(graph);

    }

    private void change_matrix() {
        int x;
        for (int k = 0; k < size; ++k) {
            for (int i = 0; i < size; ++i) {
                for (int j = 0; j < size; ++j) {
                    if ((k == i) || (k == j) || (i == j)
                            || (matrix[i][k].weight == Integer.MAX_VALUE) || (matrix[k][j].weight == Integer.MAX_VALUE))
                        continue;

                    if (matrix[i][j].weight >= (matrix[i][k].weight + matrix[k][j].weight)) {
                        x = matrix[i][k].weight + matrix[k][j].weight;
                        matrix[i][j].weight = x;
                        matrix[i][j].help_vertex = k;
                    }
                }
            }
        }
    }


    private void getShortestPathMain(int pointA, int pointB, ArrayList<Integer> way) {

        if ((matrix[pointA][pointB].help_vertex == pointB) || (matrix[pointA][pointB].help_vertex == pointA)) return;
        if (matrix[pointA][pointB].help_vertex != pointA) {
            getShortestPathMain(pointA, matrix[pointA][pointB].help_vertex, way);
            Integer help = matrix[pointA][pointB].help_vertex;
            way.add(help);
            getShortestPathMain(matrix[pointA][pointB].help_vertex, pointB, way);
        }
    }

    public WayInGraph getShortestPath(int pointA, int pointB) {
        ArrayList<Integer> way = new ArrayList<Integer>();
        if (matrix[pointA][pointB].weight == Integer.MAX_VALUE) {
            return new WayInGraph(0);
        } else {
            way.add(pointA);
            getShortestPathMain(pointA, pointB, way);
            way.add(pointB);
            int length = 0;
            int i = 1;
            while (i != way.size()) {
                length += graph.getWeightOfEdge(way.get(i - 1), way.get(i));
                ++i;
            }
            return new WayInGraph(length, way);
        }
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        size = graph.getNumberOfNodes();
        matrix = new Pair[size][size];
        for (int i = 0; i < size; ++i)
            for (int j = 0; j < size; ++j) {
                matrix[i][j] = new Pair();
            }

        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                if (i == j) matrix[i][j].weight = 0;
                else
                    matrix[i][j].weight = Integer.MAX_VALUE;
                matrix[i][j].help_vertex = i;
            }
        }

        for (int i = 0; i != size; ++i) {
            for (int j = 0; j != size; ++j) {
                if (graph.getWeightOfEdge(i, j) != null)
                    matrix[i][j].weight = graph.getWeightOfEdge(i, j);
            }
        }
        change_matrix();
    }

}
