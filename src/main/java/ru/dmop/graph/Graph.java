package ru.dmop.graph;

import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;


public class Graph extends mxGraph implements Serializable {
    private HashMap<String, Object> nodesAndEdges;

    public Graph() {
        super();
        nodesAndEdges = new HashMap<String, Object>();
    }

    public Object insertVertex(Object parent, int id, Object value, double x, double y, double width, double height) {
        String ind = "node" + id;
        Object obj = super.insertVertex(parent, ind, value, x, y, width, height);
        nodesAndEdges.put(ind, obj);
        return obj;
    }

    public Object insertEdge(Object parent, int pointA, int pointB, Object value) {
        String ind = "edge" + pointA + "_" + pointB;
        Object obj = super.insertEdge(parent, ind, value, getVertex(pointA), getVertex(pointB));
        nodesAndEdges.put(ind, obj);
        return obj;
    }

    public Object getVertex(int id) {
        return nodesAndEdges.get("node" + id);
    }

    public Object getEdge(int pointA, int pointB) {
        return nodesAndEdges.get("edge" + pointA + "_" + pointB);
    }

    public void writeGraph() {

        JFileChooser fc = new JFileChooser();
        if (fc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                FileOutputStream fileStream = new FileOutputStream(fc.getSelectedFile());
                ObjectOutputStream os = new ObjectOutputStream(fileStream);
                os.writeObject(this);
            }
            catch (Exception e) {
                System.out.println("Что-то пошло не так...");
            }
        }

    }


}
