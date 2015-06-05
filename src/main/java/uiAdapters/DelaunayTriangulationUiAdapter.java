package uiAdapters;

import geometry.Point;
import geometry.Segment;
import geometry.Triangle;
import graph.Edge;
import graph.Vertex;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import models.DelaunayTriangulation;
import settings.*;
import tools.*;

import java.util.ArrayList;

import static models.DelaunayTriangulation.BOUNDS;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class DelaunayTriangulationUiAdapter extends UiAdapter {


    final Group circleGroup = new Group();
    final Group lineGroup = new Group();

    static final Color VORONOI_EDGE_COLOR = Color.YELLOW;
    static final Color DELAUNAY_UNSELECTED_EDGE_COLOR = Color.BLUE;
    static final Color DELAUNAY_SELECTED_EDGE_COLOR = Color.WHITE;
    static final Color DELAUNAY_ALPHA_STABLE_EDGE_COLOR = Color.RED;
    static final Color VERTEX_COLOR = Color.RED;
    static final Color VERTEX_SELECTED_BORDER = Color.WHITE;
    static final Color VERTEX_UNSELECETED_BORDER = Color.BLACK;
    static final Color BACKGROUND_COLOR = new Color(0.7, 0.7, 0.73, 1.0);

    final Group vertexLabels = new Group();
    final Group delaunayVertexes = new Group();
    final Group delaunayEdges = new Group();
    final Group delaunayAngles = new Group();
    final Group voronoiEdges = new Group();
    final Group background = new Group();
    final Group circumcircles = new Group();

    double minDelaunayAngle = 90;
    double maxDelaunayAngle = 180;

    double alphaStability = 1.0;

    private boolean alphaVisible = true;

    public DelaunayTriangulationUiAdapter() {
        model = new DelaunayTriangulation();

        circumcircles.setVisible(false);

        MoveVertexTool moveVertexTool = new MoveVertexTool(this);
        RemoveVertexTool removeVertexTool = new RemoveVertexTool(this);

        tools.add(new AddVertexTool(this));
        tools.add(moveVertexTool);
        tools.add(removeVertexTool);
        tools.add(new PanTool(this));
        tools.add(new ZoomTool(this));
        tools.add(new DelaunayDistanceTool(this));

        root.getChildren().addAll(
                background, circumcircles, voronoiEdges, delaunayAngles, delaunayEdges, delaunayVertexes, vertexLabels,
                moveVertexTool.getGraphRoot(), removeVertexTool.getGraphRoot());

        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        selectedTool = tools.get(0);

        settings.add(new DelaunayEdgeSetting(this));
        settings.add(new VoronoiEdgeSetting(this));
        settings.add(new CircumcircleSetting(this));
        settings.add(new DelaunayAngleSetting(this));
        settings.add(new VertexLabelSetting(this));
        settings.add(new DelaunayAlphaStableSetting(this));

        drawBackground();
    }

    @Override
    public String getName() {
        return "Delaunay Triangulation";
    }

    public double getDelaunayDistance(int a, int b) {
        return ((DelaunayTriangulation) model).getDelaunayDistance(a, b);
    }

    public ArrayList<Integer> getDelaunayPath(int a, int b) {
        return ((DelaunayTriangulation) model).getDelaunayPath(a, b);
    }

    public double getStraightDistance(int a, int b) {
        return ((DelaunayTriangulation) model).getStraightDistance(a, b);
    }

    void drawDelaunayEdge(Edge edge, int index, boolean selected) {

        Group group = new Group();
        Segment segment = edge.getSegment();
        Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
        line.setStrokeWidth(2);
        if (selected) {
            line.setStroke(DELAUNAY_SELECTED_EDGE_COLOR);
        } else if (alphaVisible && ((DelaunayTriangulation.DelaunayEdge) edge).getAlphaStability() <= this.alphaStability){
            line.setStroke(DELAUNAY_ALPHA_STABLE_EDGE_COLOR);
        } else {
            line.setStroke(DELAUNAY_UNSELECTED_EDGE_COLOR);
        }

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

        this.delaunayEdges.getChildren().add(line);
    }

    void drawVoronoiEdge(Edge edge) {
        Group group = new Group();
        Segment segment = edge.getSegment();

        Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
        line.setStrokeWidth(2);
        line.setStroke(VORONOI_EDGE_COLOR);

        group.getChildren().add(line);

        this.voronoiEdges.getChildren().add(group);
    }

    void drawDelaunayVertex(Vertex vertex, int index, boolean selected) {
        Point point = vertex.getPoint();
        Circle circle = new Circle();

        circle.setFill(VERTEX_COLOR);
        circle.setRadius(8.d);
        circle.setCenterX(point.x);
        circle.setCenterY(point.y);
        circle.setStrokeWidth(2.d);

        if (selected) {
            circle.setStroke(VERTEX_SELECTED_BORDER);
        } else {
            circle.setStroke(VERTEX_UNSELECETED_BORDER);
        }

        circle.setId(Integer.toString(index));

        circle.setOnMousePressed(vertexOnMousePressedEventHandler);
        circle.setOnMouseDragged(vertexOnMouseDraggedEventHandler);

        Text label = new Text("p"+index);
        label.setTranslateX(point.x + 10.d);
        label.setTranslateY(point.y - 10.d);
        label.setStroke(Color.WHITE);
        label.setStrokeWidth(1.5d);
        label.setStrokeType(StrokeType.OUTSIDE);
        label.setCache(true);

        vertexLabels.getChildren().add(label);

        this.delaunayVertexes.getChildren().add(circle);
    }

    void updateDelaunayVertex(Vertex vertex, int index, boolean selected) {
        Circle circle = (Circle) delaunayVertexes.getChildren().get(index);

        Point point = vertex.getPoint();

        circle.setCenterX(point.x);
        circle.setCenterY(point.y);
        circle.setId(Integer.toString(index));

        if (selected) {
            circle.setStroke(VERTEX_SELECTED_BORDER);
        } else {
            circle.setStroke(VERTEX_UNSELECETED_BORDER);
        }

        Text text = (Text) vertexLabels.getChildren().get(index);
        text.setTranslateX(point.x + 10.d);
        text.setTranslateY(point.y - 10.d);
    }

    void drawCircumcircle(geometry.Circle circumcircle) {
        Circle circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setRadius(circumcircle.radius);
        circle.setCenterX(circumcircle.center.x);
        circle.setCenterY(circumcircle.center.y);
        circle.setStrokeWidth(2.d);
        circle.setStroke(Color.BLACK);

        circumcircles.getChildren().add(circle);
    }

    void drawAngles(Triangle triangle) {

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

        this.delaunayAngles.getChildren().add(group);
    }

    void drawBackground() {
        Rectangle rectangle = new Rectangle(-BOUNDS, -BOUNDS, 2*BOUNDS, 2*BOUNDS);
        rectangle.setFill(BACKGROUND_COLOR);
        rectangle.setOnMousePressed(backgroundOnMousePressedEventHandler);
        rectangle.setOnMouseDragged(backgroundOnMouseDraggedEventHandler);
        rectangle.setOnMouseReleased(backgroundOnMouseReleasedEventHandler);
        rectangle.setOnMouseClicked(backgroundOnMouseClickedEventHandler);
        this.background.getChildren().add(rectangle);
    }

    @Override
    public void draw() {

        int selectedIndex = 0;
        this.delaunayEdges.getChildren().clear();
        ArrayList<Edge> delaunayEdges = ((DelaunayTriangulation) model).getDelaunayEdges();
        for (int i = 0; i < delaunayEdges.size(); i++) {
            Edge delaunayEdge = delaunayEdges.get(i);
            if(selectedIndex < selectedEdges.size() && selectedEdges.get(selectedIndex) == i) {
                drawDelaunayEdge(delaunayEdge, i, true);
                selectedIndex++;
            } else {
                drawDelaunayEdge(delaunayEdge, i, false);
            }
        }

        this.voronoiEdges.getChildren().clear();
        ArrayList<Edge> voronoiEdges = ((DelaunayTriangulation) model).getVoronoiEdges();
        for (int i = 0; i < voronoiEdges.size(); i++) {
            Edge voronoiEdge = voronoiEdges.get(i);
            drawVoronoiEdge(voronoiEdge);
        }

        this.circumcircles.getChildren().clear();
        ArrayList<geometry.Circle> circumcircles = ((DelaunayTriangulation) model).getCircumcircles();
        for (geometry.Circle circumcircle : circumcircles) {
            drawCircumcircle(circumcircle);
        }

        ArrayList<Vertex> delaunayVertexes = ((DelaunayTriangulation) model).getDelaunayVertexes();

        for (int i = this.delaunayVertexes.getChildren().size() - 1; i >= delaunayVertexes.size(); i--) {
            this.delaunayVertexes.getChildren().remove(i);
            this.vertexLabels.getChildren().remove(i);
        }

        selectedIndex = 0;
        for (int i = 0; i < this.delaunayVertexes.getChildren().size(); i++) {
            if(selectedIndex < selectedVertexes.size() && selectedVertexes.get(selectedIndex) == i) {
                updateDelaunayVertex(delaunayVertexes.get(i), i, true);
                selectedIndex++;
            } else {
                updateDelaunayVertex(delaunayVertexes.get(i), i, false);
            }
        }
        for (int i = this.delaunayVertexes.getChildren().size(); i < delaunayVertexes.size(); i++) {
            if(selectedIndex < selectedVertexes.size() && selectedVertexes.get(selectedIndex) == i) {
                drawDelaunayVertex(delaunayVertexes.get(i), i, true);
                selectedIndex++;
            } else {
                drawDelaunayVertex(delaunayVertexes.get(i), i, false);
            }
        }

        this.delaunayAngles.getChildren().clear();
        ArrayList<Triangle> triangles = ((DelaunayTriangulation) model).getDelaunayTriangles();
        for (Triangle triangle : triangles) {
            drawAngles(triangle);
        }
    }


    public boolean isVertexLabelsVisible() {
        return vertexLabels.isVisible();
    }

    public void setVertexLabelsVisible(boolean visible) {
        vertexLabels.setVisible(visible);
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

    public boolean isAlphaVisible() {
        return this.alphaVisible;
    }

    public boolean setAlphaVisible(boolean visible) {
        return this.alphaVisible = visible;
    }

    public double getAlphaStability() {
        return this.alphaStability;
    }

    public void setAlphaStability(double alphaStability) {
        this.alphaStability = alphaStability;
    }
}
