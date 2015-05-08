package models;

import geometry.Circle;
import geometry.Point;
import geometry.Segment;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import tools.AddVertexTool;
import tools.MoveVertexTool;
import tools.RemoveVertexTool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by joshheinrichs on 15-05-05.
 */
public class DelaunayTriangulation extends Model {

    ArrayList<Vertex> vertexes = new ArrayList<Vertex>();
    ArrayList<Edge> edges = new ArrayList<Edge>();

    @Override
    public void addVertex(Point location) {
        vertexes.add(new Vertex(location));
        triangulate();
    }

    @Override
    public void removeVertex(int i) {
        vertexes.remove(i);
        triangulate();
    }

    @Override
    public void moveVertex(int i, Point location) {
        vertexes.get(i).setPoint(location);
        triangulate();
    }

    public void triangulate() { //temporarily public
        System.out.println("triangulating");
        edges.clear();
        if(vertexes.size() >= 3) {
            for (int i = 0; i < vertexes.size(); i++) {
                for (int j = i + 1; j < vertexes.size(); j++) {
                    for (int k = j + 1; k < vertexes.size(); k++) {
                        Circle circle = new Circle(vertexes.get(i).getPoint(), vertexes.get(j).getPoint(), vertexes.get(k).getPoint());
                        boolean intersected = false;
                        for (int l = 0; l < vertexes.size(); l++) {
                            if (circle.contains(vertexes.get(l).getPoint())) {
                                System.out.println("intersected");
                                intersected = true;
                                break;
                            }
                        }
                        if (!intersected) {
                            edges.add(new Edge(vertexes.get(i), vertexes.get(j)));
                            edges.add(new Edge(vertexes.get(j), vertexes.get(k)));
                            edges.add(new Edge(vertexes.get(k), vertexes.get(i)));
                        }

                    }
                }
            }
        }
    }

    public ArrayList<Point> getVertexPoints() {
        ArrayList<Point> points = new ArrayList<Point>();
        Iterator<Vertex> iterator = vertexes.iterator();
        while(iterator.hasNext()) {
            points.add(iterator.next().getPoint());
        }
        return points;
    }

    public ArrayList<Segment> getEdgeSegments() {
        ArrayList<Segment> segments = new ArrayList<Segment>();
        Iterator<Edge> iterator = edges.iterator();
        System.out.println(segments);
        while(iterator.hasNext()) {
            segments.add(iterator.next().getSegment());
        }
        return segments;
    }
}
