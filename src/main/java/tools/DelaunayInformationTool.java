package tools;

import geometry.Point;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
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
        return "Information";
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
}
