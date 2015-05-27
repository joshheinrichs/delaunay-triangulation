package modelAdapters;

import geometry.Point;
import geometry.Segment;
import geometry.Triangle;
import graph.Edge;
import graph.Vertex;
import graphAdapters.DelaunayTriangulationGraphAdapter;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import models.DelaunayTriangulation;
import settings.CircumcircleSetting;
import settings.DelaunayAngleSetting;
import settings.DelaunayEdgeSetting;
import settings.VoronoiEdgeSetting;
import tools.*;
import ui.IndexedCircle;

import java.util.ArrayList;

import static models.DelaunayTriangulation.BOUNDS;

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

    final Group delaunayVertexes = new Group();
    final Group delaunayEdges = new Group();
    final Group delaunayAngles = new Group();
    final Group voronoiEdges = new Group();
    final Group background = new Group();
    final Group circumcircles = new Group();

    double minDelaunayAngle = 90;
    double maxDelaunayAngle = 180;

    ArrayList<Integer> selectedVertexes = new ArrayList<Integer>();

    DelaunayTriangulationGraphAdapter dtga;

    public DelaunayTriangulationAdapter() {
        model = new DelaunayTriangulation();

        dtga = new DelaunayTriangulationGraphAdapter((DelaunayTriangulation) model);

        circumcircles.setVisible(false);

        MoveVertexTool moveVertexTool = new MoveVertexTool(this);
        RemoveVertexTool removeVertexTool = new RemoveVertexTool(this);

        tools.add(new AddVertexTool(this));
        tools.add(moveVertexTool);
        tools.add(removeVertexTool);
        tools.add(new PanTool(this));
        tools.add(new ZoomTool(this));

        root.getChildren().addAll(background, circumcircles, voronoiEdges, delaunayAngles, delaunayEdges, delaunayVertexes, moveVertexTool.root, removeVertexTool.root);
        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        this.background.getChildren().add(drawBackground());

        selectedTool = tools.get(0);

        settings.add(new DelaunayEdgeSetting(this));
        settings.add(new VoronoiEdgeSetting(this));
        settings.add(new CircumcircleSetting(this));
        settings.add(new DelaunayAngleSetting(this));
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
        ((DelaunayTriangulation) model).moveVertexes(selectedVertexes, x, y);
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

    Group drawAngles(Triangle triangle) {

        Group group = new Group();

        double angleA = triangle.getAngleA();
        double angleB = triangle.getAngleB();
        double angleC = triangle.getAngleC();

        if(angleA >= minDelaunayAngle && angleA <= maxDelaunayAngle) {
            Arc a = new Arc();
            a.setCenterX(triangle.a.x);
            a.setCenterY(triangle.a.y);
            a.setRadiusX(20.d);
            a.setRadiusY(20.d);
            a.setType(ArcType.ROUND);
            a.setStartAngle(-triangle.getAngleAStart());
            a.setLength(-angleA);
            a.setStrokeWidth(2.d);
            a.setFill(Color.TRANSPARENT);
            a.setStroke(Color.BLACK);
            group.getChildren().add(a);
        }

        if(angleB >= minDelaunayAngle && angleB <= maxDelaunayAngle) {
            Arc b = new Arc();
            b.setCenterX(triangle.b.x);
            b.setCenterY(triangle.b.y);
            b.setRadiusX(20.d);
            b.setRadiusY(20.d);
            b.setType(ArcType.ROUND);
            b.setStartAngle(-triangle.getAngleBStart());
            b.setLength(-angleB);
            b.setStrokeWidth(2.d);
            b.setFill(Color.TRANSPARENT);
            b.setStroke(Color.BLACK);
            group.getChildren().add(b);
        }

        if(angleC >= minDelaunayAngle && angleC <= maxDelaunayAngle) {
            Arc c = new Arc();
            c.setCenterX(triangle.c.x);
            c.setCenterY(triangle.c.y);
            c.setRadiusX(20.d);
            c.setRadiusY(20.d);
            c.setType(ArcType.ROUND);
            c.setStartAngle(-triangle.getAngleCStart());
            c.setLength(-angleC);
            c.setStrokeWidth(2.d);
            c.setFill(Color.TRANSPARENT);
            c.setStroke(Color.BLACK);
            group.getChildren().add(c);
        }

        return group;
    }

    Rectangle drawBackground() {
        Rectangle rectangle = new Rectangle(-BOUNDS, -BOUNDS, 2*BOUNDS, 2*BOUNDS);
        rectangle.setFill(BACKGROUND_COLOR);
        rectangle.setOnMousePressed(backgroundOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(backgroundOnMouseDraggedEventHandler);
        rectangle.setOnMouseReleased(backgroundOnMouseReleasedEventHandler);
        rectangle.setOnMouseClicked(backgroundOnMouseClickedEventHandler);
        return rectangle;
    }

    @Override
    public void draw() {

        this.delaunayEdges.getChildren().clear();
        ArrayList<Edge> delaunayEdges = ((DelaunayTriangulation) model).getDelaunayEdges();
        for (int i = 0; i < delaunayEdges.size(); i++) {
            Edge delaunayEdge = delaunayEdges.get(i);
            this.delaunayEdges.getChildren().add(drawDelaunayEdge(delaunayEdge, i));
        }

        this.voronoiEdges.getChildren().clear();
        ArrayList<Edge> voronoiEdges = ((DelaunayTriangulation) model).getVoronoiEdges();
        for (int i = 0; i < voronoiEdges.size(); i++) {
            Edge voronoiEdge = voronoiEdges.get(i);
            this.voronoiEdges.getChildren().add(drawVoronoiEdge(voronoiEdge));
        }

        this.circumcircles.getChildren().clear();
        ArrayList<geometry.Circle> circumcircles = ((DelaunayTriangulation) model).getCircumcircles();
        for (geometry.Circle circumcircle : circumcircles) {
            this.circumcircles.getChildren().add(drawCircumcircle(circumcircle));
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
            this.delaunayAngles.getChildren().add(drawAngles(triangle));
        }

        dtga.update();
    }

    public boolean isDelaunayEdgesVisible() {
        return delaunayEdges.isVisible();
    }

    public void setDelaunayEdgesVisible(boolean visible) {
        delaunayEdges.setVisible(visible);
    }

    public boolean isDelaunayAnglesVisible() {
        return delaunayAngles.isVisible();
    }

    public void setDelaunayAnglesVisible(boolean visible) {
        delaunayAngles.setVisible(visible);
    }

    public boolean isVoronoiEdgesVisible() {
        return voronoiEdges.isVisible();
    }

    public void setVoronoiEdgesVisible(boolean visible) {
        voronoiEdges.setVisible(visible);
    }

    public boolean isCircumcirclesVisible() {
        System.out.println(circumcircles.isVisible());
        return circumcircles.isVisible();
    }

    public void setCircumcirclesVisible(boolean visible) {
        circumcircles.setVisible(visible);
    }

    public double getMaxDelaunayAngle() {
        return maxDelaunayAngle;
    }

    public void setMaxDelaunayAngle(double maxDelaunayAngle) {
        this.maxDelaunayAngle = maxDelaunayAngle;
    }

    public double getMinDelaunayAngle() {
        return minDelaunayAngle;
    }

    public void setMinDelaunayAngle(double minDelaunayAngle) {
        this.minDelaunayAngle = minDelaunayAngle;
    }

}
