package ru.dmop.finderWays;


import com.mxgraph.model.mxCell;
import ru.dmop.graph.Graph;
import ru.dmop.graph.StyleConstants;

import java.util.ArrayList;

public class DejkstraFinder {

    private Boolean[] isAlso;
    private Integer[] prev;
    private Integer[] ways;
    private Graph graph;

    private Integer pointA;
    private Integer pointB;


    private Integer currentNode = -1; // вершина, которая сейчас обрабатывается
    private Integer currentRelatedNode = -1; // родственная вершина, которая сейчас обрабатывается
    private ArrayList<Integer> relatedNodes; // родственные вершины
    private Boolean isReady = false; // выполнил ли алгоритм свою задачу
    private String message = ""; // сообщение, которое будет выводится при каждой задаче


    public DejkstraFinder(Graph graph, Integer pointA, Integer pointB) {
        // инициализируем все по умолчанию
        this.pointA = pointA;
        this.pointB = pointB;
        this.graph = graph;
        isAlso = new Boolean[graph.getNumberOfNodes()];
        prev = new Integer[graph.getNumberOfNodes()];
        ways = new Integer[graph.getNumberOfNodes()];

        // инициализируем массивы prev, ways and isAlso
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

        // для каждого узла
        for (mxCell mxCell : graph.getNodes()) {
            if (graph.getIdOfNode(mxCell).equals(pointA)) { // если вершина - начало пути
                String name = (String) mxCell.getValue();
                mxCell.setValue(name + " (0) -> begin");
                mxCell.getGeometry().setWidth(110); // делаем шире
            } else {
                String name = (String) mxCell.getValue();
                mxCell.setValue(name + " (∞) -> ?");
                mxCell.getGeometry().setWidth(110); // делаем шире
            }

            graph.updateCellSize(mxCell); // обновляем размер вершин

        }
    }

    // метод, который выполняется пошагово
    public void doNextStep() {
        if (isReady) { // ндошли до конца, давайте выделим путь
            WayInGraph way = findWay(pointA, pointB);
            if (way != null && way.isOk()) {
                graph.highLightThePath(way); // выделяем путь
                message = "Путь найден. Длина пути равна " + way.getWayLength(); // выводим длину
            }
            currentNode = -1;


        } else if (currentNode == -1) { // мы в самом начале пути, еще ничего не трогали
            currentNode = pointA; // начинаем обрабатывать с начала
            message = "Рассмотрим вершину " + graph.getNameOfNode(currentNode);
            relatedNodes = graph.getRelatedNodes(currentNode); // ищем родственные пути начала

            // если нет родственных ребер у начала, значит все сразу потеряно
            if (relatedNodes.size() == 0) {
                message = message + ". Из начальной вершины не исходит ребер. Нет пути.";
                isReady = true;
                currentNode = -1;
            } else { // иначе выделяем родственные пути
                setHighlightNode(currentNode);
                setRelatedEdges(relatedNodes);
                currentRelatedNode = 0;
                message = message + ". Выходящие ребра: " + relatedNodes.size() + ". Сделаем релаксацию всех ребер";
            }

        } else if (currentRelatedNode == -1) { // если мы обработали все родственные вершины предыдущей вершины

            // возвращаем старый стиль
            setDefaultNode(currentNode);
            setDefaultEdges(relatedNodes);

            // ищем нового героя
            currentNode = findMin();


            if (currentNode == null) { // если не нашли, то не судьба
                message = "Больше нет вариантов для перебора. Нет пути.";
                isReady = true;
                currentNode = -1;
                return;
            } else if (currentNode.equals(pointB)) { // если дошли до конца, то осталось выделить путь
                isReady = true;
                message = "Мы добрались до конечной вершины. Нажми next, чтобы узнать путь.";
                return;
            }

            // выделяем нашего героя
            setHighlightNode(currentNode);
            message = "Рассмотрим вершину " + graph.getNameOfNode(currentNode);

            // если в нашего героя не пришло ни одного ребра, значит оно бесполезно
            if (ways[currentNode] == Integer.MAX_VALUE) {
                message = message + ". Все плохо (придумать причину). Нет пути";
                isReady = true;
                currentNode = -1;
                return;
            }

            relatedNodes = graph.getRelatedNodes(currentNode); // ищем родственные связи нашего героя

            if (relatedNodes.size() == 0) { // если нет родственных связей, то наш герой тихо грустит в сторонке
                message = message + ". Из данной вершины не исходит ребер.";
                currentRelatedNode = -1;
                isAlso[currentNode] = true;
            } else { // иначе он выделяет все родственные вершины и ждет команд хозяина
                setRelatedEdges(relatedNodes);
                currentRelatedNode = 0;
                message = message + ". Выходящие ребра: " + relatedNodes.size() + ". Сделаем релаксацию всех ребер";
            }

        } else { // эта ситуация, когда мы просто обрабатываем какое-то ребро, пытаемся его прорелаксировать

            if (currentRelatedNode != 0) { // если мы уже что-то обрабатывали, то нужно вернуть на место
                setDefaultOneEdge(currentNode, relatedNodes.get(currentRelatedNode - 1));
            }

            // запоминаем обрабатываемого соседа и выделяем его
            int relatedNode = relatedNodes.get(currentRelatedNode);
            setHighlightOneEdge(currentNode, relatedNode);
            message = "Рассмотрим ребро " + graph.getNameOfNode(currentNode) + " -> " + graph.getNameOfNode(relatedNode)
                    + ", длина которого " + graph.getWeightOfEdge(currentNode, relatedNode) + ". ";

            // релаксируем с соседом
            relax(currentNode, relatedNode);

            // инкрементируем счетчик соседей
            currentRelatedNode++;

            // если соседей больше нет, то мы сделали все, что могли
            if (currentRelatedNode == relatedNodes.size()) {
                isAlso[currentNode] = true;
                currentRelatedNode = -1;
            }
        }
    }

    // выделить родственные вершины
    private void setRelatedEdges(ArrayList<Integer> relatedNodes) {
        for (Integer relatedNode : relatedNodes) {
            ((mxCell) graph.getEdge(currentNode, relatedNode)).setStyle(StyleConstants.RELATED_EDGE_STYLE);
            setRelatedNode(relatedNode);
        }
    }

    // вернуть родственным вершинам цвет по умолчанию
    private void setDefaultEdges(ArrayList<Integer> relatedNodes) {
        for (Integer relatedNode : relatedNodes) {
            ((mxCell) graph.getEdge(currentNode, relatedNode)).setStyle(StyleConstants.DEFAULT_EDGE_STYLE);
            setDefaultNode(relatedNode);

        }
    }

    // выделить обрабатываемое ребро и вершину
    private void setHighlightOneEdge(Integer pointA, Integer pointB) {
        mxCell cell = (mxCell) graph.getEdge(pointA, pointB);
        cell.setStyle(StyleConstants.HIGHLIGHTED_EDGE_STYLE);
        setHighlightNode(pointB);
    }

    // вернуть обрабатываемому ребру и вершине привычный стиль
    private void setDefaultOneEdge(Integer pointA, Integer pointB) {
        mxCell cell = (mxCell) graph.getEdge(pointA, pointB);
        cell.setStyle(StyleConstants.RELATED_EDGE_STYLE);
        setDefaultNode(pointB);
    }

    // выделить ярко вершину
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

    // выделить вершину
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

    // вернуть вершине привычный стиль
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

    // метод релаксации
    private void relax(int pointA, int pointB) {
        int length = graph.getWeightOfEdge(pointA, pointB);
        // если новый путь лучше предыдущего
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

        } else { // если старый друг лучше нового
            message = message + "Длина до вершины осталась такой же.";
        }
    }


    // присваем вершине удобный текст
    private void updateNode(Integer nodeId, Integer newWeight, Integer newPrev) {
        mxCell cell = (mxCell) graph.getNode(nodeId);
        cell.setValue(graph.getNameOfNode(nodeId) + " (" + newWeight + ") -> " + graph.getNameOfNode(newPrev));
    }


    // ищем минимальную вершину
    private Integer findMin() {
        int minWay = Integer.MAX_VALUE;
        Integer min = -1;

        for (int i = 0; i < graph.getNumberOfNodes(); ++i) {
            if (ways[i] < minWay && !isAlso[i]) {
                minWay = ways[i];
                min = i;
            }
        }

        if (min == -1) { // если не найдено свободной вершины
            return null;
        } else {
            return min;
        }


    }

    // мы все обработали, осталось найти путь от точки а в точку б
    private WayInGraph findWay(int pointA, int pointB) {
        Integer end = pointB;

        // если мы не нашли путь
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
