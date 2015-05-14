package tools;

import geometry.Point;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import modelAdapters.ModelAdapter;
import models.Model;
import ui.IndexedCircle;

/**
 * The add tool adds points
 */
public class AddVertexTool extends Tool {

    int selectedVertexIndex;

    public AddVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Add Vertex";
    }

    @Override
    public void onMousePressed(MouseEvent t) {

    }

    @Override
    public void onMouseDragged(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {
        modelAdapter.addVertex(t.getX(), t.getY());
        modelAdapter.draw();
    }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) {

    }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        selectedVertexIndex = ((IndexedCircle) t.getSource()).getIndex();
        System.out.println(selectedVertexIndex);
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
//        modelAdapter.moveVertex(selectedVertexIndex, t.getX(), t.getY());
        modelAdapter.dragDraw();
    }


}
