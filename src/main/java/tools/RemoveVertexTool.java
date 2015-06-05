package tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class RemoveVertexTool extends Tool {

    double selectStartX, selectStartY;

    public RemoveVertexTool(UiAdapter uiAdapter) {
        super(uiAdapter);
    }

    public String getName() {
        return "Remove Vertex";
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
        selectStartX = t.getX();
        selectStartY = t.getY();
    }

    @Override
    public void backgroundOnMouseReleased(MouseEvent t) {
        uiAdapter.removeSelectedVertexes();
        graphRoot.getChildren().clear();
        uiAdapter.draw();
    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {
        uiAdapter.deselectAllVertexes();
        uiAdapter.selectVertexes(uiAdapter.getVertexes(selectStartX, selectStartY, t.getX(), t.getY()));

        graphRoot.getChildren().clear();
        drawSelectionArea(selectStartX, selectStartY, t.getX(), t.getY());

        uiAdapter.draw();
    }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        int index = Integer.parseInt(((Circle) t.getSource()).getId());
        uiAdapter.removeVertex(index);
        uiAdapter.draw();
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }

    void drawSelectionArea(double startX, double startY, double endX, double endY) {
        double minX = Math.min(startX, endX);
        double minY = Math.min(startY, endY);
        double maxX = Math.max(startX, endX);
        double maxY = Math.max(startY, endY);

        Rectangle rectangle = new Rectangle();
        rectangle.setX(minX);
        rectangle.setY(minY);
        rectangle.setWidth(maxX - minX);
        rectangle.setHeight(maxY - minY);

        rectangle.setFill(new Color(1.d, 0.d, 0.d, 0.1d));
        rectangle.setStroke(new Color(1.d, 0.d, 0.d, 0.5d));
        rectangle.setStrokeWidth(2.d);

        graphRoot.getChildren().add(rectangle);
    }
}
