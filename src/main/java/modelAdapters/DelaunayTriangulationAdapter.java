package modelAdapters;

import geometry.Point;
import geometry.Segment;
import geometry.Triangle;
import graph.Edge;
import graph.Vertex;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import models.DelaunayTriangulation;
import tools.*;
import ui.IndexedCircle;

import javax.swing.text.TextAction;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class DelaunayTriangulationAdapter extends ModelAdapter {

    final Group circleGroup = new Group();
    final Group lineGroup = new Group();

    static final Color VORONOI_EDGE_COLOR = Color.YELLOW;
    static final Color DELAUNAY_EDGE_COLOR = Color.BLUE;
    static final Color VERTEX_COLOR = Color.RED;
    static final Color BACKGROUND_COLOR = new Color(0.7, 0.7, 0.73, 1.0);

    final Group root = new Group();

    final Group delaunayVertexes = new Group();
    final Group delaunayEdges = new Group();
    final Group delaunayAngles = new Group();
    final Group voronoiEdges = new Group();
    final Group background = new Group();

    ArrayList<Integer> selectedVertexes = new ArrayList<Integer>();

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


    public DelaunayTriangulationAdapter() {
        model = new DelaunayTriangulation();

        tools.add(new AddVertexTool(this));
        tools.add(new MoveVertexTool(this));
        tools.add(new RemoveVertexTool(this));
        tools.add(new PanTool(this));
        tools.add(new ZoomTool(this));

        selectedTool = tools.get(0);

        root.getChildren().addAll(background, voronoiEdges, delaunayEdges, delaunayVertexes, delaunayAngles);
        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        this.background.getChildren().add(drawBackground());
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
    public void removeVertex(int index) {
        model.removeVertex(index);
    }

    @Override
    public void moveVertex(int index, double x, double y) {
        model.moveVertex(index, new Point(x, y));
    }

    public void selectVertex(int index) {
        selectedVertexes.clear();
        selectedVertexes.add(index);
    }

    public boolean isSelected(int index) {
        return selectedVertexes.contains(index);
    }

    public void selectVertexes(double startX, double startY, double endX, double endY) {
        selectedVertexes = ((DelaunayTriangulation) model).getVertexes(startX, startY, endX, endY);
    }

    /**
     * Adjusts the selected vertexes positions by the given x and y values.
     * @param x
     * @param y
     */
    public void moveSelectedVertexes(double x, double y) {
        ((DelaunayTriangulation) model).moveVertexes(selectedVertexes, x, y); //todo
    }

    public void removeSelectedVertexes() {
        ((DelaunayTriangulation) model).removeVertexes(selectedVertexes);
    }

    Line drawDelaunayEdge(Edge edge, int index) {

        Group group = new Group();
        Segment segment = edge.getSegment();
        Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
        line.setStrokeWidth(2);
        line.setStroke(DELAUNAY_EDGE_COLOR);

//        group.getChildren().add(line);
//
//        Point midpoint = segment.midpoint();
//
//        Text label = new Text("e"+index);
//        label.setTranslateX(midpoint.x);
//        label.setTranslateY(midpoint.y);
//        label.setStroke(Color.WHITE);
//        label.setStrokeWidth(1.5d);
//        label.setStrokeType(StrokeType.OUTSIDE);
//
//        group.getChildren().add(label);

        return line;
    }

    Group drawVoronoiEdge(Edge edge) {
        Group group = new Group();
        Segment segment = edge.getSegment();

        Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
        line.setStrokeWidth(2);
        line.setStroke(VORONOI_EDGE_COLOR);

        group.getChildren().add(line);

        return group;
    }

    Circle drawDelaunayVertex(Vertex vertex, int index, boolean selected) {
        Group group = new Group();
        Point point = vertex.getPoint();
        IndexedCircle circle = new IndexedCircle();

        circle.setFill(VERTEX_COLOR);
        circle.setRadius(8.d);
        circle.setCenterX(point.x);
        circle.setCenterY(point.y);
        circle.setStrokeWidth(2.d);
        circle.setStroke(Color.BLACK);

        circle.setIndex(index);

        circle.setOnMousePressed(vertexOnMousePressedEventHandler);
        circle.setOnMouseDragged(vertexOnMouseDraggedEventHandler);

//        group.getChildren().add(circle);
//
//        Text label = new Text("p"+index);
//        label.setTranslateX(point.x + 10.d);
//        label.setTranslateY(point.y - 10.d);
//        label.setStroke(Color.WHITE);
//        label.setStrokeWidth(1.5d);
//        label.setStrokeType(StrokeType.OUTSIDE);
//
//        group.getChildren().add(label);

        return circle;
    }

    Circle drawCircumcircle(geometry.Circle circumcircle) {
        Circle circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setRadius(circumcircle.radius);
        circle.setCenterX(circumcircle.center.x);
        circle.setCenterY(circumcircle.center.y);
        circle.setStrokeWidth(2.d);
        circle.setStroke(Color.BLACK);
        return circle;
    }

    Arc drawObtuseAngles(Triangle triangle) {
        Arc arc = new Arc();

        arc.setCenterX(triangle.a.x);
        arc.setCenterY(triangle.a.y);

        arc.setRadiusX(20.d);
        arc.setRadiusY(20.d);

        arc.setType(ArcType.ROUND);
        arc.setStartAngle(90.d);
        arc.setLength(90.d);

        return arc;
    }

    Rectangle drawBackground() {
        Rectangle rectangle = new Rectangle(0, 0, 1000, 1000);
        rectangle.setFill(BACKGROUND_COLOR);
        rectangle.setOnMousePressed(backgroundOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(backgroundOnMouseDraggedEventHandler);
        return rectangle;
    }

    @Override
    public Group draw() {

        this.delaunayEdges.getChildren().clear();
        ArrayList<Edge> delaunayEdges = ((DelaunayTriangulation) model).getDelaunayEdges();
        for (int i=0; i<delaunayEdges.size(); i++) {
            Edge delaunayEdge = delaunayEdges.get(i);
            this.delaunayEdges.getChildren().add(drawDelaunayEdge(delaunayEdge, i));
        }

        this.voronoiEdges.getChildren().clear();
        ArrayList<Edge> voronoiEdges = ((DelaunayTriangulation) model).getVoronoiEdges();
        for (int i=0; i<voronoiEdges.size(); i++) {
            Edge voronoiEdge = voronoiEdges.get(i);
            this.voronoiEdges.getChildren().add(drawVoronoiEdge(voronoiEdge));
        }

        ArrayList<geometry.Circle> circumcircles = ((DelaunayTriangulation) model).getCircumcircles();
        for (geometry.Circle circumcircle : circumcircles) {
            this.delaunayEdges.getChildren().add(drawCircumcircle(circumcircle));
        }

        ArrayList<Vertex> delaunayVertexes = ((DelaunayTriangulation) model).getDelaunayVertexes();
        if(this.delaunayVertexes.getChildren().size() == delaunayVertexes.size()) {
            int j=0;
            for (int i=0; i<delaunayVertexes.size(); i++) {
                Circle circle = (Circle) this.delaunayVertexes.getChildren().get(i);
                Point point = delaunayVertexes.get(i).getPoint();
                circle.setCenterX(point.x);
                circle.setCenterY(point.y);
                if(selectedVertexes.size() > j && selectedVertexes.get(j) == i) {
                    circle.setStroke(Color.WHITE);
                    j++;
                } else {
                    circle.setStroke(Color.BLACK);
                }
            }
        } else {
            int j=0;
            this.delaunayVertexes.getChildren().clear();
            for (int i=0; i<delaunayVertexes.size(); i++) {
                Vertex delaunayVertex = delaunayVertexes.get(i);
                if(selectedVertexes.size() > j && selectedVertexes.get(j) == i) {
                    this.delaunayVertexes.getChildren().add(drawDelaunayVertex(delaunayVertex, i, true));
                    j++;
                } else {
                    this.delaunayVertexes.getChildren().add(drawDelaunayVertex(delaunayVertex, i, false));
                }
            }
        }

        this.delaunayAngles.getChildren().clear();
        ArrayList<Triangle> triangles = ((DelaunayTriangulation) model).getDelaunayTriangles();
        for (Triangle triangle : triangles) {
            this.delaunayAngles.getChildren().add(drawObtuseAngles(triangle));
        }

        root.setTranslateX(cameraPosition.x);
        root.setTranslateY(cameraPosition.y);
        root.setScaleX(cameraZoom);
        root.setScaleY(cameraZoom);

        return root;
    }
}
