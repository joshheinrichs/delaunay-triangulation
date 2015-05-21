//package models;
//
//import geometry.Point;
//import geometry.Segment;
//import graph.Edge;
//import graph.Vertex;
//import javafx.scene.Group;
//import tools.AddVertexTool;
//import tools.MoveVertexTool;
//import tools.RemoveVertexTool;
//
//import java.util.*;
//
///**
// * Created by joshheinrichs on 15-05-06.
// */
//public class CompleteGraph extends Model {
//
//
//    public Collection<Vertex> delaunayVertexes = new ArrayList<Vertex>(); //temporary
//    Collection<Edge> delaunayEdges = new ArrayList<Edge>();
//
//    @Override
//    public void addVertex(Point location) {
//        Vertex vertex = new Vertex(location);
//        Iterator<Vertex> iterator = delaunayVertexes.iterator();
//        while(iterator.hasNext()) {
//            Edge edge = new Edge(vertex, iterator.next());
//            delaunayEdges.add(edge);
//            //System.out.println("added edge " + edge.getSegment());
//        }
//        delaunayVertexes.add(vertex);
//    }
//
//    @Override
//    public void moveVertex(Vertex vertex, Point position) {
//        //TODO
//    }
//
//    @Override
//    public void moveVertex(Point point, Point location) {
//        //TODO
//    }
//
//    @Override
//    public void removeVertex(Vertex vertex) {
//        //TODO
//    }
//
//    @Override
//    public void removeVertex(Point point) {
//        //TODO
//    }
//
//    public ArrayList<Point> getVertexPoints() {
//        ArrayList<Point> points = new ArrayList<Point>();
//        Iterator<Vertex> iterator = delaunayVertexes.iterator();
//        while(iterator.hasNext()) {
//            points.add(iterator.next().getPoint());
//        }
//        return points;
//    }
//
//    public ArrayList<Segment> getEdgeSegments() {
//        ArrayList<Segment> segments = new ArrayList<Segment>();
//        Iterator<Edge> iterator = delaunayEdges.iterator();
//        System.out.println(segments);
//        while(iterator.hasNext()) {
//            segments.add(iterator.next().getSegment());
//        }
//        return segments;
//    }
//}
