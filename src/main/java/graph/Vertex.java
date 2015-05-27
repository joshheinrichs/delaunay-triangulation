package graph;


import geometry.Point;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Vertex extends Circle {

    Point point;
    HashMap<String, Edge> edges = new HashMap<String, Edge>();

    public Vertex(Point point) {
        this.point = point;
    }

    public void addEdge(Edge edge) {
        edges.put(edge.toString(), edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge.toString());
    }

    public ArrayList<Edge> getEdges() {
        ArrayList<Edge> list = new ArrayList<Edge>(edges.size());
        for (String s : edges.keySet()) {
            list.add(edges.get(s));
        }
        return list;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void delete() {
        for (String s : edges.keySet()) {
            Edge edge = edges.get(s);
            if(edge.from != this) {
                edge.from.removeEdge(edge);
            } else {
                edge.to.removeEdge(edge);
            }
        }
    }

    @Override
    public String toString() {
        return this.point.toString();
    }
}
