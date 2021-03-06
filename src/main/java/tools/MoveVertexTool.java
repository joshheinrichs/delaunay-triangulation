package tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import uiAdapters.UiAdapter;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class MoveVertexTool extends Tool {

    double selectStartX, selectStartY;
    double moveStartX, moveStartY;

    public MoveVertexTool(UiAdapter uiAdapter) {
        super(uiAdapter);
    }

    @Override
    public String getName() {
        return "Move Vertex";
    }

    @Override
    public void onMouseClicked(MouseEvent t) { }

    @Override
    public void onMousePressed(MouseEvent t) { }

    @Override
    public void onMouseReleased(MouseEvent t) { }

    @Override
    public void onMouseDragged(MouseEvent t) { }

    @Override
    public void backgroundOnMouseClicked(MouseEvent t) {
        uiAdapter.deselectAllVertexes();
        uiAdapter.draw();
    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {
        selectStartX = t.getX();
        selectStartY = t.getY();
    }

    @Override
    public void backgroundOnMouseReleased(MouseEvent t) {
        graphRoot.getChildren().clear();
    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {
        if (t.isShortcutDown()) {
            uiAdapter.selectVertexes(uiAdapter.getVertexes(selectStartX, selectStartY, t.getX(), t.getY()));
        } else if (t.isAltDown()) {
            uiAdapter.deselectVertexes(uiAdapter.getVertexes(selectStartX, selectStartY, t.getX(), t.getY()));
        } else {
            uiAdapter.deselectAllVertexes();
            uiAdapter.selectVertexes(uiAdapter.getVertexes(selectStartX, selectStartY, t.getX(), t.getY()));
        }

        graphRoot.getChildren().clear();
        drawSelectionArea(selectStartX, selectStartY, t.getX(), t.getY());

        uiAdapter.draw();
    }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) { }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        String id = ((Circle) t.getSource()).getId();
        if (t.isShortcutDown()) {
            uiAdapter.selectVertex(id);
        } else if (t.isAltDown()) {
            uiAdapter.deselectVertex(id);
        } else if (!uiAdapter.isVertexSelected(id)) {
            uiAdapter.deselectAllVertexes();
            uiAdapter.selectVertex(id);
        }

        moveStartX = t.getX();
        moveStartY = t.getY();

        uiAdapter.draw();
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) { }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
        double moveX = t.getX() - moveStartX;
        double moveY = t.getY() - moveStartY;

        moveStartX = t.getX();
        moveStartY = t.getY();

        uiAdapter.moveSelectedVertexes(moveX, moveY);

        uiAdapter.draw();
    }

    @Override
    public void edgeOnMouseClicked(MouseEvent t) {

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

        rectangle.setFill(new Color(0.d, 0.d, 1.d, 0.1d));
        rectangle.setStroke(new Color(0.d, 0.d, 1.d, 0.5d));
        rectangle.setStrokeWidth(2.d);

        graphRoot.getChildren().add(rectangle);
    }

}
