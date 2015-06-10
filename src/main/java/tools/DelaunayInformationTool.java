package tools;

import geometry.Point;
import graph.Edge;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import models.DelaunayTriangulation;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-06-03.
 */
public class DelaunayInformationTool extends Tool {

    public DelaunayInformationTool(UiAdapter uiAdapter) {
        super(uiAdapter);
    }

    @Override
    public String getName() {
        return "Info";
    }

    @Override
    public void onMouseClicked(MouseEvent t) {

    }

    @Override
    public void onMousePressed(MouseEvent t) {

    }

    @Override
    public void onMouseReleased(MouseEvent t) {

    }

    @Override
    public void onMouseDragged(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) {
        String id = ((Circle) t.getSource()).getId();
        Point point = uiAdapter.getVertex(id).getPoint();
        uiAdapter.appendToOutput("Vertex " + id + " (p" + id + "):\n" +
                        "x: " + point.x + "\n" +
                        "y: " + point.y + "\n" +
                        "\n"
        );
        uiAdapter.deselectAllVertexes();
        uiAdapter.deselectAllEdges();
        uiAdapter.selectVertex(id);
        uiAdapter.draw();
    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseClicked(MouseEvent t) {
        String id = ((Line) t.getSource()).getId();
        Edge edge = uiAdapter.getEdge(id);
        uiAdapter.appendToOutput("Edge:\n" +
                        "start: " + edge.getSegment().start + "\n" +
                        "end: " + edge.getSegment().end + "\n" +
                        "length: " + edge.getSegment().length() + "\n" +
                        "alpha: " + ((DelaunayTriangulation.DelaunayEdge) edge).getAlphaStability() + "\n" +
                        "line: " + edge.getSegment().getLine() + "\n" +
                        "hull edge: " + ((DelaunayTriangulation.DelaunayEdge) edge).isHull() + "\n" +
                        "\n"
        );
        uiAdapter.deselectAllVertexes();
        uiAdapter.deselectAllEdges();
        uiAdapter.selectEdge(id);
        uiAdapter.draw();
    }

    @Override
    public void edgeOnMousePressed(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void edgeOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void angleOnMouseClicked(MouseEvent t) {
        uiAdapter.appendToOutput("Angle:\n" +
                        "length: " + (-((Arc) t.getSource()).getLength()) + " degrees\n" +
                        "\n"
        );
    }

    @Override
    public void angleOnMousePressed(MouseEvent t) {

    }

    @Override
    public void angleOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void angleOnMouseDragged(MouseEvent t) {

    }
}
