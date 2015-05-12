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
import tools.PanTool;
import tools.RemoveVertexTool;
import ui.IndexedCircle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class DelaunayTriangulationAdapter extends ModelAdapter {

    final Group root = new Group();
    final Group circleGroup = new Group();
    final Group lineGroup = new Group();

    public DelaunayTriangulationAdapter() {
        model = new DelaunayTriangulation();

        tools.add(new AddVertexTool(this));
        tools.add(new MoveVertexTool(this));
        tools.add(new RemoveVertexTool(this));
        tools.add(new PanTool(this));

        selectedTool = tools.get(0);
    }

    @Override
    public String getName() {
        return "Delaunay Triangulation";
    }

    @Override
    public void addVertex(double x, double y) {
        model.addVertex(new Point(x, y));
    }

    @Override
    public void removeVertex(int i) {
        model.removeVertex(i);
    }

    @Override
    public void moveVertex(int i, double x, double y) {
        model.moveVertex(i, new Point(x, y));
    }

    @Override
    public Group draw() {

        EventHandler<MouseEvent> onMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.onMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> onMouseDraggedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.onMouseDragged(t);
                    }
                };

        EventHandler<MouseEvent> backgroundOnMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.backgroundOnMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> backgroundOnMouseDraggedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.backgroundOnMouseDragged(t);
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

        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        Rectangle rectangle = new Rectangle(0,0,Double.MAX_VALUE,Double.MAX_VALUE);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setOnMousePressed(backgroundOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(backgroundOnMouseDraggedEventHandler);

        Collection<Line> lines = new ArrayList<Line>();
        Collection<Segment> segments = ((DelaunayTriangulation) model).getEdgeSegments();
        Iterator<Segment> segmentIterator = segments.iterator();
        while(segmentIterator.hasNext()) {
            Segment segment = segmentIterator.next();
            Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
            line.setStrokeWidth(2);
            lines.add(line);
        }


        Collection<javafx.scene.shape.Circle> circles = new ArrayList<javafx.scene.shape.Circle>();
        ArrayList<Point> points = ((DelaunayTriangulation) model).getVertexPoints();
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

        root.setTranslateX(cameraPosition.x);
        root.setTranslateY(cameraPosition.y);

        return root;
    }

    @Override
    public Group dragDraw() {

        EventHandler<MouseEvent> onMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.onMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> onMouseDraggedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.onMouseDragged(t);
                    }
                };

        EventHandler<MouseEvent> backgroundOnMousePressedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.backgroundOnMousePressed(t);
                    }
                };

        EventHandler<MouseEvent> backgroundOnMouseDraggedEventHandler =
                new EventHandler<MouseEvent>() {

                    public void handle(MouseEvent t) {
                        selectedTool.backgroundOnMouseDragged(t);
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

        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        Rectangle rectangle = new Rectangle(0, 0, Double.MAX_VALUE, Double.MAX_VALUE);
        rectangle.setFill(Color.WHITESMOKE);
        rectangle.setOnMousePressed(backgroundOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(backgroundOnMouseDraggedEventHandler);

        ArrayList<Segment> segments = ((DelaunayTriangulation) model).getEdgeSegments();
        Iterator<Segment> segmentIterator = segments.iterator();
        lineGroup.getChildren().clear();
        for(int i=0; i<segments.size(); i++) {
            Segment segment = segmentIterator.next();
            Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
            line.setStrokeWidth(2);
            lineGroup.getChildren().add(line);
        }

        ArrayList<Point> points = ((DelaunayTriangulation) model).getVertexPoints();
        for(int i=0; i < points.size(); i++){
            Point point = points.get(i);
            Circle circle = (Circle) circleGroup.getChildren().get(i);
            circle.setCenterX(point.x);
            circle.setCenterY(point.y);
        }


        root.setTranslateX(cameraPosition.x);
        root.setTranslateY(cameraPosition.y);

        return root;
    }
}
