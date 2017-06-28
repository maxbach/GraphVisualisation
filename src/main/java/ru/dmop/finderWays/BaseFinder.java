package ru.dmop.finderWays;

import ru.dmop.graph.Graph;

public interface BaseFinder {
    WayInGraph getShortestPath(int pointA, int pointB);

    void setGraph(Graph graph);
}
