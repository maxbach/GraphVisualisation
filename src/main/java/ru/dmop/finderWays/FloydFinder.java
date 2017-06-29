package ru.dmop.finderWays;

import ru.dmop.graph.Graph;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class FloydFinder extends AbstractTableModel {

    private int size;
    private Pair[][] matrix;
    private Graph graph;


    public FloydFinder(Graph graph) {
        setGraph(graph);

    }

    public void change_matrix(Triple triple) {
        int x;

        for (; triple.k < size; ++triple.k) {
            for (; triple.i < size; ++triple.i) {
                for (; triple.j < size - 1; ++triple.j) {
                    if ((triple.k == triple.i) || (triple.k == triple.j) || (triple.i == triple.j)
                            || (matrix[triple.i][triple.k].weight == Integer.MAX_VALUE) || (matrix[triple.k][triple.j].weight == Integer.MAX_VALUE))
                        continue;


                    if (matrix[triple.i][triple.j].weight >= (matrix[triple.i][triple.k].weight + matrix[triple.k][triple.j].weight)) {
                        x = matrix[triple.i][triple.k].weight + matrix[triple.k][triple.j].weight;
                        matrix[triple.i][triple.j].weight = x;
                        matrix[triple.i][triple.j].help_vertex = triple.k;
                        return;
                    }
                }
                triple.j = 0;
            }
            triple.i = 0;
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
        Triple triple = new Triple();
        for (; ((triple.i < size) && (triple.j < size) && (triple.k < size)); triple.j++)
            change_matrix(triple);
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
    }

    public int getSize() {
        return size;
    }

    public int getRowCount() {
        return size;
    }

    public int getColumnCount() {
        return size;
    }

    public Object getValueAt(int r, int c) {
        if (matrix[r][c].weight == Integer.MAX_VALUE)
            return "oo" + " , " + (char) (matrix[r][c].help_vertex + 'A');
        else
            return matrix[r][c].weight + " , " + (char) (matrix[r][c].help_vertex + 'A');
    }

}
