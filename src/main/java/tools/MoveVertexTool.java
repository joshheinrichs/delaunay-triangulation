package tools;

import geometry.Point;
import javafx.scene.input.MouseEvent;
import modelAdapters.ModelAdapter;
import models.Model;
import ui.IndexedCircle;

/**
 * Created by joshheinrichs on 15-05-06.
 */
public class MoveVertexTool extends Tool {

    double selectedX, selectedY;

    public MoveVertexTool(ModelAdapter modelAdapter) {
        super(modelAdapter);
    }

    public String getName() {
        return "Move Vertex";
    }

    @Override
    public void onMousePressed(MouseEvent t) { }

    @Override
    public void onMouseDragged(MouseEvent t) { }

    @Override
    public void backgroundOnMousePressed(MouseEvent t) { }

    @Override
    public void backgroundOnMouseDragged(MouseEvent t) { }

    @Override
    public void vertexOnMousePressed(MouseEvent t) {
        selectedX = ((IndexedCircle) t.getSource()).getCenterX();
        selectedY = ((IndexedCircle) t.getSource()).getCenterY();
    }

    @Override
    public void vertexOnMouseDragged(MouseEvent t) {
//        modelAdapter.moveVertex(selectedVertexIndex, t.getX(), t.getY());
        modelAdapter.moveVertex(selectedX, selectedY, t.getX(), t.getY());
        selectedX = t.getX();
        selectedY = t.getY();
        modelAdapter.dragDraw();
    }

}
