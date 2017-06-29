package ru.dmop.graph;

import com.mxgraph.util.mxConstants;
import com.mxgraph.view.mxStylesheet;

import java.util.Hashtable;

public class StyleConstants {
    public static final String RED_STYLE = "Red";
    public static final String GREEN_STYLE = "Green";
    public static final String DEFAULT_STYLE = "Default";
    public static final String HIGHLIGHTED_EDGE_STYLE = "Edge";
    public static final String DEFAULT_EDGE_STYLE = "Edge_Default";
    public static final String HIGHLIGHTED_NODE_STYLE = "HighNode";
    public static final String HIGHLIGHTED_GREEN_NODE_STYLE = "HighGreen";
    public static final String HIGHLIGHTED_RED_NODE_STYLE = "HighRed";
    public static final String RELATED_EDGE_STYLE = "RelateEdge";
    public static final String RELATED_NODE_STYLE = "RelateNode";
    public static final String RELATED_GREEN_NODE_STYLE = "RelateGreen";
    public static final String RELATED_RED_NODE_STYLE = "RelateRed";


    public static void addStyles(Graph graph) {
        Hashtable<String, Object> style;
        mxStylesheet stylesheet = graph.getStylesheet();

        // custom vertex style
        style = new Hashtable<>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#388E3C");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(GREEN_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#D32F2F");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(RED_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_FILLCOLOR, "#B3E5FC");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(DEFAULT_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style.put(mxConstants.STYLE_STROKECOLOR, "#D32F2F");
        style.put(mxConstants.STYLE_FONTCOLOR, "#1565C0");
        stylesheet.putCellStyle(HIGHLIGHTED_EDGE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 1);
        style.put(mxConstants.STYLE_STROKECOLOR, "#BBDEFB");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(DEFAULT_EDGE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style.put(mxConstants.STYLE_STROKECOLOR, "#304FFE");
        style.put(mxConstants.STYLE_FILLCOLOR, "#B3E5FC");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(HIGHLIGHTED_NODE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style.put(mxConstants.STYLE_STROKECOLOR, "#304FFE");
        style.put(mxConstants.STYLE_FILLCOLOR, "#388E3C");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(HIGHLIGHTED_GREEN_NODE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 5);
        style.put(mxConstants.STYLE_STROKECOLOR, "#304FFE");
        style.put(mxConstants.STYLE_FILLCOLOR, "#D32F2F");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(HIGHLIGHTED_RED_NODE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_STROKECOLOR, "#FF80AB");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        style.put(mxConstants.STYLE_FONTCOLOR, "#1565C0");
        stylesheet.putCellStyle(RELATED_EDGE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_STROKECOLOR, "#8C9EFF");
        style.put(mxConstants.STYLE_FILLCOLOR, "#B3E5FC");
        style.put(mxConstants.STYLE_FONTCOLOR, "#000000");
        stylesheet.putCellStyle(RELATED_NODE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_STROKECOLOR, "#8C9EFF");
        style.put(mxConstants.STYLE_FILLCOLOR, "#388E3C");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(RELATED_GREEN_NODE_STYLE, style);

        style = new Hashtable<>();
        style.put(mxConstants.STYLE_STROKEWIDTH, 3);
        style.put(mxConstants.STYLE_STROKECOLOR, "#8C9EFF");
        style.put(mxConstants.STYLE_FILLCOLOR, "#D32F2F");
        style.put(mxConstants.STYLE_FONTCOLOR, "#ffffff");
        stylesheet.putCellStyle(RELATED_RED_NODE_STYLE, style);

    }


}
