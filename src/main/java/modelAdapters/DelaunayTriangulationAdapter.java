package modelAdapters;

import geometry.Point;
import geometry.Segment;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import models.DelaunayTriangulation;
import tools.AddVertexTool;
import tools.MoveVertexTool;
import tools.RemoveVertexTool;
import ui.IndexedCircle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class DelaunayTriangulationAdapter extends ModelAdapter {

    DelaunayTriangulation dt = new DelaunayTriangulation();

    final Group root = new Group();
    final Group circleGroup = new Group();
    final Group lineGroup = new Group();

    public DelaunayTriangulationAdapter() {
        tools.add(new AddVertexTool(this));
        tools.add(new MoveVertexTool(this));
        tools.add(new RemoveVertexTool(this));
        selectedTool = tools.get(0);
    }

    @Override
    public String getName() {
        return "Delaunay Triangulation";
    }

    @Override
    public void addVertex(double x, double y) {
        dt.addVertex(new Point(x, y));
    }

    @Override
    public void removeVertex(int i) {
        dt.removeVertex(i);
    }

    @Override
    public void moveVertex(int i, double x, double y) {
        dt.moveVertex(i, new Point(x, y));
    }

    @Override
    public Group draw() {

        EventHandler<MouseEvent> sceneOnMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.sceneOnMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> vertexOnMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.vertexOnMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> vertexOnMouseDraggedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.vertexOnMouseDragged(t);
                    }
                };

        Rectangle rectangle = new Rectangle(0,0,1200,600);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setOnMousePressed(sceneOnMousePressedEventHandler);


        Collection<Line> lines = new ArrayList<Line>();
        Collection<Segment> segments = dt.getEdgeSegments();
        Iterator<Segment> segmentIterator = segments.iterator();
        while(segmentIterator.hasNext()) {
            Segment segment = segmentIterator.next();
            Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
            line.setStrokeWidth(2);
            lines.add(line);
        }


        Collection<javafx.scene.shape.Circle> circles = new ArrayList<javafx.scene.shape.Circle>();
        ArrayList<Point> points = dt.getVertexPoints();
        for(int i=0; i<points.size(); i++){
            Point point = points.get(i);
            IndexedCircle circle = new IndexedCircle();
            circle.setIndex(i);
            circle.setFill(Color.BLACK);
            circle.setRadius(8.d);
            circle.setCenterX(point.x);
            circle.setCenterY(point.y);
            circles.add(circle);
            circle.setOnMousePressed(vertexOnMousePressedEventHandler);
            circle.setOnMouseDragged(vertexOnMouseDraggedEventHandler);
        }

        root.getChildren().clear();
        root.getChildren().add(rectangle);
        lineGroup.getChildren().clear();
        lineGroup.getChildren().addAll(lines);
        root.getChildren().addAll(lineGroup);
        circleGroup.getChildren().clear();
        circleGroup.getChildren().addAll(circles);
        root.getChildren().addAll(circleGroup);

        System.out.println("hello");

        return root;
    }

    @Override
    public Group dragDraw() {

        Rectangle rectangle = new Rectangle(0,0,1200,600);
        rectangle.setFill(Color.WHITESMOKE);

        Collection<Line> lines = new ArrayList<Line>();
        ArrayList<Segment> segments = dt.getEdgeSegments();
        Iterator<Segment> segmentIterator = segments.iterator();
        lineGroup.getChildren().clear();
        for(int i=0; i<segments.size(); i++) {
            Segment segment = segmentIterator.next();
            Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
            line.setStrokeWidth(2);
            lineGroup.getChildren().add(line);
//            Line line = (Line) lineGroup.getChildren().get(i);
//            line.setStartX(segment.start.x);
//            line.setStartY(segment.start.y);
//            line.setEndX(segment.end.x);
//            line.setEndY(segment.end.y);
        }

        ArrayList<Point> points = dt.getVertexPoints();
        for(int i=0; i < points.size(); i++){
            Point point = points.get(i);
            Circle circle = (Circle) circleGroup.getChildren().get(i);
            circle.setCenterX(point.x);
            circle.setCenterY(point.y);
        }

        System.out.println("hello");

        return root;
    }
}
