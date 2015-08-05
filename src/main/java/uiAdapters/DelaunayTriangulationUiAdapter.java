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

    /**
     * Constructs a new DelaunayTriangulationUiAdapter.
     * @param app App in which the Delaunay triangulation is being displayed.
     */
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

    /**
     * Returns the shortest distance between the two specified vertexes along Delaunay edges.
     * @param id1 ID of the first vertex.
     * @param id2 ID of the second vertex.
     */
    public double getDelaunayDistance(String id1, String id2) {
        return ((DelaunayTriangulation) model).getDelaunayDistance(Integer.parseInt(id1), Integer.parseInt(id2));
    }

    /**
     * Returns a list of edge ids that are involved in the shortest path between the two specified vertexes.
     * @param id1 ID of the first vertex.
     * @param id2 ID of the second vertex.
     */
    public List<String> getDelaunayPath(String id1, String id2) {
        return ((DelaunayTriangulation) model).getDelaunayPath(Integer.parseInt(id1), Integer.parseInt(id2))
                .stream().map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * Returns the straight line distance between the two specified vertexes.
     * @param id1 ID of the first vertex.
     * @param id2 ID of the second vertex.
     * @return
     */
    public double getStraightDistance(String id1, String id2) {
        return ((DelaunayTriangulation) model).getStraightDistance(Integer.parseInt(id1), Integer.parseInt(id2));
    }

    /**
     * Draws the given edge, styled as a Delaunay edge.
     * @param edge  Edge to be drawn.
     * @param index Index of the vertex, which will be used as an ID.
     * @param selected True if the vertex is selected (i.e. should be highlighted), false otherwise.
     */
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

    /**
     * Draws the given edge, styled as a Voronoi edge.
     * @param edge Edge to be drawn.
     */
    void drawVoronoiEdge(Edge edge) {
        Group group = new Group();
        Segment segment = edge.getSegment();

        Line line = new Line(segment.start.x, segment.start.y, segment.end.x, segment.end.y);
        line.setStrokeWidth(2);
        line.setStroke(VORONOI_EDGE_COLOR);

        group.getChildren().add(line);

        this.voronoiEdges.getChildren().add(group);
    }

    /**
     * Draws the given vertex, styled as a Delaunay vertex.
     * @param vertex Vertex to be drawn.
     * @param index Index of the vertex, to be used as an ID.
     * @param selected True if the vertex is selected (i.e. should be highlighted), false otherwise.
     */
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

    /**
     * Updates the position of a Delaunay vertex. This should be used whenever a vertex already exists, as it means
     * you don't have to redraw the vertex labels which are quite costly (labels are cached when created).
     * @param vertex Vertex to be updated.
     * @param index Index of the vertex to be used as an ID.
     * @param selected True if the vertex is selected (i.e. should be highlighted), false otherwise.
     */
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

    /**
     * Draws the given circle, styled as a circumcircle.
     * @param circumcircle Circumcircle to be drawn.
     */
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

    /**
     * Draws the given circle, styled as a Gabriel circle.
     * @param gabrielCircle Gabriel circle to be drawn.
     */
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

    /**
     * Draws the angles in the given triangles, provided that they are within the display range specified by
     * {@link #getMinDelaunayAngle()} and {@link #getMaxDelaunayAngle()}.
     * @param triangle Triangle for which the angles should be drawn.
     */
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

    /**
     * Draws the background.
     */
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

    /**
     * Returns the triangulation mode currently being used.
     */
    public DelaunayTriangulation.Mode getMode() {
        return ((DelaunayTriangulation) model).getMode();
    }

    /**
     * Sets the triangulation mode of the delaunay triangulation.
     * @param mode Mode to which it will be set.
     */
    public void setMode(DelaunayTriangulation.Mode mode) {
        ((DelaunayTriangulation) model).setMode(mode);
        draw();
    }

    /**
     * Returns true if vertex labels are being displayed, false otherwise.
     */
    public boolean isVertexLabelsVisible() {
        return vertexLabels.isVisible();
    }

    /**
     * Sets whether on not vertex labels are displayed.
     * @param visible True if vertex labels should be displayed, false otherwise.
     */
    public void setVertexLabelsVisible(boolean visible) {
        vertexLabels.setVisible(visible);
    }

    /**
     * Returns true if delaunay edges are being displayed, false otherwise.
     */
    public boolean isDelaunayEdgesVisible() {
        return delaunayEdges.isVisible();
    }

    /**
     * Sets whether on not delaunay edges are displayed.
     * @param visible True if delaunay edges should be displayed, false otherwise.
     */
    public void setDelaunayEdgesVisible(boolean visible) {
        delaunayEdges.setVisible(visible);
    }

    /**
     * Returns true if delaunay angles are being displayed, false otherwise.
     */
    public boolean isDelaunayAnglesVisible() {
        return delaunayAngles.isVisible();
    }

    /**
     * Sets whether on not delaunay angles are displayed.
     * @param visible True if delaunay angles should be displayed, false otherwise.
     */
    public void setDelaunayAnglesVisible(boolean visible) {
        delaunayAngles.setVisible(visible);
    }

    /**
     * Returns tue if voronoi edges are being displayed, false otherwise.
     */
    public boolean isVoronoiEdgesVisible() {
        return voronoiEdges.isVisible();
    }

    /**
     * Sets whether on not voronoi edges are displayed.
     * @param visible True if voronoi edges should be displayed, false otherwise.
     */
    public void setVoronoiEdgesVisible(boolean visible) {
        voronoiEdges.setVisible(visible);
    }

    /**
     * Returns true if circumcircles are being displayed, false otherwise.
     */
    public boolean isCircumcirclesVisible() {
        return circumcircles.isVisible();
    }

    /**
     * Sets whether on not circumcircles are displayed.
     * @param visible True if circumcircles should be displayed, false otherwise.
     */
    public void setCircumcirclesVisible(boolean visible) {
        circumcircles.setVisible(visible);
    }

    /**
     * Returns true if gabriel circles are being displayed, false otherwise.
     */
    public boolean isGabrielCirclesVisible() {
        return gabrielCircles.isVisible();
    }

    /**
     * Sets whether on not gabriel circles are displayed.
     * @param visible True if gabriel circles should be displayed, false otherwise.
     */
    public void setGabrielCirclesVisible(boolean visible) {
        gabrielCircles.setVisible(visible);
    }

    /**
     * Returns the largest delaunay angle that is displayed in degrees.
     */
    public double getMaxDelaunayAngle() {
        return maxDelaunayAngle;
    }

    /**
     * Sets the largest delaunay angle that is displayed in degrees.
     * @param maxDelaunayAngle Maximum angle in degrees.
     */
    public void setMaxDelaunayAngle(double maxDelaunayAngle) {
        this.maxDelaunayAngle = maxDelaunayAngle;
    }

    /**
     * Returns the smallest delaunay angle that is displayed in degrees.
     */
    public double getMinDelaunayAngle() {
        return minDelaunayAngle;
    }

    /**
     * Sets the smallest delaunay angle that is displayed in degrees.
     * @param minDelaunayAngle Minimum angle in degrees.
     */
    public void setMinDelaunayAngle(double minDelaunayAngle) {
        this.minDelaunayAngle = minDelaunayAngle;
    }

    /**
     * Returns true if alpha stable edges are marked, false otherwise.
     */
    public boolean isAlphaVisible() {
        return this.alphaVisible;
    }

    /**
     * Sets whether or not alpha stable edges should be marked.
     * @param visible True if alpha stable edges should be marked, false otherwise.
     */
    public void setAlphaVisible(boolean visible) {
        this.alphaVisible = visible;
    }

    /**
     * Returns the largest alpha value for which alpha stable edges are marked in radians.
     */
    public double getAlphaStability() {
        return this.alphaStability;
    }

    /**
     * Sets the largest alpha value for which alpha stable edges should be marked.
     * @param alphaStability Alpha stability value in radians.
     */
    public void setAlphaStability(double alphaStability) {
        this.alphaStability = alphaStability;
    }
}
