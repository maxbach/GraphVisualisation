package ru.dmop.finderWays;

import java.util.ArrayList;

public class WayInGraph {
    private ArrayList<Integer> way;
    private Integer wayLength;

    public WayInGraph(Integer wayLength) {
        this.wayLength = wayLength;
        way = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getWay() {
        return way;
    }

    public Integer getWayLength() {
        return wayLength;
    }

    public void addNewNode(int point) {
        way.add(point);
    }
}
