package tools;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import modelAdapters.DelaunayTriangulationAdapter;
import modelAdapters.ModelAdapter;
import ui.IndexedCircle;

/**
 * Created by joshheinrichs on 15-05-07.
 */
public class RemoveVertexTool extends Tool {

    double selectStartX, selectStartY;

    public RemoveVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
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
        ((DelaunayTriangulationAdapter) modelAdapter).removeSelectedVertexes();
        root.getChildren().clear();
        modelAdapter.draw();
    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {
        System.out.println(selectStartX + " " + selectStartY + " " + t.getX() + " " + t.getY());
        ((DelaunayTriangulationAdapter) modelAdapter).selectVertexes(selectStartX, selectStartY, t.getX(), t.getY());

        root.getChildren().clear();
        root.getChildren().add(drawSelectionArea(selectStartX, selectStartY, t.getX(), t.getY()));

        modelAdapter.draw();
    }

    @Override
    public void vertexOnMouseClicked(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        int index = ((IndexedCircle) t.getSource()).getIndex();
        modelAdapter.removeVertex(index);
        modelAdapter.draw();
    }

    @Override
    public void vertexOnMouseReleased(MouseEvent t) {

    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {

    }

    Rectangle drawSelectionArea(double startX, double startY, double endX, double endY) {

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

        return rectangle;
    }
}
