package tools;

import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;
import ui.IndexedCircle;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class MoveVertexTool extends Tool {

    int selectedVertexIndex;
    double mouseStartX, mouseStartY;

    public MoveVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Move Vertex";
    }

    @Override
    public void onMousePressed(MouseEvent t) {

    }

    @Override
    public void onMouseDragged(MouseEvent t) {

    }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) {

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
        modelAdapter.moveVertex(selectedVertexIndex, t.getX(), t.getY());
        modelAdapter.dragDraw();
    }

}
