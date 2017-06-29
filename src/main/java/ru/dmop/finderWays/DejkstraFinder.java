package ru.dmop.finderWays;


import com.mxgraph.model.mxCell;
import ru.dmop.graph.Graph;
import ru.dmop.graph.StyleConstants;

import java.util.ArrayList;

public class DejkstraFinder implements BaseFinder {

    private Boolean[] isAlso;
    private Integer[] prev;
    private Integer[] ways;
    private Graph graph;

    private Integer pointA;
    private Integer pointB;


    private Integer currentNode = -1;
    private Integer currentRelatedNode = -1;
    private ArrayList<Integer> relatedNodes;
    private Boolean isReady = false;
    private String message = "";

    public DejkstraFinder() {
    }

    public DejkstraFinder(Graph graph, Integer pointA, Integer pointB) {
        this.pointA = pointA;
        this.pointB = pointB;
        setGraph(graph);
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
        isAlso = new Boolean[graph.getNumberOfNodes()];
        prev = new Integer[graph.getNumberOfNodes()];
        ways = new Integer[graph.getNumberOfNodes()];

        for (int i = 0; i < graph.getNumberOfNodes(); i++) {
            if (i == pointA) {
                prev[i] = -1;
                ways[i] = 0;
            } else {
                ways[i] = Integer.MAX_VALUE;
            }
            isAlso[i] = false;
        }
    }

    public void initGraph() {
        graph.getModel().beginUpdate();
        for (mxCell mxCell : graph.getNodes()) {
            if (graph.getIdOfNode(mxCell).equals(pointA)) {
                String name = (String) mxCell.getValue();
                mxCell.setValue(name + " (0) -> begin");
                mxCell.getGeometry().setWidth(70);
            } else {
                String name = (String) mxCell.getValue();
                mxCell.setValue(name + " (∞) -> ?");
                mxCell.getGeometry().setWidth(110);
            }

            graph.updateCellSize(mxCell);

        }
        graph.getModel().endUpdate();
    }

    public void doNextStep() {
        if (isReady) {
            setDefaultNode(currentNode);
            setDefaultEdges(relatedNodes);
            WayInGraph way = findWay(pointA, pointB);
            if (way != null && way.isOk()) {
                graph.highLightThePath(way);
                message = "Путь найден. Длина пути равна " + way.getWayLength();
            }
            currentNode = -1;

        } else if (currentNode == -1) {
            currentNode = pointA;
            setHighlightNode(currentNode);
            message = "Рассмотрим вершину " + graph.getNameOfNode(currentNode);
            relatedNodes = graph.getRelatedNodes(currentNode);
            if (relatedNodes.size() == 0) {
                message = message + ". Из начальной вершины не исходит ребер. Нет пути.";
                isReady = true;
                currentNode = -1;
            } else {
                setRelatedEdges(relatedNodes);
                currentRelatedNode = 0;
                message = message + ". Выходящие ребра: " + relatedNodes.size() + ". Сделаем релаксацию всех ребер";
            }

        } else if (currentRelatedNode == -1) {
            setDefaultNode(currentNode);
            setDefaultEdges(relatedNodes);
            currentNode = findMin();
            setHighlightNode(currentNode);
            message = "Рассмотрим вершину " + graph.getNameOfNode(currentNode);
            if (ways[currentNode] == Integer.MAX_VALUE) {
                message = message + ". Все плохо (придумать причину). Нет пути";
                isReady = true;
                currentNode = -1;
            }
            relatedNodes = graph.getRelatedNodes(currentNode);
            if (relatedNodes.size() == 0) {
                message = message + ". Из данной вершины не исходит ребер.";
                currentRelatedNode = -1;
                isAlso[currentNode] = true;
            } else {
                setRelatedEdges(relatedNodes);
                currentRelatedNode = 0;
                message = message + ". Выходящие ребра: " + relatedNodes.size() + ". Сделаем релаксацию всех ребер";
            }

        } else {
            if (currentRelatedNode != 0) {
                setDefaultOneEdge(currentNode, relatedNodes.get(currentRelatedNode - 1));
            }
            int relatedNode = relatedNodes.get(currentRelatedNode);
            setHighlightOneEdge(currentNode, relatedNode);
            message = "Рассмотрим ребро " + graph.getNameOfNode(currentNode) + " -> " + graph.getNameOfNode(relatedNode)
                    + ", длина которого " + graph.getWeightOfEdge(currentNode, relatedNode) + ". ";
            relax(currentNode, relatedNode);
            currentRelatedNode++;
            if (currentRelatedNode == relatedNodes.size()) {
                isAlso[currentNode] = true;
                currentRelatedNode = -1;
                if (currentNode.equals(pointB)) {
                    isReady = true;
                }
            }
        }
    }

    private void setRelatedEdges(ArrayList<Integer> relatedNodes) {
        for (Integer relatedNode : relatedNodes) {
            ((mxCell) graph.getEdge(currentNode, relatedNode)).setStyle(StyleConstants.RELATED_EDGE_STYLE);
            setRelatedNode(relatedNode);
        }
    }

    private void setDefaultEdges(ArrayList<Integer> relatedNodes) {
        for (Integer relatedNode : relatedNodes) {
            ((mxCell) graph.getEdge(currentNode, relatedNode)).setStyle(StyleConstants.DEFAULT_EDGE_STYLE);
            setDefaultNode(relatedNode);

        }
    }

    private void setHighlightOneEdge(Integer pointA, Integer pointB) {
        mxCell cell = (mxCell) graph.getEdge(pointA, pointB);
        cell.setStyle(StyleConstants.HIGHLIGHTED_EDGE_STYLE);
        setHighlightNode(pointB);
    }

    private void setDefaultOneEdge(Integer pointA, Integer pointB) {
        mxCell cell = (mxCell) graph.getEdge(pointA, pointB);
        cell.setStyle(StyleConstants.RELATED_EDGE_STYLE);
        setDefaultNode(pointB);
    }

    private void setHighlightNode(Integer node) {
        mxCell cell = (mxCell) graph.getNode(node);
        if (node.equals(pointA)) {
            cell.setStyle(StyleConstants.HIGHLIGHTED_GREEN_NODE_STYLE);
        } else if (node.equals(pointB)) {
            cell.setStyle(StyleConstants.HIGHLIGHTED_RED_NODE_STYLE);
        } else {
            cell.setStyle(StyleConstants.HIGHLIGHTED_NODE_STYLE);
        }
    }

    private void setRelatedNode(Integer node) {
        mxCell cell = (mxCell) graph.getNode(node);
        if (node.equals(pointA)) {
            cell.setStyle(StyleConstants.RELATED_GREEN_NODE_STYLE);
        } else if (node.equals(pointB)) {
            cell.setStyle(StyleConstants.RELATED_RED_NODE_STYLE);
        } else {
            cell.setStyle(StyleConstants.RELATED_NODE_STYLE);
        }

    }

    private void setDefaultNode(Integer node) {
        mxCell cell = (mxCell) graph.getNode(node);
        if (node.equals(pointA)) {
            cell.setStyle(StyleConstants.GREEN_STYLE);
        } else if (node.equals(pointB)) {
            cell.setStyle(StyleConstants.RED_STYLE);
        } else {
            cell.setStyle(StyleConstants.DEFAULT_STYLE);
        }
    }





    private void relax(int pointA, int pointB) {
        int length = graph.getWeightOfEdge(pointA, pointB);
        if (!isAlso[pointB] && ways[pointA] + length < ways[pointB]) {
            message = message + "Длина до вершины " + graph.getNameOfNode(pointB) + " изменилась: ";
            if (ways[pointB] == Integer.MAX_VALUE) {
                message = message + "∞ -> ";
            } else {
                message = message + ways[pointB] + " -> ";
            }
            ways[pointB] = ways[pointA] + length;
            message = message + ways[pointB];
            prev[pointB] = pointA;
            updateNode(pointB, ways[pointB], pointA);

        } else {
            message = message + "Длина до вершины осталась такой же.";
        }
    }

    private void updateNode(Integer nodeId, Integer newWeight, Integer newPrev) {
        mxCell cell = (mxCell) graph.getNode(nodeId);
        cell.setValue(graph.getNameOfNode(nodeId) + " (" + newWeight + ") -> " + graph.getNameOfNode(newPrev));
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
            Integer current = j == 0 ? pointA : findMin();
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


    private Integer findMin() {
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

    public Graph getGraph() {
        return graph;
    }

    public Boolean isReady() {
        return isReady && currentNode == -1;
    }

    public String getMessage() {
        return message;
    }
}
