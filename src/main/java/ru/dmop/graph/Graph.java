package ru.dmop.graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxGraph;
import com.mxgraph.view.mxStylesheet;
import ru.dmop.finderWays.WayInGraph;

import java.util.*;

import static ru.dmop.graph.StyleConstants.*;


public class Graph extends mxGraph {
    private HashMap<String, Object> nodesAndEdges;
    private Integer numberOfNodes = 0;
    private Integer numberOfEdges = 0;

    public Graph() {
        super();
        nodesAndEdges = new HashMap<String, Object>();
    }

    // конструктор копирования
    public Graph(Graph other) {
        super();

        // создаем новый хэшмэп
        nodesAndEdges = new HashMap<String, Object>();
        getModel().beginUpdate();

        // добавляем стили
        addStyles();

        try {
            Object parent = getDefaultParent();

            // прогоняем через все элементы старого графа и ищем вершины
            for (Map.Entry<String, Object> s : other.getNodesAndEdges().entrySet()) {
                mxCell cell = (mxCell) s.getValue();

                // если вершина
                if (cell.isVertex()) {

                    // вычисляем id
                    int id = getIdOfNode(cell);

                    //вставляем вершину
                    this.insertVertex(parent, id, cell.getValue(), cell.getGeometry().getX(),
                            cell.getGeometry().getY(), cell.getGeometry().getWidth(), cell.getGeometry().getHeight());

                    // не забываем вернуть зеленый и красный стиль началу и концу пути
                    String styleOfCell = cell.getStyle();
                    if (!styleOfCell.equals(StyleConstants.DEFAULT_STYLE)) {
                        mxCell obj = (mxCell) this.nodesAndEdges.get(s.getKey());
                        if (styleOfCell.equals(StyleConstants.GREEN_STYLE)) {
                            obj.setStyle(GREEN_STYLE);
                        } else {
                            obj.setStyle(RED_STYLE);
                        }

                    }

                }
            }

            // ищем все ребра
            for (Map.Entry<String, Object> entry : other.getNodesAndEdges().entrySet()) {
                mxCell cell = (mxCell) entry.getValue();
                if (cell.isEdge()) {
                    // вычисляем id начала и конца
                    String string = cell.getId().substring(4);
                    String[] nodes = string.split("_");
                    int pointA = Integer.parseInt(nodes[0]);
                    int pointB = Integer.parseInt(nodes[1]);

                    this.insertEdge(parent, pointA, pointB, (Integer) cell.getValue());
                }
            }
        } finally {
            getModel().endUpdate();
        }
    }

    public Object insertVertex(Object parent, int id, Object value, double x, double y, double width, double height) {
        String ind = "node" + id;
        Object obj = super.insertVertex(parent, ind, value, x, y, width, height, DEFAULT_STYLE);
        nodesAndEdges.put(ind, obj);
        numberOfNodes++;
        return obj;
    }

    public Object insertEdge(Object parent, int pointA, int pointB, Integer value) {
        String ind = "edge" + pointA + "_" + pointB;
        Object obj = super.insertEdge(parent, ind, value, getVertex(pointA), getVertex(pointB));
        nodesAndEdges.put(ind, obj);
        numberOfEdges++;
        return obj;
    }

    public Object getVertex(int id) {
        return nodesAndEdges.get("node" + id);
    }

    public Object getEdge(int pointA, int pointB) {
        return nodesAndEdges.get("edge" + pointA + "_" + pointB);
    }

    public Integer getWeightOfEdge(int pointA, int pointB) {
        Object edge = getEdge(pointA, pointB);
        Object weight = this.getModel().getValue(edge);
        if (weight instanceof Integer) {
            return (Integer) weight;
        } else {
            return null;
        }
    }

    public Integer getNumberOfNodes() {
        return numberOfNodes;
    }

    public Integer getNumberOfEdges() { return numberOfEdges; }

    public HashMap<String, Object> getNodesAndEdges (){ return nodesAndEdges; }

    public Integer getIdOfNode(Object object) {
        mxCell cell = (mxCell) object;
        String id = cell.getId();
        id = id.substring(4);
        return Integer.parseInt(id);
    }

    public ArrayList<Integer> getRelatedNodes(int node) {
        ArrayList<Integer> relatedNodes = new ArrayList<Integer>();

        for (int i = 0; i < getNumberOfNodes(); i++) {
            if (getEdge(node, i) != null) {
                relatedNodes.add(i);
            }
        }

        return relatedNodes;
    }

    public void highLightThePath(WayInGraph way) {
        int firstIndex = 0;
        int secondIndex = 1;
        Object help;
        int size = way.getWay().size();
        while (size != secondIndex){
            help = this.getEdge(way.getWay().get(firstIndex), way.getWay().get(secondIndex));
            this.getModel().setStyle(help, EDGE_STYLE);
            ++firstIndex;
            ++secondIndex;
        }
    }

    public void writeGraph () {
        Map <String, Object> treemap = new TreeMap<String, Object>(nodesAndEdges);

    }

    public void addStyles() {
        Hashtable<String, Object> style;
        mxStylesheet stylesheet = this.getStylesheet();

        // custom vertex style
        style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#388E3C");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(GREEN_STYLE, style);

        style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#D32F2F");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(RED_STYLE, style);

        style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#B3E5FC");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(DEFAULT_STYLE, style);

        style = new Hashtable<String, Object>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style.put(mxConstants.STYLE_STROKECOLOR, "#D32F2F");
        stylesheet.putCellStyle(EDGE_STYLE, style);

    }

}