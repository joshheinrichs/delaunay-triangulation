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
import ui.App;

import java.util.List;
import java.util.stream.Collectors;

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
    final Group gabrielCircles = new Group();

    double minDelaunayAngle = 90;
    double maxDelaunayAngle = 180;

    double alphaStability = 1.0;

    private boolean alphaVisible = false;

    public DelaunayTriangulationUiAdapter(App app) {
        super(app);

        model = new DelaunayTriangulation();

        circumcircles.setVisible(false);
        gabrielCircles.setVisible(false);

        circumcircles.setMouseTransparent(true);
        gabrielCircles.setMouseTransparent(true);
        vertexLabels.setMouseTransparent(true);

        root.getChildren().addAll(background, circumcircles, gabrielCircles, voronoiEdges, delaunayAngles, delaunayEdges, delaunayVertexes, vertexLabels);

        this.addTool(new AddVertexTool(this));
        this.addTool(new MoveVertexTool(this));
        this.addTool(new RemoveVertexTool(this));
        this.addTool(new PanTool(this));
        this.addTool(new ZoomTool(this));
        this.addTool(new DelaunayDistanceTool(this));
        this.addTool(new DelaunayInformationTool(this));

        root.setOnMousePressed(onMousePressedEventHandler);
        root.setOnMouseDragged(onMouseDraggedEventHandler);

        selectedTool = tools.get(0);

        settings.add(new VertexLabelSetting(this));
        settings.add(new DelaunayEdgeSetting(this));
        settings.add(new VoronoiEdgeSetting(this));
        settings.add(new CircumcircleSetting(this));
        settings.add(new GabrielCircleSetting(this));
        settings.add(new DelaunayAngleSetting(this));
        settings.add(new DelaunayAlphaStableSetting(this));
//        settings.add(new DelaunayModeSetting(this));

        drawBackground();

        this.setCameraPosition(new Point(-DelaunayTriangulation.BOUNDS, -DelaunayTriangulation.BOUNDS));
    }

    @Override
    public void resetCameraPosition() {
        this.setCameraPosition(new Point(-DelaunayTriangulation.BOUNDS, -DelaunayTriangulation.BOUNDS));
    }

    @Override
    public String getName() {
        return "Delaunay Triangulation";
    }

    public double getDelaunayDistance(String id1, String id2) {
        return ((DelaunayTriangulation) model).getDelaunayDistance(Integer.parseInt(id1), Integer.parseInt(id2));
    }

    public List<String> getDelaunayPath(String id1, String id2) {
        return ((DelaunayTriangulation) model).getDelaunayPath(Integer.parseInt(id1), Integer.parseInt(id2))
                .stream().map(Object::toString)
                .collect(Collectors.toList());
    }

    public double getStraightDistance(String id1, String id2) {
        return ((DelaunayTriangulation) model).getStraightDistance(Integer.parseInt(id1), Integer.parseInt(id2));
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

        line.setOnMouseClicked(edgeOnMouseClickedEventHandler);
        line.setId(Integer.toString(index));

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

        circle.setOnMouseClicked(vertexOnMouseClickedEventHandler);
        circle.setOnMousePressed(vertexOnMousePressedEventHandler);
        circle.setOnMouseDragged(vertexOnMouseDraggedEventHandler);
        circle.setOnMouseReleased(vertexOnMouseReleasedEventHandler);

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

        circle.setFill(VERTEX_COLOR);

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
        circle.setStrokeWidth(1.d);
        circle.setStroke(Color.RED);

        circumcircles.getChildren().add(circle);
    }

    void drawGabrielCircles(geometry.Circle gabrielCircle) {
        Circle circle = new Circle();
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.BLACK);
        circle.setRadius(gabrielCircle.radius);
        circle.setCenterX(gabrielCircle.center.x);
        circle.setCenterY(gabrielCircle.center.y);
        circle.setStrokeWidth(1.d);
        circle.setStroke(Color.GREEN);

        gabrielCircles.getChildren().add(circle);
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
            a.setOnMouseClicked(angleOnMouseClickedEventHandler);
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
            b.setOnMouseClicked(angleOnMouseClickedEventHandler);
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
            c.setOnMouseClicked(angleOnMouseClickedEventHandler);
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
        List<Edge> delaunayEdges = ((DelaunayTriangulation) model).getDelaunayEdges();
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
        List<Edge> voronoiEdges = ((DelaunayTriangulation) model).getVoronoiEdges();
        for (int i = 0; i < voronoiEdges.size(); i++) {
            Edge voronoiEdge = voronoiEdges.get(i);
            drawVoronoiEdge(voronoiEdge);
        }

        this.circumcircles.getChildren().clear();
        List<geometry.Circle> circumcircles = ((DelaunayTriangulation) model).getCircumcircles();
        circumcircles.forEach(this::drawCircumcircle);

        this.gabrielCircles.getChildren().clear();
        List<geometry.Circle> gabrielCircles = ((DelaunayTriangulation) model).getGabrielCircles();
        gabrielCircles.forEach(this::drawGabrielCircles);

        List<Vertex> delaunayVertexes = ((DelaunayTriangulation) model).getDelaunayVertexes();

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
        List<Triangle> triangles = ((DelaunayTriangulation) model).getDelaunayTriangles();
        for (Triangle triangle : triangles) {
            drawAngles(triangle);
        }
    }

    public DelaunayTriangulation.Mode getMode() {
        return ((DelaunayTriangulation) model).getMode();
    }

    public void setMode(DelaunayTriangulation.Mode mode) {
        ((DelaunayTriangulation) model).setMode(mode);
        draw();
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

    public boolean isGabrielCirclesVisible() {
        return gabrielCircles.isVisible();
    }

    public void setGabrielCirclesVisible(boolean visible) {
        gabrielCircles.setVisible(visible);
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
