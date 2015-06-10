package graph;


import geometry.Point;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class Vertex {

    Point point;
    HashMap<String, Edge> edges = new HashMap<>();

    public Vertex(Point point) {
        this.point = point;
    }

    public void addEdge(Edge edge) {
        edges.put(edge.toString(), edge);
    }

    public void removeEdge(Edge edge) {
        edges.remove(edge.toString());
    }

    public List<Edge> getEdges() {
        return edges.keySet().stream().map(edges::get).collect(Collectors.toList());
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

    public boolean isConnected(Vertex vertex) {
        Vertex temp1 = new Vertex(this.getPoint());
        Vertex temp2 = new Vertex(vertex.getPoint());

        return this.edges.containsKey(new Edge(temp1, temp2).toString())
                || this.edges.containsKey(new Edge(temp2, temp1).toString());
    }

    @Override
    public String toString() {
        return this.point.toString();
    }
}
