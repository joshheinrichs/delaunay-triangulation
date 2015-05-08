package graph;


import geometry.Point;
import graph.Edge;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Vertex extends Circle {

    Point point;
    Collection<Edge> incomingEdges = new ArrayList<Edge>();
    Collection<Edge> outgoingEdges = new ArrayList<Edge>();

    public Vertex(Point point) {
        this.point = point;
    }

    public void addIncomingEdge(Edge edge) {
        incomingEdges.add(edge);
    }

    public void addOutgoingEdge(Edge edge) {
        outgoingEdges.add(edge);
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
