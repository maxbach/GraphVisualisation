package ru.dmop.graph;

import com.mxgraph.model.mxCell;
import com.mxgraph.view.mxGraph;
import ru.dmop.finderWays.WayInGraph;

import java.util.ArrayList;
import java.util.HashMap;

import static ru.dmop.graph.StyleConstants.DEFAULT_STYLE;


public class Graph extends mxGraph {
    private HashMap<String, Object> nodesAndEdges;
    private Integer numberOfNodes;
    private Integer numberOfEdges;

    public Graph() {
        super();
        nodesAndEdges = new HashMap<String, Object>();
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

    public Integer getNumberOfEdges() {
        return numberOfEdges;
    }

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

    }


}
